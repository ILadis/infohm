package de.ladis.infohm.core.listener;

import java.util.List;

import de.ladis.infohm.core.domain.Search;

public interface SearchListener {

	public void onSearchCompleted(List<Search> results);

}
