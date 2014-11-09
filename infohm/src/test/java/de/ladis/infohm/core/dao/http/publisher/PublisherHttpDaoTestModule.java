package de.ladis.infohm.core.dao.http.publisher;

import static org.mockito.Mockito.*;

import java.io.IOException;
import java.io.InputStream;

import javax.inject.Singleton;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HttpContext;

import dagger.Module;
import dagger.Provides;
import de.ladis.infohm.core.dao.http.factory.HttpDaoRequestFactory;
import de.ladis.infohm.core.parser.xml.publisher.XmlPublishersParserTest;

@Module(
injects = {
		PublisherHttpDaoTest.class
})
public class PublisherHttpDaoTestModule {

	@Provides
	@Singleton
	public HttpClient provideHttpClient() {
		return new DefaultHttpClient() {
			@Override
			public <T> T execute(HttpHost target, HttpRequest request,
					ResponseHandler<? extends T> responseHandler,
					HttpContext context) throws IOException, ClientProtocolException {

				InputStream stream = XmlPublishersParserTest.class.getResourceAsStream("publishers.xml");

				HttpResponse response = mock(HttpResponse.class);
				HttpEntity entity = mock(HttpEntity.class);

				when(response.getEntity()).thenReturn(entity);
				when(entity.getContent()).thenReturn(stream);
				when(entity.getContentType()).thenReturn(new BasicHeader("Content-Type", "application/xml"));

				return responseHandler.handleResponse(response);
			}
		};
	}

	@Provides
	@Singleton
	public HttpRequestFactory provideHttpRequestFactory() {
		return new HttpDaoRequestFactory();
	}

	@Provides
	@Singleton
	public PublisherHttpDao providePublisherDao(HttpClient client, HttpRequestFactory factory) {
		return new PublisherHttpDao(client, null, null, factory);
	}

}