package home.diy;

import home.diy.UI.Menu_vertical;
import home.diy.UI.RetreiveActionList;
import home.diy.UI.System_Info;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.jcraft.jsch.Session;

public class ServeurActivity extends Activity implements Ecouteur {

	private ProgressDialog dialog;
	private FragmentManager fragmentManager;
	private FragmentTransaction fragmentTransaction;
	private Session session;
	private String dialogmessage, Newligne;
	private SshConnection connect;
	private System_Info Sys_info_frag;

	public ServeurActivity() {
		Newligne = System.getProperty("line.separator");
		dialogmessage = "Loading...";
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainserver);
	}

	@Override
	public void onResume() {
		super.onResume();
		SharedPreferences connectinfo = getSharedPreferences(
				Server.CONNECTION_INFO_SERVER_1, MODE_PRIVATE);
		String ip = connectinfo.getString(Server.SERVER_IP, null);
		String login = connectinfo.getString(Server.SERVER_LOGIN, null);
		String pass = connectinfo.getString(Server.SERVER_PASS, null);
		String port = connectinfo.getString(Server.SERVER_PORT, null);
		connect = (SshConnection) new SshConnection();
		connect.addobserver(this);
		connect.execute(ip, port, login, pass);
		dialog = ProgressDialog.show(ServeurActivity.this, "",
				"Loading. Please wait...", true);
	}

	@Override
	public void onStop() {
		connect.cancel(true);
	}

	@Override
	public void publishedResult(Session result) {
		session = result;
		if (session != null) {
			fragmentManager = getFragmentManager();
			fragmentTransaction = fragmentManager.beginTransaction();
			fragmentTransaction.add(R.id.System_Info, new RetreivingSysInfo(
					session));
			fragmentTransaction.add(R.id.frame_contenu, new Menu_vertical());
			fragmentTransaction.add(R.id.frame_menu, new RetreiveActionList(
					session));
			fragmentTransaction.commit();
		} else {
			Intent intent = new Intent(this, AutomationActivity.class);
			startActivity(intent);
			finish();
		}
	}

	@Override
	public void updateProgress(String[] progress) {
		dialogmessage += Newligne + progress[0];
		dialog.setMessage(dialogmessage);
		if (progress[0].equals("Connected") || progress[0].equals("Error")) {
			dialog.dismiss();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_gen, (android.view.Menu) menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.Home:
			Intent intent = new Intent(this, AutomationActivity.class);
			startActivity(intent);
			return true;
		case R.id.Reload:
			fragmentManager = getFragmentManager();
			Sys_info_frag = (System_Info) fragmentManager
					.findFragmentByTag("sys_info_frag");
			Sys_info_frag.reloadSystemInfo();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void clickonsysinfo(View view) {
	}

	public void connectClick(View view) {
	}

	@Override
	public void publishListResult(Object result) {
		// TODO Auto-generated method stub
		
	}

}
