/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 *
 * @author Andres
 */
public class Handler {
	
	public static final int SIZE_TILE = 71;
	public static final int SIZE_BOARD = 8;
		
	private static Tile[][] board;

	public Handler() {
		createBoard();
		setPieces();
	}
	
	private void createBoard() {
		board = new Tile[SIZE_BOARD][SIZE_BOARD];
		for (int y = 0; y < SIZE_BOARD; y++) {
			for (int x = 0; x < SIZE_BOARD; x++) {
				int color = Tile.COLOR_WHITE;
				if ( y%2 == (x+1)%2 ) {
					color = Tile.COLOR_BLACK;
				}
				board[x][y] = new Tile(x,y,color);
			}
		}
	}
	
	private void setPieces() {
		
		int[][] pieces = { 
			{Piece.TYPE_PAWN,SIZE_BOARD}, 
			{Piece.TYPE_BISHOP,2}, 
			{Piece.TYPE_KING,1}, 
			{Piece.TYPE_KNIGHT,2}, 
			{Piece.TYPE_QUEEN,1}, 
			{Piece.TYPE_ROOK,2} 
		};
		
		for (int i = 0; i < pieces.length; i++) {
			int pieceType = pieces[i][0];
			int number = pieces[i][1];
			for (int color = 1; color < 3; color++) {
				for (int id = 0; id < number; id++) {
					Coord position = Piece.getStartPosition(pieceType, color, id);
					board[position.getX()][position.getY()].setPiece(new Piece(pieceType, color, position));
				}
			}
		}
		
	}
	
	public BufferedImage getBoardImage() throws IOException {
		int imageSize = SIZE_TILE*SIZE_BOARD;
		BufferedImage image = new BufferedImage(imageSize, imageSize, BufferedImage.TYPE_INT_ARGB);
		Graphics g = image.getGraphics();
		
		for (int y = 0; y < SIZE_BOARD; y++) {
			for (int x = 0; x < SIZE_BOARD; x++) {
//				System.out.println(board[x][y].toString());
				g.drawImage(board[x][y].getImage(), x*SIZE_TILE, y*SIZE_TILE, null);
			}
		}
		
		return image;
	}
	
	/**
	 * checks if a coordinate is within the board
	 * @param tile the coordinate
	 * @return true if coordinate is valid
	 */
	public static boolean isValidTile(Coord tile) {
		boolean valid;
		
		valid = (tile.getX() >= SIZE_BOARD || tile.getY() >= SIZE_BOARD || tile.getX() < 0 || tile.getY() < 0);
		valid = !valid;
		
		return valid;
	}
	
	/**
	 * checks if a coordinate is within the board and has no piece
	 * @param tile the coordinate
	 * @return true if tile is empty
	 */
	public static boolean isValidMove(Coord tile) {
		boolean valid = false;
		
		if (isValidTile(tile)) {
			if (getTile(tile.getX(),tile.getY()).isEmpty()) {
				valid = false;
			}
		}
		
		return valid;
	}
	
	/**
	 * checks if a coordinate is within the board and has an enemy piece
	 * @param movingPiece
	 * @param tile the coordinate
	 * @return true if tile is occupied by enemy piece
	 */
	public static boolean isValidAttack(Piece movingPiece, Coord tile) {
		boolean valid = false;
		
		if (isValidTile(tile)) {
			Tile contestedTile = getTile(tile.getX(),tile.getY());
			if (!contestedTile.isEmpty()) {
				valid = contestedTile.getPiece().getColor() != movingPiece.getColor();
			}
		}
		
		return valid;
	}
	
	/**
	 * returns tile in coordinate
	 * @param tile 
	 * @return 
	 */
	public static Tile getTile(Coord tile) {
		if (!isValidTile(tile)) {
			return null;
		}
		return getTile(tile.getX(), tile.getY());
	}
	
	/**
	 * returns tile in position x and y
	 * @param x
	 * @param y
	 * @return 
	 */
	public static Tile getTile(int x, int y) {
		return getTile(new Coord(x,y));
	}
	
}
