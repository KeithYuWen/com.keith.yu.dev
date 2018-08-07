package com.k12.common.util.excel;

import java.util.List;

/**
 * Excel的工作簿
 * 
 * @author renyarong
 * 
 * @param <T> 想往表格中存放的数据实体类
 */
public class BookSheet<T> {

	/**
	 * Excel文件中的sheet名 注：就左下角那个角标，不同于文件名
	 */
	private String sheetName = "sheetName";

	/**
	 * Excel文件标题，也即要给用户看的概要信息，比如查询相关条件等
	 */
	private String title = "Title";

	/**
	 * 工作簿里的正文数据
	 */
	private List<T> dataList;
	
	/**
	 * 正文数据的实体类
	 */
	private Class<?> clazz;

	public BookSheet() {
		super();
	}

	/**
	 * 创建一个工作簿
	 * @param sheetName 工作簿名字
	 * @param title 工作簿标题
	 */
	public BookSheet(String sheetName, String title) {
		super();
		this.sheetName = sheetName;
		this.title = title;
	}

	/**
	 * 创建一个工作簿
	 * @param sheetName 工作簿名字
	 * @param title 工作簿标题
	 * @param dataList 所要存放的数据
	 * @param clazz 存放数据的实体类
	 */
	public BookSheet(String sheetName, String title, List<T> dataList,
			Class<?> clazz) {
		super();
		this.sheetName = sheetName;
		this.title = title;
		this.dataList = dataList;
		this.clazz = clazz;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		if (sheetName == null) 
			this.sheetName = sheetName;
		else 
			this.sheetName = sheetName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		if (title == null)
			this.title = "Title";
		else 
			this.title = title;
	}

	public List<T> getDataList() {
		return dataList;
	}

	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}
}
