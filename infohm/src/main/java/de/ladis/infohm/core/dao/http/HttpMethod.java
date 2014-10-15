package de.ladis.infohm.core.dao.http;

public enum HttpMethod {

	GET("GET"), POST("POST"), PUT("PUT");

	private final String value;

	private HttpMethod(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}

}
