package FirstPackage;

import java.util.Scanner;

public class Manager {
    public int playerTurn = 1;
    Scanner scanner = new Scanner(System.in);
    String currentCommand = "";

    public void managerRollDice(Board board, int currentPlayerNumber) {
        System.out.println("PLAYER: " + currentPlayerNumber);
        System.out.println("_____________________");
        System.out.println("Press Enter key to roll dice.");
        scanner.nextLine();
        board.rollDice();
        board.selectPiece(currentPlayerNumber);
    }

    public void takeCommand(Board board, int currentPlayerNumber) {
        System.out.println("Enter number of piece to move, or type in a command.");
        currentCommand = scanner.nextLine();
        System.out.println(currentCommand);

        if (currentCommand.equals("place")) {
            if(board.playerPlacePiece(currentPlayerNumber)); // Do nothing if playerPlacePiece successful
            else takeCommand(board, currentPlayerNumber);
        } else if (currentCommand.equals("display")) {
            board.selectPiece(currentPlayerNumber);
            takeCommand(board, currentPlayerNumber);
        } else {
            try{
                int targetPiece = Integer.parseInt(currentCommand);
                if(board.executeMove(targetPiece, currentPlayerNumber)); // Do nothing if executeMove successful
                else takeCommand(board, currentPlayerNumber);
            } catch (Exception e) {
                System.out.println("ERROR: COMMAND NOT RECOGNIZED, PLEASE TRY AGAIN");
                takeCommand(board, currentPlayerNumber);
            }
        }
    }

    private int switchPlayerTurn() {
        if (playerTurn == 1) {
            playerTurn = 2;
        } else if (playerTurn == 2) {
            playerTurn = 1;
        } else {
            System.out.println("ERROR: PLAYER TURN SWITCH");
        }

        return playerTurn;
    }

    public void managerRoutineLoop(Board board) {
        managerRollDice(board, playerTurn);
        takeCommand(board, playerTurn);
        board.displayBoardSpace();
        switchPlayerTurn();

        int remainingPiecesResult = board.checkPlayerRemainingPieces();
        /*
        // NOTE: RESULT OF 1 MEANS THAT PLAYER 1 HAS LOST, AND PLAYER 2 HAS WON
        if (remainingPiecesResult == 1) {
            System.out.println("CONGRATULATIONS! PLAYER 2 HAS WON");
        } else if (remainingPiecesResult == 2) {
            System.out.println("CONGRATULATIONS! PLAYER 1 HAS WON");
        } else {
            managerRoutineLoop(board);
        }
        */
        managerRoutineLoop(board);
    }
}
