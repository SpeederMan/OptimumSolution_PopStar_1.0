package com.df.popstar.context;

import java.util.HashSet;
import java.util.Set;

import com.df.popstar.model.Position;
import com.df.popstar.model.StarModel;
import com.df.popstar.util.ScoreUtil;

/**
 * 消除操作的上下文环境
 */
public class PopStarContext {
	/**
	 * 要消除的目标star
	 */
	private StarModel popStar;
	/**
	 * 与目标star同色相邻的所有待消除星星的位置（包括目标Star）
	 */
	private Set<Position> popPositions = new HashSet<Position>();
	
	private PopStarContext(StarModel popStar) {
		this.popStar = popStar;
		this.popPositions.add(popStar.getPosition());
	}
	
	/**
	 * 基于起始位置popStar，构建消除上下文
	 * @param popStar
	 * @return
	 */
	public static PopStarContext buildPopStarContext(StarModel popStar) {
		if(popStar != null) {
			PopStarContext context = new PopStarContext(popStar);
			context.scanPopStar(popStar);
			if(context.getPopSize() > 1) {
				return context;
			}
		}
		return null;
	}

	public StarModel getPopStar() {
		return popStar;
	}
	/**
	 * 获取此次消除的星星总数
	 * @return
	 */
	public int getPopSize() {
		return popPositions.size();
	}
	/**
	 *此次消除的得分
	 * @return
	 */
	public int getPopScore() {
		return ScoreUtil.getScoreByStarCount(getPopSize());
	}
	
	public boolean containPosition(Position position) {
		return popPositions.contains(position);
	}
	
	public Set<Position> getPopPositions() {
		return this.popPositions;
	}
	
	private void scanPopStar(StarModel popStar) {
		dealStarContext(popStar.getNeighberStar(StarModel.UP));
		dealStarContext(popStar.getNeighberStar(StarModel.DOWN));
		dealStarContext(popStar.getNeighberStar(StarModel.LEFT));
		dealStarContext(popStar.getNeighberStar(StarModel.RIGHT));
	}
	
	private void dealStarContext(StarModel neighber) {
		if(neighber != null && this.popStar.isSameColor(neighber)) {
			Position position = neighber.getPosition();
			if(!this.containPosition(position)) {
				popPositions.add(position);
				scanPopStar(neighber);
			}
		}
	}
}
