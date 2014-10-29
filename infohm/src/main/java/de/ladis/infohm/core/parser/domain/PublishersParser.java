package de.ladis.infohm.core.parser.domain;

import java.io.InputStream;
import java.util.List;

import de.ladis.infohm.core.domain.Publisher;
import de.ladis.infohm.core.parser.Parser;

public interface PublishersParser extends Parser<InputStream, List<Publisher>> {
}
