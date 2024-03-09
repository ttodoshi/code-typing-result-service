package org.speedtyping.result.aspects;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.speedtyping.result.dto.CreateResultDto;
import org.speedtyping.result.exceptions.classes.CodeExampleNotFoundException;
import org.speedtyping.result.exceptions.classes.ConnectException;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Aspect
@Component
@RequiredArgsConstructor
public class CourseThemeAspect {
    private final RestTemplate restTemplate;
    private final DiscoveryClient discoveryClient;

    @Pointcut(value = "execution(* org.speedtyping.result.services.ResultService.createResultByUserID(..)) " +
            "&& args(userID, createResultDto)", argNames = "userID, createResultDto")
    public void checkCodeExampleExistsOnCreateResultByUserID(String userID, CreateResultDto createResultDto) {
    }

    @Pointcut(value = "execution(* org.speedtyping.result.services.ResultService.createSessionResultBySession(..)) " +
            "&& args(httpSession, createResultDto)", argNames = "httpSession, createResultDto")
    public void checkCodeExampleExistsOnCreateResultBySessionID(HttpSession httpSession, CreateResultDto createResultDto) {
    }

    @Before(value = "checkCodeExampleExistsOnCreateResultByUserID(ignoredUserID, createResultDto)", argNames = "ignoredUserID, createResultDto")
    public void checkCodeExampleExistsBeforeCreateResult(String ignoredUserID, CreateResultDto createResultDto) {
        checkCodeExampleExists(createResultDto.getCodeExampleUUID());
    }

    @Before(value = "checkCodeExampleExistsOnCreateResultBySessionID(ignoredHttpSession, createResultDto)", argNames = "ignoredHttpSession, createResultDto")
    public void checkCodeExampleExistsBeforeCreateResult(HttpSession ignoredHttpSession, CreateResultDto createResultDto) {
        checkCodeExampleExists(createResultDto.getCodeExampleUUID());
    }

    private void checkCodeExampleExists(String UUID) {
        try {
            restTemplate.getForEntity(
                    discoveryClient
                            .getInstances("text-service")
                            .getFirst()
                            .getUri() + "/api/v1/texts/code-examples/" + UUID + "/",
                    String.class
            );
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().is4xxClientError()) {
                throw new CodeExampleNotFoundException(UUID);
            } else if (e.getStatusCode().is5xxServerError()) {
                throw new ConnectException();
            }
        }
    }
}
