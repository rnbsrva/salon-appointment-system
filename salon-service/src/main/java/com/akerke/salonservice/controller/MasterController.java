package com.akerke.salonservice.controller;

import com.akerke.salonservice.dto.MasterDTO;
import com.akerke.salonservice.dto.SalonDTO;
import com.akerke.salonservice.service.MasterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("master")
@RequiredArgsConstructor
public class MasterController {

    private final MasterService masterService;

    @PostMapping()
    ResponseEntity<?> save (
            @RequestBody MasterDTO masterDTO
    ){
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

}
