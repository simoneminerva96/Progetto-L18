package InterfacciaDistrubutoreFX;

import Distributore.Distributore;
import InterfacciaDistributore.ResetDisplay;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class BeverageListener implements ActionListener {

    private Distributore distributore;
    private JTextArea textArea;
    private int index;
    private ResetDisplay resetDisplay;

    public BeverageListener(Distributore distributore, JTextArea display, int index, ResetDisplay resetDisplay) {
        this.distributore = distributore;
        this.textArea = display;
        this.index = index;
        this.resetDisplay = resetDisplay;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        String id = distributore.getID(index);

        if (distributore.getCredit() >= distributore.getPrice(id)) {
            textArea.setText(distributore.selectBeverage(id));
            resetDisplay.runTimer();
        }
        else {
            if (distributore.getCredit() > 0 && distributore.getCredit() < distributore.getPrice(id)){
                textArea.setText(distributore.selectBeverage(id) + "\n" + "COSTO: " +
                        String.format("%.2f", distributore.getPrice(id)) + "\n\nCREDITO: " +
                        String.format("%.2f", distributore.getCredit()));
                resetDisplay.runTimer();
            }
            else {
                textArea.setText(distributore.getLabel(index).toUpperCase() + "\n" + "COSTO: " +
                        String.format("%.2f", distributore.getPrice(id)) + "\n\nCREDITO: " +
                        String.format("%.2f", distributore.getCredit()));
                resetDisplay.runTimer();
            }
        }
    }
}