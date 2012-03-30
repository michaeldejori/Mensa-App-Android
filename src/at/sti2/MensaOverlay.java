package at.sti2;

import java.util.List;

import org.sengaro.demo.rss.SpritMessage;

import android.graphics.drawable.Drawable;
import android.util.Log;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class MensaOverlay extends ItemizedOverlay<OverlayItem> {

	public interface SpritOverlayListener {
		public void onSpritItemTap(SpritMessage item);
	}

	private List<SpritMessage> feeds;
	private SpritOverlayListener listener;

	public MensaOverlay(List<SpritMessage> feeds, Drawable defaultDrawable,
			SpritOverlayListener listener) {
		
		super(boundCenterBottom(defaultDrawable));
		
		this.feeds = feeds;
		this.populate();
		this.listener = listener;
	}

	@Override
	protected OverlayItem createItem(int position) {
		SpritMessage msg = feeds.get(position);

		Log.d("Overlay", "createItem " + msg.toString());

		return new OverlayItem(msg.getGeoPoint(), msg.getTitle(), "test");
	}

	@Override
	public int size() {
		return feeds.size();
	}

	@Override
	protected boolean onTap(int index) {

		listener.onSpritItemTap(feeds.get(index));

		return super.onTap(index);
	}

}
