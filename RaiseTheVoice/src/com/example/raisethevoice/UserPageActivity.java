package com.example.raisethevoice;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.raisethevoice.MainActivitysd.PlaceholderFragment;
import com.example.raisethevoice.UserPublicNewsFragment.OnUserPublicNewsSelected;
import com.example.raisethevoice.model.News;
import com.example.raisethevoice.utility.ServiceHandler;
import com.squareup.picasso.Picasso;

@SuppressLint("NewApi") 
public class UserPageActivity extends Activity  implements OnUserPublicNewsSelected{
	
	ActionBar actionBar;
	LayoutInflater inflater;
	ActionBar.TabListener tablistener;
	ViewPager mViewPager;
	int pos_tab = -1;
	String url_getpublicposts;
	public static ArrayList<News> newsArray = new ArrayList<News>();
	FrameLayout frag;
	
	static String attachmentFile;
	int columnIndex;
	Uri URI = null;
	Intent dummyData;
	
	String data2;
	static ImageView myimage;
	
	static String upLoadServerUri = null;
	
	
	static HttpClient client;
	HttpPost post;
	static HttpResponse response;
	static HttpGet get;
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
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_page);
		
		actionBar = getActionBar();
		 url_getpublicposts = getString(R.string.url_getpublicposts);
	     
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		client = new DefaultHttpClient();
		
		//actionBar.setDisplayShowCustomEnabled(true);
	//	actionBar.setDisplayShowTitleEnabled(false);
		
	//	 mViewPager = (ViewPager) findViewById(R.id.pager);
		
		
		tablistener = new ActionBar.TabListener() {
			
			@Override
			public void onTabUnselected(Tab tab, FragmentTransaction ft) {
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
						GetPublicPosts getRecievedPost = new GetPublicPosts();
					     getRecievedPost.execute(); 
					     
					    
							
						}
					else 
						if(pos_tab==1){
						Toast.makeText(getApplicationContext(), "Tab 2 Selected", 1000).show();
						
						getFragmentManager().beginTransaction()
						.replace(R.id.user_page_frame, new PlaceholderFragment1()).addToBackStack("sd").commit();
					
						
					}
					
					
			}
			
			@Override
			public void onTabReselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
				
			}
		};
		
		actionBar.addTab(actionBar.newTab().setText("All News").setTabListener(tablistener));
		actionBar.addTab(actionBar.newTab().setText("Post News").setTabListener(tablistener));
		
	/*	Tab tab2 = actionBar.newTab();
		tab2.setText("Post News").setTabListener(tablistener);
		actionBar.addTab(tab2);
		actionBar.selectTab(tab2);*/
		
	}
	
	public  static class PlaceholderFragment1 extends Fragment {

		

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_post_news,
					container, false);
			
			final EditText edtTitle = (EditText)rootView.findViewById(R.id.post_news_EDT_Title);
			final EditText edtDesc = (EditText)rootView.findViewById(R.id.post_news_description);
			
			Button submitNews = (Button)rootView.findViewById(R.id.postNews);
			Button attachImage = (Button)rootView.findViewById(R.id.AttachImage);
			myimage = (ImageView)rootView.findViewById(R.id.post_news_myimage);
			
			submitNews.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
				//	upLoadServerUri = "http://vivekandroid.esy.es/rtv/UploadToServer.php";
					
					new AsyncTask<String, String, Void>(){

						@Override
						protected Void doInBackground(String... params) {
							// TODO Auto-generated method stub
							
						/*	$title = $_GET['title'];
							$img_name = $_GET['img_name'];
							$desc=$_GET['desc'];
							$uid = $_GET['uid'];
							$date = $_GET['date'];
							$flag = $_GET['flag'];
							$acc_by = $_GET['acc_by'];*/
							
							get = new HttpGet("http://vivekandroid.esy.es/rtv/insert_news.php?title="+edtTitle.getText().toString()+"&desc="+edtDesc.getText().toString()+"&submit=submit");
							
					//		try {
								
								try {
									client.execute(get);
								} catch (ClientProtocolException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
								
					/*			 in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
							
								 StringBuffer sb= new StringBuffer("");
								 String line = "";
								 String NL = System.getProperty("line.seperator");
								 
								 while((line = in.readLine())!= null){
									 
									 sb.append(line+NL);
									 
								 }
								 
								 in.close();
								 
								 result2 = sb.toString();
								 
								 JSONObject jObj = new JSONObject(result2);*/
								 
								// JSONObject jObj2 = jObj.getJSONObject(name)
								 
								 
							//	 userCategory = jObj.getString("user_category").toString();
								 
								 
								
								 
								
						/*	} catch (ClientProtocolException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}*/
							
							return null;
						}

						protected void onPostExecute(Void result) {
							
							/* if(userCategory.equals("reporter")){
								 pd.cancel();
								 Intent i = new Intent(UserPageActivity.this,EditorPageActivity.class);
								 startActivity(i);
							 }else if(userCategory.equals("user")){
								 pd.cancel();
								 Intent i = new Intent(Launcher.this,UserPageActivity.class);
									startActivity(i);
							 }*/
							 
						//	Toast.makeText(getApplicationContext(), "Registeration Details -->"+result2, 1000).show();
						//	Toast.makeText(getApplicationContext(), "Category -->"+userCategory, 1000).show();
							
						//	Log.d("asdasd", userCategory);
							
						};
						
					}.execute("");
					

					 
					 
					
				}
			});
			
			
			attachImage.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					
					
					Intent intent = new Intent();
				    intent.setType("*/*");
				    intent.setAction(Intent.ACTION_GET_CONTENT);
				    intent.putExtra("return-data", true);
				    startActivityForResult(
				                 Intent.createChooser(intent, "Complete action using"),
				                 10);
					
					
					
					
				}
			});
			
			return rootView;
		}
	}
	JSONArray news = null;	
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
			String title_fragment ="";
