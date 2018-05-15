package GUI_FX_VendingMachine;

import Distributore.Distributore;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.awt.Dimension;
import java.awt.Toolkit;

public class BeverageGrid extends GridPane {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private Distributore distributore;
    private Display display;
    private ResetDisplay resetDisplay;
    private final int BUTTON_PADDING = 50;
    private final int BUTTONS_PER_LINE = 3;
    private final int NUM_LINES = 4;

    public BeverageGrid(Distributore distributore, Display display, ResetDisplay resetDisplay) {
        this.distributore = distributore;
        this.display = display;
        this.resetDisplay = resetDisplay;
        createGrid();
    }

    private void createGrid(){
        // Per una migliore lettura, usare al massimo 12 pulsanti
        this.setPadding(new Insets(BUTTON_PADDING));
        this.setHgap(BUTTON_PADDING);
        this.setVgap(BUTTON_PADDING);

        // Creazione pulsanti bevande
        int number = 0;
        for (int row = 0; row < NUM_LINES; row++) {
            for (int col = 0; col < BUTTONS_PER_LINE; col++) {
                Button button = new Button("");
                setButton(button, 18 * screenSize.width / 100, screenSize.height / 7);
                if( number + 1 < distributore.getListSize()){
                    number = (BUTTONS_PER_LINE * row) + col;
                    // Le bevande iniziano dall'id 1
                    button.setText(distributore.getLabel(number + 1));
                    BeverageEventHandler beverageEventHandler = new BeverageEventHandler (distributore, display,
                            number + 1, resetDisplay);
                    button.setOnAction(beverageEventHandler);
                    button.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                        DropShadow shadow = new DropShadow();
                        @Override
                        public void handle(MouseEvent event) {
                            shadow.setColor(Color.BLUE);
                            button.setEffect(shadow);

                        }
                    });
                    button.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            button.setEffect(null);
                        }
                    });
                }
                this.add(button, col, row);
            }
        }
    }

    /**
     * Funzione che configura i pulsanti delle bevande
     * @param buttonToSet: pulsante da configurare
     * @param width: lunghezza del pulsante
     * @param height: altezza del pulsante
     */
    private void setButton(Button buttonToSet, int width, int height){
        buttonToSet.setStyle(
                "-fx-background-radius: 1em;" +
                        "fx-base: cornsilk;" +
                "-fx-background-color: radial-gradient(focus-angle 100deg, focus-distance 35%, radius 45%, reflect, " +
                        "cornsilk 30%, peru 90%);" +
                "-fx-focus-color: blue;"
        );
        buttonToSet.setFont(Font.font("California FB", FontWeight.BOLD, 20));
        buttonToSet.setPrefSize(width, height);
    }
}
