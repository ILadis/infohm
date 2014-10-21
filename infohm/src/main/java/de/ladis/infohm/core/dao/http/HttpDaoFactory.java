package de.ladis.infohm.core.dao.http;

import org.apache.http.client.HttpClient;

import de.ladis.infohm.core.dao.Dao;
import de.ladis.infohm.core.dao.DaoFactory;

public class HttpDaoFactory extends DaoFactory {

	private final HttpClient client;

	public HttpDaoFactory(HttpClient client) {
		this.client = client;
	}

	@Override
	public <T extends Dao<?, ?>> T create(Class<T> dao, Object... arguments) {
		return getInstanceOf(dao, argumentsWithClient(arguments));
	}

	private Object[] argumentsWithClient(Object... arguments) {
		Object[] withHandler = new Object[1 + arguments.length];

		withHandler[0] = client;
		System.arraycopy(arguments, 0, withHandler, 1, arguments.length);

		return withHandler;
	}

}
