package de.ladis.infohm.core.domain;

import org.joda.time.DateTime;

public class Entity {

	protected DateTime created;
	protected DateTime updated;

	public Entity() {
		this.created = DateTime.now();
		this.updated = DateTime.now();
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

}
