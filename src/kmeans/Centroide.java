package kmeans;

import java.util.ArrayList;

public class Centroide {
    private String id;
    private int d;
    private double[] dimensao;
    private ArrayList<Dado> dado;

    public Centroide() {
    }

    public Centroide(String id, int d) {
        this.id = id;
        this.d = d;
        dimensao = new double[d];
        dado = new ArrayList();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getD() {
        return d;
    }

    public void setD(int d) {
        this.d = d;
    }

    public double[] getDimensao() {
        return dimensao;
    }

    public void setDimensao(double[] dimensao) {
        this.dimensao = dimensao;
    }
    
    public double getDimensao(int pos) {
        return dimensao[pos];
    }

    public void setDimensao(int pos, double info) {
        dimensao[pos] = info;
    }

    public ArrayList<Dado> getDado() {
        return dado;
    }

    public void setDado(ArrayList<Dado> dado) {
        this.dado = dado;
    }
    
    public double getDado(int posA, int posV) {
        return dado.get(posA).getDimensoes(posV);
    }

    public void setDado(int pos, Dado info) {
        dado.add(pos, info);
    }
    
    public void sorteiaPontos() {
        for (int i = 0; i < dimensao.length; i++)
            dimensao[i] = Math.random() * 10;
    }
}
