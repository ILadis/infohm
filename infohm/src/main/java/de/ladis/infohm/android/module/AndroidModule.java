package de.ladis.infohm.android.module;

import javax.inject.Singleton;

import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import dagger.Module;
import dagger.Provides;

@Module(library = true)
public class AndroidModule {

	private final Application application;

	public AndroidModule(Application application) {
		this.application = application;
	}

	@Provides
	@Singleton
	public Context provideApplicationContext() {
		return application.getApplicationContext();
	}

	@Provides
	@Singleton
	public NotificationManager provideNotificationManager() {
		return (NotificationManager) application.getSystemService(Context.NOTIFICATION_SERVICE);
	}

}
