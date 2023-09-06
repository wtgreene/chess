
	// POTENTIAL FEATURES
    // possible moves highlighted
    // previous move / undo

	// TODO button placement / error handling for button placement
	// TODO board shifting

package chess.ui;

import javax.swing.*;
//import javax.swing.border.Border;
//import javax.swing.border.EtchedBorder;
//import javax.swing.border.TitledBorder;

import chess.manager.ChessManager;

import java.awt.*;
import java.awt.event.*;

/**
 * "Play Chess" GUI.
 * 
 * @author Will Greene
 */
public class ChessGUI extends JFrame implements ActionListener {
	
	/** serial version UID */
    private static final long serialVersionUID = 1L;
	
    /** GUI width */
    public static final int WIDTH = 700;
    /** GUI height */
    public static final int HEIGHT = 640;
    /** GUI x coordinate of upper left-hand corner */
    public static final int X = 100;
    /** GUI y coordinate of upper left-hand corner */
    public static final int Y = 100;
    /** GUI text size */
    public static final int FONT_SIZE = 15;
    /** GUI text width */
    public static final int TEXT_WIDTH = 10;
    /** GUI piece width */
    private static final int PIECE_WIDTH  = 50;
    /** GUI piece height */
    private static final int PIECE_HEIGHT = 50;
    /** GUI container */
    private Container cont;
    /** GUI setup */
    private JPanel setup;
    /** GUI constraints */
    private GridBagConstraints c;
        
    /** White rook (a file) button */
    private JButton whiteRook[];            // alternative text after implementing pictures
    /** White knight (b file) button */
    private JButton whiteKnight[];
    /** White bishop (c file) button */
    private JButton whiteBishop[];
    /** White queen button */
    private JButton whiteQueen[];
    /** White king button */
    private JButton whiteKing;
    /** White pawn button */
    private JButton whitePawn[];
    
    /** Black rook (a file) button */
    private JButton blackRook[];
    /** Black knight (b file) button */
    private JButton blackKnight[];
    /** Black bishop (c file) button */
    private JButton blackBishop[];
    /** Black queen button */
    private JButton blackQueen[];
    /** Black king button */
    private JButton blackKing;
    /** Black pawn button */
    private JButton blackPawn[];
    
    /** unoccupied square array */
    private JButton[][] emptySquare;
    
    /** chess piece picture */
    private ImageIcon piece;
    /** chess piece 2 picture */
    private ImageIcon piece2;
    
    /** indicates whether a piece is ready to move (true) or needs to be selected (false) */
    private boolean isMoving;
    /** indicates whether a Piece is moving to an empty square */
    private boolean isMovingToEmptySquare;
    
    /** current file of selected Piece */
    private int oldFile;
    /** current rank of selected Piece */
    private int oldRank;
    
    /** ChessManager instance */
    private ChessManager manager;
        
    /**
     * Creates instance of ChessGUI class.
     */
    private ChessGUI() {

        setDefaultCloseOperation( EXIT_ON_CLOSE );
        setSize( WIDTH, HEIGHT );
        setLocation( X, Y );
        setTitle( "Chess (2 Player)" );
        
        createGUI();
        
		JOptionPane.showMessageDialog( this, "Instructions:" + "\n" + "This is 2 player chess." + "\n" + "Select \"New Game\" to restart.", "", JOptionPane.ERROR_MESSAGE );
    }
    
    /**
     * Creates the GUI.
     */
    private void createGUI() {
    	
        cont = getContentPane();
        setup = new JPanel( new GridBagLayout() );
        c = new GridBagConstraints();
        
        initiateSquares();
        initiateWhitePieces();
        initiateBlackPieces();
        
        cont.add(setup);
        setVisible(true);
        
        isMoving = false;
        
        manager = new ChessManager();
    }
    
