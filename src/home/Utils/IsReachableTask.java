package home.Utils;

import java.io.IOException;
import java.net.Socket;

import android.os.AsyncTask;

public class IsReachableTask extends AsyncTask<String, String, Boolean> {
	
	ReachableListener listener;

	@Override
	protected Boolean doInBackground(String... params) {
		boolean is_reachable = false;
		String ip = params[0];
		try {
			int port = Integer.parseInt(params[1]);
			Socket socket = new Socket(ip, port);
			socket.close();
			is_reachable = true;
		} catch (IOException ex) {
			is_reachable = false;
		}catch (NumberFormatException e) {
			is_reachable = false;
		}
		return is_reachable;
	}
	
	public void addListener(ReachableListener fragment){
		listener=fragment;
	}
	
	protected void onPostExecute(Boolean result) {
		if ( result == true ){
			listener.targetIsReachable();
		}
		else{
			listener.targetIsNotReachable();
		}
	}
}
