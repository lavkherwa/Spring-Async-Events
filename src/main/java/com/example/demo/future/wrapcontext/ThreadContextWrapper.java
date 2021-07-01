package com.example.demo.future.wrapcontext;

import java.util.function.Supplier;

import org.springframework.stereotype.Component;

import com.example.demo.controller.Test;

@Component
public class ThreadContextWrapper {

	public <T> Supplier<T> withParentThreadContext(final Supplier<T> fn) {
		final String transactionId = Test.transactionId.get();
		return () -> {
			Test.transactionId.set(transactionId);
			try {
				return fn.get();
			} finally {
				Test.transactionId.remove();
			}
		};
	}

}
