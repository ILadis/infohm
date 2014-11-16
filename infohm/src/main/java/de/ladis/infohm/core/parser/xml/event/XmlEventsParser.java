package de.ladis.infohm.core.parser.xml.event;

import static de.ladis.infohm.util.XmlParserUtil.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import de.ladis.infohm.core.domain.Event;
import de.ladis.infohm.core.parser.domain.EventsParser;
import de.ladis.infohm.core.parser.xml.XmlParser;

public class XmlEventsParser extends XmlParser<List<Event>> implements EventsParser {

	@Override
	protected List<Event> parse(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, null, "newss");

		List<Event> events = new ArrayList<Event>();

		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String tag = parser.getName();

			if ("news".equals(tag)) {
				events.add(parseEvent(parser));
			} else {
				skipTag(parser);
			}
		}

		parser.require(XmlPullParser.END_TAG, null, "newss");

		return events;
	}

	private static Event parseEvent(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, null, "news");

		Long id = null;
		String headline = null;
		String content = null;
		DateTime created = null;
		DateTime updated = null;

		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String tag = parser.getName();

			if ("id".equals(tag)) {
				id = parseId(parser);
			} else if ("headline".equals(tag)) {
				headline = parseHeadline(parser);
			} else if ("content".equals(tag)) {
				content = parseContent(parser);
			} else if ("createdAt".equals(tag)) {
				created = parseCreatedAt(parser);
			} else if ("updatedAt".equals(tag)) {
				updated = parseUpdatedAt(parser);
			} else {
				skipTag(parser);
			}
		}

		Event event = new Event();
		event.setId(id);
		event.setHeadline(headline);
		event.setContent(content);
		event.setCreatedAt(created);
		event.setUpdatedAt(updated);

		parser.require(XmlPullParser.END_TAG, null, "news");

		return event;
	}

	private static String parseHeadline(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, null, "headline");

		String value = null;

		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.TEXT) {
				continue;
			}
			String text = parser.getText();

			value = text;
		}

		String headline = textOf(value);

		parser.require(XmlPullParser.END_TAG, null, "headline");

		return headline;
	}

	private static String parseContent(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, null, "content");

		String value = null;

		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.TEXT) {
				continue;
			}
			String text = parser.getText();

			value = text;
		}

		String content = textOf(value);

		parser.require(XmlPullParser.END_TAG, null, "content");

		return content;
	}

}
