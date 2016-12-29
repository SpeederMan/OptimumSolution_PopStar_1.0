package com.df.popstar.model;
/**
 * Œª÷√–≈œ¢
 */
public class Position {
	
	private int x;
	
	private int y;
	
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	@Override
	public int hashCode() {
		return x*10 + y;
	}

	@Override
	public boolean equals(Object obj) {
		Position other = (Position)obj;
		return this.x == other.x && this.y == other.y;
	}

	@Override
	public String toString() {
		return "[" + x + " " + y + "]";
	}
	
}
