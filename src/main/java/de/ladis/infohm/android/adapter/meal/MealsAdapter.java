package de.ladis.infohm.android.adapter.meal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import de.ladis.infohm.R;
import de.ladis.infohm.core.domain.Meal;

public class MealsAdapter extends RecyclerView.Adapter<MealsAdapter.ViewHolder> {

	protected static class ViewHolder extends RecyclerView.ViewHolder {

		private final TextView indexView;
		private final TextView nameView;
		private final TextView pricesView;

		public ViewHolder(View view) {
			super(view);

			this.indexView = (TextView) view.findViewById(R.id.adapter_meals_index);
			this.nameView = (TextView) view.findViewById(R.id.adapter_meals_name);
			this.pricesView = (TextView) view.findViewById(R.id.adapter_meals_prices);
		}

	}

	private final List<Meal> items;

	public MealsAdapter() {
		this.items = new ArrayList<Meal>();
	}

	public void addItems(Collection<Meal> meals) {
		for (Meal meal : meals) {
			addItem(meal);
		}
	}

	public void addItem(Meal meal) {
		int index = items.indexOf(meal);

		if (index >= 0) {
			items.remove(index);
			items.add(index, meal);

			notifyItemChanged(index);
		} else {
			items.add(meal);

			index = items.indexOf(meal);

			notifyItemInserted(index);
		}
	}

	public void clearItems() {
		items.clear();
		notifyDataSetChanged();
	}

	public Meal getItem(int position) {
		return items.get(position);
	}

	@Override
	public int getItemCount() {
		return items.size();
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		Context context = parent.getContext();

		View view = LayoutInflater.from(context).inflate(R.layout.adapter_meals, parent, false);

		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		Resources resources = holder.itemView.getResources();
		Meal meal = getItem(position);

		holder.indexView.setText(resources.getString(R.string.fragment_meals_index, position+1));
		holder.nameView.setText(meal.getName());
		holder.pricesView.setText(meal.getPrices().toString());
	}

}
