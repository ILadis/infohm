package de.ladis.infohm.android.module;

import dagger.Module;
import de.ladis.infohm.android.activity.account.CreateAccountActivity;
import de.ladis.infohm.android.activity.feedback.FeedbackActivity;
import de.ladis.infohm.android.activity.main.MainActivity;
import de.ladis.infohm.android.activity.publisher.StarPublisherActivity;
import de.ladis.infohm.android.activity.splash.SplashActivity;

@Module(
includes = {
		AndroidModule.class,
		ServiceModule.class,
},
injects = {
		SplashActivity.class,
		CreateAccountActivity.class,
		MainActivity.class,
		FeedbackActivity.class,
		StarPublisherActivity.class,
})
public class ActivityModule {
}
