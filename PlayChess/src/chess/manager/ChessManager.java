package chess.manager;

import chess.piece.Bishop;
import chess.piece.King;
import chess.piece.Knight;
import chess.piece.Pawn;
import chess.piece.Piece;
import chess.piece.Queen;
import chess.piece.Rook;

/**
 * This class sets up the chess board and manages movement of the pieces.
 * 
 * @author Will Greene
 */
public class ChessManager {
	
	/** chess board */
	private Square[][] board;
	
	/** White Pawns */
    public Piece whitePawn[];
	/** White Rooks */
    public Piece whiteRook[];
	/** White Knights */
    public Piece whiteKnight[];
	/** White Bishops */
    public Piece whiteBishop[];
	/** White Queens */
    public Piece whiteQueen[];
	/** White King */
    public Piece whiteKing;

	/** Black Pawns */
    public Piece blackPawn[];
	/** Black Rooks */
    public Piece blackRook[];
	/** Black Knights */
    public Piece blackKnight[];
	/** Black Bishops */
    public Piece blackBishop[];
	/** Black Queens */
    public Piece blackQueen[];
	/** Black King */
    public Piece blackKing;
    
    /** number of White Rooks on the board */
    public int numWhiteRooks;
    /** number of White Knights on the board */
    public int numWhiteKnights;
    /** number of White Bishops on the board */
    public int numWhiteBishops;
    /** number of White Queens on the board */
    public int numWhiteQueens;
    
    /** number of Black Rooks on the board */
    public int numBlackRooks;
    /** number of Black Knights on the board */
    public int numBlackKnights;
    /** number of Black Bishops on the board */
    public int numBlackBishops;
    /** number of Black Queens on the board */
    public int numBlackQueens;
	
    /** total number of Pieces on the board */
	private int numPieces;
	
	/** White's / Black's turn */
	private boolean whitesTurn;
	/** Piece attempting to move */
	private String pieceToMove;
	/** Piece number attempting to move */
	private int pieceNumberToMove;
	
	/** a file */
	private int a = 1;
	/** b file */
	private int b = 2;
	/** c file */
	private int c = 3;
	/** d file */
	private int d = 4;
	/** e file */
	private int e = 5;
	/** f file */
	private int f = 6;
	/** g file */
	private int g = 7;
	/** h file */
	private int h = 8;
	
	/**
	 * Constructs a ChessManager object.
	 */
	public ChessManager() {
		
		board = new Square[9][9];

		whiteRook = new Rook[11];
		whiteKnight = new Knight[11];
		whiteBishop = new Bishop[11];
		whiteQueen = new Queen[10];
		whitePawn = new Pawn[9];

		blackRook = new Rook[11];
		blackKnight = new Knight[11];
		blackBishop = new Bishop[11];
		blackQueen = new Queen[10];
		blackPawn = new Pawn[9];
		
		for (int i = 1; i <= 8; i++)
			whitePawn[i] = new Pawn(i, 2, true);

		whiteRook[1] = new Rook(a, 1, true);
		whiteKnight[1] = new Knight(b, 1, true);
		whiteBishop[1] = new Bishop(c, 1, true);
		whiteQueen[1] = new Queen(d, 1, true);
		whiteKing = new King(e, 1, true);
		whiteBishop[2] = new Bishop(f, 1, true);
		whiteKnight[2] = new Knight(g, 1, true);
		whiteRook[2] = new Rook (h, 1, true);
		
		for (int i = 1; i <= 8; i++)
			blackPawn[i] = new Pawn(i, 7, false);
		
		blackRook[1] = new Rook(a, 8, false);
		blackKnight[1] = new Knight(b, 8, false);
		blackBishop[1] = new Bishop(c, 8, false);
		blackQueen[1] = new Queen(d, 8, false);
		blackKing = new King(e, 8, false);
		blackBishop[2] = new Bishop(f, 8, false);
		blackKnight[2] = new Knight(g, 8, false);
		blackRook[2] = new Rook (h, 8, false);
		
		for (int i = 1; i <= 8; i++)
			board[i][2] = new Square( i, 2, whitePawn[i] );

		board[a][1] = new Square( a, 1, whiteRook[1] );
		board[b][1] = new Square( b, 1, whiteKnight[1] );
		board[c][1] = new Square( c, 1, whiteBishop[1] );
		board[d][1] = new Square( d, 1, whiteQueen[1] );
		board[e][1] = new Square( e, 1, whiteKing );
		board[f][1] = new Square( f, 1, whiteBishop[2] );
		board[g][1] = new Square( g, 1, whiteKnight[2] );
		board[h][1] = new Square( h, 1, whiteRook[2] );
		
		for (int i = 1; i <= 8; i++)
			board[i][7] = new Square( i, 7, blackPawn[i] );

		board[a][8] = new Square( a, 8, blackRook[1] );
		board[b][8] = new Square( b, 8, blackKnight[1] );
		board[c][8] = new Square( c, 8, blackBishop[1] );
		board[d][8] = new Square( d, 8, blackQueen[1] );
		board[e][8] = new Square( e, 8, blackKing );
		board[f][8] = new Square( f, 8, blackBishop[2] );
		board[g][8] = new Square( g, 8, blackKnight[2] );
		board[h][8] = new Square( h, 8, blackRook[2] );
		

		for (int i = a; i <= h; i++)
			for (int j = 3; j <= 6; j++)
				board[i][j] = new Square(i, j, null);

		numPieces = 32;

		numWhiteRooks = 2;
		numWhiteKnights = 2;
		numWhiteBishops = 2;
		numWhiteQueens = 1;
		
		numBlackRooks = 2;
		numBlackKnights = 2;
		numBlackBishops = 2;
		numBlackQueens = 1;
		
		setWhitesTurn(true);
	}
	
