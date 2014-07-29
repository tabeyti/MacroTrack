package com.tabeyti.macrotrack.adapters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.tabeyti.macrotrack.R;
import com.tabeyti.macrotrack.shared.Food;
import com.tabeyti.macrotrack.shared.Measurement;

public class FoodListAdapter extends ArrayAdapter<Food> implements SectionIndexer
{
	private HashMap<String, Integer> alphaIndexer;
	private String[] sections;
	
	public FoodListAdapter(Context context, int textViewResourceId)
	{
		super(context, textViewResourceId);
	}
	
	public FoodListAdapter(Context context, int resource, List<Food> items)
	{
		super(context, resource, items);
		alphaIndexer = new HashMap<String, Integer>();
		for (int i = 0; i < items.size(); ++i)
		{
			String s = items.get(i).getName().substring(0,1).toUpperCase();
			if (!alphaIndexer.containsKey(s))
				alphaIndexer.put(s,  i);
		}
		
		Set<String> sectionLetters = alphaIndexer.keySet();
		ArrayList<String> sectionList = new ArrayList<String>(sectionLetters);
		Collections.sort(sectionList);
		sections = new String[sectionList.size()];
		for (int i = 0; i < sectionList.size(); ++i)
			sections[i] = sectionList.get(i);			
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		View v = convertView;
		
		if (null == v)
		{
			LayoutInflater vi;
			vi = LayoutInflater.from(getContext());
			v = vi.inflate(R.layout.foodlist_row,  null);
		}
		
		Food fe = getItem(position);
		if (null == fe)
		{
			return v;
		}
		
		TextView food = (TextView)v.findViewById(R.id.foodlist_food);
		TextView unit = (TextView)v.findViewById(R.id.foodlist_rowfoodunit);
		
		TextView v1 = (TextView)v.findViewById(R.id.foodlist_calories);
		TextView v2 = (TextView)v.findViewById(R.id.foodlist_protein);
		TextView v3 = (TextView)v.findViewById(R.id.foodlist_carbohydrates);
		TextView v4 = (TextView)v.findViewById(R.id.foodlist_fat);
		
		if (food != null) {food.setText(fe.getName());}
		if (unit != null) {unit.setText(fe.getUnit());}
		if (v1 != null) {v1.setText(Double.toString(fe.getMeasurement(Measurement.CALORIES)));}
		if (v2 != null) {v2.setText(Double.toString(fe.getMeasurement(Measurement.PROTEIN)));}
		if (v3 != null) {v3.setText(Double.toString(fe.getMeasurement(Measurement.CARBOHYDRATES)));}
		if (v4 != null) {v4.setText(Double.toString(fe.getMeasurement(Measurement.FAT)));}		
		
		return v;
		
	}
	
	public int getPositionForSection(int section)
	{
		return alphaIndexer.get(sections[section]);
	}
	
	public int getSectionForPosition(int position)
	{
		return 1;
	}
	
	public Object[] getSections()
	{
		return sections;
	}
	

} // end class ListAdapter
