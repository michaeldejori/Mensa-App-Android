package at.sti2.mensaapp.activities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import at.sti2.mensaapp.MensaOverlay;
import at.sti2.mensaapp.MensaOverlayOnTabListener;
import at.sti2.mensaapp.R;
import at.sti2.model.Mensa;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class MensaMapActivity extends MapActivity implements LocationListener, MensaOverlayOnTabListener {

	private MapView mapView;
	private MapController mapController;
	private ItemizedOverlay<OverlayItem> mensaOverlay;
	private LocationManager locationManager;
	private String towers;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
		
		Bundle bundle = this.getIntent().getExtras();
		Set<String> locations = bundle.keySet();
		
		List<Mensa> mensaList = new ArrayList<Mensa>();
		
		for (Iterator<String> iterator = locations.iterator(); iterator.hasNext();) {
			String location = (String) iterator.next();
			System.out.println(bundle.getBundle(location).get("name"));
			Mensa m = new Mensa();
			m.setName((String)bundle.getBundle(location).get("name"));
			m.setMensaURI((String)bundle.getBundle(location).get("mensaURI"));
			m.setLat((String)bundle.getBundle(location).get("lat"));
			m.setLon((String)bundle.getBundle(location).get("lon"));
			mensaList.add(m);
		}

		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);

		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Criteria cr = new Criteria();
		//cr.setSpeedRequired(true);
		
		towers = locationManager.getBestProvider(cr, false);
		
		Location loc = locationManager.getLastKnownLocation(towers);
		
		GeoPoint ibk;
		
		if (loc != null){
			double lat = loc.getLatitude();
			double lon = loc.getLongitude();
		    ibk = new GeoPoint((int) (lat * 1E6), (int) (lon * 1E6));
		    System.out.println("YEAH got location");
		} else {
			System.out.println("no location recieved");
			ibk = new GeoPoint((int) (47.267222 * 1E6), (int) (11.392778 * 1E6));
		}
		
		mapController = mapView.getController();
		mapController.setCenter(ibk);
		mapController.setZoom(12);
		
		drawOverlays(mensaList);

	}

	private void drawOverlays(List<Mensa> mensaList) {
		if (mensaList.size() > 0){
			mensaOverlay = new MensaOverlay(this.getResources().getDrawable(R.drawable.poi), mensaList, this);
			mapView.getOverlays().add(mensaOverlay);
			mapView.invalidate();
		}
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onSpritItemTap(Mensa item) {
		// make intent to activity when a mensa is chosen;
		
		Bundle bundle = getIntent().getBundleExtra(item.getName());

		Intent detailsIntent = new Intent(getApplicationContext(), MensaDetailsActivity.class);
		detailsIntent.putExtras(bundle);

		startActivity(detailsIntent);
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
	

}
