package GUI_FX_Server;

import Distributore.Coins;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.StringTokenizer;

public class HistogramChart extends BarChart {

    final static String[] Monete = {"0.05", "0.10", "0.20", "0.50", "1", "2"};
    final double a = 30.0;  // Valore a cazzo
    Coins coins = new Coins();

    public HistogramChart(Axis xAxis, Axis yAxis) {
        super(xAxis, yAxis);
    }

    public void start(Stage stage) {
        Toolbar1 toolbar1 = new Toolbar1(stage);
        MenuBar1 menuBar1 = new MenuBar1(stage);
        VBox vBox = new VBox();

        vBox.getChildren().addAll(toolbar1,menuBar1);
        vBox.setFillWidth(true);

        stage.setTitle("Monete rimanenti:");

        final NumberAxis xAxis = new NumberAxis();
        final CategoryAxis yAxis = new CategoryAxis();
        final BarChart<Number, String> bc = new BarChart<>(xAxis, yAxis);
        bc.setTitle("Coins");
        xAxis.setLabel("Numero monete rimaste");
        yAxis.setLabel("Tagli di monete [€]");

        XYChart.Series series1 = new XYChart.Series();

        // Trovo numero di monete
        String numCoins = coins.moneyOnFile();
        int money[] = new int[Monete.length];
        StringTokenizer tokenizer = new StringTokenizer(numCoins, "\t");

        for(int i = 0; i < Monete.length; i++) {
            if (tokenizer.hasMoreTokens()) {
                money[i] = Integer.parseInt(tokenizer.nextToken());
            }
        }

        // Inizializzazione valori a random, giusto per vedere il grafico
        for (int i = 0; i < Monete.length; i++) {
            series1.getData().add(new XYChart.Data(money[i], Monete[i]));
        }
        GridPane gPane = new GridPane();                //Creazione GridPanel per aggiungere menubar e BarChart
        gPane.setPrefSize(800, 550);
        gPane.setMinSize(800, 550);
        ColumnConstraints Col = new ColumnConstraints();
        Col.setHgrow(Priority.ALWAYS);
        gPane.getColumnConstraints().addAll(Col);
        gPane.addRow(0,vBox);
        gPane.addRow(1,bc);

        Scene scene = new Scene(gPane, 800, 600);
        bc.getData().addAll(series1);
        stage.setScene(scene);
        stage.show();
    }
}