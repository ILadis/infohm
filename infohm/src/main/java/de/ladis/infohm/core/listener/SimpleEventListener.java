package de.ladis.infohm.core.listener;

import java.util.List;

import de.ladis.infohm.core.domain.Event;
import de.ladis.infohm.core.domain.Publisher;

public abstract class SimpleEventListener implements EventListener {

	@Override
	public void onUpdated(Publisher publisher, List<Event> events) {
	}

	@Override
	public void onGathered(List<Event> events) {
	}

	@Override
	public void onGathered(Publisher publisher, List<Event> events) {
	}

}
