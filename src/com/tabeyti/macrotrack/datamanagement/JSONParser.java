package com.tabeyti.macrotrack.datamanagement;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONParser
{
	public final static int GET = 1;
    public final static int POST = 2;
    
    static String response = null;
	
	public String makeHttpRequest(String url, int method, List<NameValuePair> params) throws JSONException
	{
		try
		{
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpEntity httpEntity = null;
			HttpResponse httpResponse = null;
			
			if (method == GET)
			{
				if (params != null)
				{
					String paramString = URLEncodedUtils.format(params,  "utf-8");
					url += "?" + paramString;
				}
				
				HttpGet httpGet = new HttpGet(url);
				httpResponse = httpClient.execute(httpGet);
			}
			
			httpEntity = httpResponse.getEntity();
			response = EntityUtils.toString(httpEntity);
			
			
		} catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } 
		
return response;		//return new JSONObject(response);
		
	} // end makeHttpRequest()
	

} // end class JSONParser
