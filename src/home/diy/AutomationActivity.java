package home.diy;

import home.diy.UI.Mydialog;
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
import android.view.View.OnLongClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class AutomationActivity extends Activity implements OnLongClickListener,OnClickListener{

	private Boolean isPreferenceViewExpended;
	final static int EXTENDED_PREFERENCE_VIEW_WIDTH=300;
	final static int RETRACTED_PREFERENCE_VIEW_WIDTH=0;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		isPreferenceViewExpended = false;
	}
	
	public boolean onLongClick(View v){
		switch (v.getId()) {
		case R.id.entry1:
			showParametersDialog();
			break;
		default:
			break;
		}
		
		return true;
	}

	@Override
	public void onResume() {
		super.onResume();
		TextView alpha2=(TextView) findViewById(R.id.entry1);
		alpha2.setOnLongClickListener(this);
		Typeface tf = Typeface.createFromAsset(getAssets(),
                "fonts/Roboto-Thin.ttf");
        TextView tv = (TextView) findViewById(R.id.entry1);
        tv.setTypeface(tf);
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
		FragmentManager fragmentManager;
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.Preference:
			FrameLayout lo = (FrameLayout) findViewById(R.id.Preference_frame);
			LinearLayout.LayoutParams params = (LayoutParams) lo
					.getLayoutParams();
			params.width = 400;
			lo.setLayoutParams(params);
			fragmentManager = getFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager
					.beginTransaction();
			fragmentTransaction.add(R.id.Preference_frame,
					new PreferenceFragment());
			fragmentTransaction.commit();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * function called when text is clicked see the menu.xml Title textview and
	 * the onClick and Clickable variables
	 * 
	 * @param view
	 */

	public void ServerButtonClick(View view) {
		SharedPreferences connectionpref = getSharedPreferences(Server.CONNECTION_INFO_SERVER_1,
				MODE_PRIVATE);
		String ipaddress = connectionpref.getString(Server.SERVER_IP, null);
		String login=connectionpref.getString(Server.SERVER_LOGIN, null);
		String pass=connectionpref.getString(Server.SERVER_PASS, null);
		String port = connectionpref.getString(Server.SERVER_PORT, null);
		if (ipaddress == null||login==null||pass==null||port==null) {
			showParametersDialog();
		}
		else{
			Intent intent = new Intent(this, ServeurActivity.class);
			startActivity(intent); 
			this.finish();
		}
	}
	
	public void showParametersDialog(){
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		Mydialog newdia = new Mydialog();
		newdia.show(ft, "dialog");
	}
	
	public void onPreferenceArrowClick(View v){
		// Retrieve the different views to modify.
		FrameLayout FLpref = (FrameLayout) findViewById(R.id.Preference_frame);
		ImageButton IBpref=(ImageButton)findViewById(R.id.preference_button);
		
		// Retrieve the fragment manager in order to add or remove the preference fragment.
		FragmentManager fm= getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		
		if (isPreferenceViewExpended == false)
		{
			ft.add(R.id.Preference_frame,new PreferenceFragment(),"pref_fragment");
			ft.commit();
			FLpref.setLayoutParams(new RelativeLayout.LayoutParams(EXTENDED_PREFERENCE_VIEW_WIDTH, LayoutParams.FILL_PARENT));
			IBpref.setImageResource(R.drawable.left_arrow);
			isPreferenceViewExpended = true;
		}
		else
		{
			ft.remove(fm.findFragmentByTag("pref_fragment"));
			ft.commit();
			FLpref.setLayoutParams(new RelativeLayout.LayoutParams(RETRACTED_PREFERENCE_VIEW_WIDTH, LayoutParams.FILL_PARENT));
			IBpref.setImageResource(R.drawable.right_arrow);
			isPreferenceViewExpended = false;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.valid_param_button:
			onPreferenceArrowClick(v);
		}
	}
}