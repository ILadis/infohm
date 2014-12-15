package de.ladis.infohm.core.domain;

import de.ladis.infohm.util.Strings;

public class Event extends Entity {

	private Long id;
	private String headline;
	private String content;

	public Event() {
		this.id = null;
		this.headline = Strings.empty();
		this.content = Strings.empty();
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

}
