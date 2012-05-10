package at.sti2.mensaapp.activities;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import at.sti2.mensaapp.InitialisationHandler;
import at.sti2.mensaapp.InitialisationHandlerListener;
import at.sti2.mensaapp.R;
import at.sti2.model.Mensa;

public class MensaAppAndroidActivity extends Activity implements InitialisationHandlerListener,
		OnItemSelectedListener {

	private HashMap<String, Vector<Mensa>> mensaHM_final;

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

	@Override
	public void onInitialLoadingFinished(HashMap<String, Vector<Mensa>> mensaHM) {

		System.out.println("MensaAppAndroidActivity.onInitialLoadingFinished() " + mensaHM);

		Toast.makeText(getApplicationContext(), "finisshed loading initial context",
				Toast.LENGTH_LONG).show();

		mensaHM_final = mensaHM;

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
		

		spinner.setOnItemSelectedListener(this);

	}

	@Override
	public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position,
			long id) {

		String name = (String) parentView.getItemAtPosition(position);

		Button cityButton = (Button) findViewById(R.id.cityButton);
		String citiesInTxt = getString(R.string.selectCityButton, name);
		cityButton.setText(citiesInTxt);

		String location = (String) parentView.getItemAtPosition(position);
		
		System.out.println(location);
		final Vector<Mensa> mensaVector = mensaHM_final.get(location);

		final Bundle bundle = new Bundle();
		for (int i = 0; i < mensaVector.size(); i++) {
			bundle.putBundle(mensaVector.get(i).getName(), mensaVector.get(i).getBundle());
		}

		cityButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent listIntent = new Intent(getApplicationContext(), ListViewActivity.class);

				listIntent.putExtras(bundle);
				startActivity(listIntent);
			}
		});

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

}