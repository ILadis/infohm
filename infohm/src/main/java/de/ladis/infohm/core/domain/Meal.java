package de.ladis.infohm.core.domain;

import static java.lang.String.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.joda.time.DateTime;

import de.ladis.infohm.util.Strings;

public class Meal implements Entity {

	private Long id;
	private String name;
	private Map<Guest, Integer> prices;
	private DateTime created;
	private DateTime updated;

	public Meal() {
		this.id = null;
		this.name = Strings.empty();
		this.prices = new HashMap<Guest, Integer>() {

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

		};
		this.created = DateTime.now();
		this.updated = DateTime.now();
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void addPrice(Guest guest, Integer price) {
		this.prices.put(guest, price);
	}

	public Map<Guest, Integer> getPrices() {
		return prices;
	}

	public void setCreatedAt(DateTime created) {
		this.created = created;
	}

	public DateTime getCreatedAt() {
		return created;
	}

	public void setUpdatedAt(DateTime updated) {
		this.updated = updated;
	}

	public DateTime getUpdatedAt() {
		return updated;
	}

	@Override
	public int hashCode() {
		return id == null ? 0 : id.intValue();
	}

	@Override
	public boolean equals(Object object) {
		if (getClass().isInstance(object)) {
			Meal other = (Meal) object;

			return id != null && id == other.id;
		}

		return false;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		builder.append(getName())
				.append('\n')
				.append(getPrices().toString());

		return builder.toString();
	}

}
