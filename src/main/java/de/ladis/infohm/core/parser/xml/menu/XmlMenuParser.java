package de.ladis.infohm.core.parser.xml.menu;

import static de.ladis.infohm.util.XmlParserUtil.*;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import de.ladis.infohm.core.domain.Guest;
import de.ladis.infohm.core.domain.Meal;
import de.ladis.infohm.core.domain.Menu;
import de.ladis.infohm.core.parser.domain.MenuParser;
import de.ladis.infohm.core.parser.xml.XmlParser;

public class XmlMenuParser extends XmlParser<Menu> implements MenuParser {

	@Override
	protected Menu parse(XmlPullParser parser) throws XmlPullParserException, IOException {
		return parseMenu(parser);
	}

	public static Menu parseMenu(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, null, "menuEntity");

		Menu menu = new Menu();

		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() == XmlPullParser.END_DOCUMENT) {
				throw new XmlPullParserException("reached unexpected end of document");
			} else if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String tag = parser.getName();

			if ("id".equals(tag)) {
				menu.setId(nextLong(parser, "id"));
			} else if ("day".equals(tag)) {
				menu.setDate(nextDateTime(parser, "day"));
			} else if ("meals".equals(tag)) {
				menu.addMeal(parseMeal(parser));
			} else {
				skipTag(parser);
			}
		}

		parser.require(XmlPullParser.END_TAG, null, "menuEntity");

		return menu;
	}

	private static Meal parseMeal(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, null, "meals");

		Meal meal = new Meal();

		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() == XmlPullParser.END_DOCUMENT) {
				throw new XmlPullParserException("reached unexpected end of document");
			} else if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String tag = parser.getName();

			if ("id".equals(tag)) {
				meal.setId(nextLong(parser, "id"));
			} else if ("name".equals(tag)) {
				meal.setName(nextString(parser, "name"));
			} else if ("priceEmployee".equals(tag)) {
				meal.addPrice(Guest.EMPLOYEE, parsePrice(parser, "priceEmployee"));
			} else if ("priceStudent".equals(tag)) {
				meal.addPrice(Guest.STUDENT, parsePrice(parser, "priceStudent"));
			} else if ("createdAt".equals(tag)) {
				meal.setCreatedAt(nextDateTime(parser, "createdAt"));
			} else if ("updatedAt".equals(tag)) {
				meal.setUpdatedAt(nextDateTime(parser, "updatedAt"));
			} else {
				skipTag(parser);
			}
		}

		parser.require(XmlPullParser.END_TAG, null, "meals");

		return meal;
	}

	private static Integer parsePrice(XmlPullParser parser, String tag) throws XmlPullParserException, IOException {
		String value = nextString(parser, tag)
				.replace("-", "00")
				.replaceAll("[^0-9]", "");

		return Integer.parseInt(value);
	}

}
