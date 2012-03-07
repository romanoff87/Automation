package home.diy;

import java.util.Properties;

import android.os.AsyncTask;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class SshConnection extends AsyncTask<String, String, Session> {

	public String plo;
	public Ecouteur ecouteur;

	public String Getresult() {
		return plo;
	}

	@Override
	protected Session doInBackground(String... params) {

		// TODO Auto-generated method stub
		// TextView t=new TextView(this);
		String serverUrl = params[0];
		String userName = params[2];
		String password = params[3];
		String po1 = null;
		// t=(TextView)findViewById(R.id.list);
		// t.setText(serverUrl);

		JSch jsch = new JSch();
		String po = "jsh creer";
		// t=(TextView)findViewById(R.id.list);
		// t.setText(po);

		Properties config = new Properties();
		config.put("StrictHostKeyChecking", "no");
		config.put("compression.s2c", "zlib,none");
		config.put("compression.c2s", "zlib,none");

		Session session;
		try {
			session = jsch.getSession(userName, serverUrl);
			po1 = "Setting Connection Parameters...";
			publishProgress(po1);
			
			session.setConfig(config);
			session.setPort(22);
			session.setPassword(password);

			po1 = "Connecting...";
			publishProgress(po1);
			session.connect();

			po1 = "Connected";
			publishProgress(po1);
			
		} catch (JSchException e) {
			e.printStackTrace();
			po1 = "Error";
			publishProgress(po1);
			return null;
		}

		return session;
	}

	protected void onProgressUpdate(String... progress) {
		ecouteur.updateProgress(progress);
	}

	public void addobserver(ServeurActivity a) {
		ecouteur = a;
	}

	protected void onPostExecute(Session result) {
		ecouteur.publishedResult(result);
	}

}