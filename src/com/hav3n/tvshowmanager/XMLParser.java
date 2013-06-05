package com.hav3n.tvshowmanager;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;
import android.util.Xml;

import com.hav3n.tvshowmanager.constants.Constants;

public class XMLParser
{

	public String getXmlFromUrl(String url) throws UnsupportedEncodingException, ClientProtocolException, IOException
	{
		String xml = null;

		// defaultHttpClient
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);

		HttpResponse httpResponse = httpClient.execute(httpPost);
		HttpEntity httpEntity = httpResponse.getEntity();
		xml = EntityUtils.toString(httpEntity);

		return xml;
	}

	public ArrayList<HashMap<String, String>> parseShowList(String xml) throws XmlPullParserException, IOException
	{
		final String TAG_RESULTS = "Results";

		XmlPullParser parser = Xml.newPullParser();
		parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
		parser.setInput(new StringReader(xml));
		parser.nextTag();

		ArrayList<HashMap<String, String>> showEntries = new ArrayList<HashMap<String, String>>();

		parser.require(XmlPullParser.START_TAG, null, TAG_RESULTS);
		while (parser.next() != XmlPullParser.END_TAG)
		{
			if (parser.getEventType() != XmlPullParser.START_TAG)
			{
				continue;
			}

			String name = parser.getName();

			if (name.equals(Constants.TAG_SHOW))
			{
				showEntries.add(ShowEntry.readShow(parser));
			} else
			{
				ShowEntry.skip(parser);
			}
		}
		parser.require(XmlPullParser.END_TAG, null, TAG_RESULTS);
		return showEntries;

	}

	public HashMap<String, String> parseShowInfo(String xml) throws XmlPullParserException, IOException
	{

		XmlPullParser parser = Xml.newPullParser();
		parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
		parser.setInput(new StringReader(xml));
		parser.nextTag();

		ArrayList<HashMap<String, String>> showInfoEntries = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> temp = new HashMap<String, String>();

		parser.require(XmlPullParser.START_TAG, null, Constants.TAG_SHOWINFO);

		temp = ShowEntry.readShowInfo(parser);

		parser.require(XmlPullParser.END_TAG, null, Constants.TAG_SHOWINFO);

		return temp;

	}
	public static class ShowEntry
	{

		public String showid;
		public String name;
		public String link;
		public String country;
		public String started;
		public String ended;
		public String seasons;
		public String status;
		public String classification;
		public String showname;
		public String showlink;
		public String image;
		public String startdate;
		public String origin_country;
		public String summary;
		public String runtime;
		public String network;
		public String airday;
		public String airtime;
		public String timezone;
		public List<String> genres;

		public static HashMap<String, String> readShow(XmlPullParser parser) throws XmlPullParserException, IOException
		{
			parser.require(XmlPullParser.START_TAG, null, Constants.TAG_SHOW);

			HashMap<String, String> map = new HashMap<String, String>();
			while (parser.next() != XmlPullParser.END_TAG)
			{
				if (parser.getEventType() != XmlPullParser.START_TAG)
				{
					continue;
				}
				String tagname = parser.getName();
				if (tagname.equals(Constants.TAG_SHOWID))
				{
					parser.require(XmlPullParser.START_TAG, null, Constants.TAG_SHOWID);

					map.put(Constants.TAG_SHOWID, parser.nextText());
					Log.i("INFO", "Done showid");
					parser.require(XmlPullParser.END_TAG, null, Constants.TAG_SHOWID);

				} else if (tagname.equals(Constants.TAG_NAME))
				{
					parser.require(XmlPullParser.START_TAG, null, Constants.TAG_NAME);
					map.put(Constants.TAG_NAME, parser.nextText());
					Log.i("INFO", "Done start");
					parser.require(XmlPullParser.END_TAG, null, Constants.TAG_NAME);
					// parser.nextTag();

				} else if (tagname.equals(Constants.TAG_LINK))
				{
					parser.require(XmlPullParser.START_TAG, null, Constants.TAG_LINK);
					map.put(Constants.TAG_LINK, parser.nextText());
					Log.i("INFO", "Done link");
					parser.require(XmlPullParser.END_TAG, null, Constants.TAG_LINK);
				} else if (tagname.equals(Constants.TAG_COUNTRY))
				{
					parser.require(XmlPullParser.START_TAG, null, Constants.TAG_COUNTRY);
					map.put(Constants.TAG_COUNTRY, parser.nextText());
					Log.i("INFO", "Done country");
					parser.require(XmlPullParser.END_TAG, null, Constants.TAG_COUNTRY);
				} else if (tagname.equals(Constants.TAG_STARTED))
				{
					parser.require(XmlPullParser.START_TAG, null, Constants.TAG_STARTED);
					map.put(Constants.TAG_STARTED, parser.nextText());
					Log.i("INFO", "Done started");
					parser.require(XmlPullParser.END_TAG, null, Constants.TAG_STARTED);
				} else if (tagname.equals(Constants.TAG_ENDED))
				{
					parser.require(XmlPullParser.START_TAG, null, Constants.TAG_ENDED);
					map.put(Constants.TAG_ENDED, parser.nextText());
					Log.i("INFO", "Done ended");
					parser.require(XmlPullParser.END_TAG, null, Constants.TAG_ENDED);

				} else if (tagname.equals(Constants.TAG_SEASONS))
				{
					parser.require(XmlPullParser.START_TAG, null, Constants.TAG_SEASONS);
					map.put(Constants.TAG_SEASONS, parser.nextText());
					Log.i("INFO", "Done seasons");
					parser.require(XmlPullParser.END_TAG, null, Constants.TAG_SEASONS);
				} else if (tagname.equals(Constants.TAG_STATUS))
				{
					parser.require(XmlPullParser.START_TAG, null, Constants.TAG_STATUS);
					map.put(Constants.TAG_STATUS, parser.nextText());
					Log.i("INFO", "Done status");
					parser.require(XmlPullParser.END_TAG, null, Constants.TAG_STATUS);
				} else if (tagname.equals(Constants.TAG_CLASSIFICATION))
				{
					parser.require(XmlPullParser.START_TAG, null, Constants.TAG_CLASSIFICATION);
					map.put(Constants.TAG_CLASSIFICATION, parser.nextText());
					Log.i("INFO", "Done class");
					parser.require(XmlPullParser.END_TAG, null, Constants.TAG_CLASSIFICATION);
				} else
				{
					skip(parser);
				}
			}
			return map;
		}

		public static HashMap<String, String> readShowInfo(XmlPullParser parser) throws XmlPullParserException, IOException
		{

			HashMap<String, String> map = new HashMap<String, String>();

			parser.require(XmlPullParser.START_TAG, null, Constants.TAG_SHOWINFO);

			while (parser.next() != XmlPullParser.END_TAG)
			{
				if (parser.getEventType() != XmlPullParser.START_TAG)
				{
					continue;
				}

				String tagname = parser.getName();

				if (tagname.equals(Constants.TAG_SHOWID))
				{
					parser.require(XmlPullParser.START_TAG, null, Constants.TAG_SHOWID);
					map.put(Constants.TAG_SHOWID, parser.nextText());
					parser.require(XmlPullParser.END_TAG, null, Constants.TAG_SHOWID);

				} else if (tagname.equals(Constants.TAG_SHOWNAME))
				{
					parser.require(XmlPullParser.START_TAG, null, Constants.TAG_SHOWNAME);
					map.put(Constants.TAG_SHOWNAME, parser.nextText());
					parser.require(XmlPullParser.END_TAG, null, Constants.TAG_SHOWNAME);

				} else if (tagname.equals(Constants.TAG_SHOWLINK))
				{
					parser.require(XmlPullParser.START_TAG, null, Constants.TAG_SHOWLINK);
					map.put(Constants.TAG_SHOWLINK, parser.nextText());
					parser.require(XmlPullParser.END_TAG, null, Constants.TAG_SHOWLINK);
				} else if (tagname.equals(Constants.TAG_SEASONS))
				{
					parser.require(XmlPullParser.START_TAG, null, Constants.TAG_SEASONS);
					map.put(Constants.TAG_SEASONS, parser.nextText());
					parser.require(XmlPullParser.END_TAG, null, Constants.TAG_SEASONS);
				} else if (tagname.equals(Constants.TAG_IMAGE))
				{
					parser.require(XmlPullParser.START_TAG, null, Constants.TAG_IMAGE);
					map.put(Constants.TAG_IMAGE, parser.nextText());
					parser.require(XmlPullParser.END_TAG, null, Constants.TAG_IMAGE);
				} else if (tagname.equals(Constants.TAG_STARTED))
				{
					parser.require(XmlPullParser.START_TAG, null, Constants.TAG_STARTED);
					map.put(Constants.TAG_STARTED, parser.nextText());
					parser.require(XmlPullParser.END_TAG, null, Constants.TAG_STARTED);
				} else if (tagname.equals(Constants.TAG_STARTDATE))
				{
					parser.require(XmlPullParser.START_TAG, null, Constants.TAG_STARTDATE);
					map.put(Constants.TAG_STARTDATE, parser.nextText());
					parser.require(XmlPullParser.END_TAG, null, Constants.TAG_STARTDATE);
				} else if (tagname.equals(Constants.TAG_ENDED))
				{
					parser.require(XmlPullParser.START_TAG, null, Constants.TAG_ENDED);
					map.put(Constants.TAG_ENDED, parser.nextText());
					parser.require(XmlPullParser.END_TAG, null, Constants.TAG_ENDED);
				} else if (tagname.equals(Constants.TAG_ORIGINCOUNTRY))
				{
					parser.require(XmlPullParser.START_TAG, null, Constants.TAG_ORIGINCOUNTRY);
					map.put(Constants.TAG_ORIGINCOUNTRY, parser.nextText());
					parser.require(XmlPullParser.END_TAG, null, Constants.TAG_ORIGINCOUNTRY);
				} else if (tagname.equals(Constants.TAG_STATUS))
				{
					parser.require(XmlPullParser.START_TAG, null, Constants.TAG_STATUS);
					map.put(Constants.TAG_STATUS, parser.nextText());
					parser.require(XmlPullParser.END_TAG, null, Constants.TAG_STATUS);
				} else if (tagname.equals(Constants.TAG_CLASSIFICATION))
				{
					parser.require(XmlPullParser.START_TAG, null, Constants.TAG_CLASSIFICATION);
					map.put(Constants.TAG_CLASSIFICATION, parser.nextText());
					parser.require(XmlPullParser.END_TAG, null, Constants.TAG_CLASSIFICATION);
				} else if (tagname.equals(Constants.TAG_SUMMARY))
				{
					parser.require(XmlPullParser.START_TAG, null, Constants.TAG_SUMMARY);
					map.put(Constants.TAG_SUMMARY, parser.nextText());
					parser.require(XmlPullParser.END_TAG, null, Constants.TAG_SUMMARY);
				} else if (tagname.equals(Constants.TAG_RUNTIME))
				{
					parser.require(XmlPullParser.START_TAG, null, Constants.TAG_RUNTIME);
					map.put(Constants.TAG_RUNTIME, parser.nextText());
					parser.require(XmlPullParser.END_TAG, null, Constants.TAG_RUNTIME);
				} else if (tagname.equals(Constants.TAG_NETWORK))
				{

					parser.require(XmlPullParser.START_TAG, null, Constants.TAG_NETWORK);

					String countryName = parser.getAttributeValue(null, "country");
					map.put(Constants.TAG_NETWORK, countryName + " " + parser.nextText());
					parser.require(XmlPullParser.END_TAG, null, Constants.TAG_NETWORK);

				} else if (tagname.equals(Constants.TAG_AIRTIME))
				{
					parser.require(XmlPullParser.START_TAG, null, Constants.TAG_AIRTIME);
					map.put(Constants.TAG_AIRTIME, parser.nextText());
					parser.require(XmlPullParser.END_TAG, null, Constants.TAG_AIRTIME);
				} else if (tagname.equals(Constants.TAG_AIRDAY))
				{
					parser.require(XmlPullParser.START_TAG, null, Constants.TAG_AIRDAY);
					map.put(Constants.TAG_AIRDAY, parser.nextText());
					parser.require(XmlPullParser.END_TAG, null, Constants.TAG_AIRDAY);
				} else if (tagname.equals(Constants.TAG_TIMEZONE))
				{
					parser.require(XmlPullParser.START_TAG, null, Constants.TAG_TIMEZONE);
					map.put(Constants.TAG_TIMEZONE, parser.nextText());
					parser.require(XmlPullParser.END_TAG, null, Constants.TAG_TIMEZONE);
				} else
				{
					skip(parser);
				}

			}

			return map;

		}

		private static void skip(XmlPullParser parser) throws XmlPullParserException, IOException
		{
			if (parser.getEventType() != XmlPullParser.START_TAG)
			{
				throw new IllegalStateException();
			}
			int depth = 1;
			while (depth != 0)
			{
				switch (parser.next())
				{
					case XmlPullParser.END_TAG :
						depth--;
						break;
					case XmlPullParser.START_TAG :
						depth++;
						break;
				}
			}
		}

		public static ArrayList<String> parseShowGenres(String xml) throws XmlPullParserException, IOException
		{
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(new StringReader(xml));
			parser.nextTag();

			ArrayList<String> genres = new ArrayList<String>();

			parser.require(XmlPullParser.START_TAG, null, Constants.TAG_GENRES);

			while (parser.next() != XmlPullParser.END_TAG)
			{
				String tagname = parser.getName();

				if (tagname.equals(Constants.TAG_GENRES))
				{
					parser.require(XmlPullParser.START_TAG, null, Constants.TAG_GENRE);
					genres.add(parser.nextText());
					parser.require(XmlPullParser.END_TAG, null, Constants.TAG_GENRE);
				} else
				{
					skip(parser);
				}

			}

			return genres;

		}
	}
}
