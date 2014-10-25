package de.ladis.infohm.util;

import java.io.Closeable;
import java.io.IOException;

public class Closeables {

	public static void close(Closeable closeable) {
		try {
			closeable.close();
		} catch (IOException e) { }
	}

}
