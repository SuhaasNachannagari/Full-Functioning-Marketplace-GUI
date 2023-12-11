/**
 * Project 5 - User
 *
 * This class helps implement the login features of the code by allowing easy storage of username and passwords.
  * @author a
 * <p>Purdue University -- CS18000 -- Fall 2023</p>
 *
 * @version December 11th, 2023
 */


public class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
