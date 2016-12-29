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
	 * 随机构建一个宽为x(0<x<10),高为y(0<y<10)的游戏空间
	 * @param x
	 * @param y
	 * @return
	 */
	private GameSpaceContext buildRandomGameSpace(int x, int y) {
		
		if(x <= 0 || x > 10 || y <= 0 || y > 10) {
			return null;
		}
		Color[][] space = new Color[10][10];
		
														//二维数组中每一列中每一个元素初始化
		IntFunction<Color> initializationColor = indexY -> {
															if(indexY >= y) {
																return null;
															}
															return getRandomColor();
														};
				
				
														//二维数组中每一行颜色初始化
		IntFunction<Color[]> initializationColorArray = indexX -> {
															if(indexX >= x) {
																return space[indexX];
															}
															Arrays.parallelSetAll(space[indexX], initializationColor);
															return space[indexX];
														};
				
		//并发初始化二维数组
		Arrays.parallelSetAll(space, initializationColorArray);
		
		return new GameSpaceContext(space);
	}
	/**
	 * 随机获得一个颜色
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
