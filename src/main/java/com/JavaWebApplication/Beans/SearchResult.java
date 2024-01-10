package com.JavaWebApplication.Beans;

public class SearchResult {
	private String VanbanId;
    private String heading;
    private String html;
    private int pageNumber;
    private int pageSize;
    
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
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getVanbanId() {
		return VanbanId;
	}
	public void setVanbanId(String vanbanId) {
		VanbanId = vanbanId;
	}
    
}