Fragment f = null;
			
			if(pos_tab==0){
		
			title_fragment = "New";
			f = new com.example.raisethevoice.UserPublicNewsFragment();
			}
			else

				if(pos_tab==1){
			
				f = new com.example.raisethevoice.UserPublicNewsFragment();
				}
				
			
			if (f != null)
			{
				
				Log.d("replace position=",String.valueOf(pos_tab));
				//frag.setVisibility(View.VISIBLE);
				while (getFragmentManager().getBackStackEntryCount() > 0)
				{
					getFragmentManager().popBackStackImmediate();
				}
				getFragmentManager().beginTransaction()
						.replace(R.id.user_page_frame, f).addToBackStack(title_fragment)
						.commit();
			}
			
		}
		
		
	}
	
	
	// Instances of this class are fragments representing a single
	// object in our collection.
	public static class DemoObjectFragment extends Fragment {
	    public static final String ARG_OBJECT = "object";

	    @Override
	    public View onCreateView(LayoutInflater inflater,
	            ViewGroup container, Bundle savedInstanceState) {
	        // The last two arguments ensure LayoutParams are inflated
	        // properly.
	        View rootView = inflater.inflate(R.layout.my_fragment, container, false);
	     
	        

	        return rootView;
	    }

	}


	@Override
	public void OnUserPublicNewsSelected(News news) {
		// TODO Auto-generated method stub
		Intent i = new Intent(this, DetailNewsActivity.class);
		   Bundle bundle = new Bundle();
		   bundle.putString("title", news.getTitle());
		   bundle.putString("description", news.getDesc());
		   Log.d("After Bundle=",news.getTitle());
			
		   i.putExtras(bundle);
			startActivity(i);
	}
	
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		if(requestCode==10 && resultCode == RESULT_OK){
			
			Uri selectedImage = data.getData();
			
			dummyData = data;
			
		//	MediaStore.Files.getContentUri(selectedImage.toString());
		//	String[] filePathColumn = { MediaStore.Images.Media.DATA };
			String[] filePathColumn = { MediaStore.Files.FileColumns.DATA };
			
			
			Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
	        cursor.moveToFirst();
			
	        columnIndex = cursor.getColumnIndex(filePathColumn[0]);
	        attachmentFile = cursor.getString(columnIndex);
	        
	        URI = Uri.parse("file://" + attachmentFile);
	        cursor.close();
	        
	        Toast.makeText(getApplicationContext(), ""+attachmentFile, 2000).show();
	        Log.d("Image", attachmentFile);
	        
	        
	        data2 = dummyData.getData().toString();
	        int i = data2.lastIndexOf("content://");
			data2 = data2.substring(10);
			data2 = "http://"+data2;
			Toast.makeText(getApplicationContext(), ""+data2, 2000).show();
			
			Log.d("Image", data2);
			Log.d("Image", URI+"");
	/*		Picasso.with(UserPageActivity.this)
			.
	        .load("" + "/" + eventInfo.getImageName())
	        .placeholder(R.drawable.calendar)
	        .error(R.drawable.calendar)
	        .into(target);
	        
	        
			*/
			
		/*	Bitmap b = BitmapFactory.decodeFile(attachmentFile);
				myimage.setImageBitmap(b);
				*/
           /* if (URI != null) {
               emailIntent.putExtra(Intent.EXTRA_STREAM, URI);
            	}*/

			
		/*	Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
	        cursor.moveToFirst();
			
	        columnIndex = cursor.getColumnIndex(filePathColumn[0]);
	        attachmentFile = cursor.getString(columnIndex);
	        
	        tv3.setText("File ="+attachmentFile);
	        
	        URI = Uri.parse("file://" + attachmentFile);
	        cursor.close();*/
	        
		//	Bitmap b = BitmapFactory.decodeFile(attachmentFile);
		//	image.setImageBitmap(b);
		}
	}
	
	
}
