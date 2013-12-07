public class Kalah {

    private static Board   tmp;
    private static Board   board;
    private static boolean gameover;

    public static void main(String[] args) {

        tmp   = new Board();
        board = new Board();

        AI p1 = new AI(true, tmp);
        AI p2 = new AI(false, tmp);

        /*
        int[] nb = new int[]{12,
                             2,0,1,0,8,0,
                             10,0,0,1,2,0,
                             12};

        tmp.setIntArray(nb);
        board.setIntArray(nb);
        */

        gameover = false;

        int bestMove = 0;

        System.out.println("/=-=-=-=-=·=·=-=-=-=-\\");
        System.out.println("|  Welcome to Kalah  |");
        System.out.println("| </</</</<>\\>\\>\\>\\> |");
        System.out.println("\\-=-=-=-=·=·=-=-=-=-=/");
        System.out.println();
        System.out.println("Game commencing...");
        System.out.println();

        board.printBoard();

        p1.setTurn(true);
        p2.setTurn(false);

        while (!gameover) {

            while (p1.getTurn()) {



            }

        }

    }

    public static void sync() {

        tmp.setBoard(board);

    }

}

/*
@łe¶ŧ←↓→øþ
æßðđŋħĸł
«»¢“”nµ─·
*/
