package de.ladis.infohm.core.domain;

public class Bookmark {

	private Long id;
	private String url;
	private String title;
	private String description;

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
		return id.intValue();
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof Bookmark) {
			Bookmark other = (Bookmark) object;

			return this.id == other.id;
		}

		return false;
	}

}
