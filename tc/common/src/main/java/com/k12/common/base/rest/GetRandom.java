package com.k12.common.base.rest;

import java.util.Random;


public class GetRandom
{
    /**
     * 生成指定位数的0-9a-z的随机字符串
     * @param passLenth 位数
     * @return
     */
	public static String getPass(int passLenth)
	{
		StringBuffer buffer = new StringBuffer(
				"0123456789abcdefghijklmnopqrstuvwxyz");
		StringBuffer stringBuffer = new StringBuffer();
		Random random = new Random();
		int range = buffer.length();
		for (int i = 0; i < passLenth; i++)
		{
			// 生成指定范围类的随机数0—字符串长度(包括0、不包括字符串长度)
			stringBuffer.append(buffer.charAt(random.nextInt(range)));
		}
		return stringBuffer.toString();
	}
	
    /**
     * 生成指定位数、最大值(不包含最大值),最小值为0的字符串
     * @param maxNumber
     * @param length
     * @return
     */
    public static String getRandomNum(int maxNumber, int length)
    {
        StringBuffer stringBuffer = new StringBuffer();
        int i=0;
        Random random = new Random();
        while (i<length)
        {
            int num = random.nextInt(maxNumber);
            stringBuffer.append(num);
            i++;
        }
        return stringBuffer.toString();
    }
    /**
     * randomNumSix生成6位随机数 
     * @return 
     * @since xuhongjie
     */
    public static int randomNumSix(){
    	Random random = new Random();
    	int x = random.nextInt(899999);
		x = x + 100000;
		return x;
    }
}
