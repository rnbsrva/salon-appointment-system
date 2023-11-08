package com.akerke.salonservice.controller;

import com.akerke.loggerlib.common.annotation.EnableLoggerLib;
import com.akerke.salonservice.domain.service.ManagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("manager")
@RequiredArgsConstructor
@Api("Manager Api")
@EnableLoggerLib
public class ManagerController {

    private final ManagerService managerService;

    @PostMapping
    @ApiOperation("Make the manager")
    ResponseEntity<?> makeManager(
            @RequestParam Long userId,
            @RequestParam Long salonId
    ) {
        managerService.add(userId, salonId);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping
    @ApiOperation("Remove the manager")
    ResponseEntity<?> removeManager (
            @RequestParam Long userId,
            @RequestParam Long salonId
    ) {
        managerService.remove(userId, salonId);
        return ResponseEntity.noContent().build();
    }

}
