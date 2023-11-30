package com.akerke.salonservice.controller;

import com.akerke.salonservice.domain.dto.MasterDTO;
import com.akerke.salonservice.domain.service.MasterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.akerke.salonservice.common.validate.Validator.validate;

@RestController
@RequestMapping("master")
@RequiredArgsConstructor
@Api(value = "Master API")
public class MasterController {

    private final MasterService masterService;

    @PostMapping()
    @ApiOperation("Save a new master")
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
    @ApiOperation("Get an master by ID")
    ResponseEntity<?> getById (
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(masterService.getById(id));
    }

    @GetMapping()
    @ApiOperation("Get a list of all masters")
    ResponseEntity<?> getAll (){
        return  ResponseEntity.ok(masterService.getAll());
    }

    @ApiOperation("Update an master by ID")
    @RequestMapping(method = {RequestMethod.PATCH, RequestMethod.PUT}, value = "{id}")
    ResponseEntity<?> update (
            @RequestBody MasterDTO masterDTO,
            @PathVariable Long id
    ) {
        masterService.update(masterDTO, id);
        return ResponseEntity.accepted().build();
    }

    @PutMapping("add-image/{id}")
    ResponseEntity<?> addImage(
            @RequestParam String imageId,
            @PathVariable Long id
    ) {
        masterService.addImage(id, imageId);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("{id}")
    @ApiOperation("Delete an master by ID")
    ResponseEntity<?> delete (
            @PathVariable Long id
    ){
        masterService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation("Add a new treatment to the master")
    @PatchMapping("{id}/add")
    ResponseEntity<?> addTreatment(
            @RequestBody List<Long> treatmentIds,
            @PathVariable Long id
            ){
        masterService.addTreatment(id,  treatmentIds);
        return ResponseEntity.accepted().build();

    }

}
