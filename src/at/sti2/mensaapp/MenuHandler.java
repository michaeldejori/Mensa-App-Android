package at.sti2.mensaapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import android.os.AsyncTask;
import android.util.Log;
import at.sti2.model.Mensa;
import at.sti2.model.Menu;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class MenuHandler extends AsyncTask<String, Integer, HashMap<String, Vector<Menu>>> {

	private MenuHandlerListener listener;

	private HashMap<String, Vector<Menu>> menuHM = new HashMap<String, Vector<Menu>>();

	public MenuHandler(MenuHandlerListener listener) {
		this.listener = listener;
	}

	@Override
	protected HashMap<String, Vector<Menu>> doInBackground(String... params) {
		System.out.println("InitialisationHandler.doInBackground()");

		return null;
	}



	@Override
	protected void onPostExecute(HashMap<String, Vector<Menu>> result) {
		System.out.println("InitialisationHandler.onPostExecute()");
		// TODO Auto-generated method stub

		// test data if null
		if (this.menuHM == null || this.menuHM.size() < 1) {
			HashMap<String, Vector<Menu>> map = new HashMap<String, Vector<Menu>>();

			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyy");
			
			Date today = new Date();
			Date yesterday = new Date(new Date().getTime() - 1000*60*60 *24);
			
			Vector<Menu> heute = new Vector<Menu>();
			heute.add(new Menu("Menu 1","ja lecker fleisch .. für 10€",new Date(),new Date()));
			heute.add(new Menu("Menu 2","ja kuchn",new Date(),new Date()));
			heute.add(new Menu("Menu 3","sauerkraut",new Date(),new Date()));
			
			Vector<Menu> gestern = new Vector<Menu>();
			gestern.add(new Menu("Menu 1","gestern lecker fleisch .. für 10€",new Date(),new Date()));
			gestern.add(new Menu("Menu 2","gestern kuchn",new Date(),new Date()));
			gestern.add(new Menu("Menu 3","altes sauerkraut",new Date(),new Date()));
			
			map.put(sdf.format(today), heute);
			map.put(sdf.format(yesterday), gestern);

			this.menuHM = map;
		}

		this.listener.onLoadingFinished(this.menuHM);
	}

}
