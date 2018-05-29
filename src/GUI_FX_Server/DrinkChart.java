package GUI_FX_Server;

import javafx.collections.ObservableList;
import javafx.scene.chart.*;
import javafx.scene.layout.BorderPane;

public class DrinkChart extends BarChart {

    private ObservableList<String> data;
    private ObservableList<String> menu;

    public DrinkChart(Axis xAxis, Axis yAxis, ObservableList<String> data, ObservableList<String> menu) {
        super(xAxis, yAxis);
        this.data = data;
        this.menu = menu;
    }

    public BorderPane initChart() {
        BorderPane mainTabPane = new BorderPane();

        if(data == null)
            return mainTabPane;

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();

        final BarChart<String,Number> itemsChart = new BarChart<>(xAxis, yAxis);
        itemsChart.setTitle("Quantità rimanenti bevande");
        itemsChart.setLegendVisible(false);

        xAxis.setLabel("Bevanda");
        yAxis.setLabel("Quantità");

        XYChart.Series series1 = new XYChart.Series();

        for(int i = 0; i < data.size(); i++) {
            if (data.get(i).startsWith("0")) {
                String[] split = data.get(i).split("\t");

                //TODO sostituire ID con nome bevanda
                series1.getData().add(new XYChart.Data(split[0], Double.parseDouble(split[1])));
            }
        }

        itemsChart.getData().add(series1);

        mainTabPane.setCenter(itemsChart);

        return mainTabPane;
    }
}