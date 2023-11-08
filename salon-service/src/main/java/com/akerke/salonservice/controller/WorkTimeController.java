package com.akerke.salonservice.controller;

import com.akerke.loggerlib.common.annotation.EnableLoggerLib;
import com.akerke.salonservice.domain.dto.WorkTimeDTO;
import com.akerke.salonservice.domain.service.WorkTimeService;
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
@RequestMapping("worktime")
@Api("WorkTime API")
@EnableLoggerLib
public class WorkTimeController {

    private final WorkTimeService workTimeService;

    @ApiOperation("Create a new work time")
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


    @ApiOperation("Get an work time by ID")
    @GetMapping("{id}")
    ResponseEntity<?> getById (
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(workTimeService.getById(id));
    }

    @ApiOperation("Get a list of all work times")
    @GetMapping()
    ResponseEntity<?> getAll (){
        return  ResponseEntity.ok(workTimeService.getAll());
    }

    @ApiOperation("Update an work time by ID")
    @RequestMapping(method = {RequestMethod.PATCH, RequestMethod.PUT}, value = "{id}")
    ResponseEntity<?> update (
            @RequestBody WorkTimeDTO workTimeDTO,
            @PathVariable Long id
    ) {
        workTimeService.update(workTimeDTO, id);
        return ResponseEntity.accepted().build();
    }

    @ApiOperation("Delete an work time by ID")
    @DeleteMapping("{id}")
    ResponseEntity<?> delete (
            @PathVariable Long id
    ){
        workTimeService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
