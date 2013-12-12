import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Kalah {

    private static AI2 p1;
    private static AI3 p2;

    private static boolean gameover;
    private static Board   board;
    private static Board   tmpBoard;
    private static int     beanSumP1;
    private static int     beanSumP2;

    private static int p1Wins;
    private static int p2Wins;
    private static int draws;

    private static ArrayList<Integer> utilArray;
    private static ArrayList<Integer> moveArray;
    private static ArrayList<Integer> line;
    private static ArrayList<String>  dataOutput;
    private static ArrayList<ArrayList<Integer>> lines;

    private static FileWriter     fWriter;
    private static BufferedWriter bWriter;

    /**
     * The constructor initialises data fields and hosts the main while loop of the game.
     */

    public static void main(String[] args) {

        int counter = 0;
        p1Wins = 0;
        p2Wins = 0;
        draws  = 0;

        while (counter < 10) {

            loop();
            System.out.println("Game No." + counter);
            counter++;

        }

        System.out.println("Player 1 has won " + p1Wins + " times.");
        System.out.println("Player 2 has won " + p2Wins + " times.");
        System.out.println("Number of draws: " + draws);
        System.out.println("P1 win percentage: " + (p1Wins / 5000.0));

    }

    public static void loop() {

        /***** Initialisation *****/

        gameover  = false;
        board     = new Board();
        tmpBoard  = new Board();
        p1        = new AI2(true, board);
        p2        = new AI3(false, board);
        beanSumP1 = 0;
        beanSumP2 = 0;

        moveArray  = new ArrayList<Integer>();
        utilArray  = new ArrayList<Integer>();
        line       = new ArrayList<Integer>();
        lines      = new ArrayList<ArrayList<Integer>>();
        dataOutput = new ArrayList<String>();

        fWriter = null;
        bWriter = null;

        int targetBeans;

        for (int i = 0; i < 14; i++) {

            if (i == 6 || i == 13) {     //Houses Nos.6 and 13 are respectively the stores of player 1 and player 2

                board.getBoard()[i] = 0; //Zero beans for the stores

            } else {

                board.getBoard()[i] = 4; //Four beans for each of the houses

            }

        }

        /***** End of initialisation *****/

        p1.setTurn(true);

        //System.out.println();
        //printBoard();
        //System.out.println();

        String lineContent = "";

        while (!gameover) {

            /***** Main Loop *****/

            while (p1.getTurn()) {

                /***** Player 1's Turn *****/

                /***** Legacy Code *****/

                //System.out.println("P1: Pick a house");
                //p1.setChosen(usrInput.nextInt());
                //p1Moves.add(p1.getChosen());

                p1.setChosen(p1.findBestMove(true, tmpBoard));

                sync();

                int utility = p1.getRating(true, tmpBoard, p1.getChosen());

                moveArray.add(p1.getChosen());
                utilArray.add(utility);

                sync();

                //if (p1.getChosen() > 5 || p1.getChosen() < 0 || board.getBoard()[p1.getChosen()] == 0) {

                    //System.out.println("P1: Invalid house");
                    //p1.setChosen(0);

                //} else {

                    //System.out.println("P1's move: " + p1.getChosen());

                    p1.setTarget(p1.getChosen() + board.getBoard()[p1.getChosen()]);

                    targetBeans = board.getBoard()[p1.getTarget() % 13];

                    move(0, p1.getChosen(), p1.getTarget());

                    for (int i = 0; i < 6; i++) {

                        beanSumP1 += board.getBoard()[i];

                    }

                    for (int i = 7; i < 13; i++) {

                        beanSumP2 += board.getBoard()[i];

                    }

                    //System.out.println("P1: " + beanSumP1 + " beans");
                    //System.out.println("P2: " + beanSumP2 + " beans");

                    if (checkAllEmptyHouses()) {

                        endGameMove();

                        gameover = true;
                        p1.setTurn(false);
                        p2.setTurn(false);

                    } else {

                        if ( (p1.getTarget() % 13) >= 0
                                && (p1.getTarget() % 13) < 6
                                && targetBeans == 0
                                && checkOppositeHouse(p1.getTarget())
                                ) {

                            bonusBeans(0, p1.getTarget());

                        }

                        if (p1.getTarget() == 6) { //Checks if player 1 gets a bonus turn

                            p1.setChosen(0);

                        } else {

                            p1.setChosen(0);
                            p1.setTurn(false);
                            p2.setTurn(true);

                        }
                    }

                //}

                //System.out.println();
                //printBoard();
                //System.out.println();

                //System.out.println("Number of beans in P1's houses: " + beanSumP1);
                //System.out.println("Number of beans in P2's houses: " + beanSumP2);

                beanSumP1 = 0;
                beanSumP2 = 0;

                //System.out.println("Sum of beans reset at end of P1 loop");

                line.clear();

                for (int i : board.getBoard()) {

                    line.add(i);

                }

                line.add(p1.getChosen());
                line.add(utility);

                lines.add(line);

                //System.out.println(lines.toString());

                /***** End of Player 1's Turn *****/

            }

            while (p2.getTurn()) {

                /***** Player 2's Turn *****/

                /***** Legacy ode *****/

                //System.out.println("P2: Pick a house");
                //p2.setChosen(usrInput.nextInt());
                //p2Moves.add(p2.getChosen());

                p2.setChosen(p2.findBestMove(false, tmpBoard));

                sync();

                int utility = p2.getRating(false, tmpBoard, p2.getChosen());

                moveArray.add(p2.getChosen());
                utilArray.add(utility);

                sync();

                //if (p2.getChosen() < 7 || p2.getChosen() >= 13 || board.getBoard()[p2.getChosen()] == 0) {

                    //System.out.println("P2: Invalid house");
                    //p2.setChosen(0);

                //} else {

                    //System.out.println("P2's move: " + p2.getChosen());

                    p2.setTarget(p2.getChosen() + board.getBoard()[p2.getChosen()]);

                    targetBeans = board.getBoard()[p2.getTarget() % 13];

                    move(1, p2.getChosen(), p2.getTarget());


                    for (int i = 0; i < 6; i++) {

                        beanSumP1 += board.getBoard()[i];

                    }

                    for (int i = 7; i < 13; i++) {

                        beanSumP2 += board.getBoard()[i];

                    }

                    //System.out.println("P1: " + beanSumP1 + " beans");
                    //System.out.println("P2: " + beanSumP2 + " beans");

                    if (checkAllEmptyHouses()) {

                        endGameMove();

                        gameover = true;
                        p2.setTurn(false);
                        p1.setTurn(false);

                    } else {

                        if ( (p2.getTarget() % 13) > 6
                                && (p2.getTarget() % 13) < 13
                                && targetBeans == 0
                                && checkOppositeHouse(p2.getTarget())
                                ) {

                            bonusBeans(1, p2.getTarget());

                        }

                        if (p2.getTarget() == 13) { //Checks if player 2 gets a bonus turn

                            p2.setChosen(0);

                        } else {

                            p2.setChosen(0);
                            p2.setTurn(false);
                            p1.setTurn(true);

                        }
                    }

                //}

                //System.out.println();
                //printBoard();
                //System.out.println();

                //System.out.println("Number of beans in P1's houses: " + beanSumP1);
                //System.out.println("Number of beans in P2's houses: " + beanSumP2);

                beanSumP1 = 0;
                beanSumP2 = 0;

                //System.out.println("Sum of beans reset at end of P2 loop");

                line.clear();

                for (int i : board.getBoard()) {

                    line.add(i);

                }

                line.add(p2.getChosen());
                line.add(utility);

                lines.add(line);

                //System.out.println(lines.toString());

                //writeDataFile(1, lines.toString());

                /***** End of Player 2's Turn *****/

            }

            /***** End of Main Loop --> GAME OVER *****/

        }

        for (ArrayList<Integer> al : lines) {

            lineContent = new String();

            for (int i : al) {

                lineContent += i;
                lineContent += ", ";

            }

            lineContent += "\r\n";

            //writeDataFile(0, lineContent);
            writeDataFile(1, lineContent);

        }

        /*

        for (int i = 0; i < lines.size(); i++) {

            for (int j : lines.get(i)) {

                lineContent += j + ", ";

            }

            lineContent += moveArray.get(i) + ", ";
            lineContent += utilArray.get(i);

            //System.out.println(moveArray.get(i));
            //System.out.println(utilArray.get(i));

            dataOutput.clear();
            dataOutput.add(lineContent);

            lineContent = "";

        }

        writeDataFile(0);
        writeDataFile(1);

        */

        if (board.getBoard()[6] > board.getBoard()[13]) {

            //System.out.println("Player 1: A WINRAR IS YOU!");
            p1Wins++;

        } else if (board.getBoard()[13] > board.getBoard()[6]) {

            //System.out.println("Player 2: A WINRAR IS YOU!");
            p2Wins++;

        } else {

            //System.out.println("EVERYONE IS A WINRAR! XD");
            draws++;

        }


    }

    /**
     * Private method move() carries out each player's moves (i.e. deploys the beans) and determines if each player gets a bonus move.
     * @param s the house chosen by the player for the move
     * @param f the house where the last bean lands
     */

    private static void move(int p, int s, int f) {

        int start  = s;
        int finish = f;
        int loops  = (finish - start) / 13;

        if (p == 0) {

            if (finish == 13) { finish++; }

            if (finish <= 12) {

                fillBeans(start + 1, finish);

            } else {

                fillBeans(start + 1, 12);

                while (loops > 0) {

                    fillBeans(0, 12);
                    loops--;

                }

                fillBeans(0, finish % 13 - 1);

            }

        } else {

            if (finish % 13 == 6) { finish++; }

            if (finish <= 13) {

                fillBeans(start + 1, finish);

            } else {

                fillBeans(start + 1, 13);

                while (loops > 0) {

                    fillBeans(0, 5);
                    fillBeans(7, 13);
                    loops--;

                }

                if (finish % 13 < 6) {

                    fillBeans(0, finish % 13 - 1);

                } else {

                    fillBeans(0, 5);
                    fillBeans(7, finish % 13);

                }

            }

        }

        board.getBoard()[start] = 0;
    }

    /**
     * This private method adds one bean to houses within a specified range.
     * @param start  The lower bound index
     * @param finish The upper bound index
     */

    private static void fillBeans(int start, int finish) {

        for (int i = start; i < finish + 1; i++) {

            board.getBoard()[i] += 1;

        }

    }

    /**
     * This private method checks to see if the house opposite the target house is empty
     * @param t The index of the target house
     * @return  A boolean
     */

    private static boolean checkOppositeHouse(int t) {

        int target = t % 13;

        if (target == 6 || target == 13) {

            return false;

        } else {

            if (board.getBoard()[12 - target] != 0) {

                return true;

            } else {

                return false;

            }
        }

    }

    /**
     * This method allocates bonus beans to the appropriate player
     * @param p The player index (0 for P1 and 1 for P2)
     * @param t The target house index
     */

    private static void bonusBeans(int p, int t) {

        int target = t % 13;

        if (p == 0) {

            board.getBoard()[6] += board.getBoard()[target] + board.getBoard()[12 - target];
            board.getBoard()[target] = 0;
            board.getBoard()[12 - target] = 0;

        } else {

            board.getBoard()[13] += board.getBoard()[target] + board.getBoard()[12 - target];
            board.getBoard()[target] = 0;
            board.getBoard()[12 - target] = 0;

        }

        beanSumP1 = 0;
        beanSumP2 = 0;

        for (int i = 0; i < 6; i++) {

            beanSumP1 += board.getBoard()[i];

        }

        for (int i = 7; i < 13; i++) {

            beanSumP2 += board.getBoard()[i];

        }

        if (checkAllEmptyHouses()) {

            endGameMove();

            gameover = true;

        }

    }

    /**
     * This method checks to see if any one of the players has six empty houses.
     * @return A boolean value
     */

    private static boolean checkAllEmptyHouses() {

        if ((beanSumP1 == 0) || (beanSumP2 == 0)) {

            return true;

        } else {

            return false;

        }

    }

    /**
     * This method is invoked when the game over conditions are met; it gathers all the remaining beans on the board and allocate them to the appropriate player.
     */

    private static void endGameMove() {

        if (beanSumP1 == 0) {

            board.getBoard()[13] += beanSumP2;

            for (int i = 7; i < 13; i++) {

                board.getBoard()[i] = 0;

            }

        } else if (beanSumP2 == 0) {

            board.getBoard()[6] += beanSumP1;

            for (int i = 0; i < 6; i++) {

                board.getBoard()[i] = 0;

            }

        }

    }

    /**
     * This private method outputs the board to console.
     */

    private static void printBoard() {

        for (int i = (board.getBoard().length - 1); i > 6; i--) {

            System.out.print(board.getBoard()[i] + " ");

        }

        System.out.println();
        System.out.print("  ");

        for (int i = 0; i < (board.getBoard().length / 2); i++) {

            System.out.print(board.getBoard()[i] + " ");

        }

        System.out.println();

    }

    private static void sync() {

        int[] tmpIntArray = new int[board.getBoard().length];

        for (int i = 0; i < board.getBoard().length; i++) {

            tmpIntArray[i] = board.getBoard()[i];

        }

        for (int i = 0; i < board.getBoard().length; i++) {

            tmpBoard.getBoard()[i] = tmpIntArray[i];

        }

    }

    private static void writeDataFile(int pID, String str) {

        try {

            if (pID == 0) {

                fWriter = new FileWriter("data.txt", true);

            } else {

                fWriter = new FileWriter("data2.txt", true);

            }

            bWriter = new BufferedWriter(fWriter);

            bWriter.write(str);

            bWriter.close();

        } catch (IOException e) {

            System.out.println(e);

        }

    }

}
