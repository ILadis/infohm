package de.ladis.infohm.core.parser.domain;

import java.io.InputStream;

import de.ladis.infohm.core.domain.Event;
import de.ladis.infohm.core.parser.Parser;

public interface EventParser extends Parser<InputStream, Event> {
}
