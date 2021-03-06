package HotDrinkVendingMachine;

import HotDrinks.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.Integer.parseInt;

/**
 * Classe che definisce il distributore automatico.
 */

public class HotDrinkVendMachine implements MaxValue, TextPathFiles {
    private HashMap<String, HotDrink> list;
    private int selected_sugar;
    private Coins coins;
    private FileManager stats;
    private FileManager ingredientsData;
    private FileManager menu;
    private ArrayList<String[]> data;
    private Dispenser dispenser;
    private Key key;

    public HotDrinkVendMachine() {
        this.list = new HashMap<>();
        this.stats = new FileManager(STATS_PATH);
        this.ingredientsData = new FileManager(DATA_PATH);
        this.menu = new FileManager(MENU_PATH);
        this.coins = new Coins();
        this.key = new Key();
        int lastRow = setValues(ingredientsData.readFile());
        this.createList(menu.readFile(), ingredientsData.readFile(), lastRow);
    }

    /**
     * Funzione che carica le quantità residue leggendole da file.
     */
    private int setValues(ArrayList<String[]> data) {
        setSugarToDefault();
        double milk = Double.parseDouble(data.get(0)[1]);
        double sugar = Double.parseDouble(data.get(1)[1]);
        double spoon = Double.parseDouble(data.get(2)[1]);
        double cup = Double.parseDouble(data.get(3)[1]);
        double vodka = Double.parseDouble(data.get(4)[1]);
        int lastRow = 4; // Ultima riga letta dal file
        dispenser = new Dispenser(milk, sugar, spoon, cup, vodka);
        this.data = data;
        dispenser.checkIfMachineIsEmpty(); // Controlla se c'è bisogno di ricaricare la macchinetta
        return lastRow;
    }

    /**
     * Funzione che crea il menu nella macchinetta.
     *
     * Nota: "data" viene utilizzata a partire dalla riga 5 (indice 4).
     *
     * @param listFromFile arraylist di stringhe fornito all'apertura del file.
     * @param data contiene le quantità rimanenti delle bevande.
     * @param dataRow riga dalla quale iniziano i dati delle bevande.
     */
    private void createList(ArrayList<String[]> listFromFile, ArrayList<String[]> data, int dataRow) {
        String storedID = "";
        int numColID = 0; // Numero della colonna in cui è salvato il dato
        int numColType = 1;

        for (int i_menu = 0; i_menu < listFromFile.size(); i_menu++) {
            dataRow++;
            String currentID = listFromFile.get(i_menu)[numColID];
            Type hotDrinkType = Type.valueOf(listFromFile.get(i_menu)[numColType]);
            // Controlla di non aver superato la lunghezza del file delle quantità rimaste
            if (dataRow < data.size()) {
                storedID = data.get(dataRow)[numColID];
            }

            if (!storedID.isEmpty() && currentID.equals(storedID)) {
                String quantityLeft = data.get(dataRow)[1];
                createDrink(hotDrinkType.ordinal(), listFromFile, i_menu, quantityLeft);
            } else {
                createDrink(hotDrinkType.ordinal(), listFromFile, i_menu);
            }
        }
    }

    /**
     * Funzione per identificare il tipo della bevanda ed aggiungerla al distributore nel caso non siano presenti dati
     * riguardanti la sua quantià residua.
     * @param type tipo della bevanda.
     * @param listFromFile file aperto contenente il menù.
     * @param i riga a cui si è arrivati a leggere.
     */
    private void createDrink(int type, ArrayList<String[]> listFromFile, int i) {
        HotDrink hotDrink;

        switch (type) {
            case 0:
                hotDrink = new Grinded(listFromFile.get(i));
                list.put(listFromFile.get(i)[0], hotDrink);
                break;
            case 1:
                hotDrink = new Capsule(listFromFile.get(i));
                list.put(listFromFile.get(i)[0], hotDrink);
                break;
            case 2:
                hotDrink = new Soluble(listFromFile.get(i));
                list.put(listFromFile.get(i)[0], hotDrink);
                break;
        }
    }

    /**
     * Funzione per identificare il tipo della bevanda e aggiungerla al distributore nel caso siano presenti dati
     * riguardanti la sua quantià residua.
     * @param type tipo della bevanda.
     * @param listFromFile file aperto contenente il menù.
     * @param index riga a cui si è arrivati a leggere.
     * @param qtyLeft quantità rimanente nella macchinetta.
     */
    private void createDrink(int type, ArrayList<String[]> listFromFile, int index, String qtyLeft) {
        HotDrink hotDrink;

        switch (type) {
            case 0:
                hotDrink = new Grinded(listFromFile.get(index), qtyLeft);
                list.put(listFromFile.get(index)[0], hotDrink);
                break;
            case 1:
                hotDrink = new Capsule(listFromFile.get(index), qtyLeft);
                list.put(listFromFile.get(index)[0], hotDrink);
                break;
            case 2:
                hotDrink = new Soluble(listFromFile.get(index), qtyLeft);
                list.put(listFromFile.get(index)[0], hotDrink);
                break;
        }
    }

