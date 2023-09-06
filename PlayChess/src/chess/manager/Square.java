package chess.manager;

import chess.piece.Piece;

/**
 * This class represents a square on a chess board.
 * 
 * @author Will Greene
 */
public class Square {
	
	/** Square's file / column (a-h, or alternatively 1-8) */
	private int file;
	/** Square's rank / row (1-8) */
	private int rank;
	/** Piece that occupies the Square */
	private Piece piece;
	
	/**
	 * Constructs a Square object.
	 * 
	 * @param file Square file
	 * @param rank Square rank
	 * @param piece Piece that occupies Square
	 */
	public Square(int file, int rank, Piece piece) {
		
		setFile(file);
		setRank(rank);
		setPiece(piece);
	}
	
	/**
	 * Returns the Square's file.
	 * @return the Square's file
	 */
	public int getFile() {
		return file;
	}
	
	/**
	 * Sets the Square's file.
	 * @param file number to set
	 */
	private void setFile(int file) {
		this.file = file;
	}
	
	/**
	 * Returns the Square's rank.
	 * @return the Square's rank
	 */
	public int getRank() {
		return rank;
	}
	
	/**
	 * Sets the Square's rank.
	 * @param rank number to set
	 */
	private void setRank(int rank) {
		this.rank = rank;
	}
	
	/**
	 * Returns the Piece that occupies the Square (null if unoccupied).
	 * @return the Piece that occupies the Square (null if unoccupied)
	 */
	public Piece getPiece() {
		return piece;
	}
	
	/**
	 * Sets the Piece that occupies the Square.
	 * @param piece Piece that will occupy the Square
	 */
	public void setPiece(Piece piece) {
		this.piece = piece;
	}
}
