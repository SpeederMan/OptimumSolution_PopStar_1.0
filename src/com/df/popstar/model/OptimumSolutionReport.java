package com.df.popstar.model;
/**
 * 最优解报告
 */
public class OptimumSolutionReport {
	/**
	 * 总得分
	 */
	private int totalScore;
	/**
	 * 剩余星星数
	 */
	private int remainStar;
	/**
	 * 消除的步骤
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
