package GUI_FX_Server;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class StatsPage extends GridPane {
    private GridPane mainPanel = new GridPane();
    private TabPane tabPane = new TabPane();
    private Tab tab1 = new Tab();
    private Tab tab2 = new Tab();
    private Tab tab3 = new Tab();
    private Tab tab4 = new Tab();
    private Tab tab5 = new Tab();
    private ObservableList<String> obsvStats;
    private ObservableList<String> obsvData;
    private ObservableList<String> obsvCoins;
    private ObservableList<String> obsvMenu;

    public StatsPage(Stage stage, ObservableList<String> obsvStats, ObservableList<String> obsvData,
                     ObservableList<String> obsvCoins, ObservableList<String> obsvMenu) {
        Group root = new Group();
        this.obsvStats = obsvStats;
        this.obsvCoins = obsvCoins;
        this.obsvData = obsvData;
        this.obsvMenu = obsvMenu;

        BorderPane borderPane = new BorderPane();

        tab1.setText("Monete");
        tab2.setText("Acquisto bevande");
        tab3.setText("Utilizzo");
        tab4.setText("Bevande");
        tab5.setText("Varie");

        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        HistogramChart coinsChart = new HistogramChart(new CategoryAxis(), new NumberAxis(), obsvCoins);
        DrinkPieChart pie = new DrinkPieChart(obsvStats);
        UsageChart usage = new UsageChart(new NumberAxis(), new NumberAxis(), obsvStats);
        DrinkChart drinks = new DrinkChart(new CategoryAxis(), new NumberAxis(), obsvData, obsvMenu);
        ItemsHistogram itemsChart = new ItemsHistogram(new CategoryAxis(), new NumberAxis(), obsvData);

        tab1.setContent(coinsChart.setBars());
        tab2.setContent(pie.setChart());
        tab3.setContent(usage.setGraph());
        tab4.setContent(drinks.initChart());
        tab5.setContent(itemsChart.setBars());

        obsvStats.addListener((ListChangeListener) change -> Platform.runLater(() -> {
            // Aggiorna UI
            System.out.println("Detected a change! ");
            tab2.setContent(pie.setChart());
            tab3.setContent(usage.setGraph());
        }));

        obsvCoins.addListener((ListChangeListener) change -> Platform.runLater(() -> {
            // Aggiorna UI
            System.out.println("Detected a change! ");
            tab1.setContent(coinsChart.setBars());
        }));

        obsvData.addListener((ListChangeListener) change -> Platform.runLater(() -> {
            // Aggiorna UI
            System.out.println("Detected a change! ");
            tab4.setContent(drinks.initChart());
            tab5.setContent(itemsChart.setBars());
        }));

        tabPane.getTabs().addAll(tab1, tab2, tab3, tab4, tab5);

        // Utilizzo di tutto lo spazio disponibile da parte del pannello

        borderPane.prefHeightProperty().bind(stage.heightProperty());
        borderPane.prefWidthProperty().bind(stage.widthProperty());
        borderPane.setCenter(tabPane);

        mainPanel.addRow(0, borderPane);
        mainPanel.prefHeightProperty().bind(stage.heightProperty());
        mainPanel.prefWidthProperty().bind(stage.widthProperty());
    }

    /**
     * Funzione per aprire uno specifico tab all'apertura della pagina.
     * @param i indice per selezionare un tab; valore iniziale '0'.
     */
    public void OpenTab(int i){
        tabPane.getSelectionModel().select(i);
    }

    public GridPane getMainPanel() {
        return mainPanel;
    }
}
