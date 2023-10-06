package com.akerke.notificationservice.controller;

import com.akerke.notificationservice.entity.Notification;
import com.akerke.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;


@Controller
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @QueryMapping
    public Notification getNotificationById(
            @Argument Long id
    ) {
        return notificationService.findById(id);
    }

}
