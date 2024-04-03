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
    boolean isLoggedin = false;
            
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        LoginSystem l = new LoginSystem();
        
        // Ask user for the action they want to take
        System.out.println("Do you want to register or login: (type: R or L) ");
        String action = new Scanner(System.in).nextLine();
        
        // Based on the action choose the correct method
        if (action.equals("R")){
          l.register();  
        } else if (action.equals("L")){
          l.login();
        }
        
    }
    
    public LoginSystem() {
        // load users from file
        loadUsers();
    }
    
    /**
     * This a method to register a user
     */
    public void register(){
        // Read all needed varaibles for the function
        String usernm;
        String pass;
        
        // A quick explanation of the function for the user
        System.out.println("For registring you need to give some inputs, if the inputs are not correct you will be asked again.");
        
        // Ask user for their username and input the response until the username is unique
        do{
            System.out.println("Input your username: ");
            usernm = new Scanner(System.in).nextLine();
        } while (!isUniqueName(usernm));
        
        // Ask user for their password and input the response until the password is strong
        do{
            System.out.println("Input your password (more than 5 letters, should have upper/lower case letter, number and special character: ");
            pass = new Scanner(System.in).nextLine();
        } while (!isPasswordStrong(pass));
        
        // encrypt the password with a salt
        String[] passSalt = {pass, ""};
        encryptWithSalt(passSalt);
        String enpass = passSalt[0];
        String salt = passSalt[1];
        
        // Ask user for their name and input the response
        System.out.println("Input your name: ");
        String nm = new Scanner(System.in).nextLine();
        
        // Ask user for their age and input the response
        System.out.println("Input your age: ");
        int age = new Scanner(System.in).nextInt();
        
        // Ask user for their gender and input the response
        System.out.println("Input your gender: ");
        String gender = new Scanner(System.in).nextLine();
        
        // Register the user
        saveUser(nm, usernm, enpass, salt, age, gender);
        
        // Automatic login
        System.out.println("You are registered and logged in, " + nm + ".");
        isLoggedin = true;
    }
    
    /**
     * This a method to login an existing user
     */
    public void login(){
        // Ask user for their username and input the response
        System.out.println("Input your username: ");
        String usernm = new Scanner(System.in).nextLine();
            
        // Ask user for theor password and input the response
        System.out.println("Input your password (more than 5 letters, should have upper/lower case letter, number and special character: ");
        String pass = new Scanner(System.in).nextLine();
        
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
                    System.out.println("You are logged in, " + u.getName() + ".");
                    isLoggedin = true;
                }
            }
        }
        
        // Error message
        if (isLoggedin == false){
            System.out.println("Sorry, the username or password is wrong.");
        }
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
