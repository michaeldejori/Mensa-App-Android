package at.sti2.mensaapp;

import android.os.AsyncTask;

public class InitialisationHandler extends AsyncTask<String, Integer, String> {

	private InitialisationHandlerListener iHL;
	
	public InitialisationHandler(InitialisationHandlerListener iHL){
		this.iHL = iHL;
	}
	
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		
		// load initial mensas
		
		return "sdfsdf";
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		this.iHL.onInitialLoadingFinished(null);
		super.onPostExecute(result);
	}

}
