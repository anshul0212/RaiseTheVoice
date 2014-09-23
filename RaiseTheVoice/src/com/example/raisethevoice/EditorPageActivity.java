package com.example.raisethevoice;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.raisethevoice.EditorAcceptedFragment.OnAcceptedSelected;
import com.example.raisethevoice.EditorInboxFragment.OnRecievePostSelected;
import com.example.raisethevoice.EditorPublicNewsFragment.OnPublicNewsSelected;
import com.example.raisethevoice.EditorRejectedNewsFragment.OnRejectedSelected;
import com.example.raisethevoice.custom.CustomActivity;
import com.example.raisethevoice.model.News;
import com.example.raisethevoice.utility.ServiceHandler;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

public class EditorPageActivity extends CustomActivity implements ActionBar.TabListener , OnRecievePostSelected , OnPublicNewsSelected , OnAcceptedSelected , OnRejectedSelected
{

	private ActionBar actionBar;
	String username,session_id, url_getrecievedposts;
	String url_getpublicposts , url_getacceptedposts , url_getrejectedposts , url_acceptreject ;
	int pos_tab = -1;
	LayoutInflater inflater;
	private String[] tabs = { "Inbox", "Public News","Accepted", "Rejected"};
	
	String title_fragment;
	
	
	public static ArrayList<News> newsArray = new ArrayList<News>();
	FrameLayout frag;
	
	public void onCreate(Bundle savedInstanceState)
	{
		 super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editor_page);
		
		actionBar = getActionBar();
		
		// load it from shared preferences
		//username   = loadUserName();
		
	
	     inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	 	
	     url_getrecievedposts = getString(R.string.url_getrecievedposts);
	     url_getpublicposts = getString(R.string.url_getpublicposts);
	     
	     url_getacceptedposts = getString(R.string.url_getacceptedposts);
	     
	     url_getrejectedposts = getString(R.string.url_getrejectedposts);
	     
	     url_acceptreject = getString(R.string.url_acceptreject);
		    
	     
	     frag = (FrameLayout) findViewById(R.id.content_frame);
		 frag.setVisibility(View.GONE);
		 
