package com.example.demo.events.data;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AuditLog {

	private String message;
	private String transactionId;

}
