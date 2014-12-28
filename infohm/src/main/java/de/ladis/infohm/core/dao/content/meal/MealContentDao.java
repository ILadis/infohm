package de.ladis.infohm.core.dao.content.meal;

import static android.net.Uri.*;
import static de.ladis.infohm.util.Arrays.*;
import static de.ladis.infohm.util.SqliteUtil.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.joda.time.DateTime;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import de.ladis.infohm.core.dao.DaoException;
import de.ladis.infohm.core.dao.content.ContentDao;
import de.ladis.infohm.core.dao.domain.MealDao;
import de.ladis.infohm.core.domain.Cafeteria;
import de.ladis.infohm.core.domain.Guest;
import de.ladis.infohm.core.domain.Meal;
import de.ladis.infohm.core.domain.Menu;

public class MealContentDao extends ContentDao<Long, Menu> implements MealDao {

	private final URI base;

	public MealContentDao(ContentResolver resolver, URI base) {
		super(resolver);

		this.base = base;
	}

	private boolean contains(Menu entitiy) throws DaoException {
		Long key = entitiy.getId();

		if (key == null || key <= 0) {
			return false;
		}

		Cursor cursor = content().query(
				parse(base + "/menu"),
				from("id"),
				"id = ?",
				from(key.toString()),
				null
		);

		return cursor.moveToFirst();
	}

	@Override
	public Menu find(Long key) throws DaoException {
		if (key == null || key <= 0) {
			return null;
		}

		Cursor cursor = content().query(
				parse(base + "/menu"),
				from("id", "date"),
				"id = ?",
				from(key.toString()),
				null
		);

		if (cursor.moveToFirst()) {
			Menu menu = fromCursor(cursor, new Menu());
			menu.addMeals(list(menu));

			return menu;
		} else {
			return null;
		}
	}

	@Override
	public List<Menu> list() throws DaoException {
		Cursor cursor = content().query(
				parse(base + "/menu"),
				from("id", "date"),
				null,
				null,
				"datetime(date) DESC"
		);

		List<Menu> menus = new ArrayList<Menu>(cursor.getCount());

		if (cursor.moveToFirst()) {
			do {
				Menu menu = fromCursor(cursor, new Menu());
				menu.addMeals(list(menu));

				menus.add(menu);
			} while (cursor.moveToNext());
		}

		return menus;
	}

	@Override
	public List<Menu> list(Cafeteria entity) throws DaoException {
		Cursor cursor = content().query(
				parse(base + "/menu"),
				from("id", "date"),
				"cid = ?",
				from(entity.getId().toString()),
				"datetime(date) DESC"
		);

		List<Menu> menus = new ArrayList<Menu>(cursor.getCount());

		if (cursor.moveToFirst()) {
			do {
				Menu menu = fromCursor(cursor, new Menu());
				menu.addMeals(list(menu));

				menus.add(menu);
			} while (cursor.moveToNext());
		}

		return menus;
	}

	private List<Menu> list(Cafeteria entity, DateTime start, DateTime end) {
		Cursor cursor = content().query(
				parse(base + "/menu"),
				from("id", "date"),
				"cid = ? AND date BETWEEN ? AND ?",
				from(entity.getId().toString(), start.toString(), end.toString()),
				"datetime(date) DESC"
		);

		List<Menu> menus = new ArrayList<Menu>(cursor.getCount());

		if (cursor.moveToFirst()) {
			do {
				Menu menu = fromCursor(cursor, new Menu());
				menu.addMeals(list(menu));

				menus.add(menu);
			} while (cursor.moveToNext());
		}

		return menus;
	}

	private List<Meal> list(Menu menu) throws DaoException {
		Cursor cursor = content().query(
				parse(base + "/meal"),
				from("id", "name", "employee", "student", "created", "updated"),
				"mid = ?",
				from(menu.getId().toString()),
				"datetime(created) DESC"
		);

		List<Meal> meals = new ArrayList<Meal>(cursor.getCount());

		if (cursor.moveToFirst()) {
			do {
				meals.add(fromCursor(cursor, new Meal()));
			} while (cursor.moveToNext());
		}

		return meals;
	}

