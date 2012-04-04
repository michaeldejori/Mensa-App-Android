package at.sti2.mensaapp;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
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
	public void onInitialLoadingFinished(List<String> feeds) {
		Toast.makeText(getApplicationContext(), "finished loading initial context", Toast.LENGTH_LONG).show();
		
	}
}