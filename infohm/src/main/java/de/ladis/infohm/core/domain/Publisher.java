package de.ladis.infohm.core.domain;

import de.ladis.infohm.util.Strings;

public class Publisher extends Entity {

	private Long id;
	private String name;
	private String description;

	public Publisher() {
		this.id = null;
		this.name = Strings.empty();
		this.description = Strings.empty();
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

}
