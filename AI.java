import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class AI {

    private boolean maximising;
    private boolean turn;
    //private Board board;
    private int     chosen;
    private int     target;

    private String fileName;
    private ArrayList<int[]> moveDB;
    private final int depthThreshold = 4;

    public AI(boolean max, Board b) {

        this.maximising = max;
        //this.board = b;
        this.moveDB = new ArrayList<int[]>();

        this.fileName = "C:\\Users\\Tom\\IdeaProjects\\Kalah Final\\src\\data.txt";

        try {

            readDataFile();

        } catch (IOException e) {

            System.out.println(e);

        }

    }

    public boolean getTurn() {

        return this.turn;

    }

    public void setTurn(boolean b) {

        this.turn = b;

    }

    private int[] findPossibleMoves(boolean max, Board b) {

        ArrayList<Integer> possibleMoves = new ArrayList<Integer>();
        int pid;

        if (max) { pid = 0; } else { pid = 1; }

        for (int i = 0; i < 6; i++) {

            if (b.getBeans(i + 7 * pid) > 0) {

                possibleMoves.add(i + 7 * pid);

            }

        }

        return convert(possibleMoves);

    }

    public int findBestMove(boolean max, Board b) {

        int bestMove;
        int bestRating;
        int tmpBestMove;
        int tmpBestRating;

        if (max == true) {

            bestMove = 0;
            bestRating = Integer.MIN_VALUE;

        } else {

            bestMove = 7;
            bestRating = Integer.MAX_VALUE;

        }

        for (int m : findPossibleMoves(max, b)) {

            if (max == true) {

                tmpBestMove = m;
                tmpBestRating = minimax(m, this.depthThreshold, b, true);

                if (tmpBestRating > bestRating) {

                    bestRating = tmpBestRating;
                    bestMove   = tmpBestMove;

                }

            } else {

                tmpBestMove = m;
                tmpBestRating = minimax(m, this.depthThreshold, b, false);

                if (tmpBestRating < bestRating) {

                    bestRating = tmpBestRating;
                    bestMove   = tmpBestMove;

                }

            }

        }

        /////////////////////////////////

        int[] currentState = new int[14];
        boolean matchFound = true;
        int     matchID    = 0;

        for (int i = 0; i < b.getBoard().length; i++) {

            currentState[i] = b.getBoard()[i];

        }

        for (int i = 0; i < this.moveDB.size(); i++) {

            for (int j = 0; j < currentState.length; j++) {

                try {

                    if (currentState[j] != this.moveDB.get(i + 1)[j]) {

                        matchFound = false;

                    }

                } catch (Exception e) {

                    //System.out.println(e);

                }

            }

            if (matchFound) {

                matchID = i + 1;

            }

        }

        if (matchFound) {

            if (this.isMaximising()) {

                if (bestRating <= this.moveDB.get(matchID)[15]) {

                    bestMove = this.moveDB.get(matchID)[14];

                }

            } else {

                if (bestRating >= this.moveDB.get(matchID)[15]) {

                    bestMove = this.moveDB.get(matchID)[14];

                }

            }

        }

        /////////////////////////////////

        return bestMove;

    }

    public int getChosen() {

        return this.chosen;

    }

    public void setChosen(int i) {

        this.chosen = i;

    }

    public int getTarget() {

        return this.target;

    }

    public void setTarget(int i) {

        this.target = i;

    }

    public int getRating(boolean max, Board b, int m) {

        int difference;
        Board tmpBoard = b;

        tmpBoard.move(max, m);

        if (max == true) {

            difference = b.getBeans(6) - b.getBeans(13);

        } else {

            difference = b.getBeans(13) - b.getBeans(6);

        }

        return difference;

    }

    private int minimax(int m, int dt, Board b, boolean max) {

        int bestValue;
        int val;

        if (dt == 0 || findPossibleMoves(max, b).length == 0) {

            return getRating(max, b, m);

        } else if (max == true) {

            bestValue = Integer.MIN_VALUE;

            for (int i : findPossibleMoves(true, b)) {

                Board nb = b;
                nb.move(max, m);

                val = minimax(i, dt - 1, nb, false);
                bestValue = Math.max(bestValue, val);

            }

            return bestValue;

        } else {

            bestValue = Integer.MAX_VALUE;

            for (int i : findPossibleMoves(false, b)) {

                Board nb = b;
                nb.move(max, m);

                val = minimax(i, dt - 1, nb, true);
                bestValue = Math.min(bestValue, val);

            }

            return bestValue;

        }

    }

    private int alphabeta(int m, int dt, int a, int b, Board B, boolean max) {

        if (dt == 0 || findPossibleMoves(max, B).length == 0) {

            return getRating(max, B, m);

        } else if (max == true) {

            for (int i : findPossibleMoves(max, B)) {

                Board nB = B;
                nB.move(max, m);

                a = Math.max(a, alphabeta(i, dt - 1, a, b, nB, false));

                if (b <= a) {

                    break;

                }

            }

            return a;

        } else {

            for (int i : findPossibleMoves(max, B)) {

                Board nB = B;
                nB.move(max, m);

                b = Math.min(b, alphabeta(i, dt - 1, a, b, nB, true));

                if (b <= a) {

                    break;

                }

            }

            return b;

        }

    }

    public boolean isMaximising() {

        return this.maximising;

    }

    private int[] convert(ArrayList<Integer> al) {

        int[] a = new int[al.size()];

        for (int i = 0; i < al.size(); i++) {

            try {

                a[i] = al.get(i);

            } catch (NullPointerException e) {

                System.out.println("ArrayList to Array conversion failed: NullPointerException thrown.");

            }

        }

        return a;

    }

    private void readDataFile() throws IOException {

        ArrayList<String> stringAL = new ArrayList<String>();
        Scanner s = null;

        try {

            s = new Scanner(new BufferedReader(new FileReader(this.fileName)));

            while (s.hasNext()) {

                stringAL.add(s.nextLine());

            }

        } finally {

            if (s != null) {

                s.close();

            }

        }

        for (String str : stringAL) {

            String[] strArray = str.split(",");
            int[] intArray = new int[strArray.length];

            for (int i = 0; i < strArray.length; i++) {

                intArray[i] = Integer.parseInt(strArray[i]);

            }

            this.moveDB.add(intArray);

        }

        /*

        for (int[] iA : this.moveDB) {

            for (int i : iA) {

                if (i < 10 && i >= 0) {

                    System.out.print(i + "  ");

                } else {

                    System.out.print(i + " ");

                }

            }

            System.out.println();

        }

        */

    }

}