	@Override
	public List<Menu> currentWeekOf(Cafeteria entity) throws DaoException {
		DateTime now = DateTime.now();

		DateTime startOfWeek = now.weekOfWeekyear().roundFloorCopy();
		DateTime endOfWeek = now.weekOfWeekyear().roundCeilingCopy();

		return list(entity, startOfWeek, endOfWeek);
	}

	@Override
	public List<Menu> nextWeekOf(Cafeteria entity) throws DaoException {
		DateTime now = DateTime.now();

		DateTime startOfNextWeek = now.weekOfWeekyear().roundFloorCopy().plusDays(7);
		DateTime endOfNextWeek = now.weekOfWeekyear().roundCeilingCopy().plusDays(7);

		return list(entity, startOfNextWeek, endOfNextWeek);
	}

	@Override
	public void insert(Menu entity) throws DaoException {
		throw new DaoException(this, new UnsupportedOperationException());
	}

	@Override
	public void insert(Cafeteria key, Menu entity) throws DaoException {
		if (contains(entity)) {
			update(entity);
		} else {
			Uri uri = content().insert(
					parse(base + "/menu"),
					toValues(key, entity)
			);

			if (uri != null) {
				Long id = Long.decode(uri.getLastPathSegment());
				entity.setId(id);

				for (Meal meal : entity.getMeals()) {
					insert(entity, meal);
				}
			}
		}
	}

	private void insert(Menu key, Meal entity) {
		Uri uri = content().insert(
				parse(base + "/meal"),
				toValues(key, entity)
		);

		if (uri != null) {
			Long id = Long.decode(uri.getLastPathSegment());
			entity.setId(id);
		}
	}

	@Override
	public void update(Menu entity) throws DaoException {
		// TODO finish update implementation (do we need a cafeteria instance?)
	}

	@Override
	public void delete(Menu entity) throws DaoException {
		content().delete(
				parse(base + "/menu"),
				"id = ?",
				from(entity.getId().toString())
		);

		content().delete(
				parse(base + "/meal"),
				"mid = ?",
				from(entity.getId().toString())
		);
	}

	private static Menu fromCursor(Cursor cursor, Menu menu) {
		Long id = getLong(cursor, "id");
		DateTime date = getDateTime(cursor, "date");

		menu.setId(id);
		menu.setDate(date);

		return menu;
	}

	private static Meal fromCursor(Cursor cursor, Meal meal) {
		Long id = getLong(cursor, "id");
		String name = getString(cursor, "name");
		Integer employee = getInteger(cursor, "employee");
		Integer student = getInteger(cursor, "student");
		DateTime created = getDateTime(cursor, "created");
		DateTime updated = getDateTime(cursor, "updated");

		meal.setId(id);
		meal.setName(name);
		meal.setCreatedAt(created);
		meal.setUpdatedAt(updated);

		if (employee != null) {
			meal.addPrice(Guest.EMPLOYEE, employee);
		}

		if (student != null) {
			meal.addPrice(Guest.STUDENT, student);
		}

		return meal;
	}

	private static ContentValues toValues(Menu entity) {
		ContentValues values = new ContentValues();

		values.put("id", entity.getId());
		values.put("date", entity.getDate().toString());

		return values;
	}

	private static ContentValues toValues(Cafeteria key, Menu entity) {
		ContentValues values = toValues(entity);
		values.put("cid", key.getId());

		return values;
	}

	private static ContentValues toValues(Meal entity) {
		ContentValues values = new ContentValues();

		values.put("id", entity.getId());
		values.put("name", entity.getName());
		values.put("created", entity.getCreatedAt().toString());
		values.put("updated", entity.getUpdatedAt().toString());

		for (Entry<Guest, Integer> price : entity.getPrices().entrySet()) {
			switch (price.getKey()) {
			case EMPLOYEE:
				values.put("employee", price.getValue());
				break;
			case STUDENT:
				values.put("student", price.getValue());
				break;
			}
		}

		return values;
	}

	private static ContentValues toValues(Menu menu, Meal entity) {
		ContentValues values = toValues(entity);
		values.put("mid", menu.getId());

		return values;
	}

}
