package de.ladis.infohm.android.module;

import javax.inject.Singleton;

import android.accounts.AccountManager;
import android.app.Application;
import android.app.NotificationManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.SearchRecentSuggestionsProvider;
import android.provider.SearchRecentSuggestions;
import android.util.DisplayMetrics;
import dagger.Module;
import dagger.Provides;
import de.ladis.infohm.android.component.account.AccountAuthenticator;

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
	public DisplayMetrics provideDisplayMetrics() {
		return application.getResources().getDisplayMetrics();
	}

	@Provides
	@Singleton
	public NotificationManager provideNotificationManager() {
		return (NotificationManager) application.getSystemService(Context.NOTIFICATION_SERVICE);
	}

	@Provides
	@Singleton
	public SearchManager provideSearchManager() {
		return (SearchManager) application.getSystemService(Context.SEARCH_SERVICE);
	}

	@Provides
	@Singleton
	public AccountManager provideAccountManager(Context context) {
		return AccountManager.get(context);
	}

	@Provides
	@Singleton
	public AccountAuthenticator provideAccountAuthenticator(Context context) {
		return new AccountAuthenticator(context);
	}

	@Provides
	@Singleton
	public SearchRecentSuggestions provideSuggestionProvider(Context context) {
		return new SearchRecentSuggestions(context, "de.ladis.infohm.provider.SuggestionProvider", SearchRecentSuggestionsProvider.DATABASE_MODE_QUERIES);
	}

}
