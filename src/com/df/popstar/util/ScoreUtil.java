package com.df.popstar.util;
/**
 * �Ʒֹ�����
 */
public class ScoreUtil {
	/**
	 * һ������count�����ǵ÷�
	 * @param count
	 * @return
	 */
	public static int getScoreByStarCount(int count) {
		return 5 * count * count;
	}
	/**
	 * ��ȡ������
	 * 
	 * ʣ��10�������ϣ�������Ϊ0
	 * 
	 * @param remainCount
	 * @return
	 */
	public static int getRewardScore(int remainCount) {
		if(remainCount >= 10) {
			return 0;
		}
		return 2000 - remainCount * remainCount * 20;
	}
}
