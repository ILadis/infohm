package de.ladis.infohm.core.parser.domain;

import java.io.InputStream;
import java.util.List;

import de.ladis.infohm.core.domain.Event;
import de.ladis.infohm.core.parser.Parser;

public interface EventsParser extends Parser<InputStream, List<Event>> {
}
