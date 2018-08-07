package com.k12.common.plugin;

import java.io.Serializable;
import java.util.List;

/**
 * //分页封装函数
 * 
 * @param <T>
 */
public class PageView implements Serializable{
	
	private static final long serialVersionUID = -5514748934288980193L;

	/**
	 * 是否分页查询
	 */
	private boolean pageflag=false;
	
	/**
	 * 排序规则
	 */
	private String orderby;
	
	/**
	 * 分页数据
	 */
	private List<?> records;

	/**
	 * 总页数 这个数是计算出来的
	 * 
	 */
	private long pageCount;

	/**
	 * 每页显示几条记录
	 */
	private int pageSize = 10;

	/**
	 * 默认 当前页 为第一页 这个数是计算出来的
	 */
	private int pageNow = 1;
	/**
	 * 总记录数
	 */
	private long rowCount;

	/**
	 * 从第几条记录开始
	 */
	private int startPage;

	/**
	 * 规定显示5个页码
	 */
	private int pagecode = 10;
	
	/**
	 * 查询分页时的错误消息，返回前台<br>
	 * added by renyarong, 2016.02.29
	 */
	private String errorMsg;

	public PageView() {
	}

	/**
	 * 要获得记录的开始索引　即　开始页码
	 * 
	 * @return
	 */
	public int getFirstResult() {
		return (this.pageNow - 1) * this.pageSize;
	}

	public int getPagecode() {
		return pagecode;
	}

	public void setPagecode(int pagecode) {
		this.pagecode = pagecode;
	}

	/**
	 * 使用构造函数，，强制必需输入 每页显示数量　和　当前页
	 * 
	 * @param pageSize
	 *            　　每页显示数量
	 * @param pageNow
	 *            　当前页
	 */
	public PageView(int pageSize, int pageNow) {
		this.pageSize = pageSize;
		this.pageNow = pageNow;
	}

	/**
	 * 使用构造函数，，强制必需输入 当前页
	 * 
	 * @param pageNow
	 *            　当前页
	 */
	public PageView(int pageNow) {
		this.pageNow = pageNow;
	}

	/**
	 * 查询结果方法 把　记录数　结果集合　放入到　PageView对象
	 * 
	 * @param rowCount
	 *            总记录数
	 * @param records
	 *            结果集合
	 */

	public void setQueryResult(long rowCount, List<?> records) {
		setRowCount(rowCount);
		setRecords(records);
	}

	public void setRowCount(long rowCount) {
		this.rowCount = rowCount;
	}

	public List<?> getRecords() {
		return records;
	}

	public void setRecords(List<?> records) {
		this.records = records;
	}

	public int getPageNow() {
		return pageNow;
	}

	public void setPageNow(int pageNow) {
		this.pageNow = pageNow;
	}

	public long getPageCount() {
		return getRowCount() % getPageSize() == 0 ? getRowCount() / getPageSize() : getRowCount() / getPageSize() + 1;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getRowCount() {
		return rowCount;
	}

	public int getStartPage() {
		return (getPageNow() - 1) * getPageSize();
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public void setPageCount(long pageCount) {
		this.pageCount = pageCount;
	}

	public String getOrderby() {
		return orderby;
	}

	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}
	
	public boolean isPageflag() {
		return pageflag;
	}

	public void setPageflag(boolean pageflag) {
		this.pageflag = pageflag;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	@Override
	public String toString() {
		return "PageView [ pageCount=" + pageCount + ", pageSize=" + pageSize + ", pageNow=" + pageNow 
			+ ", rowCount=" + rowCount + ", startPage=" + startPage + ", pagecode=" + pagecode + ",orderby=" + orderby + "]";
	}

}
