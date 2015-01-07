package de.ladis.infohm.core.domain;

import static java.lang.String.*;

import java.util.HashMap;
import java.util.Iterator;

public class Prices extends HashMap<Guest, Integer> {

	private static final long serialVersionUID = -1753014284150234619L;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		Iterator<Entry<Guest, Integer>> iterator = entrySet().iterator();

		while (iterator.hasNext()) {
			Entry<Guest, Integer> price = iterator.next();

			builder.append(price.getKey().toString())
					.append(": ")
					.append(format("%.2f", price.getValue() / 100f))
					.append(" €");

			if (iterator.hasNext()) {
				builder.append(", ");
			}
		}

		return builder.toString();
	}

}
