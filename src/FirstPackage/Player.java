package FirstPackage;

public class Player {
    private int pieceCount = 0;
    //private final int pieceLimit = 10;
    private int remainingPieces = 10;
    private int piecesAtEnd = 0;

    public void incrementPiecesAtEnd() {
        piecesAtEnd += 1;
    }

    public int getPiecesAtEnd() {
        return piecesAtEnd;
    }

    public int getRemainingPieces() {
        return remainingPieces;
    }

    public boolean decrementRemainingPieces() {
        remainingPieces -= 1;
        if (remainingPieces < 0) {
            remainingPieces = 0;
            System.out.println("ERROR: NO MORE PIECES REMAINING");
            return false;
        } else {
            return true;
        }
    }

    public int getPieceCount() {
        return pieceCount;
    }

    public void incrementPieceCount() {
        pieceCount += 1;
    }
    public void decrementPieceCount() {
        pieceCount -= 1;
    }
}
