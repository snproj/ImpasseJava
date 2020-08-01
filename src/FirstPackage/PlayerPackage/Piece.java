package FirstPackage.PlayerPackage;

public class Piece extends SpaceFiller {
    private boolean isEmpty = false;
    private int posIndex = 0;
    private int playerNumber = 0;

    public Piece(int posIndex, int playerNumber){
        this.posIndex = posIndex;
        this.playerNumber = playerNumber;
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("PIECE FINALIZER: PIECE TAKEN");
        super.finalize();
    }

    @Override
    public boolean getIsEmpty() {
        return this.isEmpty;
    }

    public int getPosIndex(){
        return this.posIndex;
    }
    public void setPosIndex(int newPosIndex){
        this.posIndex = newPosIndex;
    }

    public int getPlayerNumber() {
        return this.playerNumber;
    }
}

