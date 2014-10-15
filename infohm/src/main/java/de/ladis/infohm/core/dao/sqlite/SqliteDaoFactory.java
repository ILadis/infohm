package de.ladis.infohm.core.dao.sqlite;

import de.ladis.infohm.core.dao.Dao;
import de.ladis.infohm.core.dao.DaoFactory;

public class SqliteDaoFactory extends DaoFactory {

	private final SqliteHandler handler;

	public SqliteDaoFactory(SqliteHandler handler) {
		this.handler = handler;
	}

	@Override
	public <T extends Dao<?, ?>> T create(Class<T> dao, Object... arguments) {
		return getInstanceOf(dao, argumentsWithHandler(arguments));
	}

	private Object[] argumentsWithHandler(Object... arguments) {
		Object[] withHandler = new Object[1 + arguments.length];

		withHandler[0] = handler.getWritableDatabase();
		System.arraycopy(arguments, 0, withHandler, 1, arguments.length);

		return withHandler;
	}

}
