package com.df.popstar.util;
/**
 * 计分工具类
 */
public class ScoreUtil {
	/**
	 * 一次消除count个星星得分
	 * @param count
	 * @return
	 */
	public static int getScoreByStarCount(int count) {
		return 5 * count * count;
	}
	/**
	 * 获取奖励分
	 * 
	 * 剩余10个及以上，奖励分为0
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
