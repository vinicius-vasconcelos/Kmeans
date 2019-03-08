package kmeans;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BubbleChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;

public class FXMLDocumentController implements Initializable {

    private ArrayList<Centroide> centroides; //quantos centroides exsietem dentro do arquivo
    private ArrayList<Dado> dados; //array de dados trazidos do arquivo
    private RandomAccessFile arquivo; //arquivo escolhido

    @FXML
    private BubbleChart<?, ?> bcGrafico;
    @FXML
    private JFXComboBox<String> cbVisao1;
    @FXML
    private JFXComboBox<String> cbVisao2;
    @FXML
    private JFXButton btGerar;
    @FXML
    private JFXButton btLimpar;
    @FXML
    private AnchorPane pnAleatorio;
    @FXML
    private JFXTextField tfCentroid;
    @FXML
    private JFXButton btOk;
    @FXML
    private JFXRadioButton rbAleatorio;
    @FXML
    private JFXRadioButton rbArquivo;
    @FXML
    private JFXButton btArquivo;
    @FXML
    private JFXTextField tfDimensoes;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        limpaTela();
    }

    @FXML
    private void evtBtGerar(ActionEvent event) { // criar novas perspectivas de visão
       String[] x = cbVisao1.getSelectionModel().getSelectedItem().split(":");
       String[] y = cbVisao2.getSelectionModel().getSelectedItem().split(":");
       plotar(Integer.parseInt(x[1]), Integer.parseInt(y[1]));
    }

    @FXML
    private void evtBtLimpar(ActionEvent event) { //limpar tela para nova interação
        limpaTela();
    }

    @FXML
    private void evtBtOk(ActionEvent event) { //modificar configurações no modo aleatório
        int val = 0;
        int val2 = 0;
      
        try { //transformando centroide em inteiro
            val = Integer.parseInt(tfCentroid.getText());
            val2 = Integer.parseInt(tfDimensoes.getText());
        } catch (NumberFormatException e) {
            val = 0;
            val2 = 0;
        }

        if (val == 0) // se não for número, apenas limpa a tela
            limpaTela();
        else { //se for numero, sorteia valores e plota na tela

            for (int i = 0; i < val; i++){  //repetição dos Centroides escolhidos pelo usuario
                Centroide cen = new Centroide("Centroide " + i, val2);
                cen.sorteiaPontos();
                centroides.add(cen);
            }

            for (int i = 0; i < 1000; i++){  // repetição da criação de dados aleatórios
                Dado dad =  new Dado(val2);
                dad.sorteiaPontos();
                dados.add(dad);
            }
        }
        carregaCombos();
        reposicionar(); // aplica o algoritmo de kameans nos dados
    }

    @FXML
    private void evtRbAleatorio(ActionEvent event) { //radio button aleatório
        rbAleatorio.setSelected(true);
        pnAleatorio.setDisable(false);

        rbArquivo.setSelected(false);
        btArquivo.setDisable(true);
    }

    @FXML
    private void evtRbArquivo(ActionEvent event) { //radio button arquivo
        rbAleatorio.setSelected(false);
        pnAleatorio.setDisable(true);

        rbArquivo.setSelected(true);
        btArquivo.setDisable(false);
    }

    @FXML
    private void evtBtArquivo(ActionEvent event) { //abre arquivo e alimenta arrayList de dados
        int d = 0;
        String ler = "";
        String nome = "teste";
        
        try {
            arquivo = new RandomAccessFile("teste.txt", "r");

            ler = arquivo.readLine();
    
            while (ler != null) { // lendo do arquivo para o array de dados
                String[] linha = ler.split(",");
                d = (linha.length - 1); // quantas dimensões tem (X, Y, Z, ....)
                
                Dado dad = new Dado(d);
                for (int i = 0; i < d; i++)
                    dad.setDimensoes(i, Double.parseDouble(linha[i]));
                dados.add(dad);
                
                String classe = linha[d];
                if (!classe.equals(nome)) { //achando centroides do arquivo
                    nome = classe;
                    Centroide cen = new Centroide(nome, d);
                    cen.sorteiaPontos();
                    centroides.add(cen);
                }
                ler = arquivo.readLine();
                
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        carregaCombos();
        evtRbAleatorio(event); // atualiza tela
        
        reposicionar(); // aplica o algoritmo de kameans nos dados
    }

    private void limpaTela() { //limpa tela para nova interação
        dados = new ArrayList();
        centroides = new ArrayList();

        rbAleatorio.setSelected(true);
        rbArquivo.setSelected(false);
        btArquivo.setDisable(true);

        tfCentroid.setText("");
        tfDimensoes.setText("");
        pnAleatorio.setDisable(false);
        
        bcGrafico.getData().clear();
        
    }
    
    private void carregaCombos() {
        if(!cbVisao1.getItems().isEmpty()) {
            cbVisao1.getItems().clear();
            cbVisao2.getItems().clear();
        }

        for (int i = 0; i < centroides.get(0).getD(); i++) {
            cbVisao1.getItems().add("P:" + i );
            cbVisao2.getItems().add("P:" + i );
        }
        cbVisao1.getSelectionModel().select(0);
        cbVisao2.getSelectionModel().select(0);
    }

    private void limpaArrayDados() { //percorre array de centroides removendo todos os pontos dele, para novo calculo
        for (int i = 0; i < centroides.size(); i++) 
            if(!centroides.get(i).getDado().isEmpty())
                    centroides.get(i).getDado().clear();
                
    }
  
    private void kMeans() {
        double menor = 0;
        double menorTodos = 9000;
        int classe = 0;
        int i, j, k;

        limpaArrayDados(); // limpa array de dados dos centroides para novo calculo
    
        for (i = 0; i < dados.size(); i++) { // repetição dos dados
            for (j = 0; j < centroides.size(); j++) { // repetição dos centroides
                for (k = 0; k < centroides.get(0).getD(); k++)  // repetição das dimensões
                    menor += Math.pow(dados.get(i).getDimensoes(k) - centroides.get(j).getDimensao(k), 2);
              
                menor =  Math.sqrt(menor);
                if (menor < menorTodos) {
                    menorTodos = menor;
                    classe = j;
                }
                menor = 0;
            }
            menorTodos = 9000;
            centroides.get(classe).getDado().add(dados.get(i));
        }
    }

    private void reposicionar() {
        int i, j, k, l;
        boolean fim = false;

       do {
            kMeans();
            
            String antigo = geraStr(); //salva X, Y, Z, ... dos centroides antes de reposicionar
            
            for (i = 0; i < centroides.size(); i++) {
                if (!centroides.get(i).getDado().isEmpty()) {
                    double somador[] = new double[centroides.get(0).getD()];
                    for (j = 0; j < centroides.get(i).getDado().size(); j++) // repertição para somar e achar a média para novo ponto do centróide
                        for (k = 0; k < centroides.get(i).getD(); k++)  // repetição para andar dentro das dimensoes
                            somador[k] += centroides.get(i).getDado().get(j).getDimensoes(k);
                   
                    //achando a nova média
                    for ( l = 0; l < somador.length; l++)
                        centroides.get(i).setDimensao(l, somador[l] / j);
                }
            }
           
            String novo = geraStr(); // salva X, Y, Z, .... dos centroides dps de reposicionar
            
            if (comparar(antigo, novo)) // compara duas strings dos pontos do centroide e ve se mudou
                fim = true;
            
        } while (fim == false);
        plotar(0,1); // desenhar na tela
    }
    
    private String geraStr() {
        String str = "";
        for (int i = 0; i < centroides.size(); i++) 
            for (int j = 0; j < centroides.get(i).getD(); j++) 
                str += centroides.get(i).getDimensao(j) + ", ";
        return str;
    }

    private boolean comparar(String antigo, String novo) {
        return antigo.equalsIgnoreCase(novo);
    }

    private void plotar(int x, int y) {
        if (!bcGrafico.getData().isEmpty())
            bcGrafico.getData().clear();

        for (int i = 0; i < centroides.size(); i++) {
            //plotando centroides
            XYChart.Series cluster = new XYChart.Series(); //criando centroides

            cluster.setName(centroides.get(i).getId()); // dando nome ao centroide
            cluster.getData().add(new XYChart.Data(centroides.get(i).getDimensao(x), centroides.get(i).getDimensao(y), 0.25)); // plotando centroide

            for (int j = 0; j < centroides.get(i).getDado().size(); j++) 
                cluster.getData().add(new XYChart.Data(centroides.get(i).getDado().get(j).getDimensoes(x), centroides.get(i).getDado().get(j).getDimensoes(y), 0.1)); // plotando dado
            
            bcGrafico.getData().add(cluster); //atualizando grafico
        }
    }
    
    private void exibe() {
        for (int i = 0; i < centroides.size(); i++) {
            System.out.println("-----------------------------");
            System.out.println(centroides.get(i).getId());
            for (int j = 0; j < centroides.get(i).getDimensao().length; j++) {
                System.out.println("D " + j + ": " + centroides.get(i).getDimensao(j));
            }
            System.out.println("---- pontos: ");
            for (int j = 0; j < centroides.get(i).getDado().size(); j++) {
                System.out.println("P " + j + ": ");
                for (int k = 0; k < centroides.get(i).getDado().get(j).getDimensoes().length; k++) {
                    System.out.println("\tD " + k + ": " +centroides.get(i).getDado().get(j).getDimensoes(k));
                }
            }
            
        }
    }
}
