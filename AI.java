import java.util.ArrayList;

public class AI {

    private boolean   maximising;
    private boolean   turn;
    private Board     board;
    private final int depthThreshold = 3;

    public AI(boolean max, Board b) {

        this.maximising = max;
        this.board      = b;

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

        b.move(max, m);

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

}
