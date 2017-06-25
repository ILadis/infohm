package de.ladis.infohm.core.domain;

import org.joda.time.DateTime;

import de.ladis.infohm.util.Strings;

public class Publisher implements Entity {

	private Long id;
	private String name;
	private String description;
	private DateTime created;
	private DateTime updated;

	public Publisher() {
		this.id = null;
		this.name = Strings.empty();
		this.description = Strings.empty();
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

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
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
			Publisher other = (Publisher) object;

			return id != null && id == other.id;
		}

		return false;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		builder.append(getName())
				.append('\n')
				.append(getDescription());

		return builder.toString();
	}

}
