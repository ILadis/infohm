package de.ladis.infohm.core.dao.http;

import javax.inject.Singleton;

import org.apache.http.HttpRequestFactory;
import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import dagger.Module;
import dagger.Provides;
import de.ladis.infohm.core.dao.http.authentication.AuthenticationHttpDao;
import de.ladis.infohm.core.dao.http.authentication.AuthenticationHttpDaoTest;
import de.ladis.infohm.core.dao.http.factory.HttpDaoRequestFactory;
import de.ladis.infohm.core.dao.http.publisher.PublisherHttpDao;
import de.ladis.infohm.core.dao.http.publisher.PublisherHttpDaoTest;
import de.ladis.infohm.test.mock.MockedHttpClient;

@Module(
library = true,
injects = {
		PublisherHttpDaoTest.class,
		AuthenticationHttpDaoTest.class,
})
public class HttpDaoTestModule {

	@Provides
	@Singleton
	public MockedHttpClient provideHttpClient() {
		return new MockedHttpClient();
	}

	@Provides
	@Singleton
	public CookieStore provideCookieStore() {
		return new BasicCookieStore();
	}

	@Provides
	@Singleton
	public HttpContext provideHttpContext(CookieStore cookieStore) {
		return new BasicHttpContext();
	}

	@Provides
	@Singleton
	public HttpRequestFactory provideHttpRequestFactory() {
		return new HttpDaoRequestFactory();
	}

	@Provides
	@Singleton
	public AuthenticationHttpDao provideAuthenticationDao(MockedHttpClient client, HttpContext context, HttpRequestFactory factory) {
		return new AuthenticationHttpDao(client, null, context, factory);
	}

	@Provides
	@Singleton
	public PublisherHttpDao providePublisherDao(MockedHttpClient client, HttpRequestFactory factory) {
		return new PublisherHttpDao(client, null, null, factory);
	}

}