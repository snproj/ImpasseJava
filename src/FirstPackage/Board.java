package FirstPackage;

import FirstPackage.PlayerPackage.Piece;
import FirstPackage.PlayerPackage.SpaceFiller;

import java.util.Arrays;

public class Board {
    private final int boardLimit = 50;
    public SpaceFiller[] boardSpace = new SpaceFiller[boardLimit];
    private int currentDice = 0;
    private int[] selectKeyArray = new int[boardLimit];

    public Player player1 = new Player();
    public Player player2 = new Player();

    public void selectPiece(int currentPlayerNumber) {
        displayBoardSpace();

        StringBuilder caratString = new StringBuilder();
        try {
            for (int x = 0; x < boardLimit; x++) {
                if (boardSpace[x].getIsEmpty() == false) {
                    int piecePlayerNumber = ((Piece) boardSpace[x]).getPlayerNumber();
                    if (piecePlayerNumber == currentPlayerNumber) {
                        caratString.append(" ^ ");
                    } else {
                        caratString.append("   ");
                    }
                } else {
                    caratString.append("   ");
                }
            }
        } catch (Exception e) {
            System.out.println("CARAT STRING ERROR");
        }
        System.out.println(caratString);

        StringBuilder numberString = new StringBuilder();
        int selectNumber = 1;
        int selectKeyArrayIndex = 0;
        try {
            for (int x = 0; x < boardLimit; x++) {
                if (boardSpace[x].getIsEmpty() == false) {
                    int piecePlayerNumber = ((Piece) boardSpace[x]).getPlayerNumber();
                    if (piecePlayerNumber == currentPlayerNumber) {
                        //numberString.append(" ^ ");
                        numberString.append(" ");
                        numberString.append(Integer.toString(selectNumber));
                        numberString.append(" ");
                        selectKeyArray[selectKeyArrayIndex] = x;
                        selectNumber += 1;
                        selectKeyArrayIndex += 1;
                    } else {
                        numberString.append("   ");
                    }
                } else {
                    numberString.append("   ");
                }
            }
        } catch (Exception e) {
            System.out.println("NUMBER STRING ERROR");
        }
        System.out.println(numberString);
    }

    public void displayBoardSpace() {
        StringBuilder displayString = new StringBuilder();
        try {
            for (int x = 0; x < boardLimit; x++) {
                if (boardSpace[x].getIsEmpty() == false) {
                    int currentPlayerNumber = ((Piece) boardSpace[x]).getPlayerNumber();
                    if (currentPlayerNumber == 1) {
                        displayString.append(" 1 ");
                    } else if (currentPlayerNumber == 2) {
                        displayString.append(" 2 ");
                    } else {
                        displayString.append(" PNERROR ");
                    }
                } else {
                    displayString.append(" 0 ");
                }
            }
        } catch (Exception e) {
            System.out.println("MOVE PIECE ERROR");
        }
        System.out.println(displayString);
    }

    private void removePiece(int currentPosIndex) {
        int currentPlayerNumber = ((Piece)boardSpace[currentPosIndex]).getPlayerNumber();
        if (currentPlayerNumber == 1) {
            player1.decrementPieceCount();
            System.out.println("Player 1 now has " + player1.getPieceCount() + " pieces.");
        } else {
            player2.decrementPieceCount();
            System.out.println("Player 2 now has " + player2.getPieceCount() + " pieces.");
        }
        boardSpace[currentPosIndex] = new SpaceFiller();
        System.out.println("Piece removed.");
    }

    public boolean executeMove(int selectionNumber, int currentPlayerNumber) {
        //System.out.println(selectionNumber);
        //System.out.println(Arrays.toString(selectKeyArray));
        //System.out.println(selectKeyArray[selectionNumber - 1]);
        if (selectionNumber - 1 > selectKeyArray.length) {
            System.out.println("Out of key array selected.");
            return false;
        } else {
            int currentPosIndex = selectKeyArray[selectionNumber - 1];
            return performCollision(currentPosIndex, currentPlayerNumber);
        }



    }

