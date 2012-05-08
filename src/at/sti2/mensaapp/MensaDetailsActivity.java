package at.sti2.mensaapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MensaDetailsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details);

		System.out.println("MensaDetailsActivity.onCreate()");

		TextView mensa_name = (TextView) findViewById(R.id.mensa_name);

		Bundle bundle = this.getIntent().getExtras();
		String name = bundle.getString("name");
		mensa_name.setText(name);

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		System.out.println("MensaDetailsActivity.onPause()");
	}
}
