package Distributore.InterfacciaDistributore;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ListenerTry implements ActionListener {

    private JTextArea jTextArea;

    //todo tutta sta roba non va proprio bene. tutto questo è già presente nel distributore. non vedo perchè riscrivere
    //todo cose già fatte.
   // private double credit[] = {0, 0 , 0, 0, 0, 0};
    private double credito = 0;
    private final String monete[] = {"0.05", "0.10", "0.20", "0.50", "1", "2"};
    private final String bevande[] = {"Caffè Espresso", "Cappuccino", "Tè", "Caffè Corretto", "Caffè Lungo",
            "Ginseng", "Bicchiere"};
    private final double costo[] = {0.5, 0.6, 0.5, 0.7, 0.55, 0.65, 0.2};

    public ListenerTry(JTextArea textArea) {
        this.jTextArea = textArea;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String stringaPulsante = e.getActionCommand();

        // Stampa nome e prezzo della bevanda selezionata e il credito disponibile
        for (int j = 0; j < bevande.length; j++) {
            if (stringaPulsante.equals(bevande[j])) {
                jTextArea.setText(bevande[j].toUpperCase() + "\n" + "COSTO: " + costo[j] + "\n\n\n" + "CREDITO: " + credito);
            }
        }
        if (stringaPulsante.equals("")) {
            jTextArea.setText("BEVANDA NON DISPONIBILE" + "\n\n\n\n" + "CREDITO: " + credito);
        }

        // Stampa il credito disponibile dopo avers inserito una moneta
        /*for (int i = 0; i < monete.length; i++) {
            if (stringaPulsante.equals(monete[i])) {
                credito += Double.parseDouble(monete[i]);
                //jTextArea.setText("INSERITI: " + "\n" + "COSTO: " + "\n\n\n" + "CREDITO: " + credit[i]);
               // credito += credit[i];
                System.out.println(credito);
                //jTextArea.setText("INSERITI: " + "\n" + "COSTO: " + "\n\n\n" + "CREDITO: " + credito);
            }


        }


        //jTextArea.repaint();*/
    }
}