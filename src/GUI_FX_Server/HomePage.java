package GUI_FX_Server;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Group;
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

    @Override
    public void start(Stage primaryStage) {

        // Definizione dello stage principale e della barra del menu
        primaryStage.setTitle("Home");
        /*Group root = new Group();                                //
        *                                                          // Sfondo
        *Scene scene = new Scene(root, 800, 550, Color.DARKBLUE);  //
        */

        Toolbar1 toolbar1 = new Toolbar1(primaryStage);
        MenuBar1 menuBar1= new MenuBar1(primaryStage);

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
        VBox vBox = new VBox();
        vBox.getChildren().addAll(toolbar1,menuBar1);
        vBox.setFillWidth(true);

        // Creazione link al sito del progetto
        VBox linkBox = new VBox();
        linkBox.setFillWidth(true);

        Hyperlink hyperlink = new Hyperlink();
        hyperlink.setText("Click here for Project L-18 web site!");
        hyperlink.setAlignment(Pos.BOTTOM_CENTER);

        final String url = "https://github.com/IngSW-unipv/Progetto-L18";
        final WebView browser = new WebView();

        hyperlink.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                getHostServices().showDocument(url);    // Pagina web aperta nel browser
            }
        });

        HBox hbox = new HBox();
        hbox.getChildren().addAll(hyperlink);
        linkBox.getChildren().addAll(hbox, browser);
        VBox.setVgrow(browser, Priority.ALWAYS);

        // Creazione bottoni
        ButtonBar buttonBar = new ButtonBar();

        Button menuButton = new Button("Menu");
        ButtonBar.setButtonData(menuButton, ButtonBar.ButtonData.LEFT);
        menuButton.setPrefSize(85, 50);

        Button statsButton = new Button("Statistiche");
        ButtonBar.setButtonData(statsButton, ButtonBar.ButtonData.LEFT);
        buttonBar.setLayoutX(100);
        statsButton.setPrefSize(85, 50);

        buttonBar.getButtons().addAll(menuButton, statsButton);

        // Gestione pressione del bottone "Statistiche"
        statsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //HistogramChart coinsChart = new HistogramChart(new CategoryAxis(), new NumberAxis());
                //StatsPage statsPage = new StatsPage();
                new StatsPage(primaryStage);
            }
        });

        // Aggiunta elementi nel Pane
        gPane.setVgap(50);
        gPane.add(vBox, 0,0);
        gPane.add(label, 0,1);
        gPane.add(buttonBar, 0, 2);
        gPane.add(linkBox, 0, 3);
        gPane.setHalignment(label, HPos.CENTER);

        Scene scene = new Scene(gPane, 800, 550);
        //root.getChildren().add(gPane);    // Sfondo col
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        //primaryStage.setMaximized(true); // Lo ingrandisco subito fullscreen
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}