package de.ladis.infohm.core.parser.xml.publisher;

import static org.hamcrest.core.Is.*;
import static org.hamcrest.core.IsEqual.*;
import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.List;

import de.ladis.infohm.core.domain.Publisher;

public class XmlPublishersTestUtil {

	public static InputStream validResourceAsStream() {
		return XmlPublishersTestUtil.class.getResourceAsStream("publishers_valid.xml");
	}

	public static InputStream invalidResourceAsStream() {
		return XmlPublishersTestUtil.class.getResourceAsStream("publishers_invalid.xml");
	}

	public static InputStream extendedResourceAsStream() {
		return XmlPublishersTestUtil.class.getResourceAsStream("publishers_extended.xml");
	}

	public static Publisher newInstance() {
		Publisher publisher = new Publisher();
		publisher.setName("new");
		publisher.setDescription("this is a new publisher that should not exist");

		return publisher;
	}

	public static void assertValid(List<Publisher> results) {
		Publisher expected;

		assertThat(results.size(), is(2));

		expected = new Publisher();
		expected.setId(1l);
		expected.setName("test");
		expected.setDescription("a publisher for testing purposes");

		assertThat(results.get(0), equalTo(expected));

		expected = new Publisher();
		expected.setId(2l);
		expected.setName("yap");
		expected.setDescription("yet another publisher for testing purposes");

		assertThat(results.get(1), equalTo(expected));
	}
}
