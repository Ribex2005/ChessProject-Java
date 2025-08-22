// Piece abstract class
public abstract class Piece 
{
    int row, col;
    String color;

    Piece(String color, int row, int col) {
        this.row = row;
        this.col = col;
        this.color = color;
    }

    public String getColor() { return color; }
    public int getRow() {
         return row; }
    public int getCol() { 
        return col; }

    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public abstract boolean isValidMove(int targetRow, int targetCol, Piece[][] board);
    public abstract String getSymbol();
}

class Pawn extends Piece 
{
    Pawn(String color, int row, int col) {
        super(color, row, col);
    }

    public String getSymbol() { 
        return color.equals("white") ? "♙" : "♟"; 
    }
    public boolean isValidMove(int targetRow, int targetCol, Piece[][] board) 
    {
        if (targetRow == this.row && targetCol == this.col) 
            return false;
        int dir = color.equals("white") ? -1 : 1;   // white moves up (row - 1), black down (row + 1)
        int startRow = color.equals("white") ? 6 : 1;

        if (targetCol == this.col) 
        {
            // one step
            if (targetRow == this.row + dir && board[targetRow][targetCol] == null)
                return true;

            // two steps
            if (this.row == startRow && targetRow == this.row + 2 * dir && board[this.row + dir][this.col] == null&& board[targetRow][targetCol] == null) 
                return true;

            return false;
        }

        // Diagonal capture 
        if (Math.abs(targetCol - this.col) == 1 && targetRow == this.row + dir) 
        {
            Piece target = board[targetRow][targetCol];
            return target != null && !target.getColor().equals(this.getColor());
        }

        return false;
    }

}

class King extends Piece 
{
    King(String color, int row, int col) {
        super(color, row, col);
    }

    public String getSymbol() {
         return color.equals("white") ? "♔" : "♚"; }

    public boolean isValidMove(int targetRow, int targetCol, Piece[][] board) 
    {
        if (targetRow == this.row && targetCol == this.col) 
            return false;
        int rowDiff = Math.abs(targetRow - this.row);
        int colDiff = Math.abs(targetCol - this.col);

        if (rowDiff <= 1 && colDiff <= 1)
        {
            Piece targetPiece = board[targetRow][targetCol];
            if (targetPiece != null && targetPiece.getColor().equals(this.getColor())) 
                return false;
            return true;
        }
        return false;
    }
}

class Queen extends Piece 
{
    Queen(String color, int row, int col) {
        super(color, row, col);
    }

    public String getSymbol() {
         return color.equals("white") ? "♕" : "♛"; }

    public boolean isValidMove(int targetRow, int targetCol, Piece[][] board) 
    {
        if (targetRow == this.row && targetCol == this.col) 
            return false;

        int rowDiff = Math.abs(targetRow - this.row);
        int colDiff = Math.abs(targetCol - this.col);

        // Bishop-like (diagonal)
        if (rowDiff == colDiff) 
        {
            int rStep = (targetRow > this.row) ? 1 : -1;
            int cStep = (targetCol > this.col) ? 1 : -1;
            int r = this.row + rStep, c = this.col + cStep;
            while (r != targetRow || c != targetCol) 
            {
                if (board[r][c] != null) 
                    return false;
                r += rStep; 
                c += cStep;
            }
            Piece t = board[targetRow][targetCol];
            return t == null || !t.getColor().equals(this.getColor());
        }

        // Rook-like (same row or same column)
        if (this.row == targetRow || this.col == targetCol) 
        {
            if (this.row == targetRow) 
            {
                int step = (targetCol > this.col) ? 1 : -1;
                for (int c = this.col + step; c != targetCol; c += step) 
                {
                    if (board[this.row][c] != null) 
                        return false;
                }
            } 
            else 
            {
                int step = (targetRow > this.row) ? 1 : -1;
                for (int r = this.row + step; r != targetRow; r += step) 
                {
                    if (board[r][this.col] != null)
                        return false;
                }
            }
            Piece t = board[targetRow][targetCol];
            return t == null || !t.getColor().equals(this.getColor());
        }
        return false;
    }
}

class Bishop extends Piece 
{
    Bishop(String color, int row, int col) {
        super(color, row, col);
    }

    public String getSymbol() { 
        return color.equals("white") ? "♗" : "♝"; }

    public boolean isValidMove(int targetRow, int targetCol, Piece[][] board) 
    {
        if (targetRow == this.row && targetCol == this.col) return false;

        int rowDiff = Math.abs(targetRow - this.row);
        int colDiff = Math.abs(targetCol - this.col);
        if (rowDiff != colDiff) 
            return false;

        int rStep = (targetRow > this.row) ? 1 : -1;
        int cStep = (targetCol > this.col) ? 1 : -1;
        int r = this.row + rStep, c = this.col + cStep;
        while (r != targetRow || c != targetCol) 
        {
            if (board[r][c] != null) 
                return false;
            r += rStep; 
            c += cStep;
        }
        Piece t = board[targetRow][targetCol];
        return t == null || !t.getColor().equals(this.getColor());
    }

}

class Knight extends Piece 
{
    Knight(String color, int row, int col) {
        super(color, row, col);
    }

    public String getSymbol() { 
        return color.equals("white") ? "♘" : "♞"; }

    public boolean isValidMove(int targetRow, int targetCol, Piece[][] board) 
    {
        if (targetRow == this.row && targetCol == this.col) 
            return false;
        int rowDiff = Math.abs(targetRow - this.row);
        int colDiff = Math.abs(targetCol - this.col);

        if ((rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2)) 
        {
            Piece targetPiece = board[targetRow][targetCol];
            if (targetPiece != null && targetPiece.getColor().equals(this.getColor())) 
                return false;
            return true;
        }
        return false;
    }
}

class Rook extends Piece 
{
    Rook(String color, int row, int col) {
        super(color, row, col);
    }

    public String getSymbol() { 
        return color.equals("white") ? "♖" : "♜"; }

    public boolean isValidMove(int targetRow, int targetCol, Piece[][] board) 
    {
        if (targetRow == this.row && targetCol == this.col) 
            return false;
        Piece targetPiece = board[targetRow][targetCol];

        if (targetCol == col && targetRow <= 7) 
        {
            int initial = Math.min(targetRow, this.row) + 1;
            int end = Math.max(targetRow, this.row);
            for (int i = initial; i < end; i++) 
            {
                if (board[i][targetCol] != null) 
                    return false;
            }
            if (targetPiece != null && targetPiece.getColor().equals(this.color)) 
                return false;
            return true;
        }

        if (targetRow == row && targetCol <= 7) 
        {
            int initial = Math.min(targetCol, this.col) + 1;
            int end = Math.max(targetCol, this.col);
            for (int i = initial; i < end; i++) {
                if (board[targetRow][i] != null) 
                    return false;
            }
            if (targetPiece != null && targetPiece.getColor().equals(this.color)) 
                return false;
            return true;
        }
        return false;
    }
}
