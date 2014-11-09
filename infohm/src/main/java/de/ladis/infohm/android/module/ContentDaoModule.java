package de.ladis.infohm.android.module;

import javax.inject.Singleton;

import android.content.ContentResolver;
import android.content.Context;
import dagger.Module;
import dagger.Provides;
import de.ladis.infohm.core.dao.content.publisher.PublisherContentDao;
import de.ladis.infohm.core.dao.domain.PublisherDao;

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
	public PublisherDao providePublisherDao(ContentResolver resolver) {
		return new PublisherContentDao(resolver, "content://de.ladis.infohm.provider");
	}

}
