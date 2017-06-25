package de.ladis.infohm.core.dao.content.synchronize;

import static android.net.Uri.*;
import static de.ladis.infohm.util.Arrays.*;
import static de.ladis.infohm.util.SqliteUtil.*;

import java.net.URI;
import java.util.List;

import org.joda.time.DateTime;

import android.accounts.Account;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import de.ladis.infohm.core.dao.DaoException;
import de.ladis.infohm.core.dao.content.ContentDao;
import de.ladis.infohm.core.dao.domain.SynchronizeDao;

public class SynchronizeContentDao extends ContentDao<Account, DateTime> implements SynchronizeDao {

	private final URI base;

	public SynchronizeContentDao(ContentResolver resolver, URI base) {
		super(resolver);

		this.base = base;
	}

	private boolean contains(Account account) throws DaoException {
		return find(account) != null;
	}

	@Override
	public DateTime find(Account account) throws DaoException {
		if (account == null) {
			return null;
		}

		Cursor cursor = content().query(
				parse(base + "/sync"),
				from("synced"),
				"account = ?",
				from(account.name),
				null
		);

		if (cursor.moveToFirst()) {
			return fromCursor(cursor);
		} else {
			return null;
		}
	}

	@Override
	public List<DateTime> list() throws DaoException {
		throw new DaoException(this, new UnsupportedOperationException());
	}

	@Override
	public void insert(DateTime entity) throws DaoException {
		throw new DaoException(this, new UnsupportedOperationException());
	}

	@Override
	public void update(DateTime entity) throws DaoException {
		throw new DaoException(this, new UnsupportedOperationException());
	}

	@Override
	public void delete(DateTime entity) throws DaoException {
		throw new DaoException(this, new UnsupportedOperationException());
	}

	@Override
	public void update(Account account, DateTime when) {
		if (contains(account)) {
			content().update(
					parse(base + "/sync"),
					toValues(account, when),
					"account = ?",
					from(account.name)
			);
		} else {
			content().insert(
					parse(base + "/sync"),
					toValues(account, when)
			);
		}
	}

	private static DateTime fromCursor(Cursor cursor) {
		DateTime when = getDateTime(cursor, "synced");

		return when;
	}

	private static ContentValues toValues(Account account, DateTime when) {
		ContentValues values = new ContentValues();

		values.put("account", account.name);
		values.put("synced", when.toString());

		return values;
	}

}
