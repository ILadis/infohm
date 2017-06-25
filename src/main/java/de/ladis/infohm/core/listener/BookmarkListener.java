package de.ladis.infohm.core.listener;

import java.util.List;

import de.ladis.infohm.core.domain.Bookmark;

public interface BookmarkListener {

	public void onUpdated(List<Bookmark> bookmarks);

	public void onGathered(List<Bookmark> bookmarks);

}
