package GUI_FX_Server;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.awt.*;

public class HomePage extends Application {

    //final Stage stage = new Stage(StageStyle.UTILITY);
    //MenuBar menuBar = new MenuBar1();

    @Override
    public void start(Stage primaryStage) {

        HistogramChart coinsChart = new HistogramChart(new CategoryAxis(), new NumberAxis());

        // Definizione dello stage principale e della barra del menu
        primaryStage.setTitle("Home");
        MenuBar menuBar = new MenuBar1(primaryStage);

        // Creazione GridPane
        GridPane gPane = new GridPane();
        gPane.setPrefSize(800, 550); // Size base del pane all'avvio
        gPane.setMinSize(800, 550);  //Size minimo del pane
        ColumnConstraints Col = new ColumnConstraints();    // Serve per far crescere tutta la colonna quando si
        Col.setHgrow(Priority.ALWAYS);                      // aumenta la finestra
        gPane.getColumnConstraints().addAll(Col);

        // Creazione scritta correlata da un logo
        Label label = new Label("Welcome to project Nobildonno Home Page");
        label.setTextFill(Color.FIREBRICK);
        label.setFont(Font.font("Rockwell", 50));
        label.setWrapText(true);

        ImageView img = new ImageView("GUI_FX_Server/download.jpg");
        img.setFitHeight(250);
        img.setFitWidth(250);

        label.setGraphic(img);
        //label.setAlignment(Pos.BOTTOM_CENTER);    //Dovrebbe posizionare il testo ma non funziona

        // Creazione VBox, commento da rivedere (@LUCE)
        VBox vBox = new VBox(menuBar);
        vBox.setFillWidth(true);

        // Creazione link al sito del progetto
        VBox linkBox = new VBox();
        linkBox.setFillWidth(true);

        Hyperlink hyperlink = new Hyperlink();
        hyperlink.setText("Click here for Project L-18 web site!");
        hyperlink.setAlignment(Pos.BOTTOM_CENTER);

        final String url = "https://github.com/IngSW-unipv/Progetto-L18";
        final WebView browser = new WebView();
        final WebEngine webEngine = browser.getEngine();

        hyperlink.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                //webEngine.load(url);     // MJ: Pagina web mostrata nella finestra del server
                getHostServices().showDocument(url);    // MJ: Pagina web aperta nel browser
            }
        });

        HBox hbox = new HBox();
        hbox.getChildren().addAll(hyperlink);
        linkBox.getChildren().addAll(hbox, browser);
        VBox.setVgrow(browser, Priority.ALWAYS);

        // Creazione bottoni
        ButtonBar buttonBar = new ButtonBar();

        Button menuButton = new Button("Menu");
        ButtonBar.setButtonData(menuButton, ButtonBar.ButtonData.OTHER);
        menuButton.setPrefSize(85, 50);

        // Gestione pressione del bottone "menu"
        menuButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                menuButton.setText("Pressed");
                coinsChart.start(primaryStage);
            }
        });

        //TODO MJ: modificare eventhandler ai bottoni (button.setOnAction)

        Button statsButton = new Button("Statistiche");
        ButtonBar.setButtonData(statsButton, ButtonBar.ButtonData.OTHER);
        statsButton.setPrefSize(85, 50);

        buttonBar.getButtons().addAll(menuButton, statsButton);

        // Aggiunta elementi nel Pane
        gPane.setVgap(100);
        gPane.add(vBox, 0,0);
        gPane.add(label, 0,1);
        gPane.add(buttonBar, 0, 2);
        gPane.add(linkBox, 0, 3);
        gPane.setHalignment(label, HPos.CENTER);

        Scene scene = new Scene(gPane, 800, 550);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true); // Lo ingrandisco subito fullscreen
        primaryStage.show();

    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}