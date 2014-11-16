package de.ladis.infohm.core.dao.content.event;

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
import de.ladis.infohm.core.dao.domain.EventDao;
import de.ladis.infohm.core.domain.Event;
import de.ladis.infohm.core.domain.Publisher;

public class EventContentDao extends ContentDao<Long, Event> implements EventDao {

	private final URI base;

	public EventContentDao(ContentResolver resolver, URI base) {
		super(resolver);

		this.base = base;
	}

	private boolean contains(Event entitiy) throws DaoException {
		return find(entitiy.getId()) != null;
	}

	@Override
	public Event find(Long key) throws DaoException {
		if (key <= 0) {
			return null;
		}

		Cursor cursor = content().query(
				parse(base + "/event"),
				null,
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
	public Event find(Publisher entity, Long key) throws DaoException {
		if (entity.getId() <= 0 || key <= 0) {
			return null;
		}

		Cursor cursor = content().query(
				parse(base + "/event"),
				null,
				"id = ? AND pid = ?",
				from(key.toString(), entity.getId().toString()),
				null
		);

		if (cursor.moveToFirst()) {
			return fromCursor(cursor);
		} else {
			return null;
		}
	}

	@Override
	public List<Event> list() throws DaoException {
		Cursor cursor = content().query(
				parse(base + "/event"),
				null,
				null,
				null,
				null
		);

		List<Event> events = new ArrayList<Event>(cursor.getCount());

		if (cursor.moveToFirst()) {
			do {
				events.add(fromCursor(cursor));
			} while (cursor.moveToNext());
		}

		return events;
	}

	@Override
	public List<Event> list(Publisher entity) throws DaoException {
		Cursor cursor = content().query(
				parse(base + "/event"),
				null,
				"pid = ?",
				from(entity.getId().toString()),
				null
		);

		List<Event> events = new ArrayList<Event>(cursor.getCount());

		if (cursor.moveToFirst()) {
			do {
				events.add(fromCursor(cursor));
			} while (cursor.moveToNext());
		}

		return events;
	}

	@Override
	public List<Event> since(Publisher entity, DateTime when) throws DaoException {
		Cursor cursor = content().query(
				parse(base + "/event"),
				null,
				"pid = ? AND datetime(createdAt) >= datetime(?)",
				from(entity.getId().toString(), when.toString()),
				null
		);

		List<Event> events = new ArrayList<Event>(cursor.getCount());

		if (cursor.moveToFirst()) {
			do {
				events.add(fromCursor(cursor));
			} while (cursor.moveToNext());
		}

		return events;
	}

	@Override
	public void insert(Event entity) throws DaoException {
		throw new DaoException(this, new UnsupportedOperationException());
	}

	@Override
	public void insert(Publisher key, Event entity) throws DaoException {
		if (contains(entity)) {
			update(entity);
		} else {
			Uri uri = content().insert(
					parse(base + "/events"),
					toValues(key, entity)
			);

			if (uri != null) {
				Long id = Long.decode(uri.getLastPathSegment());
				entity.setId(id);
			}
		}
	}

	@Override
	public void update(Event entity) throws DaoException {
		content().update(
				parse(base + "/event"),
				toValues(entity),
				"id = ?",
				from(entity.getId().toString())
		);
	}

	@Override
	public void delete(Event entity) throws DaoException {
		content().delete(
				parse(base + "/event"),
				"id = ?",
				from(entity.getId().toString())
		);
	}

	private static Event fromCursor(Cursor cursor) {
		Long id = cursor.getLong(cursor.getColumnIndex("id"));
		String headline = cursor.getString(cursor.getColumnIndex("headline"));
		String content = cursor.getString(cursor.getColumnIndex("content"));
		DateTime created = DateTime.parse(cursor.getString(cursor.getColumnIndex("created")));
		DateTime updated = DateTime.parse(cursor.getString(cursor.getColumnIndex("updated")));

		Event event = new Event();
		event.setId(id);
		event.setHeadline(headline);
		event.setContent(content);
		event.setCreatedAt(created);
		event.setUpdatedAt(updated);

		return event;
	}

	private static ContentValues toValues(Event entity) {
		ContentValues values = new ContentValues();

		values.put("id", entity.getId());
		values.put("headline", entity.getHeadline());
		values.put("content", entity.getContent());
		values.put("created", entity.getCreatedAt().toString());
		values.put("updated", entity.getUpdatedAt().toString());

		return values;
	}

	private static ContentValues toValues(Publisher key, Event entity) {
		ContentValues values = toValues(entity);
		values.put("pid", key.getId());

		return values;
	}

}
