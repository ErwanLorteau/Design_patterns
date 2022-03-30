package fr.unantes.sce.people;
import fr.unantes.sce.security.UserManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

//Activate for using encryptDecryptEquivalence() test
//import static fr.unantes.sce.security.UserManager.bytesToString;
//import static fr.unantes.sce.security.UserManager.stringToByte;

public class UserManagerTest {
    UserManager manager;

    @BeforeEach
    public void setUp() throws Exception {
        manager = new UserManager();
        manager.addUser("tommy784", "petitHibouDesChamps");
        manager.addUser("LinusTorvald", "IloveLinux");
        manager.addUser("jean453", "Adx9Kdjklks_");
    }

    @Test
    public void testIsRegistered() {
        Assertions.assertTrue(manager.isRegistered("tommy784"));
        Assertions.assertFalse(manager.isRegistered("Kevin54"));
    }

    @Test

    /**Test that adding an already registered user should return an exeption**/
    void addAlreadyKnownUser() {
        IllegalArgumentException thrown = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> manager.addUser("tommy784", "petitHibouDesChamps"),

                "fdp"
        );

        Assertions.assertTrue(thrown.getMessage().contains("Invalid argument: the username is already registered."));
    }

    @Test
    public void testLogin() throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, UnsupportedEncodingException {
        Assertions.assertTrue(manager.validateLogin("tommy784", "petitHibouDesChamps"));
        Assertions.assertTrue(manager.validateLogin("LinusTorvald", "IloveLinux"));
        Assertions.assertTrue(manager.validateLogin("jean453", "Adx9Kdjklks_"));
        Assertions.assertFalse(manager.validateLogin("ridilo45", "Jhonn9ffd9"));
    }

    @Test
    public void testAddUser() {
        Assertions.assertTrue(manager.addUser("john", "super_strong_password"));
        Assertions.assertTrue(manager.addUser("pierre", "super_strong_password"));
    }

    @Test
    public void testRemoveUser() {
        Assertions.assertTrue(manager.removeUser("LinusTorvald")) ;
        Assertions.assertFalse(manager.removeUser("LinusTorvald"));
        Assertions.assertFalse(manager.removeUser("AsanoTora"));
    }


    /**Disabled test, to use it, make ecryptPassword , decryptPassword, stringToByte,
     * byteToString methods, and secret key in UserManager public, don't forget to set them PRIVATE after using
     * Test if the encryption and dectyption algorithim performs coherence in both ways

    @Test
    public void encryptDecryptEquivalence() throws UnsupportedEncodingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        String s = "password" ;
        byte[] encryptedPassword =  manager.encryptPassword(stringToByte(s) , manager.getByteArrayKey() ) ;
        String decrypted = bytesToString(manager.decryptPassword(encryptedPassword, manager.getByteArrayKey())) ;
        Assertions.assertTrue(s.equals(decrypted));
    }
    */

}


