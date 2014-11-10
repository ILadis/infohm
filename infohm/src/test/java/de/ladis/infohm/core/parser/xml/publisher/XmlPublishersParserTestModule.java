package de.ladis.infohm.core.parser.xml.publisher;

import java.io.InputStream;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.ladis.infohm.core.parser.domain.PublishersParser;

@Module(
library = true,
injects = {
		XmlPublishersParserTest.class
})
public class XmlPublishersParserTestModule {

	@Provides
	@Singleton
	public InputStream provideInputStream() {
		return XmlPublishersParserTest.class.getResourceAsStream("publishers.xml");
	}

	@Provides
	@Singleton
	public PublishersParser providePublishersParser() {
		return new XmlPublishersParser();
	}

}
