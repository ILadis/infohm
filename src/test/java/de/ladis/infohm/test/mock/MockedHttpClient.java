package de.ladis.infohm.test.mock;

import static org.mockito.Mockito.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
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

	public static class MockedHttpResponse {
		private Integer statusCode;
		private Header contentType;
		private InputStream body;

		public MockedHttpResponse statusCode(Integer statusCode) {
			this.statusCode = statusCode;

			return this;
		}

		public MockedHttpResponse contentType(String contentType) {
			this.contentType = new BasicHeader("Content-Type", contentType);

			return this;
		}

		public MockedHttpResponse andBody(InputStream body) {
			this.body = body;

			return this;
		}
	}

	public static class MockedHttpRequest {
		private HttpRequest source;

		public String readBody() {
			try {
				InputStream body = ((HttpEntityEnclosingRequest) source).getEntity().getContent();

				BufferedReader reader = new BufferedReader(new InputStreamReader(body));
				StringBuilder builder = new StringBuilder();

				String line;
				while ((line = reader.readLine()) != null) {
					builder.append(line);
				}

				return builder.toString();
			} catch (Exception e) {
				return null;
			}
		}
	}

	private MockedHttpResponse response = new MockedHttpResponse();
	private MockedHttpRequest request = new MockedHttpRequest();

	public MockedHttpResponse willRespondWith() {
		return response;
	}

	public MockedHttpRequest fromLastRequest() {
		return request;
	}

	@Override
	public <T> T execute(HttpHost target, HttpRequest request,
			ResponseHandler<? extends T> responseHandler,
			HttpContext context) throws IOException, ClientProtocolException {
		return responseHandler.handleResponse(execute(request));
	}

	@Override
	public HttpResponse execute(HttpHost target, HttpRequest request,
			HttpContext context) throws IOException, ClientProtocolException {
		return execute(request);
	}

	@Override
	public HttpResponse execute(HttpUriRequest request) throws IOException, ClientProtocolException {
		return null;
	}

	@Override
	public HttpResponse execute(HttpUriRequest request, HttpContext context)
			throws IOException, ClientProtocolException {
		return execute(request);
	}

	@Override
	public HttpResponse execute(HttpHost target, HttpRequest request)
			throws IOException, ClientProtocolException {
		return execute(request);
	}

	@Override
	public <T> T execute(HttpUriRequest request, ResponseHandler<? extends T> responseHandler)
			throws IOException, ClientProtocolException {
		return responseHandler.handleResponse(execute(request));
	}

	@Override
	public <T> T execute(HttpUriRequest request, ResponseHandler<? extends T> responseHandler,
			HttpContext context)
			throws IOException, ClientProtocolException {
		return responseHandler.handleResponse(execute(request));
	}

	@Override
	public <T> T execute(HttpHost target, HttpRequest request,
			ResponseHandler<? extends T> responseHandler) throws IOException, ClientProtocolException {
		return responseHandler.handleResponse(execute(request));
	}

	private HttpResponse execute(HttpRequest request) throws IOException, ClientProtocolException {
		this.request.source = request;

		HttpResponse response = mock(HttpResponse.class);
		HttpEntity entity = mock(HttpEntity.class);
		StatusLine status = mock(StatusLine.class);

		when(response.getEntity()).thenReturn(entity);
		when(response.getStatusLine()).thenReturn(status);

		when(entity.getContent()).thenReturn(this.response.body);
		when(entity.getContentType()).thenReturn(this.response.contentType);

		when(status.getStatusCode()).thenReturn(this.response.statusCode);

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
