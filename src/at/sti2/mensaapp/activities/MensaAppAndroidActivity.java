package at.sti2.mensaapp.activities;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;
import at.sti2.mensaapp.InitialisationHandler;
import at.sti2.mensaapp.InitialisationHandlerListener;
import at.sti2.mensaapp.R;
import at.sti2.model.Mensa;

public class MensaAppAndroidActivity extends TabActivity implements InitialisationHandlerListener,
		OnItemSelectedListener {

	// Hashmap location l -> Vector of mensas in l
	private HashMap<String, Vector<Mensa>> mensaHM_final = null;
	private ProgressDialog dialog;
	private String mensaname = "";
	private String mensalocation = "";
	private String mensaURI = "";
	private String mensaaddress = "";
	
	@Override
	protected void onCreate(Bundle icicle) {
		// TODO Auto-generated method stub
		super.onCreate(icicle);
		setContentView(R.layout.main);

		// show loading Dialog
		dialog = ProgressDialog
				.show(MensaAppAndroidActivity.this, "", "Loading. Please wait...", true);
		dialog.show();
		
		// loading initialisation data from server
		InitialisationHandler iH = new InitialisationHandler(this);
		iH.execute("Param");
		
		// loading last mensa visit
		SharedPreferences myPrefs = this.getSharedPreferences("myPrefs", MODE_PRIVATE);
		this.mensaname = myPrefs.getString("nname", "");
		this.mensalocation = myPrefs.getString("llocation", "");
		this.mensaURI = myPrefs.getString("mensaURI", "");
		this.mensaaddress = myPrefs.getString("aaddress", "");
		System.out.println("add" + this.mensaaddress);
		System.out.println("asd" + this.mensalocation);
		System.out.println("sdasd" + this.mensaURI);
		System.out.println("dwerw" + this.mensaname);
	}

	@Override
	public void onInitialLoadingFinished(HashMap<String, Vector<Mensa>> mensaHM) {
		
		dialog.dismiss();
		
		mensaHM_final = mensaHM;
		if (mensaHM == null || mensaHM.size() == 0) {
			Toast.makeText(getApplicationContext(), "Not connected to host",
					Toast.LENGTH_LONG).show();
		} else {
			
			// give all mensas to bundle
			Bundle bundle = new Bundle();
			Set<String> locations = mensaHM_final.keySet();
			for (Iterator<String> iterator = locations.iterator(); iterator.hasNext();) {
				String location = (String) iterator.next();
				Vector<Mensa> mensaVector = mensaHM_final.get(location);
				for (int i = 0; i < mensaVector.size(); i++) {
					bundle.putBundle(mensaVector.get(i).getName(), mensaVector.get(i).getBundle());
				}
			}
			
			TabHost tabHost = getTabHost();
			// Tab Map
			TabSpec mapspec = tabHost.newTabSpec("MAP");
			// setting Title and Icon for the Tab
			mapspec.setIndicator("Map",
					getResources().getDrawable(R.drawable.map_icon));
			Intent mapIntent = new Intent(this, MensaMapActivity.class);
			mapIntent.putExtras(bundle);
			mapspec.setContent(mapIntent);

			TabSpec listspec = tabHost.newTabSpec("LIST");
			listspec.setIndicator("Cities",
					getResources().getDrawable(R.drawable.city_icon));
			Intent listIntent = new Intent(this, ListViewCitiesActivity.class);
			listIntent.putExtras(bundle);
			listspec.setContent(listIntent);

			tabHost.addTab(listspec);
			tabHost.addTab(mapspec);
			
			if (!this.mensaURI.equals("")){
				TabSpec lastMensa = tabHost.newTabSpec("LAST VISIT");
				lastMensa.setIndicator("Last Visited",
						getResources().getDrawable(R.drawable.history_icon));
				Intent lastVisitIntent = new Intent(this, MensaDetailsActivity.class);
				Bundle newBundle = new Bundle();
				newBundle.putString("name",this.mensaname);
				newBundle.putString("location",this.mensalocation);
				newBundle.putString("mensaURI",this.mensaURI);
				newBundle.putString("streetaddress",this.mensaaddress);
				lastVisitIntent.putExtras(newBundle);
				lastMensa.setContent(lastVisitIntent);
				tabHost.addTab(lastMensa);
			} else
				System.out.println("MMMM null");
			
			
			

		}
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
	}

}