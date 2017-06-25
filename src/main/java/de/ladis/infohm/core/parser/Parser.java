package de.ladis.infohm.core.parser;

public interface Parser<I, O> {

	public O parse(I input) throws ParserException;

}
