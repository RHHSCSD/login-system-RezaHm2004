/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package loginsystem;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author rezahm
 */
public class EncryptionMachine {

    // declaring data for the user class
    private static String encrypted;
    
    /**
     * This method encrypts a password
     * @param pass: String
     * @return encrypted password: String
     */
    public static String encrypt(String pass) {
        encrypted = "";
        try {
            //java helper class to perform encryption
            MessageDigest md = MessageDigest.getInstance("MD5");
            //give the helper function the password
            md.update(pass.getBytes());
            //perform the encryption
            byte byteData[] = md.digest();
            //To express the byte data as a hexadecimal number (the normal way)
            for (int i = 0; i < byteData.length; ++i) {
                encrypted += (Integer.toHexString((byteData[i] & 0xFF) | 0x100).substring(1,3));}
        } catch (NoSuchAlgorithmException ex) {
            System.out.println(ex);
        }
    
        return encrypted;
    }
}
