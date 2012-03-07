package home.diy;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import android.os.AsyncTask;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class SshUpdate extends AsyncTask<Session, Void, String> {

	private String a = null;
	public Ecouteur ecouteur;

	@Override
	protected String doInBackground(Session... sessions) {
		Channel channel;
		try {
		// nom de l'hote, version noyeau, disk, Total, used, free,
		// percentage, num secu updates, num updates, load average
			String command="";
			
			
			channel = sessions[0].openChannel("exec");
			((ChannelExec) channel).setCommand(command);
			channel.setInputStream(null);
			((ChannelExec) channel).setErrStream(System.err);
			InputStream in = channel.getInputStream();
			channel.connect();
			
			byte[] tmp = new byte[1024];
			while (true) {
				while (in.available() > 0) {
					int i = in.read(tmp, 0, 1024);
					if (i < 0)break;
					a += new String(tmp, 0, i);
				}
				if (channel.isClosed()) {
					/*System.out.println("exit-status: "
							+ channel.getExitStatus());*/
					break;
				}
				try {
					Thread.sleep(1000);
				} catch (Exception ee) {
				}
			}
			channel.disconnect();

		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return a;
	}

	protected void onPostExecute(String result) {
		a = result;
		// ecouteur.publishedResult(result);
	}

	public void addobserver(ServeurActivity a) {
		ecouteur = a;
	}

}
