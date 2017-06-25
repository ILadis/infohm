package de.ladis.infohm.android.module;

import javax.inject.Singleton;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import dagger.Module;
import dagger.Provides;
import de.ladis.infohm.core.dao.sqlite.helper.SqliteOpenHelperV1;

@Module(
library = true,
includes = {
		AndroidModule.class
})
public class SqliteDaoModule {

	@Provides
	@Singleton
	public SQLiteOpenHelper provideSqliteOpenHelper(Context context) {
		return new SqliteOpenHelperV1(context);
	}

}
