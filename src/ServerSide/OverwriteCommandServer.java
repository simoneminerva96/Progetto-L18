package ServerSide;

import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;

import static ServerSide.StringCommandList.END_SENDING;
import static ServerSide.StringCommandList.OVERWRITE_MENU;

public class OverwriteCommandServer extends SendCommandServer implements CommandServer {

    public OverwriteCommandServer(ReceiverServer receiverServer, Socket clientSocket,
                                  ObservableList<String> arrayToSaveInfo, BufferedReader clientReader) {
        super(receiverServer, clientSocket, arrayToSaveInfo, clientReader);
    }

    @Override
    public void execute() {
        try {
            receiverServer.sendString(OVERWRITE_MENU, clientSocket);
            // Utilizzo dello stesso nome per l'array, ma stavolta vengono inviate informazioni al client
            for (String tmp : observableListCommand) {
                receiverServer.sendString(tmp, clientSocket);
            }
            receiverServer.sendString(END_SENDING, clientSocket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
