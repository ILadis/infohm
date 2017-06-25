package de.ladis.infohm.util;

import org.joda.time.DateTime;

import android.database.Cursor;

public class SqliteUtil {

	public static Boolean getBoolean(Cursor cursor, String column) {
		int index = cursor.getColumnIndex(column);

		if (index >= 0 && !cursor.isNull(index)) {
			return cursor.getInt(index) != 0;
		} else {
			return null;
		}
	}

	public static Long getLong(Cursor cursor, String column) {
		int index = cursor.getColumnIndex(column);

		if (index >= 0 && !cursor.isNull(index)) {
			return cursor.getLong(index);
		} else {
			return null;
		}
	}

	public static Integer getInteger(Cursor cursor, String column) {
		int index = cursor.getColumnIndex(column);

		if (index >= 0 && !cursor.isNull(index)) {
			return cursor.getInt(index);
		} else {
			return null;
		}
	}

	public static Double getDouble(Cursor cursor, String column) {
		int index = cursor.getColumnIndex(column);

		if (index >= 0 && !cursor.isNull(index)) {
			return cursor.getDouble(index);
		} else {
			return null;
		}
	}

	public static String getString(Cursor cursor, String column) {
		int index = cursor.getColumnIndex(column);

		if (index >= 0 && !cursor.isNull(index)) {
			return cursor.getString(index);
		} else {
			return null;
		}
	}

	public static DateTime getDateTime(Cursor cursor, String column) {
		int index = cursor.getColumnIndex(column);

		if (index >= 0 && !cursor.isNull(index)) {
			return DateTime.parse(cursor.getString(index));
		} else {
			return null;
		}
	}

	public static String makePlaceholders(int len) {
		if (len < 1) {
			throw new RuntimeException("can not make placeholders of length " + len);
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
