package de.ladis.infohm.core.dao.http;

import de.ladis.infohm.core.dao.Dao;

public abstract class HttpDao<K, E> implements Dao<K, E> {

	private final HttpHandler handler;

	public HttpDao() {
		this.handler = new HttpHandler();
	}

	protected HttpHandler http() {
		return handler;
	}

}
