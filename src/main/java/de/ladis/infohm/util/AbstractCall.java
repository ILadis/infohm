package de.ladis.infohm.util;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public abstract class AbstractCall<T> implements Call<T> {

	private final ExecutorService executor;

	public AbstractCall(ExecutorService executor) {
		this.executor = executor;
	}

	@Override
	public final Future<T> doAsync() {
		Callable<T> task = new Callable<T>() {

			@Override
			public T call() {
				return doSync();
			}

		};

		return executor.submit(task);
	}

}