package com.tabeyti.macrotrack.shared;

// http://www.androidhive.info/2012/05/how-to-connect-android-with-php-mysql/
// http://www.jameselsey.co.uk/blogs/techblog/extracting-out-your-asynctasks-into-separate-classes-makes-your-code-cleaner/

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.tabeyti.macrotrack.R;

public class DataManager
{
	static final String LOG_ENTRY = "log_entry";
	static final String FOOD_ENTRY = "food_entry";
	static final String NAME = "name";
	static final String UNITS = "units";
	static final String WORKOUT = "workout";
	static final String DATE = "date";
	
	// food list xml values
	static final String FOOD_LIST = "food_list";
	static final String FOOD = "food";
	static final String UNIT = "unit";
	
	// goals xml values
	static final String GOALS = "goals";
	static final String NOTES = "notes";		
	
	public static String set(LogEntry le) throws Exception
	{
		JSONObject jsonObj = new JSONObject();
		JSONArray jsonArr = new JSONArray();
		
		// food entries
		for (FoodEntry fe : le.getFoodEntries())
		{
			JSONObject feObj = new JSONObject();
			feObj.put(NAME,  fe.getName());
			feObj.put(UNITS,  fe.getUnits());
			for (Measurement msm : Measurement.values())
				feObj.put(msm.toString(),  fe.getMeasurement(msm));
			jsonArr.put(feObj);
		}
		jsonObj.put(FOOD_ENTRY,  jsonArr);
		
		// workout data
		jsonObj.put(WORKOUT, le.getWorkout().toString());
		
		// notes data
		jsonObj.put(NOTES, le.getNotes());
		
		
		return jsonObj.toString();
		
	}
	
	public static void sendFile(DataType ft, String data) throws Exception
	{
		/*
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(wurl);
		StringEntity se = new StringEntity(data);
		se.setContentType("application/json);charset=UTF-8");
		se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json;charset=UTF-8"));
		httpPost.setEntity(se);
		HttpResponse httpResponse = httpClient.execute(httpPost);
		*/		
	}
	
	public static FoodList parseFoodList(Context context) throws Exception
	{
		
		InputStream inputStream = null;
		String result = "";
		
		inputStream = context.getResources().openRawResource(R.raw.foodlist);
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
	    StringBuilder sb = new StringBuilder();

	    String line = null;
	    while ((line = reader.readLine()) != null)
	    {
	        sb.append(line + "\n");
	    }
	    result = sb.toString();
		
		JSONObject jObject = new JSONObject(result);
		JSONArray jArray = jObject.getJSONArray(FOOD);
		
		FoodList fl = new FoodList();
		for (int i=0; i < jArray.length(); i++)
		{
			JSONObject food = jArray.getJSONObject(i);
			
			double[] msm = new double[Measurement.values().length];
			for (int index = 0; index < msm.length; ++index)
				msm[index] = food.getDouble(Measurement.values()[index].toString());
			
			fl.add(new Food(
					food.getString(NAME),
					food.getString(UNIT),
					msm,
					-1
					));
		}
		
		return fl;			
	}
	
	
	