    /**
	 * Executes an action based on an event (mouse click).
	 * @param e event (mouse click)
	 */
    public void actionPerformed(ActionEvent e) {
    	
    	if ( e.getSource() == emptySquare[0][0] ) {
    		
    		for (int i = 1; i <= 8; i++)
    			for (int j = 1; j <= 8; j++)
    				emptySquare[i][j].setVisible(false);
    		
    		for (int i = 1; i <= 8; i++) {
    			whitePawn[i].setVisible(false);
    			blackPawn[i].setVisible(false);
    		}
    		
    		for (int i = 1; i <= 9; i++) {
    			whiteQueen[i].setVisible(false);
    			blackQueen[i].setVisible(false);
    		}
    		
    		for (int i = 1; i <= 10; i++) {
    			whiteBishop[i].setVisible(false);
    			blackBishop[i].setVisible(false);
    		}
    		
    		for (int i = 1; i <= 10; i++) {
    			whiteKnight[i].setVisible(false);
    			blackKnight[i].setVisible(false);
    		}
    		
    		for (int i = 1; i <= 10; i++) {
    			whiteRook[i].setVisible(false);
    			blackRook[i].setVisible(false);
    		}
    		
    		whiteKing.setVisible(false);
    		blackKing.setVisible(false);
    		
    		emptySquare[0][0].setVisible(false);
    		    		
    		createGUI();
    		
			JOptionPane.showMessageDialog( this, "Board has been reset.", "New Game", JOptionPane.ERROR_MESSAGE );
    	}
    	
    	else if ( !isMoving() ) {

    		determinePieceSelected(e);
    		oldFile = getFile(e);
    		oldRank = getRank(e);
    	}

    	else if ( isMoving() ) {

    		if ( manager.isWhitesTurn() ) {

    			for (int i = 1; i <= 10; i++)
    				if ( manager.getPieceToMove().equals("Rook") && manager.getPieceNumberToMove() == i )
    					whiteRook[i] = move(e, whiteRook[i], "white_rook", oldFile, oldRank);
    			for (int i = 1; i <= 10; i++)
    				if ( manager.getPieceToMove().equals("Knight") && manager.getPieceNumberToMove() == i )
    					whiteKnight[i] = move(e, whiteKnight[i], "white_knight", oldFile, oldRank);
    			for (int i = 1; i <= 10; i++)
    				if ( manager.getPieceToMove().equals("Bishop") && manager.getPieceNumberToMove() == i )
    					whiteBishop[i] = move(e, whiteBishop[i], "white_bishop", oldFile, oldRank);
    			for (int i = 1; i <= 9; i++)
    				if ( manager.getPieceToMove().equals("Queen") && manager.getPieceNumberToMove() == i )
    					whiteQueen[i] = move(e, whiteQueen[i], "white_queen", oldFile, oldRank);

    			for (int i = 1; i <= 8; i++) { //TODO for Black?

    				if ( manager.getPieceToMove().equals("Pawn") && manager.getPieceNumberToMove() == i && getRank(e) == 8 ) {
    					int numb = manager.numWhiteQueens + 1;
    					whiteQueen[numb] = move(e, whitePawn[i], "white_pawn", oldFile, oldRank); // TODO is the numb variable necessary here?
    				}

    				else
    					if ( manager.getPieceToMove().equals("Pawn") && manager.getPieceNumberToMove() == i )
    						whitePawn[i] = move(e, whitePawn[i], "white_pawn", oldFile, oldRank);
    			}

    			if ( manager.getPieceToMove().equals("King") )
    				whiteKing = move(e, whiteKing, "white_king", oldFile, oldRank);

    			if ( manager.isCheckmateBlack() )
    				JOptionPane.showMessageDialog( this, "Checkmate - White wins.", "", JOptionPane.ERROR_MESSAGE );
    		}

    		else if ( !manager.isWhitesTurn() ) {

    			for (int i = 1; i <= 10; i++)
    				if ( manager.getPieceToMove().equals("Rook") && manager.getPieceNumberToMove() == i )
    					blackRook[i] = move(e, blackRook[i], "black_rook", oldFile, oldRank);
    			for (int i = 1; i <= 10; i++)
    				if ( manager.getPieceToMove().equals("Knight") && manager.getPieceNumberToMove() == i )
    					blackKnight[i] = move(e, blackKnight[i], "black_knight", oldFile, oldRank);
    			for (int i = 1; i <= 10; i++)
    				if ( manager.getPieceToMove().equals("Bishop") && manager.getPieceNumberToMove() == i )
    					blackBishop[i] = move(e, blackBishop[i], "black_bishop", oldFile, oldRank);
    			for (int i = 1; i <= 9; i++)
    				if ( manager.getPieceToMove().equals("Queen") && manager.getPieceNumberToMove() == i )
    					blackQueen[i] = move(e, blackQueen[i], "black_queen", oldFile, oldRank);
    			for (int i = 1; i <= 8; i++)
    				if ( manager.getPieceToMove().equals("Pawn") && manager.getPieceNumberToMove() == i )
    					blackPawn[i] = move(e, blackPawn[i], "black_pawn", oldFile, oldRank);

    			if ( manager.getPieceToMove().equals("King") )
    				blackKing = move(e, blackKing, "black_king", oldFile, oldRank);

    			if ( manager.isCheckmateWhite() )
    				JOptionPane.showMessageDialog( this, "Checkmate - Black wins.", "", JOptionPane.ERROR_MESSAGE );
    		}

    		if ( !manager.isCheckmateWhite() && manager.isInCheckWhite() )
    			JOptionPane.showMessageDialog( this, "Check to White.", "", JOptionPane.ERROR_MESSAGE );

    		if ( !manager.isCheckmateBlack() && manager.isInCheckBlack() )
    			JOptionPane.showMessageDialog( this, "Check to Black.", "", JOptionPane.ERROR_MESSAGE );

    		if ( manager.getNumPieces() == 2 )
    			JOptionPane.showMessageDialog( this, "Draw by insufficient material.", "", JOptionPane.ERROR_MESSAGE );
    	}
    }
    
