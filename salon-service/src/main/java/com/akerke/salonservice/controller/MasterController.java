package com.akerke.salonservice.controller;

import com.akerke.salonservice.domain.dto.MasterDTO;
import com.akerke.salonservice.domain.service.MasterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.akerke.salonservice.common.validatе.Validator.validate;

@RestController
@RequestMapping("master")
@RequiredArgsConstructor
public class MasterController {

    private final MasterService masterService;

    @PostMapping()
    ResponseEntity<?> save (
            @Valid
            @RequestBody MasterDTO masterDTO,
            BindingResult bindingResult
    ){
        validate(bindingResult);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(masterService.save(masterDTO));
    }

    @GetMapping("{id}")
    ResponseEntity<?> getById (
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(masterService.getById(id));
    }

    @GetMapping()
    ResponseEntity<?> getAll (){
        return  ResponseEntity.ok(masterService.getAll());
    }

    @RequestMapping(method = {RequestMethod.PATCH, RequestMethod.PUT}, value = "{id}")
    ResponseEntity<?> update (
            @RequestBody MasterDTO masterDTO,
            @PathVariable Long id
    ) {
        masterService.update(masterDTO, id);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("{id}")
    ResponseEntity<?> delete (
            @PathVariable Long id
    ){
        masterService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("{id}/add")
    ResponseEntity<?> addTreatment(
            @RequestBody List<Long> treatmentIds,
            @PathVariable Long id
            ){
        masterService.addTreatment(id,  treatmentIds);
        return ResponseEntity.accepted().build();

    }

}
