/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package loginsystem;

// Import Some packages
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author rezahm
 */
public class LoginSystem {

    // Read some Global variables for the program
    final String DELIMETER = ",";
    final String USERSFILE = "Users.txt";
    ArrayList<User> users = new ArrayList<User>();
            
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
    }
    
    /**
     * This is class LoginSystem that creates object that can register or login a user
     */
    public LoginSystem() {
        // load users from file
        loadUsers();
    }
    
    /**
     * This a method to register a user
     * @param usernm
     * @param pass
     * @param nm
     * @param age
     * @param gender
     * @return 
     */
    public int register(String usernm, String pass, String nm, int age, String gender){
        
        // Ask user for their username and input the response until the username is unique
        if (!isUniqueName(usernm)){
            return 1;
        }
        
        // Ask user for their password and input the response until the password is strong
        if (!isPasswordStrong(pass)){
            return 2;
        }
        
        // encrypt the password with a salt
        String[] passSalt = {pass, ""};
        encryptWithSalt(passSalt);
        String enpass = passSalt[0];
        String salt = passSalt[1];
        
        // Register the user
        saveUser(nm, usernm, enpass, salt, age, gender);
        
        return 0;
    }
    
    /**
     * This a method to login an existing user
     * @param usernm
     * @param pass
     * @return 
     */
    public int login(String usernm, String pass){
        // Chech for each user
        for (User u: users){
            // Check if the username exists
            if (u.getUsername().equals(usernm)){
                // encrypt the password with a salt
                String[] passSalt = {pass, u.getSalt()};
                encryptWithSalt(passSalt);
                String enpass = passSalt[0];
                // Chech if the password is correct
                if (u.getEncryptedPass().equals(enpass)){
                    // then login
                    return 0;
                } else {
                    return 2;
                }
            }
        }
        
        return 1;
        
    }
    
    /**
     * This is a method to load all of the existing users
     */
    private void loadUsers(){
        try {
            File f = new File(USERSFILE);
            Scanner sf = new Scanner(f);
            while (sf.hasNextLine()){
                String s = sf.nextLine();
                String[] strLst = s.split(DELIMETER);
                User u = new User(strLst[0], strLst[1], strLst[2], strLst[3], Integer.parseInt(strLst[4]), strLst[5]);
                users.add(u);
            }
            sf.close();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
    
    /**
     * This is a method to save a user to a file
     * @param nm: Name(String)
     * @param usernm: Username(String)
     * @param enpass: Encrypted Password(String)
     * @param age: (int)
     * @param gender: (String)
     */
    private void saveUser(String nm, String usernm, String enpass, String salt, int age, String gender){
        try {
            File f = new File(USERSFILE);
            PrintWriter pf = new PrintWriter(new FileWriter(f,true));
            pf.println(nm + DELIMETER + usernm + DELIMETER + enpass + DELIMETER + salt + DELIMETER + age + DELIMETER + gender);
            pf.close();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
    
    /**
     * This is a method to check the uniqueness of a username
     * @param usernm: Username(String)
     * @return (boolean)
     */
    private boolean isUniqueName(String usernm){
        boolean result = true;
        //Chech for each user if their username is the same as the new user's username
        for (User u: users){
            if (u.getUsername().equals(usernm)){
                result = false;
                break;
            }
        }
        return result;
    }
    
    /**
     * This is a method to check the strength of a password
     * @param pass: password(String)
     * @return (boolean)
     */
    private boolean isPasswordStrong(String pass){
        boolean result = true;
        // Read a boolean for checking if a password has numbers
        boolean hasNumber = false;
        // Read a boolean for checking if a password has a minimum length
        boolean hasMinLen = false;
        // Read a boolean for checking if a password has uppercase letters
        boolean hasUpCase = false;
        // Read a boolean for checking if a password has lowercase letters
        boolean hasLowCase = false;
        // Read a boolean for checking if a password has special characters
        boolean hasSpecChar = false;
        // Read a boolean for checking if a password is in the list of bad password 
        boolean isBadPass = true;
        
        // Check if a password is in the list of bad password
        isBadPass = isPasswordBad(pass);
        
        // Check for each character in the password
        for (int i = 0; i<pass.length(); i++){
            char ch = pass.charAt(i);
        // Check if a password has numbers
            if (Character.isDigit(ch)){
                hasNumber = true;
        // Check if a password has lowercase letters
            } else if (Character.isLowerCase(ch)){
                hasLowCase = true;
        // Check if a password has uppercase letters
            } else if (Character.isUpperCase(ch)){
                hasUpCase = true;
        // Check if a password has special characters
            } else {
                hasSpecChar = true;
            }
        }
        
        // Check if a password has a minimum length
        if (pass.length() > 5){
            hasMinLen = true;
        }
        
        // if all boolean are true then set result to true
        result = hasNumber && hasMinLen && hasLowCase && hasUpCase && hasSpecChar && !isBadPass;
        
        // Return the result of the password check
        return result;
    }
    
    /**
     * This is a method to check if the password has weak combination of letters
     * @param pass: Password (String)
     * @return result: (boolean)
     */
    private boolean isPasswordBad(String pass){
        boolean result = false;
        try {
            // Scan file
            File f = new File("dictbadpass.txt");
            Scanner sf = new Scanner(f);
            // Check for each line (word)
            while (sf.hasNextLine()){
                String s = sf.nextLine();
                // Check if the password contains this weak/bad password
                if (pass.contains(s)){
                    result = true;
                }
            }
            // Closse file
            sf.close();
        } catch (IOException ex) {
            System.out.println(ex);
        }
        // Return the result
        return result;
    }
    
    /**
     * This is a method to encrypt a password with salt
     * It inputs a list of string which at first index it has the original password and in the second index it has the salt
     * This method changes this list throughout its body
     * @param passSalt: (String[])
     */
    private void encryptWithSalt(String[] passSalt){
        // Create a random salt
        String s = passSalt[1];
        if (s.equals("")){
            for (int i = 0; i < 10; i++){
                s += (char)((int)((Math.random()*82)+45));
            }
        }
        // Include it in original password
        String newPass = passSalt[0] + s;
        // Encrypt the new password
        String enpass = EncryptionMachine.encrypt(newPass);
        // Update the input list (Encrypted password, salt)
        passSalt[0] = enpass;
        passSalt[1] = s; 
    }
}
