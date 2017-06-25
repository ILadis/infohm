package de.ladis.infohm.core.domain;

import org.joda.time.DateTime;

import de.ladis.infohm.util.Strings;

public class Cafeteria implements Entity {

	private Long id;
	private String name;
	private Double longitude;
	private Double latitude;
	private DateTime created;
	private DateTime updated;

	public Cafeteria() {
		this.id = null;
		this.name = Strings.empty();
		this.longitude = Double.valueOf(0);
		this.latitude = Double.valueOf(0);
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

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLatitude() {
		return latitude;
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
			Cafeteria other = (Cafeteria) object;

			return id != null && id == other.id;
		}

		return false;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		builder.append(getName());

		return builder.toString();
	}

}
