package com.df.popstar.model;

import java.util.ArrayList;
import java.util.List;

import com.df.popstar.context.PopStarContext;
import com.df.popstar.util.ScoreUtil;

/**
 * ��Ϸ�ռ�ɨ����
 */
public class ScanResult {
	/**
	 * ��ɫ����
	 */
	private int redSize;
	/**
	 * ��ɫ����
	 */
	private int yellowSize;
	/**
	 * ��ɫ����
	 */
	private int greenSize;
	/**
	 * ��ɫ����
	 */
	private int buleSize;
	/**
	 * ��ɫ����
	 */
	private int pinkSize;
	/**
	 * �ռ��п�������������λ��
	 */
	private List<PopStarContext> popStarContexts = new ArrayList<PopStarContext>();
	/**
	 * �ռ������е����Ǹ���
	 * @return
	 */
	public int getTotalStarCount() {
		return redSize + yellowSize + greenSize + buleSize + pinkSize;
	}
	/**
	 * ���п�������λ��
	 * @return
	 */
	public List<PopStarContext> getPopStarContext() {
		return popStarContexts;
	}
	/**
	 * �Ƿ��п�������������
	 * @return
	 */
	public boolean isEmpty() {
		return popStarContexts.isEmpty();
	}
	/**
	 * ��¼����������������
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
	 * ���ֹ۹��Ƶ�ǰ�ռ��ܻ�õ���߷���
	 * 
	 * ��Ϊ����ͬɫ���Ƕ�������
	 * 
	 * @return
	 */
	public int getMaxScores() {
		
		int scores = 0;
		
		int remainSize = 0;
		
		if(yellowSize > 1) {
			scores += ScoreUtil.getScoreByStarCount(yellowSize);
		} else if(yellowSize == 1) {//ʣ��һ����������
			remainSize++;
		}
		if(buleSize > 1) {
			scores += ScoreUtil.getScoreByStarCount(buleSize);
		} else if(buleSize == 1) {//ʣ��һ����������
			remainSize++;
		}
		if(pinkSize > 1) {
			scores += ScoreUtil.getScoreByStarCount(pinkSize);
		} else if(pinkSize == 1) {//ʣ��һ����������
			remainSize++;
		}
		if(greenSize > 1) {
			scores += ScoreUtil.getScoreByStarCount(greenSize);
		} else if(greenSize == 1) {//ʣ��һ����������
			remainSize++;
		}
		if(redSize > 1) {
			scores += ScoreUtil.getScoreByStarCount(redSize);
		} else if(redSize == 1) {//ʣ��һ����������
			remainSize++;
		}
		//�����÷�+������
		return scores + ScoreUtil.getRewardScore(remainSize);
	}
}
