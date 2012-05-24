package at.sti2.mensaapp.activities;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

public class ListViewCitiesActivity extends ListActivity {
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		
		List<String> list = new Vector<String>();
		
		Bundle bundle = getIntent().getExtras();
		
		Set<String> mensas = bundle.keySet();

		for (Iterator<String> iterator = mensas.iterator(); iterator
				.hasNext();) {
			String mensa = (String) iterator.next();
			String location = (String)(bundle.getBundle(mensa).get("location"));
			if (!list.contains(location)){
				list.add(location);
			}
		}
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, list);
		setListAdapter(adapter);

	}

	@Override
	protected void onListItemClick(android.widget.ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);

		
		System.out.println(l.getItemAtPosition(position));

		Bundle bundle = getIntent().getExtras();
		final Bundle newBundle = new Bundle();
		Set<String> mensas = bundle.keySet();

		for (Iterator<String> iterator = mensas.iterator(); iterator
				.hasNext();) {
			String mensa = (String) iterator.next();
			String location = (String)(bundle.getBundle(mensa).get("location"));
			if (location.equals(l.getItemAtPosition(position))){
				bundle.getBundle(mensa);
				newBundle.putBundle(mensa, bundle.getBundle(mensa));
			}
		}

		
		Intent listmensas = new Intent(getApplicationContext(), ListViewMensasActivity.class);
		listmensas.putExtras(newBundle);

		startActivity(listmensas);
	}
}
