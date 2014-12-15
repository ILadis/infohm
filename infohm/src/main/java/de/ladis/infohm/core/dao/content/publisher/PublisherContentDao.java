package de.ladis.infohm.core.dao.content.publisher;

import static android.net.Uri.*;
import static de.ladis.infohm.util.Arrays.*;

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
import de.ladis.infohm.core.dao.domain.PublisherDao;
import de.ladis.infohm.core.domain.Publisher;

public class PublisherContentDao extends ContentDao<Long, Publisher> implements PublisherDao {

	private final URI base;

	public PublisherContentDao(ContentResolver resolver, URI base) {
		super(resolver);

		this.base = base;
	}

	private boolean contains(Publisher entitiy) throws DaoException {
		return find(entitiy.getId()) != null;
	}

	@Override
	public Publisher find(Long key) throws DaoException {
		if (key == null || key <= 0) {
			return null;
		}

		Cursor cursor = content().query(
				parse(base + "/publisher"),
				from("id", "name", "description", "created", "updated"),
				"id = ?",
				from(key.toString()),
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
				parse(base + "/publisher"),
				from("id", "name", "description", "created", "updated"),
				null,
				null,
				"id ASC"
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
		if (contains(entity)) {
			update(entity);
		} else {
			Uri uri = content().insert(
					parse(base + "/publisher"),
					toValues(entity)
			);

			if (uri != null) {
				Long id = Long.decode(uri.getLastPathSegment());
				entity.setId(id);
			}
		}
	}

	@Override
	public void update(Publisher entity) throws DaoException {
		content().update(
				parse(base + "/publisher"),
				toValues(entity),
				"id = ?",
				from(entity.getId().toString())
		);
	}

	@Override
	public void delete(Publisher entity) throws DaoException {
		content().delete(
				parse(base + "/publisher"),
				"id = ?",
				from(entity.getId().toString())
		);
	}

	@Override
	public List<Publisher> starred() {
		Cursor cursor = content().query(
				parse(base + "/starred"),
				from("pid"),
				null,
				null,
				null
		);

		List<Publisher> starred = new ArrayList<Publisher>(cursor.getCount());

		if (cursor.moveToFirst()) {
			do {
				Long id = cursor.getLong(0);

				starred.add(find(id));
			} while (cursor.moveToNext());
		}

		return starred;
	}

	@Override
	public void star(Publisher entity) {
		ContentValues values = new ContentValues();
		values.put("pid", entity.getId());

		content().insert(
				parse(base + "/starred"),
				values
		);
	}

	@Override
	public void unstarAll() {
		content().delete(
				parse(base + "/starred"),
				null,
				null
		);
	}

	@Override
	public void unstar(Publisher entity) {
		content().delete(
				parse(base + "/starred"),
				"pid = ?",
				from(entity.getId().toString())
		);
	}

	private static Publisher fromCursor(Cursor cursor) {
		Long id = cursor.getLong(cursor.getColumnIndex("id"));
		String name = cursor.getString(cursor.getColumnIndex("name"));
		String description = cursor.getString(cursor.getColumnIndex("description"));
		DateTime created = DateTime.parse(cursor.getString(cursor.getColumnIndex("created")));
		DateTime updated = DateTime.parse(cursor.getString(cursor.getColumnIndex("updated")));

		Publisher publisher = new Publisher();
		publisher.setId(id);
		publisher.setName(name);
		publisher.setDescription(description);
		publisher.setCreatedAt(created);
		publisher.setUpdatedAt(updated);

		return publisher;
	}

	private static ContentValues toValues(Publisher entity) {
		ContentValues values = new ContentValues();

		values.put("id", entity.getId());
		values.put("name", entity.getName());
		values.put("description", entity.getDescription());
		values.put("created", entity.getCreatedAt().toString());
		values.put("updated", entity.getUpdatedAt().toString());

		return values;
	}

}
