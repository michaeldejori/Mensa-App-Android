package at.sti2;


import android.os.Bundle;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

public class MensaMapActivity extends MapActivity{

	private MapView mapView;


@Override
protected void onCreate(Bundle icicle) {
	// TODO Auto-generated method stub
	super.onCreate(icicle);
	setContentView(R.layout.map);
	
	mapView = (MapView) findViewById(R.id.mapview);
	mapView.setBuiltInZoomControls(true);

	GeoPoint wien = new GeoPoint((int) (48.20833 * 1E6),
			(int) (16.373064 * 1E6));
	GeoPoint ibk = new GeoPoint((int) (47.267222 * 1E6),
			(int) (11.392778 * 1E6));

	MapController mapController = mapView.getController();
	mapController.setCenter(ibk);
	mapController.setZoom(12);
	
}


@Override
protected boolean isRouteDisplayed() {
	// TODO Auto-generated method stub
	return false;
}
	
}
