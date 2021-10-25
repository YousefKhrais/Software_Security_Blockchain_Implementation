import java.util.ArrayList;

public class Blockchain {

    public ArrayList<Block> chain;
    public ArrayList<Transaction> pendingTransactions;
    public int difficulty;
    public int miningReward;

    public Blockchain() {
        this.pendingTransactions = new ArrayList<>();
        this.chain = new ArrayList<>();
        this.chain.add(createGenesisBlock());
        this.miningReward = 500;
        this.difficulty = 2;
    }

    public Block createGenesisBlock() {
        return new Block(new ArrayList<>(), "0");
    }

    public Block getLatestBlock() {
        return chain.get(chain.size() - 1);
    }

    public void minePendingTransactions(String miningRewardAddress) {
        Block block = new Block(this.pendingTransactions, this.getLatestBlock().hash);
        block.mineBlock(this.difficulty);

        this.chain.add(block);

        this.pendingTransactions.clear();
        this.pendingTransactions.add(new Transaction(this.getLatestBlock().hash, miningRewardAddress, this.miningReward));
    }

    public void addTransaction(Transaction transaction) throws Exception {
        if (transaction.fromAddress == null || transaction.toAddress == null) {
            throw new RuntimeException("Transaction must include from and to address");
        }

        if (!transaction.isValid()) {
            throw new RuntimeException("Cannot add invalid transaction to chain");
        }

        if (transaction.amount <= 0) {
            throw new RuntimeException("Transaction amount should be higher than 0");
        }

//        if (this.getBalanceOfAddress(transaction.fromAddress) < transaction.amount) {
//            throw new RuntimeException("Not enough balance");
//        }

        System.out.println("Transaction Added : " + transaction);
        this.pendingTransactions.add(transaction);
    }

    public double getBalanceOfAddress(String address) {
        double balance = 0;

        for (Block block : this.chain) {
            for (Transaction transaction : block.transactions) {
                if (transaction.fromAddress.equals(address)) {
                    balance -= transaction.amount;
                }

                if (transaction.toAddress.equals(address)) {
                    balance += transaction.amount;
                }
            }
        }

        return balance;
    }

    public boolean isChainValid() throws Exception {
        for (int i = 1; i < this.chain.size(); i++) {
            Block currentBlock = this.chain.get(i);
            Block previousBlock = this.chain.get(i - 1);

            if (!currentBlock.hasValidTransactions()) {
                return false;
            }

            if (!currentBlock.hash.equals(Utils.calculateHash(currentBlock.previousHash + currentBlock.timeStamp + currentBlock.transactions.toString() + currentBlock.nonce))) {
                //return false;
            }

            if (!currentBlock.previousHash.equals(previousBlock.hash)) {
                return false;
            }
        }

        return true;
    }
}