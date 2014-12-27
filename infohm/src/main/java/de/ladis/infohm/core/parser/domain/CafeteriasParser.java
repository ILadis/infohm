package de.ladis.infohm.core.parser.domain;

import java.io.InputStream;
import java.util.List;

import de.ladis.infohm.core.domain.Cafeteria;
import de.ladis.infohm.core.parser.Parser;

public interface CafeteriasParser extends Parser<InputStream, List<Cafeteria>> {
}
