package de.ladis.infohm.core.domain;

import de.ladis.infohm.util.Strings;


public class Bookmark extends Entity {

	private Long id;
	private String url;
	private String title;
	private String description;

	public Bookmark() {
		this.id = null;
		this.url = Strings.empty();
		this.title = Strings.empty();
		this.description = Strings.empty();
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
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

}
