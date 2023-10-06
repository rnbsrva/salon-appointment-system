package com.akerke.salonservice.controller;

import com.akerke.salonservice.dto.MasterDTO;
import com.akerke.salonservice.dto.TreatmentDTO;
import com.akerke.salonservice.service.TreatmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("treatment")
@RequiredArgsConstructor
public class TreatmentController {

    private final TreatmentService treatmentService;

    @PostMapping()
    ResponseEntity<?> save (
            @RequestBody TreatmentDTO treatmentDTO
            ){
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
