package com.df.popstar;

import com.df.popstar.context.GameSpaceContext;
import com.df.popstar.model.OptimumSolutionReport;
import com.df.popstar.util.FormatUtil;
/**
 * 
 *  Y ^
 *    |
	 9| null null null null null null null null null null 
	 8|	null null null null null null null null null null 
	 7|	null null null null null null null null null null 
	 6|	null null null null null null null null null null 
	 5|	null null null null null null null null null null 
	 4|	null null null null null null null null null null 
	 3|	null null null null null null null null null null 
	 2|	RRRR GGGG YYYY null null null null null null null 
	 1|	BBBB BBBB YYYY null null null null null null null 
	 0|	BBBB BBBB BBBB null null null null null null null 
	- |--0----1----2----3----4----5----6----7----8----9---------> X
 *
 */
public class Client {
	
	public static void main(String[] args) {
		
		GameSpaceContext gameSpace = GameSpaceContext.build();
		
		gameSpace.printGameSpace();;
		
		long begin = System.currentTimeMillis();
		//¿ªÊ¼·ÖÎö
		OptimumSolutionReport report = gameSpace.startAnalyse();
		FormatUtil.printReport(report);
		
		System.out.println(getTotalTime(begin));
	}
	
	private static String getTotalTime(long begin) {
		long total = System.currentTimeMillis() - begin;
		long ms = total % 1000;
		total = total / 1000;
		long s = total % 60;
		total = total / 60;
		long mm = total % 60;
		total = total / 60;
		long h = total % 60;
		return h + " Hour " + mm + " Min " + s + " Second " + ms + " ms";
	}
}
