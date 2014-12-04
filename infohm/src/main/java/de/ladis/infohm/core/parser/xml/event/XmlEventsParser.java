package de.ladis.infohm.core.parser.xml.event;

import static de.ladis.infohm.core.parser.xml.event.XmlEventParser.*;
import static de.ladis.infohm.util.XmlParserUtil.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
			if (parser.getEventType() == XmlPullParser.END_DOCUMENT) {
				throw new XmlPullParserException("reached unexpected end of document");
			} else if (parser.getEventType() != XmlPullParser.START_TAG) {
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

}
