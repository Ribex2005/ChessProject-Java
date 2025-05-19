//Piece abstract class
public abstract class Piece
{
	int row, col;
	String color;
	Piece(String color, int row, int col)
    	{
        	this.row=row;
        	this.col=col;
        	this.color=color;
    	}
    	public String getColor()
    	{
        	return color;
    	}
    	public int getRow()
    	{
        	return row;
    	}
    	public int getCol()
    	{
        	return col;
    	}
  		 public void setPosition(int row, int col)
    	{
        	this.row=row;
        	this.col=col;
    	}

		public abstract boolean isValidMove(int targetRow, int targetCol, Piece[][] board);
    	public abstract String getSymbol();

}
class Pawn extends Piece
{
	Pawn(String color, int row, int col)
    	{
        	super(color, row, col);
    	}
   	public String getSymbol()  
	{
		return color.equals("white") ? "♙" : "♟";

 	}

	@Override
	public boolean isValidMove(int targetRow, int targetCol,Piece[][] board)
	{
		boolean valid=false;
		if (color.equals("white")) {
			if (targetRow == this.row && targetCol == this.col)
				return false;

			if (targetCol == this.col && targetRow == this.row - 1)
				valid=true;

			// First move -> 2 steps
			if (this.row == 6 && targetCol == this.col && targetRow == this.row - 2)
				valid=true;

		}
		 else
		{
			if (targetCol == this.col && targetRow == this.row + 1)
				valid= true;

			// First move -> 2 steps
			if (this.row == 1 && targetCol == this.col && targetRow == this.row + 2)
				valid= true;

		}
		if (Math.abs(targetRow - this.row) == 1 && Math.abs(targetCol - this.col) == 1)
		{
			if (board[targetRow][targetCol]==null)
				return false;
			if (board[targetRow][targetCol].getColor().equals(this.getColor()))
				return false;
			else
				valid=true;
		}
		 if (valid)
		 {
			 Piece targetPiece=board[targetRow][targetCol];
			 if (targetPiece!=null && targetPiece.getColor().equals(this.getColor()))
				 return false;
			 return true;
		 }
		return false;
	}
}
class King  extends Piece {
	King(String color, int row, int col) {
		super(color, row, col);
	}

	public String getSymbol() {
		return color.equals("white") ? "♔" : "♚";

	}

	public boolean isValidMove(int targetRow, int targetCol, Piece[][] board)
	{

		if (targetRow == this.row && targetCol == this.col)
			return false;
		int rowDiff = Math.abs(targetRow - this.row);
		int colDiff = Math.abs(targetCol - this.col);
		if (rowDiff <=1  &&  colDiff <=1)
		{
			Piece targetPiece = board[targetRow][targetCol];
			if (targetPiece!=null && targetPiece.getColor().equals(this.getColor()))
				return false;
			return true;
		}
		return false;
	}
}
class Queen extends Piece {
	Queen(String color, int row, int col) {
		super(color, row, col);
	}

	public String getSymbol() {
		return color.equals("white") ? "♕" : "♛";

	}

