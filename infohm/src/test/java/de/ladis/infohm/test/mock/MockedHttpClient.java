package de.ladis.infohm.test.mock;

import static org.mockito.Mockito.*;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

public class MockedHttpClient implements HttpClient {

	private Integer statusCode;
	private Header contentType;
	private InputStream responseStream;

	public void setResponseStatusCode(int code) {
		this.statusCode = code;
	}

	public void setResponseContentType(String contentType) {
		this.contentType = new BasicHeader("Content-Type", contentType);
	}

	public void setResponseStream(InputStream stream) {
		this.responseStream = stream;
	}

	@Override
	public <T> T execute(HttpHost target, HttpRequest request,
			ResponseHandler<? extends T> responseHandler,
			HttpContext context) throws IOException, ClientProtocolException {
		return responseHandler.handleResponse(execute());
	}

	@Override
	public HttpResponse execute(HttpHost target, HttpRequest request,
			HttpContext context) throws IOException, ClientProtocolException {
		return execute();
	}

	@Override
	public HttpResponse execute(HttpUriRequest request) throws IOException, ClientProtocolException {
		return null;
	}

	@Override
	public HttpResponse execute(HttpUriRequest request, HttpContext context)
			throws IOException, ClientProtocolException {
		return execute();
	}

	@Override
	public HttpResponse execute(HttpHost target, HttpRequest request)
			throws IOException, ClientProtocolException {
		return execute();
	}

	@Override
	public <T> T execute(HttpUriRequest request, ResponseHandler<? extends T> responseHandler)
			throws IOException, ClientProtocolException {
		return responseHandler.handleResponse(execute());
	}

	@Override
	public <T> T execute(HttpUriRequest request, ResponseHandler<? extends T> responseHandler,
			HttpContext context)
			throws IOException, ClientProtocolException {
		return responseHandler.handleResponse(execute());
	}

	@Override
	public <T> T execute(HttpHost target, HttpRequest request,
			ResponseHandler<? extends T> responseHandler) throws IOException, ClientProtocolException {
		return responseHandler.handleResponse(execute());
	}

	private HttpResponse execute() throws IOException, ClientProtocolException {
		HttpResponse response = mock(HttpResponse.class);
		HttpEntity entity = mock(HttpEntity.class);
		StatusLine status = mock(StatusLine.class);

		when(response.getEntity()).thenReturn(entity);
		when(response.getStatusLine()).thenReturn(status);

		when(entity.getContent()).thenReturn(responseStream);
		when(entity.getContentType()).thenReturn(contentType);

		when(status.getStatusCode()).thenReturn(statusCode);

		return response;
	}

	@Override
	public ClientConnectionManager getConnectionManager() {
		return null;
	}

	@Override
	public HttpParams getParams() {
		return null;
	}

}
