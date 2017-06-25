package de.ladis.infohm.util;

import java.util.Collection;
import java.util.Iterator;

public class Arrays {

	public static <T> T[] from(T... values) {
		return values;
	}

	public static <T> String glue(T... values) {
		StringBuilder builder = new StringBuilder();

		for (T value : values) {
			builder.append(value);
		}

		return builder.toString();
	}

	public static <T> String glue(Collection<T> values, String separator) {
		StringBuilder builder = new StringBuilder();

		Iterator<T> iterator = values.iterator();

		while (iterator.hasNext()) {
			builder.append(iterator.next().toString());
			if (iterator.hasNext()) {
				builder.append(separator);
			}
		}

		return builder.toString();
	}

}
