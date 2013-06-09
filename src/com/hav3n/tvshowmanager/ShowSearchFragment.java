package com.hav3n.tvshowmanager;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hav3n.tvshowmanager.constants.Constants;

public class ShowSearchFragment extends Fragment
{
	EditText searchBar;
	ListView searchResults;
	ProgressDialog progressDialog;
	String url;
	ArrayList<HashMap<String, String>> showListEntries, showInfoEntries;
	ArrayList<String> imageUrls;
	ArrayList<String> showInfoUrls;
	Context c;
	SearchListAdapter adapter;
	ArrayList<String> genres;
	ArrayList<ArrayList<String>> showGenreEntries;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.fragment_search, container, false);

		searchBar = (EditText) rootView.findViewById(R.id.searchBar);
		searchResults = (ListView) rootView.findViewById(android.R.id.list);

		progressDialog = new ProgressDialog(getActivity());

		if (showInfoEntries != null && imageUrls != null)
		{
			Log.i("INFO", "Setting Adapter");
			adapter = new SearchListAdapter(getActivity(), showListEntries, imageUrls);

			searchResults.setAdapter(adapter);
		}

		searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener()
		{

			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
			{
				if (actionId == EditorInfo.IME_ACTION_SEARCH)
				{
					try
					{

						String query = searchBar.getText().toString();
						String urlEncodedQuery = URLEncoder.encode(query, "utf-8");
						url = String.format(Constants.SEARCH, urlEncodedQuery);
						Log.i("INFO", "in Editor Action Listener");
						InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(searchBar.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);

						new DownloadXMLData().execute();

						Log.i("INFO", "Im here");
					} catch (UnsupportedEncodingException e)
					{
						Toast.makeText(getActivity().getApplicationContext(), "Could not encode url", Toast.LENGTH_LONG).show();
					}
					return true;

				} else

					return false;
			}
		});

		searchResults.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View parentView, int position, long id)
			{
				Log.i("INFO", "Starting Activity");
				Intent intent = new Intent(getActivity(), DisplaySeriesActivity.class);
				intent.putExtra("showInfoEntries", showInfoEntries);
				intent.putExtra("position", position);
				intent.putExtra("showGenreEntries", showGenreEntries);
				intent.putExtra("imageUrls", imageUrls);

				startActivity(intent);

			}
		});

		return rootView;
	}
	private class DownloadXMLData extends AsyncTask<Void, Void, Void>
	{
		XMLParser parser = new XMLParser();
		String showListXML, showInfoXMLEntry;
		ArrayList<String> showInfoXML, singleInfoGenres;
		String s, singleInfoURL, singleInfoXML;

		@Override
		protected void onPreExecute()
		{

			progressDialog.setCancelable(false);
			progressDialog.setMessage("Please Wait, Searching");
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			showListXML = new String();
			showInfoXML = new ArrayList<String>();
			showInfoUrls = new ArrayList<String>();
			showListEntries = new ArrayList<HashMap<String, String>>();
			showInfoEntries = new ArrayList<HashMap<String, String>>();
			showGenreEntries = new ArrayList<ArrayList<String>>();
			imageUrls = new ArrayList<String>();

			progressDialog.show();
			Log.i("INFO", "In Pre Execute");
		}

		@Override
		protected Void doInBackground(Void... params)
		{
			try
			{
				showListXML = parser.getXmlFromUrl(url);
				// progressDialog.setMessage("Data Dowloaded");
				Log.i("INFO", "downloaded List data");
				Log.i("INFO", showListXML);

				// progressDialog.setMessage("Parsing Data");
				showListEntries = parser.parseShowList(showListXML);
				Log.i("INFO", "parsed List data");
				if (showListEntries == null)
					Log.i("INFO", "List is null");
				HashMap<String, String> map = new HashMap<String, String>();

				for (int i = 0; i < showListEntries.size(); i++)
				{
					Log.i("INFO", "Getting urls");
					map = showListEntries.get(i);
					Log.i("INFO", "Getting map");
					s = new String();
					s = Constants.SHOW_INFO;
					Log.i("INFO", "Getting url");

					Log.i("INFO", String.format(s, map.get(Constants.TAG_SHOWID)));
					showInfoUrls.add(String.format(s, map.get(Constants.TAG_SHOWID)));

					Log.i("INFO", "Got urls");

				}

				// ArrayList<String> store = new ArrayList<String>();
				for (int i = 0; i < showInfoUrls.size(); i++)
				{

					singleInfoURL = showInfoUrls.get(i);
					singleInfoXML = parser.getXmlFromUrl(singleInfoURL);
					singleInfoGenres = parser.parseShowGenres();
					showInfoEntries.add(parser.parseShowInfo(singleInfoXML));
					showGenreEntries.add(singleInfoGenres);
					Log.i("INFO", "Got Show info data");

				}
				HashMap<String, String> imageEntry = new HashMap<String, String>();
				Log.i("INFO", "Size of showInfoEntries" + showInfoEntries.size());
				for (int i = 0; i < showInfoEntries.size(); i++)
				{
					imageEntry = showInfoEntries.get(i);
					Log.i("INFO", imageEntry.get(Constants.TAG_IMAGE));
					imageUrls.add(imageEntry.get(Constants.TAG_IMAGE));

				}

				// progressDialog.setMessage("Data Parsed");
			} catch (Exception e)
			{
				e.printStackTrace();
				Log.i("INFO", "Found exception " + e.getMessage());
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result)
		{

			progressDialog.dismiss();
			if (showInfoEntries != null && imageUrls != null)
			{
				adapter = new SearchListAdapter(getActivity(), showListEntries, imageUrls);

				searchResults.setAdapter(adapter);

				Log.i("INFO", "Setting adapter after postexec");
			}
		}

	}

}
