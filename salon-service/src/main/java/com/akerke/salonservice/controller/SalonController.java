package com.akerke.salonservice.controller;

import com.akerke.salonservice.common.payload.SalonSearch;
import com.akerke.salonservice.domain.dto.SalonDTO;
import com.akerke.salonservice.domain.service.SalonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static com.akerke.salonservice.common.specs.SalonSpecifications.buildSpecification;
import static com.akerke.salonservice.common.validate.Validator.validate;

@Controller
@RequestMapping("salon")
@Api("Salon API")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class SalonController {

    private final SalonService salonService;

    @PostMapping()
    @ApiOperation("Create a new salon")
    ResponseEntity<?> save(
            @Valid
            @RequestBody SalonDTO salonDTO,
            BindingResult bindingResult
    ) {
        validate(bindingResult);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(salonService.save(salonDTO));
    }

    @GetMapping("{id}")
    @ApiOperation("Get an salon by ID")
    ResponseEntity<?> getById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(salonService.getById(id));
    }

    @GetMapping()
    @ApiOperation("Get a list of all salons")
    ResponseEntity<?> getAll() {
        return ResponseEntity.ok(salonService.getAll());
    }

    @ApiOperation("Update an appointment by ID")
    @RequestMapping(method = {RequestMethod.PATCH, RequestMethod.PUT}, value = "{id}")
    ResponseEntity<?> update(
            @RequestBody SalonDTO salonDTO,
            @PathVariable Long id
    ) {
        salonService.update(salonDTO, id);
        return ResponseEntity.accepted().build();
    }

    @PutMapping("add-image/{id}")
    ResponseEntity<?> addImage(
            @RequestParam String imageId,
            @PathVariable Long id,
            @RequestParam Boolean isMainImage
    ) {
        salonService.addImage(id, imageId, isMainImage);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("{id}")
    @ApiOperation("Delete an salon by ID")
    ResponseEntity<?> delete(
            @PathVariable Long id
    ) {
        salonService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "search")
    @ApiOperation("Search for salon")
    ResponseEntity<?> search(
            @RequestBody SalonSearch searchRequest,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false,defaultValue = "5") int size
    ) {
        return ResponseEntity
                .ok(salonService.find(searchRequest,page,size));
    }
}

