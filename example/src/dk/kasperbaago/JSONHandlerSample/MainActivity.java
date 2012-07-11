package dk.kasperbaago.JSONHandlerSample;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

import dk.kasperbaago.JSONHandler.JSONHandler;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //Setting up a new JSONHandler object
        JSONHandler a = new JSONHandler(this);
        
        //Changing the text of the dialog
        a.setDiag("Getting data", "Please wait...");
        
        //Changing the text if an error happens
        a.setErrMsg("An error accured", "I'm sorry but an error accured!");
        
        //If you want to self handle the error, this will still run the callback if an error happens.
        //The callback will be called with the err parameter set to true!
        //a.showErrMsg(false);
        
        //Setting up the parameters I want to send with the request
        List<NameValuePair> p = new ArrayList<NameValuePair>(1); //Setting the array list to contain 1 element
        p.add(new BasicNameValuePair("MyParameter", "MyValue"));
        
        //Setting up the URL
        String url = "http://api.kasperbaago.dk/jsontest/";
        
        //Making the request to the server
        a.makeRequest(url, p, "post", "getResutls");
		
    }
    
    /**
     * Callback method in the same activity that makes the request.
     * Remember that this is only called if no errors happen, else the error message will be shown to the user.
     * 
     * @param json
     * @param err
     */
    public void getResutls(JSONArray json, Boolean err) {
    	if(json.length() > 0 && err == false) {
    		TextView txt = (TextView) findViewById(R.id.textView2);
    		try {
				txt.setText(json.getString(0));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
        
}
