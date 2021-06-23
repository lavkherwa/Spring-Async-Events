package com.example.demo.events.publisher;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.example.demo.controller.Test;
import com.example.demo.events.AuditEvent;
import com.example.demo.events.data.AuditLog;

@Component
public class AuditEventPublisher {

	private static ApplicationEventPublisher publisher;

	public AuditEventPublisher(ApplicationEventPublisher publisher) {
		AuditEventPublisher.publisher = publisher;

	}

	public static void publishEvent(String message) {

		//// @formatter:off
		publisher.publishEvent(
				new AuditEvent<AuditLog>(
						AuditLog
							.builder()
							.message(message)
							.transactionId(Test.transactionId.get()) // pass on the thread context for async
							.build()
							));
		// @formatter:on
	}

}
