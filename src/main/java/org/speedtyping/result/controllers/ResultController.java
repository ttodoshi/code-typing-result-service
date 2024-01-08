package org.speedtyping.result.controllers;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.speedtyping.result.dto.CreateResultDto;
import org.speedtyping.result.dto.UserDto;
import org.speedtyping.result.services.ResultService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/results/")
@RequiredArgsConstructor
public class ResultController {
    private final ResultService resultService;

    @GetMapping
    public ResponseEntity<?> findResults(HttpSession httpSession) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(
                authentication == null || !authentication.isAuthenticated() || authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))
                        ? resultService.findSessionResultsBySessionID(httpSession)
                        : resultService.findResultsByUserID(((UserDto) authentication.getPrincipal()).getUserID())
        );
    }

    @PostMapping
    public ResponseEntity<?> createResult(HttpSession httpSession,
                                          @RequestBody CreateResultDto createResultDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(
                authentication == null || !authentication.isAuthenticated() || authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))
                        ? resultService.createSessionResultBySessionID(
                        httpSession,
                        createResultDto
                )
                        : resultService.createResultByUserID(
                        ((UserDto) authentication.getPrincipal()).getUserID(),
                        createResultDto

                )
        );
    }
}