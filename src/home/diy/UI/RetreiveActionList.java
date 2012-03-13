package home.diy.UI;

import home.diy.Ecouteur;
import home.diy.R;
import home.diy.SshListActions;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcraft.jsch.Session;

public class RetreiveActionList extends Fragment implements Ecouteur{
	
	private Session session;
	
	public RetreiveActionList(Session ses){
		session=ses;
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.retreivingdata, container, false);
	}
	
	
	public void onResume(){
		super.onResume();
		SshListActions ListActionTask= new SshListActions();
		ListActionTask.addobserver(this);
		ListActionTask.execute(session,session);
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
		FragmentManager fragmentManager=getFragmentManager();
		FragmentTransaction ft=fragmentManager.beginTransaction();
		ft.replace(R.id.frame_menu, new ListOfAction());
		ft.commit();
	}

	@Override
	public void publishListResult(List<String> result) {
		// TODO Auto-generated method stub
		
	}*/

	@Override
	public void publishListResult(Object result) {
		// TODO Auto-generated method stub
		
	}

}
