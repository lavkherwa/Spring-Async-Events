package com.example.demo.future;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

public class ExecutorServiceExample {

	public static void execute(List<String> tasks) {

		List<CompletableFuture<String>> executedTasksList = new ArrayList<>();
		ExecutorService executor = Executors.newFixedThreadPool(5);

		// @formatter:off
		tasks
			.stream()
			.forEach( task -> {
				executedTasksList.add(CompletableFuture
						.supplyAsync(
								processData(task), executor)
						.handle((resp, err) -> {
								if(err != null) {
									System.out.println("Error occurred, details: " + err);
								}
								return resp;
						})
					);
			});
		
		
		
		// Result from all the futures
		executedTasksList.stream().forEach(task -> {
			try {
				System.out.println(task.get());
			} catch (InterruptedException | ExecutionException e) {
				System.out.println("something went wrong!");
			}
		});
		
		// @formatter:on

	}

	private static Supplier<String> processData(String task) {

		return new Supplier<String>() {
			@Override
			public String get() {
				System.out.println("Thread name: " + Thread.currentThread().getName() + ", executing task: " + task);

				if (task.equals("agent10")) {
					throw new RuntimeException("Task 10 aborted");
				}

				return "Task " + task + ", executed successfully";
			}
		};

	}

}
