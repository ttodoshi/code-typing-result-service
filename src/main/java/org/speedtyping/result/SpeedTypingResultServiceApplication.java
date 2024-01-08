package org.speedtyping.result;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
@EnableDiscoveryClient
@EnableRedisHttpSession
public class SpeedTypingResultServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpeedTypingResultServiceApplication.class, args);
    }

}
