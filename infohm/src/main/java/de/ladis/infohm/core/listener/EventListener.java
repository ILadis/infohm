package de.ladis.infohm.core.listener;

import java.util.List;

import de.ladis.infohm.core.domain.Event;
import de.ladis.infohm.core.domain.Publisher;

public interface EventListener {

	public void onUpdated(Publisher publisher, List<Event> events);

	public void onGathered(List<Event> events);

	public void onGathered(Publisher publisher, List<Event> events);

}
