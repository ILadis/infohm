package de.ladis.infohm.core.listener;

import java.util.List;

import de.ladis.infohm.core.domain.Cafeteria;
import de.ladis.infohm.core.domain.Menu;

public abstract class SimpleMealListener implements MealListener {

	@Override
	public void onUpdated(Cafeteria cafeteria, List<Menu> menus) {
	}

	@Override
	public void onCurrentWeek(Cafeteria cafeteria, List<Menu> menus) {
	}

	@Override
	public void onNextWeek(Cafeteria cafeteria, List<Menu> menus) {
	}

}
