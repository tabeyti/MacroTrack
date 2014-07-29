package com.tabeyti.macrotrack;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.tabeyti.macrotrack.R;
import com.tabeyti.macrotrack.shared.DataManager;
import com.tabeyti.macrotrack.shared.Food;
import com.tabeyti.macrotrack.shared.FoodEntry;
import com.tabeyti.macrotrack.shared.FoodList;
import com.tabeyti.macrotrack.shared.Measurement;

/**
 * @author abeyti_t
 *
 */
public class DayEntryDialogFragment extends DialogFragment
{
	private IEntryControl nodeList;
	private View view;
	private FoodDialogType type;
	private FoodEntry fe = null;
	private Spinner spinner;
	private CheckBox customCheckBox;
	
	private FoodList foodList;
	
	private List<EditText> measurementBoxes = new ArrayList<EditText>();
	private EditText unitsField;
	private EditText unitField;
	private EditText foodNameField;
	private ArrayAdapter<Food> foodAdapter;
	
	
	public DayEntryDialogFragment() {}	
	
	/**
	 * CONSTRUCTOR
	 * @param title
	 * @return
	 */
	public static DayEntryDialogFragment newaddinstance(int title)
	{				
		DayEntryDialogFragment dlg = new DayEntryDialogFragment();		
		Bundle args = new Bundle();
		args.putInt("title",  title);
		args.putSerializable("type",  FoodDialogType.ADD);
		args.putSerializable("fe",  new FoodEntry());
		dlg.setArguments(args);
		return dlg;
	}
	
	/**
	 * CONSTRUCTOR
	 * @param title
	 * @param fe
	 * @return
	 */
	public static DayEntryDialogFragment neweditinstance(int title, FoodEntry fe)
	{				
		DayEntryDialogFragment dlg = new DayEntryDialogFragment();		
		Bundle args = new Bundle();
		args.putInt("title",  title);
		args.putSerializable("type",  FoodDialogType.EDIT);
		args.putSerializable("fe",  fe);
		dlg.setArguments(args);
		return dlg;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		LayoutInflater inflater = getActivity().getLayoutInflater();
		view = inflater.inflate(R.layout.dayentry_dialog, null);
		type = (FoodDialogType)getArguments().getSerializable("type");
		
		int dialogType = R.string.add;
		if (FoodDialogType.EDIT == type)
			dialogType = R.string.ok;
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setView(view)
			.setPositiveButton(dialogType, new DialogInterface.OnClickListener() 
			{
				@Override
				public void onClick(DialogInterface dialog, int which) {clickAction(); }
			})
			.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() 
			{
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					DayEntryDialogFragment.this.getDialog().cancel();					
				}
			});
		
		unitsField = ((EditText)view.findViewById(R.id.dayentrydialog_units));
		unitField = ((EditText)view.findViewById(R.id.dayentrydialog_unit));
		
		// populate food list drop down
		getFoodList();
		spinner = (Spinner)view.findViewById(R.id.dayentrydialog_foodlist);
		foodAdapter = new ArrayAdapter<Food>(view.getContext(), 
				android.R.layout.simple_spinner_item, foodList);
		foodAdapter
	    .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(foodAdapter);
		foodNameField = (EditText)view.findViewById(R.id.dayentrydialog_foodname);
		
		createMeasurementFields();
		
		fe = (FoodEntry)getArguments().getSerializable("fe");
		
		// if edit dialog, populate fields and change set button
		if (FoodDialogType.EDIT == type)
		{
			Food f = foodList.getFood(fe.getName());
			if (null != f)
			{
				// locate food in spinner
				for (int i = 0; i < foodAdapter.getCount(); ++i)
				{
					if (((Food)foodAdapter.getItem(i)).getName().equals((f.getName())))
					{
							spinner.setSelection(i);
							break;
					}
				}				
				updateFields(f);
			}
		}
		
		// verify if it is a custom food entry
		customCheckBox = (CheckBox)view.findViewById(R.id.dayentrydialog_custom);
		if (fe.isCustom())
		{
			customCheckBox.setChecked(true);
		}
		setEnableMeasurementFields(fe.isCustom());			
		
		// listeners
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() 
		{
			@Override
			public void onItemSelected(AdapterView<?> parent, View arg1,
					int pos, long id) 
			{
				updateFields((Food)parent.getItemAtPosition(pos));
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		unitsField.setOnFocusChangeListener(new OnFocusChangeListener()
		{	
			@Override
			public void onFocusChange(View v, boolean hasFocus)	
			{ 
				updateFields((Food)spinner.getSelectedItem()); 
			}
		});
		customCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				fe.setCustom(isChecked);
				setEnableMeasurementFields(isChecked);
			}
		});

		return builder.create();
	}
	
	/**
	 * Creates the measurement fields based on the measurements in
	 * the Measurement enum class.
	 */
	private void createMeasurementFields()
	{
		// dynamically creates the measurement fields
		for (Measurement msm : Measurement.values())
		{
			EditText box = new EditText(getActivity());
			box.setHint(msm.toString());
			box.setTextSize(12);
			box.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER);
			measurementBoxes.add(box);
			((LinearLayout)view).addView((View)box);
		}
		
	} // createMeasurementFields()
	
	/**
	 * Disables all applicable text fields that shouldn't be edited when
	 * not a custom food.
	 */
	private void setEnableMeasurementFields(boolean flag)
	{
		spinner.setVisibility(flag ? View.GONE : View.VISIBLE);
		foodNameField.setVisibility(flag ? View.VISIBLE : View.GONE);
		
		((EditText)view.findViewById(R.id.dayentrydialog_unit)).setEnabled(flag);
		int i = 0;
		for (Measurement msm : Measurement.values())
			measurementBoxes.get(i++).setEnabled(flag);
		
		
	} // end disableTextFields()
		
	/**
	 * Updates the editing fields based on selected food. Does not auto
	 * calculate if it is a custom entry.
	 * @param food
	 */
	protected void updateFields(Food food) 
	{				
		if (fe.isCustom())
		{
			fe.setName(foodNameField.getText().toString());
			fe.setUnit(unitField.getText().toString());
			return;
		}
		
		fe.setUnit(food.getUnit());
		fe.setName(food.getName());
		String unitsStr = unitsField.getText().toString();
		if (null == unitsStr || 0 == unitsStr.length())
			unitsStr = "1";
		double num = Double.parseDouble(unitsStr);
		
		int i = 0;
		unitField.setText(food.getUnit());
		fe.setUnits(num);
		for (Measurement msm : Measurement.values())
		{
			measurementBoxes.get(i++).setText(
					Double.toString(num * food.getMeasurement(msm)));
			fe.setMeasurement(msm,  num * food.getMeasurement(msm));
		}		
		
	} // end updateFields()

	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		
		try
		{
			nodeList = (IEntryControl)getTargetFragment();
		} catch (ClassCastException e)
		{
			throw new ClassCastException(activity.toString()
					+ " must implement IEntryControl");
		}
	}
	
	
	/**
	 * Retrieves the list of user foods.
	 */
	public void getFoodList()
	{
		foodList = FoodListManager.getFoodList();
		
	} // end getFoodList()
	
	
	/**
	 * Either creates a note entry object and passes it to the main note list 
	 * residing in the main activity, or edits an existing notes object 
	 */
	private void clickAction()
	{
		updateFields((Food)spinner.getSelectedItem());
		
		switch (type)
		{
			case ADD:
				nodeList.addEntry(fe);
				break;
			case EDIT:
				nodeList.updateEntries();
				break;
		}
	}
	
} // end class AddNoteDialogFragment
