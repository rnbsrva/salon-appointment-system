package com.akerke.salonservice.controller;

import com.akerke.salonservice.dto.AddressDTO;
import com.akerke.salonservice.entity.Address;
import com.akerke.salonservice.service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static com.akerke.salonservice.validatе.Validator.validate;

@RestController
@RequiredArgsConstructor
@RequestMapping("address")
public class AddressController {

    private final AddressService addressService;

    @PostMapping()
    ResponseEntity<?> save (
            @Valid
            @RequestBody AddressDTO addressDTO,
            BindingResult bindingResult
    ){
        validate(bindingResult);
        return  ResponseEntity.status(HttpStatus.CREATED)
                .body(addressService.save(addressDTO));
    }


    @GetMapping("{id}")
    ResponseEntity<?> getById (
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(addressService.getById(id));
    }

    @GetMapping()
    ResponseEntity<?> getAll (){
        return  ResponseEntity.ok(addressService.getAll());
    }

    @RequestMapping(method = {RequestMethod.PATCH, RequestMethod.PUT}, value = "{id}")
    ResponseEntity<?> update (
            @RequestBody AddressDTO addressDTO,
            @PathVariable Long id
    ) {
        addressService.update(addressDTO, id);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("{id}")
    ResponseEntity<?> delete (
            @PathVariable Long id
    ){
        addressService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
