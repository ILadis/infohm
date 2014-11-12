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
				+ "id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "url TEXT NOT NULL, "
				+ "title TEXT NOT NULL, "
				+ "description TEXT NOT NULL, "
				+ "created TEXT NOT NULL, "
				+ "updated TEXT NOT NULL)");

		db.execSQL("CREATE TABLE publisher ("
				+ "id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "name TEXT NOT NULL, "
				+ "description TEXT NOT NULL, "
				+ "starred INTEGER NOT NULL, "
				+ "created TEXT NOT NULL, "
				+ "updated TEXT NOT NULL)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

}
