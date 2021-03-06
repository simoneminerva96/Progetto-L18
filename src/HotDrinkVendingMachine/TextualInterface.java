package HotDrinkVendingMachine;

import java.util.Scanner;

import static java.lang.Integer.parseInt;

/**
 * Classe che si ocuupa dell'interfaccia testuale.
 */

public class TextualInterface {
    private HotDrinkVendMachine vendMachine;

    public TextualInterface(HotDrinkVendMachine vendMachine) {
        this.vendMachine = vendMachine;
    }

    /**
     * Funzione per recepire i comandi testuali ed analizzarli.
     */
    public void textualInput() {
        String input;
        do {
            vendMachine.showList();
            System.out.println("Inserire l'ID della bevanda e la quantità di zucchero richiesta (da 0 a 5) separate " +
                    "da uno spazio.\nNel caso non venga inserito nulla sarà di default a 3");
            input = keyboard();
        } while (input.isEmpty()); // Finchè non si riceve un input non si prosegue
        String[] splitted = input.split("\\s+");

        if (splitted.length == 1) {
            vendMachine.setSugarToDefault();
        }
        else { // Espressa una preferenza
            int selectedSugar = parseInt(splitted[1]);
            for (int i = 0; i < selectedSugar; i++) {
                vendMachine.moreSugar();
            }
        }
        askForMoneyInput();
        vendMachine.selectBeverage(splitted[0]); // Funzione da usare nell'interfaccia per l'erogazione della bevanda
    }

    /**
     * Funzione per recepire input da tastiera e restituirli sotto forma di stringa.
     */
    private String keyboard() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    /**
     * Funzione che chiede quante monete inserire da tastiera.
     */
    private void askForMoneyInput() {
        double[] coinsValue = vendMachine.getCoinsValue();
        for (int i = 0; i < coinsValue.length; i++) {
            System.out.println("Inserire le monete da " + String.format("%.2f", coinsValue[i]) + " cent");
            String input = keyboard();
            if (parseInt(input) > 0) {
                double added = parseInt(input) * coinsValue[i];
                vendMachine.addCredit(added);
            }
        }
    }
}
