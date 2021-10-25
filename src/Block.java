import java.util.ArrayList;
import java.util.Date;

public class Block {

    public long timeStamp;
    public ArrayList<Transaction> transactions;
    public String previousHash;
    public String hash;
    public int nonce;

    public Block(ArrayList<Transaction> transactions, String previousHash) {
        this.timeStamp = new Date().getTime();
        this.transactions = transactions;
        this.previousHash = previousHash;
        this.hash = Utils.calculateHash(this.previousHash + this.timeStamp + this.transactions.toString() + this.nonce);
    }

    public void mineBlock(int difficulty) {
        String sDifficulty = new String(new char[difficulty]).replace('\0', '0');
        while (!this.hash.substring(0, difficulty).equals(sDifficulty)) {
            this.nonce++;
            this.hash = Utils.calculateHash(this.previousHash + this.timeStamp + this.transactions.toString() + this.nonce);
        }

        System.out.println("Block Mined: " + this.hash);
    }

    public boolean hasValidTransactions() throws Exception {
        for (Transaction transaction : transactions) {
            if (!transaction.isValid()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        return "Block{" +
                "timeStamp=" + timeStamp +
                ", transaction='" + transactions.toString() + '\'' +
                ", previousHash='" + previousHash + '\'' +
                ", hash='" + hash + '\'' +
                ", nonce=" + nonce +
                '}';
    }
}
