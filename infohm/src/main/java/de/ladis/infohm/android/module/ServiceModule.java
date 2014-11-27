package de.ladis.infohm.android.module;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import android.accounts.AccountManager;
import dagger.Module;
import dagger.Provides;
import de.ladis.infohm.core.dao.content.event.EventContentDao;
import de.ladis.infohm.core.dao.content.publisher.PublisherContentDao;
import de.ladis.infohm.core.dao.http.authentication.AuthenticationHttpDao;
import de.ladis.infohm.core.dao.http.event.EventHttpDao;
import de.ladis.infohm.core.dao.http.publisher.PublisherHttpDao;
import de.ladis.infohm.core.service.AuthenticationService;
import de.ladis.infohm.core.service.EventService;
import de.ladis.infohm.core.service.PublisherService;

@Module(
library = true,
includes = {
		AndroidModule.class,
		ContentDaoModule.class,
		HttpDaoModule.class,
})
public class ServiceModule {

	@Provides
	@Singleton
	public ExecutorService provideExecutorService() {
		return Executors.newFixedThreadPool(3);
	}

	@Provides
	@Singleton
	public AuthenticationService provideAuthenticationService(AccountManager manager, AuthenticationHttpDao dao, ExecutorService executor) {
		return new AuthenticationService(manager, dao, executor);
	}

	@Provides
	@Singleton
	public PublisherService providePublisherService(PublisherContentDao cache, PublisherHttpDao remote, ExecutorService executor) {
		return new PublisherService(cache, remote, executor);
	}

	@Provides
	@Singleton
	public EventService provideEventService(EventContentDao cache, EventHttpDao remote, ExecutorService executor) {
		return new EventService(cache, remote, executor);
	}

}
