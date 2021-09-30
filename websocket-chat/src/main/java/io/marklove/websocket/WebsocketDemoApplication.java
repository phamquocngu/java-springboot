package io.marklove.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@SpringBootApplication
public class WebsocketDemoApplication implements CommandLineRunner {

	@Autowired
	SimpMessagingTemplate template;

	public static void main(String[] args) {
		SpringApplication.run(WebsocketDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		template.convertAndSend("/topic","abc");
//		template.convertAndSend("/app","13212343");
	}
}
