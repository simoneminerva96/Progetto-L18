package GUI_FX_VendingMachine;

import Distributore.Distributore;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.awt.*;
import java.io.FileInputStream;

public class VendingMachine extends Application {
     private Distributore distributore = new Distributore();
     private ResetDisplay resetDisplay;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth() / 2;
        double height = screenSize.getHeight() / 2;

        primaryStage.setTitle("Hot Drinks Vending Machine");

        // Creo il pannello radice a cui attaccare tutti gli altri
        BorderPane root = new BorderPane();

        FileInputStream input = new FileInputStream("src/GUI_FX_VendingMachine/I.JPG");
        Image image = new Image(input);
        input.close();

        // Creo le dimensioni per lo sfondo
        BackgroundSize backgroundSize =
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false,
                                   false, false, true);

        // Creo l'immagine di sfondo
        BackgroundImage changeNameWhenFinalImage =
                new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                                    BackgroundPosition.CENTER, backgroundSize);

        // Metto lo sfondo
        root.setBackground(new Background(changeNameWhenFinalImage));

        // creazione dei vari pannelli
        BorderPane purchasePane = new BorderPane();
        purchasePane.setStyle(
                "-fx-background-color: gray;"
        );

        Display display = new Display();
        purchasePane.setCenter(display);
        resetDisplay = new ResetDisplay(display, distributore);


        //creo la grid per i tasti c - +
        GridPane user = new GridPane();
        user.setHgap(10);
        user.setHgap(10);
        Button change = new Button();
        change.setText("C");
        change.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                distributore.giveChange();

            }
        });
        user.add(change, 0, 0);

        Button minus = new Button();
        minus.setText("-");
        user.add(minus, 1, 0);

        Button plus = new Button();
        plus.setText("+");
        user.add(plus,2,0);

        purchasePane.setCenter(user);

        GridPane beveragePane = new BeverageGrid(distributore, display, resetDisplay);
        root.setLeft(beveragePane);

        root.setRight(purchasePane);

        GridPane moneyPane = new MoneyGrid(distributore, display);
        purchasePane.setBottom(moneyPane);

        Scene scene = new Scene(root, width, height, Color.LIGHTGRAY);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
