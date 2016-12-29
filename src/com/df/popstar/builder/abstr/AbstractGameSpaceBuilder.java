package com.df.popstar.builder.abstr;

import com.df.popstar.context.GameSpaceContext;
/**
 * 游戏空间构建器
 */
public interface AbstractGameSpaceBuilder {
	/**
	 * 构建游戏空间
	 * @return
	 */
	public GameSpaceContext build();
	
}
