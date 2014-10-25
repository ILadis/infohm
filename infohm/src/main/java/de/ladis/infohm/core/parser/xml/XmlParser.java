package de.ladis.infohm.core.parser.xml;

import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;
import de.ladis.infohm.core.parser.Parser;
import de.ladis.infohm.core.parser.ParserException;
import de.ladis.infohm.util.Closeables;

public abstract class XmlParser<O> implements Parser<InputStream, O> {

	@Override
	public final O parse(InputStream input) throws ParserException {
		XmlPullParser parser = null;

		try {
			parser = Xml.newPullParser();
			parser.setInput(input, null);
		} catch (XmlPullParserException e) {
			throw new ParserException(this, e);
		}

		O output = null;

		try {
			output = parse(parser);
		} finally {
			Closeables.close(input);
		}

		return output;
	}

	protected abstract O parse(XmlPullParser parser) throws ParserException;

}
