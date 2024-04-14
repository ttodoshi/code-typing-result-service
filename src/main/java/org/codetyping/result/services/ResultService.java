package org.codetyping.result.services;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.codetyping.result.dto.CreateResultDto;
import org.codetyping.result.dto.GetResultDto;
import org.codetyping.result.dto.UserDto;
import org.codetyping.result.exceptions.classes.CodeExampleNotFoundException;
import org.codetyping.result.models.Result;
import org.codetyping.result.models.SessionResult;
import org.codetyping.result.repositories.ResultsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResultService {
    private final CodeExampleService codeExampleService;
    private final ResultsRepository resultsRepository;

    private final ModelMapper mapper;

    public Page<?> findResults(Authentication authentication, HttpSession httpSession, Integer page, Integer size, String direction, String sortBy) {
        return isAnonymous(authentication)
                ? findSessionResultsBySessionID(httpSession)
                : findResultsByUserID(((UserDto) authentication.getPrincipal()).getUserID(), page, size, direction, sortBy);
    }

    @SuppressWarnings("unchecked")
    private Page<SessionResult> findSessionResultsBySessionID(HttpSession httpSession) {
        log.debug("received get results unauthorized request");
        List<SessionResult> results = (List<SessionResult>) httpSession.getAttribute("results");
        if (results == null) {
            return Page.empty();
        }
        return new PageImpl<>(results.reversed());
    }

    private Page<GetResultDto> findResultsByUserID(String userID, Integer page, Integer size, String direction, String sortBy) {
        log.debug("received get results authorized request");

        return resultsRepository
                .findAllByUserID(userID, PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sortBy)))
                .map(r -> mapper.map(r, GetResultDto.class));
    }

    public String createResult(Authentication authentication, HttpSession httpSession, CreateResultDto createResultDto) {
        if (!codeExampleService.codeExampleExists(
                (String) authentication.getCredentials(),
                createResultDto.getCodeExampleUUID()
        )) {
            throw new CodeExampleNotFoundException(createResultDto.getCodeExampleUUID());
        }
        return isAnonymous(authentication)
                ? createSessionResultBySession(
                httpSession,
                createResultDto
        )
                : createResultByUserID(
                ((UserDto) authentication.getPrincipal()).getUserID(),
                createResultDto
        );
    }

    @SuppressWarnings("unchecked")
    private String createSessionResultBySession(HttpSession httpSession, CreateResultDto createResultDto) {
        log.debug("received create results unauthorized request");
        List<SessionResult> results = (List<SessionResult>) httpSession.getAttribute("results");
        if (results == null) {
            results = new ArrayList<>();
        }
        results.add(mapper.map(
                createResultDto,
                SessionResult.class
        ));
        httpSession.setAttribute("results", results);
        return httpSession.getId();
    }

    private String createResultByUserID(String userID, CreateResultDto createResultDto) {
        log.debug("received create results authorized request");
        Result result = mapper.map(createResultDto, Result.class);
        result.setUserID(userID);
        return resultsRepository.save(
                result
        ).getID();
    }

    private boolean isAnonymous(Authentication authentication) {
        return authentication == null ||
                !authentication.isAuthenticated() ||
                authentication.getAuthorities().contains(
                        new SimpleGrantedAuthority("ROLE_ANONYMOUS")
                );
    }
}
