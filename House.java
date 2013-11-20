package Kalah;

public class House {

    private int id;
    private int beanNum;

    public House(int i, int n) {
        this.id      = i;
        this.beanNum = n;
    }

    public int getID() {
        return this.id;
    }

    public void setBeans(int i) {
        this.beanNum = i;
    }

    public void addBeans(int i) {
        this.beanNum += i;
    }

    public void rmBeans(int i) {
        if (i <= this.beanNum) {
            this.beanNum -= i;
        } else {
            System.out.println("Illegal argument.");
        }
    }

    public int getBeanNum() {
        return this.beanNum;
    }
}
