package de.ladis.infohm.core.domain;

import de.ladis.infohm.util.Strings;

public class Search implements Entity {

	public static Search with(Event event) {
		Search search = new Search();

		search.id = event.getId();
		search.type = "event";
		search.content = event.toString();

		return search;
	}

	public static Search with(Publisher publisher) {
		Search search = new Search();

		search.id = publisher.getId();
		search.type = "publisher";
		search.content = publisher.toString();

		return search;
	}

	public static Search with(Bookmark bookmark) {
		Search search = new Search();

		search.id = bookmark.getId();
		search.type = "bookmark";
		search.content = bookmark.toString();

		return search;
	}

	private Long id;
	private String type;
	private String content;
	private Double priority;

	public Search() {
		this.id = null;
		this.type = Strings.empty();
		this.content = Strings.empty();
		this.priority = Double.valueOf(0);
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setPriority(Double priority) {
		this.priority = priority;
	}

	public Double getPriority() {
		return priority;
	}

	@Override
	public int hashCode() {
		return id == null ? 0 : 31 * id.intValue() + type.hashCode();
	}

	@Override
	public boolean equals(Object object) {
		if (getClass().isInstance(object)) {
			Search other = (Search) object;

			if (id != null && id == other.id) {
				if (type != null && type.equals(other.type)) {
					return true;
				}
			}
		}

		return false;
	}

}
