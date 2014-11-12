package de.ladis.infohm.core.dao.content.publisher;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import de.ladis.infohm.core.dao.DaoException;
import de.ladis.infohm.core.dao.content.ContentDao;
import de.ladis.infohm.core.dao.domain.PublisherDao;
import de.ladis.infohm.core.domain.Publisher;

public class PublisherContentDao extends ContentDao<Integer, Publisher> implements PublisherDao {

	private final URI base;

	public PublisherContentDao(ContentResolver resolver, URI base) {
		super(resolver);

		this.base = base;
	}

	@Override
	public Publisher find(Integer key) throws DaoException {
		Cursor cursor = content().query(
				Uri.parse(base + "/publisher"),
				null,
				"id = ?",
				new String[] { String.valueOf(key) },
				null
		);

		if (cursor.moveToFirst()) {
			return fromCursor(cursor);
		} else {
			return null;
		}
	}

	@Override
	public List<Publisher> list() throws DaoException {
		Cursor cursor = content().query(
				Uri.parse(base + "/publisher"),
				null,
				null,
				null,
				null
		);

		List<Publisher> publishers = new ArrayList<Publisher>(cursor.getCount());

		if (cursor.moveToFirst()) {
			do {
				publishers.add(fromCursor(cursor));
			} while (cursor.moveToNext());
		}

		return publishers;
	}

	@Override
	public void insert(Publisher entity) throws DaoException {
		Uri uri = content().insert(
				Uri.parse(base + "/publisher"),
				toValues(entity)
		);

		if (uri != null) {
			Long id = Long.decode(uri.getLastPathSegment());

			entity.setId(id);
		}
	}

	@Override
	public void update(Publisher entity) throws DaoException {
		content().update(
				Uri.parse(base + "/publisher"),
				toValues(entity),
				"id = ?",
				new String[] { String.valueOf(entity.getId()) }
		);
	}

	@Override
	public void delete(Publisher entity) throws DaoException {
		content().delete(
				Uri.parse(base + "/publisher"),
				"id = ?",
				new String[] { String.valueOf(entity.getId()) }
		);
	}

	private static Publisher fromCursor(Cursor cursor) {
		Long id = cursor.getLong(cursor.getColumnIndex("id"));
		String name = cursor.getString(cursor.getColumnIndex("name"));
		String description = cursor.getString(cursor.getColumnIndex("description"));
		Boolean starred = cursor.getInt(cursor.getColumnIndex("starred")) != 0;

		Publisher publisher = new Publisher();
		publisher.setId(id);
		publisher.setName(name);
		publisher.setDescription(description);
		publisher.setStarred(starred);

		return publisher;
	}

	private static ContentValues toValues(Publisher entity) {
		ContentValues values = new ContentValues();

		values.put("id", entity.getId());
		values.put("name", entity.getName());
		values.put("description", entity.getDescription());
		values.put("starred", entity.isStarred());

		return values;
	}

}
