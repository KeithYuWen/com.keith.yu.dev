package com.k12.common.util.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class GetDateUtil {
    
    public static void main(String[] args) throws ParseException {
        /*plusDay2(14);
        plusDay4("2015-09-06 00:00:00");*/
    	String dateStr = "2008-1-1 1:21:28";
        String dateStr2 = "2010-1-2 1:21:28";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try 
        {
            Date date2 = format.parse(dateStr2);
            Date date = format.parse(dateStr);
            
            System.out.println("两个日期的差距：" + differentDays(date,date2));
            System.out.println("两个日期的差距：" + differentDaysByMillisecond(date,date2));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("String两个日期的差距：" + daysBetween(dateStr,dateStr2));
        
        
    }


/**
     * 指定日期加上天数后的日期
     * @param num 为增加的天数
     * @param newDate 创建时间
     * @return
     * @throws ParseException 
     */
    public static String plusDay(int num,String newDate) throws ParseException{
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date  currdate = format.parse(newDate);
        System.out.println("现在的日期是：" + currdate);
        Calendar ca = Calendar.getInstance();
        ca.setTime(currdate); 
        ca.add(Calendar.DATE, num);// num为增加的天数，可以改变的
        currdate = ca.getTime();
        String enddate = format.format(currdate);
        System.out.println("增加天数以后的日期：" + enddate);
        return enddate;
    }
    
    /**
     * 指定日期加上天数后的日期  yyyy-MM-dd
     * @param num 为增加的天数
     * @param newDate 创建时间
     * @return
     * @throws ParseException 
     */
    public static String plusDate(int num,String newDate) throws ParseException{
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date  currdate = format.parse(newDate);
        System.out.println("现在的日期是：" + currdate);
        Calendar ca = Calendar.getInstance();
        ca.setTime(currdate); 
        ca.add(Calendar.DATE, num);// num为增加的天数，可以改变的
        currdate = ca.getTime();
        String enddate = format.format(currdate);
        System.out.println("增加天数以后的日期：" + enddate);
        return enddate;
    }

    
    /**
     * 指定日期加上天数后的日期  yyyy-MM-dd
     * @param num 为增加的月份
     * @param newDate 创建时间
     * @return
     * @throws ParseException 
     */
    public static String plusMonthDate(int num,String newDate) throws ParseException{
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date  currdate = format.parse(newDate); 
        Calendar ca = Calendar.getInstance();
        ca.setTime(currdate); 
        ca.add(Calendar.MONTH, num);// num为增加的天数，可以改变的
        currdate = ca.getTime();
        String enddate = format.format(currdate); 
        return enddate;
    }

/**
     * 当前日期加上天数后的日期
     * @param num 为增加的天数
     * @return
     */
    public static String plusDay2(int num){
        Date d = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currdate = format.format(d);
        System.out.println("现在的日期是：" + currdate);

        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.DATE, num);// num为增加的天数，可以改变的
        d = ca.getTime();
        String enddate = format.format(d);
        System.out.println("增加天数以后的日期：" + enddate);
        return enddate;
    }

    
    /**
     * 当前日期加上天数后的日期
     * @param num 为增加的天数
     * @return
     */
    public static String plusDate(int num){
        Date d = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String currdate = format.format(d);
        System.out.println("现在的日期是：" + currdate);

        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.DATE, num);// num为增加的天数，可以改变的
        d = ca.getTime();
        String enddate = format.format(d);
        System.out.println("增加天数以后的日期：" + enddate);
        return enddate;
    }

    /**
     * 当前日期往后一年的  
     * @return
     */
    public static String plusDay3(){
        Date d = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currdate = format.format(d);
        System.out.println("现在的日期是：" + currdate);

        Calendar ca = Calendar.getInstance();
        ca.add(Calendar.YEAR, 1); 
        ca.add(Calendar.DATE, -1); 
        d = ca.getTime();
        String enddate = format.format(d);
        System.out.println("增加天数以后的日期：" + enddate);
        return enddate;
    }
    
    
    /**
     * 指定日期往后一年的  
     * @return
     */
    public static String plusDay4(String newDate) throws ParseException{ 
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = format.parse(newDate);
        System.out.println("指定的日期是：" + d);

        Calendar ca = Calendar.getInstance();
        ca.setTime(d);
        ca.add(Calendar.YEAR, 1); 
        ca.add(Calendar.DATE, -1); 
        d = ca.getTime();
        String enddate = format.format(d);
        System.out.println("增加天数以后的日期：" + enddate);
        return enddate; 
    }
    
    /**
     * 获取当前时间
     * @return
     * @throws ParseException
     */
    public static String currentDate() throws ParseException{
    	Date d = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String currdate = format.format(d);
        System.out.println("现在的日期是：" + currdate); 
        return currdate;
    }
    /**
     * 获取当前时间
     * @return
     * @throws ParseException
     */
    public static String currentTime() throws ParseException{
    	Date d = new Date();
    	 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currdate = format.format(d);
        System.out.println("现在的日期是：" + currdate); 
        return currdate;
    }
    
    /**
     * 获取当前时间  时分秒
     * @return
     * @throws ParseException
     */
    public static String currentTimeSfm() throws ParseException{
    	Date d = new Date();
    	 SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        String currdate = format.format(d); 
        return currdate;
    }
    
    /**
     * 获取当前时间
     * @return
     * @throws ParseException
     */
    public static String getNoCurrentTime() throws ParseException{
        Date d = new Date();
         SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String currdate = format.format(d);
        System.out.println("现在的日期是：" + currdate); 
        return currdate;
    }
    /**
     * 求两个时间的月份差
     * @throws ParseException 
     */
    public static int monthsBetween(String DateOne,String DateTwo) throws ParseException{
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        /* String str1 = "2017-02";
         String str2 = "2017-01";*/
         Calendar bef = Calendar.getInstance();
         Calendar aft = Calendar.getInstance();
         bef.setTime(sdf.parse(DateOne));
         aft.setTime(sdf.parse(DateTwo));
         int result = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH);
         int month = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR)) * 12; 
         return (Math.abs(month + result));
    }
    
    /**
     * 求两个时间的月份差
     * @throws ParseException 
     */
    public static int monthsDateBetween(Date DateOne,Date DateTwo) throws ParseException{
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        /* String str1 = "2017-02";
         String str2 = "2017-01";*/
         Calendar bef = Calendar.getInstance();
         Calendar aft = Calendar.getInstance();
         bef.setTime(DateOne);
         aft.setTime(DateTwo);
         int result = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH);
         int month = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR)) * 12; 
         return (Math.abs(month + result));
    }
    /**
     * date2比date1多的天数
     * @param date1    
     * @param date2
     * @return    
     */
    public static int differentDays(Date date1,Date date2)
    {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
       int day1= cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);
        
        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if(year1 != year2)   //同一年
        {
            int timeDistance = 0 ;
            for(int i = year1 ; i < year2 ; i ++)
            {
                if(i%4==0 && i%100!=0 || i%400==0)    //闰年            
                {
                    timeDistance += 366;
                }
                else    //不是闰年
                {
                    timeDistance += 365;
                }
            }
            
            return timeDistance + (day2-day1) ;
        }
        else    //不同年
        {
            System.out.println("判断day2 - day1 : " + (day2-day1));
            return day2-day1;
        }
    }
    /**
     * 通过时间秒毫秒数判断两个时间的间隔
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDaysByMillisecond(Date date1,Date date2)
    {
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
        return days;
    }
    
    /** 
    *字符串的日期格式的计算 
    */  
    public static int daysBetween(String smdate,String bdate) throws ParseException{  
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(sdf.parse(smdate));    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(sdf.parse(bdate));    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
            
       return Integer.parseInt(String.valueOf(between_days));     
    }   
    
    /**
     * 把Date时间转成String格式
     * @param date1
     * @return
     */
    public static String dateToString(Date date1){
    	 SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");  
    	 String StringDate = format.format(date1);
         System.out.println("现在的日期是：" + StringDate);
         return StringDate;
    }
    /**
     * 把String时间转成Date格式
     * @param dateString
     * @return
     */
    public static Date stringToDate(String dateString){ 
    	 Date date = null;
    	try  
    	{  
    	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
    	    date = sdf.parse(dateString);  
    	}  
    	catch (ParseException e)  
    	{  
    	    System.out.println(e.getMessage());  
    	} 
    	return date;
    }
    
    /**
     * 判断几分钟之内
     * @param updateDate
     * @return
     * @throws ParseException 
     */
    public static boolean getMinute(String updateDate,int minute) throws ParseException {  
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = format.parse(updateDate);
        System.out.println("指定的日期是：" + d);
        
        Calendar c1=Calendar.getInstance();  
        Calendar c2=Calendar.getInstance();  
        Calendar c3=Calendar.getInstance();   
        c1.setTime(d);//要判断的日期  
        c2.setTime(new Date());//初始日期  
        c3.setTime(new Date());//也给初始日期 把分钟加五   
        c3.add(Calendar.MINUTE, minute);  
        c2.add(Calendar.MINUTE,-minute);//减去五分钟   
        /*System.out.println("c1"+c1.getTime());  
        System.out.println("c2"+c2.getTime());  
        System.out.println("c3"+c3.getTime());  */ 
        if(c1.after(c2) && c1.before(c3)){  
            //System.out.println("五分钟之内");  
        	 return true;
        }else {  
           // System.out.println("五分钟之外");  
        	 return false;
        }  
    }
    
    /**
     * 当前时间加上账单数量作为账单号
     * @param dateString
     * @return
     */
    public static String getDateAndNum(String dateString){
    	Date date = new Date();
    	SimpleDateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");  
   	 	String StringDate = format.format(date) + dateString;
    	return StringDate;
    }
    
    /**
     *
     * //TODO 添加方法功能描述
     * @param date
     * @param format
     * @return
     */
    public static Date returnDate(String date,String format){
        SimpleDateFormat sdf=new SimpleDateFormat(format);//小写的mm表示的是分钟   
        Date returndate = null;
        try {
            returndate = sdf.parse(date);
        } catch (ParseException e) { 
            e.printStackTrace();
        }
        return returndate;
    }
    
    /**
     * 把Date时间转成String格式
     * @param date1
     * @return
     */
    public static String dateToString(Date date1,String format1){
    	 SimpleDateFormat format=new SimpleDateFormat(format1);  
    	 String StringDate = format.format(date1);
         return StringDate;
    }
    
    /**
    * 获取月份起始日期
    * @param date
    * @return
    * @throws ParseException
    */
    public static String getMinMonthDate(String date){
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	Calendar calendar = Calendar.getInstance();
    	try {
			calendar.setTime(dateFormat.parse(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
    	return dateFormat.format(calendar.getTime());
    }

    /**
    * 获取月份最后日期
    * @param date
    * @return
    * @throws ParseException
    */
    public static String getMaxMonthDate(String date){
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	Calendar calendar = Calendar.getInstance();
    	try {
			calendar.setTime(dateFormat.parse(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
    	return dateFormat.format(calendar.getTime());
    }
}