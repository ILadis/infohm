package de.ladis.infohm.core.dao;

public class DaoException extends RuntimeException {

	private static final long serialVersionUID = 2890425003042404092L;

	public DaoException(Dao<?, ?> dao, Throwable cause) {
		this(dao.getClass().getName() + " could not finish its task", cause);
	}

	public DaoException(String message, Throwable cause) {
		super(message, cause);
	}

}
