package de.ladis.infohm.core.listener;

import java.util.List;

import de.ladis.infohm.core.domain.Search;

public abstract class SimpleSearchListener implements SearchListener {

	@Override
	public void onSearchCompleted(List<Search> results) {
	}

}
