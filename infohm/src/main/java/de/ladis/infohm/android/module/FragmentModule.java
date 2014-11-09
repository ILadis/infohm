package de.ladis.infohm.android.module;

import dagger.Module;

@Module(
includes = {
		AndroidModule.class,
		ContentDaoModule.class
},
injects = {
})
public class FragmentModule {
}
