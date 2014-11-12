package de.ladis.infohm.android.module;

import dagger.Module;
import de.ladis.infohm.android.activity.authentication.AuthenticationActivity;
import de.ladis.infohm.android.activity.welcome.WelcomeActivity;

@Module(
includes = {
		AndroidModule.class,
		ServiceModule.class
},
injects = {
		AuthenticationActivity.class,
		WelcomeActivity.class
})
public class ActivityModule {
}
