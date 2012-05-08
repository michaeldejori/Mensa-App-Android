package at.sti2.mensaapp;

import java.util.HashMap;

import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

import android.view.View;

import at.sti2.mensaapp.R;
import at.sti2.model.Mensa;

public class MensaAppAndroidActivity extends Activity implements
		InitialisationHandlerListener {

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
				 * http://stackoverflow.com/questions/708012/android-how-to-declare-global-variables
				 * 
				 */
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
	public void onInitialLoadingFinished(HashMap<String,Vector<Mensa>> mensaHM) {
		Toast.makeText(getApplicationContext(), "finisshed loading initial context", Toast.LENGTH_LONG).show();
		
	
		/*for (int i=0; i < cities.size(); i++){
			Log.d("citite", cities.get(i));
		}*/
		
		// Selection of the spinner
		Spinner spinner = (Spinner) findViewById(R.id.citySpinner);

	
		Vector<String> cities = new Vector<String>();
		Set<String> s = mensaHM.keySet();
		Iterator<String> iterator = s.iterator();
	    while(iterator. hasNext()){        
	    	cities.add(iterator.next().toString());
	    }
		
		// Application of the Array to the Spinner
		ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cities);
		spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
		spinner.setAdapter(spinnerArrayAdapter);
		
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parentView, View selectedItemView,
					int position, long id) {
				System.out.println("Pos: " + position);
				Log.d("Pos", String.valueOf(position));
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parentView) {
				// TODO Auto-generated method stub
				;
			}
		});
		
	}
}