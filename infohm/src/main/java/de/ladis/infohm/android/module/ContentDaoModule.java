package de.ladis.infohm.android.module;

import java.net.URI;

import javax.inject.Singleton;

import android.content.ContentResolver;
import android.content.Context;
import dagger.Module;
import dagger.Provides;
import de.ladis.infohm.core.dao.content.bookmark.BookmarkContentDao;
import de.ladis.infohm.core.dao.content.event.EventContentDao;
import de.ladis.infohm.core.dao.content.feedback.FeedbackContentDao;
import de.ladis.infohm.core.dao.content.publisher.PublisherContentDao;

@Module(
library = true,
includes = {
		AndroidModule.class
})
public class ContentDaoModule {

	@Provides
	@Singleton
	public ContentResolver provideContentResolver(Context context) {
		return context.getContentResolver();
	}

	@Provides
	@Singleton
	public URI provideBaseUri() {
		return URI.create("content://de.ladis.infohm.provider");
	}

	@Provides
	@Singleton
	public PublisherContentDao providePublisherDao(ContentResolver resolver, URI base) {
		return new PublisherContentDao(resolver, base);
	}

	@Provides
	@Singleton
	public EventContentDao provideEventDao(ContentResolver resolver, URI base) {
		return new EventContentDao(resolver, base);
	}

	@Provides
	@Singleton
	public BookmarkContentDao provideBookmarkDao(ContentResolver resolver, URI base) {
		return new BookmarkContentDao(resolver, base);
	}

	@Provides
	@Singleton
	public FeedbackContentDao provideFeedbackDao(ContentResolver resolver, URI base) {
		return new FeedbackContentDao(resolver, base);
	}

}
