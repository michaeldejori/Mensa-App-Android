package at.sti2;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;


public class FeedHandler extends AsyncTask<String, Integer, List<MensaMessage>> {

	public interface FeedHandlerListener {
		public void feedsLoadingStart();

		public void feedsLoaded(List<SpritMessage> feeds);
	}

	FeedHandlerListener listener;

	public FeedHandler(FeedHandlerListener listener) {
		this.listener = listener;
	}

	@Override
	protected List<SpritMessage> doInBackground(String... params) {

		int count = params.length;
		List<SpritMessage> result = new ArrayList<SpritMessage>();

		for (int i = 0; i < count; i++) {
			 SaxSpritFeedParser parser = new SaxSpritFeedParser(params[i]);
			 result.addAll(parser.parse());
		}

		return result;
	}

	@Override
	protected void onPostExecute(List<SpritMessage> result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		listener.feedsLoaded(result);
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		listener.feedsLoadingStart();
	}

}
