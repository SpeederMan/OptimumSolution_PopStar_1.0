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
	/**
	 * �����ܺ�ʱ
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
	 * ��ӡ����
	 * @param report
	 */
	public void printReport() {
		System.out.println("TotalScore :" + this.getTotalScore());
		System.out.println("RemainStar :" + this.getRemainStar());
		System.out.println("Step :" + this.getSteps());
		System.out.println("TotalTime :" + this.getTotalTime());
	}
}
