package ma.enset.blockchain.entities;

import lombok.Getter;
import lombok.Setter;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Blockchain {

    private List<Block> chain;

    private TransactionPool transactionPool;

    private int difficulty;

    public Blockchain(int difficulty) {
        this.chain = new ArrayList<>();
        this.transactionPool = new TransactionPool();
        this.difficulty = difficulty;

        // Create the first block
        Block genesisBlock = createGenesisBlock();
        chain.add(genesisBlock);
    }

    private Block createGenesisBlock() {

        List<Transaction> transactions = new ArrayList<>();

        return new Block(0, "0", transactions, 0);
    }

    public Block getLatestBlock() {
        return chain.get(chain.size() - 1);
    }

    private String getDifficultyPrefix() {
        return "0".repeat(difficulty);
    }

    public boolean isValidBlock(Block block) {

        Block previousBlock = getLatestBlock();
        if (block.getIndex() != previousBlock.getIndex() + 1) {
            return false;
        }

        if (!block.getPreviousHash().equals(previousBlock.getCurrentHash())) {
            return false;
        }

        String prefix = getDifficultyPrefix();
        return block.getCurrentHash().startsWith(prefix);

    }

    public Block addBlock(Block block) {
        if (isValidBlock(block)) {
            chain.add(block);
            transactionPool.removeTransactions(block.getTransactions());
            return block;
        }
        throw new InvalidParameterException();
    }


    public Block mineBlock() {

        Block block = new Block(getChain().size(), getLatestBlock().getCurrentHash(),
                transactionPool.getPendingTransactions(), 0);

        block.setPreviousHash(getLatestBlock().getCurrentHash());

        var calculatedHash = block.calculateHash();

        while (!calculatedHash.startsWith(getDifficultyPrefix())) {
            block.incrementNonce();
            calculatedHash = block.calculateHash();
        }

        block.setCurrentHash(calculatedHash);

        return addBlock(block);
    }

    public boolean validateChain() {

        for (int i = 1; i < chain.size(); i++) {
            Block currentBlock = chain.get(i);
            Block previousBlock = chain.get(i - 1);
            if (!currentBlock.getCurrentHash().equals(currentBlock.calculateHash())) {
                return false;
            }
            if (!currentBlock.getPreviousHash().equals(previousBlock.getCurrentHash())) {
                return false;
            }
        }
        return true;
    }

    public void addTransaction(Transaction transaction) {
        transactionPool.addTransaction(transaction);
    }
}

