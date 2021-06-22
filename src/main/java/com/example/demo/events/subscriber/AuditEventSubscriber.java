package com.example.demo.events.subscriber;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.example.demo.events.AuditEvent;

@Component
public class AuditEventSubscriber {

	@Async
	@EventListener
	public void handleAuditEvent(AuditEvent<String> message) throws Exception {

		Thread.sleep(5000);
		System.out.println(message.getData() + " - has been recieved");
	}

}
