# ChessProject-Java
This is my first project in Java — a 2-player chess game built from scratch.

## Project Structure
- **Piece (abstract class)**  
  - Inherited by 6 classes: King, Queen, Rook, Bishop, Knight, Pawn.  
  - Handles movement and capture logic for each piece type.  

- **Board class**  
  - Implements an 8×8 chess board.  
  - Contains the main game logic: turn switching, check/checkmate detection, move validation, and board updates.  

- **Player class**  
  - Keeps track of players and their captured pieces.  

- **ChessGame class**  
  - Serves as the driver class to run the game.  

## Features
- **Piece Selection & Captures**  
  - Selected piece is highlighted yellow.  
  - Captured piece’s square is highlighted green.  

- **Captured Pieces Panel**  
  - After each capture, a panel shows both players’ captured pieces.  

- **Turn Indicator**  
  - After each move, a popup notification displays whose turn it is.  

- **Illegal Move Handling**  
  - If a player attempts an invalid move, an alert popup is shown.  

- **Move Validation**  
  - Ensures only legal moves are executed.  

- **User Experience**  
  - Provides a smooth and interactive chess experience.  

## Future Updates
- Implement Castling  
- Implement En Passant  
- Implement Pawn Promotion  

