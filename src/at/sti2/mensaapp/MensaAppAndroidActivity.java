package at.sti2.mensaapp;

import java.util.List;
import java.util.Vector;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import at.sti2.mensaapp.R;


public class MensaAppAndroidActivity extends Activity implements InitialisationHandlerListener{

	@Override
	protected void onCreate(Bundle icicle) {
		// TODO Auto-generated method stub
		super.onCreate(icicle);
		setContentView(R.layout.main);
		
		// loading initialisation data from server
		InitialisationHandler iH = new InitialisationHandler(this);
		iH.execute("Param");

	}

	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void onInitialLoadingFinished(Vector<String> cities) {
		Toast.makeText(getApplicationContext(), "finisshed loading initial context", Toast.LENGTH_LONG).show();
		for (int i=0; i < cities.size(); i++){
			Log.d("citite", cities.get(i));
		}
		
		// Selection of the spinner
		Spinner spinner = (Spinner) findViewById(R.id.citySpinner);

		// Application of the Array to the Spinner
		ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cities);
		spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
		spinner.setAdapter(spinnerArrayAdapter);
		
	}
}