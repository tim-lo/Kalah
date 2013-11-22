public class Player {

    private boolean turn;
    private int     chosen;
    private int     target;

    public Player() {
        this.turn   = false;
        this.chosen = 0;
        this.target = 0;
    }

    public void setTurn(boolean b) {
        this.turn = b;
    }

    public boolean getTurn() {
        return this.turn;
    }

    public void setChosen(int i) {
        this.chosen = i;
    }

    public int getChosen() {
        return this.chosen;
    }

    public void setTarget(int i) {
        this.target = i;
    }

    public int getTarget() {
        return this.target;
    }
}
