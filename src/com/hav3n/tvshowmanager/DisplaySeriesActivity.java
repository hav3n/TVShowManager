package com.hav3n.tvshowmanager;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.lang3.StringEscapeUtils;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hav3n.tvshowmanager.constants.Constants;

public class DisplaySeriesActivity extends Activity
{
	ArrayList<HashMap<String, String>> showInfoEntries;
	int position;
	ActionBar actionBar;
	HashMap<String, String> map;
	ArrayList<ArrayList<String>> showGenreEntries;
	ArrayList<String> showGenres, imageUrls;
	TextView statusText, runtimeText, networkText, airdayText, timezoneText, summaryText,genresText;
	ImageView screenCap;
	String imageUrl;
	LargeImageLoader imageLoader;
	Button showEpisodeListButton;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		Log.i("INFO","In Display Show Acitivity");
		showInfoEntries = (ArrayList<HashMap<String, String>>) getIntent().getSerializableExtra("showInfoEntries");
		showGenreEntries = (ArrayList<ArrayList<String>>) getIntent().getSerializableExtra("showGenreEntries");
		imageUrls = (ArrayList<String>) getIntent().getSerializableExtra("imageUrls");
		position = getIntent().getIntExtra("position", -1);

		map = new HashMap<String, String>();
		showGenres = new ArrayList<String>();

		showGenres = showGenreEntries.get(position);
		map = showInfoEntries.get(position);
		actionBar = getActionBar();
		actionBar.setTitle(showInfoEntries.get(position).get(Constants.TAG_SHOWNAME));

		setContentView(R.layout.show_info);

		statusText = (TextView) findViewById(R.id.status_text);
		runtimeText = (TextView) findViewById(R.id.runtime_text);
		networkText = (TextView) findViewById(R.id.network_text);
		airdayText = (TextView) findViewById(R.id.airday_text);
		timezoneText = (TextView) findViewById(R.id.timezone_text);
		summaryText = (TextView) findViewById(R.id.summary_text);
		screenCap = (ImageView) findViewById(R.id.screencap);
		genresText =(TextView) findViewById(R.id.genres_text);
		showEpisodeListButton = (Button) findViewById(R.id.episodelist_button);

		statusText.setText(map.get(Constants.TAG_STATUS));
		runtimeText.setText(map.get(Constants.TAG_RUNTIME));
		networkText.setText(map.get(Constants.TAG_NETWORK));
		airdayText.setText(map.get(Constants.TAG_AIRDAY));
		timezoneText.setText(map.get(Constants.TAG_TIMEZONE));
		summaryText.setText(StringEscapeUtils.unescapeHtml4(map.get(Constants.TAG_SUMMARY)));

		imageUrl = imageUrls.get(position);

		imageLoader = new LargeImageLoader(this);
		imageLoader.DisplayImage(imageUrl, screenCap);
		
		
		String temp=new String("");
		
		for(String s:showGenres)
		{
			temp+=(s+" ,");
		}
		
		genresText.setText(temp);
		

	}

}