    private boolean shiftPieces (int currentPosIndex, int firstEmptyIndex) {
        int count = Math.abs(firstEmptyIndex - currentPosIndex);

        int currentEmptyIndex = firstEmptyIndex;

        try {
            for (int x = 0; x < count; x++) {
                if (firstEmptyIndex > currentPosIndex) {
                    boardSpace[currentEmptyIndex] = boardSpace[currentEmptyIndex - 1];
                    currentEmptyIndex--;
                } else {
                    boardSpace[currentEmptyIndex] = boardSpace[currentEmptyIndex + 1];
                    currentEmptyIndex++;
                }
            }
            return true;
        } catch (Exception e) {
            System.out.println("UNKNOWN ERROR IN SHIFTPIECES: " + e);
            return false;
        }
    }

    private int getFirstEmptyIndex (int currentPosIndex, int signedCurrentDice) {
        int firstEmptyIndex = currentPosIndex;
        int incrOrDecr;

        if (signedCurrentDice > 0) {
            incrOrDecr = 1;
        } else {
            incrOrDecr = -1;
        }

        // must handle out of array exceptions
        try {
            if(boardSpace[currentPosIndex + incrOrDecr].getIsEmpty()) {
                return firstEmptyIndex;
            } else {
                if (signedCurrentDice > 0) {
                    firstEmptyIndex++;
                    return getFirstEmptyIndex(currentPosIndex + 1, signedCurrentDice);
                } else {
                    firstEmptyIndex--;
                    return getFirstEmptyIndex(currentPosIndex - 1, signedCurrentDice);
                }
            }
        } catch (Exception e) {
            return -1000;
        }
    }

    private void clearIntermediarySteps(int currentPosIndex, int signedCurrentDice) {
        assert(signedCurrentDice != 0);
        System.out.println("here");
        int intermediaryStep = currentPosIndex;
        int absCurrentDice = Math.abs(signedCurrentDice);

        for (int x = 0; x < absCurrentDice; x++) {
            if (signedCurrentDice > 0) {
                intermediaryStep++;
            } else {
                intermediaryStep--;
            }

            boardSpace[intermediaryStep] = new SpaceFiller();
        }
    }

    public boolean performCollision(int currentPosIndex, int currentPlayerNumber) {
        boolean collisionResult = false;
        int signedCurrentDice;

        if (currentPlayerNumber == 1) {
            signedCurrentDice = currentDice;
        } else {
            signedCurrentDice = -currentDice;
        }

        int newPosIndex = signedCurrentDice + currentPosIndex;

        if((newPosIndex > (boardLimit - 1)) || (newPosIndex < 0)) {
            removePiece(currentPosIndex);
        } else {
            int firstPiecePlayer;
            int secondPiecePlayer;

            if (boardSpace[currentPosIndex].getIsEmpty() == false) {
                firstPiecePlayer = ((Piece)boardSpace[currentPosIndex]).getPlayerNumber();
            } else {
                System.out.println("ERROR: NO FIRST PIECE SELECTED");
                firstPiecePlayer = 0;
                collisionResult = false;
            }
            if (boardSpace[newPosIndex].getIsEmpty() == false) {
                secondPiecePlayer = ((Piece)boardSpace[newPosIndex]).getPlayerNumber();
            } else {
                secondPiecePlayer = 0;
            }

            // COLLISION DIRECTORY
            if (firstPiecePlayer == secondPiecePlayer) {
                System.out.println("MOVE ERROR.");
            } else if ((firstPiecePlayer == 1 && secondPiecePlayer == 2) || (firstPiecePlayer == 2 && secondPiecePlayer == 1)) {

                removePiece(newPosIndex);

                switch (currentDice) {
                    case 1:
                        int firstEmptyIndex = getFirstEmptyIndex(currentPosIndex, signedCurrentDice);
                        if (firstEmptyIndex == -1000) {
                            collisionResult = false;
                            System.out.println("CANNOT SHIFT PIECES!");
                        } else {
                            shiftPieces(currentPosIndex, firstEmptyIndex);
                            collisionResult = true;
                        }
                        break;
                    case 2:
                    case 3:
                    case 6:
                        movePiece(currentPosIndex, signedCurrentDice);
                        collisionResult = true;
                        break;
                    case 4:
                        clearIntermediarySteps(currentPosIndex, signedCurrentDice);
                        movePiece(currentPosIndex, signedCurrentDice);
                        collisionResult = true;
                        break;
                    case 5:
                        if (boardSpace[currentPosIndex + signedCurrentDice].getIsEmpty()) {
                            System.out.println(currentPosIndex + signedCurrentDice);
                            movePiece(currentPosIndex, signedCurrentDice);
                            collisionResult = true;
                        } else {
                            System.out.println("CANNOT MOVE, SPACE TAKEN.");
                            collisionResult = false;
                        }
                        break;
                    default:
                        System.out.println("ERROR: DICE NUMBER NOT WITHIN 1 TO 6");
                        collisionResult = false;
                        break;
                }
            } else if (secondPiecePlayer == 0) {
                if (currentDice >= 1 && currentDice <= 6){
                    movePiece(currentPosIndex, signedCurrentDice);
                    collisionResult = true;
                } else {
                    System.out.println("ERROR: DICE NUMBER NOT WITHIN 1 TO 6 AND SECONDPIECEPLAYER == 0");
                    collisionResult = false;
                }
            } else {
                System.out.println("COLLISION HANDLING ERROR");
                collisionResult = false;
            }

        }

        return collisionResult;
    }

