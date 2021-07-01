package com.example.demo.future.wrapcontext;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

import org.springframework.stereotype.Component;

import com.example.demo.controller.Test;

@Component
public class CompletableFutureExample {

	private ThreadContextWrapper threadContextWrapper;

	public CompletableFutureExample(ThreadContextWrapper threadContextWrapper) {
		this.threadContextWrapper = threadContextWrapper;

	}

	public void processAsync() throws InterruptedException, ExecutionException {

		// @formatter:off
		CompletableFuture<String> future = 
										CompletableFuture
											.supplyAsync(
													threadContextWrapper
													   .withParentThreadContext(getTransactionId()));
		// @formatter:on

		System.out.println("transactionId is: " + future.get());

	}

	private static Supplier<String> getTransactionId() {

		return new Supplier<String>() {

			@Override
			public String get() {
				return Test.transactionId.get();
			}

		};

	}

}
