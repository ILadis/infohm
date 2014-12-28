package de.ladis.infohm.core.dao.content.bookmark;

import static android.net.Uri.*;
import static de.ladis.infohm.util.Arrays.*;
import static de.ladis.infohm.util.SqliteUtil.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import de.ladis.infohm.core.dao.DaoException;
import de.ladis.infohm.core.dao.content.ContentDao;
import de.ladis.infohm.core.dao.domain.BookmarkDao;
import de.ladis.infohm.core.domain.Bookmark;

public class BookmarkContentDao extends ContentDao<Long, Bookmark> implements BookmarkDao {

	private final URI base;

	public BookmarkContentDao(ContentResolver resolver, URI base) {
		super(resolver);

		this.base = base;
	}

	private boolean contains(Bookmark entitiy) throws DaoException {
		return find(entitiy.getId()) != null;
	}

	@Override
	public Bookmark find(Long key) throws DaoException {
		if (key == null || key <= 0) {
			return null;
		}

		Cursor cursor = content().query(
				parse(base + "/bookmark"),
				from("id", "title", "description", "url", "created", "updated"),
				"id = ?",
				from(key.toString()),
				null
		);

		if (cursor.moveToFirst()) {
			return fromCursor(cursor, new Bookmark());
		} else {
			return null;
		}
	}

	@Override
	public List<Bookmark> list() throws DaoException {
		Cursor cursor = content().query(
				parse(base + "/bookmark"),
				from("id", "title", "description", "url", "created", "updated"),
				null,
				null,
				"id ASC"
		);

		List<Bookmark> bookmarks = new ArrayList<Bookmark>(cursor.getCount());

		if (cursor.moveToFirst()) {
			do {
				bookmarks.add(fromCursor(cursor, new Bookmark()));
			} while (cursor.moveToNext());
		}

		return bookmarks;
	}

	@Override
	public void insert(Bookmark entity) throws DaoException {
		if (contains(entity)) {
			update(entity);
		} else {
			Uri uri = content().insert(
					parse(base + "/bookmark"),
					toValues(entity)
			);

			if (uri != null) {
				Long id = Long.decode(uri.getLastPathSegment());
				entity.setId(id);
			}
		}
	}

	@Override
	public void update(Bookmark entity) throws DaoException {
		content().update(
				parse(base + "/bookmark"),
				toValues(entity),
				"id = ?",
				from(entity.getId().toString())
		);
	}

	@Override
	public void delete(Bookmark entity) throws DaoException {
		content().delete(
				parse(base + "/bookmark"),
				"id = ?",
				from(entity.getId().toString())
		);
	}

	private static Bookmark fromCursor(Cursor cursor, Bookmark bookmark) {
		Long id = getLong(cursor, "id");
		String title = getString(cursor, "title");
		String description = getString(cursor, "description");
		String url = getString(cursor, "url");
		DateTime created = getDateTime(cursor, "created");
		DateTime updated = getDateTime(cursor, "updated");

		bookmark.setId(id);
		bookmark.setTitle(title);
		bookmark.setDescription(description);
		bookmark.setUrl(url);
		bookmark.setCreatedAt(created);
		bookmark.setUpdatedAt(updated);

		return bookmark;
	}

	private static ContentValues toValues(Bookmark entity) {
		ContentValues values = new ContentValues();

		values.put("id", entity.getId());
		values.put("title", entity.getTitle());
		values.put("description", entity.getDescription());
		values.put("url", entity.getUrl());
		values.put("created", entity.getCreatedAt().toString());
		values.put("updated", entity.getUpdatedAt().toString());

		return values;
	}

}
