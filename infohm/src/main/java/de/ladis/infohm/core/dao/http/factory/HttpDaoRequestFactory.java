package de.ladis.infohm.core.dao.http.factory;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpRequest;
import org.apache.http.MethodNotSupportedException;
import org.apache.http.RequestLine;
import org.apache.http.impl.DefaultHttpRequestFactory;

public class HttpDaoRequestFactory extends DefaultHttpRequestFactory {

	@Override
	public HttpRequest newHttpRequest(RequestLine requestline) throws MethodNotSupportedException {
		return newHttpRequest(super.newHttpRequest(requestline));
	}

	@Override
	public HttpRequest newHttpRequest(String method, String uri) throws MethodNotSupportedException {
		return newHttpRequest(super.newHttpRequest(method, uri));
	}

	private HttpRequest newHttpRequest(HttpRequest request) {
		addAuthenticationHeader(request);
		addAcceptHeader(request);

		return request;
	}

	private void addAuthenticationHeader(HttpRequest request) {
		request.addHeader("Authorization", "Basic " + new String(Base64.encodeBase64(("rest:resttest").getBytes())));
	}

	private void addAcceptHeader(HttpRequest request) {
		request.addHeader("Accept", "text/json;q=1.0,application/xml;q=0.9");
	}

}
