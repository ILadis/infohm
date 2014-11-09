package de.ladis.infohm.android.module;

import dagger.Module;
import de.ladis.infohm.android.activity.search.SearchActivity;
import de.ladis.infohm.android.activity.test.TestActivity;
import de.ladis.infohm.android.activity.test.TestActivity.TestDialog;
import de.ladis.infohm.android.activity.test.TestActivity.TestFragment;

@Module(
includes = {
		AndroidModule.class,
		ContentDaoModule.class
},
injects = {
		TestActivity.class,
		SearchActivity.class,
		TestDialog.class,
		TestFragment.class
})
public class ActivityModule {
}
