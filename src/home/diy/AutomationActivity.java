package home.diy;

import home.Utils.IsReachableTask;
import home.Utils.ReachableListener;
import home.diy.UI.AvailabilityFragment;
import home.diy.UI.LoadingFragment;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AutomationActivity extends Activity implements OnClickListener,
		ReachableListener {

	private Boolean isPreferenceViewExpended;
	final static int EXTENDED_PREFERENCE_VIEW_WIDTH = 300;
	final static int RETRACTED_PREFERENCE_VIEW_WIDTH = 0;
	final static String AVAILABILITY_FRAGMENT_TAG = "availability_frag_tag";

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

		// Add a click listener to both preference and connection arrow button.
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

		lookForTargetAvailability();
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
			PreferenceViewVisibilityTogle();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void PreferenceViewVisibilityTogle() {
		// Retrieve the different views to modify.
		FrameLayout FLpref = (FrameLayout) findViewById(R.id.Preference_frame);
		ImageButton IBpref = (ImageButton) findViewById(R.id.preference_button);

		// Retrieve the fragment manager in order to add or remove the
		// preference fragment.
		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		
		if (isPreferenceViewExpended == false) {
			// The preference view is closed so it opens it.
			// Add the preference layout to the view 
			ft.add(R.id.Preference_frame, new PreferenceFragment(),
					"pref_fragment");
			ft.commit();
			
			// Expends the view.
			FLpref.setLayoutParams(new RelativeLayout.LayoutParams(
					EXTENDED_PREFERENCE_VIEW_WIDTH, LayoutParams.FILL_PARENT));
			
			// Change the icon of the button to a closing arrow.
			IBpref.setImageResource(R.drawable.left_arrow);
			
			// Update the state of the preference view.
			isPreferenceViewExpended = true;
			
		} else {
			// The preference view is opened so it closes it.
			// Remove the preference layout from the view 
			ft.remove(fm.findFragmentByTag("pref_fragment"));
			ft.commit();
			
			// Set the view width to the retracted value.
			FLpref.setLayoutParams(new RelativeLayout.LayoutParams(
					RETRACTED_PREFERENCE_VIEW_WIDTH, LayoutParams.FILL_PARENT));
			
			// Set the icon of the button to an opening arrow.
			IBpref.setImageResource(R.drawable.right_arrow);
			
			// Updates the state of the view.
			isPreferenceViewExpended = false;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// Click on the preference button.
		case R.id.preference_button:
			// If the freference View was open close it and if closed open it.
			PreferenceViewVisibilityTogle();
			
			// Update the IP and login display.
			updateIPAndLoginDisplay();
			
			break;
		// Click on the connection button.
		case R.id.connection_button:
			// Retreive connection data.
			SharedPreferences connectionpref = getSharedPreferences(
					Server.CONNECTION_INFO_SERVER_1, MODE_PRIVATE);
			String ipaddress = connectionpref.getString(Server.SERVER_IP, null);
			String login = connectionpref.getString(Server.SERVER_LOGIN, null);
			String pass = connectionpref.getString(Server.SERVER_PASS, null);
			String port = connectionpref.getString(Server.SERVER_PORT, null);
			
			// If one of the parameters is missing display an error message.
			if (ipaddress == null || login == null || pass == null
					|| port == null) {
				int duration = Toast.LENGTH_SHORT;
				Toast toast = Toast.makeText(this,
						R.string.missing_connection_parameter, duration);
				toast.show();
				
			// If none of the parameters are null launch the ServerActivity. 
			} else {
				Intent intent = new Intent(this, ServeurActivity.class);
				startActivity(intent);
			}
		default:
			break;
		}
	}

	private void lookForTargetAvailability() {
		// Retrieve the fragment manager in order to add or remove fragments.
		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		
		// Replace the current view in the availability_frame with a loadingFragment.
		ft.replace(R.id.availability_frame, new LoadingFragment(
				""), AVAILABILITY_FRAGMENT_TAG);
		ft.commit();

		// Open the shared preferences containing the target information.
		SharedPreferences settings = getSharedPreferences(
				Server.CONNECTION_INFO_SERVER_1, 0);

		// Retrieve the values of the IP and port of the target.
		String ip = settings.getString(Server.SERVER_IP, null);
		String port = settings.getString(Server.SERVER_PORT, null);

		// Bluid the parameters necessary for the execution of the task.
		String[] task_parameters = new String[2];
		task_parameters[0] = ip;
		task_parameters[1] = port;
		
		// Creating the task used to check if the target port is open.
		IsReachableTask is_reachable_task = (IsReachableTask) new IsReachableTask();
		
		// Set the activity as a listener of the task.
		is_reachable_task.addListener(this);
		
		// Launch the task with the parameters.
		is_reachable_task.execute(task_parameters);
	}

	@Override
	public void targetIsReachable() {
		//If the target is reachable, display the write message.
		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		ft.replace(R.id.availability_frame, new AvailabilityFragment(true));
		ft.commit();
	}

	@Override
	public void targetIsNotReachable() {
		//If the target is not reachable, display the write message.
		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		ft.replace(R.id.availability_frame, new AvailabilityFragment(false));
		ft.commit();
	}
}