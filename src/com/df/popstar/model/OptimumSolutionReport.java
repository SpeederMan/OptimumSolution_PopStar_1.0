package com.df.popstar.model;
/**
 * ���Žⱨ��
 */
public class OptimumSolutionReport {
	/**
	 * �ܵ÷�
	 */
	private int totalScore;
	/**
	 * ʣ��������
	 */
	private int remainStar;
	/**
	 * �����Ĳ���
	 */
	private String steps = "";
	
	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}
	
	public void setRemainStars(int remainStar) {
		this.remainStar = remainStar;
	}
	
	public void setSteps(String steps) {
		this.steps = steps;
	}
	
	public int getTotalScore() {
		return this.totalScore;
	}
	
	public int getRemainStar() {
		return this.remainStar;
	}
	
	public String getSteps() {
		return this.steps;
	}
}
