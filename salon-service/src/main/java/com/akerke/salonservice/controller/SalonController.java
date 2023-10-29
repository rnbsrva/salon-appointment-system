package com.akerke.salonservice.controller;

import com.akerke.salonservice.common.payload.SalonSearchRequest;
import com.akerke.salonservice.domain.dto.SalonDTO;
import com.akerke.salonservice.domain.service.SalonService;
import com.akerke.salonservice.infrastructure.elastic.service.SalonElasticService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static com.akerke.salonservice.common.validatе.Validator.validate;

@Controller
@RequestMapping("salon")
@RequiredArgsConstructor
public class SalonController {

    private final SalonService salonService;
    private final SalonElasticService salonElasticService;

    @PostMapping()
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
    ResponseEntity<?> getById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(salonService.getById(id));
    }

    @GetMapping()
    ResponseEntity<?> getAll() {
        return ResponseEntity.ok(salonService.getAll());
    }

    @RequestMapping(method = {RequestMethod.PATCH, RequestMethod.PUT}, value = "{id}")
    ResponseEntity<?> update(
            @RequestBody SalonDTO salonDTO,
            @PathVariable Long id
    ) {
        salonService.update(salonDTO, id);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("{id}")
    ResponseEntity<?> delete(
            @PathVariable Long id
    ) {
        salonService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("suggest")
    ResponseEntity<?> fetchSuggestions(
            @RequestParam String query
    ) {
        return ResponseEntity.
                ok(salonElasticService.fetchSuggestions(query));
    }

    @PostMapping("search")
    ResponseEntity<?> search(
            @RequestBody SalonSearchRequest searchRequest,
            @RequestParam(defaultValue = "0") int from,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity
                .ok(salonElasticService.search(searchRequest, from, size));
    }
}

