package de.ladis.infohm.android.module;

import dagger.Module;
import de.ladis.infohm.android.content.sqlite.SqliteContentProvider;

@Module(
includes = {
		AndroidModule.class,
		SqliteDaoModule.class
},
injects = {
		SqliteContentProvider.class
})
public class ContentProviderModule {
}
