/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 *
 * @author Andres
 */
public class Tile {
	
	public static final int COLOR_WHITE = 1;
	public static final int COLOR_BLACK = 2;
	
	private int x;
	private int y;
	private int color;
	private Piece piece;
	private Portal portal;
	private boolean selected;

	public Tile(int x, int y, int color) {
		this.x = x;
		this.y = y;
		this.color = color;
		this.selected = false;
	}
	
	/**
	 * @return the color
	 */
	public int getColor() {
		return color;
	}

	/**
	 * @return the piece
	 */
	public Piece getPiece() {
		return piece;
	}

	/**
	 * @param piece the piece to set
	 */
	public void setPiece(Piece piece) {
		this.piece = piece;
	}

	/**
	 * @return the portal
	 */
	public Portal getPortal() {
		return portal;
	}

	/**
	 * @param portal the portal to set
	 */
	public void setPortal(Portal portal) {
		this.portal = portal;
	}
	
	public void removePortal() {
		if (this.portal != null) {
			this.portal.unlink();
		}
	}
	/**
	 * @return true if a piece is in this tile
	 */
	public boolean isEmpty() {
		return (this.piece == null);
	}
	
	/**
	 * @return true if a portal is in this tile
	 */
	public boolean hasPortal() {
		return (this.portal == null);
	}
	
	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * @return the name of this tile's color
	 */
	private String getColorName() {
		String side = "";
		switch(this.color) {
			case COLOR_WHITE:
				side = "white";
				break;
			case COLOR_BLACK:
				side = "black";
				break;
		}
		return side;
	}

	@Override
	public String toString() {
		String description = "a " + this.getColorName() + " tile";
		
		if (this.piece != null) {
			description = description + " with " + this.piece.toString();
			if (this.portal != null) {
				description = description + "and " + this.portal.toString();
			}
		} else if (this.portal != null) {
			description = description + " with " + this.portal.toString();
		}
		
		return description;
	}
	
	public BufferedImage getImage () throws IOException  {
		BufferedImage image = new BufferedImage(Handler.SIZE_TILE, Handler.SIZE_TILE, BufferedImage.TYPE_INT_ARGB);
		Graphics g = image.getGraphics();
		
		if (this.color == COLOR_BLACK) {
			g.setColor(Color.DARK_GRAY);
		} else if (this.color == COLOR_WHITE) {
			g.setColor(Color.GRAY);
		}
		
		g.fillRect(0, 0, Handler.SIZE_TILE, Handler.SIZE_TILE);
		
		if (this.portal != null) {
			g.drawImage(this.portal.getImage(), 0, 0, null);
		}
		
		if (this.piece != null) {
			g.drawImage(this.piece.getImage(), 0, 0, null);
		}
		
		return image;
	}

	/**
	 * @return the selected
	 */
	public boolean isSelected() {
		return selected;
	}

	/**
	 * @param selected the selected to set
	 */
	public void setSelected(boolean selected) {
		if (!this.isEmpty()) {
			this.selected = selected;
		}
	}
}
