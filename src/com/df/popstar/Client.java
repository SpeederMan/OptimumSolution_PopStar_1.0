package com.df.popstar;

import com.df.popstar.context.GameSpaceContext;
import com.df.popstar.model.OptimumSolutionReport;
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
		//���������Ϸ�ռ�
		GameSpaceContext gameSpace = GameSpaceContext.build();
		//��ӡ�ռ�
		gameSpace.printGameSpace();;
		//�������Ž�
		OptimumSolutionReport report = gameSpace.startAnalyse();
		//��ӡ��������
		report.printReport();
	}
}
