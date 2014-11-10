package de.ladis.infohm.core.dao.http.authentication;

import javax.inject.Singleton;

import org.apache.http.HttpRequestFactory;
import org.apache.http.protocol.HttpContext;

import dagger.Module;
import dagger.Provides;
import de.ladis.infohm.core.dao.http.HttpDaoTestModule;
import de.ladis.infohm.core.dao.http.MockedHttpClient;

@Module(
includes = {
		HttpDaoTestModule.class
},
injects = {
		AuthenticationHttpDaoTest.class
})
public class AuthenticationHttpDaoTestModule {

	@Provides
	@Singleton
	public AuthenticationHttpDao provideAuthenticationDao(MockedHttpClient client, HttpContext context, HttpRequestFactory factory) {
		return new AuthenticationHttpDao(client, null, context, factory);
	}

}