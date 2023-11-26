package com.akerke.salonservice.controller;

import com.akerke.salonservice.domain.dto.AddressDTO;
import com.akerke.salonservice.domain.service.AddressService;
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
@RequestMapping("address")
@Api(value = "Address API")
public class AddressController {

    private final AddressService addressService;

    @PostMapping()
    @ApiOperation("Create a new address")
    ResponseEntity<?> save(
            @Valid
            @RequestBody AddressDTO addressDTO,
            BindingResult bindingResult
    ) {
        validate(bindingResult);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(addressService.save(addressDTO));
    }


    @GetMapping("{id}")
    @ApiOperation("Get an address by ID")
    ResponseEntity<?> getById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(addressService.getById(id));
    }

    @GetMapping()
    @ApiOperation("Get a list of all addresses")
    ResponseEntity<?> getAll() {
        return ResponseEntity.ok(addressService.getAll());
    }

    @RequestMapping(method = {RequestMethod.PATCH, RequestMethod.PUT}, value = "{id}")
    @ApiOperation("Update an address by ID")
    ResponseEntity<?> update(
            @RequestBody AddressDTO addressDTO,
            @PathVariable Long id
    ) {
        addressService.update(addressDTO, id);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("{id}")
    @ApiOperation("Delete an address by ID")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(
            @PathVariable Long id
    ) {
        addressService.delete(id);
    }

}
