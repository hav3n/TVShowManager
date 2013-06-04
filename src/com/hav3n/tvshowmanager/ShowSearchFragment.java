package com.hav3n.tvshowmanager;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class ShowSearchFragment extends ListFragment
{
	EditText searchBar;
	ListView searchResults;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.fragment_search, container, false);

		searchBar = (EditText) rootView.findViewById(R.id.searchBar);
		searchResults = (ListView) rootView.findViewById(android.R.id.list);

		searchBar.setOnEditorActionListener(new TextView.OnEditorActionListener()
		{

			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
			{
				if (actionId == EditorInfo.IME_ACTION_SEARCH)
				{

					return true;

				} else

					return false;
			}
		});

		return rootView;
	}

	public void performSearch()
	{

	}

}
