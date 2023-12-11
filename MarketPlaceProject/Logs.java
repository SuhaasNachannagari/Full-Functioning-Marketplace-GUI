import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Project 5 - Logs
 *
 * Allows for easy login and writing to .txt files to save the login information of users. 
 * The logic and methods developed in this are used in ClientSide for implementing the GUIs for logging in.
 *
 */


public class Logs{


    String username;

    public Logs() {}


    public synchronized static void saveToCustomerFile(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("CustomerLoginDetails.txt", true))) {
            String toFile = user.getUsername() + "," + user.getPassword() + "\n";
            writer.write(toFile);
            writer.flush();
            System.out.println(toFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized static List<String> loadFromCustomerFile() {
        List<String> Customers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("CustomerLoginDetails.txt")) ){
            String line;
            while ((line = reader.readLine()) != null && !(line.isEmpty())) {
                String userdet = line;
                Customers.add(line);
            }
            return Customers;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public boolean checkExistingCustomerUserName(String userName, List<User> Customers) {
        boolean exists = false;
        for (int i = 0; i < Customers.size(); i++) {
            if (Customers.get(i).getUsername().equals(userName)) {
                exists = true;
                return exists;
            }
        }
        return exists;
    }

    public User createCustomer(String username, String password, List<User> Customers) {
        boolean created = false;
        // Check if the username already exists
        if (getCustomerByUsername(username,Customers) == null) {
            User newUser = new User(username, password);
            Customers.add(newUser);
            JOptionPane.showMessageDialog(null, "User " + username + " created successfully.",
                    "Marketplace", JOptionPane.PLAIN_MESSAGE);
            return newUser;
        } else {
            JOptionPane.showMessageDialog(null, "Username already exists. Please choose a different one.",
                    "Marketplace", JOptionPane.PLAIN_MESSAGE);
            return null;
        }
    }

    private User getCustomerByUsername(String username, List<User> Customers) {
        for (User user : Customers) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public boolean loginCustomer(String username, String password, List<User> Customers) {
        loadFromCustomerFile();
        User user = getCustomerByUsername(username, Customers);
        if (user != null && user.getPassword().equals(password)) {
            JOptionPane.showMessageDialog(null, "Login successful for " + username,
                    "Marketplace", JOptionPane.PLAIN_MESSAGE);
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Login failed. Please check your username and password.",
                    "Marketplace", JOptionPane.PLAIN_MESSAGE);
            return false;
        }
    }

    public synchronized static void saveToSellerFile(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("SellerLoginDetails.txt", true))) {
            String toFile = user.getUsername() + "," + user.getPassword() + "\n";
            writer.write(toFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized static List<String> loadFromSellerFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("SellerLoginDetails.txt")) ){
            String line;
            List<String> Sellers = new ArrayList<>();
            while ((line = reader.readLine()) != null && !(line.isEmpty())) {
                Sellers.add(line);
            }
            return Sellers;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public boolean checkExistingSellerUserName(String userName, List<User> Sellers) {
        boolean exists = false;
        for (int i = 0; i < Sellers.size(); i++) {
            if (Sellers.get(i).getUsername().equals(userName)) {
                exists = true;
                return exists;
            }
        }
        return exists;
    }

    public User createSeller(String username, String password, List<User> Sellers) {
        boolean created = false;
        // Check if the username already exists
        if (getSellerByUsername(username, Sellers) == null) {
            User newUser = new User(username, password);
            JOptionPane.showMessageDialog(null, "User " + username + " created successfully.",
                    "Marketplace", JOptionPane.PLAIN_MESSAGE);
            created = true;
            return newUser;
        } else {
            JOptionPane.showMessageDialog(null, "Username already exists. Please choose a different one.",
                    "Marketplace", JOptionPane.PLAIN_MESSAGE);
            created = false;
            return null;
        }
    }

    private User getSellerByUsername(String username, List<User> Sellers) {
        for (User user : Sellers) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public boolean loginSeller(String username, String password, List<User> Sellers) {
        User user = getSellerByUsername(username, Sellers);
        if (user != null && user.getPassword().equals(password)) {
            JOptionPane.showMessageDialog(null, "Login successful for " + username,
                    "Marketplace", JOptionPane.PLAIN_MESSAGE);
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Login failed. Please check your username and password.",
                    "Marketplace", JOptionPane.PLAIN_MESSAGE);
            return false;
        }
    }


    public List<Object> LogIn(List<User> Customers, List<User> Sellers) {
        String password;
        User newUser = null;
        List<Object> toReturn = new ArrayList<>();
        JOptionPane.showMessageDialog(null, "Welcome to the marketplace.",
                "Marketplace", JOptionPane.PLAIN_MESSAGE);
        String[] SellerCustomer = {"Seller", "Customer"};
        String response = (String) JOptionPane.showInputDialog(null, "Are you a seller or a customer?",
                "Marketplace", JOptionPane.QUESTION_MESSAGE, null, SellerCustomer, SellerCustomer[0]);
        int sc = 0;
        boolean checkUsername = false;
        if (response.equals("Seller")) {
            boolean input = false;
            do {
                int yn = JOptionPane.showConfirmDialog(null, "Does the account exist?",
                        "Marketplace", JOptionPane.YES_NO_OPTION);
                if (yn == 0) {
                    do {
                        boolean input1 = false;
                        do {
                            username = JOptionPane.showInputDialog(null, "Please enter your username('back' to go back).",
                                    "Marketplace", JOptionPane.QUESTION_MESSAGE);
                            checkUsername = checkExistingSellerUserName(username, Sellers);
                            if (username.equals("back")) {
                                input = false;
                                break;
                            }
                            if (!checkUsername) {
                                JOptionPane.showMessageDialog(null, "The username is invalid.",
                                        "Marketplace", JOptionPane.PLAIN_MESSAGE);
                                input1 = false;
                            }
                        } while (input1);
                        if (username.equals("back") || !(checkUsername)){
                            break;
                        }
                        password = JOptionPane.showInputDialog(null, "Please enter your password",
                                "Marketplace", JOptionPane.QUESTION_MESSAGE);
                        input = loginSeller(username,password,Sellers);
                        newUser = new User(username,password);
                    } while (!input);
                    toReturn.add(newUser);
                    toReturn.add("Seller");
                    toReturn.add("false");
                    return toReturn;
                } else {
                    do {
                        boolean input1 = false;
                        do {
                            username = JOptionPane.showInputDialog(null, "Please enter your username('back' to go back).",
                                    "Marketplace", JOptionPane.QUESTION_MESSAGE);
                            checkUsername = checkExistingSellerUserName(username,Sellers);
                            if (username.equals("back")) {
                                input = false;
                                break;
                            }
                            if (checkUsername) {
                                JOptionPane.showMessageDialog(null, "The username already exists.",
                                        "Marketplace", JOptionPane.PLAIN_MESSAGE);
                                input1 = false;
                            }
                        } while (input1);
                        if (username.equals("back") || (checkUsername)){
                            input = false;
                            break;
                        }
                        password = JOptionPane.showInputDialog(null, "Please enter your password",
                                "Marketplace", JOptionPane.QUESTION_MESSAGE);
                        newUser = createSeller(username,password,Sellers);
                        input = true;
                    } while (!input);
                    toReturn.add(newUser);
                    toReturn.add("Seller");
                    toReturn.add("true");
                    return toReturn;
                }
            } while (!input);
        } else if (response.equals("Customer")) {
            boolean input = false;
            do {
                int yn = JOptionPane.showConfirmDialog(null, "Does the account exist?",
                        "Marketplace", JOptionPane.YES_NO_OPTION);
                if (yn == 0) {
                    do {
                        boolean input1 = false;
                        do {
                            username = JOptionPane.showInputDialog(null, "Please enter your username('back' to go back).",
                                    "Marketplace", JOptionPane.QUESTION_MESSAGE);
                            checkUsername = checkExistingCustomerUserName(username, Customers);
                            if (username.equals("back")) {
                                input = false;
                                break;
                            }
                            if (!checkUsername) {
                                JOptionPane.showMessageDialog(null, "The username is invalid.",
                                        "Marketplace", JOptionPane.PLAIN_MESSAGE);
                                input1 = false;
                            }
                        } while (input1);
                        if (username.equals("back") || !(checkUsername)){
                            break;
                        }
                        password = JOptionPane.showInputDialog(null, "Please enter your password",
                                "Marketplace", JOptionPane.QUESTION_MESSAGE);
                        input = loginCustomer(username,password,Customers);
                        newUser = new User(username,password);
                    } while (!input);
                    toReturn.add(newUser);
                    toReturn.add("Customer");
                    toReturn.add("false");
                    return toReturn;
                } else {
                    do {
                        boolean input1 = false;
                        do {
                            username = JOptionPane.showInputDialog(null, "Please enter your username('back' to go back).",
                                    "Marketplace", JOptionPane.QUESTION_MESSAGE);
                            checkUsername = checkExistingCustomerUserName(username,Customers);
                            if (username.equals("back")) {
                                input = false;
                                break;
                            }
                            if (checkUsername) {
                                JOptionPane.showMessageDialog(null, "The username already exists.",
                                        "Marketplace", JOptionPane.PLAIN_MESSAGE);
                                input1 = false;
                            }
                        } while (input1);
                        if (username.equals("back") || (checkUsername)){
                            input = false;
                            break;
                        }
                        password = JOptionPane.showInputDialog(null, "Please enter your password",
                                "Marketplace", JOptionPane.QUESTION_MESSAGE);
                        newUser = createCustomer(username,password,Customers);
                        input = true;
                    } while (!input);
                    toReturn.add(newUser);
                    toReturn.add("Customer");
                    toReturn.add("true");
                    return toReturn;
                }
            } while (!input);
        }
        return null;
    }

    public static void main(String[] args) {}
}
