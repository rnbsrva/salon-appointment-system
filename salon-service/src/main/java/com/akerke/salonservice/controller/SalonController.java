package com.akerke.salonservice.controller;

import com.akerke.salonservice.dto.SalonDTO;
import com.akerke.salonservice.entity.Salon;
import com.akerke.salonservice.service.SalonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("salon")
@RequiredArgsConstructor
public class SalonController {

    private final SalonService salonService;

    @PostMapping()
    ResponseEntity<?> save (
            @RequestBody SalonDTO salonDTO
            ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(salonService.save(salonDTO));
    }

    @GetMapping("{id}")
    ResponseEntity<?> getById (
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(salonService.getById(id));
    }

    @GetMapping()
    ResponseEntity<?> getAll (){
        return  ResponseEntity.ok(salonService.getAll());
    }

    @RequestMapping(method = {RequestMethod.PATCH, RequestMethod.PUT}, value = "{id}")
    ResponseEntity<?> update (
            @RequestBody SalonDTO salonDTO,
            @PathVariable Long id
    ) {
        salonService.update(salonDTO, id);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("{id}")
    ResponseEntity<?> delete (
            @PathVariable Long id
    ){
        salonService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
