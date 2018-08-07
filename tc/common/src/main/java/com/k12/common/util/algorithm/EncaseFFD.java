/**
 * 项目名: oss-common
 * 文件名：EncaseFFD.java 
 * 版本信息： V1.0
 * 日期：2016年4月5日 
 * Copyright: Corporation 2016 版权所有
 *
 */
package com.k12.common.util.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSON;

/** 
 * 项目名称：oss-common <br>
 * 类名称：EncaseFFD <br>
 * 类描述：一维装箱算法，FFD实现<br>
 * Copyright: Copyright (c) 2016 by 江苏宏坤供应链管理有限公司<br>
 * Company: 江苏宏坤供应链管理有限公司<br>
 * 创建人：arlen <br>
 * 创建时间：2016年4月5日 下午1:23:49 <br>
 * 修改人：arlen<br>
 * 修改时间：2016年4月5日 下午1:23:49 <br>
 * 修改备注：<br>
 * @version 1.0
 * @author arlen
 */
public class EncaseFFD<T extends OccupySpace> {
	
	/**
	 * 每个箱子的容量
	 */
	private int boxCaptation;
	
	/**
	 * 创建一个新的实例 EncaseFFD. 
	 *
	 * @param boxCaptation
	 */
	public EncaseFFD(int boxCaptation) {
		this.boxCaptation = boxCaptation;
	}

	/**
	 * 装箱，FFD算法
	 * encase(这里用一句话描述这个方法的作用) 
	 * @param prdList
	 * @return 箱子列表 {@link Box}
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 * @author arlen
	 */
	public List<Box<T>> encase(List<T> prdList) {
    	if (null == prdList || prdList.size() <= 0) {
    		return new ArrayList<Box<T>>();
    	}
        return encaseWithExistBox(prdList, new ArrayList<Box<T>>());
    }

	/**
	 * 装箱，FFD算法，跟已有的未满的箱子一起装箱
	 * encase(这里用一句话描述这个方法的作用) 
	 * @param prdList
	 * @return 箱子列表 {@link Box}
	 * @since CodingExample　Ver(编码范例查看) 1.1
	 * @author arlen
	 */
	public List<Box<T>> encaseWithExistBox(List<T> prdList, List<Box<T>> unfullBoxList) {
    	if (null == prdList || prdList.size() <= 0) {
    		return unfullBoxList;
    	}
    	// 1. 排序，不同于NF算法的地方，去掉NF算法求最优解时做减法求最小空间
        Collections.sort(prdList, new Comparator<T>() {
			@Override
			public int compare(T o1, T o2) {
				if (o1 == null || o2 == null) {
					return 0;
				}
				return o2.getOccupySpace() - o1.getOccupySpace();
			}
		});
        
        // 2. 装箱，如果已用过的箱子可容纳新产品，则装入，否则，分配新箱子，装入产品
        List<Box<T>> boxList = unfullBoxList;
        for (T prd : prdList) {
        	boolean putted = false;
			for (Box<T> box : boxList) {
				if ((putted = box.put(prd))) {
					break;
				}
			}
			if (!putted) {
				Box<T> newBox = new Box<T>(this.boxCaptation);
				newBox.put(prd);
				boxList.add(newBox);
			}
		}
        return boxList;
    }
	
	/**
	 * 项目名称：oss-common <br>
	 * 类名称：Box <br>
	 * 类描述：用来装货物的箱子，包含箱子已用容量，箱子剩余容量，箱子中商品列表<br>
	 * Copyright: Copyright (c) 2016 by 江苏宏坤供应链管理有限公司<br>
	 * Company: 江苏宏坤供应链管理有限公司<br>
	 * 创建人：arlen <br>
	 * 创建时间：2016年4月5日 下午4:53:58 <br>
	 * 修改人：arlen<br>
	 * 修改时间：2016年4月5日 下午4:53:58 <br>
	 * 修改备注：<br>
	 * @version 1.0
	 * @author arlen
	 */
	public static class Box<T extends OccupySpace> {

    	private int capacity;
    	
    	private final List<T> prdList = new ArrayList<T>();
    	
    	/**
    	 * 容量因子，用来实际标识容量的Map；Map中实际因子的个数标识箱子已用容量<br>
    	 * eg. Map&lt;税则号, [货号1, 货号2, 货号3]&gt;
    	 */
		private Map<Integer, List<String>> capacityFactorMap = new HashMap<Integer, List<String>>();
    	
		public Box(int capacity) {
			this.capacity = capacity;
		}
		
    	public int getUsedCapacity() {
    		return capacityFactorMap.size();
    	}
    	
    	public boolean put(T prd) {
    		if (prdList.size() > 0) {
    			// 如果其他限制条件不相同，无法装箱
    			String thisOtherCondition = prdList.get(0).getOtherCondition();
    			if (thisOtherCondition != null && !thisOtherCondition.equals(prd.getOtherCondition())) {
    				return false;
    			}
    		}
    		
    		Map<Integer, List<String>> tempFactorMap = reCalcBoxCapcityFactor(prd);
    		if (tempFactorMap.size() > capacity) {
    			return false;
    		}
    		
    		capacityFactorMap = tempFactorMap;
    		prdList.add(prd);
    		return true;
    	}