    /**
     * Determines what Piece has been selected to move
     * @param e event (mouse click)
     */
    private void determinePieceSelected(ActionEvent e) {
    	
    	boolean found = false;
		
		if ( manager.isWhitesTurn() ) {
			
			for (int i = 1; i <= 8; i++) {
				if ( e.getSource() == whitePawn[i] ) {
					manager.setPieceToMove("Pawn", i);
					found = true;
				}
			}
			
			for (int i = 1; i <= 10; i++) {
				if ( e.getSource() == whiteRook[i] ) {
					manager.setPieceToMove("Rook", i);
					found = true;
				}
			}
			
			for (int i = 1; i <= 10; i++) {
				if ( e.getSource() == whiteKnight[i] ) {
					manager.setPieceToMove("Knight", i);
					found = true;
				}
			}
			
			for (int i = 1; i <= 10; i++) {
				if ( e.getSource() == whiteBishop[i] ) {
					manager.setPieceToMove("Bishop", i);
					found = true;
				}
			}
			
			for (int i = 1; i <= 9; i++) {
				if ( e.getSource() == whiteQueen[i] ) {
					manager.setPieceToMove("Queen", i);
					found = true;
				}
			}
	
			if ( e.getSource() == whiteKing ) {
				manager.setPieceToMove("King", 0);
				found = true;
			}
			
			// Error - empty square selection
			if ( isEmptySquare(e) )
				JOptionPane.showMessageDialog( this, "Please select a piece.", "Oops!", JOptionPane.ERROR_MESSAGE );
	
			// Error - incorrect color
			else if (!found)
				JOptionPane.showMessageDialog( this, "Incorrect color, White.", "Oops!", JOptionPane.ERROR_MESSAGE );
			
			if (found)
				setMoving();
		}
	
		else if ( !manager.isWhitesTurn() ) {
			
			for (int i = 1; i <= 8; i++) {
				if ( e.getSource() == blackPawn[i] ) {
					manager.setPieceToMove("Pawn", i);
					found = true;
				}
			}
			
			for (int i = 1; i <= 10; i++) {
				if ( e.getSource() == blackRook[i] ) {
					manager.setPieceToMove("Rook", i);
					found = true;
				}
			}
			
			for (int i = 1; i <= 10; i++) {
				if ( e.getSource() == blackKnight[i] ) {
					manager.setPieceToMove("Knight", i);
					found = true;
				}
			}
			
			for (int i = 1; i <= 10; i++) {
				if ( e.getSource() == blackBishop[i] ) {
					manager.setPieceToMove("Bishop", i);
					found = true;
				}
			}
			
			for (int i = 1; i <= 9; i++) {
				if ( e.getSource() == blackQueen[i] ) {
					manager.setPieceToMove("Queen", i);
					found = true;
				}
			}
	
			if ( e.getSource() == blackKing ) {
				manager.setPieceToMove("King", 0);
				found = true;
			}
	
			// Error - empty square selection
			if ( isEmptySquare(e) )
				JOptionPane.showMessageDialog( this, "Please select a piece.", "Oops!", JOptionPane.ERROR_MESSAGE );
	
			// Error - incorrect color
			else if (!found)
				JOptionPane.showMessageDialog( this, "Incorrect color, Black.", "Oops!", JOptionPane.ERROR_MESSAGE );
			
			if (found)
				setMoving();
		}
	}
    
