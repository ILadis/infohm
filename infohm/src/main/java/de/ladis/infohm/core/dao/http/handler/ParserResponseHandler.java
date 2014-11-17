package de.ladis.infohm.core.dao.http.handler;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;

import de.ladis.infohm.core.parser.Parser;

public class ParserResponseHandler<T> implements ResponseHandler<T> {

	private final Parser<InputStream, T> parser;

	public ParserResponseHandler(Parser<InputStream, T> parser) {
		this.parser = parser;
	}

	@Override
	public final T handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
		int statusCode = response.getStatusLine().getStatusCode();

		if (statusCode == 200) {
			HttpEntity entity = response.getEntity();
			InputStream stream = entity.getContent();

			return parser.parse(stream);
		} else {
			return null;
		}
	}

}
