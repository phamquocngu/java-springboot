package io.marklove.springboot.firebase.serivce;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.*;
import io.marklove.springboot.firebase.dto.NotificationRequestDto;
import io.marklove.springboot.firebase.dto.SubscriptionRequestDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * @author ngupq
 */
@Slf4j
@Service
public class NotificationService {

    @Value("${app.firebase-config}")
    private String firebaseConfig;

    private FirebaseApp firebaseApp;

    @PostConstruct
    private void initialize() {
        try {
            GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(firebaseConfig).getInputStream());

            FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(googleCredentials)
                .build();

            if (FirebaseApp.getApps().isEmpty()) {
                this.firebaseApp = FirebaseApp.initializeApp(options, "my-app");
            } else {
                this.firebaseApp = FirebaseApp.getInstance();
            }
        } catch (IOException e) {
            log.error("Create FirebaseApp Error", e);
        }
    }

    public void subscribeToTopic(SubscriptionRequestDto subscriptionRequestDto) {
        try {
            FirebaseMessaging.getInstance(firebaseApp).subscribeToTopic(subscriptionRequestDto.getTokens(),
                subscriptionRequestDto.getTopicName());
        } catch (FirebaseMessagingException e) {
            log.error("Firebase subscribe to topic fail", e);
        }
    }

    public void unsubscribeFromTopic(SubscriptionRequestDto subscriptionRequestDto) {
        try {
            FirebaseMessaging.getInstance(firebaseApp).unsubscribeFromTopic(subscriptionRequestDto.getTokens(),
                subscriptionRequestDto.getTopicName());
        } catch (FirebaseMessagingException e) {
            log.error("Firebase unsubscribe from topic fail", e);
        }
    }

    public String sendPnsToDevice(NotificationRequestDto notificationRequestDto) {
        Message message = Message.builder()
            .setToken(notificationRequestDto.getTarget())
            .setNotification(Notification.builder()
                .setTitle(notificationRequestDto.getTitle())
                .setBody(notificationRequestDto.getBody())
                .build())
            .putData("content", notificationRequestDto.getTitle())
            .putData("body", notificationRequestDto.getBody())
            .build();

        String response = null;
        try {
            response = FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
            log.error("Fail to send firebase notification", e);
        }

        return response;
    }

    public String sendPnsToTopic(NotificationRequestDto notificationRequestDto) {
        Message message = Message.builder()
            .setTopic(notificationRequestDto.getTarget())
            .setNotification(Notification.builder()
                .setTitle(notificationRequestDto.getTitle())
                .setBody(notificationRequestDto.getBody())
                .build())
            .putData("content", notificationRequestDto.getTitle())
            .putData("body", notificationRequestDto.getBody())
            .build();

        String response = null;
        try {
            response = FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
            log.error("Fail to send firebase notification", e);
        }

        return response;
    }
}
