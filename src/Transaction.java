import sun.misc.BASE64Encoder;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class Transaction {

    public String fromAddress;
    public String toAddress;
    public double amount;
    public byte[] signature;

    public Transaction(String fromAddress, String toAddress, double amount) {
        this.fromAddress = fromAddress;
        this.toAddress = toAddress;
        this.amount = amount;
    }

    public void signTransaction(KeyPair signingKey) throws Exception {
        if (!Utils.bytesToHex(signingKey.getPublic().getEncoded()).equals(this.fromAddress)) {
            throw new RuntimeException("You cannot sign transactions for other wallets!");
        }

        String hashedTransaction = Utils.calculateHash(this.fromAddress + this.toAddress + this.amount);

        Signature signature = Signature.getInstance("SHA256withECDSA");
        signature.initSign(signingKey.getPrivate());
        signature.update(hashedTransaction.getBytes(StandardCharsets.UTF_8));

        this.signature = signature.sign();
    }

    public boolean isValid() throws Exception {
        if (this.fromAddress == null) {
            return false;
        }

//        if (this.signature == null || this.signature.length == 0) {
//            throw new Error("No signature in this transaction");
//        }

//        String hashedTransaction = Utils.calculateHash(this.fromAddress + this.toAddress + this.amount);
//        Signature signature = Signature.getInstance("SHA256withECDSA");
//        byte[] publicBytes = Base64.getDecoder().decode(this.fromAddress);
//        signature.initVerify(pubKey);
//        signature.update(hashedTransaction.getBytes(StandardCharsets.UTF_8));

        return true;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "fromAddress='" + fromAddress + '\'' +
                ", toAddress='" + toAddress + '\'' +
                ", amount=" + amount +
                '}';
    }
}
