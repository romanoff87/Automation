package home.diy.UI;

import home.diy.R;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class Action_frag extends Fragment implements OnClickListener{

	
	private String Actionlist;
	
	public Action_frag(String Action_list) {
		Actionlist=Action_list;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.action_frag_layout, container, false);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		Button torrentlist = (Button)getActivity().findViewById(R.id.button_torrentlist);
		torrentlist.setText(Actionlist);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.systemupdate_button:
			
			break;

		default:
			break;
		}
		
	}
}
