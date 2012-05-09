package at.sti2.mensaapp;

import java.util.*;

import at.sti2.model.Mensa;
import at.sti2.model.Menu;

public interface MenuHandlerListener {

	public void onLoadingFinished(HashMap<String, Vector<Menu>> menuHM);

	
}

