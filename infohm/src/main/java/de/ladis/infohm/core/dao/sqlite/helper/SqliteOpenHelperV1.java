package de.ladis.infohm.core.dao.sqlite.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqliteOpenHelperV1 extends SQLiteOpenHelper {

	public SqliteOpenHelperV1(Context context) {
		super(context, "infohm", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE bookmark ("
				+ "id INTEGER NOT NULL, "
				+ "url TEXT NOT NULL, "
				+ "title TEXT NOT NULL, "
				+ "description TEXT NOT NULL, "
				+ "created TEXT NOT NULL, "
				+ "updated TEXT NOT NULL, "
				+ "PRIMARY KEY (id))");

		db.execSQL("CREATE TABLE publisher ("
				+ "id INTEGER NOT NULL, "
				+ "name TEXT NOT NULL, "
				+ "description TEXT NOT NULL, "
				+ "created TEXT NOT NULL, "
				+ "updated TEXT NOT NULL, "
				+ "PRIMARY KEY (id))");

		db.execSQL("CREATE TABLE starred ("
				+ "id INTEGER NOT NULL, "
				+ "pid INTEGER NOT NULL, "
				+ "PRIMARY KEY (id, pid), "
				+ "FOREIGN KEY (pid) REFERENCES publisher(id))");

		db.execSQL("CREATE TABLE event ("
				+ "id INTEGER NOT NULL, "
				+ "pid INTEGER NOT NULL, "
				+ "headline TEXT NOT NULL, "
				+ "content TEXT NOT NULL, "
				+ "created TEXT NOT NULL, "
				+ "updated TEXT NOT NULL, "
				+ "PRIMARY KEY (id),"
				+ "FOREIGN KEY (pid) REFERENCES publisher(id))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

}
