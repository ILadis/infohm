package de.ladis.infohm.android.module;

import javax.inject.Singleton;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequestFactory;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import android.content.Context;
import android.net.http.AndroidHttpClient;
import dagger.Module;
import dagger.Provides;
import de.ladis.infohm.core.dao.http.authentication.AuthenticationHttpDao;
import de.ladis.infohm.core.dao.http.bookmark.BookmarkHttpDao;
import de.ladis.infohm.core.dao.http.event.EventHttpDao;
import de.ladis.infohm.core.dao.http.factory.HttpDaoRequestFactory;
import de.ladis.infohm.core.dao.http.publisher.PublisherHttpDao;

@Module(
library = true,
includes = {
		AndroidModule.class
},
injects = {
		PublisherHttpDao.class
})
public class HttpDaoModule {

	@Provides
	@Singleton
	public HttpClient provideHttpClient(Context context) {
		return AndroidHttpClient.newInstance(System.getProperty("http.agent"), context);
	}

	@Provides
	@Singleton
	public HttpHost provideHttpHost() {
		return new HttpHost("192.168.0.9", 8080);
	}

	@Provides
	@Singleton
	public CookieStore provideCookieStore() {
		return new BasicCookieStore();
	}

	@Provides
	@Singleton
	public HttpContext provideHttpContext(CookieStore cookieStore) {
		HttpContext context = new BasicHttpContext();
		context.setAttribute(ClientContext.COOKIE_STORE, cookieStore);

		return context;
	}

	@Provides
	@Singleton
	public HttpRequestFactory provideHttpRequestFactory() {
		return new HttpDaoRequestFactory();
	}


	@Provides
	@Singleton
	public AuthenticationHttpDao provideAuthenticationDao(HttpClient client, HttpHost host, HttpContext context, HttpRequestFactory factory) {
		return new AuthenticationHttpDao(client, host, context, factory);
	}

	@Provides
	@Singleton
	public PublisherHttpDao providePublisherDao(HttpClient client, HttpHost host, HttpContext context, HttpRequestFactory factory) {
		return new PublisherHttpDao(client, host, context, factory);
	}

	@Provides
	@Singleton
	public EventHttpDao provideEventDao(HttpClient client, HttpHost host, HttpContext context, HttpRequestFactory factory) {
		return new EventHttpDao(client, host, context, factory);
	}

	@Provides
	@Singleton
	public BookmarkHttpDao provideBookmarkDao(HttpClient client, HttpHost host, HttpContext context, HttpRequestFactory factory) {
		return new BookmarkHttpDao(client, host, context, factory);
	}

}
