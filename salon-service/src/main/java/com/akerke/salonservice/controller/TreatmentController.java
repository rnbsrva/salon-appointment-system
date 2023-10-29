package com.akerke.salonservice.controller;

import com.akerke.salonservice.domain.dto.TreatmentDTO;
import com.akerke.salonservice.domain.service.TreatmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static com.akerke.salonservice.common.validatе.Validator.validate;

@RestController
@RequestMapping("treatment")
@RequiredArgsConstructor
public class TreatmentController {

    private final TreatmentService treatmentService;

    @PostMapping()
    ResponseEntity<?> save (
            @Valid
            @RequestBody TreatmentDTO treatmentDTO,
            BindingResult bindingResult
            ){
        validate(bindingResult);
        return  ResponseEntity.status(HttpStatus.CREATED)
                .body(treatmentService.save(treatmentDTO));
    }


    @GetMapping("{id}")
    ResponseEntity<?> getById (
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(treatmentService.getById(id));
    }

    @GetMapping()
    ResponseEntity<?> getAll (){
        return  ResponseEntity.ok(treatmentService.getAll());
    }

    @RequestMapping(method = {RequestMethod.PATCH, RequestMethod.PUT}, value = "{id}")
    ResponseEntity<?> update (
            @RequestBody TreatmentDTO treatmentDTO,
            @PathVariable Long id
    ) {
        treatmentService.update(treatmentDTO, id);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("{id}")
    ResponseEntity<?> delete (
            @PathVariable Long id
    ){
        treatmentService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
