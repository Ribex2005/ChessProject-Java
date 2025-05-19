import java.util.ArrayList;
import java.util.List;

public class Player
{
    String name;
    String color;  // "white" or "black"
    List<Piece> capturedPieces;

     Player(String name, String color) {
        this.name = name;
        this.color = color;
        this.capturedPieces = new ArrayList<>();
    }

     String getName() {
        return name;
    }

     String getColor() {
        return color;
    }

     List<Piece> getCapturedPieces() {
        return capturedPieces;
    }

     void capturePiece(Piece piece) {
        capturedPieces.add(piece);
    }
    int countCapturedPieces() {
         return capturedPieces.size();
    }

}
