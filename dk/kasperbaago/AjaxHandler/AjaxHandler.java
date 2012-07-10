/* 
 * AJAX HANDLER
 * Contacts a JSON web server, and returns the data as an JSON array in a callback function.
 * The class extends async task.
 * 
 * @version: 1.0
 * @author: Kasper Baag¿ Jensen(kaper@kasperbaago.dk)
 * @license: 
 */
package dk.kasperbaago.AjaxHandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Process;
import android.util.Log;

public class AjaxHandler extends AsyncTask<String, Process, JSONArray>{
	
	private ProgressDialog prossDiag;
	private Context context;
	private Object object;
	private String callbackMethod;
	private String HTML;
	private String fileField;
	private Boolean errorHasHappened;
	
	//Dialog messages
	private String  diagTitle;
	private String diagMsg;
	private Boolean showErrorMsg;
	private String errorMsgTitle;
	private String errorMsgTxt;
	
	//URL Setting
	private String url;
	private List<NameValuePair> parameters;
	private String method;
	
	//Constructor for AJAX handler
	public AjaxHandler(Context con) {
		this.context = con;
		this.object = con;
		this.errorHasHappened = false;
		
		//Initialize dialog messages
		this.diagTitle = "Please wait...";
		this.diagMsg = "Getting data from server...";
		
		this.showErrorMsg = true;
		this.errorMsgTitle = "An error has accured!";
		this.errorMsgTxt = "When making the request to " + this.url + " a error happened!";
	}
	
	/**
	 * Makes a request to the given webserver, with the given list of parameters.
	 * 
	 * @param String url
	 * @param List<NameValuePair> parameters
	 * @param String method("post" or "get")
	 * @param String callBack(callback method that should be used when the server returns a result)
	 */
	public void makeRequest(String url, List<NameValuePair> parameters, String method, String callBack) {
		if(method == "get" || method == "post") {
			this.url = url;
			this.method = method;
			this.parameters = parameters;
			this.callbackMethod = callBack;
			
			this.execute();
		}
	}
	
	/**
	 * Sets the shown dialog settings, when loading data.
	 * 
	 * @param String title
	 * @param String msg
	 */
	public void setDiag(String title, String msg) {
		this.diagTitle = title;
		this.diagMsg = msg;
	}
	
	/**
	 * Sets the name of the field that contains a path to a file that can be uploadet.
	 * NOTICE: This field is ONLY used in POST requests!
	 * @param String Name
	 */
	public void setNameOfFileField(String name) {
		this.fileField = name;
	}
	
	/**
	 * Returns the clear HTML as sting
	 * @return String
	 */
	public String getHTML() {
		return this.HTML;
	}
	
	//*** HTTP FUNCTIONS ***//
	
	//Gets an URL value
	public String getURL() {
		
		//Making a HTTP client
		HttpClient client = new DefaultHttpClient();
		
		//Declaring HTTP response
		HttpResponse response = null;
		String myReturn = null;
		
		try {	
		
			//Making a HTTP getRequest or post request
			if(method == "get") {
				String parms = "";
				if(this.parameters.size() > 0) {
					parms = "?";
					parms += URLEncodedUtils.format(this.parameters, "utf-8");
				}
				
				Log.i("parm", parms);
				Log.i("URL", this.url + parms);
				HttpGet getUrl = new HttpGet(this.url + parms);
				
				//Executing the request
				response = client.execute(getUrl);
			} else if(method == "post") {
				HttpPost getUrl = new HttpPost(this.url);				
				
				//Sets parameters to add
				MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
				for(int i = 0; i < this.parameters.size(); i++) {
					if(this.parameters.get(i).getName().equalsIgnoreCase(fileField)) {
							entity.addPart(parameters.get(i).getName(), new FileBody(new File(parameters.get(i).getValue())));
						} else {
							entity.addPart(parameters.get(i).getName(), new StringBody(parameters.get(i).getName()));
						}
					}
				
				getUrl.setEntity(entity);
				
				//Executing the request
				response = client.execute(getUrl);
			} else {
				return "false";
			}
			
			//Returns the data		
			HttpEntity content = response.getEntity();			
			InputStream mainContent = content.getContent();
			myReturn = this.convertToString(mainContent);
			this.HTML = myReturn;
			Log.i("Result", myReturn);
						
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return myReturn;
	}
	
	//Converts an input stream to a string
	public String convertToString(InputStream input) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		
		String result = null;
		String returnData = "";
		try {
			while((result = reader.readLine()) != null) {
				returnData = returnData + result + "\n";
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				try {
					input.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
		return returnData;	
	}
	
	//Converts a JSON string to an JSON array
	public JSONArray stringToJSON(String jsonStr) {
		try {
			JSONArray jsonArr = new JSONArray(jsonStr);
			return jsonArr;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	//*** PRE and POST excution methods ***//
	
	@Override
	protected void onPreExecute() {
		this.prossDiag = ProgressDialog.show(this.context, this.diagTitle, this.diagMsg);
		super.onPreExecute();
	}
	
	@Override
	protected void onPostExecute(JSONArray result)  {
		this.prossDiag.dismiss();
		if(this.errorHasHappened && this.showErrorMsg) {
			this.showErrorMsg();
		} else {
			this.runCallback(result, this.errorHasHappened);
		}
		super.onPostExecute(result);
	}
	
	@Override
	protected JSONArray doInBackground(String... params) {
		 String jsonString = this.getURL();
		 if(jsonString == "false" || jsonString == null) {
			 jsonString = "[{return:false}]";
			 this.errorHasHappened = true;
		 }
			JSONArray jArray = this.stringToJSON(jsonString);
		 	return jArray;
	}
	
	//*** ERROR MSG ***
	
	/**
	 *  Shows an error to the user, if the request fails.
	 *  Callback is NOT called if this is shown. To still run callback and use your own error handler, use showErrMsg(false);
	 */
	private void showErrorMsg() {
		AlertDialog alert = new AlertDialog.Builder(this.context).create();
		alert.setTitle(this.errorMsgTitle);
		alert.setMessage(this.errorMsgTxt);
		alert.setButton("Ok",  new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();					
			}
		});
		alert.show();
	}
	
	/**
	 * Sets if error message should be shown.
	 * If error are shown, callback will not be run.
	 * If error not are shown, callback will be run, but with the last boolean parameter set to true if error has happened.
	 * 
	 * @param boolean setting
	 */
	public void showErrMsg(Boolean setting) {
		this.showErrorMsg = setting;
	}
	
	/**
	 * Sets title and text, if error happens.
	 * 
	 * @param String title
	 * @param String txt
	 */
	public void setErrMsg(String title, String txt) {
		this.errorMsgTitle = title;
		this.errorMsgTxt = txt;
	}
	
	//*** CALLBACK ****//
	
	//Runs the callback method
	private void runCallback(JSONArray result, Boolean error) {
		Method method;
		try {
			method = this.object.getClass().getMethod(this.callbackMethod, JSONArray.class, Boolean.class);
			method.invoke(this.object, result, error);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
