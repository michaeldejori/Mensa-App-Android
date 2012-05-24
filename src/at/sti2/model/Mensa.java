package at.sti2.model;

import java.util.Date;
import java.util.Vector;

import android.os.Bundle;

public class Mensa {
	private String name;
	private String mensaURI;
	private String location;
	private String lat;
	private String lon;

	public Mensa(String name, String mensaURI, String location, String lat, String lon) {
		super();
		this.mensaURI = mensaURI;
		this.name = name;
		this.location = location;
		this.lat = lat;
		this.lon = lon;
	}

	public Mensa() {
	}

	public Bundle getBundle() {
		Bundle bundle = new Bundle();
		bundle.putString("name", getName());
		bundle.putString("mensaURI", getMensaURI());
		bundle.putString("location", getLocation());
		bundle.putString("lat", getLat());
		bundle.putString("lon", getLon());
		return bundle;
	}

	// TODO:
	public Vector<String> getMenuVector(Date date) {

		Vector<String> v = new Vector<String>();
		v.add("Schnitzel 3000");

		return v;
	}

	public static Vector<String> getMenuVector(String id, Date date) {

		Vector<String> v = new Vector<String>();
		v.add("Schnitzel 3000");
		v.add("Eintopf .. 5€");
		v.add("Tagessuppe " + date.getDay());
		return v;
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

	public String getMensaURI() {
		return mensaURI;
	}

	public void setMensaURI(String mensaURI) {
		this.mensaURI = mensaURI;
	}
}
