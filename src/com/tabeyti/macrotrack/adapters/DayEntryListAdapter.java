package com.tabeyti.macrotrack.adapters;

import java.util.List;

import com.tabeyti.macrotrack.R;
import com.tabeyti.macrotrack.shared.FoodEntry;
import com.tabeyti.macrotrack.shared.Measurement;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class DayEntryListAdapter extends ArrayAdapter<FoodEntry>
{
	public DayEntryListAdapter(Context context, int textViewResourceId)
	{
		super(context, textViewResourceId);
	}
	
	public DayEntryListAdapter(Context context, int resource, List<FoodEntry> items)
	{
		super(context, resource, items);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		View v = convertView;
		
		
		if (null == v)
		{
			LayoutInflater vi;
			vi = LayoutInflater.from(getContext());
			v = vi.inflate(R.layout.dayentry_row,  null);
		}
		
		FoodEntry fe = getItem(position);
		if (null == fe)
		{
			return v;
		}
		
		
		TextView food = (TextView)v.findViewById(R.id.dayentry_food);
		TextView quantity = (TextView)v.findViewById(R.id.dayentry_rowfoodquantity);
		
		TextView v1 = (TextView)v.findViewById(R.id.dayentry_calories);
		TextView v2 = (TextView)v.findViewById(R.id.dayentry_protein);
		TextView v3 = (TextView)v.findViewById(R.id.dayentry_carbohydrates);
		TextView v4 = (TextView)v.findViewById(R.id.dayentry_fat);
		
		if (food != null) {food.setText(fe.getName());}
		if (quantity != null)
		{
			quantity.setText(Double.toString(fe.getUnits()) + " " + fe.getUnit());
		}
		if (v1 != null) {v1.setText(Double.toString(fe.getMeasurement(Measurement.CALORIES)));}
		if (v2 != null) {v2.setText(Double.toString(fe.getMeasurement(Measurement.PROTEIN)));}
		if (v3 != null) {v3.setText(Double.toString(fe.getMeasurement(Measurement.CARBOHYDRATES)));}
		if (v4 != null) {v4.setText(Double.toString(fe.getMeasurement(Measurement.FAT)));}		
		
		return v;
		
	}

} // end class ListAdapter
