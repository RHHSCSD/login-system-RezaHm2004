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
    private String password;
    private String encryptedPass;
    private int age;
    private String gender;
    
    /**
     * This a a class to create a user.
     * @param name: String
     * @param username: String
     * @param password: String
     * @param encryptedPass: String
     * @param age: Int
     * @param gender: String
     */
    public User(String name, String username, String password, String encryptedPass, int age, String gender) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.encryptedPass = encryptedPass;
        this.age = age;
        this.gender = gender;
    }
    
    /**
     * This method returns the user's detail
     * @return 
     */
    @Override
    public String toString(){
       String detail = "User: \nUsername: " + username + "\n" + "Name: " + name + "\n" + "Age: " + age + "\n" + "Gender: " + gender + "\n";
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
     * This method returns the user's password
     * @return password: (String)
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method returns the user's encrypted password
     * @return encrypted password: (String)
     */
    public String getEncryptedPass() {
        return encryptedPass;
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
