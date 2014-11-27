package de.ladis.infohm.android.module;

import dagger.Module;
import de.ladis.infohm.android.fragment.authentication.AuthenticationFragment;
import de.ladis.infohm.android.fragment.drawer.NavigationDrawerFragment;
import de.ladis.infohm.android.fragment.events.EventsFragment;
import de.ladis.infohm.android.fragment.events.EventsPagerFragment;
import de.ladis.infohm.android.fragment.publisher.StarPublisherFragment;
import de.ladis.infohm.android.fragment.search.SearchMenuFragment;

@Module(
includes = {
		AndroidModule.class,
		ServiceModule.class,
},
injects = {
		AuthenticationFragment.class,
		StarPublisherFragment.class,
		EventsPagerFragment.class,
		EventsFragment.class,
		NavigationDrawerFragment.class,
		SearchMenuFragment.class,
})
public class FragmentModule {
}
