package com.example.demo.events.subscriber;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.example.demo.controller.Test;
import com.example.demo.events.AuditEvent;
import com.example.demo.events.data.AuditLog;

@Component
public class AuditEventSubscriber {

	@Async
	@EventListener
	public void handleAuditEvent(AuditEvent<AuditLog> auditLog) throws Exception {

		// test this variable is initialize for spawned thread, not the main parent thread
		Test.transactionId.set("test"); //auditLog.getData().getTransactionId()
		
		Thread.sleep(5000);
		System.out.println("Spawned thread: " + Test.transactionId.get());
		/*
		System.out.println(auditLog.getData().getMessage() + " - message has been recieved");
		System.out.println(auditLog.getData().getDwcHeaders().get("param1") + " - DWC param1 has been recieved");
		System.out.println(auditLog.getData().getMdcContext().get("param1") + " - MDC param1 has been recieved");
		*/
	}

}
