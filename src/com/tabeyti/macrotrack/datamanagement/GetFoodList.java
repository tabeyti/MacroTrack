package com.tabeyti.macrotrack.datamanagement;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.tabeyti.macrotrack.MainActivity;
import com.tabeyti.macrotrack.shared.Food;
import com.tabeyti.macrotrack.shared.FoodList;
import com.tabeyti.macrotrack.shared.Measurement;

public class GetFoodList extends AsyncTask<String, String, FoodList>
{
	private Context context;
	private IDataCallBack<FoodList> listener;
	private ProgressDialog pDialog;
	private JSONParser jsonParser = new JSONParser();
	private String error = null;
	
	// TODO remove
	String json = "no data";
	
	public GetFoodList(Context context, IDataCallBack<FoodList> listener)
	{
		this.context = context;
		this.listener = listener;
	}

	protected void onPreExecute()
	{
		super.onPreExecute();
        
		pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading food list. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
	}
	
	@Override
	protected FoodList doInBackground(String... args)
	{	
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		FoodList fl = new FoodList();
		try
		{
			json = jsonParser.makeHttpRequest(Tags.WURL + "get_food_list.php", 
					JSONParser.GET, params);
									
			Log.d("Food List", json);
			
			JSONObject js = new JSONObject(json);
			
			if (1 == js.getInt(Tags.SUCCESS))
			{
				JSONArray foods = js.getJSONArray(Tags.FOOD_LIST);
				for (int i = 0; i < foods.length(); ++i)
				{
					JSONObject f = foods.getJSONObject(i);
					
					double[] mesm = new double[Measurement.values().length];
					int m = 0;
					for (Measurement msm : Measurement.values())
						mesm[m++] = f.getDouble(msm.toString().toLowerCase());
					
					fl.add(new Food(
							f.getString(Tags.NAME),
							f.getString(Tags.UNIT),
							mesm,
							f.getInt(Tags.FOOD_ID)));
				}
				
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
		
		return fl;
	}
	
	@Override
	protected void onPostExecute(FoodList fl)
	{
		// TODO: temp since server is down
//		try
//		{
//			fl = DataManager.parseFoodList(context);
//		} catch (Exception e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		super.onPostExecute(fl);
		
		if (null == fl)
		{
			MainActivity.error(error);
		}
		
		listener.onTaskComplete(fl);
		pDialog.dismiss();
	}

} // end class GetFoodList
