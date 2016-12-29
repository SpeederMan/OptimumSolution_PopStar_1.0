package com.df.popstar.context;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.df.popstar.builder.abstr.AbstractGameSpaceBuilder;
import com.df.popstar.builder.impl.RandomGameSpaceBuilder;
import com.df.popstar.model.Color;
import com.df.popstar.model.OptimumSolutionReport;
import com.df.popstar.model.Position;
import com.df.popstar.model.ScanResult;
import com.df.popstar.model.StarModel;
import com.df.popstar.util.ScoreUtil;
import com.df.popstar.util.SpaceUtil;
/**
 * 游戏空间上下文环境    坐标如下
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
public class GameSpaceContext implements Cloneable {
	
	private Color[][] space;
	
	public GameSpaceContext(Color[][] space) {
		this.space = space;
	}
	
	public boolean isExist(int x, int y) {
		return space[x][y] != null;
	}
	
	public boolean isExist(Position position) {
		return isExist(position.getX(), position.getY());
	}
	
	public Color getColor(Position position) {
		return space[position.getX()][position.getY()];
	}
	
	public static GameSpaceContext build() {
		return build(new RandomGameSpaceBuilder());
	}
	
	public static GameSpaceContext build(AbstractGameSpaceBuilder builder) {
		return builder.build();
	}
	/**
	 * 消除目标星星
	 * @param star
	 */
	public void popStar(PopStarContext star) {

		star.getPopPositions().stream()
		.forEach(position -> {
				space[position.getX()][position.getY()] = null;
			});
		resetSpace();
	}
	
	/**
	 * 空间重排
	 * @param space
	 */
	private void resetSpace() {
		
		int[] copyIndex = new int[]{0,0};
		for(int x=0; x<space.length; x++) {
			copyIndex[1] = 0;
			for(int y=0; y<space.length; y++) {
				if(copyIndex[0] >= space.length) {
					space[x][y] = null;
				} else {
					copyIndex = getCopyPosition(space, copyIndex[0], copyIndex[1]);
					if(copyIndex[1] >= space.length) {
						space[x][y] = null;
					} else {
						if(copyIndex[0] != x || copyIndex[1] != y) {
							space[x][y] = space[copyIndex[0]][copyIndex[1]];
						}
						copyIndex[1]++;
					}
				}
			}
			copyIndex[0]++;
		}
	}
	/**
	 * 获取拷贝位置
	 * @param space
	 * @param beginX
	 * @param beginY
	 * @return
	 */
	private int[] getCopyPosition(Color[][] space, int beginX, int beginY) {
		
		if(beginX < space.length) {
			Color[] spaceX = space[beginX];
			if(beginY < spaceX.length) {
				boolean fromBegin = (beginY == 0);
				for(;beginY<spaceX.length; beginY++) {
					if(spaceX[beginY] != null) {
						return new int[]{beginX, beginY};
					}
				}
				if(fromBegin) {
					for(beginX++;beginX<space.length; beginX++) {
						spaceX = space[beginX];
						for(int y=0; y<spaceX.length; y++) {
							if(spaceX[y] != null) {
								return new int[]{beginX, y};
							}
						}
						
					}
					return new int[]{space.length, space.length};
				}
			}
			return new int[]{beginX, spaceX.length}; 
		}
		return new int[]{space.length, space.length};
	}
	
	/**
	 * 扫描所有可以消除的方式
	 * @return
	 */
	public ScanResult scanSpace() {
		ScanResult result = new ScanResult();
		if(space[0][0] == null) {//全部消除
			return result;
		}
		
		Set<Position> haveScanPositions = new HashSet<Position>();
		
		Color[] tempX;
		
		for(int x=0; x<space.length; x++) {
			tempX = space[x];
			if(tempX[0] == null) {
				break;
			}
			for(int y=0; y<tempX.length; y++) {//当前位置已没有元素
				if(tempX[y] == null) {
					break;
				}
				if(haveScanPositions.contains(new Position(x, y))) {//当前位置元素已经被扫描过的
					continue;
				}
				PopStarContext scanResult = PopStarContext.buildPopStarContext(StarModel.buildStarContext(this, x, y));
				if(scanResult == null) {
					result.recordColor(tempX[y]);
					continue;
				}
				result.addPopStarContext(scanResult);
				haveScanPositions.addAll(scanResult.getPopPositions());
			}
		}
		return result;
	}
	
	/**
	 * 开始分析
	 * @param starSpace
	 */
	public OptimumSolutionReport startAnalyse() {
		
		OptimumSolutionReport report = new OptimumSolutionReport();
		
		long begin = System.currentTimeMillis();
		analyse(report, new TempHighLevelContext());
		report.setTotalTime(System.currentTimeMillis() - begin);
		
		return report;
	}
	/**
	 * 预测结果比已知最优解差
	 * @param report
	 * @param highLevelContext
	 * @return
	 */
	private boolean isBadThanOptimumSolution(OptimumSolutionReport report, TempHighLevelContext highLevelContext, ScanResult scanResult) {
		//上层环境既得得分
		int currentScores = highLevelContext.getScore();
		//预测能得到的最高分
		int maxScore = currentScores + scanResult.getMaxScores();
		//比已知最优解中的得分低  不在后续计算
		return report.getScore() >= maxScore;
	}
	/**
	 * 一轮完整的消除结束，如果优于已知最优解，则更新最优报告
	 * @param report
	 * @param highLevelContext
	 * @param scanResult
	 */
	private void updateOptimumSolutionReportIfNecessary(OptimumSolutionReport report, TempHighLevelContext highLevelContext, ScanResult scanResult) {
		//计算奖励分
		int rewardScore = ScoreUtil.getRewardScore(scanResult.getTotalStarCount());
		//此路径的最终得分
		int scores = highLevelContext.getScore() + rewardScore;
		if(scores > report.getScore()) {//比已知记录的得分高，更新最高记录
			report.setScore(scores);
			report.setRemainStars(scanResult.getTotalStarCount());
			report.setSteps(highLevelContext.getSteps());
		}
	}
	
	/**
	 * 递归分析
	 * @param space 当前空间
	 * @param report 分析报告
	 * @param highLevelContext 上层运行环境
	 * @return
	 */
	private void analyse(OptimumSolutionReport report, TempHighLevelContext highLevelContext) {
		
		//空间扫描结果
		ScanResult scanResult = scanSpace();
		
		//当前空间已没有可以消除的星星
		if(scanResult.isEmpty()) {
			updateOptimumSolutionReportIfNecessary(report, highLevelContext, scanResult);
			return;
		}
		//预测最优消除得分低于已知最优解则不在后续消除
		if(isBadThanOptimumSolution(report, highLevelContext, scanResult)) {
			return;
		}
		
		scanResult.getPopStarContext().stream()
		.forEach(popStarContext -> {
			
			highLevelContext.pushStep(popStarContext);
			
			GameSpaceContext gameSpaceClone = this.clone();
			gameSpaceClone.popStar(popStarContext);
			gameSpaceClone.analyse(report, highLevelContext);
			
			highLevelContext.popStep();
		});
	}
	
	@Override
	public GameSpaceContext clone() {
		try {
			return new GameSpaceContext(SpaceUtil.cloneSpace(this.space));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	@Override
	public String toString() {
		
		StringBuffer result = new StringBuffer("=================================================\n");

		for(int y=space.length-1; y>=0; y--) {
			for(int x=0; x<10; x++) {
				result.append(space[x][y]).append(" ");
			}
			result.append("\n");
		}
		return result.toString();
	}
	/**
	 * 打印游戏空间
	 * @param gameSpace
	 */
	public void printGameSpace() {
		System.out.println(this);
	}
	/**
	 *  记录上一层中间结果
	 *
	 */
	class TempHighLevelContext {
		/**
		 * 按照路径step消除所得分
		 */
		private int score;
		/**
		 * 剩余星星数
		 */
		private int remainStar;
		/**
		 * 消除路径
		 */
		private List<PopStarContext> steps = new ArrayList<PopStarContext>();
		
		public void pushStep(PopStarContext step) {
			steps.add(step);
			this.score += step.getPopScore();
		}
		
		public int getScore() {
			return this.score;
		}
		
		public void setRemainStar(int remainStar) {
			this.remainStar = remainStar;
		}
		
		public int getRemainStar() {
			return this.remainStar;
		}
		
		public List<PopStarContext> getSteps() {
			return steps;
		}
		
		public void popStep() {
			if(!steps.isEmpty()) {
				PopStarContext step = steps.remove(steps.size()-1);
				this.score -= step.getPopScore();
			}
		}
	}
}
