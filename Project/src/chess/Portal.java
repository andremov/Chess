/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Andres
 */
public class Portal {

	/**
	 * Tracks this portal's current position.
	 */
	private Coord position;
	
	/**
	 * Identifies this portal's linked portal.
	 */
	private Portal portal;

	public Portal(Coord position) {
		this.position = position;
	}
	
	/**
	 * Link this portal with target portal
	 * @param target 
	 */
	public void link(Portal target) {
		target.portal = this;
		this.portal = target;
	}
	
	/**
	 * Unlink this portal from its target portal, and vice versa. 
	 */
	public void unlink() {
		if (this.isLinked()) {
			this.getPortal().portal = null;
			this.portal = null;
		}
	}
	
	/**
	 * Checks if this portal is linked to another portal.
	 * @return true if portal is linked
	 */
	public boolean isLinked() {
		return (portal != null);
	}
	
	/**
	 * @return this portal's tile
	 */
	public Tile getParent() {
		return (Handler.getTile(position.getX(),position.getY()));
	}
	
	/**
	 * Unlinks this portal, removes it from its tile, and removes its linked portal from its tile.
	 */
	public void destroy() {
		this.getParent().setPortal(null);
		this.getPortal().getParent().setPortal(null);
		this.unlink();
	}
	
	/**
	 * @return the portal
	 */
	public Portal getPortal() {
		return portal;
	}
	
	/**
	 * Returns a graphical representation of this portal.
	 * @return image
	 */
	public BufferedImage getImage()  {
		BufferedImage image = new BufferedImage(Handler.SIZE_TILE, Handler.SIZE_TILE, BufferedImage.TYPE_INT_ARGB);
		Graphics g = image.getGraphics();
		
		g.setColor(Color.getHSBColor(272.1f, (int)(100/100f), (int)(82.7f/100)));
		g.fillRect(0, 0, (int)(Handler.SIZE_TILE*0.3f), (int)(Handler.SIZE_TILE*0.3f));
		
		return image;
	}
	
	@Override
	public String toString() {
		String result;
		if (portal != null) {
			result = "a portal linked to (" + portal.getPosition().getX() + ", " + portal.getPosition().getY() + ")";
		} else {
			result = "an unlinked portal";
		}
		return result;
	}

	/**
	 * @return the position
	 */
	public Coord getPosition() {
		return position;
	}
}
