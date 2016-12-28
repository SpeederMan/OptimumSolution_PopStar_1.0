package com.df.popstar.util;

import com.df.popstar.model.OptimumSolutionReport;
/**
 * 格式化工具类
 */
public class FormatUtil {
	
	private FormatUtil(){};
	
	/**
	 * 打印消除报告
	 * @param report
	 */
	public static void printReport(OptimumSolutionReport report) {
		System.out.println("TotalScore :" + report.getTotalScore());
		System.out.println("RemainStar :" + report.getRemainStar());
		System.out.println("Step :" + report.getSteps());
	}
}
