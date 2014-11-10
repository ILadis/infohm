package de.ladis.infohm.core.dao.http.publisher;

import javax.inject.Singleton;

import org.apache.http.HttpRequestFactory;

import dagger.Module;
import dagger.Provides;
import de.ladis.infohm.core.dao.http.HttpDaoTestModule;
import de.ladis.infohm.core.dao.http.MockedHttpClient;

@Module(
includes = {
		HttpDaoTestModule.class
},
injects = {
		PublisherHttpDaoTest.class
})
public class PublisherHttpDaoTestModule {

	@Provides
	@Singleton
	public PublisherHttpDao providePublisherDao(MockedHttpClient client, HttpRequestFactory factory) {
		return new PublisherHttpDao(client, null, null, factory);
	}

}