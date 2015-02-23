package de.ladis.infohm.core.dao.content.cafeteria;

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
import de.ladis.infohm.core.dao.DaoException;
import de.ladis.infohm.core.dao.content.ContentDao;
import de.ladis.infohm.core.dao.domain.CafeteriaDao;
import de.ladis.infohm.core.domain.Cafeteria;

public class CafeteriaContentDao extends ContentDao<Long, Cafeteria> implements CafeteriaDao {

	private final URI base;

	public CafeteriaContentDao(ContentResolver resolver, URI base) {
		super(resolver);

		this.base = base;
	}

	private boolean contains(Cafeteria entitiy) throws DaoException {
		return find(entitiy.getId()) != null;
	}

	@Override
	public Cafeteria find(Long key) throws DaoException {
		if (key == null || key <= 0) {
			return null;
		}

		Cursor cursor = content().query(
				parse(base + "/cafeteria"),
				from("id", "name", "longitude", "latitude", "created", "updated"),
				"id = ?",
				from(key.toString()),
				null
		);

		if (cursor.moveToFirst()) {
			return fromCursor(cursor, new Cafeteria());
		} else {
			return null;
		}
	}

	@Override
	public List<Cafeteria> list() throws DaoException {
		Cursor cursor = content().query(
				parse(base + "/cafeteria"),
				from("id", "name", "longitude", "latitude", "created", "updated"),
				null,
				null,
				"name ASC"
		);

		List<Cafeteria> cafeterias = new ArrayList<Cafeteria>(cursor.getCount());

		if (cursor.moveToFirst()) {
			do {
				cafeterias.add(fromCursor(cursor, new Cafeteria()));
			} while (cursor.moveToNext());
		}

		return cafeterias;
	}

	@Override
	public void insert(Cafeteria entity) throws DaoException {
		if (contains(entity)) {
			update(entity);
		} else {
			content().insert(
					parse(base + "/cafeteria"),
					toValues(entity)
			);
		}
	}

	@Override
	public void update(Cafeteria entity) throws DaoException {
		content().update(
				parse(base + "/cafeteria"),
				toValues(entity),
				"id = ?",
				from(entity.getId().toString())
		);
	}

	@Override
	public void delete(Cafeteria entity) throws DaoException {
		content().delete(
				parse(base + "/cafeteria"),
				"id = ?",
				from(entity.getId().toString())
		);
	}

	private static Cafeteria fromCursor(Cursor cursor, Cafeteria cafeteria) {
		Long id = getLong(cursor, "id");
		String name = getString(cursor, "name");
		Double longitude = getDouble(cursor, "longitude");
		Double latitude = getDouble(cursor, "latitude");
		DateTime created = getDateTime(cursor, "created");
		DateTime updated = getDateTime(cursor, "updated");

		cafeteria.setId(id);
		cafeteria.setName(name);
		cafeteria.setLongitude(longitude);
		cafeteria.setLatitude(latitude);
		cafeteria.setCreatedAt(created);
		cafeteria.setUpdatedAt(updated);

		return cafeteria;
	}

	private static ContentValues toValues(Cafeteria entity) {
		ContentValues values = new ContentValues();

		values.put("id", entity.getId());
		values.put("name", entity.getName());
		values.put("longitude", entity.getLongitude());
		values.put("latitude", entity.getLatitude());
		values.put("created", entity.getCreatedAt().toString());
		values.put("updated", entity.getUpdatedAt().toString());

		return values;
	}

}