    /**
     * Returns the file of a clicked square.
     * 
     * @param e event(mouse click)
     * @return the file of a clicked square
     */
    private int getFile(ActionEvent e) {
    	    	
		for (int i = 1; i <= 10; i++)
			if ( e.getSource() == whiteRook[i] )
				return manager.whiteRook[i].getFile();
		for (int i = 1; i <= 10; i++)
			if ( e.getSource() == whiteKnight[i] )
				return manager.whiteKnight[i].getFile();
		for (int i = 1; i <= 10; i++)
			if ( e.getSource() == whiteBishop[i] )
				return manager.whiteBishop[i].getFile();
		for (int i = 1; i <= 9; i++)
			if ( e.getSource() == whiteQueen[i] )
				return manager.whiteQueen[i].getFile();
		for (int i = 1; i <= 8; i++)
			if ( e.getSource() == whitePawn[i] )
				return manager.whitePawn[i].getFile();

		if ( e.getSource() == whiteKing )
			return manager.whiteKing.getFile();
		
		for (int i = 1; i <= 10; i++)
			if ( e.getSource() == blackRook[i] )
				return manager.blackRook[i].getFile();
		for (int i = 1; i <= 10; i++)
			if ( e.getSource() == blackKnight[i] )
				return manager.blackKnight[i].getFile();
		for (int i = 1; i <= 10; i++)
			if ( e.getSource() == blackBishop[i] )
				return manager.blackBishop[i].getFile();
		for (int i = 1; i <= 9; i++)
			if ( e.getSource() == blackQueen[i] )
				return manager.blackQueen[i].getFile();
		for (int i = 1; i <= 8; i++)
			if ( e.getSource() == blackPawn[i] )
				return manager.blackPawn[i].getFile();

		if ( e.getSource() == blackKing )
			return manager.blackKing.getFile();
		
		return 0;
    }
    
    /**
     * Returns the rank of a clicked square.
     * 
     * @param e event(mouse click)
     * @return the rank of a clicked square
     */
    private int getRank(ActionEvent e) {
    	
		// White
		for (int i = 1; i <= 10; i++)
			if ( e.getSource() == whiteRook[i] )
				return manager.whiteRook[i].getRank();
		for (int i = 1; i <= 10; i++)
			if ( e.getSource() == whiteKnight[i] )
				return manager.whiteKnight[i].getRank();
		for (int i = 1; i <= 10; i++)
			if ( e.getSource() == whiteBishop[i] )
				return manager.whiteBishop[i].getRank();
		for (int i = 1; i <= 9; i++)
			if ( e.getSource() == whiteQueen[i] )
				return manager.whiteQueen[i].getRank();
		
		for (int i = 1; i <= 8; i++)
			if ( e.getSource() == whitePawn[i] )
				return manager.whitePawn[i].getRank();
		
		if ( e.getSource() == whiteKing )
			return manager.whiteKing.getRank();
		
		// Black
		for (int i = 1; i <= 10; i++)
			if ( e.getSource() == blackRook[i] )
				return manager.blackRook[i].getRank();
		for (int i = 1; i <= 10; i++)
			if ( e.getSource() == blackKnight[i] )
				return manager.blackKnight[i].getRank();
		for (int i = 1; i <= 10; i++)
			if ( e.getSource() == blackBishop[i] )
				return manager.blackBishop[i].getRank();
		for (int i = 1; i <= 9; i++)
			if ( e.getSource() == blackQueen[i] )
				return manager.blackQueen[i].getRank();
		
		for (int i = 1; i <= 8; i++)
			if ( e.getSource() == blackPawn[i] )
				return manager.blackPawn[i].getRank();
		
		if ( e.getSource() == blackKing )
			return manager.blackKing.getRank();
		
		return 0;
    }

	/**
	 * Initiates the location of a JButton.
	 * 
	 * @param square JButton
	 * @param file JButton file
	 * @param rank JButton rank
	 */
	private void initiateLocation( JButton square, int file, int rank ) {
		
		c.gridx = file;
		c.gridy = 9 - rank; // TODO change to 11?
		c.fill = GridBagConstraints.BOTH;
		
		if ( (file != 0 && rank != 9) ) {
			square.setOpaque(true);
			square.setBorderPainted(false);
		}
		
    	if (file == 3 && rank == 10)
    		;
    	else if ( (file + rank) % 2 == 0 )
			square.setBackground(Color.gray);
		else
			square.setBackground(Color.white);
		
		square.addActionListener(this);
					
		setup.add(square, c);
	}

	/**
	 * 
	 * @param pieceName
	 * @return
	 */
	private ImageIcon initiatePiece(String pieceName) {
		
	    ImageIcon image = new ImageIcon( "pieces/" + pieceName + ".png" ); //TODO button.setIcon(...)
		
//		return new ImageIcon( "pieces/" + pieceName + ".png" );
	    
	    Image oldPiece = image.getImage();
	    Image newPiece = oldPiece.getScaledInstance( PIECE_WIDTH, PIECE_HEIGHT, Image.SCALE_SMOOTH );
	    
	    return new ImageIcon(newPiece);
	}
	
//	private ImageIcon initiateEmpty(String pieceName) {
//		
////	    ImageIcon image = new ImageIcon( "pieces/" + pieceName + ".png" );
////	    
////	    Image oldPiece = image.getImage();
////	    Image newPiece = oldPiece.getScaledInstance( 50, 50, Image.SCALE_SMOOTH );
////	    
////	    return new ImageIcon(newPiece);
//	}

