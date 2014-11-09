package de.ladis.infohm.core.domain;

public class Publisher {

	private Long id;
	private String name;
	private String description;

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
		return id.intValue();
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof Publisher) {
			Publisher other = (Publisher) object;

			return this.id == other.id
					&& this.name.equals(other.name)
					&& this.description.equals(other.description);
		}

		return false;
	}

}
