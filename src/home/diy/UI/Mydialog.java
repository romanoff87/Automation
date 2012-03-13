package home.diy.UI;

import home.diy.R;
import home.diy.Server;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class Mydialog extends DialogFragment implements OnClickListener {

	EditText ip, port, pass, log;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(STYLE_NO_TITLE, 0);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.preference_layout, container, false);
		Button valid = (Button) v.findViewById(
				R.id.valid_param_button);
		valid.setOnClickListener(this);

		SharedPreferences settings = getActivity().getSharedPreferences(
				Server.CONNECTION_INFO_SERVER_1, 0);

		String sport = settings.getString(Server.SERVER_PORT, null);
		String sip = settings.getString(Server.SERVER_IP, null);
		String slog = settings.getString(Server.SERVER_LOGIN, null);
		String spass = settings.getString(Server.SERVER_PASS, null);

		ip = (EditText) v.findViewById(R.id.ipadress);
		log = (EditText) v.findViewById(R.id.login);
		pass = (EditText) v.findViewById(R.id.password);
		port = (EditText) v.findViewById(R.id.portnumber);

		if (sport != null) {
			port.setText(sport);
		}
		if (sip != null) {
			ip.setText(sip);
		}
		if (slog != null) {
			log.setText(slog);
		}
		if (spass != null) {
			pass.setText(spass);
		}
		return v;
	}

	public void onClick(View v) {
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		Fragment prev = getFragmentManager().findFragmentByTag("dialog");
		
		switch (v.getId()) {
		case R.id.valid_param_button:

			SharedPreferences settings = getActivity().getSharedPreferences(
					Server.CONNECTION_INFO_SERVER_1, 0);
			SharedPreferences.Editor editor = settings.edit();

			editor.putString(Server.SERVER_PORT, port.getText().toString());
			editor.putString(Server.SERVER_IP, ip.getText().toString());
			editor.putString(Server.SERVER_LOGIN, log.getText().toString());
			editor.putString(Server.SERVER_PASS, pass.getText().toString());

			// Commit the edits!
			editor.commit();

			if (prev != null) {
				ft.remove(prev);
			}
			ft.addToBackStack(null);
			ft.commit();
			break;
			
		default:
			break;
		}

	}
}
