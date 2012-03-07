package home.diy;

import home.diy.UI.System_Info;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class SshSystemInfo extends AsyncTask<Session, String, List<String>> {

	private String a = "";
	private List<String> list_result,intermediary;
	public Ecouteur ecouteur;

	public SshSystemInfo() {
		list_result = new ArrayList<String>();
		intermediary = new ArrayList<String>();
	}

	@Override
	protected List<String> doInBackground(Session... sessions) {
		Channel channel;
		try {
		// nom de l'hote, version noyeau, disk, Total, used, free,
		// percentage, num secu updates, num updates, load average
			String command="uname -n";
			command+="&&uname -r";
			command+="&&uptime|cut -d ' ' -f5";
			command+="&&df -h|grep --max-count=1 /sd";
			command+="&&echo updatenumber&&/usr/lib/update-notifier/apt-check 2>&1 | cut -d ';' -f 1";
			command+="&&/usr/lib/update-notifier/apt-check 2>&1 | cut -d ';' -f 2";
			command+="&&cat /proc/loadavg";
			command+="&&free -m -o |grep Mem";
			
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
		int i = a.indexOf("\n");	
		while (i != -1) {
			intermediary.add(a.substring(0, i));
			a = a.substring(i + 1);
			i = a.indexOf("\n");
		}

		// Mise dans la liste de l'espace sur le disque.
		int k=0;
		while (k<intermediary.size()){
			String b = intermediary.get(k);
			if (b.indexOf(" ")==-1){
				list_result.add(b);
			}else{
				i = b.indexOf(" ");
				while (i != -1) {
					if (i == 0) {
						b = b.substring(1);
					} else {
						list_result.add(b.substring(0, i));
						b = b.substring(i + 1);
					}
					i = b.indexOf(" ");
				}
			}
			k++;
		}
		intermediary.clear();


		/*// mise dans la liste des load average et uptime
		String c = list_result.get(list_result.size() - 1);
		list_result.remove(list_result.size() - 1);
		i = c.indexOf(" ");
		while (i != -1) {
			if (i == 0) {
				c = c.substring(1);
			} else {
				list_result.add(list_result.size(), c.substring(0, i));
				j++;
				c = c.substring(i + 1);
			}
			i = c.indexOf(" ");
		}*/
		return list_result;
	}

	public void addobserver(RetreivingSysInfo b) {
		ecouteur = b;
	}

	protected void onPostExecute(List<String> result) {
		ecouteur.publishListResult(result);
	}

}
