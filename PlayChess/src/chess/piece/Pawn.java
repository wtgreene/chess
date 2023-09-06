package chess.piece;

/**
 * This class represents the Pawn piece on a chess board.
 * 
 * @author Will Greene
 */
public class Pawn extends Piece {
	
	/**
	 * Constructs a Pawn object.
	 * 
	 * @param file Square file that the Pawn currently occupies
	 * @param rank Square rank that the Pawn currently occupies
	 * @param isWhite White or Black piece
	 */
	public Pawn(int file, int rank, boolean isWhite) {
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
		
		if (Math.abs(getFile() - file) > 1)
			return false;
		
		if ( isWhite() ) {
			
			if (!isFirstMove() && rank != getRank() + 1)
				return false;
			if ( isFirstMove() && (rank != getRank() + 1 && rank != getRank() + 2) )
				return false;
		}
		
		else if ( !isWhite() ) {
			
			if (!isFirstMove() && rank != getRank() - 1)
				return false;
			if ( isFirstMove() && (rank != getRank() - 1 && rank != getRank() - 2) )
				return false;
		}
		
		return true;
	}
}
