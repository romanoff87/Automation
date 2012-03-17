package home.diy;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PreferenceFragment extends Fragment implements OnClickListener{
	
	private EditText ET_ip, ET_log, ET_pass, ET_port;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.preference_layout, container, false);
	}
	
	@Override
	public void onResume(){
		super.onResume();
		
		String sport, sip, slog, spass;
		
		ET_ip = (EditText) getActivity().findViewById(R.id.ipadress);
		ET_log = (EditText) getActivity().findViewById(R.id.login);
		ET_pass = (EditText) getActivity().findViewById(R.id.password);
		ET_port = (EditText) getActivity().findViewById(R.id.portnumber);
		Button B_valid = (Button) getActivity().findViewById(R.id.valid_param_button);
		
		B_valid.setOnClickListener(this);

		SharedPreferences settings = getActivity().getSharedPreferences(
				Server.CONNECTION_INFO_SERVER_1, 0);

		sport = settings.getString(Server.SERVER_PORT, null);
		sip = settings.getString(Server.SERVER_IP, null);
		slog = settings.getString(Server.SERVER_LOGIN, null);
		spass = settings.getString(Server.SERVER_PASS, null);

		if (sport != null) {
			ET_port.setText(sport);
		}
		if (sip != null) {
			ET_ip.setText(sip);
		}
		if (slog != null) {
			ET_log.setText(slog);
		}
		if (spass != null) {
			ET_pass.setText(spass);
		}
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.valid_param_button:

			SharedPreferences settings = getActivity().getSharedPreferences(
					Server.CONNECTION_INFO_SERVER_1, 0);
			SharedPreferences.Editor editor = settings.edit();
			
			if (ET_port!=null)
			{
				editor.putString(Server.SERVER_PORT, ET_port.getText().toString());
			}
			
			if (ET_ip!=null)
			{
				editor.putString(Server.SERVER_IP, ET_ip.getText().toString());
			}
			
			if (ET_log!=null)
			{
				editor.putString(Server.SERVER_LOGIN, ET_log.getText().toString());
			}
			
			if (ET_pass!=null)
			{
				editor.putString(Server.SERVER_PASS, ET_pass.getText().toString());
			}

			// Commit the edits!
			editor.commit();
			
			((AutomationActivity)getActivity()).onClick(getActivity().findViewById(R.id.preference_button));
			
			break;
			
		default:
			break;
		}
		
	}

}
