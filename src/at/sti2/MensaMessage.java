package at.sti2;

import java.net.URL;
import java.util.HashMap;

import com.google.android.maps.GeoPoint;

public class MensaMessage {

	private String name;
	private String city;
	private GeoPoint geoPoint;
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public GeoPoint getGeoPoint() {
		return geoPoint;
	}
	public void setGeoPoint(GeoPoint geoPoint) {
		this.geoPoint = geoPoint;
	}
	
}
