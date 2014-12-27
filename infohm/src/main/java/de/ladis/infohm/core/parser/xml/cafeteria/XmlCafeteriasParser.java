package de.ladis.infohm.core.parser.xml.cafeteria;

import static de.ladis.infohm.core.parser.xml.cafeteria.XmlCafeteriaParser.*;
import static de.ladis.infohm.util.XmlParserUtil.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import de.ladis.infohm.core.domain.Cafeteria;
import de.ladis.infohm.core.parser.domain.CafeteriasParser;
import de.ladis.infohm.core.parser.xml.XmlParser;

public class XmlCafeteriasParser extends XmlParser<List<Cafeteria>> implements CafeteriasParser {

	@Override
	protected List<Cafeteria> parse(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, null, "cafeterias");

		List<Cafeteria> cafeterias = new ArrayList<Cafeteria>();

		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() == XmlPullParser.END_DOCUMENT) {
				throw new XmlPullParserException("reached unexpected end of document");
			} else if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String tag = parser.getName();

			if ("cafeteria".equals(tag)) {
				cafeterias.add(parseCafeteria(parser));
			} else {
				skipTag(parser);
			}
		}

		parser.require(XmlPullParser.END_TAG, null, "cafeterias");

		return cafeterias;
	}

}
