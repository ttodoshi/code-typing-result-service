package org.codetyping.result.services;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class CodeExampleService {
    private final RestTemplate restTemplate;
    private final DiscoveryClient discoveryClient;

    public Boolean codeExampleExists(final String UUID) {
        try {
            restTemplate.getForEntity(
                    discoveryClient
                            .getInstances("text-service")
                            .getFirst()
                            .getUri() + "/api/v1/texts/code-examples/" + UUID + "/",
                    String.class
            );
        } catch (HttpClientErrorException e) {
            return false;
        }
        return true;
    }
}
