/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package loginsystem;

/**
 *
 * @author rezahm
 */
public class User {
    
    // declaring data for the user class
    private String name;
    private String username;
    private String encryptedPass;
    private String salt;
    private int age;
    private String gender;
    
    /**
     * This a a class to create a user.
     * @param name: String
     * @param username: String
     * @param encryptedPass: String
     * @param salt: String
     * @param age: Int
     * @param gender: String
     */
    public User(String name, String username, String encryptedPass, String salt, int age, String gender) {
        this.name = name;
        this.username = username;
        this.encryptedPass = encryptedPass;
        this.salt = salt;
        this.age = age;
        this.gender = gender;
    }
    
    /**
     * This method returns the user's detail
     * @return detail: (String)
     */
    @Override
    public String toString(){
       String detail = "User: \nUsername: " + username + "\nName: " + name + "\nAge: " + age + "\nGender: " + gender + "\n";
       return detail;
    }
    
    /**
     * This method returns the user's name
     * @return name: (String)
     */
    public String getName() {
        return name;
    }

    /**
     * This method returns the user's username
     * @return username: (String)
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method returns the user's encrypted password
     * @return encrypted password: (String)
     */
    public String getEncryptedPass() {
        return encryptedPass;
    }
    
    /**
     * This method returns the user's salt for password
     * @return salt: (String)
     */
    public String getSalt() {
        return salt;
    }

    /**
     * This method returns the user's age
     * @return age: (int)
     */
    public int getAge() {
        return age;
    }

    /**
     * This method returns the user's gender
     * @return gender: (String)
     */
    public String getGender() {
        return gender;
    }
}
