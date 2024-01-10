package com.JavaWebApplication.Beans;

public class RawData {

	private int VanbanId;
	private String slug;
	private String heading;
	private String html;
	private String title;
	public String getSlug() {
		return slug;
	}
	public void setSlug(String slug) {
		this.slug = slug;
	}
	public String getHeading() {
		return heading;
	}
	public void setHeading(String heading) {
		this.heading = heading;
	}
	public String getHtml() {
		return html;
	}
	public void setHtml(String html) {
		this.html = html;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	private String id;
	public String getTitle() {
		return title;
	}
	public void setTitle(String tittle) {
		this.title = tittle;
	}
	public int getVanbanId() {
		return VanbanId;
	}
	public void setVanbanId(int vanbanId) {
		VanbanId = vanbanId;
	}
	
}
