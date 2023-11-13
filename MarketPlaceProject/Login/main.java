import java.util.ArrayList;
import java.util.Scanner;

public class main extends Marketplace {
    private static ArrayList<Seller> sellers;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Filler");//Fill in the welcome message.
        boolean correctInput = true;
        // Tri: I bring this out so that I can use this variable later;
        int custOrSell;
        //
        String username = "";
        do {
            System.out.println("Are you a customer or a seller?" +
                    "(1 - Seller, 2 - Customer, 3 - Exit)");
            custOrSell = scanner.nextInt();
            scanner.nextLine();
            if (custOrSell == 1) { //Seller
                SellerLogin sellerLogin = new SellerLogin();
                boolean correctInput1 = true;
                do {
                    System.out.println("Does the account already exist? (1 - Exists, 2 - New Account)");
                    int input = scanner.nextInt();
                    scanner.nextLine();
                    if (input == 1) {
                        boolean usernameExists;
                        boolean passwordIsCorrect;
                        do {
                            do {
                                System.out.println("Please enter your username(enter 'back' to go back): ");
                                username = scanner.nextLine();
                                if (username.equals("back")) {
                                    correctInput1 = false;
                                    break;
                                }
                                usernameExists = sellerLogin.checkExistingUserName(username);
                                if (!usernameExists) {
                                    System.out.println("Please input a valid username.");
                                }
                            } while (!usernameExists);
                            if (username.equals("back")) {
                                break;
                            }
                            System.out.println("Please enter your password.");
                            String password = scanner.nextLine();
                            passwordIsCorrect = sellerLogin.loginUser(username, password);
                        } while (!passwordIsCorrect);
                        if (username.equals("back")) {
                            break;
                        }
                        /*
                        Continue The code here for an existing seller.
                         */


                    } else if (input == 2) {
                        boolean usernameExists;
                        do {
                            System.out.println("Please enter your username(enter 'back' to go back): ");
                            username = scanner.nextLine();
                            if (username.equals("back")) {
                                correctInput1 = false;
                                break;
                            }
                            usernameExists = sellerLogin.checkExistingUserName(username);
                            if (usernameExists) {
                                System.out.println("This username already exists.");
                            }
                        } while (usernameExists);
                        if (username.equals("back")) {
                            break;
                        }
                        System.out.println("Please enter your password.");
                        String password = scanner.nextLine();
                        sellerLogin.createUser(username, password);
                        /*
                        Rest of the code for a new account.
                         */


                    } else {
                        System.out.println("Incorrect input, please try again.");
                        correctInput1 = false;
                    }
                } while (!correctInput1);
            } else if (custOrSell == 2) {
                CustomerLogin customerLogin = new CustomerLogin();
                boolean correctInput1 = true;
                do {
                    System.out.println("Does the account already exist? (1 - Exists, 2 - New Account)");
                    int input = scanner.nextInt();
                    scanner.nextLine();
                    if (input == 1) {
                        boolean usernameExists;
                        boolean passwordIsCorrect;
                        do {
                            do {
                                System.out.println("Please enter your username(enter 'back' to go back): ");
                                username = scanner.nextLine();
                                if (username.equals("back")) {
                                    correctInput1 = false;
                                    break;
                                }
                                usernameExists = customerLogin.checkExistingUserName(username);
                                if (!usernameExists) {
                                    System.out.println("Please input a valid username.");
                                }
                            } while (!usernameExists);
                            if (username.equals("back")) {
                                break;
                            }
                            System.out.println("Please enter your password.");
                            String password = scanner.nextLine();
                            passwordIsCorrect = customerLogin.loginUser(username, password);
                        } while (!passwordIsCorrect);
                        if (username.equals("back")) {
                            break;
                        }
                        /*
                        Continue The code here for an existing customer.
                         */

                    } else if (input == 2) {
                        boolean usernameExists;
                        do {
                            System.out.println("Please enter your username(enter 'back' to go back): ");
                            username = scanner.nextLine();
                            if (username.equals("back")) {
                                correctInput1 = false;
                                break;
                            }
                            usernameExists = customerLogin.checkExistingUserName(username);
                            if (usernameExists) {
                                System.out.println("This username already exists.");
                            }
                        } while (usernameExists);
                        if (username.equals("back")) {
                            break;
                        }
                        System.out.println("Please enter your password.");
                        String password = scanner.nextLine();
                        customerLogin.createUser(username, password);
                        /*
                        Rest of the code for a new account.
                         */

                    } else {
                        System.out.println("Incorrect input, please try again.");
                        correctInput1 = false;
                    }
                } while (!correctInput1);
            } else if (custOrSell == 3) {
                break;
            } else {
                System.out.println("Incorrect input, please try again.");
                correctInput = false;
            }
        } while (!correctInput);

