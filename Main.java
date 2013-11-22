import java.util.Scanner;

public class Main {

    private static boolean gameover;
    private static int[]   board;
    private static int     beanSumP1;
    private static int     beanSumP2;

    public static void main(String[] args) {
        Scanner usrInput = new Scanner(System.in);

        /***** Initialisation *****/

        boolean gameover  = false;
                board     = new int[14];
        Player  p1        = new Player();
        Player  p2        = new Player();
                beanSumP1 = 0;
                beanSumP2 = 0;

        for (int i = 0; i < 14; i++) {
            if (i == 6 || i == 13) {  //Houses Nos.6 and 13 are respectively the stores of player 1 and player 2
                board[i] = 0;         //Zero beans for the stores
            } else {
                board[i] = 4;         //Four beans for each of the houses
            }
        }

        /***** End of initialisation *****/

        p1.setTurn(true);

        printBoard();

        while (!gameover) {

            /***** Main Loop *****/

            while (p1.getTurn()) {

                /***** Player 1's Turn *****/

                System.out.println("P1: Pick a house");
                p1.setChosen(usrInput.nextInt());

                if (p1.getChosen() > 5 || p1.getChosen() < 0 || board[p1.getChosen()] == 0) {

                    System.out.println("P1: Invalid house");
                    p1.setChosen(0);

                } else {

                    p1.setTarget(p1.getChosen() + board[p1.getChosen()]);

                    //System.out.println("P1: Target = " + p1.getTarget());

                    move(p1.getChosen(), p1.getTarget());

                    if (checkAllEmptyHouses()) {

                        endGameMove();

                        gameover = true;
                        p1.setTurn(false);
                        p2.setTurn(false);

                    } else {

                        if ((p1.getTarget() % 13) >= 0 && (p1.getTarget() % 13) < 6 && checkOppositeHouse(p1.getTarget())) {
                            bonusBeans(0, p1.getTarget());
                        }

                        System.out.println(checkOppositeHouse(p1.getTarget()));

                        if (p1.getTarget() == 6) {  //Checks if player 1 gets a bonus turn

                            p1.setChosen(0);

                        } else {

                            p1.setChosen(0);
                            p1.setTurn(false);
                            p2.setTurn(true);

                        }
                    }

                }

                printBoard();

                /***** End of Player 1's Turn *****/

            }

            while (p2.getTurn()) {

                /***** Player 2's Turn *****/

                System.out.println("P2: Pick a house");
                p2.setChosen(usrInput.nextInt());

                if (p2.getChosen() < 7 || p2.getChosen() >= 13  || board[p2.getChosen()] == 0) {

                    System.out.println("P2: Invalid house");
                    p2.setChosen(0);

                } else {

                    p2.setTarget(p2.getChosen() + board[p2.getChosen()]);

                    //System.out.println("P2: Target = " + p2.getTarget());

                    move(p2.getChosen(), p2.getTarget());

                    if (checkAllEmptyHouses()) {

                        endGameMove();

                        gameover = true;
                        p2.setTurn(false);
                        p1.setTurn(false);

                    } else {

                        if ((p2.getTarget() % 13) > 6 && (p2.getTarget() % 13) < 13 && checkOppositeHouse(p2.getTarget())) {
                            bonusBeans(1, p2.getTarget());
                        }

                        //System.out.println(checkOppositeHouse(p2.getTarget()));

                        if (p2.getTarget() == 13) {  //Checks if player 2 gets a bonus turn

                            p2.setChosen(0);

                        } else {

                            p2.setChosen(0);
                            p2.setTurn(false);
                            p1.setTurn(true);

                        }
                    }

                }

                printBoard();

                /***** End of Player 2's Turn *****/
            }

            /***** End of Main Loop --> GAME OVER *****/

        }

        if (board[6] > board[13]) {
            System.out.println("Player 1: A WINRAR IS YOU!");
        } else if (board[13] > board[6]) {
            System.out.println("Player 2: A WINRAR IS YOU!");
        } else {
            System.out.println("EVERYONE IS A WINRAR! XD");
        }
    }

    /**
     * Public method move() carries out each player's moves (i.e. deploys the beans) and determines if each player gets a bonus move.
     * @param start  the house chosen by the player for the move
     * @param finish the house where the last bean lands
     */

    private static void move(int start, int finish) {
        int loops  = (finish - start) / 13;

        if (finish <= 13) {
            fillBeans(start + 1, finish);
        } else {

            do {

                fillBeans(start + 1, 13);
                loops -= 1;

            } while (loops >= 0);

            fillBeans(0, (finish % 13) - 1);

        }

        board[start] = 0;
    }

    private static void fillBeans(int start, int finish) {
        for (int i = start; i < finish + 1; i++) {
            board[i] += 1;
        }
    }

    private static boolean checkOppositeHouse(int t) {
        int target = t % 13;

        if (t == 6 || t == 13) {
            return false;
        } else {
            if (board[12 - target] == 0) {
                return true;
            } else {
                return false;
            }
        }
    }

    private static void bonusBeans(int p, int t) {
        int target = t % 13;

        if (p == 0) {
            board[6]          += board[target] + board[12 - target];
            board[target]      = 0;
            board[12 - target] = 0;
        } else {
            board[13]         += board[target] + board[12 - target];
            board[target]      = 0;
            board[12 - target] = 0;
        }
    }

    private static boolean checkAllEmptyHouses() {
        for (int i = 0; i < 6; i++) {
            beanSumP1 += 1;
        }

        for (int i = 7; i < 13; i++) {
            beanSumP2 += 1;
        }

        return (beanSumP1 == 0) || (beanSumP2 == 0);
    }

    private static void endGameMove() {
        if (beanSumP1 == 0) {

            board[13] += beanSumP2;

            for (int i = 7; i < 13; i++) {
                board[i] = 0;
            }

        } else if (beanSumP2 == 0) {

            board[6] += beanSumP1;

            for (int i = 0; i < 6; i++) {
                board[i] = 0;
            }

        }
    }

    private static void printBoard() {
        for (int i = (board.length - 1); i > 6; i--) {
            System.out.print(board[i] + " ");
        }

        System.out.println();
        System.out.print("  ");

        for (int i = 0; i < (board.length / 2); i++) {
            System.out.print(board[i] + " ");
        }

        System.out.println();
    }
}
