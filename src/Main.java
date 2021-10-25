import java.security.*;

public class Main {

    public static KeyPair myKeyPair;
    public static String myWalletAddress;

    public static void main(String[] args) throws Exception {
        myKeyPair = KeyGenerator.generateKey();
        myWalletAddress = Utils.bytesToHex(myKeyPair.getPublic().getEncoded());

        Blockchain blockchain = new Blockchain();

        // Mine first block
        blockchain.minePendingTransactions(myWalletAddress);
        System.out.println("******************************************");

        ///////////////////////////////////
        // Create a transaction
        Transaction transaction1 = new Transaction(myWalletAddress, "public key goes here", 150);
        transaction1.signTransaction(myKeyPair);
        blockchain.addTransaction(transaction1);

        // Mine block
        System.out.println("Starting the miner...");
        blockchain.minePendingTransactions(myWalletAddress);

        System.out.println("Balance of yousef is: " + blockchain.getBalanceOfAddress(myWalletAddress));
        System.out.println("******************************************");

        // Create second transaction
        Transaction transaction2 = new Transaction(myWalletAddress, myWalletAddress, 150);
        transaction2.signTransaction(myKeyPair);
        blockchain.addTransaction(transaction2);

        // Mine block
        System.out.println("Starting the miner...");
        blockchain.minePendingTransactions(myWalletAddress);

        System.out.println("Balance of yousef is: " + blockchain.getBalanceOfAddress(myWalletAddress));
        System.out.println("******************************************");
///////////////////////////////////

        // Check if the chain is valid
        System.out.println("Blockchain valid? " + blockchain.isChainValid());
    }
}
