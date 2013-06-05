package com.hav3n.tvshowmanager;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hav3n.tvshowmanager.constants.Constants;

public class SearchListAdapter extends BaseAdapter
{
	ArrayList<HashMap<String, String>> showInfoEntries;
	ArrayList<String> imageUrls;
	private static LayoutInflater inflater = null;
	public ImageLoader imageLoader;
	Activity a;
	TextView showName;
	ImageView showImage;

	public SearchListAdapter(Activity a, ArrayList<HashMap<String, String>> showInfoEntries, ArrayList<String> imageUrls)
	{
		this.a = a;
		this.showInfoEntries = showInfoEntries;
		this.imageUrls = imageUrls;
		imageLoader = new ImageLoader(a.getApplicationContext());
		inflater = (LayoutInflater) a.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount()
	{

		return showInfoEntries.size();
	}

	@Override
	public Object getItem(int position)
	{
		return showInfoEntries.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View v = convertView;

		if (convertView == null)
			v = inflater.inflate(R.layout.search_listrow, null);

		showName = (TextView) v.findViewById(R.id.title);
		showImage = (ImageView) v.findViewById(R.id.list_image);

		HashMap<String, String> map = new HashMap<String, String>();
		String images;
		map = showInfoEntries.get(position);
		images = imageUrls.get(position);
		
		Log.i("INFO",map.get(Constants.TAG_NAME));

		showName.setText(map.get(Constants.TAG_NAME));
		imageLoader.DisplayImage(images, showImage);

		return v;
	}

}
