package com.df.popstar.model;

import com.df.popstar.context.GameSpaceContext;
/**
 * Star�Ŀռ�λ�û���
 */
public class StarModel {
	
	public static int UP    = 1;
	
	public static int DOWN  = 2;
	
	public static int LEFT  = 3;
	
	public static int RIGHT = 4;
	//������λ��
	private Position position;
	//�����Ŀռ价��
	private GameSpaceContext gameSpaceContext;
	
	private StarModel(GameSpaceContext gameSpaceContext, Position position) {
		this.gameSpaceContext = gameSpaceContext;
		this.position = position;
	}
	/**
	 * ָ���ռ�λ�ý�ģ
	 * 
	 * �����ָ��λ��û��Ԫ���򷵻�null
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
	 * ��ȡλ��
	 * @return
	 */
	public Position getPosition() {
		return this.position;
	}
	/**
	 * ��ȡ��ɫ
	 * @return
	 */
	public Color getColor() {
		return gameSpaceContext.getColor(position);
	}
	/**
	 * ��ȡ��Ϸspace
	 * @return
	 */
	public GameSpaceContext getSpace() {
		return gameSpaceContext;
	}
	
	/**
	 * �Ƿ���Ա�����
	 * 
	 * �����ǰstar����������λ��û��ͬɫstar�����ܱ�����
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
	 * ��ȡ����λ�õ�  λ�ý�ģ
	 * 
	 * �������λ�ó����ռ�߽��δ�����߽絫��λ��û��Ԫ�أ��򷵻�null
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
