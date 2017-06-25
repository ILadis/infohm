package de.ladis.infohm.core.parser.xml.menu;

import static de.ladis.infohm.core.parser.xml.menu.XmlMenuParser.*;
import static de.ladis.infohm.util.XmlParserUtil.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import de.ladis.infohm.core.domain.Menu;
import de.ladis.infohm.core.parser.domain.MenusParser;
import de.ladis.infohm.core.parser.xml.XmlParser;

public class XmlMenusParser extends XmlParser<List<Menu>> implements MenusParser {

	@Override
	protected List<Menu> parse(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, null, "menuEntities");

		List<Menu> menus = new ArrayList<Menu>();

		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() == XmlPullParser.END_DOCUMENT) {
				throw new XmlPullParserException("reached unexpected end of document");
			} else if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String tag = parser.getName();

			if ("menuEntity".equals(tag)) {
				menus.add(parseMenu(parser));
			} else {
				skipTag(parser);
			}
		}

		parser.require(XmlPullParser.END_TAG, null, "menuEntities");

		return menus;
	}

}
