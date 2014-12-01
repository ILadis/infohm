package de.ladis.infohm.util;

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

}
