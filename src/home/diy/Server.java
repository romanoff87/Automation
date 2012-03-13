package home.diy;

import android.app.Fragment;
import android.content.SharedPreferences;


public class Server extends Fragment{
	
	private String servername,ipaddress,login,password;
	private int port;
	public static final String CONNECTION_INFO_SERVER_1="server_info";
	public static final String SERVER_LOGIN="login";
	public static final String SERVER_PASS="pass";
	public static final String SERVER_IP="ip";
	public static final String SERVER_PORT="port";
	
	private SharedPreferences settings;
	
	public Server(String name){
		servername=name;
		settings = getActivity().getSharedPreferences(
				Server.CONNECTION_INFO_SERVER_1, 0);
	}
	
	public Server(String name,String ip, String log, String pass, int port){
		setServername(name);
		setIpaddress(ip);
		setLogin(log);
		setPassword(pass);
		setPort(port);
	}

	public String getServername() {
		return servername;
	}

	public void setServername(String servername) {
		this.servername = servername;
	}

	public String getIpaddress() {
		return settings.getString(Server.SERVER_IP, null);
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	public String getLogin() {
		return settings.getString(Server.SERVER_LOGIN, null);
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return settings.getString(Server.SERVER_PASS, null);
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPort() {
		return Integer.parseInt(settings.getString(Server.SERVER_PORT, null)); 
	}

	public void setPort(int port) {
		this.port = port;
	}
}
