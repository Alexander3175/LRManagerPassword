import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.util.Base64;

public class PasswordEncryptor {
    private static final String KEY_FILE = "key.txt";
    private SecretKey secretKey;

    public PasswordEncryptor() throws Exception {
        this.secretKey = loadOrGenerateKey();
    }

    private SecretKey loadOrGenerateKey() throws Exception {
        File keyFile = new File(KEY_FILE);
        if (keyFile.exists()) {
            byte[] encodedKey = new byte[(int) keyFile.length()];
            try (FileInputStream fis = new FileInputStream(keyFile)) {
                fis.read(encodedKey);
            }
            return new SecretKeySpec(encodedKey, "AES");
        } else {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128);
            SecretKey secretKey = keyGen.generateKey();
            try (FileOutputStream fos = new FileOutputStream(KEY_FILE)) {
                fos.write(secretKey.getEncoded());
            }
            return secretKey;
        }
    }

    public String encrypt(String data) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public String decrypt(String encryptedData) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedData);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes);
    }
}
