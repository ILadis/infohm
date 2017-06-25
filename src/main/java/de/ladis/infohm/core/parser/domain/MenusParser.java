package de.ladis.infohm.core.parser.domain;

import java.io.InputStream;
import java.util.List;

import de.ladis.infohm.core.domain.Menu;
import de.ladis.infohm.core.parser.Parser;

public interface MenusParser extends Parser<InputStream, List<Menu>> {
}
