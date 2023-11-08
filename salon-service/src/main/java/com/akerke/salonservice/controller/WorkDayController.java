package com.akerke.salonservice.controller;

import com.akerke.loggerlib.common.annotation.EnableLoggerLib;
import com.akerke.salonservice.domain.dto.WorkDayDTO;
import com.akerke.salonservice.domain.service.WorkDayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@EnableLoggerLib
@Api("Work Day API")
public class WorkDayController {

    private final WorkDayService workDayService;

    @PostMapping()
    @ApiOperation("Create a new work day")
    ResponseEntity<?> save (
            @Valid
            @RequestBody WorkDayDTO workDayDTO,
            BindingResult bindingResult
    ){
        validate(bindingResult);
        return  ResponseEntity.status(HttpStatus.CREATED)
                .body(workDayService.save(workDayDTO));
    }


    @ApiOperation("Get an work day by ID")
    @GetMapping("{id}")
    ResponseEntity<?> getById (
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(workDayService.getById(id));
    }

    @ApiOperation("Get a list of all work days")
    @GetMapping()
    ResponseEntity<?> getAll (){
        return  ResponseEntity.ok(workDayService.getAll());
    }

    @ApiOperation("Update an work day by ID")
    @RequestMapping(method = {RequestMethod.PATCH, RequestMethod.PUT}, value = "{id}")
    ResponseEntity<?> update (
            @RequestBody WorkDayDTO workDayDTO,
            @PathVariable Long id
    ) {
        workDayService.update(workDayDTO, id);
        return ResponseEntity.accepted().build();
    }

    @ApiOperation("Delete an work day by ID")
    @DeleteMapping("{id}")
    ResponseEntity<?> delete (
            @PathVariable Long id
    ){
        workDayService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
