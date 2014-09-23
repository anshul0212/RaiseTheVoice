package com.example.raisethevoice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.DefaultClientConnection;
import org.apache.http.message.BasicNameValuePair;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

@SuppressLint("NewApi") public class Register extends Activity{
	
	EditText username,password,imei;
	Button submit;
	TelephonyManager telephonyManager;
	
	String deviceIMEI;
	String  mobileNumber;
	
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
	
	SharedPreferences sharedP;
	
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_register);
			
			username = (EditText)findViewById(R.id.register_edt_name);
			password = (EditText)findViewById(R.id.register_edt_password);
			imei = (EditText)findViewById(R.id.register_edt_IMEI);
			
			sharedP = getSharedPreferences("Register", Context.MODE_PRIVATE);
			
			telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
			
			client = new DefaultHttpClient();
		//	post = new HttpPost("http://www.vivekandroid.esy.es/rtv/insert.php");
		//	get = new HttpGet("http://www.vivekandroid.esy.es/rtv/insert.php");
		}
		
		@Override
		protected void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
			
			
			deviceIMEI = telephonyManager.getDeviceId();
			
		//	mobileNumber = telephonyManager.getSimSerialNumber();
			
	//		mobileNumber = telephonyManager.getLine1Number();
			
			Toast.makeText(getApplicationContext(), "IMEI -->"+deviceIMEI, 1000).show();
		//	Toast.makeText(getApplicationContext(), "Mobile -->"+mobileNumber.toString(), 1000).show();

			
			imei.setText(deviceIMEI);
			
			
		}
		
		
		
		public void registerButtonClicked(View v){
			
		//	Intent i = new Intent(Register.this, UserPageActivity.class);
		//	startActivity(i);
			
		/*	 List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
			 postParameters.add(new BasicNameValuePair("user_name", username.getText().toString()));
			 postParameters.add(new BasicNameValuePair("mob_no", password.getText().toString() ));
			 postParameters.add(new BasicNameValuePair("imei", imei.getText().toString()));
			 
			 try {
				 
				formEntity = new UrlEncodedFormEntity(postParameters);
				
				post.setEntity(formEntity);
				
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			
			new AsyncTask<String, String, Void>(){

				@Override
				protected Void doInBackground(String... params) {
					// TODO Auto-generated method stub
					
					
					get = new HttpGet("http://www.vivekandroid.esy.es/rtv/insert_user.php?password="+password.getText().toString()+"&imei="+deviceIMEI+"&user_name="+username.getText().toString()+"&submit=submit");
					
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
						 
						 
						
						
					} catch (ClientProtocolException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					return null;
				}

				protected void onPostExecute(Void result) {
					
					Toast.makeText(getApplicationContext(), "Registeration Details -->"+result2, 1000).show();
					
					Log.d("MyResponse", result2);
					 if(result2.equals("successnull")){
						 
						 Editor editor = sharedP.edit();
						 editor.putString("username", username.getText().toString());
						 editor.putString("password", password.getText().toString());
						 editor.putString("imei", deviceIMEI);
						 editor.commit();
						 
						 Intent i = new Intent(Register.this, UserPageActivity.class);
								startActivity(i);
								
					 }
				};
				
			}.execute("");
		
		}
		
		@Override
		protected void onPause() {
			// TODO Auto-generated method stub
			super.onPause();
			finish();
		}

}
