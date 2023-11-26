package com.akerke.salonservice.controller;

import com.akerke.salonservice.domain.dto.FeedbackDTO;
import com.akerke.salonservice.domain.service.FeedbackService;
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
@RequestMapping("feedback")
@RequiredArgsConstructor
@Api(value = "Feedback API")
public class FeedbackController {

    private final FeedbackService feedbackService;

    @PostMapping()
    @ApiOperation("Create a new feedback")
    ResponseEntity<?> save (
            @Valid
            @RequestBody FeedbackDTO feedbackDTO,
            BindingResult bindingResult
    ){
        validate(bindingResult);
        return  ResponseEntity.status(HttpStatus.CREATED)
                .body(feedbackService.save(feedbackDTO));
    }


    @GetMapping("{id}")
    @ApiOperation("Get an feedback by ID")
    ResponseEntity<?> getById (
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(feedbackService.getById(id));
    }

    @GetMapping()
    @ApiOperation("Get a list of all feedbacks")
    ResponseEntity<?> getAll (){
        return  ResponseEntity.ok(feedbackService.getAll());
    }

    @ApiOperation("Update an feedback by ID")
    @RequestMapping(method = {RequestMethod.PATCH, RequestMethod.PUT}, value = "{id}")
    ResponseEntity<?> update (
            @RequestBody FeedbackDTO feedbackDTO,
            @PathVariable Long id
    ) {
        feedbackService.update(feedbackDTO, id);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("{id}")
    @ApiOperation("Delete an feedback by ID")
    ResponseEntity<?> delete (
            @PathVariable Long id
    ){
        feedbackService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
