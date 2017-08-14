/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Andres
 */
public class Screen extends Canvas implements Runnable {
	
	private Handler main;
	
	/**
	 * Create a GameDisplay custom canvas.
	 */
	public Screen(int width, int height, Handler main) {
		setSize(width,height);
		setLocation(1,1);
		setBackground(Color.black);
		this.main = main;
	}

	@Override
	public void paint(Graphics g) {
		try {
			getBufferStrategy().show();
		} catch (Exception ex) {

		}
	}

	@Override
	public void run() {
		createBufferStrategy(2);
		while (true) {
			Graphics g = getBufferStrategy().getDrawGraphics();
			
			try {
				g.drawImage(main.getBoardImage(), 0, 0, this);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
			repaint();

			try {
				Thread.sleep(100);
			} catch (InterruptedException ex) {
			}
		}
	}

}
