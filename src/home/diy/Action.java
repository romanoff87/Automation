package home.diy;

public class Action {

	private String name;
	private String description;

	public Action() {
		name = "";
		description = "";
	}

	public Action(String title, String description) {
		name = title;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
