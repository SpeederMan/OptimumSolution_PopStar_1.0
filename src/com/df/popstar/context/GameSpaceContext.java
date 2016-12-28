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
 * ��Ϸ�ռ������Ļ���    ��������
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
	 * ����Ŀ������
	 * @param star
	 */
	public void popStar(PopStarContext star) {

		star.getPopPositions().stream()
		.peek(position -> {
				space[position.getX()][position.getY()] = null;
			});
		resetSpace();
	}
	
	/**
	 * �ռ�����
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
	 * ��ȡ����λ��
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
	 * ɨ�����п��������ķ�ʽ
	 * @return
	 */
	public ScanResult scanSpace() {
		ScanResult result = new ScanResult();
		if(space[0][0] == null) {//ȫ������
			return result;
		}
		
		Set<Position> haveScanPositions = new HashSet<Position>();
		
		Color[] tempX;
		
		for(int x=0; x<space.length; x++) {
			tempX = space[x];
			if(tempX[0] == null) {
				break;
			}
			for(int y=0; y<tempX.length; y++) {//��ǰλ����û��Ԫ��
				if(tempX[y] == null) {
					break;
				}
				if(haveScanPositions.contains(new Position(x, y))) {//��ǰλ��Ԫ���Ѿ���ɨ�����
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
	 * ��ʼ����
	 * @param starSpace
	 */
	public OptimumSolutionReport startAnalyse() {
		
		OptimumSolutionReport report = new OptimumSolutionReport();
		analyse(report, new TempHighLevelContext());
		return report;
	}
	/**
	 * �ݹ����
	 * @param space ��ǰ�ռ�
	 * @param report ��������
	 * @param highLevelContext �ϲ����л���
	 * @return
	 */
	private void analyse(OptimumSolutionReport report, TempHighLevelContext highLevelContext) {
		
		//�ռ�ɨ����
		ScanResult scanResult = scanSpace();
		
		//�ϲ㻷���÷�
		int currentScores = highLevelContext.getScore();
		
		//δ�������ռ䣬Ԥ���ܵõ�����߷�
		int maxScore = currentScores + scanResult.getMaxScores();
		//�ȼ�¼�е���߷ֵ�  ���ں�������
		if(maxScore < report.getTotalScore()) {
			return;
		}
		
		if(scanResult.isEmpty()) {//��ǰ�ռ��Ѳ�������
			//���㽱����
			int rewardScore = ScoreUtil.getRewardScore(scanResult.getTotalStarCount());
			//��·����������ߵ÷�
			currentScores = currentScores + rewardScore;
			if(currentScores > report.getTotalScore()) {//����֪��¼�ĵ÷ָߣ�������߼�¼
				report.setTotalScore(currentScores);
				report.setRemainStars(scanResult.getTotalStarCount());
				report.setSteps(collectPath(highLevelContext.getSteps()));
			}
			return;
		}
		
		for(PopStarContext temp : scanResult.getPopStarContext()) {
			int popScore = temp.getPopScore();
			highLevelContext.addStep(temp);
			highLevelContext.setScore(currentScores + popScore);
			
			GameSpaceContext gameSpaceClone = this.clone();
			gameSpaceClone.popStar(temp);
			gameSpaceClone.analyse(report, highLevelContext);
			
			highLevelContext.removeLastStep();
			highLevelContext.setScore(highLevelContext.getScore() - popScore);
		}
	}
	
	private String collectPath(List<PopStarContext> steps) {
		StringBuilder path = new StringBuilder();
		for(PopStarContext step : steps) {
			Position model = step.getPopStar().getPosition();
			path.append((model.getX() + 1) + "" + (model.getY() + 1) + "@");
		}
		return path.toString();
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
	 * ��ӡ��Ϸ�ռ�
	 * @param gameSpace
	 */
	public void printGameSpace() {
		System.out.println(this);
	}
	/**
	 *  ��¼��һ���м���
	 *
	 */
	class TempHighLevelContext {
		/**
		 * ����·��step�������÷�
		 */
		private int score;
		/**
		 * ʣ��������
		 */
		private int remainStar;
		/**
		 * ����·��
		 */
		private List<PopStarContext> steps = new ArrayList<PopStarContext>();
		
		public void setScore(int score) {
			this.score = score;
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
		
		public void addStep(PopStarContext step) {
			steps.add(step);
		}
		
		public void addStepBegin(PopStarContext step) {
			steps.add(0,step);
		}
		
		public List<PopStarContext> getSteps() {
			return steps;
		}
		
		public void removeLastStep() {
			if(!steps.isEmpty()) {
				steps.remove(steps.size()-1);
			}
		}
	}
}
