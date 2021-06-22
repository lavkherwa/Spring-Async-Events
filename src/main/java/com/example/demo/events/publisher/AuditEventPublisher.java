package com.example.demo.events.publisher;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.example.demo.events.AuditEvent;

@Component
public class AuditEventPublisher {

	private static ApplicationEventPublisher publisher;

	public AuditEventPublisher(ApplicationEventPublisher publisher) {
		AuditEventPublisher.publisher = publisher;

	}

	public static void publishEvent(String message) {
		publisher.publishEvent(new AuditEvent<String>(message));
	}

}
