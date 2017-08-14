/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 *
 * @author Andres
 */
public class Piece {
	
	public static final int TYPE_PAWN = 1;
	public static final int TYPE_ROOK = 2;
	public static final int TYPE_BISHOP = 3;
	public static final int TYPE_KNIGHT = 4;
	public static final int TYPE_QUEEN = 5;
	public static final int TYPE_KING = 6;
	
	public static final int COLOR_WHITE = 1;
	public static final int COLOR_BLACK = 2;
	
	/**
	 * Identifies the piece as either black or white.
	 */
	private int color;
	/**
	 * Identifies the piece type as a pawn, rook, bishop, knight, queen or king.
	 */
	private int type;
	/**
	 * Identifies whether the piece has moved or not.
	 */
	private boolean moved;
	/**
	 * Tracks this piece's current position.
	 */
	private Coord position;
	/**
	 * List of moves this piece can make.
	 */
	private ArrayList<Coord> possibleMoves;
	
	
	public Piece(int type, int color, Coord position) {
		this.type = type;
		this.color = color;
		this.position = position;
		this.moved = false;
	}
	
	static public Coord getStartPosition (int type, int color, int id) {
		int x = 0;
		int y = 0;
		
		if (type == TYPE_PAWN) {
			x = id;
			y = 1;
		} else if (type == TYPE_BISHOP) {
			x = 2 + (id * 3);
		} else if (type == TYPE_KNIGHT) {
			x = 1 + (id * 5);
		} else if (type == TYPE_KING) {
			x = 4;
		} else if (type == TYPE_QUEEN) {
			x = 3;
		} else if (type == TYPE_ROOK) {
			x = 0 + (id * (Handler.SIZE_BOARD - 1));
		}
		
		if (color == COLOR_WHITE) {
			y = Handler.SIZE_BOARD - 1 - y;
		}
		
		
		return new Coord(x,y);
	}
	
	public BufferedImage getImage() throws IOException {
		BufferedImage image = new BufferedImage(Handler.SIZE_TILE, Handler.SIZE_TILE, BufferedImage.TYPE_INT_ARGB);
		Graphics g = image.getGraphics();
		
		g.drawImage(ImageIO.read(new File("assets/"+this.getSideName()+"/"+this.getPieceName()+".png")), 0, 0, Handler.SIZE_TILE, Handler.SIZE_TILE, null);
		
		return image;
	}

	@Override
	public String toString() {
		return "a " + this.getSideName() + " " + this.getPieceName();
	}
	
	/**
	 * @return the name of this piece's type
	 */
	private String getPieceName() {
		String name = "";
		switch(this.getType()) {
			case TYPE_PAWN:
				name = "pawn";
				break;
			case TYPE_BISHOP:
				name = "bishop";
				break;
			case TYPE_KING:
				name = "king";
				break;
			case TYPE_KNIGHT:
				name = "knight";
				break;
			case TYPE_QUEEN:
				name = "queen";
				break;
			case TYPE_ROOK:
				name = "rook";
				break;
			default:
				System.err.println(this.getColor());
				break;
		}
		return name;
	}
	
	/**
	 * @return the name of this piece's side
	 */
	private String getSideName() {
		String side = "";
		switch(this.getColor()) {
			case COLOR_WHITE:
				side = "white";
				break;
			case COLOR_BLACK:
				side = "black";
				break;
			default:
				System.err.println(this.getColor());
				break;
		}
		return side;
	}
	
	/**
	 * @return the moved
	 */
	public boolean isMoved() {
		return moved;
	}

	/**
	 * @return the position
	 */
	public Coord getPosition() {
		return position;
	}

	public void generateMoves() {
		ArrayList<Coord> moves;
		
		switch(this.getType()) {
			case (TYPE_BISHOP):
				moves = bishopMoves();
				break;
			case (TYPE_KING):
				moves = kingMoves();
				break;
			case (TYPE_KNIGHT):
				moves = knightMoves();
				break;
			case (TYPE_PAWN):
				moves = pawnMoves();
				break;
			case (TYPE_QUEEN):
				moves = bishopMoves();
				moves.addAll(rookMoves());
				break;
			case (TYPE_ROOK):
				moves = rookMoves();
				break;
			default:
				moves = new ArrayList<>();
				break;
		}
		
		this.possibleMoves = moves;
	}
	
