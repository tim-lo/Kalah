import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class AI {

    private boolean maximising;
    private boolean turn;
    //private Board board;

    private BufferedReader inputStream;

    private Scanner s;

    private ArrayList<int[]> moveDB;

    private final int depthThreshold = 3;

    public AI(boolean max, Board b) throws IOException {

        this.maximising  = max;
        //this.board     = b;
        this.moveDB      = new ArrayList<int[]>();
        this.inputStream = null;
        this.s           = null;

        ArrayList<String> stringAL = new ArrayList<String>();

        try {

            this.s = new Scanner(new BufferedReader(new FileReader("data.txt")));

            while (s.hasNext()) {

                stringAL.add(s.nextLine());

            }

        } finally {

            if (s != null) {

                s.close();

            }

        }

        for (String s : stringAL) {

            String[] stringA;
            int[] intA = new int[s.length()];

            stringA = s.split("");

            for (int i = 0; i < s.length(); i++) {

                try {

                    intA[i] = Integer.parseInt(stringA[i].trim());

                } catch (Exception e) {

                    System.out.println(e);

                }

            }

            this.moveDB.add(intA);

        }

        for (int[] iA : this.moveDB) {

            for (int i : iA) {

                System.out.print(i);

            }

            System.out.println();

        }

        /*
        for (String s : tmpAL) {

            System.out.println(s);

        }
        */

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

                    bestMove = tmpBestMove;

                }

            } else {

                tmpBestMove   = m;
                tmpBestRating = minimax(m, this.depthThreshold, b, false);

                if (tmpBestRating < bestRating) {

                    bestMove = tmpBestMove;

                }

            }

        }

        return bestMove;

    }

    private int getRating(boolean max, Board b, int m) {

        int difference;
        Board tmpBoard = b;

        tmpBoard.move(max, m);

        if (max == true) {

            difference = b.getBeans(6) - b.getBeans(13);

        } else {

            difference = b.getBeans(13) - b.getBeans(6);

        }

        /*/////////////////////////////////

        int[] currentState = new int[15];

        for (int i = 0; i < b.getIntArray().length; i++) {

            currentState[i] = b.getIntArray()[i];

        }

        /////////////////////////////////*/

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

}