		/**
		 * 重新合并容量因子
		 * reCalculateBoxCapcity(这里用一句话描述这个方法的作用) 
		 * @param prd
		 * @return 
		 */
		private Map<Integer, List<String>> reCalcBoxCapcityFactor(T prd) {
			Map<Integer, List<String>> otherFactorMap = prd.getCapacityFactorMap();
			if (otherFactorMap == null || otherFactorMap.size() == 0) {
				return capacityFactorMap;
			}
			Map<Integer, List<String>> tempFactorMap = new HashMap<Integer, List<String>>(capacityFactorMap);
			Iterator<Entry<Integer, List<String>>> it = otherFactorMap.entrySet().iterator();
			while (it.hasNext()) {
				Entry<Integer, List<String>> entry = it.next();
				if (tempFactorMap.containsKey(entry.getKey())) {
					tempFactorMap.get(entry.getKey()).addAll(entry.getValue());
				} else {
					tempFactorMap.put(entry.getKey(), entry.getValue());
				}
			}
			return tempFactorMap;
		}

		public List<T> getPrdList() {
			return prdList;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			for (T t : prdList) {
				sb.append("; 商品名(订单号)：" + t.getPrdName() + "，商品因子数(订单内品名)：" + t.getOccupySpace());
			}
			return "Box [\n\t已用容量(因子)=" + getUsedCapacity() + ", 总容量="+capacity+", 已装商品(订单)总数="+prdList.size()+", \n\t容量因子(品名)=" + JSON.toJSONString(capacityFactorMap) 
					+ ", \n\t已装商品(订单)=[" + (sb.length() == 0? "": sb.substring(2)) + "]\n]";
		}
	}
	
	public static class Tester implements OccupySpace{

		private int space;
		private String name;
		private Map<Integer, List<String>> factorMap;
		
		public Tester(int space, String name, Map<Integer, List<String>> factorMap) {
			this.space = space;
			this.name = name;
			this.factorMap = factorMap;
		}

		@Override
		public int getOccupySpace() {
			return space;
		}

		@Override
		public String getPrdName() {
			return name;
		}

		@Override
		public String getOtherCondition() {
			return null;
		}

		@Override
		public Map<Integer, List<String>> getCapacityFactorMap() {
			return factorMap;
		}
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) {
    	List<Tester> testerList = new ArrayList<Tester>();
    	Map<Integer, List<String>> factorMap = new HashMap<Integer, List<String>>();
    	factorMap.put(0, new ArrayList(Arrays.asList(new String[]{"prd1", "prd3", "prd4", "prd6"})));
    	factorMap.put(1, new ArrayList(Arrays.asList(new String[]{"prd2", "prd5"})));
    	factorMap.put(3, new ArrayList(Arrays.asList(new String[]{"prd7", "prd9"})));
    	// 3个品名，8个货号
    	testerList.add(new Tester(3, "足球", factorMap));
    	
    	factorMap = new HashMap<Integer, List<String>>();
    	factorMap.put(0, new ArrayList(Arrays.asList(new String[]{"prd3", "prd4"})));
    	factorMap.put(3, new ArrayList(Arrays.asList(new String[]{"prd7", "prd9"})));
    	factorMap.put(4, new ArrayList(Arrays.asList(new String[]{"prd11", "prd10"})));
    	// 3个品名，6个货号
    	testerList.add(new Tester(3, "篮球", factorMap));
    	
    	factorMap = new HashMap<Integer, List<String>>();
    	factorMap.put(5, new ArrayList(Arrays.asList(new String[]{"prd12", "prd15"})));
    	factorMap.put(3, new ArrayList(Arrays.asList(new String[]{"prd7"})));
    	testerList.add(new Tester(2, "羽毛球", factorMap));
    	
    	factorMap = new HashMap<Integer, List<String>>();
    	factorMap.put(6, new ArrayList(Arrays.asList(new String[]{"prd61", "prd63", "prd64"})));
    	factorMap.put(7, new ArrayList(Arrays.asList(new String[]{"prd72", "prd75"})));
    	factorMap.put(9, new ArrayList(Arrays.asList(new String[]{"prd97", "prd99"})));
    	testerList.add(new Tester(3, "网球", factorMap));
    	
    	factorMap = new HashMap<Integer, List<String>>();
    	factorMap.put(0, new ArrayList(Arrays.asList(new String[]{"prd1", "prd3", "prd4", "prd6"})));
    	factorMap.put(1, new ArrayList(Arrays.asList(new String[]{"prd2", "prd5"})));
    	factorMap.put(3, new ArrayList(Arrays.asList(new String[]{"prd7", "prd9"})));
    	factorMap.put(4, new ArrayList(Arrays.asList(new String[]{"prd11", "prd10"})));
    	factorMap.put(6, new ArrayList(Arrays.asList(new String[]{"prd61", "prd63", "prd64"})));
    	testerList.add(new Tester(5, "排球", factorMap));
    	
    	EncaseFFD<Tester> encaseFfd = new EncaseFFD<Tester>(5);
    	List<Box<Tester>> boxList = encaseFfd.encase(testerList);

        // 打印装箱明细
        for (Box<Tester> box : boxList) {
			System.out.println(box);
		}
	}
	
}
