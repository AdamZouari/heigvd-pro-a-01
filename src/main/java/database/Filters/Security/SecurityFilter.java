package main.java.database.Filters.Security;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Source regex: https://stackoverflow.com/questions/8204680/java-regex-email#8204716
 <<<<<<< Updated upstream
 * Source random: https://stackoverflow.com/questions/41107/how-to-generate-a-random-alpha-numeric-string#41156
 * **/

public class SecurityFilter {

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    // Source : https://stackoverflow.com/questions/19605150/regex-for-password-must-contain-at-least-eight-characters-at-least-one-number-a

    private static final Pattern VALID_PASSWORD_POLICY = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{12,}$",Pattern.CASE_INSENSITIVE);


    private static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static final String lower = upper.toLowerCase(Locale.ROOT);

    private static final String digits = "0123456789";

    private static final String alphanum = upper + lower + digits;

    private String passwordTest = "thisIsATestPassword";


    public String hashId(int id) throws NoSuchAlgorithmException {
        // sha256 hash
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(BigInteger.valueOf(id).toByteArray());

        return hash.toString();
    }

    public String hashPassword(String password) throws NoSuchAlgorithmException{
        // sha256 hash
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));

        return hash.toString();
    }

    public void checkPasswordPolicy(){
        //
    }

    public void checkHash(){

    }
    // TODO compare hash with the password stored in DATABASE (server side)
    public boolean checkIfPasswordsHashesMatch(String pw) throws NoSuchAlgorithmException {
        return hashPassword(passwordTest).equals(hashPassword(pw));
    }




    public static String generateRandomString(int nbChars) {
        char buf[] = new char[nbChars];
        SecureRandom random = new SecureRandom();
        char charSymbols[] = alphanum.toCharArray();
        for (int idx = 0; idx < buf.length; ++idx){
            buf[idx] = charSymbols[random.nextInt(charSymbols.length)];

        }
        return new String(buf);

    }
    public static boolean isAgoodPassword(String password){

        Matcher matcher = VALID_PASSWORD_POLICY.matcher(password);
        return matcher.find();
    }

    // id of 256 bits (32 characters)

    public static void main(String[] args){

        String randomTest = generateRandomString(32);
        Scanner scanner = new Scanner(System.in);

        String pass = "heeeeo123$E";
        String pass2 = scanner.nextLine();
        System.out.println(randomTest);
        System.out.println("My password is:" + pass2);
        System.out.println("Is this password sufficient ? -> " + isAgoodPassword(pass2));


    }
}
