package de.ladis.infohm.core.listener;

import java.util.List;

import de.ladis.infohm.core.domain.Publisher;

public interface PublisherListener {

	public void onUpdated(List<Publisher> publishers);

	public void onGathered(List<Publisher> publishers);

	public void onStarred(List<Publisher> publishers);

}
