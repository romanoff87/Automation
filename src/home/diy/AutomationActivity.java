package home.diy;

import home.diy.UI.Mydialog;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class AutomationActivity extends Activity implements OnClickListener {

	private Boolean isPreferenceViewExpended;
	final static int EXTENDED_PREFERENCE_VIEW_WIDTH = 300;
	final static int RETRACTED_PREFERENCE_VIEW_WIDTH = 0;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// The preference view is closed
		isPreferenceViewExpended = false;
	}

	@Override
	public void onResume() {
		super.onResume();
		ImageButton IB_preference_arrow = (ImageButton) findViewById(R.id.preference_button);
		ImageButton IB_connection = (ImageButton) findViewById(R.id.connection_button);
		TextView TV_alpha2 = (TextView) findViewById(R.id.entry1);
		
		// Set the font of the Server Name.
		Typeface tf = Typeface.createFromAsset(getAssets(),
				"fonts/Roboto-Thin.ttf");
		TV_alpha2.setTypeface(tf);

		// Add a click listener to  both preference and connection arrow button.
		IB_preference_arrow.setOnClickListener(this);
		IB_connection.setOnClickListener(this);

		// Updates the ip and login display under the server name according to
		// the values used to connect to the server
		updateIPAndLoginDisplay();
	}

	private void updateIPAndLoginDisplay() {
		TextView TV_login = (TextView) findViewById(R.id.login_display);
		TextView TV_ip = (TextView) findViewById(R.id.ip_display);

		// Open the shared preferences containing the connection information.
		SharedPreferences settings = getSharedPreferences(
				Server.CONNECTION_INFO_SERVER_1, 0);

		// Retreive the values of the login, ip and port used to connect.
		String login = settings.getString(Server.SERVER_LOGIN, null);
		String ip = settings.getString(Server.SERVER_IP, null);
		String port = settings.getString(Server.SERVER_PORT, null);

		// Bluid the string for the display
		if (port == null) {
			ip = "IP: " + ip;
		} else {
			ip = "IP: " + ip + " : " + port;
		}
		
		// Update the displays.
		TV_ip.setText(ip);
		TV_login.setText("Login: " + login);

	}

	/**
	 * Implement the ActionBar described in the menu_gen.xml file
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_gen, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.Preference:
			onClick(findViewById(R.id.preference_button));
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.preference_button:
			// Retrieve the different views to modify.
			FrameLayout FLpref = (FrameLayout) findViewById(R.id.Preference_frame);
			ImageButton IBpref = (ImageButton) findViewById(R.id.preference_button);

			// Retrieve the fragment manager in order to add or remove the
			// preference fragment.
			FragmentManager fm = getFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();

			if (isPreferenceViewExpended == false) {
				ft.add(R.id.Preference_frame, new PreferenceFragment(),
						"pref_fragment");
				ft.commit();
				FLpref.setLayoutParams(new RelativeLayout.LayoutParams(
						EXTENDED_PREFERENCE_VIEW_WIDTH,
						LayoutParams.FILL_PARENT));
				IBpref.setImageResource(R.drawable.left_arrow);
				isPreferenceViewExpended = true;
			} else {
				ft.remove(fm.findFragmentByTag("pref_fragment"));
				ft.commit();
				FLpref.setLayoutParams(new RelativeLayout.LayoutParams(
						RETRACTED_PREFERENCE_VIEW_WIDTH,
						LayoutParams.FILL_PARENT));
				IBpref.setImageResource(R.drawable.right_arrow);
				isPreferenceViewExpended = false;
			}

			updateIPAndLoginDisplay();
			break;
			
		case R.id.connection_button:
			SharedPreferences connectionpref = getSharedPreferences(
					Server.CONNECTION_INFO_SERVER_1, MODE_PRIVATE);
			String ipaddress = connectionpref.getString(Server.SERVER_IP, null);
			String login = connectionpref.getString(Server.SERVER_LOGIN, null);
			String pass = connectionpref.getString(Server.SERVER_PASS, null);
			String port = connectionpref.getString(Server.SERVER_PORT, null);
			if (ipaddress == null || login == null || pass == null || port == null) {
				int duration = Toast.LENGTH_SHORT;
				Toast toast = Toast.makeText(this, R.string.missing_connection_parameter, duration);
				toast.show();
			} else {
				Intent intent = new Intent(this, ServeurActivity.class);
				startActivity(intent);
				this.finish();
			}
		default:
			break;
		}
	}
}