	/*// log entry xml values
	
	
	
	*//**
	 * Creates an xml representation of the passed in log entry and saves it to the server.
	 * @param le
	 * @throws Exception
	 *//*
	public static void setLogEntry(LogEntry le) throws Exception
	{
		Document doc = XMLParser.createDocument();
		Element root;
		Element foodEntryElem;
		Element child;
		Element workoutElem;
	   	Element notesElem;	
		
		// root element
		root = doc.createElement(LOG_ENTRY);
		doc.appendChild(root);
		
		Element dateElem = doc.createElement(DATE);
		dateElem.appendChild(doc.createTextNode(le.getDate()));
		root.appendChild(dateElem);

		// inner elements
		for (FoodEntry fe : le.getFoodEntries())
		{
			// food entry
			foodEntryElem = doc.createElement(FOOD_ENTRY);
			
			// name a units
			child = doc.createElement(NAME);
			child.appendChild(doc.createTextNode(fe.getName()));
			foodEntryElem.appendChild(child);
			child = doc.createElement(UNITS);
			child.appendChild(doc.createTextNode(Double.toString(fe.getUnits())));
			foodEntryElem.appendChild(child);
			
			// all measurements
			for (Measurement msm : Measurement.values())
			{
				child = doc.createElement(msm.toString());
				child.appendChild(doc.createTextNode(Double.toString(fe.getMeasurement(msm))));
				foodEntryElem.appendChild(child);				
			}
					
			// close food entry
			root.appendChild(foodEntryElem);
		}
		
		// workout data
		workoutElem = doc.createElement(WORKOUT);
		workoutElem.appendChild(doc.createTextNode(le.getWorkout().toString()));
		root.appendChild(workoutElem);
		
		// notes data
		notesElem = doc.createElement(NOTES);
		notesElem.appendChild(doc.createTextNode(le.getNotes()));
		root.appendChild(notesElem);
		
		// send byte stream to the server
		RPCInterfaceAsync rpci = GWT.create(RPCInterface.class);
		rpci.sendFile(doc.toString().getBytes(), le.getDate(), FileType.LOGENTRY, new MessageCallBack());
		
	} // end setLogEntry()


	*//**
	 * Parses the log entry from the received Data object. DONE
	 * @param data
	 * @return
	 * @throws Exception
	 *//*
	public static LogEntry parseLogEntry(Data data) throws Exception
	{
		// creates empty log entry
		LogEntry logEntry = new LogEntry(data.getFileName());
		
		if (null == data.getData())
			return logEntry;
		
		Document doc;
		String dat = new String(data.getData(), "UTF-8");
		doc = XMLParser.parse(dat);
		
		// parse date 
		if (null != doc.getElementsByTagName(DATE).item(0))
			logEntry.setDate(doc.getElementsByTagName(DATE).item(0).getFirstChild().getNodeValue());
				
		// pulls in food entries
		NodeList nodes = doc.getElementsByTagName(FOOD_ENTRY);
		for (int index = 0; index < nodes.getLength(); ++index)
		{
			FoodEntry fe = new FoodEntry();
			Element foodEntry = (Element)nodes.item(index);

			fe.setName(getElementTextValue(foodEntry, NAME));
			fe.setUnits(Double.parseDouble(getElementTextValue(foodEntry, UNITS)));
			// cycles through all measurements
			for (Measurement msm : Measurement.values())
			{
				String value = getElementTextValue(foodEntry, msm.toString());
				if (null == value)
					continue;
				fe.setMeasurement(msm, Double.parseDouble(value));
			}
			
			logEntry.addFoodEntry(fe);
		}
		
		// pulls in workout
		if (null != doc.getElementsByTagName(WORKOUT).item(0))
			logEntry.setWorkout(Workout.findByValue(doc.getElementsByTagName(WORKOUT).item(0).getFirstChild().getNodeValue()));
		
		// pulls in notes
		if (null != doc.getElementsByTagName(NOTES).item(0) && 
			null != doc.getElementsByTagName(NOTES).item(0).getFirstChild())
			logEntry.setNotes(doc.getElementsByTagName(NOTES).item(0).getFirstChild().getNodeValue());
		
		return logEntry;
		
	} // end parseLogEntry()
	
	
	*//**
	 * Parses and generates a food list from the passed byte data. DONE
	 * @param data
	 * @return
	 * @throws Exception
	 *//*
	public static FoodList parseFoodList(Data data) throws Exception
	{
		// creates empty food list
		FoodList fl = new FoodList();
		
		if (null == data)
			return fl;
		
		Document doc;
		String dat = new String(data.getData(), "UTF-8");
		doc = XMLParser.parse(dat);
				
		// pulls in food entries
		NodeList nodes = doc.getElementsByTagName(FOOD);
		for (int index = 0; index < nodes.getLength(); ++index)
		{
			Food food = new Food();
			String value = "";
			Element foodElement = (Element)nodes.item(index);

			value = getElementTextValue(foodElement, NAME);
			if (null == value)
				continue;
			food.setName(value);
			
			value = getElementTextValue(foodElement, UNIT);
			if (null == value)
				continue;
			food.setUnit(getElementTextValue(foodElement, UNIT));
			// cycles through all measurements
			for (Measurement msm : Measurement.values())
			{
				value = getElementTextValue(foodElement, msm.toString());
				if (null == value)
					continue;
				food.setMeasurement(msm, Double.parseDouble(value));
			}
			
			fl.add(food);
		}
		
		return fl;
			
	} // end parseFoodList()
	
	
	*//**
	 * Saves list of foods to the output file. 
	 * @param fl
	 *//*
	public static void setFoodList(FoodList fl) throws Exception
	{
		Document doc = XMLParser.createDocument();
		Element root;
		Element foodElem;
		Element child;
		Element workoutElem;
	   		
		// root element
		root = doc.createElement(FOOD_LIST);
		doc.appendChild(root);

		// inner elements
		for (Food food : fl)
		{
			// food element
			foodElem = doc.createElement(FOOD);
			
			// name a units
			child = doc.createElement(NAME);
			child.appendChild(doc.createTextNode(food.getName()));
			foodElem.appendChild(child);
			child = doc.createElement(UNIT);
			child.appendChild(doc.createTextNode(food.getUnit()));
			foodElem.appendChild(child);
			
			// all measurements
			for (Measurement msm : Measurement.values())
			{
				child = doc.createElement(msm.toString());
				child.appendChild(doc.createTextNode(Double.toString(food.getMeasurement(msm))));
				foodElem.appendChild(child);				
			}
					
			// close food entry
			root.appendChild(foodElem);
		}
		
		// send byte stream to the server
		RPCInterfaceAsync rpci = GWT.create(RPCInterface.class);
		rpci.sendFile(doc.toString().getBytes(), Globals.foodListFileName, 
				FileType.FOODLIST, new MessageCallBack());
		
		
	} // end setFoodList()
	

	
	*//**
	 * Parses the xml data and creates a goals object to be returned to the 
	 * user. DONE
	 * @param data
	 * @return
	 * @throws Exception
	 *//*
	public static Goals parseGoals(Data data) throws Exception
	{
		// creates empty log entry
		Goals goals = new Goals();
		
		if (null == data)
			return goals;
		
		Document doc;
		String dat = new String(data.getData(), "UTF-8");
		doc = XMLParser.parse(dat);
				
		// cycles through all measurements, pulling in all the goal values for 
		// each
		Element root = (Element)doc.getElementsByTagName(GOALS).item(0);
		for (Measurement msm : Measurement.values())
		{
			String value = getElementTextValue(root, msm.toString());
			if (null == value)
				continue;
			goals.setMeasurement(msm, Double.parseDouble(value));			
		}
		
		// sets the notes section of the goals
		String notes = getElementTextValue(root,  NOTES);
		if (null != notes)
			goals.setNotes(notes);
		
		return goals;
		
	} // end parseGoals()
	
	
	*//**
	 * Creates goals xml from the Goals object and sends it to the server to 
	 * be stored.
	 * @param goals
	 * @throws Exception
	 *//*
	public static void setGoals(Goals goals) throws Exception
	{
		Document doc = XMLParser.createDocument();
	   		
		// root element
		Element root = doc.createElement(GOALS);
		doc.appendChild(root);

		// measurements
		for (Measurement msm : Measurement.values())
		{
			// food element
			Element msmElem = doc.createElement(msm.toString());
			msmElem.appendChild(doc.createTextNode(Double.toString(goals.getMeasurement(msm))));
					
			// close measurement entry
			root.appendChild(msmElem);
		}
		
		// sets notes
		Element notesElem = doc.createElement(NOTES);
		notesElem.appendChild(doc.createTextNode(goals.getNotes()));
		root.appendChild(notesElem);
		
		// send byte stream to the server
		RPCInterfaceAsync rpci = GWT.create(RPCInterface.class);
		rpci.sendFile(doc.toString().getBytes(), 
				Globals.goalsFileName, 
				FileType.GOALS, 
				new MessageCallBack());		
		
	} // end setGoals()
	
	
	*//**
	 * Returns the textual data of an xml element.
	 * @param parent
	 * @param tag
	 * @return
	 *//*
	private static String getElementTextValue(Element parent, String tag)
	{
		if (null == parent.getElementsByTagName(tag).item(0))
			return null;
		return parent.getElementsByTagName(tag).item(0).getFirstChild().getNodeValue();
		 
	} // end getElementTextValue() 
*/
} // end class DataManager
