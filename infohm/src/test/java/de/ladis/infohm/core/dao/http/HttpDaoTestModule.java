package de.ladis.infohm.core.dao.http;

import javax.inject.Singleton;

import org.apache.http.HttpRequestFactory;
import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import dagger.Module;
import dagger.Provides;
import de.ladis.infohm.core.dao.http.factory.HttpDaoRequestFactory;

@Module(library = true)
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

}