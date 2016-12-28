package com.df.popstar.model;

import com.df.popstar.context.GameSpaceContext;
/**
 * Star的空间位置环境
 */
public class StarModel {
	
	public static int UP    = 1;
	
	public static int DOWN  = 2;
	
	public static int LEFT  = 3;
	
	public static int RIGHT = 4;
	//所处的位置
	private Position position;
	//所处的空间环境
	private GameSpaceContext gameSpaceContext;
	
	private StarModel(GameSpaceContext gameSpaceContext, Position position) {
		this.gameSpaceContext = gameSpaceContext;
		this.position = position;
	}
	/**
	 * 指定空间位置建模
	 * 
	 * 如果所指定位置没有元素则返回null
	 * @param space
	 * @param x
	 * @param y
	 * @return
	 */
	public static StarModel buildStarContext(GameSpaceContext gameSpaceContext, int x, int y) {
		if(gameSpaceContext.isExist(x, y)) {
			return new StarModel(gameSpaceContext, new Position(x, y));
		}
		return null;
	}
	/**
	 * 获取位置
	 * @return
	 */
	public Position getPosition() {
		return this.position;
	}
	/**
	 * 获取颜色
	 * @return
	 */
	public Color getColor() {
		return gameSpaceContext.getColor(position);
	}
	/**
	 * 获取游戏space
	 * @return
	 */
	public GameSpaceContext getSpace() {
		return gameSpaceContext;
	}
	
	/**
	 * 是否可以被消除
	 * 
	 * 如果当前star的上下左右位置没有同色star，则不能被消除
	 * @return
	 */
	public boolean isCanDelete() {
		StarModel up = getNeighberStar(UP);
		if(up != null) {
			if(isSameColor(up)) {
				return true;
			}
		}
		StarModel down = getNeighberStar(DOWN);
		if(down != null) {
			if(isSameColor(down)) {
				return true;
			}
		}
		StarModel left = getNeighberStar(LEFT);
		if(left != null) {
			if(isSameColor(left)) {
				return true;
			}
		}
		StarModel right = getNeighberStar(RIGHT);
		if(right != null) {
			if(isSameColor(right)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isSameColor(StarModel other) {
		return getColor() == other.getColor();
	}
	
	/**
	 * 获取相邻位置的  位置建模
	 * 
	 * 如果相邻位置超出空间边界或未超出边界但该位置没有元素，则返回null
	 * @param direction
	 * @return
	 */
	public StarModel getNeighberStar(int direction) {
		
		if(UP == direction) {
			if(position.getY() >= 9) {
				return null;
			}
			return StarModel.buildStarContext(gameSpaceContext, position.getX(), position.getY()+1);
		} else if(DOWN == direction) {
			if(position.getY() <= 0) {
				return null;
			}
			return StarModel.buildStarContext(gameSpaceContext, position.getX(), position.getY()-1);
		} else if(LEFT == direction) {
			if(position.getX() <= 0) {
				return null;
			}
			return StarModel.buildStarContext(gameSpaceContext, position.getX()-1, position.getY());
		} else {
			if(position.getX() >= 9) {
				return null;
			}
			return StarModel.buildStarContext(gameSpaceContext, position.getX()+1, position.getY());
		}
	}
}
