package de.ladis.infohm.android.module;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.ladis.infohm.core.dao.http.authentication.AuthenticationHttpDao;
import de.ladis.infohm.core.service.AuthenticationService;

@Module(
library = true,
includes = {
	HttpDaoModule.class
})
public class ServiceModule {

	@Provides
	@Singleton
	public ExecutorService provideExecutorService() {
		return Executors.newFixedThreadPool(3);
	}

	@Provides
	@Singleton
	public AuthenticationService provideAuthenticationService(AuthenticationHttpDao dao, ExecutorService executor) {
		return new AuthenticationService(dao, executor);
	}

}
