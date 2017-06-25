package de.ladis.infohm.android.parcel.meal;

import java.util.List;
import java.util.Map.Entry;

import org.joda.time.DateTime;

import android.os.Parcel;
import android.os.Parcelable;
import de.ladis.infohm.android.parcel.ParcelHolder;
import de.ladis.infohm.core.domain.Guest;
import de.ladis.infohm.core.domain.Meal;
import de.ladis.infohm.core.domain.Menu;

public class MealsParcelHolder extends ParcelHolder<Menu> {

	public static final Parcelable.Creator<MealsParcelHolder> CREATOR = new Parcelable.Creator<MealsParcelHolder>() {

		@Override
		public MealsParcelHolder createFromParcel(Parcel parcel) {
			return new MealsParcelHolder(parcel);
		}

		@Override
		public MealsParcelHolder[] newArray(int size) {
			return new MealsParcelHolder[size];
		}

	};

	public MealsParcelHolder(Menu entity) {
		super(entity);
	}

	public MealsParcelHolder(Parcel parcel) {
		super(parcel);
	}

	@Override
	protected void writeToParcel(Menu entity, Parcel parcel) {
		parcel.writeLong(entity.getId());
		parcel.writeString(entity.getDate().toString());

		List<Meal> meals = entity.getMeals();
		parcel.writeInt(meals.size());

		for (Meal meal : meals) {
			parcel.writeLong(meal.getId());
			parcel.writeString(meal.getName());
			parcel.writeInt(meal.getPrices().size());

			for (Entry<Guest, Integer> price : meal.getPrices().entrySet()) {
				parcel.writeString(price.getKey().name());
				parcel.writeInt(price.getValue());
			}

			parcel.writeString(meal.getCreatedAt().toString());
			parcel.writeString(meal.getUpdatedAt().toString());
		}
	}

	@Override
	protected Menu readFromParcel(Parcel parcel) {
		Menu entity = new Menu();

		entity.setId(parcel.readLong());
		entity.setDate(DateTime.parse(parcel.readString()));

		int size = parcel.readInt();
		for (int i = 0; i < size; i++) {
			Meal meal = new Meal();
			meal.setId(parcel.readLong());
			meal.setName(parcel.readString());

			int prices = parcel.readInt();
			for (int j = 0; j < prices; j++) {
				Guest guest = Guest.valueOf(parcel.readString());
				Integer price = parcel.readInt();

				meal.addPrice(guest, price);
			}

			meal.setCreatedAt(DateTime.parse(parcel.readString()));
			meal.setUpdatedAt(DateTime.parse(parcel.readString()));

			entity.addMeal(meal);
		}

		return entity;
	}

}
