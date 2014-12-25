package de.ladis.infohm.core.domain;

import de.ladis.infohm.util.Strings;

public class Feedback implements Entity {

	private Long id;
	private String subject;
	private String message;
	private Boolean anonymous;

	public Feedback() {
		this.id = null;
		this.subject = Strings.empty();
		this.message = Strings.empty();
		this.anonymous = Boolean.valueOf(false);
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSubject() {
		return subject;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setAnonymous(Boolean anonymous) {
		this.anonymous = anonymous;
	}

	public Boolean isAnonymous() {
		return anonymous;
	}

	@Override
	public int hashCode() {
		return id == null ? 0 : id.intValue();
	}

	@Override
	public boolean equals(Object object) {
		if (getClass().isInstance(object)) {
			Feedback other = (Feedback) object;

			return id != null && id == other.id;
		}

		return false;
	}

}
