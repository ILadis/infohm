package de.ladis.infohm.core.dao.sqlite;

import de.ladis.infohm.core.dao.Dao;
import android.database.sqlite.SQLiteDatabase;

public abstract class SqliteDao<K, E> implements Dao<K, E> {

	private final SQLiteDatabase database;

	public SqliteDao(SQLiteDatabase database) {
		this.database = database;
	}

	protected SQLiteDatabase sql() {
		return database;
	}

}
