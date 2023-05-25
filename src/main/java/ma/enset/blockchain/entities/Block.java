package ma.enset.blockchain.entities;

import lombok.Getter;
import lombok.Setter;
import ma.enset.blockchain.hash.HashUtil;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
public class Block {

    private int index;

    private Instant timestamp;

    private String previousHash;

    private String currentHash;

    private List<Transaction> transactions;

    private int nonce;

    public Block(int index, String previousHash, List<Transaction> transactions, int nonce) {
        this.index = index;
        this.timestamp = Instant.now();
        this.previousHash = previousHash;
        this.transactions = transactions;
        this.nonce = nonce;
        this.currentHash = calculateHash();
    }

    public void incrementNonce() {
        nonce++;
    }

    public String calculateHash() {
        String data = index + timestamp.toString() + previousHash + transactions.toString() + nonce;
        return HashUtil.calculateSHA256(data);
    }

}
