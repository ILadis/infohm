package de.ladis.infohm.core.dao.content.search;

import static android.net.Uri.*;
import static de.ladis.infohm.util.Arrays.*;

import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import de.ladis.infohm.core.dao.DaoException;
import de.ladis.infohm.core.dao.content.ContentDao;
import de.ladis.infohm.core.dao.domain.SearchDao;
import de.ladis.infohm.core.domain.Search;

public class SearchContentDao extends ContentDao<Void, Search> implements SearchDao {

	private final URI base;

	public SearchContentDao(ContentResolver resolver, URI base) {
		super(resolver);

		this.base = base;
	}

	private boolean contains(Search entity) throws DaoException {
		Cursor cursor = content().query(
				parse(base + "/search"),
				from("id"),
				"id = ? AND type = ?",
				from(entity.getId().toString(), entity.getType()),
				null
		);

		return cursor.getCount() != 0;
	}

	@Override
	public Search find(Void key) throws DaoException {
		throw new DaoException(this, new UnsupportedOperationException());
	}

	@Override
	public List<Search> list() throws DaoException {
		Cursor cursor = content().query(
				parse(base + "/search"),
				from("id", "type", "content"),
				null,
				null,
				"id ASC"
		);

		List<Search> searches = new ArrayList<Search>(cursor.getCount());

		if (cursor.moveToFirst()) {
			do {
				searches.add(fromCursor(cursor));
			} while (cursor.moveToNext());
		}

		return searches;
	}

	@Override
	public void insert(Search entity) throws DaoException {
		if (contains(entity)) {
			update(entity);
		} else {
			content().insert(
					parse(base + "/search"),
					toValues(entity)
			);
		}
	}

	@Override
	public void update(Search entity) throws DaoException {
		content().update(
				parse(base + "/search"),
				toValues(entity),
				"id = ? AND type = ?",
				from(entity.getId().toString(), entity.getType())
		);
	}

	@Override
	public List<Search> search(String query) {
		Cursor cursor = content().query(
				parse(base + "/search"),
				from("id", "type", "content", "matchinfo(search,'pnalx')"),
				"content MATCH ?",
				from(query),
				"id ASC"
		);

		List<Search> searches = new ArrayList<Search>(cursor.getCount());

		if (cursor.moveToFirst()) {
			do {
				searches.add(fromCursor(cursor));
			} while (cursor.moveToNext());
		}

		return searches;
	}

	@Override
	public void delete(Search entity) throws DaoException {
		content().delete(
				parse(base + "/search"),
				"id = ? AND type = ?",
				from(entity.getId().toString(), entity.getType())
		);
	}

	private static Search fromCursor(Cursor cursor) {
		Long id = cursor.getLong(cursor.getColumnIndex("id"));
		String type = cursor.getString(cursor.getColumnIndex("type"));
		String content = cursor.getString(cursor.getColumnIndex("content"));
		Double priority = priorityOf(cursor.getBlob(3));

		Search search = new Search();
		search.setId(id);
		search.setType(type);
		search.setContent(content);
		search.setPriority(priority);

		return search;
	}

	private static Double priorityOf(byte[] matchinfo) {
		ByteBuffer buffer = ByteBuffer.wrap(matchinfo);
		buffer.order(ByteOrder.LITTLE_ENDIAN);

		// calculate priority with okapi bm25 algorithm
		int termCount = buffer.getInt(0 * 4);
		int searchCol = 3;

		double totalDocs = buffer.getInt(1 * 4);
		double avgLength = buffer.getInt(4 * 4);
		double docLength = buffer.getInt(8 * 4);

		double idfE = 0.0001d;
		double priority = 0;

		for (int i = 0; i < termCount; i++) {
			double termFrequency = buffer.getInt(10 * 4 + i * searchCol * 4 * 4 + 6 * 4);
			double docsWithTerm = buffer.getInt(10 * 4 + i * searchCol * 4 * 4 + 8 * 4);

			double idf = Math.log(
					(totalDocs - docsWithTerm + 0.5d) /
					(docsWithTerm + 0.5d)
			);

			if (idf < idfE) {
				idf = idfE;
			}

			double rightSide = (
					(termFrequency * 2.2d) /
					(termFrequency + (1.2d * (1 - 0.75d + (0.75d * (docLength / avgLength)))))
			);

			priority += idf * rightSide;
		}

		return priority;
	}

	private static ContentValues toValues(Search entity) {
		ContentValues values = new ContentValues();

		values.put("id", entity.getId());
		values.put("type", entity.getType());
		values.put("content", entity.getContent());

		return values;
	}

}
