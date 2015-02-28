package org.td.dto;

/*
 * Generic dto for jqgrid data loading from server,it integrates spring data with jqgrid capabilities
 */

import java.util.List;

import org.springframework.data.domain.Page;

public class ViewPage<T> {

	private List<T> rows;

	private int page;
	private int max;
	private long total;

	public ViewPage() {
	}

	public ViewPage(Page<T> dbPage) {
		this.rows = dbPage.getContent();
		this.page = dbPage.getNumber() + 1;
		this.max = dbPage.getSize();
		this.total = dbPage.getTotalElements();
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}
}

