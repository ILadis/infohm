package de.ladis.infohm.core.parser.xml.event;

import static de.ladis.infohm.util.XmlParserUtil.*;

import java.io.IOException;

import org.joda.time.DateTime;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import de.ladis.infohm.core.domain.Event;
import de.ladis.infohm.core.parser.domain.EventParser;
import de.ladis.infohm.core.parser.xml.XmlParser;

public class XmlEventParser extends XmlParser<Event> implements EventParser {

	@Override
	protected Event parse(XmlPullParser parser) throws XmlPullParserException, IOException {
		return parseEvent(parser);
	}

	public static Event parseEvent(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, null, "news");

		Long id = null;
		String headline = null;
		String content = null;
		DateTime created = null;
		DateTime updated = null;

		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() == XmlPullParser.END_DOCUMENT) {
				throw new XmlPullParserException("reached unexpected end of document");
			} else if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String tag = parser.getName();

			if ("id".equals(tag)) {
				id = nextLong(parser, "id");
			} else if ("headline".equals(tag)) {
				headline = nextString(parser, "headline");
			} else if ("content".equals(tag)) {
				content = nextString(parser, "content");
			} else if ("createdAt".equals(tag)) {
				created = nextDateTime(parser, "createdAt");
			} else if ("updatedAt".equals(tag)) {
				updated = nextDateTime(parser, "updatedAt");
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

}