	public boolean isValidMove(int targetRow, int targetCol, Piece[][] board)
	{
		if (targetRow == this.row && targetCol == this.col)
			return false;

		int rowDiff = Math.abs(targetRow - this.row);
		int colDiff = Math.abs(targetCol - this.col);

		if (rowDiff != colDiff)
			return false;

		int rowDirection = (targetRow > this.row) ? 1 : -1;
		int colDirection = (targetCol > this.col) ? 1 : -1;
		int currentRow= this.row+rowDirection;
		int currentCol= this.col+colDirection;

		while (currentRow != targetRow && currentCol != targetCol) {
			if (board[currentRow][currentCol] != null)
				return false; // path is blocked
			currentRow = currentRow+ rowDirection;
			currentCol = currentCol+ colDirection;
		}

		Piece targetPiece = board[targetRow][targetCol];
		if (targetPiece != null && targetPiece.getColor().equals(this.getColor()))
			return false;

		return true;

	}
}
class Bishop extends Piece
{
	Bishop(String color, int row, int col)
	{
		super(color,row,col);
	}
	public String getSymbol() 
	{
    		return color.equals("white") ? "♗" : "♝";		

	}
	public boolean isValidMove(int targetRow, int targetCol, Piece[][] board) {
		if (targetRow == this.row && targetCol == this.col)
			return false;

		int rowDiff = Math.abs(targetRow - this.row);
		int colDiff = Math.abs(targetCol - this.col);

		if (rowDiff != colDiff)
			return false;

		int rowDirection = (targetRow > this.row) ? 1 : -1;
		int colDirection = (targetCol > this.col) ? 1 : -1;
		int currentRow= this.row+rowDirection;
		int currentCol= this.col+colDirection;

		while (currentRow != targetRow && currentCol != targetCol) {
			if (board[currentRow][currentCol] != null)
				return false; // path is blocked
			currentRow = currentRow+ rowDirection;
			currentCol = currentCol+ colDirection;
		}

		Piece targetPiece = board[targetRow][targetCol];
		if (targetPiece != null && targetPiece.getColor().equals(this.getColor()))
			return false;

		if (targetCol == col && targetRow <= 7) {
			int initial=Math.min(targetRow,this.row)+1;
			int end=Math.max(targetRow,this.row);
			for (int i = initial; i < end; i++) {
				if ( board[i][targetCol] != null)
					return false;
			}
			if (targetPiece != null && targetPiece.getColor().equals(this.color))
				return false;
			return true;
		}
		if (targetRow == row && targetCol <= 7) {
			int initial=Math.min(targetCol,this.col)+1;
			int end=Math.max(targetCol,this.col);
			for (int i = initial; i < end; i++) {
				if (board[targetRow][i] != null )
					return false;
			}
			if (targetPiece != null && targetPiece.getColor().equals(this.color))
				return false;
			return true;
		}
		return true;
	}

}
class Knight extends Piece
{
	Knight(String color, int row, int col)
	{
		super(color,row,col);
	}
	public String getSymbol() 
	{
    		return color.equals("white") ? "♘" : "♞";

	}
	public boolean isValidMove(int targetRow, int targetCol, Piece[][] board)
	{
			if (targetRow == this.row && targetCol == this.col)
				return false;
			int rowDiff=Math.abs(targetRow - this.row);
			int colDiff=Math.abs(targetCol - this.col);
			if ((rowDiff ==2 && colDiff ==1)||(rowDiff ==1 && colDiff ==2)){
				Piece targetPiece=board[targetRow][targetCol];
				if (targetPiece!=null && targetPiece.getColor().equals(this.getColor()))
					return false;
				return true;

			}
		return false;
	}

}
class Rook extends Piece {
	Rook(String color, int row, int col) {
		super(color, row, col);
	}

	public String getSymbol() {
		return color.equals("white") ? "♖" : "♜";

	}

	public boolean isValidMove(int targetRow, int targetCol,Piece[][] board)
	{
		//boolean valid=false;
		if (targetRow == this.row && targetCol == this.col)
			return false;
		Piece targetPiece=board[targetRow][targetCol];

		if (targetCol == col && targetRow <= 7) {
			int initial=Math.min(targetRow,this.row)+1;
			int end=Math.max(targetRow,this.row);
			for (int i = initial; i < end; i++) {
				if ( board[i][targetCol] != null)
					return false;
			}
			if (targetPiece != null && targetPiece.getColor().equals(this.color))
				return false;
			return true;
		}
		if (targetRow == row && targetCol <= 7) {
			int initial=Math.min(targetCol,this.col)+1;
			int end=Math.max(targetCol,this.col);
			for (int i = initial; i < end; i++) {
				if (board[targetRow][i] != null )
					return false;
			}
			if (targetPiece != null && targetPiece.getColor().equals(this.color))
				return false;
			return true;
		}

		return false;
	}

}