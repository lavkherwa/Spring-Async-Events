package com.example.demo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.events.publisher.AuditEventPublisher;

@RestController
@RequestMapping("/")
public class Test {

	@PostMapping("/test")
	public String test(@RequestBody String message) {

		int counter = 0;
		do {
			AuditEventPublisher.publishEvent(message);
			counter++;

		} while (counter != 100);

		
		return "test processed successfully";
		
	}

}
