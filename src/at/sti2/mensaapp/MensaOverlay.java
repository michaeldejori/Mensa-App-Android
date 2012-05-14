package at.sti2.mensaapp;

import java.util.List;

import android.graphics.drawable.Drawable;
import at.sti2.model.Mensa;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class MensaOverlay extends ItemizedOverlay<OverlayItem>{

	private MensaOverlayOnTabListener listener;
	private List<Mensa> mensaList;
	
	public MensaOverlay(Drawable defaultMarker, List<Mensa> mensaList, MensaOverlayOnTabListener listener) {
		super(boundCenter(defaultMarker));
		this.mensaList = mensaList;
		this.listener = listener;
		
		this.populate();
	}

	@Override
	protected OverlayItem createItem(int i) {
		double lat = Double.parseDouble(mensaList.get(i).getLat());
		double lon = Double.parseDouble(mensaList.get(i).getLon());
		GeoPoint point = new GeoPoint((int)(lat * 1e6),
                (int)(lon * 1e6));
		OverlayItem item = new OverlayItem(point, mensaList.get(i).getName(), "");
		return item;
	}

	@Override
	public int size() {
		return mensaList.size();
	}
	
	protected boolean onTap(int index){
		listener.onSpritItemTap(this.mensaList.get(index));
		return true;
	}

}
