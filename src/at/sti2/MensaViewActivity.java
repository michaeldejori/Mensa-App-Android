package at.sti2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class MensaViewActivity extends Activity {
	
	 protected ArrayAdapter<CharSequence> mAdapter;
	 
	 
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Spinner spinner = (Spinner) findViewById(R.id.citySpinner);
        this.mAdapter = ArrayAdapter.createFromResource(this, R.array.static_cities,
                android.R.layout.simple_spinner_dropdown_item);
        String[] arr = new String[]{"Innsbruck","Wien"};
        
//        this.mAdapter = new ArrayAdapter<String>(this,R.id.citySpinner,arr);
        
        spinner.setAdapter(this.mAdapter);
        
        Button button = (Button) findViewById(R.id.button1);
		button.setText("Mensen auf der Karte");
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "Click",
						Toast.LENGTH_LONG).show();

				// Intent intent = new Intent(Intent.ACTION_VIEW);
				// intent.setData(Uri.parse("http://www.google.com"));
				// startActivity(intent);

				Intent intent_map = new Intent(getApplicationContext(),
						MensaMapActivity.class);
				startActivity(intent_map);

			}
		});
        
        

//        OnItemSelectedListener spinnerListener = new myOnItemSelectedListener(this,this.mAdapter);

        /*
         * Attach the listener to the Spinner.
         */

//        spinner.setOnItemSelectedListener(spinnerListener);
        
        
    }
}