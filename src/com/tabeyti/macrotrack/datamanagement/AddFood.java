package com.tabeyti.macrotrack.datamanagement;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.tabeyti.macrotrack.MainActivity;
import com.tabeyti.macrotrack.shared.Food;
import com.tabeyti.macrotrack.shared.Id;
import com.tabeyti.macrotrack.shared.Measurement;

public class AddFood extends AsyncTask<String, String, Id>
{
	private Context context;
	private IDataCallBack<Id> listener;
	private ProgressDialog pDialog;
	private JSONParser jsonParser = new JSONParser();
	private String error = null;
	private Food food;
	
	
	// TODO remove
	String json = "no data";
	
	public AddFood(Context context,  Food food, IDataCallBack<Id> listener)
	{
		this.food = food;
		this.context = context;
		this.listener = listener;
	}

	protected void onPreExecute()
	{
		super.onPreExecute();
        
		pDialog = new ProgressDialog(context);
        pDialog.setMessage("Adding food. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
	}
	
	@Override
	protected Id doInBackground(String... args)
	{	
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(Tags.NAME, food.getName()));
		params.add(new BasicNameValuePair(Tags.UNIT, food.getUnit()));
		for (Measurement msm : Measurement.values())
			params.add(new BasicNameValuePair(msm.toString(), 
					Double.toString(food.getMeasurement(msm))));
		try
		{
			json = jsonParser.makeHttpRequest(Tags.WURL + "add_food.php", 
					JSONParser.POST, params);
									
			Log.d("Add food", json);
			
			JSONObject js = new JSONObject(json);
			
			if (1 == js.getInt(Tags.SUCCESS))
			{
				return new Id(js.getInt(Tags.FOOD_ID));
			}
			else
			{
				error = js.getString("message");
				return null;
			}
			
		} catch (JSONException e)
		{
			error = e.getMessage();
			return null;
		}
		
	}
	
	@Override
	protected void onPostExecute(Id id)
	{
		super.onPostExecute(id);
		
		if (null != error)
		{
			MainActivity.error(error);
		}
		listener.onTaskComplete(id);
		pDialog.dismiss();
	}

} // end class GetFoodList
