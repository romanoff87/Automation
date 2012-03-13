package home.diy;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcraft.jsch.Session;

public class RetreivingSysInfo extends Fragment implements Ecouteur{
	
	private Session session;
	
	public RetreivingSysInfo(Session ses){
		session=ses;
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.retreivingdata, container, false);
	}
	
	
	public void onResume(){
		super.onResume();
		SshSystemInfo RetreiveSysinfoTask=(SshSystemInfo)new SshSystemInfo();
		RetreiveSysinfoTask.addobserver(this);
		RetreiveSysinfoTask.execute(session,session);
		
	}

	@Override
	public void publishedResult(Session result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateProgress(String[] progress) {
		// TODO Auto-generated method stub
		
	}

	/*@Override
	public void publishStringResult(String result) {
		// TODO Auto-generated method stub
		
	}*/

	/*@Override
	public void publishListResult(List<String> result) {
		FragmentManager fm=getFragmentManager();
		FragmentTransaction ft=fm.beginTransaction();
		ft.replace(R.id.System_Info, new System_Info(result,session),"sys_info_frag");
		ft.commit();
	}*/

	@Override
	public void publishListResult(Object result) {
		// TODO Auto-generated method stub
		
	}

}
