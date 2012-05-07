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
import java.util.HashMap;
import java.util.Vector;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import android.os.AsyncTask;
import android.util.Log;
import at.sti2.model.Mensa;

public class InitialisationHandler extends AsyncTask<String, Integer, HashMap<String,Vector<Mensa>>> {

	private InitialisationHandlerListener iHL;

	
	private HashMap<String,Vector<Mensa>> mensaHM = new HashMap<String, Vector<Mensa>>();
	
	public InitialisationHandler(InitialisationHandlerListener iHL){
		this.iHL = iHL;
	}
	
	@Override
	protected HashMap<String,Vector<Mensa>> doInBackground(String... params) {
		try {
			// construct endpoint URI
			URI uri = new URI(SparqlQueries.scheme, "", SparqlQueries.host, SparqlQueries.port, SparqlQueries.path, "query=" + SparqlQueries.mensaCityLatLonQuery, "");
			Log.d("InitialHandler URI", uri.toString());
			URL url = new URL(uri.toString());
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setDoOutput(true);
			con.setRequestMethod("GET");
			con.setRequestProperty("Accept", "application/sparql-results+json");
			
			if (con.getResponseCode() != 200){
				throw new RuntimeException("Failed : HTTP error code : "
						+ con.getResponseCode());
			}
			
			StringBuffer sb = readBufferedReaderIntoStringBuffer(con.getInputStream());
			con.disconnect();

			parseJSON(sb);
			//return this.cities;

			return null;
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	private void parseJSON(StringBuffer sb) {
		JsonParser parser = new JsonParser();
		JsonObject o = (JsonObject)parser.parse(sb.toString());
		JsonObject joresults = o.getAsJsonObject("results");
		System.out.println(joresults);
		JsonArray jabindings = joresults.getAsJsonArray("bindings");
		int i = 0;
		
		for (; i < jabindings.size(); i++){
			// ?location ?mensa ?mensaname ?lat ?lon
			JsonObject jobLocation = jabindings.get(i).getAsJsonObject().getAsJsonObject("location");
			JsonElement jeLocation = jobLocation.get("value");
			String location = jeLocation.getAsString();
			
			JsonObject jobMensaname = jabindings.get(i).getAsJsonObject().getAsJsonObject("mensaname");
			JsonElement jeMensaname = jobMensaname.get("value");
			String mensaName = jeMensaname.getAsString();
			
			JsonObject jobLat = jabindings.get(i).getAsJsonObject().getAsJsonObject("lat");
			JsonElement jeLat = jobLat.get("value");
			String lat = jeLat.getAsString();
			
			JsonObject jobLon = jabindings.get(i).getAsJsonObject().getAsJsonObject("lon");
			JsonElement jeLon = jobLon.get("value");
			String lon = jeLon.getAsString();
			//v.add(je.getAsString());
			Mensa m = new Mensa(mensaName, location, lat, lon);
			appendMensaToHashMap(m);
			
		}
	}

	private void appendMensaToHashMap(Mensa m) {
		Vector<Mensa> v = this.mensaHM.get(m.getLocation());
		if (v == null){
			this.mensaHM.put(m.getLocation(), new Vector<Mensa>());
			v = this.mensaHM.get(m.getLocation());
		}
		v.add(m);
	}

	/**
	 * 
	 * @param is input stream
	 * @return StringBuffer filled from input stream
	 * @throws IOException
	 */
	private StringBuffer readBufferedReaderIntoStringBuffer(InputStream is) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(
				(is))); 
		
		String line;
		StringBuffer all = new StringBuffer();
		while ((line = br.readLine()) != null) {
			all.append(line);
		}
		return all;
	}

	@Override
	protected void onPostExecute(HashMap<String,Vector<Mensa>> result) {
		// TODO Auto-generated method stub
		this.iHL.onInitialLoadingFinished(this.mensaHM);
	}

}
