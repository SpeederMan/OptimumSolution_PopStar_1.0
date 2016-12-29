package com.df.popstar.util;

import com.df.popstar.model.Color;

public class SpaceUtil {
	
	private SpaceUtil(){};
	
	/**
	 * 拷贝一个space
	 * @param space
	 * @return
	 */
	public static Color[][] cloneSpace(Color[][] space) {
		
		Color[][] copy = new Color[10][10];
		
		Color[] tempX;
		for(int x=0; x<copy.length; x++) {
			tempX = space[x];
			if(tempX[0] == null) {
				break;
			}
			for(int y=0; y<tempX.length; y++) {
				if(tempX[y] == null) {
					break;
				}
				copy[x][y] = tempX[y];
			}
		}
		return copy;
	}
	
}
