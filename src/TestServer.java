import GUI_FX_Server.ServerConnection;

import java.util.ArrayList;

public class TestServer {

    private static ArrayList<String> stats = new ArrayList<>();
    private static ArrayList<String> menu =  new ArrayList<>();
    private static ArrayList<String> coins =  new ArrayList<>();
    private static ArrayList<String> data =  new ArrayList<>();

    public static void main(String[] args) {
        // Avvio Server e client di Prova
        new ServerConnection(80, stats, menu, coins, data).run();
    }
}