package at.sti2.mensaapp.activities;

import java.util.List;
import java.util.Set;
import java.util.Vector;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

public class ListViewActivity extends ListActivity {
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		
		List<String> list = new Vector<String>();

		Bundle stdBundle = getIntent().getExtras();
		Set<String> keySet = stdBundle.keySet();
		System.out.println(keySet);
	
		// make list of mensas that are in the previously
		// selected city
		for (String key : keySet) {
			list.add(getIntent().getBundleExtra(key).getString("name"));
		}
		
		System.out.println(list);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, list);
		setListAdapter(adapter);

	}

	@Override
	protected void onListItemClick(android.widget.ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);

		System.out.println(l.getItemAtPosition(position));


		String name = (String) l.getItemAtPosition(position);
		Bundle bundle = getIntent().getBundleExtra(name);

		Intent detailsIntent = new Intent(getApplicationContext(), MensaDetailsActivity.class);
		detailsIntent.putExtras(bundle);

		startActivity(detailsIntent);
	}
}
