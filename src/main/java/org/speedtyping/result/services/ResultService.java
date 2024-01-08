package org.speedtyping.result.services;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.speedtyping.result.dto.CreateResultDto;
import org.speedtyping.result.dto.GetResultDto;
import org.speedtyping.result.models.Result;
import org.speedtyping.result.models.SessionResult;
import org.speedtyping.result.repositories.ResultsRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResultService {
    private final ResultsRepository resultsRepository;

    private final ModelMapper mapper;

    public List<SessionResult> findSessionResultsBySessionID(HttpSession httpSession) {
        log.debug("received get results unauthorized request");
        List<SessionResult> results = (List<SessionResult>) httpSession.getAttribute("results");
        if (results == null) {
            return Collections.emptyList();
        }
        return results;
    }

    public List<GetResultDto> findResultsByUserID(String userID) {
        log.debug("received get results authorized request");
        return resultsRepository
                .findResultsByUserIDOrderByEndTime(userID)
                .stream()
                .map(r -> mapper.map(r, GetResultDto.class))
                .toList();
    }

    public String createSessionResultBySessionID(HttpSession httpSession, CreateResultDto createResultDto) {
        log.debug("received create results unauthorized request");
        List<SessionResult> results = (List<SessionResult>) httpSession.getAttribute("results");
        if (results == null) {
            results = List.of(mapper.map(
                    createResultDto,
                    SessionResult.class
            ));
            httpSession.setAttribute("results", results);
        } else {
            ArrayList<SessionResult> sessionResults = new ArrayList<>(results);
            sessionResults.add(
                    mapper.map(
                            createResultDto,
                            SessionResult.class
                    )
            );
            httpSession.setAttribute("results", sessionResults);
        }
        return httpSession.getId();
    }

    public String createResultByUserID(String userID, CreateResultDto createResultDto) {
        log.debug("received create results authorized request");
        Result result = mapper.map(createResultDto, Result.class);
        result.setUserID(userID);
        return resultsRepository.save(
                result
        ).getID();
    }
}
