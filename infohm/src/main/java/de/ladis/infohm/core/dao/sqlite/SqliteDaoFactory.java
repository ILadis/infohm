package de.ladis.infohm.core.dao.sqlite;

import android.database.sqlite.SQLiteOpenHelper;
import de.ladis.infohm.core.dao.Dao;
import de.ladis.infohm.core.dao.DaoFactory;

public class SqliteDaoFactory extends DaoFactory {

	private final SQLiteOpenHelper helper;

	public SqliteDaoFactory(SQLiteOpenHelper handler) {
		this.helper = handler;
	}

	@Override
	public <T extends Dao<?, ?>> T create(Class<T> dao, Object... arguments) {
		return getInstanceOf(dao, argumentsWithDatabase(arguments));
	}

	private Object[] argumentsWithDatabase(Object... arguments) {
		Object[] withHandler = new Object[1 + arguments.length];

		withHandler[0] = helper.getWritableDatabase();
		System.arraycopy(arguments, 0, withHandler, 1, arguments.length);

		return withHandler;
	}

}
