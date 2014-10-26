package de.ladis.infohm.core.dao.content;

import android.content.ContentResolver;
import de.ladis.infohm.core.dao.Dao;

public abstract class ContentDao<K, E> implements Dao<K, E> {

	private final ContentResolver resolver;

	public ContentDao(ContentResolver resolver) {
		this.resolver = resolver;
	}

	public ContentResolver content() {
		return resolver;
	}

}
