package edu.byu.cs.tweeter.util;

import java.math.BigInteger;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Pbkdf2 {
    private static final int ITERATIONS = 1000;
    private static final int KEY_LENGTH = 192; // bits

    public static String hashPassword(String password, String salt){
        char[] passwordChars = password.toCharArray();
        byte[] saltBytes = salt.getBytes();

        PBEKeySpec spec = new PBEKeySpec(
                passwordChars,
                saltBytes,
                ITERATIONS,
                KEY_LENGTH
        );

        try {
            SecretKeyFactory key = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hashedPassword = key.generateSecret(spec).getEncoded();
            return String.format("%x", new BigInteger(hashedPassword));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return "Error while hashing password";
    }
}
