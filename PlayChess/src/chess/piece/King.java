package chess.piece;

/**
 * This class represents the King piece on a chess board.
 * 
 * @author Will Greene
 */
public class King extends Piece {
	
	/**
	 * Constructs a King object.
	 * 
	 * @param file Square file that the King currently occupies
	 * @param rank Square rank that the King currently occupies
	 * @param isWhite White or Black piece
	 */
	public King(int file, int rank, boolean isWhite) {
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
		
		if ( isFirstMove() && rank == getRank() && (file == getFile() + 2 || file == getFile() - 2) )
			return true;
		
		if ( Math.abs(file - getFile()) > 1 || Math.abs(rank - getRank()) > 1 )
			return false;
		
		return true;
	}
}
