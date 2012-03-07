package home.diy;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ActionListAdapteur extends BaseAdapter{
	
	private ArrayList<Action> actionsList;
	private LayoutInflater mInflater;
	
	public ActionListAdapteur(Context context, ArrayList<Action> actions){
		actionsList=actions;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return actionsList.size();
	}

	@Override
	public Object getItem(int position) {
		  return actionsList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		  if (convertView == null) {
		   convertView = mInflater.inflate(R.layout.list_item, null);
		   holder = new ViewHolder();
		   holder.txtActionName = (TextView) convertView.findViewById(R.id.action_name);
		   holder.txtActiondescription = (TextView) convertView.findViewById(R.id.action_description);

		   convertView.setTag(holder);
		  } else {
		   holder = (ViewHolder) convertView.getTag();
		  }
		  
		  holder.txtActionName.setText(actionsList.get(position).getName());
		  holder.txtActiondescription.setText(actionsList.get(position).getDescription());

		  return convertView;
	}
	
	static class ViewHolder {
        TextView txtActionName;
        TextView txtActiondescription;
    }


}


