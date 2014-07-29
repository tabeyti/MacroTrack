package com.tabeyti.macrotrack.datamanagement;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.tabeyti.macrotrack.FoodListManager;
import com.tabeyti.macrotrack.shared.FoodEntries;
import com.tabeyti.macrotrack.shared.FoodEntry;
import com.tabeyti.macrotrack.shared.LogEntry;

public class GetLogEntry extends AsyncTask<String, String, LogEntry>
{
	private Context context;
	private IDataCallBack<LogEntry> listener;
	private ProgressDialog pDialog;
	private JSONParser jsonParser = new JSONParser();
	private String date = "";
	
	// TODO remove
	String json = "no data";
	
	public GetLogEntry(Context context, IDataCallBack<LogEntry> listener, String date)
	{
		this.date = date;
		this.context = context;
		this.listener = listener;
	}

	protected void onPreExecute()
	{
		super.onPreExecute();
        
		pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading log entry. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
	}
	
	@Override
	protected LogEntry doInBackground(String... args)
	{	
		LogEntry le = new LogEntry("2014-05-26");
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("date", "2014-05-26"));
		params.add(new BasicNameValuePair("user_ID", "1"));
		try
		{
			String json =  jsonParser.makeHttpRequest(Tags.WURL + "get_log_entry.php", 
					JSONParser.GET, params);
			JSONObject js = new JSONObject(json);
			JSONObject leJson = js.getJSONObject(Tags.LOG_ENTRY);			
			if (1 == js.getInt(Tags.SUCCESS))
			{
				le.setId(leJson.getInt(Tags.LOG_ID));
				//le.setWorkout(Workout.leJson.getInt(Tags.WORKOUT));
				
				JSONArray foods = leJson.getJSONArray(Tags.FOOD_ENTRY);
				for (int i = 0; i < foods.length(); ++i)
				{
					le.addFoodEntry(new FoodEntry());
				}
			}
		} catch (Exception e)
		{

		}
		return le;
	}
	
	@Override
	protected void onPostExecute(LogEntry le)
	{
		super.onPostExecute(le);
		//le = new LogEntry(json);
		listener.onTaskComplete(le);
		pDialog.dismiss();
	}
	
	
	// TODO: Temporary log entry 
	protected LogEntry createLogEntry()
	{
		String[] splt = date.split("-");
		int day = Integer.parseInt(splt[1]);
		
		LogEntry le = new LogEntry(date);
		
		int i = 0;
		if (day % 2 == 0)
		{
			le.getFoodEntries().add(new FoodEntry(FoodListManager.getFoodList().get(i++)));
			le.getFoodEntries().add(new FoodEntry(FoodListManager.getFoodList().get(i++)));
			le.getFoodEntries().add(new FoodEntry(FoodListManager.getFoodList().get(i++)));
			le.getFoodEntries().add(new FoodEntry(FoodListManager.getFoodList().get(i++)));
			le.getFoodEntries().add(new FoodEntry(FoodListManager.getFoodList().get(i++)));
			le.getFoodEntries().add(new FoodEntry(FoodListManager.getFoodList().get(i++)));
		}
		else
		{
			i = 10;
			le.getFoodEntries().add(new FoodEntry(FoodListManager.getFoodList().get(i++)));
			le.getFoodEntries().add(new FoodEntry(FoodListManager.getFoodList().get(i++)));
			le.getFoodEntries().add(new FoodEntry(FoodListManager.getFoodList().get(i++)));
		}
				
		return le;		
	}

} // end class GetLogEntry
