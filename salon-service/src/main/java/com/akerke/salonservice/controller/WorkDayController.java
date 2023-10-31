package com.akerke.salonservice.controller;

import com.akerke.salonservice.domain.dto.WorkDayDTO;
import com.akerke.salonservice.domain.service.WorkDayService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static com.akerke.salonservice.common.validate.Validator.validate;

@RestController
@RequiredArgsConstructor
@RequestMapping("workday")
public class WorkDayController {

    private final WorkDayService workDayService;

    @PostMapping()
    ResponseEntity<?> save (
            @Valid
            @RequestBody WorkDayDTO workDayDTO,
            BindingResult bindingResult
    ){
        validate(bindingResult);
        return  ResponseEntity.status(HttpStatus.CREATED)
                .body(workDayService.save(workDayDTO));
    }


    @GetMapping("{id}")
    ResponseEntity<?> getById (
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(workDayService.getById(id));
    }

    @GetMapping()
    ResponseEntity<?> getAll (){
        return  ResponseEntity.ok(workDayService.getAll());
    }

    @RequestMapping(method = {RequestMethod.PATCH, RequestMethod.PUT}, value = "{id}")
    ResponseEntity<?> update (
            @RequestBody WorkDayDTO workDayDTO,
            @PathVariable Long id
    ) {
        workDayService.update(workDayDTO, id);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("{id}")
    ResponseEntity<?> delete (
            @PathVariable Long id
    ){
        workDayService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
