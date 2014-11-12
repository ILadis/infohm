package de.ladis.infohm.android.module;

import dagger.Module;
import de.ladis.infohm.android.fragment.authentication.AuthenticationFragment;

@Module(
includes = {
		AndroidModule.class,
		ServiceModule.class
},
injects = {
		AuthenticationFragment.class
})
public class FragmentModule {
}
