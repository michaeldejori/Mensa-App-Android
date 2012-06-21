package at.sti2.mensaapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import android.os.AsyncTask;
import android.util.Log;
import at.sti2.externalCode.MyEncoder;
import at.sti2.model.Menu;

public class MenuHandler extends AsyncTask<Date, Integer, HashMap<String, Vector<Menu>>> {

	private String mensaURI = null;

	private MenuHandlerListener listener;

	private HashMap<String, Vector<Menu>> menuHM = new HashMap<String, Vector<Menu>>();

	public MenuHandler(MenuHandlerListener listener, String mensaURI) {
		this.listener = listener;
		this.mensaURI = mensaURI;
	}

	@Override
	protected HashMap<String, Vector<Menu>> doInBackground(Date... params) {
		try {
			System.out.println("MenuHandler.doInBackground()");

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

			String query;
			if (params.length < 2)
				query = SparqlQueries.getMenuQueryOfDay(this.mensaURI,
						dateFormat.format(new Date()));
			else {
				String start = dateFormat.format(params[0]);
				String end = dateFormat.format(params[1]);
				query = SparqlQueries.getMenuQueryOfDay(this.mensaURI, start, end);
			}

			Log.d("plain query", query);

			// construct endpoint URL
			String urlString = SparqlQueries.scheme + "://" + SparqlQueries.host + ":"
					+ SparqlQueries.port + SparqlQueries.path + "?query=" + MyEncoder.encode(query);
			Log.d("URL for querying meal", urlString);

			boolean connectionPossible = true;
			if (connectionPossible) {
				URL url = new URL(urlString);
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setConnectTimeout(1000);
				con.setDoOutput(true);
				con.setRequestMethod("GET");
				con.setRequestProperty("Accept", "application/sparql-results+json");

				if (con.getResponseCode() != 200) {
					System.err.println("Failed : HTTP error code : " + con.getResponseCode());
					throw new RuntimeException("Failed : HTTP error code : "
							+ con.getResponseCode());
				}

				StringBuffer sb = readBufferedReaderIntoStringBuffer(con.getInputStream());
				con.disconnect();

				parseJSON(sb);
			}

			return null;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("error on background: " + e.getMessage());
			e.printStackTrace();
		}

		System.out.println("nach catch block");

		return null;
	}

	private void parseJSON(StringBuffer sb) {
		System.out.println("MenuHandler.parseJSON()");

		JsonParser parser = new JsonParser();
		JsonObject o = (JsonObject) parser.parse(sb.toString());
		JsonObject joresults = o.getAsJsonObject("results");
//		System.out.println(joresults);
		JsonArray jabindings = joresults.getAsJsonArray("bindings");

		this.menuHM = new HashMap<String, Vector<Menu>>();

		Vector<Menu> menuVectorOfDay = new Vector<Menu>();

		for (int i = 0; i < jabindings.size(); i++) {

			// ?name ?description ?start ?end
			JsonObject nameJO = jabindings.get(i).getAsJsonObject().getAsJsonObject("name");
			JsonElement nameJE = nameJO.get("value");
			String name = nameJE.getAsString();

			JsonObject descriptionJO = jabindings.get(i).getAsJsonObject()
					.getAsJsonObject("description");
			JsonElement descriptionJE = descriptionJO.get("value");
			String description = descriptionJE.getAsString();

			JsonObject startJO = jabindings.get(i).getAsJsonObject().getAsJsonObject("start");
			JsonElement startJE = startJO.get("value");
			String start = startJE.getAsString();

			JsonObject endJO = jabindings.get(i).getAsJsonObject().getAsJsonObject("end");
			JsonElement endJE = endJO.get("value");
			String end = endJE.getAsString();

			Menu menu = new Menu(name, description, start, end);
			menuVectorOfDay.add(menu);

			appendMenuToHashMap(menu);

		}

		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		Date today = new Date();
		this.menuHM.put(sdf.format(today), menuVectorOfDay);
	}

	/** @bug snaks laufen über eine woche - werden aber nur am erste tag angezeigt 
	 *  TODO: jeden verfügbaren tag für menü in hashmap schreiben
	 * @param menu
	 */
	private void appendMenuToHashMap(Menu menu) {

		String key = menu.getAvailabilityStarts().substring(0, 10);
		// System.out.println(key);

		Vector<Menu> vector = menuHM.get(key);
		if (vector == null)
			vector = new Vector<Menu>();
		vector.add(menu);
		menuHM.put(key, vector);

	}

	/**
	 * 
	 * @param is
	 *            input stream
	 * @return StringBuffer filled from input stream
	 * @throws IOException
	 */
	private StringBuffer readBufferedReaderIntoStringBuffer(InputStream is) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader((is)));

		String line;
		StringBuffer all = new StringBuffer();
		while ((line = br.readLine()) != null) {
			all.append(line);
		}
		return all;
	}

	@Override
	protected void onPostExecute(HashMap<String, Vector<Menu>> result) {
		System.out.println("InitialisationHandler.onPostExecute()");
		// TODO Auto-generated method stub

		// test data if null
		/*
		 * if (this.menuHM == null || this.menuHM.size() < 1) { HashMap<String,
		 * Vector<Menu>> map = new HashMap<String, Vector<Menu>>();
		 * 
		 * SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		 * 
		 * Date today = new Date(); Date yesterday = new Date(new
		 * Date().getTime() - 1000 * 60 * 60 * 24);
		 * 
		 * Vector<Menu> heute = new Vector<Menu>(); heute.add(new Menu("Menu 1",
		 * "ja lecker fleisch .. für 10€", new Date(), new Date()));
		 * heute.add(new Menu("Menu 2", "ja kuchn", new Date(), new Date()));
		 * heute.add(new Menu("Menu 3", "sauerkraut", new Date(), new Date()));
		 * 
		 * Vector<Menu> gestern = new Vector<Menu>(); gestern.add(new
		 * Menu("Menu 1", "gestern lecker fleisch .. für 10€", new Date(), new
		 * Date())); gestern.add(new Menu("Menu 2", "gestern kuchn", new Date(),
		 * new Date())); gestern.add(new Menu("Menu 3", "altes sauerkraut", new
		 * Date(), new Date()));
		 * 
		 * map.put(sdf.format(today), heute); map.put(sdf.format(yesterday),
		 * gestern);
		 * 
		 * this.menuHM = map; }
		 */
		this.listener.onLoadingFinished(this.menuHM);
	}

}
