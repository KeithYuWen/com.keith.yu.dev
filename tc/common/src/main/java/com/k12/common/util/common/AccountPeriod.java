package com.k12.common.util.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
 * 计算账期Tools
 *
 */
public class AccountPeriod {

	private final static SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd");
	
	// 获取当前日期所在周的上下界  
    public final static int CURRENT_WEEK = 0;  
    // 获取当前日期上一周的上下界  
    public final static int LAST_WEEK = 1;  
    // 获取当前日期所在月的上下界  
    public final static int CURRENT_MONTH = 2;  
    // 获取当前日期上一月的上下界  
    public final static int LAST_MONTH = 3;  
    // 获取当前日期所在月的上下界  
    public final static int CURRENT_YEAR = 4;  
    // 获取当前日期上一月的上下界  
    public final static int LAST_YEAR = 5;  
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		//下单日期
	    Date orderDate = new SimpleDateFormat("yyyy-MM-dd").parse("2017-08-08");
		//周期天数 1周4月5季6半年7年
		int cycle = 1;
		//周期开始日期
		Date cycleTime = sdf.parse("2017-01-07");
		//对账天数
		int reconciliationDay = 3;
		//还款天数
		int repaymentDay = 3;
		
		//调用核算方法
		Map<String, Object> map = getAccountPeriodDate( cycle,  cycleTime,  reconciliationDay,  repaymentDay,  orderDate);
		System.out.println(map);
		
	}
	
	/**
	 * 
	 * @param cycle  周期天数 1周4月5季6半年7年
	 * @param CycleTime 周期开始日期
	 * @param reconciliationDay 对账天数
	 * @param repaymentDay 还款天数
	 * @param orderDate 下单日期
	 * @return
	 * 		cycleStartDate	周期开始日期
	 * 		orderDate	下单日期
	 * 		thisCycleLastDate	下单日期所在周期的最后一天
	 * 		cycleDimension	周期维度
	 * 		settleDays	周期结算天数  *账期天数
	 * 		paymentDays	周期结算天数+对账天数+还款天数/付款日期   *本次账期时间
	 *  	
	 */
	public static Map<String, Object> getAccountPeriodDate(int cycle, Date cycleTime, int reconciliationDay, int repaymentDay, Date orderDate){
		Map<String, Object> map = new HashMap<String, Object>();  
		
		//周期末最后一天日期
		Date endDate = null;
		//周期维度
		String CycleDimension = "";
		//1周
		if(cycle == 1){
			endDate = getConvertWeekByDate(orderDate);  
			CycleDimension = "周";
			System.out.println("获取日期的当前周的周末日期"+sdf.format(getConvertWeekByDate(orderDate))); 
		}
		//4月
		if(cycle == 4){
			endDate = getConvertMonthByDate(orderDate);  
			CycleDimension = "月";
			System.out.println("获取日期的当前月的月末日期"+sdf.format(getConvertMonthByDate(orderDate)));  
		}
		//5季
		if(cycle == 5){
			endDate = getCurrentQuarterEndTime(orderDate);
			CycleDimension = "季度";
			System.out.println("获取日期的当前季的季末日期"+sdf.format(getCurrentQuarterEndTime(orderDate)));
		}
		//6半年
		if(cycle == 6){
			endDate = getHalfYearEndTime(orderDate);
			CycleDimension = "半年";
			System.out.println("获取日期的当前半年的半年末日期"+sdf.format(getHalfYearEndTime(orderDate)));
		}
		//7年
		if(cycle == 7){
			endDate = getConvertYearByDate(orderDate);
			CycleDimension = "年";
			System.out.println("获取日期的当前年的年末日期"+sdf.format(getConvertYearByDate(orderDate)));
		}
		
		//开始核算
		
		//周期开始日期
		map.put("cycleStartDate", sdf.format(cycleTime));	
		//下单日期
		map.put("orderDate", sdf.format(orderDate));
		//下单日期所在周期的最后一天
		map.put("thisCycleLastDate", sdf.format(endDate));
		//周期维度
		map.put("cycleDimension", CycleDimension);
		//周期结算天数
		map.put("settleDays", daysOfTwo(orderDate ,endDate)+1);
		//周期结算天数+对账天数+还款天数/付款日
		Calendar   calendar   =   new   GregorianCalendar(); 
		calendar.setTime(endDate);
		calendar.add(calendar.DATE,reconciliationDay);
		calendar.add(calendar.DATE,repaymentDay);
		Date tomorrow = calendar.getTime();  
		map.put("paymentDays", tomorrow);
		return map;
	}
	
	/** 
     * 根据日期计算所在周的上下界 
     *  
     * @param time 
     */  
    public static Date getConvertWeekByDate(Date time) {  
        Map<String, Object> map = new HashMap<String, Object>();  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 设置时间格式  
        Calendar cal = Calendar.getInstance();  
        cal.setTime(time);  
        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了  
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天  
        if (1 == dayWeek) {  
            cal.add(Calendar.DAY_OF_MONTH, -1);  
        }  
        cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一  
        int day = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天  
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值  
        String imptimeBegin = sdf.format(cal.getTime());  
        // System.out.println("所在周星期一的日期：" + imptimeBegin);  
        cal.add(Calendar.DATE, 6);  
        String imptimeEnd = sdf.format(cal.getTime());  
        // System.out.println("所在周星期日的日期：" + imptimeEnd);  
        map.put("last", imptimeEnd); 
        return cal.getTime();  
    }  
  
    /** 
     * 根据日期计算当月的第一天与最后一天 
     *  
     * @param time 
     * @return 
     */  
    public static Date getConvertMonthByDate(Date date) {  
        Map<String, Object> map = new HashMap<String, Object>();  
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");  
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);  
        calendar.add(Calendar.MONTH, -1);  
        Date theDate = calendar.getTime();  
        // 上个月第一天  
        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();  
        gcLast.setTime(theDate);  
        gcLast.set(Calendar.DAY_OF_MONTH, 1);  
        String day_first = df.format(gcLast.getTime());  
        // 上个月最后一天  
        calendar.add(Calendar.MONTH, 2); // 加一个月  
        calendar.set(Calendar.DATE, 1); // 设置为该月第一天  
        calendar.add(Calendar.DATE, -1); // 再减一天即为上个月最后一天  
        String day_last = df.format(calendar.getTime());  
        map.put("last", day_last);  
        return calendar.getTime();  
  
    }  
    
    public static Date getCurrentQuarterEndTime(Date date) {
    	Map<String, Object> map = new HashMap<String, Object>();  
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int currentMonth = c.get(Calendar.MONTH) + 1;
        Date now = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 3) {
                c.set(Calendar.MONTH, 2);
                c.set(Calendar.DATE, 31);
            } else if (currentMonth >= 4 && currentMonth <= 6) {
                c.set(Calendar.MONTH, 5);
                c.set(Calendar.DATE, 30);
            } else if (currentMonth >= 7 && currentMonth <= 9) {
                c.set(Calendar.MONTH, 8);
                c.set(Calendar.DATE, 30);
            } else if (currentMonth >= 10 && currentMonth <= 12) {
                c.set(Calendar.MONTH, 11);
                c.set(Calendar.DATE, 31);
            }
            now = sdf.parse(sdf.format(c.getTime()) + " 23:59:59");
        } catch (Exception e) {
            e.printStackTrace();
        }
        String day_last = sdf.format(now);  
        map.put("last", day_last); 
        return now;
    }
    
    public static Date getHalfYearEndTime(Date date){
    	Map<String, Object> map = new HashMap<String, Object>(); 
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int currentMonth = c.get(Calendar.MONTH) + 1;
        Date now = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 6){
                c.set(Calendar.MONTH, 5);
                c.set(Calendar.DATE, 30);
            }else if (currentMonth >= 7 && currentMonth <= 12){
                c.set(Calendar.MONTH, 11);
                c.set(Calendar.DATE, 31);
            }
            now = sdf.parse(sdf.format(c.getTime()) + " 23:59:59");
        } catch (Exception e) {
            e.printStackTrace();
        }
        String day_last = sdf.format(now);  
        map.put("last", day_last); 
        return now;
    }
    
    /** 
     * 根据日期计算当年的第一天与最后一天 
     *  
     * @param time 
     * @return 
     */  
    public static Date getConvertYearByDate(Date date) {  
        Map<String, Object> map = new HashMap<String, Object>();  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
        Calendar calendar = Calendar.getInstance();  
        int year = date.getYear() + 1900;  
        calendar.clear();  
        calendar.set(Calendar.YEAR, year);  
        Date currYearFirst = calendar.getTime();  
        calendar.set(Calendar.YEAR, year + 1);  
        calendar.add(calendar.DATE, -1);  
        Date lastYearFirst = calendar.getTime();  
        map.put("last", sdf.format(lastYearFirst));  
        return calendar.getTime();  
  
    }  

    public static int daysOfTwo(Date orderDate , Date endDate){
        //跨年的情况会出现问题哦
        //如果时间为：2016-03-18 11:59:59 和 2016-03-19 00:00:01的话差值为 1
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(orderDate);
        int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
        aCalendar.setTime(endDate);
        int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
        int days=day2-day1;
        return days;
    }
}
