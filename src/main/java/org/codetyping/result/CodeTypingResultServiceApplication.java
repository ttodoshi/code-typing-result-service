package org.codetyping.result;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
@EnableDiscoveryClient
@EnableRedisHttpSession
public class CodeTypingResultServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodeTypingResultServiceApplication.class, args);
    }

}
