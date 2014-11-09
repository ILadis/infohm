package de.ladis.infohm.core.parser.xml.publisher;

import static org.hamcrest.core.Is.*;
import static org.hamcrest.core.IsEqual.*;
import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import de.ladis.infohm.core.domain.Publisher;
import de.ladis.infohm.core.parser.domain.PublishersParser;
import de.ladis.infohm.core.parser.xml.publisher.XmlPublishersParser;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class  XmlPublishersParserTest {

	@Test
	public void parserShouldParseWellFormedXmlSuccessful() throws Exception {
		InputStream stream =  XmlPublishersParserTest.class.getResourceAsStream("publishers.xml");
		PublishersParser parser = new XmlPublishersParser();

		Publisher expected = new Publisher();
		expected.setId(1l);
		expected.setName("test");
		expected.setDescription("a publisher for testing purposes");

		List<Publisher> results = parser.parse(stream);

		assertThat(results.size(), is(1));
		assertThat(results.get(0), equalTo(expected));
	}

}
