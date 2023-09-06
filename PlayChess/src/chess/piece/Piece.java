package chess.piece;

/**
 * This class represents a piece on a chess board.
 * 
 * @author Will Greene
 */
public abstract class Piece {
	
	/** Piece file / column (a-h, or alternatively 1-8) */
	private int file;
	/** Piece rank / row (1-8) */
	private int rank;
	/** Piece color (Black if false) */
	private boolean isWhite;
	
	/** indicates whether Piece has moved (used for Pawn, King & Rook) */
	private boolean isFirstMove;
	/** indicates whether Piece is in danger of En Passant (only applies to Pawns - see chess rules for more info) */
	private boolean isEnPassant;
	/** indicates whether Piece has been captured */
	
	private boolean isCaptured;
	/** Piece that is currently attacking this Piece */
	private Piece attackingPiece;
	
	/**
	 * Constructs a Piece object.
	 * 
	 * @param file Piece file
	 * @param rank Piece rank
	 * @param isWhite Piece color
	 */
	public Piece(int file, int rank, boolean isWhite) {
		
		setFile(file);
		setRank(rank);
		setWhite(isWhite);
		
		setFirstMove(true);
		setEnPassant(false);
		setCaptured(false, null);
	}

	/**
	 * Returns Piece file.
	 * @return Piece file
	 */
	public int getFile() {
		return file;
	}

	/**
	 * Sets Piece file.
	 * @param file new Piece file
	 */
	private void setFile(int file) {
		this.file = file;
	}

	/**
	 * Returns Piece rank.
	 * @return Piece rank
	 */
	public int getRank() {
		return rank;
	}

	/**
	 * Sets Piece rank.
	 * @param rank new Piece rank
	 */
	private void setRank(int rank) {
		this.rank = rank;
	}
	
	/**
	 * Returns Piece color.
	 * @return Piece color
	 */
	public boolean isWhite() {
		return isWhite;
	}

	/**
	 * Sets Piece color.
	 * @param isWhite Piece color
	 */
	private void setWhite(boolean isWhite) {
		this.isWhite = isWhite;
	}

	/**
	 * Returns whether Piece has moved.
	 * @return whether Piece has moved
	 */
	public boolean isFirstMove() {
		return isFirstMove;
	}

	/**
	 * Sets Piece first move indication.
	 * @param isFirstMove indication of first move
	 */
	public void setFirstMove(boolean isFirstMove) {
		this.isFirstMove = isFirstMove;
	}
	
	/**
	 * Returns whether Piece (applicable to Pawn only) is in danger of En Passant.
	 * @return whether Piece (applicable to Pawn only) is in danger of En Passant
	 */
	public boolean isEnPassant() {
		return isEnPassant;
	}
	
	/**
	 * Sets whether Piece (applicable to Pawn only) is in danger of En Passant.
	 * @param isEnPassant whether Piece is in danger of En Passant.
	 */
	public void setEnPassant(boolean isEnPassant) {
		this.isEnPassant = isEnPassant;
	}
	
	/**
	 * Returns whether Piece is captured.
	 * @return whether Piece is captured
	 */
	public boolean isCaptured() {
		return isCaptured;
	}
	
	/**
	 * Sets whether Piece is captured.
	 * 
	 * @param isCaptured whether piece is captured
	 * @param attackingPiece Piece that is attempting to capture this Piece
	 */
	public void setCaptured(boolean isCaptured, Piece attackingPiece) {
		this.isCaptured = isCaptured;
		this.attackingPiece = attackingPiece;
	}

	/**
	 * Returns Piece that is attempting to capture this Piece.
	 * @return Piece that is attempting to capture this Piece
	 */
	public Piece getAttackingPiece() {
		return attackingPiece;
	}
	
	/**
	 * Moves the Piece to a specified file and rank.
	 * @param file file to move to
	 * @param rank rank to move to
	 */
	public void moveTo(int file, int rank) {
		setFile(file);
		setRank(rank);
	}

	/**
	 * Returns whether moving to the specified Square is a legal move on the board.
	 * 
	 * @param file Square file to move to
	 * @param rank Square rank to move to
	 * @return true if legal, false if not
	 */
	public abstract boolean isLegalMove(int file, int rank);
}
