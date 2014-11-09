package de.ladis.infohm.core.parser.xml;

import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import de.ladis.infohm.core.parser.Parser;
import de.ladis.infohm.core.parser.ParserException;
import de.ladis.infohm.util.Closeables;

public abstract class XmlParser<O> implements Parser<InputStream, O> {

	@Override
	public final O parse(InputStream input) throws ParserException {
		XmlPullParser parser = null;

		try {
			parser = XmlPullParserFactory.newInstance().newPullParser();
			parser.setInput(input, null);
			parser.nextTag();
		} catch (Exception e) {
			throw new ParserException(this, e);
		}

		O output = null;

		try {
			output = parse(parser);
		} catch (Exception e) {
			throw new ParserException(this, e);
		} finally {
			Closeables.close(input);
		}

		return output;
	}

	protected abstract O parse(XmlPullParser parser) throws XmlPullParserException, IOException;

}
