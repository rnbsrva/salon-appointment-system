package com.akerke.chatservice.controller;

import com.akerke.chatservice.service.UserStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@RestController
@RequestMapping("status")
public class StatusController {

    private final UserStatusService userStatusService;

    @GetMapping("{id}")
    ResponseEntity<?> userIsOnline(
            @PathVariable Long id
    ) {
        return ResponseEntity
                .ok(CompletableFuture.supplyAsync(() -> userStatusService.isOnline(id)));
    }

    @GetMapping("{id}")
    ResponseEntity<?> supportChatIsOnline(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                CompletableFuture.supplyAsync(() -> userStatusService.supportChatIsOnline(id))
        );
    }

}
