package de.ladis.infohm.core.parser.domain;

import java.io.InputStream;
import java.util.List;

import de.ladis.infohm.core.domain.Bookmark;
import de.ladis.infohm.core.parser.Parser;

public interface BookmarksParser extends Parser<InputStream, List<Bookmark>> {
}
