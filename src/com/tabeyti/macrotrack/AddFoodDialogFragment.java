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
public class AddFoodDialogFragment extends DialogFragment
{
	private View view;
	private FoodDialogType type;
	private List<EditText> measurementBoxes = new ArrayList<EditText>();
	private EditText unitField;
	private EditText foodNameField;	
	private Food food = null;
	private IEntryControl nodeList;
	
	public AddFoodDialogFragment() {}	
	
	/**
	 * CONSTRUCTOR
	 * @param title
	 * @return
	 */
	public static AddFoodDialogFragment newaddinstance(int title)
	{				
		AddFoodDialogFragment dlg = new AddFoodDialogFragment();		
		Bundle args = new Bundle();
		args.putInt("title",  title);
		args.putSerializable("type",  FoodDialogType.ADD);
		args.putSerializable("food",  new Food());
		dlg.setArguments(args);
		return dlg;
	}
	
	/**
	 * CONSTRUCTOR
	 * @param title
	 * @param fe
	 * @return
	 */
	public static AddFoodDialogFragment neweditinstance(int title, Food food)
	{				
		AddFoodDialogFragment dlg = new AddFoodDialogFragment();		
		Bundle args = new Bundle();
		args.putInt("title",  title);
		args.putSerializable("type",  FoodDialogType.EDIT);
		args.putSerializable("food",  food);
		dlg.setArguments(args);
		return dlg;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		LayoutInflater inflater = getActivity().getLayoutInflater();
		view = inflater.inflate(R.layout.addfood_dialog, null);
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
					AddFoodDialogFragment.this.getDialog().cancel();					
				}
			});
		
		unitField = ((EditText)view.findViewById(R.id.addfooddialog_unit));
		foodNameField = (EditText)view.findViewById(R.id.addfooddialog_foodname);
		createMeasurementFields();
		
		food = (Food)getArguments().getSerializable("food");
		
		// if edit dialog, populate fields and change set button
		if (FoodDialogType.EDIT == type)
		{
			
		}
		
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
	 * Either creates a note entry object and passes it to the main note list 
	 * residing in the main activity, or edits an existing notes object 
	 */
	private void clickAction()
	{
		switch (type)
		{
			case ADD:
				food = new Food();
				food.setName(foodNameField.getText().toString());
				food.setUnit(unitField.getText().toString());
				int i = 0;
				for (Measurement msm : Measurement.values())
					food.setMeasurement(msm, 
							Double.parseDouble(measurementBoxes.get(i++).getText().toString()));
				nodeList.addEntry(food);
				break;
			case EDIT:
				
				break;
		}
	}
	
} // end class AddNoteDialogFragment
