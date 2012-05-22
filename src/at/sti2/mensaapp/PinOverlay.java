package at.sti2.mensaapp;

import java.util.ArrayList;
import java.util.List;

import android.graphics.drawable.Drawable;
import android.location.Address;
import at.sti2.model.Mensa;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class PinOverlay extends ItemizedOverlay<OverlayItem>{
	
	private ArrayList<OverlayItem> overlayItemList = new ArrayList<OverlayItem>();
	
	
	public PinOverlay(Drawable defaultMarker, GeoPoint currLoc) {
		super(boundCenter(defaultMarker));
		this.addItem(currLoc);
		this.populate();
	}

	public void addItem(GeoPoint geo){
		OverlayItem newItem = new OverlayItem(geo, "Your position", "");
		this.overlayItemList.add(newItem);
	}
	
	@Override
	protected OverlayItem createItem(int i) {
		return this.overlayItemList.get(i);
	}

	@Override
	public int size() {
		return overlayItemList.size();
	}
}
