package GUI_FX_VendingMachine;

import Distributore.Distributore;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;


public class BeverageGrid extends GridPane {
    private Distributore distributore;
    private Display display;
    private ResetDisplay resetDisplay;
    private final int BUTTON_PADDING = 50;
    private final int BUTTONS_PER_LINE = 3;
    private final int NUM_LINES = 4;

    public BeverageGrid(Distributore distributore, Display display, ResetDisplay resetDisplay, double screenWidth, double screenHeight) {
        this.distributore = distributore;
        this.display = display;
        this.resetDisplay = resetDisplay;
        createGrid(screenWidth, screenHeight);
    }

    private void createGrid(double screenWidth, double screenHeight){
        // Per una migliore lettura, usare al massimo 12 pulsanti
       this.setPadding(new Insets(BUTTON_PADDING));
        this.setHgap(BUTTON_PADDING);
        this.setVgap(BUTTON_PADDING);

        int number = 0;
        for (int row = 0; row < NUM_LINES; row++) {
            for (int col = 0; col < BUTTONS_PER_LINE; col++) {
                Button button = new Button("");
                setButton(button, (int)(18 * screenWidth / 100), (int) (screenHeight / 7) );
                if( number + 1 < distributore.getListSize()){
                    number = (BUTTONS_PER_LINE * row) + col;
                    // Le bevande iniziano dall'id 1
                    button.setText(distributore.getLabel(number + 1));
                    BeverageEventHandler beverageEventHandler = new BeverageEventHandler (distributore, display,
                            number + 1, resetDisplay);
                    button.setOnAction(beverageEventHandler);
                }
                this.add(button, col, row);
            }
        }
    }

    private void setButton(Button buttonToSet, int width, int height){
        buttonToSet.setStyle(
                        "-fx-background-radius: 1em;" +
                        "-fx-base: gainsboro;" +
                        "-fx-focus-color: blue;"
        );
        buttonToSet.setFont(Font.font("Century", 20));
        buttonToSet.setPrefSize(width, height);
    }
}
