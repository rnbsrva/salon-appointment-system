package com.akerke.salonservice.controller;

import com.akerke.loggerlib.common.annotation.EnableLoggerLib;
import com.akerke.salonservice.domain.dto.AppointmentDTO;
import com.akerke.salonservice.domain.service.AppointmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static com.akerke.salonservice.common.validate.Validator.validate;

@RequestMapping("appointment")
@RequiredArgsConstructor
@RestController
@Api(value = "Appointment API")
@EnableLoggerLib
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping()
    @ApiOperation("Create a new appointment")
    ResponseEntity<?> save(
            @Valid @RequestBody AppointmentDTO appointmentDTO,
            BindingResult bindingResult
    ) {
        validate(bindingResult);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(appointmentService.save(appointmentDTO));
    }


    @GetMapping("{id}")
    @ApiOperation("Get an appointment by ID")
    ResponseEntity<?> getById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(appointmentService.getById(id));
    }

    @GetMapping()
    @ApiOperation("Get a list of all appointments")
    ResponseEntity<?> getAll() {
        return ResponseEntity.ok(appointmentService.getAll());
    }

    @RequestMapping(method = {RequestMethod.PATCH, RequestMethod.PUT}, value = "{id}")
    @ApiOperation("Update an appointment by ID")
    ResponseEntity<?> update(
            @RequestBody AppointmentDTO appointmentDTO,
            @PathVariable Long id
    ) {
        appointmentService.update(appointmentDTO, id);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("{id}")
    @ApiOperation("Delete an appointment by ID")
    ResponseEntity<?> delete(
            @PathVariable Long id
    ) {
        appointmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
