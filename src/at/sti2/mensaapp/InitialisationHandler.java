package at.sti2.mensaapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class InitialisationHandler extends AsyncTask<String, Integer, String> {

	private InitialisationHandlerListener iHL;
	
	public InitialisationHandler(InitialisationHandlerListener iHL){
		this.iHL = iHL;
	}
	
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		try {
			// construct endpoint URI
			URI uri = new URI(SparqlQueries.scheme, "", SparqlQueries.host, SparqlQueries.port, SparqlQueries.path, "query=" + SparqlQueries.citiesQuery, "");
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
		


		
		return "sdfsdf";
	}

	private void parseJSON(StringBuffer sb) {
		JsonParser parser = new JsonParser();
		JsonObject o = (JsonObject)parser.parse(sb.toString());
		JsonObject joresults = o.getAsJsonObject("results");
		System.out.println(joresults);
		JsonArray jabindings = joresults.getAsJsonArray("bindings");
		int i = 0;
		for (; i < jabindings.size(); i++){
			JsonObject job1 = jabindings.get(i).getAsJsonObject();
			JsonObject job11 = job1.getAsJsonObject("location");
			JsonElement je = job11.get("value");
			System.out.println(je.getAsString() + "\n");
		}
		
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
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		this.iHL.onInitialLoadingFinished(null);
		super.onPostExecute(result);
	}

}
