package de.ladis.infohm.core.domain;

import org.joda.time.DateTime;

import de.ladis.infohm.util.Strings;


public class Bookmark implements Entity {

	private Long id;
	private String title;
	private String description;
	private String url;
	private DateTime created;
	private DateTime updated;

	public Bookmark() {
		this.id = null;
		this.title = Strings.empty();
		this.description = Strings.empty();
		this.url = Strings.empty();
		this.created = DateTime.now();
		this.updated = DateTime.now();
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
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
			Bookmark other = (Bookmark) object;

			return id != null && id == other.id;
		}

		return false;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		builder.append(getTitle())
				.append('\n')
				.append(getDescription())
				.append('\n')
				.append(getUrl());

		return builder.toString();
	}

}