	private ArrayList<Coord> bishopMoves() {
		ArrayList<Coord> moves = new ArrayList<>();
		
		for (int i = 0; i < 2; i++) {
			
			int invert = -1;
			if (i == 1) {
				invert = 1;
			}
			
			for (int j = 1; j < 8; j++) {
				Coord newCoord = new Coord(this.position.getX()+(j*invert),this.position.getY()+j);
				if (Handler.isValidMove(newCoord)) {
					// is a valid movement
					moves.add(newCoord);
				} else if (Handler.isValidAttack(this,newCoord)) {
					// is not a valid movement
					// is a valid attack
					moves.add(newCoord);
					break;
				} else {
					// is not a valid movement
					// is not a valid attack
					break;
				}
			}

			for (int j = 1; j < 8; j++) {
				Coord newCoord = new Coord(this.position.getX()+(j*invert),this.position.getY()-j);
				if (Handler.isValidMove(newCoord)) {
					// is a valid movement
					moves.add(newCoord);
				} else if (Handler.isValidAttack(this,newCoord)) {
					// is not a valid movement
					// is a valid attack
					moves.add(newCoord);
					break;
				} else {
					// is not a valid movement
					// is not a valid attack
					break;
				}
			}
		}
		
		return moves;
	}
	
	private ArrayList<Coord> rookMoves() {
		ArrayList<Coord> moves = new ArrayList<>();
		
		for (int i = 0; i < 2; i++) {
			
			int invert = -1;
			if (i == 1) {
				invert = 1;
			}
			
			for (int x = 1; x < 8; x++) {
				Coord newCoord = new Coord(this.position.getX()+(x*invert),this.position.getY());
				if (Handler.isValidMove(newCoord)) {
					// is a valid movement
					moves.add(newCoord);
				} else if (Handler.isValidAttack(this,newCoord)) {
					// is not a valid movement
					// is a valid attack
					moves.add(newCoord);
					break;
				} else {
					// is not a valid movement
					// is not a valid attack
					break;
				}
			}

			for (int y = 1; y < 8; y++) {
				Coord newCoord = new Coord(this.position.getX(),this.position.getY()+(y*invert));
				if (Handler.isValidMove(newCoord)) {
					// is a valid movement
					moves.add(newCoord);
				} else if (Handler.isValidAttack(this,newCoord)) {
					// is not a valid movement
					// is a valid attack
					moves.add(newCoord);
					break;
				} else {
					// is not a valid movement
					// is not a valid attack
					break;
				}
			}
		}
		
		return moves;
	}
	
	private ArrayList<Coord> pawnMoves() {
		ArrayList<Coord> moves = new ArrayList<>();
		
		
		
		return moves;
	}
	
	private ArrayList<Coord> knightMoves() {
		ArrayList<Coord> moves = new ArrayList<>();
		
		moves.add(new Coord(this.position.getX()-1,this.position.getY()-2));
		moves.add(new Coord(this.position.getX()+1,this.position.getY()-2));
		moves.add(new Coord(this.position.getX()-2,this.position.getY()-1));
		moves.add(new Coord(this.position.getX()+2,this.position.getY()-1));
		moves.add(new Coord(this.position.getX()-2,this.position.getY()+1));
		moves.add(new Coord(this.position.getX()+2,this.position.getY()+1));
		moves.add(new Coord(this.position.getX()-1,this.position.getY()+2));
		moves.add(new Coord(this.position.getX()+1,this.position.getY()+2));
		
		for (int i = 0; i < moves.size(); i++) {
			if (!Handler.isValidMove(moves.get(i)) && !Handler.isValidAttack(this,moves.get(i))){
				moves.remove(i);
			}
		}
		
		return moves;
	}
	
	private ArrayList<Coord> kingMoves() {
		ArrayList<Coord> moves = new ArrayList<>();
		
		for (int x = -1; x < 2; x++) {
			for (int y = -1; y < 2; y++) {	
				Coord newCoord = new Coord(this.position.getX()+x,this.position.getY()+y);
				moves.add(newCoord);
			}
		}
		
		for (int i = 0; i < moves.size(); i++) {
			if (moves.get(i).equals(this.position)) {
				moves.remove(i);
			} else if (!Handler.isValidMove(moves.get(i)) && !Handler.isValidAttack(this,moves.get(i))){
				moves.remove(i);
			}
		}
		
		return moves;
	}

	/**
	 * @return the color
	 */
	public int getColor() {
		return color;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @return the possibleMoves
	 */
	public ArrayList<Coord> getPossibleMoves() {
		return possibleMoves;
	}
	
}
