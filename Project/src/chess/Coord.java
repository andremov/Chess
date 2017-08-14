/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

/**
 *
 * @author Andres
 */
public class Coord {
	private int x;
	private int y;

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	public Coord(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public Coord clone() {
		return new Coord(this.x,this.y);
	}

	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if (obj instanceof Coord) {
			result = equals((Coord)obj);
		}
		return result;
	}
	
	public boolean equals(Coord obj) {
		return (this.getX() == obj.getX() && this.getY() == obj.getY());
	}

	public Coord(Coord base) {
		this.x = base.getX();
		this.y = base.getY();
	}
	
}
