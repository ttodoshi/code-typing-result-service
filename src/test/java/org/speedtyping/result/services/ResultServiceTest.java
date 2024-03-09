package org.speedtyping.result.services;

import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.speedtyping.result.dto.CreateResultDto;
import org.speedtyping.result.dto.UserDto;
import org.speedtyping.result.models.Result;
import org.speedtyping.result.models.SessionResult;
import org.speedtyping.result.repositories.ResultsRepository;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ResultServiceTest {
    @Mock
    private ResultsRepository resultsRepository;
    @Mock
    private ModelMapper mapper;

    private ResultService resultService;

    @BeforeEach
    public void setUp() {
        resultService = new ResultService(resultsRepository, mapper);
    }

    @Test
    public void testFindResultsAnonymous() {
        // mocks
        HttpSession httpSession = mock(HttpSession.class);
        when(
                httpSession.getAttribute("results")
        ).thenReturn(
                new ArrayList<>(List.of(new SessionResult()))
        );
        Authentication authentication = mock(Authentication.class);

        // test
        List<?> results = resultService.findResults(authentication, httpSession);

        // asserts
        assertNotNull(results);
        assertFalse(results.isEmpty());
    }

    @Test
    public void testFindResultsAuthenticated() {
        // mocks
        HttpSession httpSession = mock(HttpSession.class);
        Authentication authentication = mock(Authentication.class);
        when(
                authentication.isAuthenticated()
        ).thenReturn(true);

        String userID = UUID.randomUUID().toString();
        when(
                authentication.getPrincipal()
        ).thenReturn(
                new UserDto(
                        userID,
                        "random_nickname"
                )
        );
        when(
                resultsRepository.findResultsByUserIDOrderByEndTime(userID)
        ).thenReturn(
                new ArrayList<>(List.of(new Result()))
        );

        // test
        List<?> results = resultService.findResults(authentication, httpSession);

        // asserts
        assertNotNull(results);
        assertFalse(results.isEmpty());
    }

    @Test
    public void testCreateResultAnonymous_whenResultsIsNull() {
        // mocks
        HttpSession httpSession = mock(HttpSession.class);
        when(
                httpSession.getAttribute("results")
        ).thenReturn(null);
        when(
                httpSession.getId()
        ).thenReturn(UUID.randomUUID().toString());
        Authentication authentication = mock(Authentication.class);

        // test
        String resultID = resultService.createResult(
                authentication, httpSession,
                new CreateResultDto(
                        UUID.randomUUID().toString(),
                        Stream.generate(() -> (int) (300 * Math.random())).limit(10).collect(Collectors.toList()),
                        (int) (300 * Math.random()),
                        LocalDateTime.now(),
                        LocalDateTime.now().plusSeconds((long) (Math.random() * 300)),
                        (int) (40 * Math.random()),
                        Math.random()
                )
        );

        // asserts
        assertTrue(StringUtils.isNotBlank(resultID));
    }

    @Test
    public void testCreateResultAnonymous_whenResultsAlreadyExists() {
        // mocks
        HttpSession httpSession = mock(HttpSession.class);
        when(
                httpSession.getAttribute("results")
        ).thenReturn(
                new ArrayList<>(List.of(new SessionResult(), new SessionResult()))
        );
        when(
                httpSession.getId()
        ).thenReturn(UUID.randomUUID().toString());
        Authentication authentication = mock(Authentication.class);

        // test
        String resultID = resultService.createResult(
                authentication, httpSession,
                new CreateResultDto(
                        UUID.randomUUID().toString(),
                        Stream.generate(() -> (int) (300 * Math.random())).limit(10).collect(Collectors.toList()),
                        (int) (300 * Math.random()),
                        LocalDateTime.now(),
                        LocalDateTime.now().plusSeconds((long) (Math.random() * 300)),
                        (int) (40 * Math.random()),
                        Math.random()
                )
        );

        // asserts
        assertTrue(StringUtils.isNotBlank(resultID));
    }

    @Test
    public void testCreateResultAuthenticated() {
        // mocks
        HttpSession httpSession = mock(HttpSession.class);
        Authentication authentication = mock(Authentication.class);
        when(
                authentication.isAuthenticated()
        ).thenReturn(true);

        String userID = UUID.randomUUID().toString();
        when(
                authentication.getPrincipal()
        ).thenReturn(
                new UserDto(
                        userID,
                        "random_nickname"
                )
        );
        Result result = new Result();
        result.setID(UUID.randomUUID().toString());
        when(resultsRepository.save(any(Result.class))).thenReturn(result);
        when(
                mapper.map(any(CreateResultDto.class), eq(Result.class))
        ).thenReturn(result);

        // test
        String resultID = resultService.createResult(
                authentication, httpSession,
                new CreateResultDto(
                        UUID.randomUUID().toString(),
                        Stream.generate(() -> (int) (300 * Math.random())).limit(10).collect(Collectors.toList()),
                        (int) (300 * Math.random()),
                        LocalDateTime.now(),
                        LocalDateTime.now().plusSeconds((long) (Math.random() * 300)),
                        (int) (40 * Math.random()),
                        Math.random()
                )
        );

        // asserts
        assertTrue(StringUtils.isNotBlank(resultID));
    }
}