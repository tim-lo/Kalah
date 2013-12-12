import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class AI3 {

    private boolean maximising;
    private boolean turn;
    //private Board board;
    private int     chosen;
    private int     target;

    private String fileName;
    private ArrayList<int[]> moveDB;

    public AI3(boolean max, Board b) {

        this.maximising = max;
        //this.board = b;
        this.moveDB = new ArrayList<int[]>();

        this.fileName = "C:\\Users\\Tom\\IdeaProjects\\Kalah Final\\src\\data2.txt";

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

        Random rand = new Random();
        int size = findPossibleMoves(max, b).length;

        return findPossibleMoves(max, b)[rand.nextInt(size)];

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

        ArrayList<String> stringAL    = new ArrayList<String>();
        Scanner s                     = null;
        //BufferedReader br             = null;

        try {

            s = new Scanner(new BufferedReader(new FileReader(this.fileName)));

            /*

            br = new BufferedReader(new FileReader(this.fileName));

            if (br.readLine() == null) {



            }

            */

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

    }

}
