import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Project 4 - Main
 *
 * This class represents the marketplace.
 */

public class main {
    public static ArrayList<Seller> sellers;

    // added static, but not updated
    public static ArrayList<Seller> getSellers() {
        return sellers;
    }

    public static ArrayList<Customer> customers;

    // added static for getCust, but not updated;
    public static ArrayList<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(ArrayList<Customer> customers) {
        this.customers = customers;
    }
    static boolean checkIndexUser;
    static boolean checkIndexDoAgain;
    static int checkDoAgain;
    static String username = null;

    public static void main(String[] args) {
        // this read the file and set the "sellers" variable
        //sellers = readDataSeller();
        if (!readDataSeller().isEmpty()) { sellers = readDataSeller(); }
        if (!readDataCustomer().isEmpty()) { customers = readDataCustomer(); }


        Scanner scanner = new Scanner(System.in);
        System.out.println("Filler");//Fill in the welcome message.
        boolean correctInput = true;
        // Tri: I bring this out so that I can use this variable later;
        int custOrSell;
        //
        
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
                        runSeller();


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
                        // Add new seller
                        if (username.equals("back")) {
                            break;
                        }
                        System.out.println("Please enter your password.");
                        String password = scanner.nextLine();

                        sellerLogin.createUser(username, password);
                        ArrayList<Store> storesList = new ArrayList<>();
                        ArrayList<Product> productsList = new ArrayList<>();
                        storesList.add(new Store(productsList,""));
                        productsList.add(new Product("", "", "",0, 0.0));
                        sellers.add(new Seller(storesList, username));
                        /*
                        Rest of the code for a new account.
                         */
                        runSeller();


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
                        runCustomer();



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

                        ArrayList<Product> shoppingCart = new ArrayList<>();
                        shoppingCart.add(new Product("","", "",0,0.0));
                        customers.add(new Customer(null, username));
                        /*
                        Rest of the code for a new account.
                         */
                        runCustomer();


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


        // added but haven't updated
        writeDateSeller();
        writeDataCustomer();
    }

