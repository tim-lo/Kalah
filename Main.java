import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Scanner in       = new Scanner(System.in);
        boolean gameover = false;
        int     chosen   = 0;
        int     beanNum  = 0;
        int     target   = 0;

        ArrayList<Player> players = new ArrayList<Player>();

        Player p1 = new Player(0);
        Player p2 = new Player(1);

        players.add(p1);
        players.add(p2);

        System.out.println("Welcome to Kalah.");

        p1.setTurn(true);

        while (!gameover) {

            /******** Main Loop *********/

            while (p1.isTurn() == true) {

                /******** Player 1's turn *********/

                System.out.println("Player 1's Turn.");
                System.out.print("Player 1, please select a house: ");
                chosen = in.nextInt();

                if(chosen < 6 && chosen >= 0) {
                    p1.select(chosen);
                    System.out.println("Target: " + p1.getTarget());

                    chosen = 0;
                    p1.setTurn(false);
                    p2.setTurn(true);
                } else {
                    chosen = 0;
                    System.out.println("Invalid house.");
                    p1.setTurn(true);
                }
            }

            while (p2.isTurn() == true) {

                /******** Player 2's turn *********/

                System.out.println("Player 2's Turn.");
                System.out.print("Player 2, please select a house: ");
                chosen = in.nextInt();

                if(chosen < 6 && chosen >= 0) {
                    p2.select(chosen);
                    System.out.println("Target: " + p2.getTarget());

                    chosen = 0;
                    p2.setTurn(false);
                    p1.setTurn(true);
                } else {
                    chosen = 0;
                    System.out.println("Invalid house.");
                    p2.setTurn(true);
                }
            }
        }

        System.out.println("GAME OVER");
    }

    public static void move(Player p, int init, int beanNum) {

        /******** This method handles all moves made by players
         *        and deploys appropriate number of seeds to each of the houses and stores. *********/

    }
}
