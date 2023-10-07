package com.akerke.salonservice.controller;

import com.akerke.salonservice.dto.FeedbackDTO;
import com.akerke.salonservice.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    @PostMapping()
    ResponseEntity<?> save (
            @RequestBody FeedbackDTO feedbackDTO
    ){
        return  ResponseEntity.status(HttpStatus.CREATED)
                .body(feedbackService.save(feedbackDTO));
    }


    @GetMapping("{id}")
    ResponseEntity<?> getById (
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(feedbackService.getById(id));
    }

    @GetMapping()
    ResponseEntity<?> getAll (){
        return  ResponseEntity.ok(feedbackService.getAll());
    }

    @RequestMapping(method = {RequestMethod.PATCH, RequestMethod.PUT}, value = "{id}")
    ResponseEntity<?> update (
            @RequestBody FeedbackDTO feedbackDTO,
            @PathVariable Long id
    ) {
        feedbackService.update(feedbackDTO, id);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("{id}")
    ResponseEntity<?> delete (
            @PathVariable Long id
    ){
        feedbackService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
