package de.ladis.infohm.android.module;

import dagger.Module;
import de.ladis.infohm.android.fragment.authentication.AuthenticationFragment;

@Module(
includes = {
		AndroidModule.class,
		ContentDaoModule.class
},
injects = {
		AuthenticationFragment.class
})
public class FragmentModule {
}
