package com.df.popstar.model;

import java.util.ArrayList;
import java.util.List;

import com.df.popstar.context.PopStarContext;
import com.df.popstar.util.ScoreUtil;

/**
 * 游戏空间扫描结果
 */
public class ScanResult {
	/**
	 * 红色个数
	 */
	private int redSize;
	/**
	 * 黄色个数
	 */
	private int yellowSize;
	/**
	 * 绿色个数
	 */
	private int greenSize;
	/**
	 * 蓝色个数
	 */
	private int buleSize;
	/**
	 * 粉色个数
	 */
	private int pinkSize;
	/**
	 * 空间中可以消除的所有位置
	 */
	private List<PopStarContext> popStarContexts = new ArrayList<PopStarContext>();
	/**
	 * 空间中所有的星星个数
	 * @return
	 */
	public int getTotalStarCount() {
		return redSize + yellowSize + greenSize + buleSize + pinkSize;
	}
	/**
	 * 所有可消除的位置
	 * @return
	 */
	public List<PopStarContext> getPopStarContext() {
		return popStarContexts;
	}
	/**
	 * 是否有可以消除的星星
	 * @return
	 */
	public boolean isEmpty() {
		return popStarContexts.isEmpty();
	}
	/**
	 * 记录到可消除的星星中
	 * @param context
	 */
	public void addPopStarContext(PopStarContext context) {
		recordColor(context.getPopStar().getColor(), context.getPopSize());
		popStarContexts.add(context);
	}
	
	public void recordColor(Color color) {
		recordColor(color, 1);
	}
	
	private void recordColor(Color color, int size) {
		if(Color.BBBB == color) {
			buleSize += size;
		} else if(Color.RRRR == color) {
			redSize += size;
		} else if(Color.GGGG == color) {
			greenSize += size;
		} else if(Color.PPPP == color) {
			pinkSize += size;
		} else if(Color.YYYY == color) {
			yellowSize += size;
		}
	}
	/**
	 * 最乐观估计当前空间能获得的最高分数
	 * 
	 * 认为所有同色星星都能消除
	 * 
	 * @return
	 */
	public int getMaxScores() {
		
		int scores = 0;
		
		int remainSize = 0;
		
		if(yellowSize > 1) {
			scores += ScoreUtil.getScoreByStarCount(yellowSize);
		} else if(yellowSize == 1) {//剩余一个不能消除
			remainSize++;
		}
		if(buleSize > 1) {
			scores += ScoreUtil.getScoreByStarCount(buleSize);
		} else if(buleSize == 1) {//剩余一个不能消除
			remainSize++;
		}
		if(pinkSize > 1) {
			scores += ScoreUtil.getScoreByStarCount(pinkSize);
		} else if(pinkSize == 1) {//剩余一个不能消除
			remainSize++;
		}
		if(greenSize > 1) {
			scores += ScoreUtil.getScoreByStarCount(greenSize);
		} else if(greenSize == 1) {//剩余一个不能消除
			remainSize++;
		}
		if(redSize > 1) {
			scores += ScoreUtil.getScoreByStarCount(redSize);
		} else if(redSize == 1) {//剩余一个不能消除
			remainSize++;
		}
		//消除得分+奖励分
		return scores + ScoreUtil.getRewardScore(remainSize);
	}
}
