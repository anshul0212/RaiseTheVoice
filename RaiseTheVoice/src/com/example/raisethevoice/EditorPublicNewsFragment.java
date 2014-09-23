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

public class EditorPublicNewsFragment extends CustomFragment implements  OnItemClickListener{

	OnPublicNewsSelected onPublicNewsSelected;
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
		onPublicNewsSelected=(OnPublicNewsSelected)this.getActivity();
		super.onAttach(activity);
		
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		News news=(News)newsItems.get(arg2);
		Log.d("news==============",news.toString());
		onPublicNewsSelected.onPublicNewsSelected(news);
	}
	

	
	public interface OnPublicNewsSelected
	{
		public void onPublicNewsSelected(News news);

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
			
		    
	        TextView lbl = (TextView) view.findViewById(R.id.id_news_title);
	        lbl.setText("Title -"+newsTitle );
	    	return view;
		}

	}

	
}