	private void initiateSquares() {
		
	    emptySquare = new JButton[9][9];
	    
	    piece = initiatePiece("empty_square_gray");
	    piece2 = initiatePiece("empty_square_white");
	    
	    for (int i = 1; i <= 8; i++) {
	    	
	    	for (int j = 1; j <= 8; j++) {
	    		
	    		if ( (i + j) % 2 == 0 )
	    			emptySquare[i][j] = new JButton(piece);
	    		
	    		else
	    			emptySquare[i][j] = new JButton(piece2);
	    		
//	    		emptySquare[i][j].setSize(50, 50);
	    		
//	    		emptySquare[i][j] = new JButton();
//	    		emptySquare[i][j].setBounds(1, 1, 50, 50); //TODO will this solve color issue?
//	    		emptySquare[i][j].setSize(100, 100);
//	    		emptySquare[i][j].setOpaque(true);
//	    		emptySquare[i][j].setBorderPainted(false);
	    		
//	    		if ( (i + j) % 2 == 0 )
//	    			emptySquare[i][j].setBackground(Color.black);
//	    		else
//	    			emptySquare[i][j].setBackground(Color.white);

	    		initiateLocation(emptySquare[i][j], i, j);
	    		
	    		if (j < 3 || j > 6)
	    			emptySquare[i][j].setVisible(false);
	    	}
	    }
	    
	    emptySquare[0][0] = new JButton("New Game");
	    initiateLocation(emptySquare[0][0], 0, 9);
	}
	
	private void initiateWhitePieces() {
		
		// declare all pieces
		whiteRook = new JButton[11];
		whiteKnight = new JButton[11];
		whiteBishop = new JButton[11];
		whiteQueen = new JButton[10];
		whitePawn = new JButton[9];
		
	    // Rook(s) Setup
	    piece = initiatePiece( "white_rook" );
	    
//	    for (int i = 1; i <= 10; i++) {
//	    	whiteRook[i] = new JButton(piece);
////	    	whiteRook[i].setIcon(piece);
////	    	whiteRook[i].setOpaque(true);
////	    	whiteRook[i].setBorderPainted(false);
//	    }
	    
	    for (int i = 1; i <= 10; i++)
	    	whiteRook[i] = new JButton(piece);
	    
	    initiateLocation( whiteRook[1], 1, 1 );
	    initiateLocation( whiteRook[2], 8, 1 );
	    
	    // Knight(s) Setup
	    piece = initiatePiece( "white_knight" );
	    
	    for (int i = 1; i <= 10; i++)
	    	whiteKnight[i] = new JButton(piece);
	    
	    initiateLocation( whiteKnight[1], 2, 1 );
	    initiateLocation( whiteKnight[2], 7, 1 );
	    
	    // Bishop(s) Setup
	    piece = initiatePiece( "white_bishop" );
	    
	    for (int i = 1; i <= 10; i++)
	    	whiteBishop[i] = new JButton(piece);
	    
	    initiateLocation( whiteBishop[1], 3, 1 );
	    initiateLocation( whiteBishop[2], 6, 1 );
	    
	    // Queen Setup
	    piece = initiatePiece( "white_queen" );
	    
	    for (int i = 1; i <= 9; i++)
	    	whiteQueen[i] = new JButton(piece);
	    
	    initiateLocation( whiteQueen[1], 4, 1 );
	    
	    // King Setup
	    piece = initiatePiece( "white_king" );
	    whiteKing = new JButton( piece );
	    initiateLocation( whiteKing, 5, 1 );
	    
	    // Pawn(s) Setup
	    piece = initiatePiece( "white_pawn" );
	    
	    for (int i = 1; i <= 8; i++) {
	    	whitePawn[i] = new JButton(piece);
	    	initiateLocation( whitePawn[i], i, 2 );
	    }
	}
	
