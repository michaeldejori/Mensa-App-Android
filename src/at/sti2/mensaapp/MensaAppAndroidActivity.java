package at.sti2.mensaapp;

import android.app.Activity;
import android.os.Bundle;
import at.sti2.mensaapp.R;


public class MensaAppAndroidActivity extends Activity {

	@Override
	protected void onCreate(Bundle icicle) {
		// TODO Auto-generated method stub
		super.onCreate(icicle);
		setContentView(R.layout.main);

	}

	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}