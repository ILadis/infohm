package de.ladis.infohm.core.parser.json;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.gson.stream.JsonReader;

import de.ladis.infohm.core.parser.Parser;
import de.ladis.infohm.core.parser.ParserException;
import de.ladis.infohm.util.Closeables;

public abstract class JsonParser<O> implements Parser<InputStream, O> {

	@Override
	public final O parse(InputStream input) throws ParserException {
		JsonReader reader = null;

		try {
			reader = new JsonReader(new InputStreamReader(input, "UTF-8"));
		} catch (Exception e) {
			throw new ParserException(this, e);
		}

		O output = null;

		try {
			output = parse(reader);
		} catch (Exception e) {
			throw new ParserException(this, e);
		} finally {
			Closeables.close(reader);
			Closeables.close(input);
		}

		return output;
	}

	protected abstract O parse(JsonReader reader) throws IOException;

}
