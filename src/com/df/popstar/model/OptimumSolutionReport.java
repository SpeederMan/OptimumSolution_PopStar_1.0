package com.df.popstar.model;

import java.util.ArrayList;
import java.util.List;

import com.df.popstar.context.PopStarContext;
/**
 * ���Žⱨ��
 */
public class OptimumSolutionReport {
	/**
	 * �ܵ÷�
	 */
	private int score;
	/**
	 * ʣ��������
	 */
	private int remainStar;
	/**
	 * �����Ĳ���
	 */
	private List<PopStarContext> steps = new ArrayList<PopStarContext>();
	/**
	 * �����ܺ�ʱ
	 */
	private long totalTime;
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public void setRemainStars(int remainStar) {
		this.remainStar = remainStar;
	}
	
	public void setSteps(List<PopStarContext> steps) {
		this.steps.clear();
		this.steps.addAll(steps);
	}
	
	public void setTotalTime(long totalTime) {
		this.totalTime = totalTime;
	}
	
	public int getScore() {
		return this.score;
	}
	
	public int getRemainStar() {
		return this.remainStar;
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
		System.out.println("TotalScore :" + this.getScore());
		System.out.println("RemainStar :" + this.getRemainStar());
		System.out.println("TotalTime :" + this.getTotalTime());
		
		this.steps.stream()
		.forEach(step -> {
			step.getPopStar().getSpace().printGameSpace();
			System.out.println("popLocation :" + step.getPopStar().getPosition());
			
		});
	}
}
