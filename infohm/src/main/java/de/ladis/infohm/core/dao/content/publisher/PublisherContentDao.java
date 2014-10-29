package de.ladis.infohm.core.dao.content.publisher;

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

	private final String baseUri;

	public PublisherContentDao(ContentResolver resolver, String baseUri) {
		super(resolver);

		this.baseUri = baseUri;
	}

	private static Publisher fromCursor(Cursor cursor) {
		Long id = cursor.getLong(cursor.getColumnIndex("id"));
		String name = cursor.getString(cursor.getColumnIndex("name"));
		String description = cursor.getString(cursor.getColumnIndex("description"));

		Publisher publisher = new Publisher();
		publisher.setId(id);
		publisher.setName(name);
		publisher.setDescription(description);

		return publisher;
	}

	private static ContentValues toValues(Publisher entity) {
		ContentValues values = new ContentValues();

		values.put("name", entity.getName());
		values.put("description", entity.getDescription());

		return values;
	}

	@Override
	public Publisher find(Integer key) throws DaoException {
		Cursor cursor = content().query(
				Uri.parse(baseUri + "/publisher"),
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
				Uri.parse(baseUri + "/publisher"),
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
				Uri.parse(baseUri + "/publisher"),
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
				Uri.parse(baseUri + "/publisher"),
				toValues(entity),
				"id = ?",
				new String[] { String.valueOf(entity.getId()) }
		);
	}

	@Override
	public void delete(Publisher entity) throws DaoException {
		content().delete(
				Uri.parse(baseUri + "/publisher"),
				"id = ?",
				new String[] { String.valueOf(entity.getId()) }
		);
	}

}
