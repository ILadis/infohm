package de.ladis.infohm.util;

import java.util.concurrent.Future;

public interface Call<T> {

	public T doSync();

	public Future<T> doAsync();

}
