package home.diy;


public class Server{
	
	private String servername,ipaddress,login,password;
	private int port;
	public static final String CONNECTION_INFO_SERVER_1="server_info";
	public static final String SERVER_LOGIN="login";
	public static final String SERVER_PASS="pass";
	public static final String SERVER_IP="ip";
	public static final String SERVER_PORT="port";
	
	public Server(String name){
		servername=name;
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
		return ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
}
