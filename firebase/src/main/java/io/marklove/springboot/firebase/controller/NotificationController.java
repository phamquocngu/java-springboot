package io.marklove.springboot.firebase.controller;

import io.marklove.springboot.firebase.dto.NotificationRequestDto;
import io.marklove.springboot.firebase.dto.SubscriptionRequestDto;
import io.marklove.springboot.firebase.serivce.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * @author ngupq
 */
@RestController
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/subscribe")
    public void subscribeToTopic(@RequestBody SubscriptionRequestDto subscriptionRequestDto) {
        notificationService.subscribeToTopic(subscriptionRequestDto);
    }

    @PostMapping("/unsubscribe")
    public void unsubscribeFromTopic(SubscriptionRequestDto subscriptionRequestDto) {
        notificationService.unsubscribeFromTopic(subscriptionRequestDto);
    }

    @PostMapping("/token")
    public String sendPnsToDevice(@RequestBody NotificationRequestDto notificationRequestDto) {
        return notificationService.sendPnsToDevice(notificationRequestDto);
    }

    @PostMapping("/topic")
    public String sendPnsToTopic(@RequestBody NotificationRequestDto notificationRequestDto) {
        return notificationService.sendPnsToTopic(notificationRequestDto);
    }
}
