package com.example.demo.events;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;

@Getter
public class AuditEvent<T> extends ApplicationEvent {

	private static final long serialVersionUID = 4557855442641105638L;

	private T data;

	public AuditEvent(T source) {
		super(source);

		data = source;
	}

}
