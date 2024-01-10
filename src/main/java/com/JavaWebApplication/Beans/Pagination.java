package com.JavaWebApplication.Beans;

import java.util.List;

public class Pagination {
	public List<RawData> getList() {
		return list;
	}
	public void setList(List<RawData> list) {
		this.list = list;
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
	List<RawData> list;
	int pageNumber;
	int pageSize;
}
