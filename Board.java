import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Board extends JFrame implements ActionListener 
{
    JButton[][] buttons = new JButton[8][8];
    Piece[][] boardPieces = new Piece[8][8];
    int selRow = -1;
    int selCol = -1;
    Player whitePlay, blackPlay;
    Player player;

    Board() 
    {
        super("CHESS BY RIDDHIMA BISHT");
        setSize(600, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(8, 8));

        for (int row = 0; row < 8; row++) 
        {
            for (int col = 0; col < 8; col++) 
            {
                buttons[row][col] = new JButton();
                buttons[row][col].setFont(new Font("Noto Sans Symbols", Font.PLAIN, 36));
                if ((row + col) % 2 == 0) buttons[row][col].setBackground(Color.WHITE);
                else buttons[row][col].setBackground(Color.LIGHT_GRAY);
                buttons[row][col].addActionListener(this);
                add(buttons[row][col]);
            }
        }
        whitePlay = new Player("Player1", "white");
        blackPlay = new Player("Player2", "black");
        player = whitePlay;

        for (int col = 0; col < 8; col++) {
            boardPieces[6][col] = new Pawn("white", 6, col);
            boardPieces[1][col] = new Pawn("black", 1, col);
        }

        boardPieces[7][4] = new King("white", 7, 4);
        boardPieces[0][4] = new King("black", 0, 4);
        boardPieces[7][3] = new Queen("white", 7, 3);
        boardPieces[0][3] = new Queen("black", 0, 3);
        boardPieces[7][0] = new Rook("white", 7, 0);
        boardPieces[7][7] = new Rook("white", 7, 7);
        boardPieces[0][0] = new Rook("black", 0, 0);
        boardPieces[0][7] = new Rook("black", 0, 7);
        boardPieces[0][1] = new Knight("black", 0, 1);
        boardPieces[0][6] = new Knight("black", 0, 6);
        boardPieces[7][1] = new Knight("white", 7, 1);
        boardPieces[7][6] = new Knight("white", 7, 6);
        boardPieces[0][2] = new Bishop("black", 0, 2);
        boardPieces[0][5] = new Bishop("black", 0, 5);
        boardPieces[7][2] = new Bishop("white", 7, 2);
        boardPieces[7][5] = new Bishop("white", 7, 5);

        update();
        setVisible(true);
    }

    int lastRow = -999;
    int lastCol = -999;

    public void actionPerformed(ActionEvent e) 
    {
        for (int row = 0; row < 8; row++) 
        {
            for (int col = 0; col < 8; col++) 
            {
                if (e.getSource() == buttons[row][col]) 
                {
                    if (selRow == -1 && selCol == -1) 
                    {
                        Piece selected = boardPieces[row][col];
                        if (selected != null && selected.getColor().equals(player.getColor())) 
                        {
                            selRow = row;
                            selCol = col;
                            buttons[row][col].setBackground(Color.YELLOW); // highlight the selected piece
                        }
                    } 
                    
                    else 
                    {
                        Piece selected = boardPieces[selRow][selCol];
                        if (selected != null && selected.isValidMove(row, col, boardPieces)) 
                        {
                            // Move is valid
                            Piece target = boardPieces[row][col];
                            if (target != null && !target.getColor().equals(player.getColor())) 
                            {
                                player.capturePiece(target);
                                buttons[row][col].setBackground(Color.RED); // highlight capture
                                lastCol = col;
                                lastRow = row;
                                // Show captured pieces popup
                                showCapturedPieces();
                            }
                            boardPieces[row][col] = selected;
                            boardPieces[selRow][selCol] = null;
                            selected.setPosition(row, col);
                            if (isGameOver()) 
                            {
                                JOptionPane.showMessageDialog(this, "Game Over! " + player.getName() + " wins!");
                                System.exit(0); // or disable the board instead of exiting
                            }
                            // Switch turn
                            switchTurn();
                        } 
                        else if (selected != null) 
                            JOptionPane.showMessageDialog(this, "Illegal move! Try again.","Invalid Move",JOptionPane.ERROR_MESSAGE);
                        // Reset selection
                        resetButtonColor(selRow, selCol);
                        selRow = selCol = -1;
                        update();
                    }
                    return;
                }
            }
        }
    }

    void resetButtonColor(int row, int col) 
    {
        // Reset the previously selected square
        if ((row + col) % 2 == 0) 
            buttons[row][col].setBackground(Color.WHITE);
        else 
            buttons[row][col].setBackground(Color.LIGHT_GRAY);

        // Reset the last captured square (if any)
        if (lastRow != -999 && lastCol != -999) {
            if ((lastRow + lastCol) % 2 == 0) 
                buttons[lastRow][lastCol].setBackground(Color.WHITE);
            else 
                buttons[lastRow][lastCol].setBackground(Color.LIGHT_GRAY);
        }
        // Clear last captured markers
        lastRow = lastCol = -999;
    }
    void update() 
    {
        for (int row = 0; row < 8; row++) 
        {
            for (int col = 0; col < 8; col++) 
            {
                if (boardPieces[row][col] != null) 
                    buttons[row][col].setText(boardPieces[row][col].getSymbol());
                else 
                    buttons[row][col].setText("");
            }
        }
    }

    public void switchTurn() 
    {
        player = (player == whitePlay) ? blackPlay : whitePlay;
        setTitle("Chess -> : " + player.getColor() + "'s turn");
        showTurnPopup(player.getColor() + "'s turn!");

    }
    public void showTurnPopup(String message) 
    {
        // Create a non-modal dialog
        JDialog dialog = new JDialog(this, "Turn Switched", false);
        dialog.setUndecorated(true); // no close/minimize buttons
        dialog.setLayout(new BorderLayout());
        JLabel label = new JLabel(message, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        dialog.add(label, BorderLayout.CENTER);
        dialog.setSize(200, 100);
        dialog.setLocationRelativeTo(this); // center on main window
        dialog.setVisible(true);
        new javax.swing.Timer(500, e -> dialog.dispose()).start();
    }
    public void showCapturedPieces() 
    {
        JDialog dialog = new JDialog(this, "Captured Pieces", false);
        dialog.setLayout(new GridLayout(2, 1));

        StringBuilder whiteCaptured = new StringBuilder("White captured: ");
        for (Piece p : whitePlay.getCapturedPieces()) 
            whiteCaptured.append(p.getSymbol()).append(" ");
    
        JLabel whiteLabel = new JLabel(whiteCaptured.toString(), SwingConstants.CENTER);
        StringBuilder blackCaptured = new StringBuilder("Black captured: ");
        for (Piece p : blackPlay.getCapturedPieces()) {
            blackCaptured.append(p.getSymbol()).append(" ");
        }
        JLabel blackLabel = new JLabel(blackCaptured.toString(), SwingConstants.CENTER);

        dialog.add(whiteLabel);
        dialog.add(blackLabel);

        dialog.setSize(400, 150);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);

        javax.swing.Timer t = new javax.swing.Timer(1000, e -> dialog.dispose());
        t.setRepeats(false);
        t.start();
    }

    public boolean isKingCaptured(String color) {
        for (int r = 0; r < 8; r++) 
        {
            for (int c = 0; c < 8; c++) 
            {
                Piece p = boardPieces[r][c];
                if (p != null && p instanceof King && p.getColor().equals(color)) 
                    return false;          
            }
        }
        return true; // King not found -> captured
    }
    public boolean isGameOver() {
        return isKingCaptured("white") || isKingCaptured("black");
    }
}


