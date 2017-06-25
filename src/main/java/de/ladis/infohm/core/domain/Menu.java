package de.ladis.infohm.core.domain;

import static de.ladis.infohm.util.Arrays.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.joda.time.DateTime;

public class Menu implements Entity {

	private Long id;
	private DateTime date;
	private List<Meal> meals;

	public Menu() {
		this.id = null;
		this.date = DateTime.now();
		this.meals = new ArrayList<Meal>();
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setDate(DateTime date) {
		this.date = date;
	}

	public DateTime getDate() {
		return date;
	}

	public void addMeal(Meal meal) {
		this.meals.add(meal);
	}

	public void addMeals(Collection<Meal> meals) {
		for (Meal meal : meals) {
			this.meals.add(meal);
		}
	}

	public List<Meal> getMeals() {
		return meals;
	}

	@Override
	public int hashCode() {
		return id == null ? 0 : id.intValue();
	}

	@Override
	public boolean equals(Object object) {
		if (getClass().isInstance(object)) {
			Menu other = (Menu) object;

			return id != null && id == other.id;
		}

		return false;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		builder.append("Speisen am ")
				.append(getDate().toString("dd.MM.YYYY"))
				.append(":\n");

		builder.append(glue(getMeals(), "\n"));

		return builder.toString();
	}

}
