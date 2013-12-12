public class Board {

    private int[] board;

    public Board() {

        this.board = new int[14];

        for (int i = 0; i < 14; i++) {

            if (i == 6 || i == 13) {     //Houses Nos.6 and 13 are respectively the stores of player 1 and player 2

                this.board[i] = 0;       //Zero beans for the stores

            } else {

                this.board[i] = 4;       //Four beans for each of the houses

            }

        }

    }

    public Board(int[] b) {

        this.board = new int[14];
        this.board = b;

    }

    public int getBeans(int i) {

        return this.board[i];

    }

    public void setBeans(int i, int b) {

        this.board[i] = b;

    }

    public int[] getBoard() {

        return this.board;

    }

    public void setBoard(Board b) {

        this.board = b.getBoard();

    }

    public void setBoard(int[] a) {

        this.board = a;

    }

    public void move(boolean max, int m) {

        int start  = m;

        int finish = m + this.board[m];
        int loops  = (finish - start) / 13;

        if (max) {

            if (finish == 13) { finish++; }

            if (finish <= 12) {

                fillBeans(start + 1, finish);

            } else {

                fillBeans(start + 1, 12);

                while (loops > 0) {

                    fillBeans(0, 12);
                    loops--;

                }

                fillBeans(0, finish % 13);

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

                    fillBeans(0, finish % 13);

                } else {

                    fillBeans(0, 5);
                    fillBeans(7, finish % 13);

                }

            }

        }

        this.setBeans(start, 0);

    }

    private void fillBeans(int s, int f) {

        for (int i = s; i < f + 1; i++) {

            this.setBeans(i, this.getBeans(i) + 1);

        }

    }

    public void printBoard() {

        for (int i = (this.board.length - 1); i > 6; i--) {

            System.out.print(this.board[i] + " ");

        }

        System.out.println();
        System.out.print("  ");

        for (int i = 0; i < (this.board.length / 2); i++) {

            System.out.print(this.board[i] + " ");

        }

        System.out.println();
        System.out.println();

    }

}
