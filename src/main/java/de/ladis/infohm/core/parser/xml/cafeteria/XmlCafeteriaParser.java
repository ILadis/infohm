package de.ladis.infohm.core.parser.xml.cafeteria;

import static de.ladis.infohm.util.XmlParserUtil.*;

import java.io.IOException;

import org.joda.time.DateTime;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import de.ladis.infohm.core.domain.Cafeteria;
import de.ladis.infohm.core.parser.domain.CafeteriaParser;
import de.ladis.infohm.core.parser.xml.XmlParser;

public class XmlCafeteriaParser extends XmlParser<Cafeteria> implements CafeteriaParser {

	@Override
	protected Cafeteria parse(XmlPullParser parser) throws XmlPullParserException, IOException {
		return parseCafeteria(parser);
	}

	public static Cafeteria parseCafeteria(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, null, "cafeteria");

		Long id = null;
		String name = null;
		Double longitude = null;
		Double latitude = null;
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
			} else if ("name".equals(tag)) {
				name = nextString(parser, "name");
			} else if ("longitude".equals(tag)) {
				longitude = nextDouble(parser, "longitude");
			} else if ("latitude".equals(tag)) {
				latitude = nextDouble(parser, "latitude");
			} else if ("createdAt".equals(tag)) {
				created = nextDateTime(parser, "createdAt");
			} else if ("updatedAt".equals(tag)) {
				updated = nextDateTime(parser, "updatedAt");
			} else {
				skipTag(parser);
			}
		}

		Cafeteria cafeteria = new Cafeteria();
		cafeteria.setId(id);
		cafeteria.setName(name);
		cafeteria.setLongitude(longitude);
		cafeteria.setLatitude(latitude);
		cafeteria.setCreatedAt(created);
		cafeteria.setUpdatedAt(updated);

		parser.require(XmlPullParser.END_TAG, null, "cafeteria");

		return cafeteria;
	}

}
