package de.ladis.infohm.core.domain;

public enum Guest {
	EMPLOYEE, STUDENT;

	@Override
	public String toString() {
		switch (this) {
		case EMPLOYEE:
			return "Angestellter";
		case STUDENT:
			return "Student";
		default:
			return null;
		}
	}

}
