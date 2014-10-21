package de.ladis.infohm.core.dao.http;

import org.apache.http.client.HttpClient;

import de.ladis.infohm.core.dao.Dao;

public abstract class HttpDao<K, E> implements Dao<K, E> {

	private final HttpClient client;

	public HttpDao(HttpClient client) {
		this.client = client;
	}

	protected HttpClient http() {
		return client;
	}

}
