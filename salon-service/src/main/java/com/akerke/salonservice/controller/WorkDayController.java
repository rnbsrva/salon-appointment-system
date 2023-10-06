package com.akerke.salonservice.controller;

import com.akerke.salonservice.dto.WorkDayDTO;
import com.akerke.salonservice.service.WorkDayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("workday")
public class WorkDayController {

    private final WorkDayService workDayService;

    @PostMapping()
    ResponseEntity<?> save (
            @RequestBody WorkDayDTO workDayDTO
    ){
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
