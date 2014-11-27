package de.ladis.infohm.android.module;

import dagger.Module;
import de.ladis.infohm.android.activity.account.CreateAccountActivity;
import de.ladis.infohm.android.activity.events.EventsActivity;
import de.ladis.infohm.android.activity.splash.SplashActivity;
import de.ladis.infohm.android.activity.welcome.WelcomeActivity;

@Module(
includes = {
		AndroidModule.class,
		ServiceModule.class,
},
injects = {
		SplashActivity.class,
		CreateAccountActivity.class,
		WelcomeActivity.class,
		EventsActivity.class,
})
public class ActivityModule {
}
