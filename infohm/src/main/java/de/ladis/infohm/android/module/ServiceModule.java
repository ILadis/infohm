package de.ladis.infohm.android.module;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import android.accounts.AccountManager;
import android.content.Context;
import dagger.Module;
import dagger.Provides;
import de.ladis.infohm.core.dao.content.bookmark.BookmarkContentDao;
import de.ladis.infohm.core.dao.content.event.EventContentDao;
import de.ladis.infohm.core.dao.content.feedback.FeedbackContentDao;
import de.ladis.infohm.core.dao.content.publisher.PublisherContentDao;
import de.ladis.infohm.core.dao.content.synchronize.SynchronizeContentDao;
import de.ladis.infohm.core.dao.http.authentication.AuthenticationHttpDao;
import de.ladis.infohm.core.dao.http.bookmark.BookmarkHttpDao;
import de.ladis.infohm.core.dao.http.event.EventHttpDao;
import de.ladis.infohm.core.dao.http.feedback.FeedbackHttpDao;
import de.ladis.infohm.core.dao.http.publisher.PublisherHttpDao;
import de.ladis.infohm.core.service.AuthenticationService;
import de.ladis.infohm.core.service.BookmarkService;
import de.ladis.infohm.core.service.EventService;
import de.ladis.infohm.core.service.FeedbackService;
import de.ladis.infohm.core.service.PublisherService;
import de.ladis.infohm.core.service.SynchronizeService;

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

	@Provides
	@Singleton
	public BookmarkService provideBookmarkService(BookmarkContentDao cache, BookmarkHttpDao remote, ExecutorService executor) {
		return new BookmarkService(cache, remote, executor);
	}

	@Provides
	@Singleton
	public FeedbackService provideFeedbackService(FeedbackContentDao cache, FeedbackHttpDao remote, ExecutorService executor) {
		return new FeedbackService(cache, remote, executor);
	}

	@Provides
	@Singleton
	public SynchronizeService provideSynchronizeService(Context context, SynchronizeContentDao dao, ExecutorService executor) {
		return new SynchronizeService(context, dao, executor);
	}

}
