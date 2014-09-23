package com.example.raisethevoice;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.raisethevoice.custom.CustomActivity;
import com.example.raisethevoice.model.News;
import com.example.raisethevoice.utility.ServiceHandler;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.support.v4.app.Fragment;

public class DetailNewsActivity extends CustomActivity implements OnClickListener{

	private ActionBar actionBar;
	String username,session_id, url_getrecievedposts, url_acceptreject;
	int pos_tab = -1;
	LayoutInflater inflater;
	
	
	public void onCreate(Bundle savedInstanceState)
	{
		 super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_news);
		
		actionBar = getActionBar();
		getActionBar().setTitle("Detail View");
		 Intent i = getIntent();
		 String  flag ;
		 
		 
		EditText titleEditText = (EditText)findViewById(R.id.titleDataDetailActId);
		titleEditText.setEnabled(true);
		
		EditText descEditText = (EditText)findViewById(R.id.descDataDetailActId);
		titleEditText.setEnabled(true);
		
		int nid = i.getExtras().getInt("nid");
		int acc_by = i.getExtras().getInt("acc_by");
		
		url_acceptreject = getString(R.string.url_acceptreject);
		 url_acceptreject = url_acceptreject+"?nid="+nid+"&acc_by="+acc_by+"&submit=submit";
		 
		 
		Log.d("niid =", nid+"");
		Log.d("acc_by =", acc_by+"");
		
		   		
		   
		   
		if(i.hasExtra("title"))
		{
			titleEditText.setText(i.getExtras().getString("title"));
			descEditText.setText(i.getExtras().getString("description"));
		    	
		}
		
		
		Button btnAccept = (Button)findViewById(R.id.AcceptDetailActId);
		
		btnAccept.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v)
			{
				url_acceptreject = url_acceptreject+"&flag=1";
				AcceptReject accrej = new AcceptReject();
				
				accrej.execute();
			}
		});
	
	     inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	 	
	   
		
		
	}
	

	private class AcceptReject extends AsyncTask<Void,Void,Void>
	{

		@Override
		protected void onPreExecute()
		{
			
		}
		
		@Override
		protected Void doInBackground(Void... arg0) {
		
			ServiceHandler sh = new ServiceHandler();
			String jsonStr = sh.makeServiceCall( url_acceptreject , ServiceHandler.GET );
		     
            Log.d("Response: ", "> " + jsonStr);
			
 
			return null;
		}

		
		
		@Override
		protected void onPostExecute(Void result)
		{

		}
		
		
	}
	

}
	

