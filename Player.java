import java.util.ArrayList;

public class Player {

    private int id;
    private boolean turn;
    private int chosen;
    private int target;

    private ArrayList<House> houses;

    public Player(int i) {
        this.id     = i;
        this.houses = new ArrayList<House>();
        this.turn   = false;
        this.target = 0;

        House h1    = new House(0, 4);
        House h2    = new House(1, 4);
        House h3    = new House(2, 4);
        House h4    = new House(3, 4);
        House h5    = new House(4, 4);
        House h6    = new House(5, 4);
        House store = new House(6, 0);

        this.houses.add(h1);
        this.houses.add(h2);
        this.houses.add(h3);
        this.houses.add(h4);
        this.houses.add(h5);
        this.houses.add(h6);
        this.houses.add(store);
    }

    public void setTurn(boolean b) {
        this.turn = b;
    }

    public boolean isTurn() {
        return this.turn;
    }

    public void select(int i) {
        this.chosen = i;
        this.target = this.chosen + this.houses.get(this.chosen).getBeanNum();
    }

    public int getTarget() {
        return this.target;
    }

    public House getHouse(int i) {
        return this.houses.get(i);
    }
}
