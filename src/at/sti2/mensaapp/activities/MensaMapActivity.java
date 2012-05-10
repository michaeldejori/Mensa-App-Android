package at.sti2.mensaapp.activities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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

public class MensaMapActivity extends MapActivity implements MensaOverlayOnTabListener{

	private MapView mapView;
	private MapController mapController;
	private ItemizedOverlay<OverlayItem> mensaOverlay;

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

		GeoPoint ibk = new GeoPoint((int) (47.267222 * 1E6), (int) (11.392778 * 1E6));

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
	public void onSpritItemTap(String item) {
		// TODO Auto-generated method stub
		
	}

}
