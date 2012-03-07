package home.diy;

import home.diy.UI.RetreiveActionList;
import home.diy.UI.System_Info;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import android.os.AsyncTask;

public class SshListActions extends AsyncTask<Session, String, List<String>>{
	
	private List<String> actionsList;
	public Ecouteur ecouteur;

	@Override
	protected List<String> doInBackground(Session... sessions) {
		Channel channel;
		actionsList=new ArrayList<String>();
		try {
		// nom de l'hote, version noyeau, disk, Total, used, free,
		// percentage, num secu updates, num updates, load average
			String command="cd ~/.Automation&&find *.sh";
			
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
		ecouteur.publishStringResult(result);
	}
	
	public void addobserver(RetreiveActionList b) {
		ecouteur = b;
	}
	
}

