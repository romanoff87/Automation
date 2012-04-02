package home.diy.UI;

import home.diy.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class LoadingFragment extends Fragment {
	
	private String message;
	
	public LoadingFragment(String text){
		message = text;
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
			return inflater.inflate(R.layout.loading, container, false);
	}
	
	public void onResume(){
		super.onResume();
		TextView TV_message = (TextView) getActivity().findViewById(R.id.message);
		TV_message.setText(message);
	}

}
