package org.codetyping.result.consumers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.codetyping.result.dto.MigrateSessionResultsDto;
import org.codetyping.result.models.Result;
import org.codetyping.result.models.SessionResult;
import org.codetyping.result.repositories.ResultsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class RabbitMQConsumer {
    private final SessionRepository<? extends Session> sessionRepository;
    private final ResultsRepository resultsRepository;

    private final ModelMapper mapper;

    @RabbitListener(queues = "results-queue")
    @Transactional
    public void migrateSessionResults(MigrateSessionResultsDto migrateSessionResultsDto) {
        log.debug("migrate session results message");

        String sessionId = new String(
                Base64.getDecoder().decode(migrateSessionResultsDto.getSession()),
                StandardCharsets.UTF_8
        );
        saveResults(sessionId, migrateSessionResultsDto);

        sessionRepository.deleteById(sessionId);
    }

    private void saveResults(String sessionId, MigrateSessionResultsDto migrateSessionResultsDto) {
        Session session = sessionRepository.findById(sessionId);
        if (session == null) {
            return;
        }
        List<SessionResult> sessionResults = session.getAttribute("results");
        if (sessionResults == null) {
            return;
        }

        List<Result> results = sessionResults.stream()
                .map(r -> {
                    Result result = mapper.map(r, Result.class);
                    result.setUserID(migrateSessionResultsDto.getUserID());
                    return result;
                })
                .collect(Collectors.toList());
        resultsRepository.saveAll(results);
    }
}