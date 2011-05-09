package com.djavafactory.pttech.rrm.util;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.roo.addon.javabean.RooJavaBean;

/**
 * 
 * @author Mario Tinton Prambudi
 * May 8, 2011 10:32:46 PM
 *
 */
@RooJavaBean
public class PageableImpl implements Pageable {

	private int pageNumber;
	private int pageSize;
	private int offset;
	private Sort sort;
	
	public PageableImpl() {
	}
	
	public PageableImpl(int pageNumber, int pageSize, int offset, Sort sort) {
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.offset = offset;
		this.sort = sort;
	}

	@Override
	public int getPageNumber() {
		return pageNumber;
	}

	@Override
	public int getPageSize() {
		return pageSize;
	}

	@Override
	public int getOffset() {
		return offset;
	}

	@Override
	public Sort getSort() {
		return sort;
	}

}
