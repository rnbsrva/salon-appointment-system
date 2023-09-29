package com.akerke.salonservice.controller;

import com.akerke.salonservice.service.SalonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("salon")
@RequiredArgsConstructor
public class SalonController {

    private final SalonService salonService;



}
