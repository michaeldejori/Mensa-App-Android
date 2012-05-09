package at.sti2.mensaapp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import at.sti2.model.Mensa;

public class MensaDetailsActivity extends Activity implements OnClickListener {

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
			String name = bundle.getString("name");
			String location = bundle.getString("location");
			String id = bundle.getString("id");
			String dateS = bundle.getString("date");

			date = new Date(Date.parse(dateS));

			Vector<String> v = Mensa.getMenuVector(id, date);

			TextView mensa_name = (TextView) findViewById(R.id.mensa_name);
			TextView mensa_location = (TextView) findViewById(R.id.mensa_location);
			ListView menuList = (ListView) findViewById(R.id.menuListView);

			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, v);
			menuList.setAdapter(adapter);

			mensa_name.setText(name);
			mensa_location.setText(location);

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

	}

	public void viewDay(boolean tomorrow) {
		int MILLIS_IN_DAY = 1000 * 60 * 60 * 24;
//		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
//		String prevDate = dateFormat.format(date.getTime() - MILLIS_IN_DAY);
//		String currDate = dateFormat.format(date.getTime());
//		String nextDate = dateFormat.format(date.getTime() + MILLIS_IN_DAY);

//		System.out.println(prevDate + " " + currDate + " " + nextDate);

		Date newDate;
		if(tomorrow)
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
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		System.out.println("MensaDetailsActivity.onPause()");
	}

	@Override
	public void onClick(View v) {
		System.out.println("MensaDetailsActivity.onClick()");
	}

	class MyGestureDetector extends SimpleOnGestureListener {
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			System.out.println("MensaDetailsActivity.MyGestureDetector.onFling()");
			System.out.println(e1.getY() + " " + e2.getY());
			try {
				if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
					return false;
				// right to left swipe
				if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
						&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
					// nächster tag
					Toast.makeText(MensaDetailsActivity.this, "Left Swipe", Toast.LENGTH_SHORT)
							.show();
					System.out.println("left");
					viewDay(true);
				} else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
						&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
					// vortag
					Toast.makeText(MensaDetailsActivity.this, "Right Swipe", Toast.LENGTH_SHORT)
							.show();
					System.out.println("right");
					viewDay(false);
				}
			} catch (Exception e) {
				// nothing
			}
			return false;
		}

	}

}
