/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package loginsystem;

/**
 *
 * @author rezahm
 */
public class EncryptionMachine {

    // declaring data for the user class
    private static String encrypted;
    private static String decrypted;
    
    /**
     * This method encrypts a password
     * @param pass: String
     * @return encrypted password: String
     */
    public static String encrypt(String pass){
        encrypted = pass;
        return encrypted;
    }
    
    /**
     * This method decrypts a encrypted password
     * @param enpass
     * @return 
     */
    public static String decrypt(String enpass){
        decrypted = enpass;
        return decrypted;
    }
}
