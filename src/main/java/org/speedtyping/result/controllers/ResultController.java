package org.speedtyping.result.controllers;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.speedtyping.result.dto.CreateResultDto;
import org.speedtyping.result.services.ResultService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/results/")
@RequiredArgsConstructor
public class ResultController {
    private final ResultService resultService;

    @GetMapping
    public ResponseEntity<?> findResults(
            @CurrentSecurityContext(expression = "authentication") Authentication authentication,
            HttpSession httpSession
    ) {
        return ResponseEntity.ok(
                resultService.findResults(authentication, httpSession)
        );
    }

    @PostMapping
    public ResponseEntity<?> createResult(
            @CurrentSecurityContext(expression = "authentication") Authentication authentication,
            HttpSession httpSession,
            @RequestBody CreateResultDto createResultDto) {
        return ResponseEntity.ok(
                resultService
                        .createResult(authentication, httpSession, createResultDto)
        );
    }
}