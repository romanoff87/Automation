package home.diy.UI;

import home.diy.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

// voir pour créer une classe derive de Listfragment.
public class Menu_vertical extends Fragment {

	public Menu_vertical() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.menu, container, false);
	}
}
