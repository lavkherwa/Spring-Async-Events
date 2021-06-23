package com.example.demo.controller;

import java.util.UUID;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.events.publisher.AuditEventPublisher;

@RestController
@RequestMapping("/")
public class Test {

	public static ThreadLocal<String> transactionId = new ThreadLocal<>();
	
	@PostMapping("/test")
	public String test(@RequestBody String message) throws InterruptedException {

		transactionId.set(UUID.randomUUID().toString());
		
		System.out.println("Main thread: " + transactionId.get());
		
		
		int counter = 0;
		do {
			AuditEventPublisher.publishEvent(message);
			counter++;

		} while (counter != 10);
		
		
		Thread.sleep(10000);
		
		return "test processed successfully " + transactionId.get();

	}

}
