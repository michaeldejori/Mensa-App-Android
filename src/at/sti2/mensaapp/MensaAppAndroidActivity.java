package at.sti2.mensaapp;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import at.sti2.model.Mensa;

public class MensaAppAndroidActivity extends Activity implements InitialisationHandlerListener {

	@Override
	protected void onCreate(Bundle icicle) {
		// TODO Auto-generated method stub
		super.onCreate(icicle);
		setContentView(R.layout.main);

		// nearby button clicked
		Button b = (Button) findViewById(R.id.nearby);
		b.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// intent auf mensa App activity

				/***
				 * 
				 * http://stackoverflow.com/questions/708012/android-how-to-
				 * declare-global-variables
				 * 
				 */
				Intent mapIntent = new Intent(getApplicationContext(), MensaMapActivity.class);

				mapIntent.putExtra("mensa1Bsp", new Mensa().getBundle());
				mapIntent.putExtra("mensa2Bsp", new Mensa().getBundle());
				startActivity(mapIntent);
			}
		});

		// loading initialisation data from server
		InitialisationHandler iH = new InitialisationHandler(this);
		iH.execute("Param");

	}

	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onInitialLoadingFinished(HashMap<String, Vector<Mensa>> mensaHM) {

		System.out.println("MensaAppAndroidActivity.onInitialLoadingFinished() " + mensaHM);

		Toast.makeText(getApplicationContext(), "finisshed loading initial context",
				Toast.LENGTH_LONG).show();

		final HashMap<String, Vector<Mensa>> mensaHM_final = mensaHM;

		/*
		 * for (int i=0; i < cities.size(); i++){ Log.d("citite",
		 * cities.get(i)); }
		 */

		// Selection of the spinner
		final Spinner spinner = (Spinner) findViewById(R.id.citySpinner);

		Vector<String> cities = new Vector<String>();
		Set<String> s = mensaHM.keySet();
		System.out.println(s);
		Iterator<String> iterator = s.iterator();
		while (iterator.hasNext()) {
			cities.add(iterator.next().toString());
		}
		System.out.println(cities);

		// Application of the Array to the Spinner
		ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, cities);
		spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(spinnerArrayAdapter);

		
		

		
		
		
		
		
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			int iCurrentSelection = spinner.getSelectedItemPosition();
			
			@Override
			public void onItemSelected(AdapterView<?> parentView, View selectedItemView,
					int position, long id) {

				
				if (iCurrentSelection != position){
					// show informations for selected mensa
					String name = (String) parentView.getItemAtPosition(position);
					System.out.println(name);
					
					Intent listIntent = new Intent(getApplicationContext(), ListViewActivity.class);
					
					Vector<Mensa> mensaV = getMensaVector(mensaHM_final, parentView, selectedItemView,
							position, id);
					
					Bundle bundle = new Bundle();
					for (int i = 0; i < mensaV.size(); i++) {
						bundle.putBundle(mensaV.get(i).getName(), mensaV.get(i).getBundle());
						
					}
					listIntent.putExtras(bundle);
					startActivity(listIntent);
				}
				iCurrentSelection = position;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parentView) {
				// TODO Auto-generated method stub
			}
		});

	}

	/**
	 * @param mensaHM
	 * @param parentView
	 * @param selectedItemView
	 * @param position
	 * @param id
	 * @return returns selected Mensa Object
	 */
	protected Vector<Mensa> getMensaVector(HashMap<String, Vector<Mensa>> mensaHM,
			AdapterView<?> parentView, View selectedItemView, int position, long id) {

		// TODO return selected Mensa Object
		String location = (String) parentView.getItemAtPosition(position);
		System.out.println(location);
		Vector<Mensa> v = mensaHM.get(location);
		// v.get(0);

		return v;
	}
}