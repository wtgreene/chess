package chess.piece;

/**
 * This class represents the Bishop piece on a chess board.
 * 
 * @author Will Greene
 */
public class Bishop extends Piece {
	
	/**
	 * Constructs a Bishop object.
	 * 
	 * @param file Square file that the Bishop currently occupies
	 * @param rank Square rank that the Bishop currently occupies
	 * @param isWhite White or Black piece
	 */
	public Bishop(int file, int rank, boolean isWhite) {
		super(file, rank, isWhite);
	}
	
	/**
	 * Returns whether moving to the specified Square is a legal move on the board.
	 * 
	 * @param file Square file to move to
	 * @param rank Square rank to move to
	 * @return true if legal, false if not
	 */
	public boolean isLegalMove(int file, int rank) {
		
		if ( Math.abs(file - getFile()) == Math.abs(rank - getRank()) )
			return true;
		
		return false;
	}
}
