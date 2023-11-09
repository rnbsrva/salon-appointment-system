package com.akerke.tgbot.feign;

import com.akerke.tgbot.tg.entity.Notification;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.Argument;

import java.util.List;

@FeignClient(name = "notification-service")
public interface NotificationServiceFeignClient {

    @QueryMapping
    @ApiOperation("Retrieve notifications for a user by their ID")
    List<Notification> getNotificationsByUser (
            @Argument Long id
    );



}