    /**
     * Funzione per selezionare una bevanda. Controlla anche che il credito sia sufficiente.
     * @param ID id della bevanda selezionata.
     */
    public String selectBeverage(String ID) {
        if (!list.get(ID).isAvailable() || !dispenser.checkAvailability(list.get(ID),selected_sugar)) {
            return "Bevanda non disponibile";
        }

        boolean transaction;

        if (key.isConnected()) {
            transaction = key.pay(list.get(ID).getPrice());
            // Scrittura statistiche su file
            stats.writeFile(list.get(ID).getName(),transaction);
            dispenser.subtractIngredients(list.get(ID), selected_sugar);
            updateData(ID);
            setSugarToDefault();

            if (transaction) {
                return "Bevanda erogata";
            }
            else {
                return "Saldo non sufficiente";
            }
        }

        //funzione che si occupa della gestione tramite monete ed erogazione della bevanda
        if (coins.getCredit() >= list.get(ID).getPrice() && list.get(ID).isAvailable()
                && dispenser.checkAvailability(list.get(ID), selected_sugar)) {
            // Se il credito è uguale o maggiore significa che si puo' potenzialmente acquistare la bevanda
            transaction = true;
            dispenser.subtractIngredients(list.get(ID), selected_sugar);
            coins.updateBalance(list.get(ID).getPrice());
            setSugarToDefault();

            // Scrittura statistiche su file
            stats.writeFile(list.get(ID).getName(), transaction);
            updateData(ID);

            if (coins.getCredit() != 0) {
                coins.giveChange();
                System.out.println("Bevanda erogata. Ritirare il resto");
                return "Bevanda erogata. Ritirare il resto";
            } else {
                System.out.println("Bevanda erogata.");
                return "Bevanda erogata";
            }
        } else {
            return "Credito non sufficiente";
        }
    }

    /**
     * Funzione che mostra la lista delle bevande contenute nel distributore.
     */
    public void showList() {
        for (int i = 1; i < list.size() + 1; i++) {
            System.out.println(list.get("0" + i));
        }
    }

    /**
     * Funzione da usare nell'interfaccia per aggiungere i soldi.
     * @param inserted valore associato al tasto di riferimento.
     */
    public void addCredit(double inserted) {
        if (key.isConnected()) {
            key.addBalance(inserted);
            coins.chargeKey(inserted);
        }
        else {
            coins.addCoin(inserted);
        }
    }

    public String getLabel(int i) {
        return (list.get("0" + i).getName());
    }

    public int getListSize() {
        return list.size();
    }

    public String getID(int id) {
        return list.get("0" + id).getId();
    }

    /**
     * Dopo l'erogazione della bevanda lo zucchero viene riportato alla quantità di default.
     */
    public void setSugarToDefault() {
        selected_sugar = 3;
    }

    /**
     * Funzione per aumentare lo zucchero selezionato (Tasto +).
     *
     * Nota: Da utilizzare nell'interfaccia.
     */
    public void moreSugar() {
        if (selected_sugar < MAX_SUGAR_LEVEL) {
            selected_sugar++;
        }
    }

    /**
     * Funzione per diminuire lo zucchero selezionato (Tasto -).
     *
     * Nota: Da utilizzare nell'interfaccia.
     */
    public void lessSugar() {
        if (selected_sugar > 0) {
            selected_sugar--;
        }
    }

    public int getSelectedSugar() {
        return selected_sugar;
    }

    /**
     * Funzione per aggiornare il file Data.txt, contenente le quantità di oggetti e di ingredienti.
     */
    private void updateData(String ID) {
        String valDati[] = dispenser.getData();
        String newLine = "";
        String oldLine = "";

        try {
            for (int i = 0; i < valDati.length; i++) {
                oldLine = data.get(i)[0] + "\t" + data.get(i)[1];

                if (data.get(i)[0].equals("Cups") || data.get(i)[0].equals("Spoons")) {
                    newLine = data.get(i)[0] + "\t" + valDati[i];
                }
                else {newLine = data.get(i)[0] + "\t" + Double.parseDouble(valDati[i]);}
                ingredientsData.overwriteFile(newLine, oldLine);
            }
            setValues(ingredientsData.readFile());
            oldLine = ID + "\t" + list.get(ID).getLeftQuantity();
            list.get(ID).subtractDose();
            newLine = ID + "\t" + (float)list.get(ID).getLeftQuantity();
            ingredientsData.overwriteFile(newLine, oldLine);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Funzione che restituisce il credito della chiavetta.
     * @return credito della chiavetta.
     */
    public double getCredit() {
        if (key.isConnected()) {
            return key.getKeyBalance();
        }
        else {
            return coins.getCredit();
        }
    }

    /**
     * Funzione che associa i nomi dei pulsanti ai relativi valori.
     *
     * Nota: Da utilizzare nell'interfaccia
     *
     * @return valori delle monete in forma vettore.
     */
    public double[] getCoinsValue() {
        return coins.getCOINS_VALUE();
    }

    public double getPrice(String ID) {
        return list.get(ID).getPrice();
    }

    public void giveChange() {
        coins.giveChange();
    }

    /**
     * Funzione che aggiorna il credito della chiavetta.
     */
    public void setConnectionKey() {
        key.setConnected();
        if (coins.getCredit() !=0 ) {
            key.addBalance(coins.getCredit());
            coins.updateBalance(coins.getCredit());
        }
    }
}
