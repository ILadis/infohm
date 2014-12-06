package de.ladis.infohm.util;

public class SqliteUtil {

	public static String makePlaceholders(int len) {
		if (len < 1) {
			throw new RuntimeException("No placeholders");
		} else {
			StringBuilder builder = new StringBuilder(len * 3 - 2);
			builder.append("?");
			for (int i = 1; i < len; i++) {
				builder.append(", ?");
			}
			return builder.toString();
		}
	}

}
