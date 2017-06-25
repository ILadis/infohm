package de.ladis.infohm.core.dao.content.feedback;

import static android.net.Uri.*;
import static de.ladis.infohm.util.Arrays.*;
import static de.ladis.infohm.util.SqliteUtil.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import de.ladis.infohm.core.dao.DaoException;
import de.ladis.infohm.core.dao.content.ContentDao;
import de.ladis.infohm.core.dao.domain.FeedbackDao;
import de.ladis.infohm.core.domain.Feedback;

public class FeedbackContentDao extends ContentDao<Long, Feedback> implements FeedbackDao {

	private final URI base;

	public FeedbackContentDao(ContentResolver resolver, URI base) {
		super(resolver);

		this.base = base;
	}

	private boolean contains(Feedback entitiy) throws DaoException {
		return find(entitiy.getId()) != null;
	}

	@Override
	public Feedback find(Long key) throws DaoException {
		if (key == null || key <= 0) {
			return null;
		}

		Cursor cursor = content().query(
				parse(base + "/feedback"),
				from("id", "subject", "message", "anonymous"),
				"id = ?",
				from(key.toString()),
				null
		);

		if (cursor.moveToFirst()) {
			return fromCursor(cursor, new Feedback());
		} else {
			return null;
		}
	}

	@Override
	public List<Feedback> list() throws DaoException {
		Cursor cursor = content().query(
				parse(base + "/feedback"),
				from("id", "subject", "message", "anonymous"),
				null,
				null,
				"id ASC"
		);

		List<Feedback> feedbacks = new ArrayList<Feedback>(cursor.getCount());

		if (cursor.moveToFirst()) {
			do {
				feedbacks.add(fromCursor(cursor, new Feedback()));
			} while (cursor.moveToNext());
		}

		return feedbacks;
	}

	@Override
	public void insert(Feedback entity) throws DaoException {
		if (contains(entity)) {
			update(entity);
		} else {
			Uri uri = content().insert(
					parse(base + "/feedback"),
					toValues(entity)
			);

			if (uri != null) {
				Long id = Long.decode(uri.getLastPathSegment());
				entity.setId(id);
			}
		}
	}

	@Override
	public void update(Feedback entity) throws DaoException {
		content().update(
				parse(base + "/feedback"),
				toValues(entity),
				"id = ?",
				from(entity.getId().toString())
		);
	}

	@Override
	public void delete(Feedback entity) throws DaoException {
		content().delete(
				parse(base + "/feedback"),
				"id = ?",
				from(entity.getId().toString())
		);
	}

	private static Feedback fromCursor(Cursor cursor, Feedback feedback) {
		Long id = getLong(cursor, "id");
		String subject = getString(cursor, "subject");
		String message = getString(cursor, "message");
		Boolean anonymous = getBoolean(cursor, "anonymous");

		feedback.setId(id);
		feedback.setSubject(subject);
		feedback.setMessage(message);
		feedback.setAnonymous(anonymous);

		return feedback;
	}

	private static ContentValues toValues(Feedback entity) {
		ContentValues values = new ContentValues();

		values.put("id", entity.getId());
		values.put("subject", entity.getSubject());
		values.put("message", entity.getMessage());
		values.put("anonymous", entity.isAnonymous());

		return values;
	}

}
