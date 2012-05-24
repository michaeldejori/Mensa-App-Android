package at.sti2.mensaapp.activities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import at.sti2.mensaapp.MensaOverlay;
import at.sti2.mensaapp.MensaOverlayOnTabListener;
import at.sti2.mensaapp.PinOverlay;
import at.sti2.mensaapp.R;
import at.sti2.model.Mensa;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class MensaMapActivity extends MapActivity implements
		MensaOverlayOnTabListener, LocationListener {

	private MapView mapView;
	private MapController mapController;
	private ItemizedOverlay<OverlayItem> mensaOverlay;
	private ItemizedOverlay<OverlayItem> pinOverlay;
	private LocationManager locationManager;
	private String provider;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);

		Bundle bundle = this.getIntent().getExtras();
		Set<String> locations = bundle.keySet();

		List<Mensa> mensaList = new ArrayList<Mensa>();

		for (Iterator<String> iterator = locations.iterator(); iterator
				.hasNext();) {
			String loc = (String) iterator.next();
			System.out.println(bundle.getBundle(loc).get("name"));
			Mensa m = new Mensa();
			m.setName((String) bundle.getBundle(loc).get("name"));
			m.setMensaURI((String) bundle.getBundle(loc).get("mensaURI"));
			m.setLat((String) bundle.getBundle(loc).get("lat"));
			m.setLon((String) bundle.getBundle(loc).get("lon"));
			mensaList.add(m);
		}

		// Get the location manager
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		if (!locationManager
				.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
			Intent myIntent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
			startActivity(myIntent);
		} else {
			
			// Register the listener with the Location Manager to receive
			// location updates
			locationManager.requestLocationUpdates(
					LocationManager.GPS_PROVIDER, 0, 0, this);
			
			Location location = locationManager
					.getLastKnownLocation(LocationManager.GPS_PROVIDER);

			GeoPoint currLoc;
			double lat;
			double lon;
			System.out.println("1");
			// Initialize the location fields
			if (location != null) {
				System.out.println("2");
				System.out.println("Provider " + provider
						+ " has been selected.");
				lat = location.getLatitude();
				lon = location.getLongitude();
				currLoc = new GeoPoint((int) (lat * 1E6), (int) (lon * 1E6));
				Log.d("lat", String.valueOf(lat));
				Log.d("lat", String.valueOf(lon));
			} else {
				System.out.println("3");
				currLoc = new GeoPoint((int) (47.267222 * 1E6),
						(int) (11.392778 * 1E6));
				Log.d("lat", "Provider not available");
				Log.d("lat", "Provider not available");
			}

			mapView = (MapView) findViewById(R.id.mapview);
			mapView.setBuiltInZoomControls(true);

			mapController = mapView.getController();
			mapController.setCenter(currLoc);
			mapController.setZoom(14);

			drawOverlays(mensaList);
			drawpinOverlay(currLoc);
		}
	}

	private void drawpinOverlay(GeoPoint currLoc) {
		pinOverlay = new PinOverlay(this.getResources().getDrawable(
				R.drawable.pin), currLoc);
		mapView.getOverlays().add(pinOverlay);
		mapView.invalidate();
	}

	private void drawOverlays(List<Mensa> mensaList) {
		if (mensaList.size() > 0) {
			mensaOverlay = new MensaOverlay(this.getResources().getDrawable(
					R.drawable.poi), mensaList, this);
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

		Intent detailsIntent = new Intent(getApplicationContext(),
				MensaDetailsActivity.class);
		detailsIntent.putExtras(bundle);

		startActivity(detailsIntent);
	}

	@Override
	public void onLocationChanged(Location location) {
		System.out.println("Location location changed");

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
