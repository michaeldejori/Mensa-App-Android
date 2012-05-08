package at.sti2.model;

import android.os.Bundle;

public class Mensa {
	private String name;
	private String location;
	private String lat;
	private String lon;
	
	public Mensa(String name, String location, String lat, String lon) {
		super();
		this.name = name;
		this.location = location;
		this.lat = lat;
		this.lon = lon;
	}
	
	public Mensa() {
		// TODO Auto-generated constructor stub
	}

	public Bundle getBundle(){
		Bundle bundle = new Bundle();
		bundle.putString("name", getName());
		bundle.putString("location", getLocation());
		bundle.putString("lat", getLat());
		bundle.putString("lon", getLon());
		
		return bundle;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLon() {
		return lon;
	}
	public void setLon(String lon) {
		this.lon = lon;
	}
}
