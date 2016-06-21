package winker.core.interceptor.bean;

/**
 * 
 * Created by tom on 15/10/09 基础类 前台table插件基础参数 其他查询条件可集成该类
 */
public class PageParameter {
	private int totalElements;
	private int currentPage;
	private int totalPage;
	private int pageSize;
	private int limit;
	private int offset;

	public int getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(int totalElements) {
		this.totalElements = totalElements;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
