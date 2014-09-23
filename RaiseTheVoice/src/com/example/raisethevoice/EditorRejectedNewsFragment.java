package com.example.raisethevoice;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.raisethevoice.custom.CustomFragment;
import com.example.raisethevoice.model.News;

public class EditorRejectedNewsFragment extends CustomFragment implements  OnItemClickListener{

	OnRejectedSelected onRejectedSelected;
	Context context;
	ArrayList<News> newsItems;
	ListView list;
	EditorPublicPostAdapter publicPostAdapter;
	@Override
	public View onCreateView(LayoutInflater layoutInflater, ViewGroup container , Bundle savedInstance ){
		
		View view = layoutInflater.inflate(R.layout.fragment_editor_inbox, null);
		
		//setHasOptionsMenu(true);
		
		context = container.getContext();
		newsItems = new ArrayList<News>();
		newsItems = EditorPageActivity.newsArray;
		
		Log.d("newsItems", newsItems.toString());
		
		list = (ListView)view.findViewById(R.id.list);
		Log.d("1", newsItems.toString());
		publicPostAdapter = new EditorPublicPostAdapter(this.getActivity().getBaseContext(), newsItems);
		Log.d("2", newsItems.toString());
		
		list.setAdapter(publicPostAdapter);
		list.setOnItemClickListener(this);
	    
		return view;
	}
	
	@Override
	public void onAttach(Activity activity) {
		onRejectedSelected=(OnRejectedSelected)this.getActivity();
		super.onAttach(activity);
		
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		News news=(News)newsItems.get(arg2);
		Log.d("news==============",news.toString());
		onRejectedSelected.onRejectedSelected(news);
	}
	

	
	public interface OnRejectedSelected
	{
		public void onRejectedSelected(News news);

	}

	
    
    private class EditorPublicPostAdapter extends BaseAdapter
	{

        
        private Context context;
        private ArrayList<News> newsItems;
        private String session_id, username;
    	
        public EditorPublicPostAdapter(Context context, ArrayList<News> userItems){
            this.context = context;
            this.newsItems = userItems;
        }
        
		/* (non-Javadoc)
		 * @see android.widget.Adapter#getCount()
		 */
		@Override
		public int getCount()
		{
			return newsItems.size();
		}

		/* (non-Javadoc)
		 * @see android.widget.Adapter#getItem(int)
		 */
		@Override
		public String getItem(int position)
		{
			return newsItems.get(position).getTitle();
		}

		/* (non-Javadoc)
		 * @see android.widget.Adapter#getItemId(int)
		 */
		@Override
		public long getItemId(int position)
		{
			return position;
		}

		
		
		
		/* (non-Javadoc)
		 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
		 */
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent )
		{

		//	LayoutInflater inflater = null;
			
			View view=convertView;
			Log.d("sdsdsdsd=", "Sdsdsd");
		    
//	        View vi_position=convertView;
			  if(convertView==null)
		        {
		            LayoutInflater inflater = (LayoutInflater)
		                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		               view = inflater.inflate(R.layout.layout_listview_item, null);
		        }
	        String newsTitle = getItem(position);
	        
	         // load it from shared preferences
		//	username=loadUserName(); 
			
			Log.d("newsTitle=", newsTitle);
	    
		    ImageView imgAccept = (ImageView)view.findViewById(R.id.imageView1);
			/*imgAccept.setOnClickListener(new OnClickListener() {
			       
				@Override
				public void onClick(View v) {
					
				  Log.d("Accept Clicked", "Clicked yesmsg");
				  
				  LinearLayout vwParentRow = (LinearLayout)v.getParent().getParent();
			         
			        TextView child = (TextView)vwParentRow.getChildAt(0);
			      //  Button btnChild = (Button)vwParentRow.getChildAt(1);
			        //btnChild.setText(child.getText());
			      //  btnChild.setText("I've been clicked!");
			        Log.d("CLicked on Accept", child.getText().toString().split(" ")[1]);
			        String uname = child.getText().toString().split(" ")[1] ;
			        int c = Color.CYAN;
			        
			        url_connection_respond_android_accept = getString(R.string.url_connection_respond_android_accept);
					url_connection_respond_android_accept =url_connection_respond_android_accept+"session_id="+session_id+"&username="+username+"&uname="+uname;
			    
			        vwParentRow.setBackgroundColor(c); 
			        vwParentRow.refreshDrawableState();
			        
				   // Accept accept=new Accept();
				   // accept.execute();
				  
				//  notifyDataSetChanged();
				 }
			 });
			 
			 ImageView imgReject = (ImageView)vi.findViewById(R.id.rejectImage);
			 imgReject.setOnClickListener(new OnClickListener() {
			       
				 @Override
			        public void onClick(View arg0) {
			        	Log.d("Reject Clicked", "Clicked yesmsg");
			        	Reject reject=new Reject();
			        	reject.execute();
			        }	
			  });
	        */
		    
	        TextView lbl = (TextView) view.findViewById(R.id.id_news_title);
	        lbl.setText("Title -"+newsTitle );
	    	return view;
		}

	}

	
}