    // write information of each Seller in the Sellers ArrayList to a file called SellerInfo.bi using Object Output Stream
    // purpose: store sellers' data (which also include Products and Stores data
    public static void writeDateSeller() {
        ArrayList<Seller> sellerData = getSellers();
        try (FileOutputStream fos = new FileOutputStream("SellerInfo.bin");
             ObjectOutput oos = new ObjectOutputStream(fos);) {
            for (int i = 0; i < sellerData.size(); i++) {
                oos.writeObject(sellerData.get(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // read the file (SellerInfo.bi) that contains Sellers' data, this method will return an ArrayList<Seller>
    public static ArrayList<Seller> readDataSeller() {
        ArrayList<Seller> result = null;
        try (FileInputStream fis = new FileInputStream("SellerInfo.bin");
             ObjectInputStream ois = new ObjectInputStream(fis);) {
            result = new ArrayList<>();
            for (; ; ) {
                result.add((Seller) ois.readObject());
            }
        } catch (EOFException eof) {
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public static void writeDataCustomer() {
        ArrayList<Customer> customerData = getCustomers();
        try (FileOutputStream fos = new FileOutputStream("CustomerInfo.bin");
             ObjectOutputStream oos = new ObjectOutputStream(fos);) {
            for (int i = 0; i < customerData.size(); i++) {
                oos.writeObject(customerData.get(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static ArrayList<Customer> readDataCustomer() {
        ArrayList<Customer> result = null;
        try (FileInputStream fis = new FileInputStream("CustomerInfo.bin");
             ObjectInputStream ois = new ObjectInputStream(fis);) {
            result = new ArrayList<>();
            for (; ; ) {
                result.add((Customer) ois.readObject());
            }
        } catch (EOFException eof) {
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return result;
    }
    public static void runSeller() {
        Scanner scanner = new Scanner(System.in);
        do {
            do {
                checkIndexUser = true;
                System.out.println("What do you want to do?");
                System.out.println("1 - Delete, 2 - Edit, 3 - Create, 4 - View, 5 - Import/Export, 6 - Dashboard ");
                int option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    // Tri
                    case 1:
                        Delete delete = new Delete();
                        delete.setSeller(username);
                        break;
                    case 2:
                        Edit edit = new Edit();
                        edit.setSeller(username);
                        break;
                    case 3:
                        Create create = new Create();
                        create.setSeller(username);
                        break;
                    //Raghav
                    case 4:
                        //view
                        for (int i = 0; i < sellers.size(); i++) {
                            if (sellers.get(i).getUserName().equals(username)) {
                                //sellers.get(i).view();
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
                                    if (sellers.get(i).getUserName().equals(username)) {
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
                                    if (sellers.get(i).getUserName().equals(username)) {
                                        if (sellers.get(i).getStores().get(i).getName().equals(storeName)) {
                                            sellers.get(i).saveToFileProduct(product);
                                        }
                                    } else {
                                        System.out.println("No store found. Making a new one.");
                                        ArrayList<Product> productsInStore = new ArrayList<>();
                                        productsInStore.add(product);
                                        Store store = new Store(productsInStore, storeName);
                                        sellers.get(i).getStores().add(store);
                                    }
                                }
                            } else if (choice == 3) {
                                flag = false;
                            } else {
                                System.out.println("Invalid choice. Try again");
                            }
                        } while (flag);
                        break;

                    case 6:
                        //Dashboard
                        Dashboard dashboard = new Dashboard();
                        dashboard.setSeller(username);
                        break;
                    //Raghav
                    default:
                        System.out.println("Please enter the correct number!");
                        checkIndexUser = false;
                        break;
                }
            } while (!checkIndexUser);

            do {
                checkIndexDoAgain = false;
                System.out.println("Do you want to use the program again? ( 1 - Yes, 2 - No");
                checkDoAgain = scanner.nextInt();
                scanner.nextLine();

                if (checkDoAgain != 2 && checkDoAgain != 1) {
                    checkIndexDoAgain = true;
                    System.out.println("Please enter the correct number");
                }
            } while (checkIndexDoAgain);

        } while (checkDoAgain == 1);
    }

    public static void runCustomer() {
        do {
            Scanner scanner = new Scanner(System.in);
            do {
                checkIndexUser = true;
                System.out.println("What do you want to do?");
                System.out.println("1 - Sort, 2 - View, 3 - Search, 4 - Shopping Carts, 5 - Purchased Items, 6 - Dashboard ");
                int option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    // Thomas, Suhaas, and Rohan
                    case 1:
                        Sort sorter = new Sort();
                        boolean checkSortBy = true;
                        do {
                            System.out.println("What do you want to do?");
                            System.out.println("1 - Sort by prices, 2 - Sort by quantity");
                            int sortBy = scanner.nextInt();
                            if (sortBy == 1) {
                                sorter.sortByPrice();
                                System.out.println("Which product number would you like to look at?");
                                int priceNum = scanner.nextInt();
                                boolean validPriceNum = true;
                                do {
                                    validPriceNum = sorter.priceShowProduct(priceNum);
                                    if (!validPriceNum) {
                                        System.out.println("Enter a valid input please:");
                                        priceNum = scanner.nextInt();
                                    }
                                } while (!validPriceNum);
                                System.out.println("Would you like to: 1 - Purchase the Product, 2 - Add the Product to your cart, 3 - Leave a review.");
                                boolean validActionProduct = true;
                                int actionProduct = 0;
                                do {
                                    System.out.println("Would you like to: 1 - Purchase the Product, 2 - Add the Product to your cart, 3 - Leave a review. ");
                                    actionProduct = scanner.nextInt();
                                    switch (actionProduct) {
                                        case 1:
                                            System.out.println("How much of the product would you like to buy?");
                                            int quantityPurchased = scanner.nextInt();
                                            sorter.quantityPurchaseItems(username, quantityPurchased, priceNum);
                                            break;
                                        case 2:
                                            System.out.println("How much of the product would you like to add to cart??");
                                            int quantityToShoppingCart = scanner.nextInt(); scanner.nextLine();
                                            sorter.quantityAddToShoppingCart(username, quantityToShoppingCart, priceNum);
                                            break;
                                        case 3:
                                            System.out.println("Type your review below: ");
                                            String review = scanner.nextLine();
                                            sorter.addReview(review, priceNum);
                                            break;
                                        default:
                                            System.out.println("Enter a valid input");
                                            validActionProduct = false;
                                    }
                                } while (!validActionProduct);
                            } else if (sortBy == 2) {
                                sorter.sortByQuantity();
                                System.out.println("Which product number would you like to look at?");
                                int quantityNum = scanner.nextInt();
                                boolean validQuantityNum = true;
                                do {
                                    validQuantityNum = sorter.quantityShowProduct(quantityNum);
                                    if (!validQuantityNum) {
                                        System.out.println("Enter a valid input please:");
                                        int priceNum = scanner.nextInt();
                                    }
                                } while (!validQuantityNum);
                                boolean validActionProduct = true;
                                int actionProduct = 0;
                                do {
                                    System.out.println("Would you like to: 1 - Purchase the Product, 2 - Add the Product to your cart, 3 - Leave a review. ");
                                    actionProduct = scanner.nextInt();
                                    switch (actionProduct) {
                                        case 1:
                                            System.out.println("How much of the product would you like to buy?");
                                            int quantityPurchased = scanner.nextInt();
                                            sorter.quantityPurchaseItems(username, quantityPurchased, quantityNum);
                                            break;
                                        case 2:
                                            System.out.println("How much of the product would you like to add to cart??");
                                            int quantityToShoppingCart = scanner.nextInt(); scanner.nextLine();
                                            sorter.quantityAddToShoppingCart(username, quantityToShoppingCart, quantityNum);
                                            break;
                                        case 3:
                                            System.out.println("Type your review below: ");
                                            String review = scanner.nextLine();
                                            sorter.addReview(review, quantityNum);
                                            break;
                                        default:
                                            System.out.println("Enter a valid input");
                                            validActionProduct = false;
                                    }
                                } while (!validActionProduct);
                            } else {
                                System.out.println("Please enter the correct number");
                                checkSortBy = false;
                            }
                        } while (!checkSortBy);
                        break;
                    case 2:
                        View viewer = new View();
                        viewer.listProducts();
                        System.out.println("Which product number would you like to look at?");
                        int itemNum = scanner.nextInt();
                        boolean validItemNum = true;
                        do {
                            validItemNum = viewer.showProduct(itemNum);
                            if (!validItemNum) {
                                System.out.println("Enter a valid input please:");
                                itemNum = scanner.nextInt();
                            }
                        } while (!validItemNum);
                        System.out.println("Would you like to: 1 - Purchase the Product, 2 - Add the Product to your cart, 3 - Leave a review.");
                        boolean validActionProduct = true;
                        int actionProduct = 0;
                        do {
                            System.out.println("Would you like to: 1 - Purchase the Product, 2 - Add the Product to your cart, 3 - Leave a review. ");
                            actionProduct = scanner.nextInt();
                            switch (actionProduct) {
                                case 1:
                                    System.out.println("How much of the product would you like to buy?");
                                    int quantityPurchased = scanner.nextInt();
                                    viewer.purchaseItem(username, quantityPurchased, itemNum);
                                    break;
                                case 2:
                                    System.out.println("How much of the product would you like to add to cart??");
                                    int quantityToShoppingCart = scanner.nextInt(); scanner.nextLine();
                                    viewer.addToShoppingCart(username, quantityToShoppingCart, itemNum);
                                    break;
                                case 3:
                                    System.out.println("Type your review below: ");
                                    String review = scanner.nextLine();
                                    viewer.addReview(review, itemNum);
                                    break;
                                default:
                                    System.out.println("Enter a valid input");
                                    validActionProduct = false;
                            }
                        } while (!validActionProduct);
                        break;
                    case 3:
                        Search searcher = new Search();
                        int searchAgain = 1;
                        do {
                            System.out.println("What would you like to search?");
                            String search = scanner.nextLine();
                            boolean isMatch = searcher.searchProducts(search);
                            if (isMatch) {
                                System.out.println("Which product number would you like to look at?");
                                int searchNum = scanner.nextInt();
                                boolean validSearchNum = true;
                                do {
                                    validSearchNum = searcher.showProduct(searchNum);
                                    if (!validSearchNum) {
                                        System.out.println("Enter a valid input please:");
                                        searchNum = scanner.nextInt();
                                    }
                                } while (!validSearchNum);
                                System.out.println("Would you like to: 1 - Purchase the Product, 2 - Add the Product to your cart, 3 - Leave a review.");
                                boolean validSearchProduct = true;
                                int searchProduct = 0;
                                do {
                                    System.out.println("Would you like to: 1 - Purchase the Product, 2 - Add the Product to your cart, 3 - Leave a review. ");
                                    searchProduct = scanner.nextInt();
                                    switch (searchProduct) {
                                        case 1:
                                            System.out.println("How much of the product would you like to buy?");
                                            int quantityPurchased = scanner.nextInt();
                                            searcher.purchaseItem(username, quantityPurchased, searchNum);
                                            break;
                                        case 2:
                                            System.out.println("How much of the product would you like to add to cart??");
                                            int quantityToShoppingCart = scanner.nextInt(); scanner.nextLine();
                                            searcher.addToShoppingCart(username, quantityToShoppingCart, searchNum);
                                            break;
                                        case 3:
                                            System.out.println("Type your review below: ");
                                            String review = scanner.nextLine();
                                            searcher.addReview(review, searchNum);
                                            break;
                                        default:
                                            System.out.println("Enter a valid input");
                                            validSearchProduct = false;
                                    }
                                } while (!validSearchProduct);
                            }
                            System.out.println("Would you like to search again? 1 - Yes, 2 - No");
                            searchAgain = scanner.nextInt();
                        } while (searchAgain == 1);
                        break;
                    case 4:
                        // add code for purchase history
                        break;
                    case 5:
                        // add code for shopping cart
                        break;
                    //Rohan
                    //case 6:
                    //  CustomerDashboard dashboard = new CustomerDashboard();
                    //dashboard.printDashboard();
                    default:
                        System.out.println("Please enter the correct number!");
                        checkIndexUser = false;
                }
            } while (!checkIndexUser);

            do {
                checkIndexDoAgain = false;
                System.out.println("Do you want to use the program again? ( 1 - Yes, 2 - No)");
                checkDoAgain = scanner.nextInt();
                scanner.nextLine();

                if (checkDoAgain != 2 && checkDoAgain != 1) {
                    checkIndexDoAgain = true;
                    System.out.println("Please enter the correct number");
                }
            } while (checkIndexDoAgain);

        } while (checkDoAgain == 1);

        if (checkDoAgain == 2) {
            System.out.println("Have a good day");
        }
    }
}
