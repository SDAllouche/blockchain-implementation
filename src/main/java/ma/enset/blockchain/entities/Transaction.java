package ma.enset.blockchain.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Transaction {

    private final String sender;

    private final String recipient;

    private final double amount;

    private String signature;

    public Transaction(String sender, String recipient, double amount) {
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
        this.signature = ""; // Placeholder for digital signature
    }

}
