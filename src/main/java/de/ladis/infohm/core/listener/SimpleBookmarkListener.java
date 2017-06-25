package de.ladis.infohm.core.listener;

import java.util.List;

import de.ladis.infohm.core.domain.Bookmark;

public abstract class SimpleBookmarkListener implements BookmarkListener {

	@Override
	public void onUpdated(List<Bookmark> bookmarks) {
	}

	@Override
	public void onGathered(List<Bookmark> bookmarks) {
	}

}
