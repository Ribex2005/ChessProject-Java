import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Board extends JFrame implements ActionListener
{
    JButton[][] buttons = new JButton[8][8];
    Piece[][] boardPieces = new Piece[8][8];
    int selRow= -1;
    int selCol=-1;
    Player whitePlay, blackPlay;
    Player player;



    Board() 
    {
        super("CHESS BY RIDDHIMA BISHT");
        setSize(600, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(8, 8));

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                buttons[row][col] = new JButton();
                buttons[row][col].setFont(new Font("Noto Sans Symbols", Font.PLAIN, 36));

                if ((row + col) % 2 == 0)
                    buttons[row][col].setBackground(Color.WHITE);
                else
                    buttons[row][col].setBackground(Color.LIGHT_GRAY);
                buttons[row][col].addActionListener(this);
                add(buttons[row][col]);
            }
        }
        whitePlay= new Player("Player1","white");
        blackPlay= new Player("Player2","black");
        player=whitePlay;

        for (int col = 0; col < 8; col++) 
        {
            boardPieces[6][col] = new Pawn("white", 6, col);
            boardPieces[1][col] = new Pawn("black", 1, col);
        }
	    boardPieces[7][4]=new King("white",7,4);
	    boardPieces[0][4]=new King("black",0,4);
	    boardPieces[7][3]=new Queen("white",7,3);
	    boardPieces[0][3]=new Queen("black",0,3);
	    boardPieces[7][0]=new Rook("white",7,0);
	    boardPieces[7][7]=new Rook("white",7,7);
	    boardPieces[0][0]=new Rook("black",0,0);
	    boardPieces[0][7]=new Rook("black",0,7);
        boardPieces[0][1]=new Knight("black",0,1);
        boardPieces[0][6]=new Knight("black",0,6);
        boardPieces[7][1]=new Knight("white",7,1);
        boardPieces[7][6]=new Knight("white",7,6);
        boardPieces[0][2]=new Bishop("black",0,2);
        boardPieces[0][5]=new Bishop("black",0,5);
        boardPieces[7][2]=new Bishop("white",7,2);
        boardPieces[7][5]=new Bishop("white",7,5);

        update();
        setVisible(true);
    }
    //int lastRow;
    //int lastCol;
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
                            buttons[row][col].setBackground(Color.YELLOW); // highlight selection
                        }
                    }
                    else
                    {
                        Piece selected = boardPieces[selRow][selCol];

                        if (selected != null && selected.isValidMove(row, col,boardPieces))
                        {
                            Piece target = boardPieces[row][col];
                            if (target != null && !target.getColor().equals(player.getColor()))
                                player.capturePiece(target);

                            boardPieces[row][col] = selected;
                            //buttons[row][col].setBackground(Color.GREEN);
                            boardPieces[selRow][selCol] = null;
                            selected.setPosition(row, col);
                            // Switch player
                            switchTurn();
                        }

                        // Reset selection
                        resetButtonColor(selRow, selCol);
              //          lastRow=selRow;
               //         lastCol=selCol;
                        selRow = selCol = -1;
                        update();
                    }

                    return;
                }
            }
        }
    }
    void resetButtonColor(int row, int col) {
        if ((row + col) % 2 == 0)
            buttons[row][col].setBackground(Color.WHITE);
        else
            buttons[row][col].setBackground(Color.LIGHT_GRAY);
    }




    void update() 
    {
        for (int row = 0; row < 8; row++) 
	    {
            for (int col = 0; col < 8; col++) 
            {
                if (boardPieces[row][col] != null)
		        {
		      	    buttons[row][col].setText(boardPieces[row][col].getSymbol());

		        }
                else
                    buttons[row][col].setText("");
                resetButtonColor(row, col);
            }
        }


    }
    public void switchTurn() {
        player = (player == whitePlay) ? blackPlay : whitePlay;
        setTitle("Chess -> : " + player.getColor() + "\'s turn");


    }


}    