	/**
	 * Returns whether a Piece is able to move to a given file and rank.
	 * 
	 * @param oldFile Piece's current file
	 * @param oldRank Piece's current rank
	 * @param file Piece's potential file
	 * @param rank Piece's potential rank
	 * @return true if able, false if not
	 */
	public boolean ableToMove(int oldFile, int oldRank, int file, int rank) {
		
		if ( isWhitesTurn() ) {
			
			for (int i = 1; i <= 10; i++) {
				if ( getPieceToMove().equals("Rook") && getPieceNumberToMove() == i && whiteRook[i].isLegalMove(file, rank) ) {
					whiteRook[i] = ableToMoveHelper( whiteRook[i], oldFile, oldRank, file, rank );
					if ( whiteRook[i].getFile() == oldFile && whiteRook[i].getRank() == oldRank )
						return false;
					whiteRook[i].setFirstMove(false);
					return true;
				}
			}
			
			for (int i = 1; i <= 10; i++) {
				if ( getPieceToMove().equals("Knight") && getPieceNumberToMove() == i && whiteKnight[i].isLegalMove(file, rank) ) {
					whiteKnight[i] = ableToMoveHelper( whiteKnight[i], oldFile, oldRank, file, rank );
					if ( whiteKnight[i].getFile() == oldFile && whiteKnight[i].getRank() == oldRank )
						return false;
					return true;
				}
			}
			
			for (int i = 1; i <= 10; i++) {
				if ( getPieceToMove().equals("Bishop") && getPieceNumberToMove() == i && whiteBishop[i].isLegalMove(file, rank) ) {
					whiteBishop[i] = ableToMoveHelper( whiteBishop[i], oldFile, oldRank, file, rank );
					if ( whiteBishop[i].getFile() == oldFile && whiteBishop[i].getRank() == oldRank )
						return false;
					return true;
				}
			}
			
			for (int i = 1; i <= 9; i++) {
				if ( getPieceToMove().equals("Queen") && getPieceNumberToMove() == i && whiteQueen[i].isLegalMove(file, rank) ) {
					whiteQueen[i] = ableToMoveHelper( whiteQueen[i], oldFile, oldRank, file, rank );
					if ( whiteQueen[i].getFile() == oldFile && whiteQueen[i].getRank() == oldRank )
						return false;
					return true;
				}
			}
			
			for (int i = 1; i <= 8; i++) {
				if ( getPieceToMove().equals("Pawn") && getPieceNumberToMove() == i && whitePawn[i].isLegalMove(file, rank) ) {
					whitePawn[i] = ableToMoveHelper( whitePawn[i], oldFile, oldRank, file, rank );
					if ( whitePawn[i].getFile() == oldFile && whitePawn[i].getRank() == oldRank )
						return false;
					whitePawn[i].setFirstMove(false);
					return true;
				}
			}
			
			if ( getPieceToMove().equals("King") && whiteKing.isLegalMove(file, rank) ) {
				whiteKing = ableToMoveHelper( whiteKing, oldFile, oldRank, file, rank );
				if ( whiteKing.getFile() == oldFile && whiteKing.getRank() == oldRank )
					return false;
				whiteKing.setFirstMove(false);
				return true;
			}
		}
		
		else if ( !isWhitesTurn() ) {
			
			for (int i = 1; i <= 10; i++) {
				if ( getPieceToMove().equals("Rook") && getPieceNumberToMove() == i && blackRook[i].isLegalMove(file, rank) ) {
					blackRook[i] = ableToMoveHelper( blackRook[i], oldFile, oldRank, file, rank );
					if ( blackRook[i].getFile() == oldFile && blackRook[i].getRank() == oldRank )
						return false;
					blackRook[i].setFirstMove(false);
					return true;
				}
			}
			
			for (int i = 1; i <= 10; i++) {
				if ( getPieceToMove().equals("Knight") && getPieceNumberToMove() == i && blackKnight[i].isLegalMove(file, rank) ) {
					blackKnight[i] = ableToMoveHelper( blackKnight[i], oldFile, oldRank, file, rank );
					if ( blackKnight[i].getFile() == oldFile && blackKnight[i].getRank() == oldRank )
						return false;
					return true;
				}
			}
			
			for (int i = 1; i <= 10; i++) {
				if ( getPieceToMove().equals("Bishop") && getPieceNumberToMove() == i && blackBishop[i].isLegalMove(file, rank) ) {
					blackBishop[i] = ableToMoveHelper( blackBishop[i], oldFile, oldRank, file, rank );
					if ( blackBishop[i].getFile() == oldFile && blackBishop[i].getRank() == oldRank )
						return false;
					return true;
				}
			}
			
			for (int i = 1; i <= 9; i++) {
				if ( getPieceToMove().equals("Queen") && getPieceNumberToMove() == i && blackQueen[i].isLegalMove(file, rank) ) {
					blackQueen[i] = ableToMoveHelper( blackQueen[i], oldFile, oldRank, file, rank );
					if ( blackQueen[i].getFile() == oldFile && blackQueen[i].getRank() == oldRank )
						return false;
					return true;
				}
			}
			
			for (int i = 1; i <= 8; i++) {
				if ( getPieceToMove().equals("Pawn") && getPieceNumberToMove() == i && blackPawn[i].isLegalMove(file, rank) ) {
					blackPawn[i] = ableToMoveHelper( blackPawn[i], oldFile, oldRank, file, rank );
					if ( blackPawn[i].getFile() == oldFile && blackPawn[i].getRank() == oldRank )
						return false;
					blackPawn[i].setFirstMove(false);
					return true;
				}
			}
			
			if ( getPieceToMove().equals("King") && blackKing.isLegalMove(file, rank) ) {
				blackKing = ableToMoveHelper( blackKing, oldFile, oldRank, file, rank );
				if ( blackKing.getFile() == oldFile && blackKing.getRank() == oldRank )
					return false;
				blackKing.setFirstMove(false);
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Helper method for ableToMove().
	 * 
	 * @param p Piece to analyze
	 * @param oldFile p's current file
	 * @param oldRank p's current rank
	 * @param file p's potential file
	 * @param rank p's potential rank
	 * @return p, with it's new file and rank (will be the same if unable to move)
	 */
	private Piece ableToMoveHelper(Piece p, int oldFile, int oldRank, int file, int rank) {
				
		if (p instanceof Rook || p instanceof Queen) {
			
			if (file == oldFile && rank > oldRank)
				for (int i = 1; i < Math.abs(rank - oldRank); i++)
					if ( getSquare(oldFile, oldRank + i).getPiece() != null )
						return p;
			
			if (file == oldFile && rank < oldRank)
				for (int i = 1; i < Math.abs(rank - oldRank); i++)
					if ( getSquare(oldFile, oldRank - i).getPiece() != null )
						return p;
			
			if (rank == oldRank && file > oldFile)
				for (int i = 1; i < Math.abs(file - oldFile); i++)
					if ( getSquare(oldFile + i, oldRank).getPiece() != null )
						return p;
			
			if (rank == oldRank && file < oldFile)
				for (int i = 1; i < Math.abs(file - oldFile); i++)
					if ( getSquare(oldFile - i, oldRank).getPiece() != null )
						return p;
		}
		
		if (p instanceof Bishop || p instanceof Queen) {
			
			if (file - oldFile == rank - oldRank && file - oldFile >= 1)
				for (int i = 1; i < Math.abs(file - oldFile); i++)
					if ( getSquare(oldFile + i, oldRank + i).getPiece() != null )
						return p;
			
			if (file - oldFile == oldRank - rank && file - oldFile >= 1)
				for (int i = 1; i < Math.abs(file - oldFile); i++)
					if ( getSquare(oldFile + i, oldRank - i).getPiece() != null )
						return p;
			
			if (file - oldFile == rank - oldRank && file - oldFile <= -1)
				for (int i = 1; i < Math.abs(file - oldFile); i++)
					if ( getSquare(oldFile - i, oldRank - i).getPiece() != null )
						return p;
			
			if (file - oldFile == oldRank - rank && file - oldFile <= -1)
				for (int i = 1; i < Math.abs(file - oldFile); i++)
					if ( getSquare(oldFile - i, oldRank + i).getPiece() != null )
						return p;
		}
		
		if ( getSquare(file, rank).getPiece() == null ) {
			
			if (p instanceof King && p.isFirstMove() && file == oldFile + 2) {
				
				if ( isWhitesTurn() && whiteRook[2].isFirstMove() || !isWhitesTurn() && blackRook[2].isFirstMove() ) {

					if (getSquare(oldFile + 1, oldRank).getPiece() != null || getSquare(oldFile + 2, oldRank).getPiece() != null)
						return p;

					p.moveTo(oldFile + 1, oldRank);
					getSquare(oldFile + 1, oldRank).setPiece(p);
					getSquare(oldFile, oldRank).setPiece(null);

					if ( (isWhitesTurn() && isInCheckWhite()) || (!isWhitesTurn() && isInCheckBlack()) ) {
						p.moveTo(oldFile, oldRank);
						getSquare(oldFile + 1, oldRank).setPiece(null);
						getSquare(oldFile, oldRank).setPiece(p);
						return p;
					}

					p.moveTo(oldFile + 2, oldRank);
					getSquare(oldFile + 2, oldRank).setPiece(p);
					getSquare(oldFile + 1, oldRank).setPiece(null);

					if ( (isWhitesTurn() && isInCheckWhite()) || (!isWhitesTurn() && isInCheckBlack()) ) {
						p.moveTo(oldFile, oldRank);
						getSquare(oldFile + 2, oldRank).setPiece(null);
						getSquare(oldFile, oldRank).setPiece(p);
						return p;
					}
					
					if ( isWhitesTurn() ) {
						whiteKing.setFirstMove(false);
						whiteRook[2].setFirstMove(false);
						whiteRook[2].moveTo(f, 1);
						getSquare(h, 1).setPiece(null);
						getSquare(oldFile + 1, oldRank).setPiece( whiteRook[2] );
						return p;
					}
					
					else {
						blackKing.setFirstMove(false);
						blackRook[2].setFirstMove(false);
						blackRook[2].moveTo(f, 8);
						getSquare(h, 8).setPiece(null);
						getSquare(oldFile + 1, oldRank).setPiece( blackRook[2] );
						return p;
					}

				}

				return p;
			}
			
			if (p instanceof King && p.isFirstMove() && file == oldFile - 2) {

				if ( isWhitesTurn() && whiteRook[1].isFirstMove() || !isWhitesTurn() && blackRook[1].isFirstMove() ) {

					if (getSquare(oldFile - 1, oldRank).getPiece() != null || getSquare(oldFile - 2, oldRank).getPiece() != null)
						return p;

					p.moveTo(oldFile - 1, oldRank);
					getSquare(oldFile - 1, oldRank).setPiece(p);
					getSquare(oldFile, oldRank).setPiece(null);

					if ( (isWhitesTurn() && isInCheckWhite()) || (!isWhitesTurn() && isInCheckBlack()) ) {
						p.moveTo(oldFile, oldRank);
						getSquare(oldFile - 1, oldRank).setPiece(null);
						getSquare(oldFile, oldRank).setPiece(p);
						return p;
					}

					p.moveTo(oldFile - 2, oldRank);
					getSquare(oldFile - 2, oldRank).setPiece(p);
					getSquare(oldFile - 1, oldRank).setPiece(null);

					if ( (isWhitesTurn() && isInCheckWhite()) || (!isWhitesTurn() && isInCheckBlack()) ) {
						p.moveTo(oldFile, oldRank);
						getSquare(oldFile - 2, oldRank).setPiece(null);
						getSquare(oldFile, oldRank).setPiece(p);
						return p;
					}
					
					if ( isWhitesTurn() ) {
						whiteKing.setFirstMove(false);
						whiteRook[1].setFirstMove(false);
						whiteRook[1].moveTo(d, 1);
						getSquare(a, 1).setPiece(null);
						getSquare(oldFile - 1, oldRank).setPiece( whiteRook[1] );
						return p;
					}
					
					else {
						blackKing.setFirstMove(false);
						blackRook[1].setFirstMove(false);
						blackRook[1].moveTo(d, 8);
						getSquare(a, 8).setPiece(null);
						getSquare(oldFile - 1, oldRank).setPiece( blackRook[1] );
						return p;
					}

				}

				return p;
			}
			
			if (p instanceof Pawn) {
				
				// En passant
				if ( isWhitesTurn() && Math.abs(file - oldFile) == 1 && rank == oldRank + 1
						&& getSquare(file, rank - 1).getPiece() instanceof Pawn && getSquare(file, rank - 1).getPiece().isEnPassant() ) {
					
					p.moveTo(file, rank);
					
					for (int i = 1; i <= 8; i++) {
						if ( blackPawn[i].isEnPassant() ) {
							getSquare(file, rank).setPiece( blackPawn[i] );
							getSquare(file, rank).getPiece().setCaptured(true, p);
						}
					}
					
					getSquare(oldFile, oldRank).setPiece(null);
					getSquare(oldFile, oldRank - 1).setPiece(null);					

					if ( (isWhitesTurn() && isInCheckWhite()) || (!isWhitesTurn() && isInCheckBlack()) ) {
						
						p.moveTo(oldFile, oldRank);
						
						for (int i = 1; i <= 8; i++) {
							if ( blackPawn[i].isEnPassant() ) {
								getSquare(file, rank - 1).setPiece( blackPawn[i] );
								getSquare(file, rank - 1).getPiece().setCaptured(false, null);
							}
						}
						
						getSquare(oldFile, oldRank).setPiece(p);
						getSquare(file, rank).setPiece(null);
						return p;
					}

					getSquare(file, rank).setPiece(p);
					numPieces--;
					return p;
				}
				
				else if ( !isWhitesTurn() && Math.abs(file - oldFile) == 1 && rank == oldRank - 1
						&& getSquare(file, rank + 1).getPiece() instanceof Pawn && getSquare(file, rank + 1).getPiece().isEnPassant() ) {
					
					p.moveTo(file, rank);
					
					for (int i = 1; i <= 8; i++) {
						if ( whitePawn[i].isEnPassant() ) {
							getSquare(file, rank).setPiece( whitePawn[i] );
							getSquare(file, rank).getPiece().setCaptured(true, p);
						}
					}
					
					getSquare(oldFile, oldRank).setPiece(null);
					getSquare(oldFile, oldRank + 1).setPiece(null);					

					if ( (isWhitesTurn() && isInCheckWhite()) || (!isWhitesTurn() && isInCheckBlack()) ) {
						
						p.moveTo(oldFile, oldRank);
						
						for (int i = 1; i <= 8; i++) {
							if ( whitePawn[i].isEnPassant() ) {
								getSquare(file, rank + 1).setPiece( whitePawn[i] );
								getSquare(file, rank + 1).getPiece().setCaptured(false, null);
							}
						}
						
						getSquare(oldFile, oldRank).setPiece(p);
						getSquare(file, rank).setPiece(null);
						return p;
					}

					getSquare(file, rank).setPiece(p);
					numPieces--;
					return p;
				}
				
				if (file != oldFile)
					return p;
	
				if ( isWhitesTurn() ) {
					
					if (rank == oldRank + 2 && getSquare(oldFile, oldRank + 1).getPiece() != null )
						return p;
				}
				
				else
					if (rank == oldRank - 2 && getSquare(oldFile, oldRank - 1).getPiece() != null )
						return p;
				
				if (isWhitesTurn() && rank == 8) {
					
					whiteQueen[++numWhiteQueens] = new Queen(file, rank, true);
					
					whiteQueen[numWhiteQueens].moveTo(file, rank);
					getSquare(file, rank).setPiece( whiteQueen[numWhiteQueens] );
					getSquare(oldFile, oldRank).setPiece(null);

					if ( (isWhitesTurn() && isInCheckWhite()) || (!isWhitesTurn() && isInCheckBlack()) ) {
						p.moveTo(oldFile, oldRank);
						getSquare(oldFile, oldRank).setPiece(p);
						getSquare(file, rank).setPiece(null);
						numWhiteQueens--;
						return p;
					}
					
					p.moveTo(file, rank);
					return p;
				}
								
				else if (!isWhitesTurn() && rank == 1) {
					
					blackQueen[++numBlackQueens] = new Queen(file, rank, false);
					
					blackQueen[numBlackQueens].moveTo(file, rank);
					getSquare(file, rank).setPiece( blackQueen[numBlackQueens] );
					getSquare(oldFile, oldRank).setPiece(null);

					if ( (isWhitesTurn() && isInCheckBlack()) || (!isWhitesTurn() && isInCheckBlack()) ) {
						p.moveTo(oldFile, oldRank);
						getSquare(oldFile, oldRank).setPiece(p);
						getSquare(file, rank).setPiece(null);
						numBlackQueens--;
						return p;
					}
					
					p.moveTo(file, rank);
					return p;
				}
			}
			
			p.moveTo(file, rank);
			getSquare(file, rank).setPiece(p);
			getSquare(oldFile, oldRank).setPiece(null);

			if ( (isWhitesTurn() && isInCheckWhite()) || (!isWhitesTurn() && isInCheckBlack()) ) {
				p.moveTo(oldFile, oldRank);
				getSquare(oldFile, oldRank).setPiece(p);
				getSquare(file, rank).setPiece(null);
				return p;
			}

			if ( p instanceof Pawn && p.isFirstMove() && rank == oldRank + 2 && isWhitesTurn() ) {
				Pawn q = (Pawn)p;
				q.setEnPassant(true);
			}
			
			if ( p instanceof Pawn && p.isFirstMove() && rank == oldRank - 2 && !isWhitesTurn() ) {
				Pawn q = (Pawn)p;
				q.setEnPassant(true);
			}
			
			return p;
		}
			
		else if ( (isWhitesTurn() && !getSquare(file, rank).getPiece().isWhite())
				|| (!isWhitesTurn() && getSquare(file, rank).getPiece().isWhite()) ) {
			
			if (p instanceof Pawn) {
				
				if ( Math.abs(file - oldFile) != 1 )
					return p;
				
				if ( isWhitesTurn() && rank - oldRank !=  1)
					return p;
				
				if (!isWhitesTurn() && rank - oldRank != -1)
					return p;
				
				if (isWhitesTurn() && rank == 8) {
					
					whiteQueen[++numWhiteQueens] = new Queen(file, rank, true);
					
					getSquare(file, rank).getPiece().setCaptured(true, whiteQueen[numWhiteQueens]);
					getSquare(oldFile, oldRank).setPiece(null);

					if ( (isWhitesTurn() && isInCheckWhite()) || (!isWhitesTurn() && isInCheckBlack()) ) {
						p.moveTo(oldFile, oldRank);
						getSquare(oldFile, oldRank).setPiece(p);
						getSquare(file, rank).getPiece().setCaptured(false, null);
						numWhiteQueens--;
						return p;
					}
					
					getSquare(file, rank).setPiece( whiteQueen[numWhiteQueens] );
					numPieces--;
					p.moveTo(file, rank);
					return p;
				}
								
				else if (!isWhitesTurn() && rank == 1) {
					
					blackQueen[++numBlackQueens] = new Queen(file, rank, true);
					
					getSquare(file, rank).getPiece().setCaptured(true, blackQueen[numBlackQueens]);
					getSquare(oldFile, oldRank).setPiece(null);

					if ( (isWhitesTurn() && isInCheckBlack()) || (!isWhitesTurn() && isInCheckBlack()) ) {
						p.moveTo(oldFile, oldRank);
						getSquare(oldFile, oldRank).setPiece(p);
						getSquare(file, rank).getPiece().setCaptured(false, null);
						numBlackQueens--;
						return p;
					}
					
					getSquare(file, rank).setPiece( blackQueen[numBlackQueens] );
					numPieces--;
					p.moveTo(file, rank);
					return p;
				}
			}
			
			p.moveTo(file, rank);
			getSquare(file, rank).getPiece().setCaptured(true, p);
			getSquare(oldFile, oldRank).setPiece(null);

			if ( (isWhitesTurn() && isInCheckWhite()) || (!isWhitesTurn() && isInCheckBlack()) ) {
				p.moveTo(oldFile, oldRank);
				getSquare(oldFile, oldRank).setPiece(p);
				getSquare(file, rank).getPiece().setCaptured(false, null);
				return p;
			}

			getSquare(file, rank).setPiece(p);
			numPieces--;
			return p;
		}
		
		return p;
	}
	
	/**
	 * Changes turn.
	 */
	public void changeTurn() {
		
		if ( isWhitesTurn() )
			setWhitesTurn(false);
		
		else
			setWhitesTurn(true);
	}
	
	/**
	 * Returns the Square of a specified file and rank.
	 * 
	 * @param file Square file
	 * @param rank Square rank
	 * @return the Square of a specified file and rank
	 */
	public Square getSquare(int file, int rank) {
		return board[file][rank];
	}
	
	/**
	 * Returns number of pieces on the board.
	 * @return number of pieces on the board
	 */
	public int getNumPieces() {
		return numPieces;
	}

	/**
	 * Returns whether Black has delivered checkmate to White.
	 * @return whether Black has delivered checkmate to White
	 */
	public boolean isCheckmateWhite() {
		
		if ( !isInCheckWhite() )
			return false;
		
		int file;
		int rank;
		
		// Knights
		for (int i = 1; i <= 10; i++) {
			
			if ( whiteKnight[i] == null || whiteKnight[i].isCaptured() )
				continue;
			
			file = whiteKnight[i].getFile();
			rank = whiteKnight[i].getRank();
						
			getSquare(file, rank).setPiece(null);
			
			if ( isCheckmateWhiteHelper(whiteKnight[i], file, rank, file - 1, rank + 2) == 0
					|| isCheckmateWhiteHelper(whiteKnight[i], file, rank, file + 1, rank + 2) == 0
					|| isCheckmateWhiteHelper(whiteKnight[i], file, rank, file + 2, rank + 1) == 0
					|| isCheckmateWhiteHelper(whiteKnight[i], file, rank, file + 2, rank - 1) == 0
					|| isCheckmateWhiteHelper(whiteKnight[i], file, rank, file + 1, rank - 2) == 0
					|| isCheckmateWhiteHelper(whiteKnight[i], file, rank, file - 1, rank - 2) == 0
					|| isCheckmateWhiteHelper(whiteKnight[i], file, rank, file - 2, rank - 1) == 0
					|| isCheckmateWhiteHelper(whiteKnight[i], file, rank, file - 2, rank + 1) == 0 )
				
				return false;
			
			getSquare(file, rank).setPiece(whiteKnight[i]);
		}
		
		// Rooks
		for (int i = 1; i <= 10; i++) {
			
			if ( whiteRook[i] == null || whiteRook[i].isCaptured() )
				continue;
			
			file = whiteRook[i].getFile();
			rank = whiteRook[i].getRank();
			
			getSquare(file, rank).setPiece(null);
	
			for (int j = 1; j < 8; j++)
	
				if ( isCheckmateWhiteHelper(whiteRook[i], file, rank, file + j, rank) == -1 )
					break;
				
				else if ( isCheckmateWhiteHelper(whiteRook[i], file, rank, file + j, rank) == 0 )
					return false;
			
			for (int j = 1; j < 8; j++)
								
				if ( isCheckmateWhiteHelper(whiteRook[i], file, rank, file - j, rank) == -1 )
					break;
			
				else if ( isCheckmateWhiteHelper(whiteRook[i], file, rank, file - j, rank) == 0 )
					return false;
			
			for (int j = 1; j < 8; j++)
				
				if ( isCheckmateWhiteHelper(whiteRook[i], file, rank, file, rank + j) == -1 )
					break;
			
				else if ( isCheckmateWhiteHelper(whiteRook[i], file, rank, file, rank + j) == 0 )
					return false;
			
			for (int j = 1; j < 8; j++)
				
				if ( isCheckmateWhiteHelper(whiteRook[i], file, rank, file, rank - j) == -1 )
					break;
			
				else if ( isCheckmateWhiteHelper(whiteRook[i], file, rank, file, rank - j) == 0 )
					return false;
			
			getSquare(file, rank).setPiece(whiteRook[i]);
		}
		
		// Bishops
		for (int i = 1; i <= 10; i++) {
			
			if ( whiteBishop[i] == null || whiteBishop[i].isCaptured() )
				continue;
			
			file = whiteBishop[i].getFile();
			rank = whiteBishop[i].getRank();
			
			getSquare(file, rank).setPiece(null);
			
			for (int j = 1; j < 8; j++)
				
				if ( isCheckmateWhiteHelper(whiteBishop[i], file, rank, file + j, rank + j) == -1 )
					
					break;
				else if ( isCheckmateWhiteHelper(whiteBishop[i], file, rank, file + j, rank + j) == 0 )
					return false;
			
			for (int j = 1; j < 8; j++)
				
				if ( isCheckmateWhiteHelper(whiteBishop[i], file, rank, file - j, rank - j) == -1 )
					break;
			
				else if ( isCheckmateWhiteHelper(whiteBishop[i], file, rank, file - j, rank - j) == 0 )
					return false;
			
			for (int j = 1; j < 8; j++)
				
				if ( isCheckmateWhiteHelper(whiteBishop[i], file, rank, file - j, rank + j) == -1 )
					break;
			
				else if ( isCheckmateWhiteHelper(whiteBishop[i], file, rank, file - j, rank + j) == 0 )
					return false;
			
			for (int j = 1; j < 8; j++)
				
				if ( isCheckmateWhiteHelper(whiteBishop[i], file, rank, file + j, rank - j) == -1 )
					break;
				
				else if ( isCheckmateWhiteHelper(whiteBishop[i], file, rank, file + j, rank - j) == 0 )
					return false;
			
			getSquare(file, rank).setPiece(whiteBishop[i]);
		}
		
		// Queen(s)
		for (int i = 1; i < 10; i++) {
			
			if ( whiteQueen[i] == null || whiteQueen[i].isCaptured() )
				continue;
			
			file = whiteQueen[i].getFile();
			rank = whiteQueen[i].getRank();
			
			getSquare(file, rank).setPiece(null);
	
			for (int j = 1; j < 8; j++)
				
				if ( isCheckmateWhiteHelper(whiteQueen[i], file, rank, file + j, rank) == -1 )
					break;
			
				else if ( isCheckmateWhiteHelper(whiteQueen[i], file, rank, file + j, rank) == 0 )
					return false;
			
			for (int j = 1; j < 8; j++)
				
				if ( isCheckmateWhiteHelper(whiteQueen[i], file, rank, file - j, rank) == -1 )
					break;
			
				else if ( isCheckmateWhiteHelper(whiteQueen[i], file, rank, file - j, rank) == 0 )
					return false;
			
			for (int j = 1; j < 8; j++)
				
				if ( isCheckmateWhiteHelper(whiteQueen[i], file, rank, file, rank + j) == -1 )
					break;
			
				else if ( isCheckmateWhiteHelper(whiteQueen[i], file, rank, file, rank + j) == 0 )
					return false;
			
			for (int j = 1; j < 8; j++)
				
				if ( isCheckmateWhiteHelper(whiteQueen[i], file, rank, file, rank - j) == -1 )
					break;
			
				else if ( isCheckmateWhiteHelper(whiteQueen[i], file, rank, file, rank - j) == 0 )
					return false;
			
			for (int j = 1; j < 8; j++)
				
				if ( isCheckmateWhiteHelper(whiteQueen[i], file, rank, file + j, rank + j) == -1 )
					break;
			
				else if ( isCheckmateWhiteHelper(whiteQueen[i], file, rank, file + j, rank + j) == 0 )
					return false;
			
			for (int j = 1; j < 8; j++)
				
				if ( isCheckmateWhiteHelper(whiteQueen[i], file, rank, file - j, rank - j) == -1 )
					break;
			
				else if ( isCheckmateWhiteHelper(whiteQueen[i], file, rank, file - j, rank - j) == 0 )
					return false;
			
			for (int j = 1; j < 8; j++)
				
				if ( isCheckmateWhiteHelper(whiteQueen[i], file, rank, file - j, rank + j) == -1 )
					break;
			
				else if ( isCheckmateWhiteHelper(whiteQueen[i], file, rank, file - j, rank + j) == 0 )
					return false;
			
			for (int j = 1; j < 8; j++)
				
				if ( isCheckmateWhiteHelper(whiteQueen[i], file, rank, file + j, rank - j) == -1 )
					break;
			
				else if ( isCheckmateWhiteHelper(whiteQueen[i], file, rank, file + j, rank - j) == 0 )
					return false;
			
			getSquare(file, rank).setPiece(whiteQueen[i]);
		}
		
		// King
		file = whiteKing.getFile();
		rank = whiteKing.getRank();
		
		getSquare(file, rank).setPiece(null);
		
		if ( isCheckmateWhiteHelper(whiteKing, file, rank, file + 0, rank + 1) == 0
				|| isCheckmateWhiteHelper(whiteKing, file, rank, file + 1, rank + 1) == 0
				|| isCheckmateWhiteHelper(whiteKing, file, rank, file + 1, rank + 0) == 0
				|| isCheckmateWhiteHelper(whiteKing, file, rank, file + 1, rank - 1) == 0
				|| isCheckmateWhiteHelper(whiteKing, file, rank, file + 0, rank - 1) == 0
				|| isCheckmateWhiteHelper(whiteKing, file, rank, file - 1, rank - 1) == 0
				|| isCheckmateWhiteHelper(whiteKing, file, rank, file - 1, rank + 0) == 0
				|| isCheckmateWhiteHelper(whiteKing, file, rank, file - 1, rank + 1) == 0 )
			
			return false;

		getSquare(file, rank).setPiece(whiteKing);
		
		// Pawn
		for (int i = 1; i <= 8; i++) {
			
			if ( whitePawn[i].isCaptured() )
				continue;
			
			file = whitePawn[i].getFile();
			rank = whitePawn[i].getRank();
	
			getSquare(file, rank).setPiece(null);
	
			if ( isCheckmateWhiteHelper(whitePawn[i], file, rank, file, rank + 1) == 0
					|| isCheckmateWhiteHelper(whitePawn[i], file, rank, file - 1, rank + 1) == 0	
					|| isCheckmateWhiteHelper(whitePawn[i], file, rank, file + 1, rank + 1) == 0
					|| isCheckmateWhiteHelper(whitePawn[i], file, rank, file, rank + 2) == 0 && whitePawn[i].isFirstMove() )
				
				return false;
			
			getSquare(file, rank).setPiece(whitePawn[i]);
		}
				
		return true;
	}
	
	/**
	 * Helper method for isCheckmateWhite().
	 * 
	 * @param p Piece to analyze
	 * @param oldFile p's current file
	 * @param oldRank p's current rank
	 * @param file p's potential file
	 * @param rank p's potential rank
	 * @return -1 for a dead end (stop this analysis line),
	 * 			0 if the move avoids checkmate
	 * 			1 if this move does not avoid checkmate
	 */
	private int isCheckmateWhiteHelper(Piece p, int oldFile, int oldRank, int file, int rank) {
		
		int deadEnd = -1;
		int avoidsCheckmate = 0;
		int doesNotAvoidCheckmate = 1;
				
		if (file < 1 || file > 8 || rank < 1 || rank > 8)
			return deadEnd;
		
		if (p instanceof Pawn) {
			
			if ( file == oldFile && rank == oldRank + 2
					&& (getSquare(oldFile, oldRank + 1).getPiece() != null
					|| getSquare(oldFile, oldRank + 2).getPiece() != null) )
				return doesNotAvoidCheckmate;
			
			if ( file == oldFile && rank == oldRank + 1
					&& getSquare(oldFile, oldRank + 1).getPiece() != null )
				return doesNotAvoidCheckmate;
			
			if ( Math.abs(file - oldFile) == 1 && rank == oldRank + 1
					&& getSquare(file, rank).getPiece() == null )
				return doesNotAvoidCheckmate;
		}
		
		if ( getSquare(file, rank).getPiece() == null ) {
			
			p.moveTo(file, rank);
			getSquare(oldFile, oldRank).setPiece(null);
			getSquare(file, rank).setPiece(p);
			
				if ( !isInCheckWhite() ) {
					p.moveTo(oldFile, oldRank);
					getSquare(oldFile, oldRank).setPiece(p);
					getSquare(file, rank).setPiece(null);
					return avoidsCheckmate;
				}
				
			p.moveTo(oldFile, oldRank);
			getSquare(oldFile, oldRank).setPiece(p);
			getSquare(file, rank).setPiece(null);
		}
	
		else if ( !getSquare(file, rank).getPiece().isWhite() ) {
			
			p.moveTo(file, rank);
			getSquare(oldFile, oldRank).setPiece(null);
			getSquare(file, rank).getPiece().setCaptured(true, p);
			
				if ( !isInCheckWhite() ) {
					p.moveTo(oldFile, oldRank);
					getSquare(oldFile, oldRank).setPiece(p);
					getSquare(file, rank).getPiece().setCaptured(false, null);
					return avoidsCheckmate;
				}
				
			p.moveTo(oldFile, oldRank);
			getSquare(oldFile, oldRank).setPiece(p);
			getSquare(file, rank).getPiece().setCaptured(false, null);
			return deadEnd;
		}
		
		else if ( getSquare(file, rank).getPiece().isWhite() )
			return deadEnd;
		
		return doesNotAvoidCheckmate;
	}

	/**
	 * Returns whether White is in check.
	 * @return whether White is in check
	 */
	public boolean isInCheckWhite() {
					
		int kingFile = whiteKing.getFile();
		int kingRank = whiteKing.getRank();
		
		int otherKingFile = blackKing.getFile();
		int otherKingRank = blackKing.getRank();
				
		// Rook, Bishop, Queen or Pawn check
		if ( isInCheckWhiteStraightLine(kingFile, kingRank, -1, -1)
				|| isInCheckWhiteStraightLine(kingFile, kingRank, -1,  0)
				|| isInCheckWhiteStraightLine(kingFile, kingRank, -1,  1)
				|| isInCheckWhiteStraightLine(kingFile, kingRank,  0, -1)
				|| isInCheckWhiteStraightLine(kingFile, kingRank,  0,  1)
				|| isInCheckWhiteStraightLine(kingFile, kingRank,  1, -1)
				|| isInCheckWhiteStraightLine(kingFile, kingRank,  1,  0)
				|| isInCheckWhiteStraightLine(kingFile, kingRank,  1,  1) )
			
			return true;
		
		// King check (other King)
		if ( (kingFile + 0 == otherKingFile && kingRank + 1 == otherKingRank)
				|| (kingFile + 1 == otherKingFile && kingRank + 1 == otherKingRank)
				|| (kingFile + 1 == otherKingFile && kingRank + 0 == otherKingRank)
				|| (kingFile + 1 == otherKingFile && kingRank - 1 == otherKingRank)
				|| (kingFile + 0 == otherKingFile && kingRank - 1 == otherKingRank)
				|| (kingFile - 1 == otherKingFile && kingRank - 1 == otherKingRank)
				|| (kingFile - 1 == otherKingFile && kingRank + 0 == otherKingRank)
				|| (kingFile - 1 == otherKingFile && kingRank + 1 == otherKingRank) )
			
			return true;
		
		// Knight check
		if ( isInCheckByKnight(kingFile, kingRank, -1, 2, true)
				|| isInCheckByKnight(kingFile, kingRank,  1,  2, true)
				|| isInCheckByKnight(kingFile, kingRank,  2,  1, true)
				|| isInCheckByKnight(kingFile, kingRank,  2, -1, true)
				|| isInCheckByKnight(kingFile, kingRank,  1, -2, true)
				|| isInCheckByKnight(kingFile, kingRank, -1, -2, true)
				|| isInCheckByKnight(kingFile, kingRank, -2, -1, true)
				|| isInCheckByKnight(kingFile, kingRank, -2,  1, true) )
			
			return true;
		
		return false;
	}

	/**
	 * Helper method for isInCheckWhite() for straight-line checks.
	 * 
	 * @param kingFile White King's file
	 * @param kingRank White King's rank
	 * @param fileDirection direction of the file analysis - negative, zero or positive
	 * @param rankDirection direction of the rank analysis - negative, zero or positive
	 * @return true if White is in check, false if not
	 */
	private boolean isInCheckWhiteStraightLine(int kingFile, int kingRank, int fileDirection, int rankDirection) {
		
		for (int i = 1; i < 8; i++) {
			
			if (kingFile + i * fileDirection < 1
					|| kingFile + i * fileDirection > 8
					|| kingRank + i * rankDirection < 1
					|| kingRank + i * rankDirection > 8)
				
				return false;
			
			if ( getSquare(kingFile + i * fileDirection, kingRank + i * rankDirection).getPiece() == null )
				
				continue;
			
			if ( getSquare(kingFile + i * fileDirection, kingRank + i * rankDirection).getPiece() != null
					&& getSquare(kingFile + i * fileDirection, kingRank + i * rankDirection).getPiece().isWhite()
					&& !getSquare(kingFile + i * fileDirection, kingRank + i * rankDirection).getPiece().isCaptured() )
				
				return false;
			
			if ( getSquare(kingFile + i * fileDirection, kingRank + i * rankDirection).getPiece() != null
					&& !getSquare(kingFile + i * fileDirection, kingRank + i * rankDirection).getPiece().isWhite()
					&& getSquare(kingFile + i * fileDirection, kingRank + i * rankDirection).getPiece().isCaptured() )
				
				return false;
			
			if ( Math.abs(fileDirection) != Math.abs(rankDirection)
					&& getSquare(kingFile + i * fileDirection, kingRank + i * rankDirection).getPiece() != null
					&& !getSquare(kingFile + i * fileDirection, kingRank + i * rankDirection).getPiece().isWhite()
					&& !getSquare(kingFile + i * fileDirection, kingRank + i * rankDirection).getPiece().isCaptured()
					&& !(getSquare(kingFile + i * fileDirection, kingRank + i * rankDirection).getPiece() instanceof Rook
							|| getSquare(kingFile + i * fileDirection, kingRank + i * rankDirection).getPiece() instanceof Queen) )
				
				return false;
			
			if ( Math.abs(fileDirection) != Math.abs(rankDirection)
					&& (getSquare(kingFile + i * fileDirection, kingRank + i * rankDirection).getPiece() instanceof Rook
					|| getSquare(kingFile + i * fileDirection, kingRank + i * rankDirection).getPiece() instanceof Queen) )
				
				return true;
			
			if ( Math.abs(fileDirection) == Math.abs(rankDirection)
					&& getSquare(kingFile + i * fileDirection, kingRank + i * rankDirection).getPiece() != null
					&& !getSquare(kingFile + i * fileDirection, kingRank + i * rankDirection).getPiece().isWhite()
					&& !getSquare(kingFile + i * fileDirection, kingRank + i * rankDirection).getPiece().isCaptured()
					&& !(getSquare(kingFile + i * fileDirection, kingRank + i * rankDirection).getPiece() instanceof Bishop
							|| getSquare(kingFile + i * fileDirection, kingRank + i * rankDirection).getPiece() instanceof Queen
							|| (getSquare(kingFile + i * fileDirection, kingRank + i * rankDirection).getPiece() instanceof Pawn
									&& i == 1 && rankDirection == 1)) )
				
				return false;
	
			if ( Math.abs(fileDirection) == Math.abs(rankDirection)
					&& getSquare(kingFile + i * fileDirection, kingRank + i * rankDirection).getPiece() instanceof Bishop
					|| getSquare(kingFile + i * fileDirection, kingRank + i * rankDirection).getPiece() instanceof Queen
					|| (getSquare(kingFile + i * fileDirection, kingRank + i * rankDirection).getPiece() instanceof Pawn
							&& i == 1 && rankDirection == 1) )
				
				return true;
		}
		
		return false;
	}
	
	/**
	 * Returns whether White has delivered checkmate to Black.
	 * @return whether White has delivered checkmate to Black.
	 */
	public boolean isCheckmateBlack() {
						
		if ( !isInCheckBlack() )
			return false;
		
		int file;
		int rank;
				
		// Knights
		for (int i = 1; i <= 10; i++) {
			
			if ( blackKnight[i] == null || blackKnight[i].isCaptured() )
				continue;
			
			file = blackKnight[i].getFile();
			rank = blackKnight[i].getRank();
						
			getSquare(file, rank).setPiece(null);
			
			if ( isCheckmateBlackHelper(blackKnight[i], file, rank, file - 1, rank + 2) == 0
					|| isCheckmateBlackHelper(blackKnight[i], file, rank, file + 1, rank + 2) == 0
					|| isCheckmateBlackHelper(blackKnight[i], file, rank, file + 2, rank + 1) == 0
					|| isCheckmateBlackHelper(blackKnight[i], file, rank, file + 2, rank - 1) == 0
					|| isCheckmateBlackHelper(blackKnight[i], file, rank, file + 1, rank - 2) == 0
					|| isCheckmateBlackHelper(blackKnight[i], file, rank, file - 1, rank - 2) == 0
					|| isCheckmateBlackHelper(blackKnight[i], file, rank, file - 2, rank - 1) == 0
					|| isCheckmateBlackHelper(blackKnight[i], file, rank, file - 2, rank + 1) == 0 )
				
				return false;
			
			getSquare(file, rank).setPiece(blackKnight[i]);
		}
				
		// Rooks
		for (int i = 1; i <= 10; i++) {
			
			if ( blackRook[i] == null || blackRook[i].isCaptured() )
				continue;
			
			file = blackRook[i].getFile();
			rank = blackRook[i].getRank();
			
			getSquare(file, rank).setPiece(null);
	
			for (int j = 1; j < 8; j++)
	
				if ( isCheckmateBlackHelper(blackRook[i], file, rank, file + j, rank) == -1 )
					break;
				
				else if ( isCheckmateBlackHelper(blackRook[i], file, rank, file + j, rank) == 0 )
					return false;
			
			for (int j = 1; j < 8; j++)
								
				if ( isCheckmateBlackHelper(blackRook[i], file, rank, file - j, rank) == -1 )
					break;
			
				else if ( isCheckmateBlackHelper(blackRook[i], file, rank, file - j, rank) == 0 )
					return false;
			
			for (int j = 1; j < 8; j++)
				
				if ( isCheckmateBlackHelper(blackRook[i], file, rank, file, rank + j) == -1 )
					break;
			
				else if ( isCheckmateBlackHelper(blackRook[i], file, rank, file, rank + j) == 0 )
					return false;
			
			for (int j = 1; j < 8; j++)
				
				if ( isCheckmateBlackHelper(blackRook[i], file, rank, file, rank - j) == -1 )
					break;
			
				else if ( isCheckmateBlackHelper(blackRook[i], file, rank, file, rank - j) == 0 )
					return false;
			
			getSquare(file, rank).setPiece(blackRook[i]);
		}
				
		// Bishops
		for (int i = 1; i <= 10; i++) {
			
			if ( blackBishop[i] == null || blackBishop[i].isCaptured() )
				continue;
			
			file = blackBishop[i].getFile();
			rank = blackBishop[i].getRank();
			
			getSquare(file, rank).setPiece(null);
			
			for (int j = 1; j < 8; j++)
				
				if ( isCheckmateBlackHelper(blackBishop[i], file, rank, file + j, rank + j) == -1 )
					
					break;
				else if ( isCheckmateBlackHelper(blackBishop[i], file, rank, file + j, rank + j) == 0 )
					return false;
			
			for (int j = 1; j < 8; j++)
				
				if ( isCheckmateBlackHelper(blackBishop[i], file, rank, file - j, rank - j) == -1 )
					break;
			
				else if ( isCheckmateBlackHelper(blackBishop[i], file, rank, file - j, rank - j) == 0 )
					return false;
			
			for (int j = 1; j < 8; j++)
				
				if ( isCheckmateBlackHelper(blackBishop[i], file, rank, file - j, rank + j) == -1 )
					break;
			
				else if ( isCheckmateBlackHelper(blackBishop[i], file, rank, file - j, rank + j) == 0 )
					return false;
			
			for (int j = 1; j < 8; j++)
				
				if ( isCheckmateBlackHelper(blackBishop[i], file, rank, file + j, rank - j) == -1 )
					break;
				
				else if ( isCheckmateBlackHelper(blackBishop[i], file, rank, file + j, rank - j) == 0 )
					return false;
			
			getSquare(file, rank).setPiece(blackBishop[i]);
		}
				
		// Queen(s)
		for (int i = 1; i < 10; i++) {
			
			if ( blackQueen[i] == null || blackQueen[i].isCaptured() )
				continue;
			
			file = blackQueen[i].getFile();
			rank = blackQueen[i].getRank();
			
			getSquare(file, rank).setPiece(null);
	
			for (int j = 1; j < 8; j++)
				
				if ( isCheckmateBlackHelper(blackQueen[i], file, rank, file + j, rank) == -1 )
					break;
			
				else if ( isCheckmateBlackHelper(blackQueen[i], file, rank, file + j, rank) == 0 )
					return false;
			
			for (int j = 1; j < 8; j++)
				
				if ( isCheckmateBlackHelper(blackQueen[i], file, rank, file - j, rank) == -1 )
					break;
			
				else if ( isCheckmateBlackHelper(blackQueen[i], file, rank, file - j, rank) == 0 )
					return false;
			
			for (int j = 1; j < 8; j++)
				
				if ( isCheckmateBlackHelper(blackQueen[i], file, rank, file, rank + j) == -1 )
					break;
			
				else if ( isCheckmateBlackHelper(blackQueen[i], file, rank, file, rank + j) == 0 )
					return false;
			
			for (int j = 1; j < 8; j++)
				
				if ( isCheckmateBlackHelper(blackQueen[i], file, rank, file, rank - j) == -1 )
					break;
			
				else if ( isCheckmateBlackHelper(blackQueen[i], file, rank, file, rank - j) == 0 )
					return false;
			
			for (int j = 1; j < 8; j++)
				
				if ( isCheckmateBlackHelper(blackQueen[i], file, rank, file + j, rank + j) == -1 )
					break;
			
				else if ( isCheckmateBlackHelper(blackQueen[i], file, rank, file + j, rank + j) == 0 )
					return false;
			
			for (int j = 1; j < 8; j++)
				
				if ( isCheckmateBlackHelper(blackQueen[i], file, rank, file - j, rank - j) == -1 )
					break;
			
				else if ( isCheckmateBlackHelper(blackQueen[i], file, rank, file - j, rank - j) == 0 )
					return false;
			
			for (int j = 1; j < 8; j++)
				
				if ( isCheckmateBlackHelper(blackQueen[i], file, rank, file - j, rank + j) == -1 )
					break;
			
				else if ( isCheckmateBlackHelper(blackQueen[i], file, rank, file - j, rank + j) == 0 )
					return false;
			
			for (int j = 1; j < 8; j++)
				
				if ( isCheckmateBlackHelper(blackQueen[i], file, rank, file + j, rank - j) == -1 )
					break;
			
				else if ( isCheckmateBlackHelper(blackQueen[i], file, rank, file + j, rank - j) == 0 )
					return false;
			
			getSquare(file, rank).setPiece(blackQueen[i]);
		}
				
		// King
		file = blackKing.getFile();
		rank = blackKing.getRank();
		
		getSquare(file, rank).setPiece(null);
		
		if ( isCheckmateBlackHelper(blackKing, file, rank, file + 0, rank + 1) == 0
				|| isCheckmateBlackHelper(blackKing, file, rank, file + 1, rank + 1) == 0
				|| isCheckmateBlackHelper(blackKing, file, rank, file + 1, rank + 0) == 0
				|| isCheckmateBlackHelper(blackKing, file, rank, file + 1, rank - 1) == 0
				|| isCheckmateBlackHelper(blackKing, file, rank, file + 0, rank - 1) == 0
				|| isCheckmateBlackHelper(blackKing, file, rank, file - 1, rank - 1) == 0
				|| isCheckmateBlackHelper(blackKing, file, rank, file - 1, rank + 0) == 0
				|| isCheckmateBlackHelper(blackKing, file, rank, file - 1, rank + 1) == 0 )
			
			return false;

		getSquare(file, rank).setPiece(blackKing);
		
		// Pawn
		for (int i = 1; i <= 8; i++) {
			
			if ( blackPawn[i] == null || blackPawn[i].isCaptured() )
				continue;
			
			file = blackPawn[i].getFile();
			rank = blackPawn[i].getRank();
	
			getSquare(file, rank).setPiece(null);
	
			if ( isCheckmateBlackHelper(blackPawn[i], file, rank, file, rank - 1) == 0
					|| isCheckmateBlackHelper(blackPawn[i], file, rank, file - 1, rank - 1) == 0	
					|| isCheckmateBlackHelper(blackPawn[i], file, rank, file + 1, rank - 1) == 0
					|| isCheckmateBlackHelper(blackPawn[i], file, rank, file, rank - 2) == 0 && blackPawn[i].isFirstMove() ) {
				
				return false;
			}
			
			getSquare(file, rank).setPiece(blackPawn[i]);
		}
				
		return true;
	}
	
	/**
	 * Helper method for isCheckmateBlack().
	 * 
	 * @param p Piece to analyze
	 * @param oldFile p's current file
	 * @param oldRank p's current rank
	 * @param file p's potential file
	 * @param rank p's potential rank
	 * @return -1 for a dead end (stop this analysis line),
	 * 			0 if the move avoids checkmate
	 * 			1 if this move does not avoid checkmate
	 */
	private int isCheckmateBlackHelper(Piece p, int oldFile, int oldRank, int file, int rank) {
		
		int deadEnd = -1;
		int avoidsCheckmate = 0;
		int doesNotAvoidCheckmate = 1;
				
		if (file < 1 || file > 8 || rank < 1 || rank > 8)
			return deadEnd;
		
		if (p instanceof Pawn) {
			
			if ( file == oldFile && rank == oldRank - 2
					&& (getSquare(oldFile, oldRank - 1).getPiece() != null
					|| getSquare(oldFile, oldRank - 2).getPiece() != null) )
				return doesNotAvoidCheckmate;
			
			if ( file == oldFile && rank == oldRank - 1
					&& getSquare(oldFile, oldRank - 1).getPiece() != null )
				return doesNotAvoidCheckmate;
			
			if ( Math.abs(file - oldFile) == 1 && rank == oldRank - 1
					&& getSquare(file, rank).getPiece() == null )
				return doesNotAvoidCheckmate;
		}
		
		if ( getSquare(file, rank).getPiece() == null ) {
			
			p.moveTo(file, rank);
			getSquare(oldFile, oldRank).setPiece(null);
			getSquare(file, rank).setPiece(p);
			
				if ( !isInCheckBlack() ) {
					p.moveTo(oldFile, oldRank);
					getSquare(oldFile, oldRank).setPiece(p);
					getSquare(file, rank).setPiece(null);
					return avoidsCheckmate;
				}
				
			p.moveTo(oldFile, oldRank);
			getSquare(oldFile, oldRank).setPiece(p);
			getSquare(file, rank).setPiece(null);
		}
	
		else if ( getSquare(file, rank).getPiece().isWhite() ) {
			
			p.moveTo(file, rank);
			getSquare(oldFile, oldRank).setPiece(null);
			getSquare(file, rank).getPiece().setCaptured(true, p);
			
				if ( !isInCheckBlack() ) {
					p.moveTo(oldFile, oldRank);
					getSquare(oldFile, oldRank).setPiece(p);
					getSquare(file, rank).getPiece().setCaptured(false, null);
					return avoidsCheckmate;
				}
				
			p.moveTo(oldFile, oldRank);
			getSquare(oldFile, oldRank).setPiece(p);
			getSquare(file, rank).getPiece().setCaptured(false, null);
			return deadEnd;
		}
		
		else if ( !getSquare(file, rank).getPiece().isWhite() )
			return deadEnd;
		
		return doesNotAvoidCheckmate;
	}
	
	/**
	 * Returns whether Black is in check.
	 * @return whether Black is in check
	 */
	public boolean isInCheckBlack() {
				
		int kingFile = blackKing.getFile();
		int kingRank = blackKing.getRank();
		
		int otherKingFile = whiteKing.getFile();
		int otherKingRank = whiteKing.getRank();
				
		// Rook, Bishop, Queen or Pawn check
		if ( isInCheckBlackStraightLine(kingFile, kingRank, -1, -1)
				|| isInCheckBlackStraightLine(kingFile, kingRank, -1,  0)
				|| isInCheckBlackStraightLine(kingFile, kingRank, -1,  1)
				|| isInCheckBlackStraightLine(kingFile, kingRank,  0, -1)
				|| isInCheckBlackStraightLine(kingFile, kingRank,  0,  1)
				|| isInCheckBlackStraightLine(kingFile, kingRank,  1, -1)
				|| isInCheckBlackStraightLine(kingFile, kingRank,  1,  0)
				|| isInCheckBlackStraightLine(kingFile, kingRank,  1,  1) )
			
			return true;
		
		// King check (other King)
		if ( (kingFile + 0 == otherKingFile && kingRank + 1 == otherKingRank)
				|| (kingFile + 1 == otherKingFile && kingRank + 1 == otherKingRank)
				|| (kingFile + 1 == otherKingFile && kingRank + 0 == otherKingRank)
				|| (kingFile + 1 == otherKingFile && kingRank - 1 == otherKingRank)
				|| (kingFile + 0 == otherKingFile && kingRank - 1 == otherKingRank)
				|| (kingFile - 1 == otherKingFile && kingRank - 1 == otherKingRank)
				|| (kingFile - 1 == otherKingFile && kingRank + 0 == otherKingRank)
				|| (kingFile - 1 == otherKingFile && kingRank + 1 == otherKingRank) )
			
			return true;
		
		// Knight check
		if ( isInCheckByKnight(kingFile, kingRank, -1, 2, false)
				|| isInCheckByKnight(kingFile, kingRank,  1,  2, false)
				|| isInCheckByKnight(kingFile, kingRank,  2,  1, false)
				|| isInCheckByKnight(kingFile, kingRank,  2, -1, false)
				|| isInCheckByKnight(kingFile, kingRank,  1, -2, false)
				|| isInCheckByKnight(kingFile, kingRank, -1, -2, false)
				|| isInCheckByKnight(kingFile, kingRank, -2, -1, false)
				|| isInCheckByKnight(kingFile, kingRank, -2,  1, false) )
			
			return true;
		
		return false;
	}
	
	/**
	 * Helper method for isInCheckBlack() for straight-line checks.
	 * 
	 * @param kingFile Black King's file
	 * @param kingRank Black King's rank
	 * @param fileDirection direction of the file analysis - negative, zero or positive
	 * @param rankDirection direction of the rank analysis - negative, zero or positive
	 * @return true if Black is in check, false if not
	 */
	private boolean isInCheckBlackStraightLine(int kingFile, int kingRank, int fileDirection, int rankDirection) {
		
		for (int i = 1; i < 8; i++) {
			
			if (kingFile + i * fileDirection < 1
					|| kingFile + i * fileDirection > 8
					|| kingRank + i * rankDirection < 1
					|| kingRank + i * rankDirection > 8)
				
				return false;
			
			if ( getSquare(kingFile + i * fileDirection, kingRank + i * rankDirection).getPiece() == null )
				
				continue;
			
			if ( getSquare(kingFile + i * fileDirection, kingRank + i * rankDirection).getPiece() != null
					&& !getSquare(kingFile + i * fileDirection, kingRank + i * rankDirection).getPiece().isWhite()
					&& !getSquare(kingFile + i * fileDirection, kingRank + i * rankDirection).getPiece().isCaptured() )
				
				return false;
			
			if ( getSquare(kingFile + i * fileDirection, kingRank + i * rankDirection).getPiece() != null
					&& getSquare(kingFile + i * fileDirection, kingRank + i * rankDirection).getPiece().isWhite()
					&& getSquare(kingFile + i * fileDirection, kingRank + i * rankDirection).getPiece().isCaptured() )
				
				return false;
			
			if ( Math.abs(fileDirection) != Math.abs(rankDirection)
					&& getSquare(kingFile + i * fileDirection, kingRank + i * rankDirection).getPiece() != null
					&& getSquare(kingFile + i * fileDirection, kingRank + i * rankDirection).getPiece().isWhite()
					&& !getSquare(kingFile + i * fileDirection, kingRank + i * rankDirection).getPiece().isCaptured()
					&& !(getSquare(kingFile + i * fileDirection, kingRank + i * rankDirection).getPiece() instanceof Rook
							|| getSquare(kingFile + i * fileDirection, kingRank + i * rankDirection).getPiece() instanceof Queen) )
				
				return false;
			
			if ( Math.abs(fileDirection) != Math.abs(rankDirection)
					&& (getSquare(kingFile + i * fileDirection, kingRank + i * rankDirection).getPiece() instanceof Rook
					|| getSquare(kingFile + i * fileDirection, kingRank + i * rankDirection).getPiece() instanceof Queen) )
				
				return true;
			
			if ( Math.abs(fileDirection) == Math.abs(rankDirection)
					&& getSquare(kingFile + i * fileDirection, kingRank + i * rankDirection).getPiece() != null
					&& getSquare(kingFile + i * fileDirection, kingRank + i * rankDirection).getPiece().isWhite()
					&& !getSquare(kingFile + i * fileDirection, kingRank + i * rankDirection).getPiece().isCaptured()
					&& !(getSquare(kingFile + i * fileDirection, kingRank + i * rankDirection).getPiece() instanceof Bishop
							|| getSquare(kingFile + i * fileDirection, kingRank + i * rankDirection).getPiece() instanceof Queen
							|| (getSquare(kingFile + i * fileDirection, kingRank + i * rankDirection).getPiece() instanceof Pawn
									&& i == 1 && rankDirection == -1)) )
				
				return false;
	
			if ( Math.abs(fileDirection) == Math.abs(rankDirection)
					&& getSquare(kingFile + i * fileDirection, kingRank + i * rankDirection).getPiece() instanceof Bishop
					|| getSquare(kingFile + i * fileDirection, kingRank + i * rankDirection).getPiece() instanceof Queen
					|| (getSquare(kingFile + i * fileDirection, kingRank + i * rankDirection).getPiece() instanceof Pawn
							&& i == 1 && rankDirection == -1) )
				
				return true;
		}
		
		return false;
	}
	
	/**
	 * Helper method by isInCheckWhite() AND isInCheckBlack() for Knight checks.
	 * 
	 * @param kingFile White / Black King's file
	 * @param kingRank White / Black King's rank
	 * @param fileDiff file differential
	 * @param rankDiff rank differential
	 * @param whiteKing boolean for White / Black King distinction
	 * @return true if in check, false if not
	 */
	private boolean isInCheckByKnight(int kingFile, int kingRank, int fileDiff, int rankDiff, boolean whiteKing) {
		
		if ( kingFile + fileDiff < 1 || kingFile + fileDiff > 8 || kingRank + rankDiff < 1 || kingRank + rankDiff > 8 )
			return false;
		
		if (!whiteKing) {
			
			if ( getSquare(kingFile + fileDiff, kingRank + rankDiff).getPiece() != null
					&& ((getSquare(kingFile + fileDiff, kingRank + rankDiff).getPiece().isWhite()
							&& getSquare(kingFile + fileDiff, kingRank + rankDiff).getPiece() instanceof Knight
							&& !getSquare(kingFile + fileDiff, kingRank + rankDiff).getPiece().isCaptured())
							|| (!getSquare(kingFile + fileDiff, kingRank + rankDiff).getPiece().isWhite()
									&& getSquare(kingFile + fileDiff, kingRank + rankDiff).getPiece().isCaptured()
									&& getSquare(kingFile + fileDiff, kingRank + rankDiff).getPiece().getAttackingPiece() instanceof Knight)) )
				return true;
		}
		
		else if (whiteKing) {
			
			if ( getSquare(kingFile + fileDiff, kingRank + rankDiff).getPiece() != null
					&& ((!getSquare(kingFile + fileDiff, kingRank + rankDiff).getPiece().isWhite()
							&& getSquare(kingFile + fileDiff, kingRank + rankDiff).getPiece() instanceof Knight
							&& !getSquare(kingFile + fileDiff, kingRank + rankDiff).getPiece().isCaptured())
							|| (getSquare(kingFile + fileDiff, kingRank + rankDiff).getPiece().isWhite()
									&& getSquare(kingFile + fileDiff, kingRank + rankDiff).getPiece().isCaptured()
									&& getSquare(kingFile + fileDiff, kingRank + rankDiff).getPiece().getAttackingPiece() instanceof Knight)) )
				return true;
		}
		
		return false;
	}
	
	/**
	 * Returns whether it is White's turn.
	 * @return whether it is White's turn
	 */

	public boolean isWhitesTurn() {
		return whitesTurn;
	}
	
	/**
	 * Returns Piece that is attempting to move.
	 * @return Piece that is attempting to move
	 */

	public String getPieceToMove() {
		return pieceToMove;
	}
	
	/**
	 * Sets Piece that is attempting to move.
	 * 
	 * @param pieceToMove String representation of Piece type
	 * @param pieceNumberToMove Piece number
	 */

	public void setPieceToMove(String pieceToMove, int pieceNumberToMove) {
		this.pieceToMove = pieceToMove;
		this.pieceNumberToMove = pieceNumberToMove;
	}
	
	/**
	 * Returns Piece that is attempting to move's number.
	 * @return Piece that is attempting to move's number
	 */
	
	public int getPieceNumberToMove() {
		return pieceNumberToMove;
	}
	
	/**
	 * Sets White's turn to true or false.
	 * @param isWhitesTurn true if White's turn, false if Black's turn
	 */

	private void setWhitesTurn(boolean isWhitesTurn) {
		this.whitesTurn = isWhitesTurn;
	}
}
