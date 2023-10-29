package com.akerke.salonservice.controller;

import com.akerke.salonservice.domain.dto.WorkTimeDTO;
import com.akerke.salonservice.domain.service.WorkTimeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static com.akerke.salonservice.common.validatе.Validator.validate;

@RestController
@RequiredArgsConstructor
@RequestMapping("worktime")
public class WorkTimeController {

    private final WorkTimeService workTimeService;

    @PostMapping()
    ResponseEntity<?> save (
            @Valid
            @RequestBody WorkTimeDTO workTimeDTO,
            BindingResult bindingResult
    ){
        validate(bindingResult);
        return  ResponseEntity.status(HttpStatus.CREATED)
                .body(workTimeService.save(workTimeDTO));
    }


    @GetMapping("{id}")
    ResponseEntity<?> getById (
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(workTimeService.getById(id));
    }

    @GetMapping()
    ResponseEntity<?> getAll (){
        return  ResponseEntity.ok(workTimeService.getAll());
    }

    @RequestMapping(method = {RequestMethod.PATCH, RequestMethod.PUT}, value = "{id}")
    ResponseEntity<?> update (
            @RequestBody WorkTimeDTO workTimeDTO,
            @PathVariable Long id
    ) {
        workTimeService.update(workTimeDTO, id);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("{id}")
    ResponseEntity<?> delete (
            @PathVariable Long id
    ){
        workTimeService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
