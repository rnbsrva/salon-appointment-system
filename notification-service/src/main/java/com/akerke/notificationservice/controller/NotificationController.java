package com.akerke.notificationservice.controller;

import com.akerke.notificationservice.entity.Notification;
import com.akerke.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;


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

    @QueryMapping
    public List<Notification> getNotificationsByUser (
            @Argument Long id
    ) {
        return notificationService.findAllByUser(id);
    }

    @MutationMapping
    public void deleteNotificationById(
            @Argument Long id
    ){
        notificationService.deleteById(id);
    }

    @MutationMapping
    public void deleteNotificationsByUser(
            @Argument Long id
    ){
        notificationService.deleteByUser(id);
    }

}