        //body
        if (custOrSell == 1) {
            do {
                do {
                    checkIndexUser = true;
                    System.out.println("What do you want to do?");
                    System.out.println("1 - Delete, 2 - Edit, 3 - Create, 4 - View, 5 - Import/Export, 6 - Dashboard ");
                    int option = scanner.nextInt();
                    scanner.nextLine();

                    switch (option) {
                        case 1:
                            Delete delete = null;
                            delete.setSeller(dummyUserName);
                        case 2:
                            Edit edit = null;
                            edit.setSeller(dummyUserName);
                        case 3:
                            Create create = null;
                            create.setSeller(dummyUserName);
                            break;
                        //Raghav
                        case 4:
                            //view
                            for (int i = 0; i < sellers.size(); i++) {
                                if (sellers.get(i).getUsername().equals(username)) {
                                    sellers.get(i).view();
                                }
                            }
                            break;
                        case 5:
                            //import export files
                            int choice;
                            boolean flag = true;
                            do {
                                System.out.println("Do you want to import or export files (1 - Import, 2 - Export, " +
                                        "3 - exit");
                                choice = scanner.nextInt();

                                if (choice == 1) {
                                    for (int i = 0; i < sellers.size(); i++) {
                                        if (sellers.get(i).getUsername().equals(username)) {
                                            sellers.get(i).loadFromFileProduct();
                                        }
                                    }

                                } else if (choice == 2) {
                                    System.out.println("Enter product details you want to add to export file:");
                                    String productName = scanner.nextLine();
                                    String storeName = scanner.nextLine();
                                    String description = scanner.nextLine();
                                    int quantity = scanner.nextInt();
                                    double price = scanner.nextDouble();

                                    Product product = new Product(productName, storeName, description, quantity, price);

                                    for (int i = 0; i < sellers.size(); i++) {
                                        if (sellers.get(i).getUsername().equals(username)) {
                                            if (sellers.get(i).getStores().get(i).getName().equals(storeName)) {
                                                sellers.get(i).saveToFileProduct(product);
                                            }
                                        } else {
                                            System.out.println("No store found. Making a new one.");
                                            ArrayList<Product> productsInStore = new ArrayList<>();
                                            productsInStore.add(product);
                                            Store store = new Store(productsInStore, storeName);
                                            sellers.get(i).stores.add(store);
                                        }
                                    }
                                } else if(choice == 3){
                                    flag = false;
                                } else {
                                    System.out.println("Invalid choice. Try again");
                                }
                            } while (flag);
                            break;

                        case 6:
                            //Dashboard
                            for(int i = 0; i < sellers.size(); i++) {
                                System.out.println("Store:");
                                System.out.println(sellers.get(i).getStores().get(i));
                                System.out.println("Products\tPrice");
                                for(int j = 0; j < sellers.get(i).getStores().get(i).getProducts().size(); j++) {
                                    System.out.println(sellers.get(i).getStores().get(i).getProducts().get(j) + "\t"
                                            + sellers.get(i).getStores().get(i).getProducts().get(j).getSales());
                                }
                            }
                            break;
                            //Raghav

                        default:
                            System.out.println("Please enter the correct number!");
                            checkIndexUser = false;
                    }
                } while (!checkIndexUser);

                do {
                    checkIndexDoAgain = true;
                    System.out.println("Do you want to use the program again? ( 1 - Yes, 2 - No");
                    checkDoAgain = scanner.nextInt();
                    scanner.nextLine();

                    if (checkDoAgain != 2 || checkDoAgain != 1) {
                        checkIndexDoAgain = false;
                        System.out.println("Please enter the correct number");
                    }
                } while (!checkIndexDoAgain);

            } while (checkDoAgain == 1);
        }

        if (custOrSell == 2) {
            do {
                do {
                    checkIndexUser = true;
                    System.out.println("What do you want to do?");
                    System.out.println("1 - Sort, 2 - View, 3 - Search, 4 - Shopping Carts, 5 - Purchased Items, 6 - Dashboard ");
                    int option = scanner.nextInt();
                    scanner.nextLine();

                    switch (option) {
                        // Thomas, Suhaas, and Rohan
                        default:
                            System.out.println("Please enter the correct number!");
                            checkIndexUser = false;
                    }
                } while (!checkIndexUser);

                do {
                    checkIndexDoAgain = true;
                    System.out.println("Do you want to use the program again? ( 1 - Yes, 2 - No");
                    checkDoAgain = scanner.nextInt();
                    scanner.nextLine();

                    if (checkDoAgain != 2 || checkDoAgain != 1) {
                        checkIndexDoAgain = false;
                        System.out.println("Please enter the correct number");
                    }
                } while (!checkIndexDoAgain);

            } while (checkDoAgain == 1);

            if (checkDoAgain == 2) {
                System.out.println("Have a good day");
            }

        }

    }
}
