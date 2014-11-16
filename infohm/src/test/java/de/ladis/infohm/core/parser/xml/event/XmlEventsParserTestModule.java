package de.ladis.infohm.core.parser.xml.event;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.ladis.infohm.core.parser.domain.EventsParser;

@Module(
library = true,
injects = {
		XmlEventsParserTest.class
})
public class XmlEventsParserTestModule {

	@Provides
	@Singleton
	public EventsParser provideEventsParser() {
		return new XmlEventsParser();
	}

}
