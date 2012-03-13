package home.diy;

import com.jcraft.jsch.Session;

public interface Ecouteur {

	public void publishedResult(Session result);

	public void updateProgress(String[] progress);

	/*public void publishStringResult(String result);
	
	public void publishListResult(List<String> result);*/
	
	public void publishListResult(Object result);

}
