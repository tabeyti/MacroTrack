package com.tabeyti.macrotrack;

import com.tabeyti.macrotrack.R;
import com.tabeyti.macrotrack.shared.FoodEntries;
import com.tabeyti.macrotrack.shared.Measurement;

import android.widget.TextView;

public class DayEntrySummaryBar 
{
	private DayEntryPage parent;
	
	
	public DayEntrySummaryBar(DayEntryPage parent)
	{
		this.parent = parent;
				
	} // end DayEntrySummaryBar()
	
	
	public void update(FoodEntries fes)
	{
		TextView cals = (TextView)parent.view().findViewById(R.id.dayentry_summarycalories);
		TextView prot = (TextView)parent.view().findViewById(R.id.dayentry_summaryprotein);
		TextView carbs = (TextView)parent.view().findViewById(R.id.dayentry_summarycarbohydrates);
		TextView fat = (TextView)parent.view().findViewById(R.id.dayentry_summaryfat);
		
		cals.setText(Double.toString(fes.calculateTotal(Measurement.CALORIES))); 
		prot.setText(Double.toString(fes.calculateTotal(Measurement.PROTEIN)) + " g");
		carbs.setText(Double.toString(fes.calculateTotal(Measurement.CARBOHYDRATES)) + " g");
		fat.setText(Double.toString(fes.calculateTotal(Measurement.FAT)) + " g");
		
	} // end update()
	

} // end class DayEntrySummaryBar
