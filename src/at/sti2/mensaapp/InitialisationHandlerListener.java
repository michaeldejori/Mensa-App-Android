package at.sti2.mensaapp;

import java.util.*;
import at.sti2.model.Mensa;

public interface InitialisationHandlerListener {

	public void onInitialLoadingFinished(HashMap<String,Vector<Mensa>> feeds);

	
}
