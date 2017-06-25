package de.ladis.infohm.core.domain;

import org.joda.time.DateTime;

import de.ladis.infohm.util.Strings;

public class Event implements Entity {

	private Long id;
	private String headline;
	private String content;
	private DateTime created;
	private DateTime updated;

	public Event() {
		this.id = null;
		this.headline = Strings.empty();
		this.content = Strings.empty();
		this.created = DateTime.now();
		this.updated = DateTime.now();
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setHeadline(String headline) {
		this.headline = headline;
	}

	public String getHeadline() {
		return headline;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
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
			Event other = (Event) object;

			return id != null && id == other.id;
		}

		return false;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		builder.append(getHeadline())
				.append('\n')
				.append(getContent());

		return builder.toString();
	}

}
