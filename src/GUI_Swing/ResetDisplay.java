package GUI_Swing;

import Distributore.Distributore;

import javax.swing.*;
import java.util.TimerTask;
import java.util.Timer;

public class ResetDisplay {

    private final String DEFAULTMESSAGE = "SCEGLIERE UNA BEVANDA";
    private JTextArea display;
    private JTextField sugarDisplay;
    private Distributore distributore;
    private Timer timer = new Timer();
    private TimerTask timerTask;

    public ResetDisplay(JTextArea display, JTextField sugarDisplay, Distributore distributore) {
        this.display = display;
        this.sugarDisplay = sugarDisplay;
        this.distributore = distributore;
    }

    /**
     * funzione che nel caso il credito sia 0 riporta la macchinetta ad uno stato di default
     */

    public void runTimer() {
        resetTimer();
        int time;
        if (distributore.getCredit() == 0){
            time = 5000;
        }
        else {
            time = 10000;
        }

        timerTask = new TimerTask() {
            @Override
            public void run() {
                if (distributore.getCredit() == 0){
                    display.setText(DEFAULTMESSAGE);
                    distributore.setSugarToDefault();
                }
                else {
                    display.setText(DEFAULTMESSAGE + "\n\n\nCREDITO: " + String.format("%.2f",
                            distributore.getCredit()));
                }
                setDots();

            }
        };
        timer.schedule(timerTask,time);

    }


    /**
     * funzione per resettare il timer e reinizializzarlo
     */

    private void resetTimer() {
        timer.purge();
        timer.cancel();
        timer = new Timer();
    }

    /**
     * Funzione che aggiorna il display dello zucchero in base alla quantità selezionata
     */

    public void setDots(){
        String quantity;
        switch (distributore.getSelected_sugar()){
            //u25cf è pallino pieno
            //u25cb è pallino vuoto
            case 0:
                quantity = "Senza zucchero";
                break;
            case 1:
                quantity = "- \u25cf \u25cb \u25cb \u25cb \u25cb +";
                break;
            case 2:
                quantity = "- \u25cf \u25cf \u25cb \u25cb \u25cb +";
                break;
            case 3:
                quantity = "- \u25cf \u25cf \u25cf \u25cb \u25cb +";
                break;
            case 4:
                quantity = "- \u25cf \u25cf \u25cf \u25cf \u25cb +";
                break;
            case 5:
                quantity = "- \u25cf \u25cf \u25cf \u25cf \u25cf +";
                break;
            default:
                quantity = "- \u25cf \u25cf \u25cf \u25cb \u25cb +";
                break;
        }

        sugarDisplay.setText(quantity);
    }
}