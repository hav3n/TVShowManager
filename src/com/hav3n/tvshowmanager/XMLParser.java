package com.hav3n.tvshowmanager;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

public class XMLParser
{

	public String getXmlFromUrl(String url)
	{
		String xml = null;

		try
		{
			// defaultHttpClient
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);

			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			xml = EntityUtils.toString(httpEntity);

		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		} catch (ClientProtocolException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		// return XML
		return xml;
	}

	public List parseShowList(String xml) throws XmlPullParserException, IOException
	{
		final String TAG_RESULTS = "Results";
		final String TAG_SHOW = "show";
		// public static String TAG_SHOWID;
		final String TAG_NAME = "name";

		XmlPullParser parser = Xml.newPullParser();
		parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
		parser.setInput(new StringReader(xml));
		parser.nextTag();

		List showEntries = new ArrayList();

		parser.require(XmlPullParser.START_TAG, null, TAG_RESULTS);
		while (parser.next() != XmlPullParser.END_TAG)
		{
			if (parser.getEventType() != XmlPullParser.START_TAG)
			{
				continue;
			}

			String name = parser.getName();

			if (name.equals(TAG_SHOW))
			{
				showEntries.add(ShowEntry.readShow(parser));
			} else
			{
				ShowEntry.skip(parser);
			}
		}

		return showEntries;

	}

	public static class ShowEntry
	{
		public final static String TAG_SHOW = "show";
		public final static String TAG_SHOWID = "showid";
		public final static String TAG_NAME = "name";
		public final static String TAG_LINK = "link";
		public final static String TAG_COUNTRY = "country";
		public final static String TAG_STARTED = "started";
		public final static String TAG_ENDED = "ended";
		public final static String TAG_SEASONS = "seasons";
		public final static String TAG_STATUS = "status";
		public final static String TAG_CLASSIFICATION = "classification";
		public final static String TAG_GENRES = "genres";
		public final static String TAG_GENRE = "genre";
		public final String showid;
		public final String name;
		public final String link;
		public final String country;
		public final String started;
		public final String ended;
		public final String seasons;
		public final String status;
		public final String classification;
		public final List<String> genres;

		public ShowEntry(String showid, String name, String link, String country, String started, String ended, String seasons, String status,
				String classification, List<String> genres)
		{
			this.showid = showid;
			this.name = name;
			this.link = link;
			this.country = country;
			this.started = started;
			this.ended = ended;
			this.seasons = seasons;
			this.status = status;
			this.classification = classification;
			this.genres = genres;
		}

		public static ShowEntry readShow(XmlPullParser parser) throws XmlPullParserException, IOException
		{
			parser.require(XmlPullParser.START_TAG, null, TAG_SHOW);
			String showid = null;
			String name = null;
			String link = null;
			String country = null;
			String started = null;
			String ended = null;
			String seasons = null;
			String status = null;
			String classification = null;
			List<String> genres = null;

			while (parser.next() != XmlPullParser.END_TAG)
			{
				if (parser.getEventType() != XmlPullParser.START_TAG)
				{
					continue;
				}
				String tagname = parser.getName();
				if (tagname.equals(TAG_SHOWID))
				{
					parser.require(XmlPullParser.START_TAG, null, TAG_SHOWID);
					showid = parser.getText();
					parser.require(XmlPullParser.END_DOCUMENT, null, TAG_SHOWID);

				} else if (tagname.equals(TAG_NAME))
				{
					parser.require(XmlPullParser.START_TAG, null, TAG_NAME);
					name = parser.getText();
					parser.require(XmlPullParser.END_TAG, null, TAG_NAME);

				} else if (tagname.equals(TAG_LINK))
				{
					parser.require(XmlPullParser.START_TAG, null, TAG_LINK);
					link = parser.getText();
					parser.require(XmlPullParser.END_TAG, null, TAG_LINK);
				} else if (tagname.equals(TAG_COUNTRY))
				{
					parser.require(XmlPullParser.START_TAG, null, TAG_COUNTRY);
					country = parser.getText();
					parser.require(XmlPullParser.END_TAG, null, TAG_COUNTRY);
				} else if (tagname.equals(TAG_STARTED))
				{
					parser.require(XmlPullParser.START_TAG, null, TAG_STARTED);
					started = parser.getText();
					parser.require(XmlPullParser.END_TAG, null, TAG_STARTED);
				} else if (tagname.equals(TAG_ENDED))
				{
					parser.require(XmlPullParser.START_TAG, null, TAG_ENDED);
					ended = parser.getText();
					parser.require(XmlPullParser.END_TAG, null, TAG_ENDED);

				} else if (tagname.equals(TAG_SEASONS))
				{
					parser.require(XmlPullParser.START_TAG, null, TAG_SEASONS);
					seasons = parser.getText();
					parser.require(XmlPullParser.END_TAG, null, TAG_SEASONS);
				} else if (tagname.equals(TAG_STATUS))
				{
					parser.require(XmlPullParser.START_TAG, null, TAG_STATUS);
					status = parser.getText();
					parser.require(XmlPullParser.END_TAG, null, TAG_STATUS);
				} else if (tagname.equals(TAG_CLASSIFICATION))
				{
					parser.require(XmlPullParser.START_TAG, null, TAG_CLASSIFICATION);
					classification = parser.getText();
					parser.require(XmlPullParser.END_TAG, null, TAG_CLASSIFICATION);
				} else if (tagname.equals(TAG_GENRES))
				{
					genres = new ArrayList<String>();
					parser.require(XmlPullParser.START_TAG, null, TAG_GENRES);
					while (parser.getEventType() != XmlPullParser.END_TAG)
					{
						parser.require(XmlPullParser.START_TAG, null, TAG_GENRE);
						genres.add(parser.getText());
						parser.require(XmlPullParser.END_TAG, null, TAG_GENRE);
						parser.nextTag();
					}
					parser.require(XmlPullParser.END_TAG, null, TAG_GENRES);
				} else
				{
					skip(parser);
				}
			}
			return new ShowEntry(showid, name, link, country, started, ended, seasons, status, classification, genres);
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
	}
}
