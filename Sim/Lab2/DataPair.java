public class DataPair {
    private int h, p;

    public DataPair(int h, int p) {
        this.h = h;
        this.p = p;
    }

    public void setH(int h) { this.h = h; }
    public void setP(int p) { this.p = p; }

    public int getH() { return h; }
    public int getP() { return p; }

    public String toString() {
        return "h = " + h + ", p = " + p;
    }
}
