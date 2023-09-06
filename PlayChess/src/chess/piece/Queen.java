package chess.piece;

/**
 * This class represents the Queen piece on a chess board.
 * 
 * @author Will Greene
 */
public class Queen extends Piece {
	
	/**
	 * Constructs a Queen object.
	 * 
	 * @param file Square file that the Queen currently occupies
	 * @param rank Square rank that the Queen currently occupies
	 * @param isWhite White or Black piece
	 */
	public Queen(int file, int rank, boolean isWhite) {
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
		
		if ( file == getFile() || rank == getRank() )
			return true;
		
		if ( Math.abs(getFile() - file) == Math.abs(getRank() - rank) )
			return true;
		
		return false;
	}
}
