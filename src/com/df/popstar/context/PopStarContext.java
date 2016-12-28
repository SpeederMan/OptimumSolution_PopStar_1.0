package com.df.popstar.context;

import java.util.HashSet;
import java.util.Set;

import com.df.popstar.model.Position;
import com.df.popstar.model.StarModel;
import com.df.popstar.util.ScoreUtil;

/**
 * ���������������Ļ���
 */
public class PopStarContext {
	/**
	 * Ҫ������Ŀ��star
	 */
	private StarModel popStar;
	/**
	 * ��Ŀ��starͬɫ���ڵ����д��������ǵ�λ�ã�����Ŀ��Star��
	 */
	private Set<Position> popPositions = new HashSet<Position>();
	
	private PopStarContext(StarModel popStar) {
		this.popStar = popStar;
		this.popPositions.add(popStar.getPosition());
	}
	
	/**
	 * ������ʼλ��popStar����������������
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
	 * ��ȡ�˴���������������
	 * @return
	 */
	public int getPopSize() {
		return popPositions.size();
	}
	/**
	 * �˴������ĵ÷�
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
