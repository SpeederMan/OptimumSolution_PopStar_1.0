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
	/**
	 * 分析总耗时
	 */
	private long totalTime;
	
	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}
	
	public void setRemainStars(int remainStar) {
		this.remainStar = remainStar;
	}
	
	public void setSteps(String steps) {
		this.steps = steps;
	}
	
	public void setTotalTime(long totalTime) {
		this.totalTime = totalTime;
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
	
	public String getTotalTime() {
		long ms = totalTime % 1000;
		totalTime = totalTime / 1000;
		long s = totalTime % 60;
		totalTime = totalTime / 60;
		long mm = totalTime % 60;
		totalTime = totalTime / 60;
		long h = totalTime % 60;
		return h + " Hour " + mm + " Min " + s + " Second " + ms + " ms";
	}
	/**
	 * 打印报告
	 * @param report
	 */
	public void printReport() {
		System.out.println("TotalScore :" + this.getTotalScore());
		System.out.println("RemainStar :" + this.getRemainStar());
		System.out.println("Step :" + this.getSteps());
		System.out.println("TotalTime :" + this.getTotalTime());
	}
}
