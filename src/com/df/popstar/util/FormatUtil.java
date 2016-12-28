package com.df.popstar.util;

import com.df.popstar.model.OptimumSolutionReport;
/**
 * ��ʽ��������
 */
public class FormatUtil {
	
	private FormatUtil(){};
	
	/**
	 * ��ӡ��������
	 * @param report
	 */
	public static void printReport(OptimumSolutionReport report) {
		System.out.println("TotalScore :" + report.getTotalScore());
		System.out.println("RemainStar :" + report.getRemainStar());
		System.out.println("Step :" + report.getSteps());
	}
}