	private void initiateBlackPieces() {
		
		// declare all pieces
		blackRook = new JButton[11];
		blackKnight = new JButton[11];
		blackBishop = new JButton[11];
		blackQueen = new JButton[10];
		blackPawn = new JButton[9];
		
	    // Rook(s) Setup
	    piece = initiatePiece( "black_rook" );
	    
	    for (int i = 1; i <= 10; i++)
	    	blackRook[i] = new JButton(piece);

	    initiateLocation( blackRook[1], 1, 8 );
	    initiateLocation( blackRook[2], 8, 8 );
	    
	    // Knight(s) Setup
	    piece = initiatePiece( "black_knight" );
	    
	    for (int i = 1; i <= 10; i++)
	    	blackKnight[i] = new JButton(piece);
	    
	    initiateLocation( blackKnight[1], 2, 8 );
	    initiateLocation( blackKnight[2], 7, 8 );
	    
	    // Bishop(s) Setup
	    piece = initiatePiece( "black_bishop" );
	    
	    for (int i = 1; i <= 10; i++)
	    	blackBishop[i] = new JButton(piece);
	    
	    initiateLocation( blackBishop[1], 3, 8 );
	    initiateLocation( blackBishop[2], 6, 8 );
	    
	    // Queen Setup
	    piece = initiatePiece( "black_queen" );
	    
	    for (int i = 1; i <= 9; i++)
	    	blackQueen[i] = new JButton(piece);
	    
	    initiateLocation( blackQueen[1], 4, 8 );
	    
	    // King Setup
	    piece = initiatePiece( "black_king" );
	    blackKing = new JButton( piece );
	    initiateLocation( blackKing, 5, 8 );
	    
	    // Pawn(s) Setup
	    piece = initiatePiece( "black_pawn" );
	    
	    for (int i = 1; i <= 8; i++) {
	    	blackPawn[i] = new JButton(piece);
	    	initiateLocation( blackPawn[i], i, 7 );
	    }
	}

	private boolean isEmptySquare(ActionEvent e) {
		
		for (int i = 1; i <= 8; i++)
			for (int j = 1; j <= 8; j++)
				if ( e.getSource() == emptySquare[i][j] )
					return true;
		
		return false;
	}

	/**
	 * 
	 * @return
	 */
	private boolean isMoving() {
		return isMoving;
	}

	private boolean isMovingToEmptySquare() {
		return isMovingToEmptySquare;
	}

