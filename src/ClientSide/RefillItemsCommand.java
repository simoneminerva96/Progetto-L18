package ClientSide;

import java.io.IOException;

public class RefillItemsCommand implements Command {
    private ReceiverRefill receiverRefill;

    public RefillItemsCommand(ReceiverRefill receiverRefill) {
        this.receiverRefill = receiverRefill;
    }

    @Override
    public void execute() {
        try {
            receiverRefill.refillItems();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
