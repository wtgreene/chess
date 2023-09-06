package chess.piece;

/**
 * This class represents the Knight piece on a chess board.
 * 
 * @author Will Greene
 */
public class Knight extends Piece {
	
	/**
	 * Constructs a Knight object.
	 * 
	 * @param file Square file that the Knight currently occupies
	 * @param rank Square rank that the Knight currently occupies
	 * @param isWhite White or Black piece
	 */
	public Knight(int file, int rank, boolean isWhite) {
		super(file, rank, isWhite);
	}
	
	/**
	 * Returns whether moving to the specified Square is a legal move on the board.
	 * 
	 * @param file Square file to move to
	 * @param rank Square rank to move to
	 * @return true if legal, false if not
	 */
	public boolean isLegalMove( int file, int rank ) {
		
		if ( (file == getFile() - 1 && rank == getRank() + 2)
				|| (file == getFile() + 1 && rank == getRank() + 2)
				|| (file == getFile() + 2 && rank == getRank() + 1)
				|| (file == getFile() + 2 && rank == getRank() - 1)
				|| (file == getFile() + 1 && rank == getRank() - 2)
				|| (file == getFile() - 1 && rank == getRank() - 2)
				|| (file == getFile() - 2 && rank == getRank() - 1)
				|| (file == getFile() - 2 && rank == getRank() + 1) )
			
			return true;
		
		return false;
	}
}