	private JButton move(ActionEvent e, JButton jb, String pieceType, int oldFile, int oldRank) {
		
	    	setMovingToEmptySquare(false);
	    				
			for (int i = 1; i <= 8; i++) {
				for (int j = 1; j <= 8; j++) {
					if ( e.getSource() == emptySquare[i][j] ) {

						if ( manager.ableToMove(oldFile, oldRank, i, j) ) {

							setMovingToEmptySquare(true);

							if (manager.isWhitesTurn() && manager.getPieceToMove().equals("Pawn") && j == 8) {

								jb.setVisible(false);
								emptySquare[i][j].setVisible(false);

								piece = initiatePiece("white_queen");
								whiteQueen[manager.numWhiteQueens] = new JButton(piece);
								initiateLocation(whiteQueen[manager.numWhiteQueens], i, j);

								whiteQueen[manager.numWhiteQueens].setVisible(true);
								emptySquare[oldFile][oldRank].setVisible(true);

								setMoving();
								manager.changeTurn();
								return whiteQueen[manager.numWhiteQueens];
							}
							
							else if (!manager.isWhitesTurn() && manager.getPieceToMove().equals("Pawn") && j == 1) {

								jb.setVisible(false);
								emptySquare[i][j].setVisible(false);

								piece = initiatePiece("black_queen");
								blackQueen[manager.numBlackQueens] = new JButton(piece);
								initiateLocation(blackQueen[manager.numBlackQueens], i, j);

								blackQueen[manager.numBlackQueens].setVisible(true);
								emptySquare[oldFile][oldRank].setVisible(true);

								setMoving();
								manager.changeTurn();
								return blackQueen[manager.numBlackQueens];
							}
							
							else if (manager.isWhitesTurn() && manager.getPieceToMove().equals("Pawn")
									&& i != manager.getSquare(oldFile, oldRank).getFile() ) {
								
								if ( i == manager.getSquare(oldFile, oldRank).getFile() + 1 ) {
									
									jb.setVisible(false);
									emptySquare[i][j].setVisible(false);
									
									for (int k = 1; k <= 8; k++)
										if ( manager.blackPawn[k].isEnPassant() )
											blackPawn[k].setVisible(false);

									piece = initiatePiece(pieceType);
									jb = new JButton(piece);
									initiateLocation(jb, i, j);

									jb.setVisible(true);
									emptySquare[oldFile][oldRank].setVisible(true);
									emptySquare[i][j - 1].setVisible(true);
								}
								
								if ( i == manager.getSquare(oldFile, oldRank).getFile() - 1 ) {
									
									jb.setVisible(false);
									emptySquare[i][j].setVisible(false);
									
									for (int k = 1; k <= 8; k++)
										if ( manager.blackPawn[k].isEnPassant() )
											blackPawn[k].setVisible(false);

									piece = initiatePiece(pieceType);
									jb = new JButton(piece);
									initiateLocation(jb, i, j);

									jb.setVisible(true);
									emptySquare[oldFile][oldRank].setVisible(true);
									emptySquare[i][j - 1].setVisible(true);
								}
							}
							
							else if (!manager.isWhitesTurn() && manager.getPieceToMove().equals("Pawn")
									&& i != manager.getSquare(oldFile, oldRank).getFile() ) {
								
								if ( i == manager.getSquare(oldFile, oldRank).getFile() + 1 ) {
									
									jb.setVisible(false);
									emptySquare[i][j].setVisible(false);
									
									for (int k = 1; k <= 8; k++)
										if ( manager.whitePawn[k].isEnPassant() )
											whitePawn[k].setVisible(false);

									piece = initiatePiece(pieceType);
									jb = new JButton(piece);
									initiateLocation(jb, i, j);

									jb.setVisible(true);
									emptySquare[oldFile][oldRank].setVisible(true);
									emptySquare[i][j + 1].setVisible(true);
								}
								
								if ( i == manager.getSquare(oldFile, oldRank).getFile() - 1 ) {
									
									jb.setVisible(false);
									emptySquare[i][j].setVisible(false);
									
									for (int k = 1; k <= 8; k++)
										if ( manager.whitePawn[k].isEnPassant() )
											whitePawn[k].setVisible(false);

									piece = initiatePiece(pieceType);
									jb = new JButton(piece);
									initiateLocation(jb, i, j);

									jb.setVisible(true);
									emptySquare[oldFile][oldRank].setVisible(true);
									emptySquare[i][j + 1].setVisible(true);
								}
							}
							
							else if ( manager.getPieceToMove().equals("King") && (oldFile + 2 == i || oldFile - 2 == i) ) {

								if ( manager.isWhitesTurn() ) {

									if (oldFile + 2 == i) {

										whiteRook[2].setVisible(false);
										emptySquare[6][1].setVisible(false);

										piece = initiatePiece("white_rook");
										whiteRook[2] = new JButton(piece);
										initiateLocation(whiteRook[2], 6, 1);

										whiteRook[2].setVisible(true);
										emptySquare[8][1].setVisible(true);
									}

									else if (oldFile - 2 == i) {

										whiteRook[1].setVisible(false);
										emptySquare[4][1].setVisible(false);

										piece = initiatePiece("white_rook");
										whiteRook[1] = new JButton(piece);
										initiateLocation(whiteRook[1], 4, 1);

										whiteRook[1].setVisible(true);
										emptySquare[1][1].setVisible(true);
									}
								}

								else if ( !manager.isWhitesTurn() ) {

									if (oldFile + 2 == i) {

										blackRook[2].setVisible(false);
										emptySquare[6][8].setVisible(false);

										piece = initiatePiece("black_rook");
										blackRook[2] = new JButton(piece);
										initiateLocation(blackRook[2], 6, 8);

										blackRook[2].setVisible(true);
										emptySquare[8][8].setVisible(true);
									}

									else if (oldFile - 2 == i) {

										blackRook[1].setVisible(false);
										emptySquare[4][8].setVisible(false);

										piece = initiatePiece("black_rook");
										blackRook[1] = new JButton(piece);
										initiateLocation(blackRook[1], 4, 8);

										blackRook[1].setVisible(true);
										emptySquare[1][8].setVisible(true);
									}
								}
							}

							else {
								
								jb.setVisible(false);
								emptySquare[i][j].setVisible(false);

								piece = initiatePiece(pieceType);
								jb = new JButton(piece);
								initiateLocation(jb, i, j);

								jb.setVisible(true);
								emptySquare[oldFile][oldRank].setVisible(true);
							}
							
							if ( manager.isWhitesTurn() )
								for (int k = 1; k <= 8; k++)
									manager.blackPawn[k].setEnPassant(false);
							
							else if ( !manager.isWhitesTurn() )
								for (int k = 1; k <= 8; k++)
									manager.whitePawn[k].setEnPassant(false);

							setMoving();
							manager.changeTurn();
							return jb;
						}

						else {
							setMovingToEmptySquare(true);
							JOptionPane.showMessageDialog(this, "Invalid move.", "", JOptionPane.ERROR_MESSAGE);
							setMoving();
							return jb;
						}
					}
				}
			}
			
			if ( !isMovingToEmptySquare() ) {
	
				int file = getFile(e);
				int rank = getRank(e);
	
				if ( file >= 1 && file <= 8 && rank >=1 && rank <= 8 && manager.ableToMove( oldFile, oldRank, file, rank) ) {
					
					if ( manager.isWhitesTurn() && manager.getPieceToMove().equals("Pawn") && rank == 8 ) {
						
						jb.setVisible(false);
						takePiece(e);

						piece = initiatePiece("white_queen");
						whiteQueen[manager.numWhiteQueens] = new JButton(piece);
						initiateLocation(whiteQueen[manager.numWhiteQueens], file, rank);

						whiteQueen[manager.numWhiteQueens].setVisible(true);
						emptySquare[oldFile][oldRank].setVisible(true);

						setMoving();
						manager.changeTurn();
						return whiteQueen[manager.numWhiteQueens];
					}
					
					else if ( !manager.isWhitesTurn() && manager.getPieceToMove().equals("Pawn") && rank == 1 ) {
						
						jb.setVisible(false);
						takePiece(e);

						piece = initiatePiece("black_queen");
						blackQueen[manager.numBlackQueens] = new JButton(piece);
						initiateLocation(blackQueen[manager.numBlackQueens], file, rank);

						blackQueen[manager.numBlackQueens].setVisible(true);
						emptySquare[oldFile][oldRank].setVisible(true);

						setMoving();
						manager.changeTurn();
						return blackQueen[manager.numBlackQueens];
					}

					else {
						
						jb.setVisible(false);
						takePiece(e);

						piece = initiatePiece(pieceType);
						jb = new JButton(piece);
						initiateLocation(jb, file, rank);

						jb.setVisible(true);
						emptySquare[oldFile][oldRank].setVisible(true);
						
						if ( manager.isWhitesTurn() )
							for (int k = 1; k <= 8; k++)
								manager.blackPawn[k].setEnPassant(false);
						
						else if ( !manager.isWhitesTurn() )
							for (int k = 1; k <= 8; k++)
								manager.whitePawn[k].setEnPassant(false);

						setMoving();
						manager.changeTurn();
						return jb;
					}
				} 
				
				else {
					JOptionPane.showMessageDialog(this, "Invalid move.", "", JOptionPane.ERROR_MESSAGE);
					setMoving();
					return jb;
				}
			}
			
			return null;
	    }

