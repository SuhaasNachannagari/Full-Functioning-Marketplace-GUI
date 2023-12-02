import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Logs {
    private List<User> Customers;
    private List<User> Sellers;

    String username;

    public Logs() {
        Customers = new ArrayList<>();
        Sellers = new ArrayList<>();
    }

    public void saveToCustomerFile(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("CustomerLoginDetails.txt", true))) {
            String toFile = user.getUsername() + "," + user.getPassword() + "\n";
            writer.write(toFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFromCustomerFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("CustomerLoginDetails.txt")) ){
            String line;
            while ((line = reader.readLine()) != null && !(line.isEmpty())) {
                String[] userdet = line.split(",");
                User user = new User(userdet[0], userdet[1]);
                Customers.add(user);
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public boolean checkExistingCustomerUserName(String userName) {
        loadFromCustomerFile();
        boolean exists = false;
        for (int i = 0; i < Customers.size(); i++) {
            if (Customers.get(i).getUsername().equals(userName)) {
                exists = true;
                return exists;
            }
        }
        return exists;
    }

    public boolean createCustomer(String username, String password) {
        loadFromCustomerFile();
        boolean created = false;
        // Check if the username already exists
        if (getCustomerByUsername(username) == null) {
            User newUser = new User(username, password);
            Customers.add(newUser);
            JOptionPane.showMessageDialog(null, "User " + username + " created successfully.",
                    "Marketplace", JOptionPane.PLAIN_MESSAGE);
            saveToCustomerFile(newUser);
            created = true;
        } else {
            JOptionPane.showMessageDialog(null, "Username already exists. Please choose a different one.",
                    "Marketplace", JOptionPane.PLAIN_MESSAGE);
            created = false;
        }
        return created;
    }

    private User getCustomerByUsername(String username) {
        for (User user : Customers) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public boolean loginCustomer(String username, String password) {
        loadFromCustomerFile();
        User user = getCustomerByUsername(username);
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

    public void saveToSellerFile(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("SellerLoginDetails.txt", true))) {
            String toFile = user.getUsername() + "," + user.getPassword() + "\n";
            writer.write(toFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFromSellerFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("SellerLoginDetails.txt")) ){
            String line;
            while ((line = reader.readLine()) != null && !(line.isEmpty())) {
                String[] userdet = line.split(",");
                User user = new User(userdet[0], userdet[1]);
                Sellers.add(user);
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public boolean checkExistingSellerUserName(String userName) {
        loadFromSellerFile();
        boolean exists = false;
        for (int i = 0; i < Sellers.size(); i++) {
            if (Sellers.get(i).getUsername().equals(userName)) {
                exists = true;
                return exists;
            }
        }
        return exists;
    }

    public boolean createSeller(String username, String password) {
        loadFromSellerFile();
        boolean created = false;
        // Check if the username already exists
        if (getSellerByUsername(username) == null) {
            User newUser = new User(username, password);
            Sellers.add(newUser);
            JOptionPane.showMessageDialog(null, "User " + username + " created successfully.",
                    "Marketplace", JOptionPane.PLAIN_MESSAGE);
            saveToSellerFile(newUser);
            created = true;
        } else {
            JOptionPane.showMessageDialog(null, "Username already exists. Please choose a different one.",
                    "Marketplace", JOptionPane.PLAIN_MESSAGE);
            created = false;
        }
        return created;
    }

    private User getSellerByUsername(String username) {
        for (User user : Sellers) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public boolean loginSeller(String username, String password) {
        loadFromSellerFile();
        User user = getSellerByUsername(username);
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


    public int LogIn() {
        String password;
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
                            checkUsername = checkExistingSellerUserName(username);
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
                        input = loginSeller(username,password);
                    } while (!input);
                } else {
                    do {
                        boolean input1 = false;
                        do {
                            username = JOptionPane.showInputDialog(null, "Please enter your username('back' to go back).",
                                    "Marketplace", JOptionPane.QUESTION_MESSAGE);
                            checkUsername = checkExistingCustomerUserName(username);
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
                            break;
                        }
                        password = JOptionPane.showInputDialog(null, "Please enter your password",
                                "Marketplace", JOptionPane.QUESTION_MESSAGE);
                        input = createCustomer(username,password);
                    } while (!input);
                }
            } while (!input);
            return 1;
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
                            checkUsername = checkExistingCustomerUserName(username);
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
                        input = loginCustomer(username,password);
                    } while (!input);
                } else {
                    do {
                        boolean input1 = false;
                        do {
                            username = JOptionPane.showInputDialog(null, "Please enter your username('back' to go back).",
                                    "Marketplace", JOptionPane.QUESTION_MESSAGE);
                            checkUsername = checkExistingCustomerUserName(username);
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
                            break;
                        }
                        password = JOptionPane.showInputDialog(null, "Please enter your password",
                                "Marketplace", JOptionPane.QUESTION_MESSAGE);
                        input = createCustomer(username,password);
                    } while (!input);
                }
            } while (!input);
            return 0;
        }
        return 0;
    }



    public static void main(String[] args) {
        Logs log = new Logs();
        log.LogIn();
    }
}
