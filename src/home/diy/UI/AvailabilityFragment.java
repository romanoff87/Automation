package home.diy.UI;

import home.diy.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AvailabilityFragment extends Fragment{

	private Boolean is_target_available;
	
	public AvailabilityFragment(Boolean availability){
		is_target_available = availability;
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if ( is_target_available ){
			return inflater.inflate(R.layout.targetisreachable, container, false);
		}
		else{
			return inflater.inflate(R.layout.targetisnotreachable, container, false);
		}
	}

	public void onResume() {
		super.onResume();	
	}
}
