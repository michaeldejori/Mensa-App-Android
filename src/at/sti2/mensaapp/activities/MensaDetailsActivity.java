package at.sti2.mensaapp.activities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import at.sti2.mensaapp.MenuHandler;
import at.sti2.mensaapp.MenuHandlerListener;
import at.sti2.mensaapp.R;
import at.sti2.mensaapp.R.id;
import at.sti2.mensaapp.R.layout;
import at.sti2.model.Mensa;
import at.sti2.model.Menu;

public class MensaDetailsActivity extends Activity implements OnClickListener, MenuHandlerListener {

	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_MAX_OFF_PATH = 250;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	private GestureDetector gestureDetector;
	View.OnTouchListener gestureListener;
	private Date date;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.details);

		System.out.println("MensaDetailsActivity.onCreate()");

		// Gesture detection
		gestureDetector = new GestureDetector(new MyGestureDetector());
		gestureListener = new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				return gestureDetector.onTouchEvent(event);
			}
		};

		View view = findViewById(R.id.detailsView_id);
		view.setOnClickListener(this);
		view.setOnTouchListener(gestureListener);

		try {

			Bundle bundle = this.getIntent().getExtras();
			// mensa data
			String name = bundle.getString("name");
			String location = bundle.getString("location");
			String id = bundle.getString("id");
			String dateS = bundle.getString("date");

			date = new Date(Date.parse(dateS));
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

			Vector<String> v = Mensa.getMenuVector(id, date);

			TextView mensa_name = (TextView) findViewById(R.id.mensa_name);
			TextView mensa_location = (TextView) findViewById(R.id.mensa_location);
			// ListView menuList = (ListView) findViewById(R.id.menuListView);
			TextView dateTxt = (TextView) findViewById(R.id.date_txt);

			// List<Map<String, String>> data = new ArrayList<Map<String,
			// String>>();
			//
			// Set<String> keySet = bundle.keySet();
			// System.out.println(keySet);
			//
			// for (String key : keySet) {
			//
			// // menu data
			// String description = bundle.getString("description");
			// String caption = bundle.getString("caption"); // menu name
			// String starts = bundle.getString("starts");
			// String ends = bundle.getString("ends");
			//
			// Map<String, String> map = new HashMap<String, String>(2);
			// map.put("title", "überschrift");
			// map.put("txt", v.get(i));
			// bundle.
			// map.putAll(bundle);
			// data.add(map);
			// }
			//
			// String[] from = new String[] { "title", "txt" };
			// int[] to = new int[] { android.R.id.text1, android.R.id.text2 };
			//
			// SimpleAdapter simpleAdapter = new SimpleAdapter(this, data,
			// android.R.layout.simple_list_item_2, from, to);
			//
			// menuList.setAdapter(simpleAdapter);

			mensa_name.setText(name);
			mensa_location.setText(location);
			dateTxt.setText(dateFormat.format(date));

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		// loading initialisation data from server
		MenuHandler iH = new MenuHandler(this);
		iH.execute(date);

	}

	public void viewDay(boolean tomorrow) {
		int MILLIS_IN_DAY = 1000 * 60 * 60 * 24;

		Date newDate;
		if (tomorrow)
			newDate = new Date(date.getTime() + MILLIS_IN_DAY);
		else
			newDate = new Date(date.getTime() - MILLIS_IN_DAY);

		Bundle bundle = getIntent().getExtras();
		bundle.putString("date", newDate.toString());
		Intent detailsIntent = new Intent(getApplicationContext(), MensaDetailsActivity.class);
		detailsIntent.putExtras(bundle);

		startActivity(detailsIntent);

	}

	@Override
	public void onClick(View v) {
		System.out.println("MensaDetailsActivity.onClick()");
	}

	class MyGestureDetector extends SimpleOnGestureListener {
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			// System.out.println("MensaDetailsActivity.MyGestureDetector.onFling()");
			// System.out.println(e1.getY() + " " + e2.getY());
			try {
				if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
					return false;
				// right to left swipe
				if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
						&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
					// nächster tag
					System.out.println("next day");
					viewDay(true);
				} else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
						&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
					// vortag
					System.out.println("day before");
					viewDay(false);
				}
			} catch (Exception e) {
				// nothing
			}
			return false;
		}

	}

	@Override
	public void onLoadingFinished(HashMap<String, Vector<Menu>> menuHM) {

		try {
			List<Map<String, String>> data = new ArrayList<Map<String, String>>();

			Set<String> keySet = menuHM.keySet();
			System.out.println(keySet);

			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyy");

			Vector<Menu> v = menuHM.get(sdf.format(date));

			for (Menu menu : v) {
				Map<String, String> map = new HashMap<String, String>(2);
				map.put("title", menu.getName());
				map.put("txt", menu.getDescription());
				data.add(map);
			}

			String[] from = new String[] { "title", "txt" };
			int[] to = new int[] { android.R.id.text1, android.R.id.text2 };

			SimpleAdapter simpleAdapter = new SimpleAdapter(this, data,
					android.R.layout.simple_list_item_2, from, to);

			ListView menuList = (ListView) findViewById(R.id.menuListView);
			menuList.setAdapter(simpleAdapter);

		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), "Data not available", Toast.LENGTH_SHORT)
					.show();
		}
	}

}
