package com.example.demo.future;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

public class ExecutorServiceExample {

	public static void executeSync(List<String> tasks) {

		List<String> executedTasksList = new ArrayList<>();

		// @formatter:off
		tasks
			.stream()
			.forEach(task -> {
					try {
						executedTasksList.add(processDataSync(task));
					} catch (Exception e) {
						System.out.println(e);
					}
				});

		executedTasksList
			.stream()
			.forEach(result -> System.out.println(result));
		// @formatter:on
	}

	public static void executeAsync(List<String> tasks) {

		List<CompletableFuture<String>> executedTasksList = new ArrayList<>();
		ExecutorService executor = Executors.newFixedThreadPool(4);

		try {

			// @formatter:off
			tasks
				.stream()
				.forEach( task -> {
					executedTasksList.add(CompletableFuture
							.supplyAsync(
									processDataAsync(task), executor)
							.handle((resp, err) -> {
									if(err != null) {
										System.out.println("Error occurred, details: " + err);
										}
									return resp + "; supplyAsyc thread: " + Thread.currentThread().getName(); 
									})
							.thenApplyAsync((data) -> {
								return data + "; thenApplyAsync thread: " + Thread.currentThread().getName();
							}, executor)
							
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

		} finally {
			executor.shutdown();
		}

	}

	private static String processDataSync(String task) {
		try {
			Thread.sleep(2000); // simulate task taking 2s I/O bound operation
		} catch (InterruptedException e) {
			System.out.println("thread sleep exception; details: " + e);
		}

		System.out.println("Thread name: " + Thread.currentThread().getName() + ", executing task: " + task);
		if (task.equals("agent10")) {
			throw new RuntimeException("Task 10 aborted");
		}

		return "Task " + task + ", executed successfully";
	}

	private static Supplier<String> processDataAsync(String task) {

		return new Supplier<String>() {
			@Override
			public String get() {

				try {
					Thread.sleep(2000); // simulate task taking 2s I/O bound operation
				} catch (InterruptedException e) {
					System.out.println("thread sleep exception; details: " + e);
				}

				System.out.println("Thread name: " + Thread.currentThread().getName() + ", executing task: " + task);
				if (task.equals("agent10")) {
					throw new RuntimeException("Task 10 aborted");
				}

				return "Task " + task + ", executed successfully";
			}
		};

	}

}
