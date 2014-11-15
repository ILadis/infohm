package de.ladis.infohm.core.listener;

import java.util.List;

import de.ladis.infohm.core.domain.Publisher;

public abstract class SimplePublisherListener implements PublisherListener {

	@Override
	public void onUpdated(List<Publisher> publishers) {
	}

	@Override
	public void onGathered(List<Publisher> publishers) {
	}

	@Override
	public void onStarred(List<Publisher> publishers) {
	}

}
