package InterfacciaDistributore;

import Distributore.Distributore;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class ResetDisplay extends Thread{

    private final String DEFAULTMESSAGE = "     SCEGLIERE UNA BEVANDA";
    JTextArea display;
    JTextField sugarDisplay;
    Distributore distributoreR;

    public ResetDisplay(JTextArea display, JTextField sugarDisplay, Distributore distributore) {
        this.display = display;
        this.sugarDisplay = sugarDisplay;
        this.distributoreR = distributore;
    }

    public void run(){
        java.util.Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (distributoreR.getCredit() == 0) {
                    display.setText(DEFAULTMESSAGE);
                    distributoreR.setSugarToDefault();
                    System.out.println(distributoreR.getCredit());
                    setDots(sugarDisplay);
                }
            }
        };
        timer.schedule(timerTask, 5000);
    }


    private void setDots(JTextField display){
        int sugar = distributoreR.getSelected_sugar();
        String quantity = null;
        switch (sugar){
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
        }

        display.setText(quantity);
    }
}
