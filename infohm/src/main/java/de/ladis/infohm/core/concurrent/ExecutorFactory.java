package de.ladis.infohm.core.concurrent;

import java.util.concurrent.ExecutorService;

public interface ExecutorFactory {

	public ExecutorService forLocal();

	public ExecutorService forRemote();

}
