package utils;

import org.apache.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Crypto {

    private final static Logger LOG = Logger.getLogger(Crypto.class.getName());

    /**
     * Hash the password with sha512. Very handy. Wow.
     *
     * @param passwordToHash the password to hash
     * @param salt           the salt to use
     * @return the sha512 of the salted hash
     */
    // Taken from : https://stackoverflow.com/questions/33085493/hash-a-password-with-sha-512-in-java
    public static String sha512(String passwordToHash, String salt) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            LOG.error(e.getMessage(), e);
        }
        return generatedPassword;
    }

}
