package com.akerke.salonservice.controller;

import com.akerke.salonservice.dto.WorkDayDTO;
import com.akerke.salonservice.dto.WorkTimeDTO;
import com.akerke.salonservice.entity.WorkTime;
import com.akerke.salonservice.service.WorkTimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("worktime")
public class WorkTimeController {

    private final WorkTimeService workTimeService;

    @PostMapping()
    ResponseEntity<?> save (
            @RequestBody WorkTimeDTO workTimeDTO
    ){
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
