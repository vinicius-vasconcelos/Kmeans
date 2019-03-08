package kmeans;

public class Dado {
    private int d;
    private double[] dimensoes;

    public Dado() {
    }

    public Dado(int d) {
        this.d = d;
        dimensoes =  new double[d];
    }

    public int getD() {
        return d;
    }

    public void setD(int d) {
        this.d = d;
    }

    public double[] getDimensoes() {
        return dimensoes;
    }

    public void setDimensoes(double[] dimensoes) {
        this.dimensoes = dimensoes;
    }

    public double getDimensoes(int pos) {
        return dimensoes[pos];
    }

    public void setDimensoes(int pos, double info) {
        dimensoes[pos] = info;
    }
    
    public void sorteiaPontos() {
        for (int i = 0; i < dimensoes.length; i++)
            dimensoes[i] = Math.random() * 10;
    }
}
