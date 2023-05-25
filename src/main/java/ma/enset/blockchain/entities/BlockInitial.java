package ma.enset.blockchain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ma.enset.blockchain.hash.HashUtil;

import java.time.Instant;

@AllArgsConstructor
@Getter @Setter
public class BlockInitial {

    private int index;
    private Instant timestamp;
    private String previous;
    private String current;
    private String data;

    public BlockInitial(int index, String previous, String data) {
        this.index = index;
        this.timestamp = Instant.now();
        this.previous = previous;
        this.data = data;
        this.current = calculateHash();
    }

    public String calculateHash(){
        String toBeHashed = index + timestamp.getEpochSecond() + previous + data;
        String hash= HashUtil.calculateSHA256(toBeHashed);
        return hash;
    }

    public boolean validateBlock(){
        String calculatedHash = calculateHash();
        return this.current.equals(calculatedHash);
    }

}
