package de.ladis.infohm.android.module;

import dagger.Module;
import de.ladis.infohm.android.component.account.AccountAuthenticator;
import de.ladis.infohm.android.component.content.ContentProvider;
import de.ladis.infohm.android.component.content.SyncAdapter;

@Module(
includes = {
		AndroidModule.class,
		SqliteDaoModule.class,
		ServiceModule.class,
},
injects = {
		ContentProvider.class,
		AccountAuthenticator.class,
		SyncAdapter.class,
})
public class ComponentModule {
}
