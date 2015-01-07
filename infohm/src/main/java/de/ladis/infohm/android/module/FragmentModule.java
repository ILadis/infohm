package de.ladis.infohm.android.module;

import dagger.Module;
import de.ladis.infohm.android.fragment.authentication.AuthenticationFragment;
import de.ladis.infohm.android.fragment.bookmarks.BookmarksFragment;
import de.ladis.infohm.android.fragment.cafeteria.CafeteriasPagerFragment;
import de.ladis.infohm.android.fragment.events.EventsFragment;
import de.ladis.infohm.android.fragment.events.EventsHighlightFragment;
import de.ladis.infohm.android.fragment.events.EventsPagerFragment;
import de.ladis.infohm.android.fragment.feedback.FeedbackFragment;
import de.ladis.infohm.android.fragment.meal.DailyMealsFragment;
import de.ladis.infohm.android.fragment.meal.MealsFragment;
import de.ladis.infohm.android.fragment.meal.MealsPagerFragment;
import de.ladis.infohm.android.fragment.navigation.NavigationFragment;
import de.ladis.infohm.android.fragment.publisher.StarPublisherFragment;
import de.ladis.infohm.android.fragment.search.SearchFragment;
import de.ladis.infohm.android.fragment.search.SearchMenuFragment;
import de.ladis.infohm.android.fragment.startup.StartupFragment;

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
		EventsHighlightFragment.class,
		NavigationFragment.class,
		SearchMenuFragment.class,
		BookmarksFragment.class,
		FeedbackFragment.class,
		StartupFragment.class,
		SearchFragment.class,
		CafeteriasPagerFragment.class,
		DailyMealsFragment.class,
		MealsPagerFragment.class,
		MealsFragment.class,
})
public class FragmentModule {
}
