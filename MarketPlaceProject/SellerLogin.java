import java.io.*;
import java.util.ArrayList;
import java.util.List;
/*
Login for sellers:
Once the user says they are a seller,
ask them whether they have an existing account or not.
If: The account exists, check if the username exists and return an error if it doesn't.
Ask for the password and if its correct go into the rest of the code.
If: The account doesn't exist, ask them for a username and return an error if the username already exists.
ask them for a password.
*/

public class SellerLogin {
    private List<User> users;

    public SellerLogin() {
        users = new ArrayList<>();
    }

    public void saveToFile(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("SellerLoginDetails.txt", true))) {
            /*for (int i = 0; i < users.size(); i++) {
                String toFile = users.get(i).getUsername() + "," + users.get(i).getPassword() + "\n";
                writer.write(toFile);
            }*/
            String toFile = user.getUsername() + "," + user.getPassword() + "\n";
            writer.write(toFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("SellerLoginDetails.txt")) ){
            String line;
            while ((line = reader.readLine()) != null && !(line.isEmpty())) {
                String[] userdet = line.split(",");
                User user = new User(userdet[0], userdet[1]);
                users.add(user);
            }
            } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        //System.out.println("User data loaded from " + "filler.txt");
    }

    public boolean checkExistingUserName(String userName) {
        loadFromFile();
        boolean exists = false;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(userName)) {
                exists = true;
                return exists;
            }
        }
        return exists;
    }

    public void createUser(String username, String password) {
        loadFromFile();
        // Check if the username already exists
        if (getUserByUsername(username) == null) {
            User newUser = new User(username, password);
            users.add(newUser);
            System.out.println("User " + username + " created successfully.");
            saveToFile(newUser);
        } else {
            System.out.println("Username already exists. Please choose a different one.");
        }
    }

    public boolean loginUser(String username, String password) {
        loadFromFile();
        User user = getUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            System.out.println("Login successful for " + username);
            return true;
        } else {
            System.out.println("Login failed. Please check your username and password.");
            return false;
        }
    }

    private User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    /*public static void main(String[] args) {
        Login loginSystem = new LoginS();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Login");
            System.out.println("2. Create New User");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter username: ");
                    String loginUsername = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String loginPassword = scanner.nextLine();
                    loginSystem.loginUser(loginUsername, loginPassword);
                    break;

                case 2:
                    System.out.print("Enter username for the new user: ");
                    String newUsername = scanner.nextLine();
                    System.out.print("Enter password for the new user: ");
                    String newPassword = scanner.nextLine();
                    loginSystem.createUser(newUsername, newPassword);
                    break;

                case 3:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice. Please choose a valid option.");
            }
        }
    }*/
}

