package de.ladis.infohm.core.listener;

import java.util.List;

import de.ladis.infohm.core.domain.Cafeteria;
import de.ladis.infohm.core.domain.Menu;

public interface MealListener {

	public void onUpdated(Cafeteria cafeteria, List<Menu> menus);

	public void onCurrentWeek(Cafeteria cafeteria, List<Menu> menus);

	public void onNextWeek(Cafeteria cafeteria, List<Menu> menus);

}
