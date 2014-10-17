package de.ladis.infohm.core.dao.http;

import de.ladis.infohm.core.dao.Dao;
import de.ladis.infohm.core.dao.DaoFactory;

public class HttpDaoFactory extends DaoFactory {

	private final HttpHandler handler;

	public HttpDaoFactory() {
		this.handler = new HttpHandler();
	}

	@Override
	public <T extends Dao<?, ?>> T create(Class<T> dao, Object... arguments) {
		return getInstanceOf(dao, argumentsWithHandler(arguments));
	}

	private Object[] argumentsWithHandler(Object... arguments) {
		Object[] withHandler = new Object[1 + arguments.length];

		withHandler[0] = handler;
		System.arraycopy(arguments, 0, withHandler, 1, arguments.length);

		return withHandler;
	}

}
