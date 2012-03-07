package home.diy;
import android.os.Handler;
import android.os.SystemClock;
import android.widget.TextView;


public class UpdateTime implements Runnable {
	
	private long time,lastupdate;
	private TextView texttime;
	private Handler handler;
	
	public UpdateTime(TextView text,long lastup,Handler h){
		lastupdate=lastup;
		texttime=text;
		handler=h;
		time=0;
	}

	
	@Override
	public void run() {
		long diff=0;
		String s="";
		time=SystemClock.uptimeMillis();
		diff=(time-lastupdate)/(1000*60);
		if (diff==0){
			s="Up to date";
		}
		else{
			s="Last updated "+String.valueOf(diff)+" minutes ago.";
		}
		texttime.setText(s);
		handler.postDelayed(this, 60000);
	}

}
