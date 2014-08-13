package com.depobrp.service.common;


public final class PaginationUtils {

	/**
	 * index start with 0
	 * @param page
	 * @param rowPerPage
	 * @return
	 */
	public static int getFirstIndex(int page, int rowPerPage) {
		if(page <= 0) return 0;
//		return ((page - 1) * rowPerPage);
		return ((page) * rowPerPage);
	}

	/**
	 * 
	 * @param recordCount
	 * @param rowPerPage
	 * @return
	 */
	public static int getTotalPage(int recordCount, int rowPerPage) {
		if (recordCount <= 0)
			return 0;
		return ((recordCount - 1) / rowPerPage) + 1;
	}
}
