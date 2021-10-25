import java.security.*;
import java.security.spec.ECGenParameterSpec;

public class KeyGenerator {

    public static KeyPair generateKey() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC", "SunEC");
        ECGenParameterSpec ecGenParameterSpec = new ECGenParameterSpec("secp256k1");
        keyPairGenerator.initialize(ecGenParameterSpec);

        return keyPairGenerator.genKeyPair();
    }
}
