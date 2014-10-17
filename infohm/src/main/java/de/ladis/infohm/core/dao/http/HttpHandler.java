package de.ladis.infohm.core.dao.http;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.Build;

public class HttpHandler {

	static {
		CookieManager cookieManager = new CookieManager();
		CookieHandler.setDefault(cookieManager);

		// disables http connection reuse which was buggy pre-froyo
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.FROYO) {
			System.setProperty("http.keepAlive", "false");
		}
	}

	private static enum HttpMethod {

		OPTIONS("OPTIONS"), GET("GET"), HEAD("HEAD"), PUT("PUT"), POST("POST"), DELETE("DELETE"),
		TRACE("TRACE"), CONNECT("CONNECT");

		private final String value;

		private HttpMethod(String value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return value;
		}

	}

	private URL parseUri(String uri) {
		try {
			return new URL(uri);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	private HttpURLConnection openConnection(String uri, HttpMethod method) throws IOException {
		URL url = parseUri(uri);

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setReadTimeout(10000);
		connection.setConnectTimeout(15000);

		switch (method) {
		case GET:
			connection.setDoInput(true);
			connection.setDoOutput(false);
			break;
		case POST:
		case PUT:
			connection.setDoInput(true);
			connection.setDoOutput(true);
			break;
		default:
			throw new UnsupportedOperationException("http method " + method + " unsupported");
		}

		connection.setRequestMethod(method.toString());

		return connection;
	}

	private void writeBody(HttpURLConnection connection, String body) throws IOException {
		OutputStream stream = connection.getOutputStream();
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stream, "UTF-8"));

		writer.write(body);
		writer.flush();
		writer.close();

		stream.close();
	}

	public HttpURLConnection get(String uri) throws IOException {
		HttpURLConnection connection = openConnection(uri, HttpMethod.GET);

		connection.connect();

		return connection;
	}

	public HttpURLConnection post(String uri, String body) throws IOException {
		HttpURLConnection connection = openConnection(uri, HttpMethod.POST);
		writeBody(connection, body);

		connection.connect();

		return connection;
	}

	public HttpURLConnection put(String uri, String body) throws IOException {
		HttpURLConnection connection = openConnection(uri, HttpMethod.PUT);
		writeBody(connection, body);

		connection.connect();

		return connection;
	}

}