	     actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);        
	        // Adding Tabs
	        for (String tab_name : tabs) {
	        	actionBar.addTab(actionBar.newTab().setText(tab_name)
	                    .setTabListener(this));
	        }
	     actionBar.setHomeButtonEnabled(true);
	     
	     
	     

		
		
	}
	JSONArray news = null;
	
	private class GetRecievedPosts extends AsyncTask<Void,Void,Void>
	{

		@Override
		protected void onPreExecute()
		{
			
		}
		
		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			ServiceHandler sh = new ServiceHandler();
			String jsonStr = sh.makeServiceCall( url_getrecievedposts , ServiceHandler.GET );
		     
            Log.d("Response: ", "> " + jsonStr);
			
 
            newsArray = getNewsInArrayList(jsonStr);
            Log.d("newsArray: ", "> " + newsArray);
            
			return null;
		}

		protected ArrayList<News> getNewsInArrayList(String jsonStr)
		{
			
			ArrayList<News> newsArray = new ArrayList<News>();
		News newsObj;
           if (jsonStr != null) {
        	   
                try {
                	  
                	 news = new JSONArray(jsonStr);
                	 Log.d("json=", news.toString());
              
                    	if(news != null)
                    	{
                    		for (int i = 0; i < news.length(); i++) 
                    		{
                       
                    		
                    			JSONObject c = news.getJSONObject(i);
                                
                    			Log.d("list inbox: ", "> " + c);
                    			String titleString = c.getString("title"); 
                    			String descString = c.getString("desc"); 
                            	String nid = c.getString("nid");
                            	String acc_by = c.getString("acc_by");
                            	
                        			newsObj = new News();
                        			newsObj.setTitle(titleString);
                        			newsObj.setDesc(descString);
                        			newsObj.setNid(Integer.parseInt(nid));
                        			newsObj.setAcc_by(Integer.parseInt(acc_by));
                        			Log.d("list inbox: ", "> " + newsObj.getNid());
                        			
                        			newsArray.add(newsObj);
                    		}
                    }
                    else
                    	return null;
                    
                    
                	
                } catch (JSONException e) {
                    e.printStackTrace();
                  
            } 
            }else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }
        	
        	//return arrayListUser;
			
			return newsArray;
		}
		
		@Override
		protected void onPostExecute(Void result)
		{
			android.app.Fragment f = null;
			
			if(pos_tab==0){
		
			title_fragment = "New";
			f = new com.example.raisethevoice.EditorInboxFragment();
			}
			else

				if(pos_tab==1){
			
				f = new com.example.raisethevoice.EditorPublicNewsFragment();
				}
				
			
			if (f != null)
			{
				
				Log.d("replace position=",String.valueOf(pos_tab));
				frag.setVisibility(View.VISIBLE);
				while (getFragmentManager().getBackStackEntryCount() > 0)
				{
					getFragmentManager().popBackStackImmediate();
				}
				getFragmentManager().beginTransaction()
						.replace(R.id.content_frame, f).addToBackStack(title_fragment)
						.commit();
			}
		}
		
		
	}
	
	
	
	private class GetPublicPosts extends AsyncTask<Void,Void,Void>
	{

		@Override
		protected void onPreExecute()
		{
			
		}
		
		@Override
		protected Void doInBackground(Void... arg0) {
		
			ServiceHandler sh = new ServiceHandler();
			String jsonStr = sh.makeServiceCall( url_getpublicposts , ServiceHandler.GET );
		     
            Log.d("Response: ", "> " + jsonStr);
			
 
            newsArray = getNewsInArrayList(jsonStr);
            Log.d("newsArray: ", "> " + newsArray);
            
			return null;
		}

		protected ArrayList<News> getNewsInArrayList(String jsonStr)
		{
			
			ArrayList<News> newsArray = new ArrayList<News>();
		News newsObj;
           if (jsonStr != null) {
        	   
                try {
                	  
                	 news = new JSONArray(jsonStr);
                	 Log.d("json=", news.toString());
              
                    	if(news != null)
                    	{
                    		for (int i = 0; i < news.length(); i++) 
                    		{
                       
                    		
                    			JSONObject c = news.getJSONObject(i);
                        
                    			Log.d("list: ", "> " + c);
                    			String titleString = c.getString("title"); 
                    			String descString = c.getString("desc"); 
                            	String nid = c.getString("nid");
                            	String acc_by = c.getString("acc_by");
                            	
                        			newsObj = new News();
                        			newsObj.setTitle(titleString);
                        			newsObj.setDesc(descString);
                        			newsObj.setNid(Integer.parseInt(nid));
                        			newsObj.setAcc_by(Integer.parseInt(acc_by));
                        			newsArray.add(newsObj);
                    		}
                    }
                    else
                    	return null;
                    
                    
                	
                } catch (JSONException e) {
                    e.printStackTrace();
                  
            } 
            }else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }
        	
        	//return arrayListUser;
			
			return newsArray;
		}
		
		@Override
		protected void onPostExecute(Void result)
		{
Fragment f = null;
			
			if(pos_tab==0){
		
			title_fragment = "New";
			f = new com.example.raisethevoice.EditorInboxFragment();
			}
			else

				if(pos_tab==1){
			
				f = new com.example.raisethevoice.EditorPublicNewsFragment();
				}
				
			
			if (f != null)
			{
				
				Log.d("replace position=",String.valueOf(pos_tab));
				frag.setVisibility(View.VISIBLE);
				while (getFragmentManager().getBackStackEntryCount() > 0)
				{
					getFragmentManager().popBackStackImmediate();
				}
				getFragmentManager().beginTransaction()
						.replace(R.id.content_frame, f).addToBackStack(title_fragment)
						.commit();
			}
			
		}
		
		
	}
	

	private class GetAcceptedPosts extends AsyncTask<Void,Void,Void>
	{

		@Override
		protected void onPreExecute()
		{
			
		}
		
		@Override
		protected Void doInBackground(Void... arg0) {
		
			ServiceHandler sh = new ServiceHandler();
			String jsonStr = sh.makeServiceCall( url_getacceptedposts , ServiceHandler.GET );
		     
            Log.d("Response: ", "> " + jsonStr);
			
 
            newsArray = getNewsInArrayList(jsonStr);
            Log.d("newsArray: ", "> " + newsArray);
            
			return null;
		}

		protected ArrayList<News> getNewsInArrayList(String jsonStr)
		{
			
			ArrayList<News> newsArray = new ArrayList<News>();
		News newsObj;
           if (jsonStr != null) {
        	   
                try {
                	  
                	 news = new JSONArray(jsonStr);
                	 Log.d("json=", news.toString());
              
                    	if(news != null)
                    	{
                    		for (int i = 0; i < news.length(); i++) 
                    		{
                       
                    		
                    			JSONObject c = news.getJSONObject(i);
                        
                    			Log.d("list: ", "> " + c);
                    			String titleString = c.getString("title"); 
                    			String descString = c.getString("desc"); 
                            	String nid = c.getString("nid");
                            	String acc_by = c.getString("acc_by");
                            	
                        			newsObj = new News();
                        			newsObj.setTitle(titleString);
                        			newsObj.setDesc(descString);
                        			newsObj.setNid(Integer.parseInt(nid));
                        			newsObj.setAcc_by(Integer.parseInt(acc_by));
                        	
                        			newsArray.add(newsObj);
                    		}
                    }
                    else
                    	return null;
                    
                    
                	
                } catch (JSONException e) {
                    e.printStackTrace();
                  
            } 
            }else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }
        	
        	//return arrayListUser;
			
			return newsArray;
		}
		
		@Override
		protected void onPostExecute(Void result)
		{
Fragment f = null;
			
			if(pos_tab==0){
		
			title_fragment = "New";
			f = new com.example.raisethevoice.EditorInboxFragment();
			}
			else

				if(pos_tab==2){
			
				f = new com.example.raisethevoice.EditorAcceptedFragment();
				}
				
			
			if (f != null)
			{
				
				Log.d("replace position=",String.valueOf(pos_tab));
				frag.setVisibility(View.VISIBLE);
				while (getFragmentManager().getBackStackEntryCount() > 0)
				{
					getFragmentManager().popBackStackImmediate();
				}
				getFragmentManager().beginTransaction()
						.replace(R.id.content_frame, f).addToBackStack(title_fragment)
						.commit();
			}
			
		}
		
		
	}
	

	private class GetRejectedPosts extends AsyncTask<Void,Void,Void>
	{

		@Override
		protected void onPreExecute()
		{
			
		}
		
		@Override
		protected Void doInBackground(Void... arg0) {
		
			ServiceHandler sh = new ServiceHandler();
			String jsonStr = sh.makeServiceCall( url_getacceptedposts , ServiceHandler.GET );
		     
            Log.d("Response: ", "> " + jsonStr);
			
 
            newsArray = getNewsInArrayList(jsonStr);
            Log.d("newsArray: ", "> " + newsArray);
            
			return null;
		}

		protected ArrayList<News> getNewsInArrayList(String jsonStr)
		{
			
			ArrayList<News> newsArray = new ArrayList<News>();
		News newsObj;
           if (jsonStr != null) {
        	   
                try {
                	  
                	 news = new JSONArray(jsonStr);
                	 Log.d("json=", news.toString());
              
                    	if(news != null)
                    	{
                    		for (int i = 0; i < news.length(); i++) 
                    		{
                       
                    		
                    			JSONObject c = news.getJSONObject(i);
                        
                    			Log.d("list: ", "> " + c);
                    			String titleString = c.getString("title"); 
                    			String descString = c.getString("desc"); 
                            	String nid = c.getString("nid");
                            	String acc_by = c.getString("acc_by");
                            	
                        			newsObj = new News();
                        			newsObj.setTitle(titleString);
                        			newsObj.setDesc(descString);
                        			newsObj.setNid(Integer.parseInt(nid));
                        			newsObj.setAcc_by(Integer.parseInt(acc_by));
                        	
                        			newsArray.add(newsObj);
                    		}
                    }
                    else
                    	return null;
                    
                    
                	
                } catch (JSONException e) {
                    e.printStackTrace();
                  
            } 
            }else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }
        	
        	//return arrayListUser;
			
			return newsArray;
		}
		
		@Override
		protected void onPostExecute(Void result)
		{
Fragment f = null;
			
			if(pos_tab==0){
		
			title_fragment = "New";
			f = new com.example.raisethevoice.EditorInboxFragment();
			}
			else

				if(pos_tab==3){
			
				f = new com.example.raisethevoice.EditorRejectedNewsFragment();
				}
				
			
			if (f != null)
			{
				
				Log.d("replace position=",String.valueOf(pos_tab));
				frag.setVisibility(View.VISIBLE);
				while (getFragmentManager().getBackStackEntryCount() > 0)
				{
					getFragmentManager().popBackStackImmediate();
				}
				getFragmentManager().beginTransaction()
						.replace(R.id.content_frame, f).addToBackStack(title_fragment)
						.commit();
			}
			
		}
		
		
	}
	
	
	//Loading username from shared preferences
			private String loadUserName() {
				SharedPreferences sharedPreferences = getSharedPreferences("Register",0);
				String username = sharedPreferences.getString("username", null);
				return username;
			}

		
	//public Class 
	
	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
		pos_tab = tab.getPosition();
			
			Log.d("Position",String.valueOf(pos_tab));
			Fragment f = null;
			//if(!loading){
				//Log.d("1----",String.valueOf(pos_tab));
				
			if(pos_tab==0){
				Log.d("profile----",String.valueOf(pos_tab));
				GetRecievedPosts getRecievedPost = new GetRecievedPosts();
			     getRecievedPost.execute(); 
				
				title_fragment = "New";
				f = new com.example.raisethevoice.EditorInboxFragment();
			}
			else 
				if(pos_tab==1){
					GetPublicPosts getPublicPosts = new GetPublicPosts();
	  				getPublicPosts.execute();
	  				
				title_fragment = "Public News";
			
				//f = new com.example.raisethevoice.EditorPublicNewsFragment();
				
			}
				else 
  				if(pos_tab==2){
  				Log.d("Connection----",String.valueOf(pos_tab));
  				title_fragment = "Added";
  				
  				GetAcceptedPosts getAcceptedPosts = new GetAcceptedPosts();
  				getAcceptedPosts.execute();
  				
  				
  				//f = new com.events.ui.AddedConnection();
  				}
  				else 
  	  				if(pos_tab==3){
  	  				Log.d("Connection----",String.valueOf(pos_tab));
  	  				title_fragment = "Added";
  	  				
  	  			GetRejectedPosts getRejectedPosts = new GetRejectedPosts();
  	  		getRejectedPosts.execute();
  	  				
  	  				
  	  				//f = new com.events.ui.AddedConnection();
  	  				}
			
			
		/*	
			if (f != null)
			{
				
				Log.d("replace position=",String.valueOf(pos_tab));
				frag.setVisibility(View.VISIBLE);
				while (getSupportFragmentManager().getBackStackEntryCount() > 0)
				{
					getSupportFragmentManager().popBackStackImmediate();
				}
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.content_frame, f).addToBackStack(title_fragment)
						.commit();
			}
			*/
			
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void OnRecievePostSelected(News news) {
		// TODO Auto-generated method stub
		Log.d("user list selected=",news.getDesc());
			Intent i = new Intent(this, DetailNewsActivity.class);
		   Bundle bundle = new Bundle();
		   bundle.putString("title", news.getTitle());
		   bundle.putString("description", news.getDesc());
		   Log.d("After Bundle=",news.getTitle());
			
		   i.putExtras(bundle);
			startActivity(i);
	}


	@Override
	public void onPublicNewsSelected(News news) {
		// TODO Auto-generated method stub
		Log.d("user list selected=",news.getDesc());
		Intent i = new Intent(this, DetailNewsActivity.class);
	   Bundle bundle = new Bundle();
	   bundle.putString("title", news.getTitle());
	   bundle.putString("description", news.getDesc());
	   Log.d("After Bundle=",news.getTitle());
		
	   i.putExtras(bundle);
		startActivity(i);
	}


	@Override
	public void onAcceptedSelected(News news) {
		// TODO Auto-generated method stub
		Log.d("user list selected=",news.getDesc());
		Intent i = new Intent(this, DetailNewsActivity.class);
	   Bundle bundle = new Bundle();
	   bundle.putString("title", news.getTitle());
	   bundle.putString("description", news.getDesc());
	   Log.d("After Bundle=",news.getTitle());
		
	   i.putExtras(bundle);
		startActivity(i);
	}
	
	@Override
	public void onRejectedSelected(News news) {
		// TODO Auto-generated method stub
		Log.d("user list selected=",news.getDesc());
		Intent i = new Intent(this, DetailNewsActivity.class);
	   Bundle bundle = new Bundle();
	   bundle.putString("title", news.getTitle());
	   bundle.putString("description", news.getDesc());
	   bundle.putInt("nid", news.getNid());
	   bundle.putInt("acc_by", news.getAcc_by());
	   
	   Log.d("After Bundle=",news.getTitle());
		
	   i.putExtras(bundle);
		startActivity(i);
	}

}
