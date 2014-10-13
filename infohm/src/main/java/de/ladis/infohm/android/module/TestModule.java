package de.ladis.infohm.android.module;

import dagger.Module;
import de.ladis.infohm.android.activity.TestActivity;

@Module(library = true, includes = AndroidModule.class, injects = TestActivity.class)
public class TestModule {
}
