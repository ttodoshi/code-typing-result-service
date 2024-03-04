package org.speedtyping.result.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.speedtyping.result.dto.CreateResultDto;
import org.speedtyping.result.services.ResultService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    @Operation(summary = "Find results", tags = "results")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Find all results",
                    content = {
                            @Content(
                                    mediaType = "application/json"
                            )
                    })
    })
    public ResponseEntity<?> findResults(
            @CurrentSecurityContext(expression = "authentication") Authentication authentication,
            HttpSession httpSession
    ) {
        return ResponseEntity.ok(
                resultService.findResults(authentication, httpSession)
        );
    }

    @PostMapping
    @Operation(summary = "Add result", tags = "results")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Result added",
                    content = {
                            @Content(
                                    mediaType = "application/json"
                            )
                    })
    })
    public ResponseEntity<?> createResult(
            @CurrentSecurityContext(expression = "authentication") Authentication authentication,
            HttpSession httpSession,
            @RequestBody CreateResultDto createResultDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.TEXT_PLAIN)
                .body(
                        resultService
                                .createResult(authentication, httpSession, createResultDto)
                );
    }
}