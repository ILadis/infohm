package de.ladis.infohm.core.parser;

public class ParserException extends RuntimeException {

	private static final long serialVersionUID = 2890425003042404092L;

	public ParserException(Parser<?, ?> parser, Throwable cause) {
		this(parser.getClass().getName() + " could not finish its task", cause);
	}

	public ParserException(String message, Throwable cause) {
		super(message, cause);
	}

}
