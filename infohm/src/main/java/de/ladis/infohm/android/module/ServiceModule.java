package de.ladis.infohm.android.module;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import android.accounts.AccountManager;
import android.content.Context;
import dagger.Module;
import dagger.Provides;
import de.ladis.infohm.core.concurrent.ExecutorFactory;
import de.ladis.infohm.core.dao.content.bookmark.BookmarkContentDao;
import de.ladis.infohm.core.dao.content.cafeteria.CafeteriaContentDao;
import de.ladis.infohm.core.dao.content.event.EventContentDao;
import de.ladis.infohm.core.dao.content.feedback.FeedbackContentDao;
import de.ladis.infohm.core.dao.content.meal.MealContentDao;
import de.ladis.infohm.core.dao.content.publisher.PublisherContentDao;
import de.ladis.infohm.core.dao.content.search.SearchContentDao;
import de.ladis.infohm.core.dao.content.synchronize.SynchronizeContentDao;
import de.ladis.infohm.core.dao.http.authentication.AuthenticationHttpDao;
import de.ladis.infohm.core.dao.http.bookmark.BookmarkHttpDao;
import de.ladis.infohm.core.dao.http.cafeteria.CafeteriaHttpDao;
import de.ladis.infohm.core.dao.http.event.EventHttpDao;
import de.ladis.infohm.core.dao.http.feedback.FeedbackHttpDao;
import de.ladis.infohm.core.dao.http.meal.MealHttpDao;
import de.ladis.infohm.core.dao.http.publisher.PublisherHttpDao;
import de.ladis.infohm.core.service.AuthenticationService;
import de.ladis.infohm.core.service.BookmarkService;
import de.ladis.infohm.core.service.CafeteriaService;
import de.ladis.infohm.core.service.EventService;
import de.ladis.infohm.core.service.FeedbackService;
import de.ladis.infohm.core.service.MealService;
import de.ladis.infohm.core.service.PublisherService;
import de.ladis.infohm.core.service.SearchService;
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
	public ExecutorFactory provideExecutorFactory() {
		final ExecutorService remote = Executors.newFixedThreadPool(3);
		final ExecutorService local = Executors.newSingleThreadExecutor();

		return new ExecutorFactory() {

			@Override
			public ExecutorService forRemote() {
				return remote;
			}

			@Override
			public ExecutorService forLocal() {
				return local;
			}

		};
	}

	@Provides
	@Singleton
	public AuthenticationService provideAuthenticationService(AccountManager manager, AuthenticationHttpDao dao, ExecutorFactory factory) {
		return new AuthenticationService(manager, dao, factory);
	}

	@Provides
	@Singleton
	public PublisherService providePublisherService(PublisherContentDao cache, PublisherHttpDao remote, SearchContentDao search, ExecutorFactory factory) {
		return new PublisherService(cache, remote, search, factory);
	}

	@Provides
	@Singleton
	public EventService provideEventService(EventContentDao cache, EventHttpDao remote, SearchContentDao search, ExecutorFactory factory) {
		return new EventService(cache, remote, search, factory);
	}

	@Provides
	@Singleton
	public BookmarkService provideBookmarkService(BookmarkContentDao cache, BookmarkHttpDao remote, SearchContentDao search, ExecutorFactory factory) {
		return new BookmarkService(cache, remote, search, factory);
	}

	@Provides
	@Singleton
	public FeedbackService provideFeedbackService(FeedbackContentDao cache, FeedbackHttpDao remote, ExecutorFactory factory) {
		return new FeedbackService(cache, remote, factory);
	}

	@Provides
	@Singleton
	public SynchronizeService provideSynchronizeService(Context context, SynchronizeContentDao dao, ExecutorFactory factory) {
		return new SynchronizeService(context, dao, factory);
	}

	@Provides
	@Singleton
	public SearchService provideSearchService(SearchContentDao dao, ExecutorFactory factory) {
		return new SearchService(dao, factory);
	}

	@Provides
	@Singleton
	public CafeteriaService provideCafeteriaService(CafeteriaContentDao cache, CafeteriaHttpDao remote, SearchContentDao search, ExecutorFactory factory) {
		return new CafeteriaService(cache, remote, search, factory);
	}

	@Provides
	@Singleton
	public MealService provideMealService(MealContentDao cache, MealHttpDao remote, ExecutorFactory factory) {
		return new MealService(cache, remote, factory);
	}

}
