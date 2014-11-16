package de.ladis.infohm.core.domain;

import de.ladis.infohm.util.Strings;

public class Event extends Entity {

	private Long id;
	private String headline;
	private String content;

	public Event() {
		this.id = Long.valueOf(0);
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
		return id.intValue();
	}

	@Override
	public boolean equals(Object object) {
		if (this.getClass().isInstance(object)) {
			Event other = (Event) object;

			return this.id == other.id
					&& this.headline.equals(other.headline)
					&& this.content.equals(other.content)
					&& this.created.isEqual(other.created)
					&& this.updated.isEqual(other.updated);
		}

		return false;
	}

}
