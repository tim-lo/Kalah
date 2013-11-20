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

    public void addBean() {
        this.beanNum += 1;
    }

    public void rmBeans() {
        this.beanNum = 0;
    }

    public int getBeanNum() {
        return this.beanNum;
    }
}
