package de.ladis.infohm.android.module;

import dagger.Module;
import de.ladis.infohm.android.activity.authentication.AuthenticationActivity;

@Module(
includes = {
		AndroidModule.class,
		ServiceModule.class
},
injects = {
		AuthenticationActivity.class
})
public class ActivityModule {
}
