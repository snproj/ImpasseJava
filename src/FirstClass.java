import FirstPackage.Board;
import FirstPackage.Manager;

public class FirstClass {
    public static void main(String[] args) {
        Board board = new Board();
        Manager manager = new Manager();
        manager.managerRoutineLoop(board);
    }
}
