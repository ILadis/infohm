package de.ladis.infohm.android.module;

import dagger.Module;
import de.ladis.infohm.android.fragment.authentication.AuthenticationFragment;
import de.ladis.infohm.android.fragment.publisher.StarPublisherFragment;

@Module(
includes = {
		AndroidModule.class,
		ServiceModule.class
},
injects = {
		AuthenticationFragment.class,
		StarPublisherFragment.class
})
public class FragmentModule {
}
