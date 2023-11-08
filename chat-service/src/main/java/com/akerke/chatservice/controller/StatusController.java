package com.akerke.chatservice.controller;

import com.akerke.chatservice.domain.service.UserStatusService;
import com.akerke.loggerlib.common.annotation.EnableLoggerLib;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api("Status API")
@EnableLoggerLib
public class StatusController {

    private final UserStatusService userStatusService;

    @GetMapping("user/{id}")
    @ApiOperation("Check if a user is online")
    ResponseEntity<?> userIsOnline(
            @PathVariable Long id
    ) {
        return ResponseEntity
                .ok(CompletableFuture.supplyAsync(() -> userStatusService.isOnline(id)));
    }

    @GetMapping("salon/{id}")
    @ApiOperation("Check if support chat is online")
    ResponseEntity<?> supportChatIsOnline(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                CompletableFuture.supplyAsync(() -> userStatusService.supportChatIsOnline(id))
        );
    }

}
