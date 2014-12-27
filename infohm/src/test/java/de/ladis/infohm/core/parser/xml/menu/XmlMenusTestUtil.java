package de.ladis.infohm.core.parser.xml.menu;

import static org.hamcrest.core.Is.*;
import static org.hamcrest.core.IsEqual.*;
import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.List;

import org.joda.time.DateTime;

import de.ladis.infohm.core.domain.Guest;
import de.ladis.infohm.core.domain.Meal;
import de.ladis.infohm.core.domain.Menu;

public class XmlMenusTestUtil {

	public static InputStream validResourceAsStream() {
		return XmlMenusTestUtil.class.getResourceAsStream("menus_valid.xml");
	}

	public static InputStream invalidResourceAsStream() {
		return XmlMenusTestUtil.class.getResourceAsStream("menus_invalid.xml");
	}

	public static InputStream extendedResourceAsStream() {
		return XmlMenusTestUtil.class.getResourceAsStream("menus_extended.xml");
	}

	public static void assertValid(List<Menu> results) {
		Menu expected;
		Meal meal;

		assertThat(results.size(), is(2));

		expected = new Menu();
		expected.setId(Long.valueOf(1l));
		expected.setDate(new DateTime(2014, 12, 27, 0, 0, 0));

		meal = new Meal();
		meal.setId(Long.valueOf(1l));
		meal.setName("meal 1");
		meal.addPrice(Guest.EMPLOYEE, 100);
		meal.addPrice(Guest.STUDENT, 150);
		meal.setCreatedAt(new DateTime(2014, 12, 27, 13, 44, 13));
		meal.setUpdatedAt(new DateTime(2014, 12, 27, 13, 44, 13));

		expected.addMeal(meal);

		meal = new Meal();
		meal.setId(Long.valueOf(3l));
		meal.setName("meal 2");
		meal.addPrice(Guest.EMPLOYEE, 160);
		meal.addPrice(Guest.STUDENT, 170);
		meal.setCreatedAt(new DateTime(2014, 12, 27, 13, 44, 13));
		meal.setUpdatedAt(new DateTime(2014, 12, 27, 13, 44, 13));

		expected.addMeal(meal);

		assertEqual(results.get(0), expected);

		expected = new Menu();
		expected.setId(Long.valueOf(2l));
		expected.setDate(new DateTime(2014, 12, 28, 0, 0, 0));

		meal = new Meal();
		meal.setId(Long.valueOf(2l));
		meal.setName("meal 1");
		meal.addPrice(Guest.EMPLOYEE, 99);
		meal.addPrice(Guest.STUDENT, 250);
		meal.setCreatedAt(new DateTime(2014, 12, 28, 13, 44, 13));
		meal.setUpdatedAt(new DateTime(2014, 12, 28, 13, 44, 13));

		expected.addMeal(meal);

		assertEqual(results.get(1), expected);
	}

	public static void assertEqual(Menu actual, Menu expected) {
		assertThat(actual.getId(), equalTo(expected.getId()));
		assertTrue(actual.getDate().isEqual(expected.getDate()));
		assertThat(actual.getMeals().size(), is(expected.getMeals().size()));

		int count = actual.getMeals().size();
		for (int i = 0; i < count; i++) {
			assertEqual(actual.getMeals().get(i), expected.getMeals().get(i));
		}
	}

	public static void assertEqual(Meal actual, Meal expected) {
		assertThat(actual.getId(), equalTo(expected.getId()));
		assertThat(actual.getName(), equalTo(expected.getName()));
		assertThat(actual.getPrices(), equalTo(expected.getPrices()));
		assertTrue(actual.getCreatedAt().isEqual(expected.getCreatedAt()));
		assertTrue(actual.getUpdatedAt().isEqual(expected.getUpdatedAt()));
	}

}
