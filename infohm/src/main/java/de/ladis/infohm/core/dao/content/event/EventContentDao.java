package de.ladis.infohm.core.dao.content.event;

import static android.net.Uri.*;
import static de.ladis.infohm.util.Arrays.*;
import static de.ladis.infohm.util.SqliteUtil.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.google.common.collect.Range;

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
		if (key == null || key <= 0) {
			return null;
		}

		Cursor cursor = content().query(
				parse(base + "/event"),
				from("id", "headline", "content", "created", "updated"),
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
				from("id", "headline", "content", "created", "updated"),
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
				from("id", "headline", "content", "created", "updated"),
				null,
				null,
				"datetime(created) DESC"
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
				from("id", "headline", "content", "created", "updated"),
				"pid = ?",
				from(entity.getId().toString()),
				"datetime(created) DESC"
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
	public List<Event> list(Publisher entity, Range<Integer> range) throws DaoException {
		Integer count = range.upperEndpoint() - range.lowerEndpoint() + 1;
		Integer offset = range.lowerEndpoint();

		Cursor cursor = content().query(
				parse(base + "/event"),
				from("id", "headline", "content", "created", "updated"),
				"pid = ?",
				from(entity.getId().toString()),
				"datetime(created) DESC LIMIT "+ offset + ", " + count
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
	public List<Event> highlights(Range<Integer> range) throws DaoException {
		Cursor cursor = content().query(
				parse(base + "/event"),
				from("pid"),
				null,
				null,
				null);

		List<Event> events = new ArrayList<Event>();

		if (cursor.moveToFirst()) {
			Set<String> pids = new HashSet<String>(cursor.getCount());

			while (cursor.moveToNext()) {
				pids.add(String.valueOf(cursor.getInt(0)));
			}

			Integer count = range.upperEndpoint() - range.lowerEndpoint() + 1;
			Integer offset = range.lowerEndpoint();

			cursor = content().query(
					parse(base + "/event"),
					from("id", "headline", "content", "created", "updated"),
					"pid IN (" + makePlaceholders(pids.size()) +")",
					from(pids.toArray(new String[] { })),
					"datetime(created) DESC LIMIT "+ offset + ", " + count);

			if (cursor.moveToFirst()) {
				do {
					events.add(fromCursor(cursor));
				} while (cursor.moveToNext());
			}
		}

		return events;
	}

	@Override
	public Event lastOf(Publisher entity) throws DaoException {
		Cursor cursor = content().query(
				parse(base + "/event"),
				from("id", "headline", "content", "created", "updated"),
				"pid = ?",
				from(entity.getId().toString()),
				"id DESC LIMIT 1"
		);

		Event last = null;

		if (cursor.moveToFirst()) {
			last = fromCursor(cursor);
		}

		return last;
	}

	@Override
	public List<Event> since(Publisher key, Event entity) throws DaoException {
		Cursor cursor = content().query(
				parse(base + "/event"),
				from("id", "headline", "content", "created", "updated"),
				"pid = ? AND id > ?",
				from(entity.getId().toString(), entity.getId().toString()),
				"datetime(created) DESC"
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
					parse(base + "/event"),
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
