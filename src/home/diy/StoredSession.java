package home.diy;

import java.util.Properties;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class StoredSession {

	private Properties config;
	private JSch jsch;
	private String userName, serverUrl, password;
	private int portNumber;

	/**
	 * Create a Stored session in the jsch term of it and configure it
	 * 
	 * @param conf
	 */
	public StoredSession(Properties conf, String url, String username,
			String pass, int port) {
		jsch = new JSch();
		config = conf;
		serverUrl = url;
		userName = username;
		password = pass;
		portNumber = port;
	}

	/**
	 * 
	 * @return the created session if all goes well and null if not.
	 */
	public Session getSession() {
		Session session;
		try {
			session = jsch.getSession(userName, serverUrl);
			session.setConfig(config);
			session.setPort(portNumber);
			session.setPassword(password);
			return session;
		} catch (JSchException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Properties getConfig() {
		return config;
	}

	public void setConfig(Properties config) {
		this.config = config;
	}

	public JSch getJsch() {
		return jsch;
	}

	public void setJsch(JSch jsch) {
		this.jsch = jsch;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getServerUrl() {
		return serverUrl;
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPortNumber() {
		return portNumber;
	}

	public void setPortNumber(int portNumber) {
		this.portNumber = portNumber;
	}

}
