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
    static final String DELIMETER = ",";
    static ArrayList<User> users = new ArrayList<User>();
    static boolean isLoggedin = false;
            
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        loadUsers();
        
        // Ask user for the action they want to take
        System.out.println("Do you want to register or login: (type: R or L) ");
        String action = new Scanner(System.in).nextLine();
        
        // Based on the action choose the correct method
        if (action.equals("R")){
          register();  
        } else if (action.equals("L")){
            login();
        }
        
    }
    
    /**
     * This a method to register a user
     */
    public static void register(){
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
        saveUser(nm, usernm, pass, pass, age, gender);
        
        // Automatic login
        System.out.println("You are registered and logged in.");
        isLoggedin = true;
    }
    
    /**
     * This a method to login an existing user
     */
    public static void login(){
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
                // Chech if the password exists
                if (u.getPassword().equals(pass)){
                    // then login
                    System.out.println("You are logged in.");
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
    private static void loadUsers(){
        try {
            File f = new File("Users.txt");
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
     * @param pass: Password(String)
     * @param enpass: Encrypted Password(String)
     * @param age: (int)
     * @param gender: (String)
     */
    private static void saveUser(String nm, String usernm, String pass, String enpass, int age, String gender){
        try {
            File f = new File("Users.txt");
            PrintWriter pf = new PrintWriter(new FileWriter(f,true));
            pf.println(nm + DELIMETER + usernm + DELIMETER + pass + DELIMETER + enpass + DELIMETER + age + DELIMETER + gender);
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
    private static boolean isUniqueName(String usernm){
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
    private static boolean isPasswordStrong(String pass){
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
        result = hasNumber && hasMinLen && hasLowCase && hasUpCase && hasSpecChar;
        
        // Return the result of the password check
        return result;
    }
}
