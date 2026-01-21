package com.todolist.security.dto;

import java.util.List;

public class TodoListResponse {
	private List<TodoResponse> data;
	private int page;
	private int limit;
	private long total;

	public TodoListResponse() {}

	public TodoListResponse(List<TodoResponse> data, int page, int limit, long total) {
		this.data = data;
		this.page = page;
		this.limit = limit;
		this.total = total;
	}

	public List<TodoResponse> getData() {
		return data;
	}

	public void setData(List<TodoResponse> data) {
		this.data = data;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}
}

