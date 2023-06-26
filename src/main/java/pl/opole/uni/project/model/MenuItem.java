package pl.opole.uni.project.model;

public class MenuItem {

	private String name;
	private String title;
	private String page;
	
	public MenuItem() {
		
	}
	
	public MenuItem(String name, String title, String page) {
		super();
		this.name = name;
		this.title = title;
		this.page = page;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}
	
}
