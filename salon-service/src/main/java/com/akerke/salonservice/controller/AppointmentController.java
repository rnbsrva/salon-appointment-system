package com.akerke.salonservice.controller;

import com.akerke.salonservice.dto.AppointmentDTO;
import com.akerke.salonservice.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("appointment")
@RequiredArgsConstructor
@RestController
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping()
    ResponseEntity<?> save (
            @RequestBody AppointmentDTO appointmentDTO
    ){
        return  ResponseEntity.status(HttpStatus.CREATED)
                .body(appointmentService.save(appointmentDTO));
    }


    @GetMapping("{id}")
    ResponseEntity<?> getById (
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(appointmentService.getById(id));
    }

    @GetMapping()
    ResponseEntity<?> getAll (){
        return  ResponseEntity.ok(appointmentService.getAll());
    }

    @RequestMapping(method = {RequestMethod.PATCH, RequestMethod.PUT}, value = "{id}")
    ResponseEntity<?> update (
            @RequestBody AppointmentDTO appointmentDTO,
            @PathVariable Long id
    ) {
        appointmentService.update(appointmentDTO, id);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("{id}")
    ResponseEntity<?> delete (
            @PathVariable Long id
    ){
        appointmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