    public void movePiece(int currentPosIndex, int signedOffset) {
        int newPosIndex = signedOffset + currentPosIndex;

        ((Piece)boardSpace[currentPosIndex]).setPosIndex(newPosIndex);
        boardSpace[newPosIndex] = boardSpace[currentPosIndex];
        boardSpace[currentPosIndex] = new SpaceFiller();

    }

    public void emptyBoardSpace(){
        for (int x = 0; x < boardLimit; x++) {
            boardSpace[x] = new SpaceFiller();
        }
    }
    public void emptySelectKeyArray(){
        for (int x = 0; x < boardLimit; x++) {
            selectKeyArray[x] = 0;
        }
    }

    public Board(){
        emptySelectKeyArray();
        emptyBoardSpace();

        // Player 1
        setPiece(3, 1);
        setPiece(5, 1);
        setPiece(7, 1);
        setPiece(9, 1);
        setPiece(11, 1);
        setPiece(12, 1);
        setPiece(13, 1);

        //Player 2 test
        setPiece(14, 2);
        setPiece(15, 2);
        setPiece(16, 2);
        setPiece(18, 2);
        setPiece(20, 2);
        setPiece(22, 2);
        setPiece(24, 2);

        // Player 2 real
        setPiece(36, 2);
        setPiece(37, 2);
        setPiece(38, 2);
        setPiece(40, 2);
        setPiece(42, 2);
        setPiece(44, 2);
        setPiece(46, 2);
    }

    private boolean setPiece(int posIndex, int playerNumber) {
        if (playerNumber == 1) {
            if (player1.decrementRemainingPieces()) {
                player1.incrementPieceCount();
                System.out.println("Player 1 now has " + player1.getRemainingPieces() + " pieces left in reserve.");
            } else {
                System.out.println("ERROR: SET PIECE FAILED FOR PLAYER 1");
            }
        } else {
            if (player2.decrementRemainingPieces()) {
                player2.incrementPieceCount();
                System.out.println("Player 2 now has " + player2.getRemainingPieces() + " pieces left in reserve.");
            } else {
                System.out.println("ERROR: SET PIECE FAILED FOR PLAYER 2");
            }
        }

        if (boardSpace[posIndex].getIsEmpty() == false) {
            System.out.println("CANNOT PLACE: SPOT ALREADY FULL");
            return false;
        } else {
            boardSpace[posIndex] = new Piece(posIndex, playerNumber);
            return true;
        }
    }

    public int rollDice() {
        double double_NewDice = Math.floor(Math.random()*((6-1)+1)+1);
        int newDice = (int)double_NewDice;
        this.currentDice = newDice;
        System.out.println("DICE ROLL: " + currentDice);
        return newDice;
    }

    public boolean playerPlacePiece(int currentPlayerNumber) {
        if (currentPlayerNumber == 1) {
            return setPiece(currentDice - 1, currentPlayerNumber);
        } else {
            return setPiece(boardLimit - currentDice, currentPlayerNumber);
        }
    }

    public int checkPlayerRemainingPieces() {
        if (player1.getRemainingPieces() == 0 || player1.getPieceCount() == 0) {
            return 1;
        } else if (player2.getRemainingPieces() == 0 || player2.getPieceCount() == 0) {
            return 2;
        } else {
            return 0;
        }
    }
}
