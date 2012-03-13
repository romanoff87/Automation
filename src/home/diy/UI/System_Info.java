package home.diy.UI;

import home.diy.Ecouteur;
import home.diy.R;
import home.diy.RetreivingSysInfo;
import home.diy.SshSystemInfo;
import home.diy.UpdateTime;

import java.util.List;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jcraft.jsch.Session;

public class System_Info extends Fragment implements Ecouteur {

	private List<String> System_information;
	private Session session;
	private long lastupdatetime;
	private UpdateTime updatetimetask;
	private Handler shechuleforupdatetime;

	public System_Info(List<String> Sysinfo,Session ses) {
		System_information = Sysinfo;
		session=ses;
		shechuleforupdatetime= new Handler();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.new_sys_info, container, false);
	}

	@Override
	public void onResume() {
		super.onResume();
		int i,totalram,usedram;
		TextView t;
		ProgressBar p;
		String s;
		
		lastupdatetime= SystemClock.uptimeMillis();
		
		t = (TextView) getActivity().findViewById(R.id.hostname);
		t.setText(System_information.get(0));
		
		t = (TextView) getActivity().findViewById(R.id.kernelversion);
		t.setText(System_information.get(1));
		
		t = (TextView) getActivity().findViewById(R.id.uptime);
		t.setText(System_information.get(2));
		
		t = (TextView) getActivity().findViewById(R.id.usedspace);
		t.setText(System_information.get(7));
		
		t = (TextView) getActivity().findViewById(R.id.freespace);
		t.setText(System_information.get(6));
		
		t = (TextView) getActivity().findViewById(R.id.totalspace);
		t.setText(System_information.get(4));
		
		p=(ProgressBar) getActivity().findViewById(R.id.diskspacebar);
		s=System_information.get(7).substring(0,System_information.get(7).indexOf("%"));
		p.setProgress(Integer.parseInt(s));
		
		p=(ProgressBar) getActivity().findViewById(R.id.ramspacebar);
		i=System_information.indexOf("Mem:");
		t= (TextView) getActivity().findViewById(R.id.freeram);
		t.setText(System_information.get(i+3)+"M");
		t= (TextView) getActivity().findViewById(R.id.totalram);
		t.setText(System_information.get(i+1)+"M");
		totalram=Integer.parseInt(System_information.get(i+1));
		usedram=Integer.parseInt(System_information.get(i+2));
		p.setProgress(usedram*100/totalram);
		t= (TextView) getActivity().findViewById(R.id.usedram);
		t.setText((usedram*100/totalram)+"%");
		
		t = (TextView) getActivity().findViewById(R.id.numberofupdate);
		t.setText(System_information.get(System_information.indexOf("updatenumber")+1));
		
		t = (TextView) getActivity().findViewById(R.id.numberofsecurityupdate);
		int k=System_information.indexOf("updatenumber")+2;
		t.setText(System_information.get(k));
		
		if (updatetimetask!=null){
			shechuleforupdatetime.removeCallbacks(updatetimetask);
		}
		
		updatetimetask=new UpdateTime((TextView) getActivity().findViewById(R.id.lastupdatetime), lastupdatetime,shechuleforupdatetime);
		shechuleforupdatetime.removeCallbacks(updatetimetask);
		shechuleforupdatetime.postDelayed(updatetimetask, 100);
	
	}
	
	public void reloadSystemInfo(){
		
		FragmentManager fm=getFragmentManager();
		FragmentTransaction ft=fm.beginTransaction();
		ft.replace(R.id.System_Info, new RetreivingSysInfo(session));
		ft.commit();
		
	}

	/*@Override
	public void publishStringResult(String result) {
		TextView t = (TextView) getActivity().findViewById(R.id.hostname);
		t.setText(result);
	}*/

	@Override
	public void publishedResult(Session result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateProgress(String[] progress) {
		// TODO Auto-generated method stub

	}

	/*@Override
	public void publishListResult(List<String> result) {
		
	}*/

	@Override
	public void publishListResult(Object result) {
		// TODO Auto-generated method stub
		
	}
}
