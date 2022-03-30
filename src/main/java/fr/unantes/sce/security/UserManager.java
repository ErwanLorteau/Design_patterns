package fr.unantes.sce.security;

import javax.crypto.*;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private Map<String, byte[]> usernameToPassword;
    private SecretKey secretKey;

    public UserManager() throws NoSuchAlgorithmException {
        usernameToPassword = new HashMap<>();
        generateKey() ;
    }

    /**Generate a secret  128-byte AES key (for this exemple, the key is unique for all the users)**/
    private void generateKey () throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        this.secretKey = keyGenerator.generateKey();
    }


    /*** Check if an username is in the database***/
    public boolean isRegistered(String userName) {
        return usernameToPassword.containsKey(userName);
    }

    /*** Add a user to the dataBase***/
    public boolean addUser(String username, String password) throws IllegalArgumentException {
        if (isRegistered(username)) {
            throw new IllegalArgumentException("Invalid argument: the username is already registered.");
        }

        /**Generate a bytes[] from the given password and add the account to the database**/
        try {
            byte[] encryptedPassword = encryptPassword(password.getBytes(), getByteArrayKey());
            usernameToPassword.put(username, encryptedPassword);
            return true;
        } catch (Exception e) {
           e.printStackTrace();
           return false  ;
        }
    }

    /**Remove user from the database**/
    public boolean removeUser(String userName) {
        if (usernameToPassword.containsKey(userName)) {
            usernameToPassword.remove(userName);
            return true ;
        }
        return false;
    }

    /*** Encrypt a message using AES algorithm***/
    private byte[] encryptPassword(byte[] message, byte[] keyBytes)
            throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException,
            BadPaddingException, IllegalBlockSizeException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, NoSuchAlgorithmException {

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, this.secretKey);
        return cipher.doFinal(message);
    }

    /*** Decrypt a message using the same algorithm***/
    private byte[] decryptPassword(byte[] encryptedMessage, byte[] keyBytes)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, this.secretKey);
        return cipher.doFinal(encryptedMessage);
    }

    /**Check if an user loggin attemp is valid**/
    public boolean validateLogin(String username, String password) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, UnsupportedEncodingException {
        if (isRegistered(username)) {
            byte[] decryptedPasswordByte = decryptPassword(usernameToPassword.get(username), secretKey.toString().getBytes());
            String decoded = bytesToString(decryptedPasswordByte) ;
            return password.equals(decoded);
        }
        return false;
    }

    /**Return the BytesArray of the key (used for encryption and decryption)**/
    //Could be improve later, removing this methos and find an iso for casting SecureKey to Bytes in a unique way...
    private byte[] getByteArrayKey() {
        return secretKey.toString().getBytes();
    }

    /**Cast a byte array to a string using ISO-8859-1**/
    /**HACK::do not touch, had issue with handling correspondance between bytes[] and String in case of CpherEncryption**/
    private static String bytesToString(byte[] b) throws UnsupportedEncodingException {
       return new String(b, "ISO-8859-1");
    }

    private static byte[] stringToByte(String s) throws UnsupportedEncodingException {
        byte[] encoded = s.getBytes("ISO-8859-1");
        return encoded ;
    }
}

