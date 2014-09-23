package com.example.raisethevoice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

public class Launcher extends Activity {
	
	SharedPreferences sharedP;
	String user,pass;
	
	HttpClient client;
	HttpPost post;
	HttpResponse response;
	HttpGet get;
	InputStream is;
	BufferedReader bfr;
	HttpEntity entity;
	UrlEncodedFormEntity formEntity;
	BufferedReader in =null;
	String result2;
	String userCategory;
	
	ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        
        pd = new ProgressDialog(this);
        pd.setTitle("Authentication.....");
      //  pd.show();
        
        Thread t = new Thread(){
        	
        	@Override
        	public void run() {
        		// TODO Auto-generated method stub
        		super.run();
        		
        		try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        };
        
        t.start();
        
		sharedP = getSharedPreferences("Register", Context.MODE_PRIVATE);

		client = new DefaultHttpClient();
        
     
		user = sharedP.getString("username", "username");
		pass = sharedP.getString("password", "password");
		
		Toast.makeText(getApplicationContext(), "Username :-->"+user, 1000).show();
		Toast.makeText(getApplicationContext(), "Password :-->"+pass, 1000).show();
		
		//--------------------------------------------------------
		if(user.equals("username")){
			Intent i = new Intent(Launcher.this,Register.class);
			startActivity(i);
		}
		else{
			
			Toast.makeText(getApplicationContext(), "Username Already Exists", 1000).show();
			
			new AsyncTask<String, String, Void>(){

				@Override
				protected Void doInBackground(String... params) {
					// TODO Auto-generated method stub
					
					
					get = new HttpGet("http://www.vivekandroid.esy.es/rtv/user_login.php?user_name="+user+"&password="+pass+"&submit=submit");
					
					try {
						
						response = client.execute(get);
						
						
						 in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
					
						 StringBuffer sb= new StringBuffer("");
						 String line = "";
						 String NL = System.getProperty("line.seperator");
						 
						 while((line = in.readLine())!= null){
							 
							 sb.append(line+NL);
							 
						 }
						 
						 in.close();
						 
						 result2 = sb.toString();
						 
						 JSONObject jObj = new JSONObject(result2);
						 
						// JSONObject jObj2 = jObj.getJSONObject(name)
						 
						 
						 userCategory = jObj.getString("user_category").toString();
						 
						 
						
						 
						
					} catch (ClientProtocolException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					return null;
				}

				protected void onPostExecute(Void result) {
					
					 if(userCategory.equals("reporter")){
						 pd.cancel();
						 Intent i = new Intent(Launcher.this,EditorPageActivity.class);
						 startActivity(i);
					 }else if(userCategory.equals("user")){
						 pd.cancel();
						 Intent i = new Intent(Launcher.this,UserPageActivity.class);
							startActivity(i);
					 }
					 
					Toast.makeText(getApplicationContext(), "Registeration Details -->"+result2, 1000).show();
					Toast.makeText(getApplicationContext(), "Category -->"+userCategory, 1000).show();
					
					Log.d("asdasd", userCategory);
					
				};
				
			}.execute("");
			
			
		}
		
		/*Editor editor = sharedP.edit();
		editor.getString("key", "value");
		editor.commit();*/
		
		
		
		//--------------------------------------------------------//
       
    }
    
    @Override
    protected void onPause() {
    	// TODO Auto-generated method stub
    	super.onPause();
    	
    	finish();
    }
}
