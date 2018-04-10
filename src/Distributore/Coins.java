package Distributore;

import static java.lang.Integer.parseInt;

public class Coins {
    private int money[] = new int[6];
    private double balance=0, profit=0;
    private double credit=0;
    private String[] addedCoins;
    private final double valueCoins[] = {0.05, 0.10, 0.20, 0.50 , 1.00, 2.00};

    private Data data = new Data("monete.txt");

    public Coins() {
        //todo aggiungere funzione che legga da file il quantitativo di monete presente e le inserisca nel vettore.
        this.money[0] = 20;
        this.money[1] = 30;
        this.money[2] = 20;
        this.money[3] = 10;
        this.money[4] = 5;
        this.money[5] = 5;
        initializeBalance();
    }

    private void initializeBalance() {
        for(int i=0;i<money.length;i++){
            balance+=money[i]*valueCoins[i];
        }
    }

    public void updateBalance(double vendita) {
        balance += vendita;
        profit += vendita;
        credit -= vendita;
    }

    /**
     * Funzione per identificare le monete inserite in base alla stringa in input (da tastiera o interfaccia)
     *
     * @param input è la stringa da analizzare per identificare, in base alla posizione.
     *              taglio: 0.05 0.10. 0.20 0.50 1 2
     *              input:  1     0    3    1   0 0
     *              ad esempio posso capire quante monete per ogni taglio relativo alla posizione essendo separate da spazi.
     */

    public void addCredit(String input) {
        addedCoins = input.split("\\s+"); //i tagli sono separati da spazi.
        if (addedCoins.length == 6) {
            for (int i = 0; i < addedCoins.length; i++) {
                credit += parseInt(addedCoins[i]) * valueCoins[i];

                //Utilizzo le motenete inserite al credit per dare resto
                //money[i] += parseInt(addedCoins[i]);

            }
        } // Significa che non ho inserito tutti i dati riferiti ai singoli tagli.
        else {
            System.out.println("Restituzione delle monete data l'assenza di tutti i campi");
        }
    }

    public double getCredit() {
        return credit;
    }

    private double getBalance() {
        double balance = 0;
        for (int i = 0; i < money.length; i++) {
            balance += money[i] * valueCoins[i];
        }
        return balance;
    }

    public void giveChange() {

        if (checkChange()) {
            int[] change = new int[6];                        //(change[0], change[1], ecc...) è il numero di monete
            int[] divisor = {5, 10, 20, 50, 100, 200};            // del tipo indicato
            double resto;

            resto = (credit * 100);

            for (int i = 5; i > -1; i--) {
                change[i] = (int) (resto) / divisor[i];
                resto = resto % divisor[i];

                if (change[i] > money[i]) {
                    resto += (change[i] - money[i]) * divisor[i];
                    change[i] = money[i];
                }
                money[i] -= change[i];
            }

            //TODO Abbellire output
            System.out.println("5c:" + change[0] + "\n10c:" + change[1] + "\n20c:" + change[2] + "\n50c:"
                    + change[3] + "\n1E:" + change[4] + "\n2E:" + change[5]);


        }  else if(!checkChange()) {
            System.out.println("Resto NON disponibile");
        }

    }

    private boolean checkChange() {
        if (credit<=getBalance()){
            return true;
        }
        else {
            return false;
        }
    }
}



