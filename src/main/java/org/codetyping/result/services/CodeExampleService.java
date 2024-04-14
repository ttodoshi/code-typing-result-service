package org.codetyping.result.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class CodeExampleService {
    private final RestTemplate restTemplate;

    public Boolean codeExampleExists(String bearerToken, final String UUID) {
        try {
            HttpHeaders headers = new HttpHeaders();
            if (bearerToken != null && !bearerToken.isEmpty()) {
                headers.setBearerAuth(bearerToken);
            }

            restTemplate.exchange(
                    "http://text-service/api/v1/texts/code-examples/{uuid}/",
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    String.class,
                    UUID
            );
        } catch (HttpClientErrorException e) {
            return false;
        }
        return true;
    }
}
