# ChessProject-Java
This is my first project that I created using Java. I made a 2player chess game. 
I created an abstract Piece class that is inherited by 6 classes (King, Queen, Rook, Bishop, Pawn and Knight) and handles the movement and capture logic of all the Pieces.
I created a Board class that consists of a 8x8 Chess Board and Pieces. It contains the main logic of my chess board. It handles turn switching, checkmate and updation after each turn.
I created a Player class to keep a track of the Players and the captured pieces.
I created the ChessGame class as a Driver Class.
Features:
1. As soon as a Piece is selected, it is highlighted  yellow and when a piece is captured, it turns green.
2. There is a panel after each capture displaying each players' captures.
3. There is a pop-up  after each turn to indicate the respective player's turn.
Future updates:
1. Castling
2. En passant
3. Pawn promotion


