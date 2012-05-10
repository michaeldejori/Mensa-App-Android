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
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;
import at.sti2.mensaapp.MenuHandler;
import at.sti2.mensaapp.MenuHandlerListener;
import at.sti2.mensaapp.R;
import at.sti2.model.Mensa;
import at.sti2.model.Menu;

public class MensaDetailsActivity extends Activity implements OnClickListener, MenuHandlerListener {

	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_MAX_OFF_PATH = 250;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	private GestureDetector gestureDetector;
	View.OnTouchListener gestureListener;
	private Date date;
	private HashMap<String, Vector<Menu>> menuHM;
	private SimpleDateFormat dateFormat;
	private String mensaURI;
	private String mensalocation;
	private String mensaname;
	private TextView dateTxt;

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
		this.dateFormat = new SimpleDateFormat("dd.MM.yyyy");

		// gesture detection
		View view = findViewById(R.id.detailsView_id);
		view.setOnClickListener(this);
		view.setOnTouchListener(gestureListener);

		// gesture detection in list
		ListView menuList = (ListView) findViewById(R.id.menuListView);
		menuList.setOnTouchListener(gestureListener);

		// mensa data
		Bundle bundle = this.getIntent().getExtras();
		mensaname = bundle.getString("name");
		mensalocation = bundle.getString("location");
		mensaURI = bundle.getString("mensaURI");
		
		Log.d("michi", mensalocation+mensaname+mensaURI);
		
		// todays date
		String dateToday = new Date().toString();

		try {
			this.date = new Date(Date.parse(dateToday));
		} catch (Exception e) {
			System.err.println(e.getMessage());
			this.date = new Date();
		}

		TextView mensa_name = (TextView) findViewById(R.id.mensa_name);
		TextView mensa_location = (TextView) findViewById(R.id.mensa_location);
		dateTxt = (TextView) findViewById(R.id.date_txt);

		mensa_name.setText(mensaname);
		mensa_location.setText(mensalocation);
		dateTxt.setText(dateFormat.format(date));

		// ViewSwitcher viewSwitcher = new
		// ViewSwitcher(getApplicationContext());
		// viewSwitcher.addView()

		// load menu data from server
		MenuHandler iH = new MenuHandler(this, mensaURI);
		//TODO: id als parameter übergeben
		iH.execute(date);

	}

	public void viewDay(boolean tomorrow) {
		int MILLIS_IN_DAY = 1000 * 60 * 60 * 24;

		Date newDate;
		if (tomorrow)
			newDate = new Date(date.getTime() + MILLIS_IN_DAY);
		else
			newDate = new Date(date.getTime() - MILLIS_IN_DAY);

		this.date = newDate;
		dateTxt.setText(dateFormat.format(date));
		fillListView(menuHM, newDate);

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
		this.menuHM = menuHM;
		fillListView(menuHM, date);
	}

	private void fillListView(HashMap<String, Vector<Menu>> menuHM, Date date) {

		Set<String> keySet = menuHM.keySet();
		System.out.println(keySet);

		Vector<Menu> v = menuHM.get(dateFormat.format(date));
		if (v == null) {
			//TODO: load new data
			v = new Vector<Menu>();
			
			Toast.makeText(getApplicationContext(), "Data not available", Toast.LENGTH_SHORT)
			.show();
		}

		fillListView(v);

	}

	private void fillListView(Vector<Menu> v) {
		List<Map<String, String>> data = new ArrayList<Map<String, String>>();

		for (Menu menu : v) {
			Map<String, String> map = new HashMap<String, String>(2);
			map.put("title", menu.getName());
			map.put("txt", menu.getDescription());
			data.add(map);
		}

		//TODO: where should we display the availability 
		
		String[] from = new String[] { "title", "txt" };
		int[] to = new int[] { android.R.id.text1, android.R.id.text2 };

		SimpleAdapter simpleAdapter = new SimpleAdapter(this, data,
				android.R.layout.simple_list_item_2, from, to);

		ListView menuList = (ListView) findViewById(R.id.menuListView);
		menuList.setAdapter(simpleAdapter);
	}

}
