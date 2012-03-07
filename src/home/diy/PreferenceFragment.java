package home.diy;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PreferenceFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.preference_layout, container, false);
	}
	
	@Override
	public void onResume(){
		super.onResume();
		
	}
	
	public void saveServer(Server server){
		
	}

}
