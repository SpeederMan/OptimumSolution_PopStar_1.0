package com.df.popstar.builder.impl;

import java.util.Arrays;
import java.util.Random;
import java.util.function.IntFunction;

import com.df.popstar.builder.abstr.AbstractGameSpaceBuilder;
import com.df.popstar.context.GameSpaceContext;
import com.df.popstar.model.Color;

public class RandomGameSpaceBuilder implements AbstractGameSpaceBuilder {
	@Override
	public GameSpaceContext build() {
		return buildRandomGameSpace(5, 6);
	}
	
	/**
	 * �������һ����Ϊx(0<x<10),��Ϊy(0<y<10)����Ϸ�ռ�
	 * @param x
	 * @param y
	 * @return
	 */
	private GameSpaceContext buildRandomGameSpace(int x, int y) {
		
		if(x <= 0 || x > 10 || y <= 0 || y > 10) {
			return null;
		}
		Color[][] space = new Color[10][10];
		
		//��ά������ÿһ����ÿһ��Ԫ�س�ʼ��
		IntFunction<Color> initializationColor = indexY -> {
															if(indexY >= y) {
																return null;
															}
															return getRandomColor();
														};
				
				
		//��ά������ÿһ����ɫ��ʼ��
		IntFunction<Color[]> initializationColorArray = indexX -> {
															if(indexX >= x) {
																return space[indexX];
															}
															Arrays.parallelSetAll(space[indexX], initializationColor);
															return space[indexX];
														};
				
		//������ʼ����ά����
		Arrays.parallelSetAll(space, initializationColorArray);
		
		return new GameSpaceContext(space);
	}
	/**
	 * ������һ����ɫ
	 * @return
	 */
	private Color getRandomColor() {
		Random rand = new Random(System.nanoTime());
		int value = rand.nextInt(5);
		switch(value) {
			case 1:return Color.YYYY;
			case 2:return Color.PPPP;
			case 3:return Color.GGGG;
			case 4:return Color.BBBB;
			case 5:return Color.RRRR;
			default:return Color.RRRR;
		}
	}
}
