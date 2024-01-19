import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

class Block {
    private int index;
    private long timestamp;
    private String data;
    private String previousHash;
    private String hash;

    
    public Block(int index, long timestamp, String data, String previousHash) {
        this.index = index;
        this.timestamp = timestamp;
        this.data = data;
        this.previousHash = previousHash;
        this.hash = calculateHash();
    }

    
    String calculateHash() {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String input = index + timestamp + data + previousHash;
            byte[] hashBytes = digest.digest(input.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xFF & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    
    public int getIndex() {
        return index;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getData() {
        return data;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public String getHash() {
        return hash;
    }
}

class Blockchain {
    private List<Block> chain;

    
    public Blockchain() {
        this.chain = new ArrayList<>();
        
        createGenesisBlock();
    }

    
    private void createGenesisBlock() {
        Block genesisBlock = new Block(0, System.currentTimeMillis(), "Genesis Block", "0");
        chain.add(genesisBlock);
    }

    
    public void addBlock(String data) {
        Block latestBlock = chain.get(chain.size() - 1);
        int newIndex = latestBlock.getIndex() + 1;
        long newTimestamp = System.currentTimeMillis();
        String newHash = latestBlock.calculateHash();
        Block newBlock = new Block(newIndex, newTimestamp, data, latestBlock.getHash());
        chain.add(newBlock);
    }

    
    


    public boolean isValid() {
        for (int i = 1; i < chain.size(); i++) {
            Block currentBlock = chain.get(i);
            Block previousBlock = chain.get(i - 1);

            
            if (!currentBlock.getHash().equals(currentBlock.calculateHash())) {
                return false;
            }

            
            if (!currentBlock.getPreviousHash().equals(previousBlock.getHash())) {
                return false;
            }
        }
        return true;
    }

    
    public List<Block> getChain() {
        return chain;
    }
}

public class Main {
    public static void main(String[] args) {
        
        Blockchain blockchain = new Blockchain();

        
        blockchain.addBlock("Transaction 1");
        blockchain.addBlock("Transaction 2");

        
        List<Block> chain = blockchain.getChain();
        for (Block block : chain) {
            System.out.println("Index: " + block.getIndex());
            System.out.println("Timestamp: " + block.getTimestamp());
            System.out.println("Data: " + block.getData());
            System.out.println("Previous Hash: " + block.getPreviousHash());
            System.out.println("Hash: " + block.getHash());
            System.out.println();
        }

        
        boolean isValid = blockchain.isValid();
        System.out.println("Is blockchain valid? " + isValid);
    }
}

