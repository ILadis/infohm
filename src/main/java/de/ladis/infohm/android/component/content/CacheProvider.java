package de.ladis.infohm.android.component.content;

import java.util.Locale;

import javax.inject.Inject;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import de.ladis.infohm.android.component.BaseContentProvider;

public class CacheProvider extends BaseContentProvider {

	@Inject
	protected SQLiteOpenHelper helper;

	private static String getTableFrom(Uri uri) {
		return uri.getLastPathSegment().toLowerCase(Locale.US);
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
			String sortOrder) {

		String table = getTableFrom(uri);

		return helper.getReadableDatabase().query(
				table,
				projection,
				selection,
				selectionArgs,
				null,
				null,
				sortOrder
		);
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		String table = getTableFrom(uri);

		long id = helper.getWritableDatabase().insert(
				table,
				null,
				values
		);

		if (id != -1) {
			getContext().getContentResolver()
					.notifyChange(uri, null);

			return Uri.parse(uri + "/" + id);
		} else {
			return null;
		}
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		String table = getTableFrom(uri);

		int count = helper.getWritableDatabase().update(
				table,
				values,
				selection,
				selectionArgs
		);

		if (count != 0) {
			getContext().getContentResolver()
					.notifyChange(uri, null);
		}

		return count;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		String table = getTableFrom(uri);

		int count = helper.getWritableDatabase().delete(
				table,
				selection,
				selectionArgs
		);

		if (count != 0) {
			getContext().getContentResolver()
					.notifyChange(uri, null);
		}

		return count;
	}

	@Override
	public String getType(Uri uri) {
		return "vnd.android.cursor.dir/vnd.infohm." + getTableFrom(uri);
	}

}
