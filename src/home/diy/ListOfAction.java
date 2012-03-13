package home.diy;

import java.util.ArrayList;

import android.app.ListFragment;
import android.os.Bundle;

public class ListOfAction extends ListFragment {
	
	private ArrayList<Action> action_list;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		action_list = new ArrayList<Action>();
		
		action_list.add(new Action("Action1","Description action1"));
		action_list.add(new Action("Action2","Description action2"));
		action_list.add(new Action("Action3","Description action3"));
		action_list.add(new Action("Action3","Description action3"));
		action_list.add(new Action("Action3","Description action3"));
		action_list.add(new Action("Action3","Description action3"));
		action_list.add(new Action("Action3","Description action3"));
		action_list.add(new Action("Action3","Description action3"));
		
		setListAdapter(new ActionListAdapteur(this.getActivity(),action_list));

		
	}
}