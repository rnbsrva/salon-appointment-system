package com.akerke.salonservice.controller;

import com.akerke.salonservice.domain.service.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("manager")
@RequiredArgsConstructor
public class ManagerController {

    private final ManagerService managerService;

    @PostMapping
    ResponseEntity<?> makeManager(
            @RequestParam Long userId,
            @RequestParam Long salonId
    ) {
        managerService.add(userId, salonId);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping
    ResponseEntity<?> removeManager (
            @RequestParam Long userId,
            @RequestParam Long salonId
    ) {
        managerService.remove(userId, salonId);
        return ResponseEntity.noContent().build();
    }

}