	/**
	 * 
	 */
	private void setMoving() {
		
		if ( isMoving() )
			isMoving = false;
		
		else
			isMoving = true;
	}

	
	private void setMovingToEmptySquare(boolean flag) {
		isMovingToEmptySquare = flag;
	}

	
	private void takePiece(ActionEvent e) {
				
		// White
		for (int i = 1; i <= 10; i++)
			if ( e.getSource() == whiteRook[i] )
				whiteRook[i].setVisible(false);
		for (int i = 1; i <= 10; i++)
			if ( e.getSource() == whiteKnight[i] )
				whiteKnight[i].setVisible(false);
		for (int i = 1; i <= 10; i++)
			if ( e.getSource() == whiteBishop[i] )
				whiteBishop[i].setVisible(false);
		for (int i = 1; i < 10; i++)
			if ( e.getSource() == whiteQueen[i] )
				whiteQueen[i].setVisible(false);
		
		for (int i = 1; i <= 8; i++)
			if ( e.getSource() == whitePawn[i] )
				whitePawn[i].setVisible(false);
		
		if ( e.getSource() == whiteKing )
			JOptionPane.showMessageDialog( this, "Should've been a check.", "Oops!", JOptionPane.ERROR_MESSAGE );
		
		// Black
		for (int i = 1; i <= 10; i++)
			if ( e.getSource() == blackRook[i] )
				blackRook[i].setVisible(false);
		for (int i = 1; i <= 10; i++)
			if ( e.getSource() == blackKnight[i] )
				blackKnight[i].setVisible(false);
		for (int i = 1; i <= 10; i++)
			if ( e.getSource() == blackBishop[i] )
				blackBishop[i].setVisible(false);
		for (int i = 1; i < 10; i++)
			if ( e.getSource() == blackQueen[i] )
				blackQueen[i].setVisible(false);
		
		for (int i = 1; i <= 8; i++)
			if ( e.getSource() == blackPawn[i] )
				blackPawn[i].setVisible(false);
		
		if ( e.getSource() == blackKing )
			JOptionPane.showMessageDialog( this, "Should've been a check.", "Oops!", JOptionPane.ERROR_MESSAGE );
	}

	/**
     * Starts up "Play Chess."
     * 
     * @param args args
     */
    public static void main( String args[] ) {
    	new ChessGUI();
    }
}

//Border lowerEtched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
//TitledBorder borderActions = BorderFactory.createTitledBorder(lowerEtched, "Actions");
//
//// create pnlActions variable?
//
////pnlActions.setBorder(borderActions);
////pnlActions.setToolTipText( "Scheduler Actions" );
//
//Toolkit mySystem = Toolkit.getDefaultToolkit();
//Dimension d = mySystem.getScreenSize();
//
//JPanel panel2 = new JPanel();
//panel2.setLayout( new FlowLayout() );
//
// JCanvas / Canvas / java.awt.Canvas?

