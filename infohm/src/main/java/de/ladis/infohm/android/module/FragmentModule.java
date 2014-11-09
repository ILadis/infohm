package de.ladis.infohm.android.module;

import dagger.Module;
import de.ladis.infohm.android.activity.test.TestActivity.TestDialog;
import de.ladis.infohm.android.activity.test.TestActivity.TestFragment;

@Module(
includes = {
		AndroidModule.class,
		ContentDaoModule.class
},
injects = {
		TestDialog.class,
		TestFragment.class
})
public class FragmentModule {
}
