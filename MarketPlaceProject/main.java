import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Project 4 - Main
 *
 * This class represents the marketplace.
 */

public class main {
    public static ArrayList<Seller> sellers = new ArrayList<>();

    // added static, but not updated
    public static ArrayList<Seller> getSellers() { return sellers; }
    public static ArrayList<Customer> customers = new ArrayList<>();

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
    public static String username = null;

    public static void main(String[] args) throws IOException { // throws IOException cuz of runCustomer()
        // this read the file and set the "sellers" variable
        //sellers = readDataSeller();
        //readDataCustomer();
        sellers = readDataSeller();
        customers = readDataCustomer();

        System.out.println(sellers.size() + " checkSellerSize0");

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the marketplace.");
        boolean correctInput = true;
        int custOrSell = 0;

        do {
            boolean checkFormat1;
            do {
                checkFormat1 = true;
                try {
                    System.out.println("Are you a customer or a seller?" +
                            "(1 - Seller, 2 - Customer, 3 - Exit)");
                    custOrSell = scanner.nextInt();
                    scanner.nextLine();
                } catch (NumberFormatException e) {
                    checkFormat1 = false;
                    System.out.println("Enter the correct format!");
                }
            } while (!checkFormat1);
            if (custOrSell == 1) { //Seller
                SellerLogin sellerLogin = new SellerLogin();
                boolean correctInput1 = true;
                do {
                    int input = 0;
                    boolean checkFormat;
                    do {
                        checkFormat = true;
                        try {
                            System.out.println("Does the account already exist? (1 - Exists, 2 - New Account)");
                            input = scanner.nextInt();
                            scanner.nextLine();
                        } catch (NumberFormatException e) {
                            System.out.println("Enter the correct format!");
                            checkFormat = false;
                        }
                    } while (!checkFormat);
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
                        storesList.add(new Store(productsList, ""));
                        productsList.add(new Product("", "", "", 0, 0.0));
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
                    int input = 0;
                    boolean checkFormat2;
                    do {
                        checkFormat2 = true;
                        try {
                            System.out.println("Does the account already exist? (1 - Exists, 2 - New Account)");
                            input = scanner.nextInt();
                            scanner.nextLine();
                        } catch (NumberFormatException e) {
                            System.out.println("Please enter the correct format!");
                            checkFormat2 = false;
                        }
                    } while (!checkFormat2);
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
                        shoppingCart.add(new Product("", "", "", 0, 0.0));
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

    }

    public static void runSeller() {
        Scanner scanner = new Scanner(System.in);
        do {
            do {
                checkIndexUser = true;
                System.out.println("What do you want to do?");
                boolean checkFormat;
                int option = 0;
                do {
                    checkFormat = true;
                    try {
                        System.out.println("1 - Delete, 2 - Edit, 3 - Create, 4 - View, 5 - Import/Export, 6 - Dashboard ");
                        option = scanner.nextInt();
                        scanner.nextLine();
                    } catch (NumberFormatException e) {
                        checkFormat = false;
                        System.out.println("Please enter the correct format!");
                    }
                } while (!checkFormat);
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
                                sellers.get(i).view();
                            }
                        }
                        break;
                    case 5:
                        //import export files
                        int choice = 0;
                        boolean flag = true;
                        do {
                            boolean checkFormat1;
                            do {
                                checkFormat1 = true;
                                try {
                                    System.out.println("Do you want to import or export files (1 - Import, 2 - Export, " +
                                            "3 - exit");
                                    choice = scanner.nextInt(); scanner.nextLine();
                                } catch (NumberFormatException e) {
                                    checkFormat1 = false;
                                }
                            } while (!checkFormat1);
                            if (choice == 1) {
                                for (int i = 0; i < sellers.size(); i++) {
                                    if (sellers.get(i).getUserName().equals(username)) {
                                        System.out.println("Enter file name:");
                                        String fileName = scanner.nextLine();
                                        sellers.get(i).loadFromFileProduct(fileName);
                                        break;
                                    }
                                }
                            } else if (choice == 2) {
                                System.out.println("Enter product details you want to add to export file:");
                                String productName = scanner.nextLine();
                                String storeName = scanner.nextLine();
                                String description = scanner.nextLine();
                                int quantity = scanner.nextInt(); scanner.nextLine();
                                double price = scanner.nextDouble(); scanner.nextLine();

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
                System.out.println("Do you want to continue your implementation? ( 1 - Yes, 2 - No");
                checkDoAgain = scanner.nextInt();
                scanner.nextLine();

                if (checkDoAgain != 2 && checkDoAgain != 1) {
                    checkIndexDoAgain = true;
                    System.out.println("Please enter the correct number");
                }
            } while (checkIndexDoAgain);

        } while (checkDoAgain == 1);

        writeDataSeller();
        writeDataCustomer();
    }

    public static void runCustomer() throws IOException { // throw IOException becuz of the Purchased Items;
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
                        System.out.println(customers.size() + " checkCustSize1");
                        Sort sorter = new Sort();
                        boolean checkSortBy = true;
                        do {
                            System.out.println("What do you want to do?");
                            System.out.println("1 - Sort by prices, 2 - Sort by quantity");
                            int sortBy = scanner.nextInt(); scanner.nextLine();
                            if (sortBy == 1) {
                                sorter.sortByPrice();
                                System.out.println("Which product number would you like to look at?");
                                int priceNum = scanner.nextInt(); scanner.nextLine();
                                boolean validPriceNum = true;
                                do {
                                    validPriceNum = sorter.priceShowProduct(priceNum);
                                    if (!validPriceNum) {
                                        System.out.println("Enter a valid input please:");
                                        priceNum = scanner.nextInt(); scanner.nextLine();
                                    }
                                } while (!validPriceNum);

                                boolean validActionProduct = true;
                                int actionProduct = 0;
                                do {
                                    System.out.println("Would you like to: 1 - Purchase the Product, 2 - Add the Product to your cart, 3 - Leave a review. ");
                                    actionProduct = scanner.nextInt(); scanner.nextLine();
                                    switch (actionProduct) {
                                        case 1:
                                            System.out.println("How much of the product would you like to buy?");
                                            int pricePurchased = scanner.nextInt(); scanner.nextLine();
                                            sorter.pricePurchaseItem(username, pricePurchased, priceNum);
                                            break;
                                        case 2:
                                            System.out.println("How much of the product would you like to add to cart??");
                                            int priceToShoppingCart = scanner.nextInt(); scanner.nextLine();
                                            sorter.priceAddToShoppingCart(username, priceToShoppingCart, priceNum);
                                            break;
                                        case 3:
                                            System.out.println("Type your review below: ");
                                            String review = scanner.nextLine();
                                            sorter.addReviewPrice(review,priceNum);
                                            break;
                                        default:
                                            System.out.println("Enter a valid input");
                                            validActionProduct = false;
                                    }
                                } while (!validActionProduct);
                            } else if (sortBy == 2) {
                                sorter.sortByQuantity();
                                System.out.println("Which product number would you like to look at?");
                                int quantityNum = scanner.nextInt();  scanner.nextLine();
                                boolean validQuantityNum = true;
                                do {
                                    validQuantityNum = sorter.quantityShowProduct(quantityNum);
                                    if (!validQuantityNum) {
                                        System.out.println("Enter a valid input please:");
                                        int priceNum = scanner.nextInt(); scanner.nextLine();
                                    }
                                } while (!validQuantityNum);
                                boolean validActionProduct = true;
                                int actionProduct = 0;
                                do {
                                    System.out.println("Would you like to: 1 - Purchase the Product, 2 - Add the Product to your cart, 3 - Leave a review. ");
                                    actionProduct = scanner.nextInt(); scanner.nextLine();
                                    switch (actionProduct) {
                                        case 1:
                                            System.out.println("How much of the product would you like to buy?");
                                            int quantityPurchased = scanner.nextInt(); scanner.nextLine();
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
                                            sorter.addReviewQuantity(review, quantityNum);
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
                        int itemNum = scanner.nextInt(); scanner.nextLine();
                        boolean validItemNum = true;
                        do {
                            validItemNum = viewer.showProduct(itemNum);
                            if (!validItemNum) {
                                System.out.println("Enter a valid input please:");
                                itemNum = scanner.nextInt(); scanner.nextLine();
                            }
                        } while (!validItemNum);

                        boolean validActionProduct = true;
                        int actionProduct = 0;
                        do {
                            System.out.println("Would you like to: 1 - Purchase the Product, 2 - Add the Product to your cart, 3 - Leave a review. ");
                            actionProduct = scanner.nextInt(); scanner.nextLine();
                            switch (actionProduct) {
                                case 1:
                                    System.out.println("How much of the product would you like to buy?");
                                    int quantityPurchased = scanner.nextInt(); scanner.nextLine();
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
                                int searchNum = scanner.nextInt(); scanner.nextLine();
                                boolean validSearchNum = true;
                                do {
                                    validSearchNum = searcher.showProduct(searchNum);
                                    if (!validSearchNum) {
                                        System.out.println("Enter a valid input please:");
                                        searchNum = scanner.nextInt(); scanner.nextLine();
                                    }
                                } while (!validSearchNum);
                                boolean validSearchProduct = true;
                                int searchProduct = 0;
                                do {
                                    System.out.println("Would you like to: 1 - Purchase the Product, 2 - Add the Product to your cart, 3 - Leave a review. ");
                                    searchProduct = scanner.nextInt(); scanner.nextLine();
                                    switch (searchProduct) {
                                        case 1:
                                            System.out.println("How much of the product would you like to buy?");
                                            int quantityPurchased = scanner.nextInt(); scanner.nextLine();
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
                            searchAgain = scanner.nextInt(); scanner.nextLine();
                        } while (searchAgain == 1);
                        break;
                    case 4: // Shopping Cart
                        System.out.println("What do you want to do? ");
                        System.out.println("1 - Remove from cart, 2 - Check out, 3 - View cart, 4 - Add to cart");
                        int choice = scanner.nextInt();
                        scanner.nextLine();

                        for (Customer customer : customers) {
                            if (customer.getCustomerUserName().equals(username)) {

                                switch (choice) {
                                    case 1:
                                        customer.deleteShoppingCart();
                                        break;
                                    case 2:
                                        boolean keepDoing = true;
                                        boolean checkFormat;
                                        int buyIndex = 0;

                                        do {
                                            if (customer.getShoppingCar().isEmpty()) {
                                                System.out.println("Shopping Cart doesn't contain any products" +
                                                        ", please add some!");
                                            } else {

                                                keepDoing = false;
                                                do {
                                                    checkFormat = true;
                                                    try {
                                                        System.out.println("1. Buy one item        2. Buy all");
                                                        buyIndex = scanner.nextInt();
                                                        scanner.nextLine();
                                                    } catch (InputMismatchException e) {
                                                        checkFormat = false;
                                                        System.out.println("Please enter the correct format!");
                                                    }
                                                    if (buyIndex != 1 && buyIndex != 2) {
                                                        checkFormat = false;
                                                        System.out.println("Please enter the correct format!");
                                                    }
                                                } while (!checkFormat);

                                                if (buyIndex == 1) {
                                                    PurchaseCart purchase = new PurchaseCart();
                                                    purchase.showProducts();
                                                } else if (buyIndex == 2) {
                                                    PurchaseCart purchase = new PurchaseCart();
                                                    purchase.purchaseItemALL();
                                                }

                                                boolean checkFormat2;
                                                do {
                                                    checkFormat2 = true;
                                                    int checkKeepDoing = 0;
                                                    try {
                                                        System.out.println("Do you want to continue buying? 1. Yes      2. No");
                                                        checkKeepDoing = scanner.nextInt();
                                                        scanner.nextLine();

                                                        if (checkKeepDoing == 1) {
                                                            keepDoing = true;
                                                        }
                                                    } catch (InputMismatchException e) {
                                                        checkFormat2 = false;
                                                        System.out.println("Please enter the correct format!");
                                                    }
                                                    if (checkKeepDoing != 1 && checkKeepDoing != 2) {
                                                        checkFormat2 = false;
                                                        System.out.println("Please enter the correct format!");
                                                    }
                                                } while (!checkFormat2);
                                            }
                                        } while (keepDoing && !customer.getShoppingCar().isEmpty());


                                        //Export to CSV and clear shoppingCart
                                        //ShoppingCartExporter.exportToCSV(shoppingCart, "shopping_cart.csv");
                                        //Read and display the CSV file
                                        //CSVReader.readCSV("shopping_cart.csv");
                                        break;
                                    case 3:
                                        customer.viewShoppingCart();
                                        break;
                                    case 4:
                                        View view2 = new View();
                                        view2.listProducts();
                                        System.out.println("Which product number would you like to look at?");
                                        int itemNum2 = scanner.nextInt(); scanner.nextLine();
                                        boolean validItemNum2 = true;
                                        do {
                                            validItemNum2 = view2.showProduct(itemNum2);
                                            if (!validItemNum2) {
                                                System.out.println("Enter a valid input please:");
                                                itemNum2 = scanner.nextInt(); scanner.nextLine();
                                            }
                                        } while (!validItemNum2);

                                        System.out.println("How much of the product would you like to add to cart??");
                                        int quantityToShoppingCart = scanner.nextInt(); scanner.nextLine();
                                        view2.addToShoppingCart(username, quantityToShoppingCart, itemNum2);
                                        break;
                                    default:
                                        System.out.println("Invalid choice. Please enter a number between 1 and 4.");
                                }
                            }
                        }

                        break;
                    case 5: //Purchased History
                        boolean checkFormat;
                        int choiceFive = 0;
                        do {
                            checkFormat = true;
                            try {
                                System.out.println("1. Review Purchased History       2. Export Purchase History as csv. file ");
                                choiceFive = scanner.nextInt(); scanner.nextLine();
                            } catch (InputMismatchException e) {
                                checkFormat = false;
                                System.out.println("Please enter the correct format!");
                            }
                            if (!(choiceFive == 1 || choiceFive == 2)) {
                                checkFormat = false;
                                System.out.println("Please enter the correct format!");
                            }
                        } while (!checkFormat);

                        if (choiceFive == 1) {
                            for (Customer customer : customers) {
                                if (customer.getCustomerUserName().equals(username)) {
                                    customer.viewPurchasedHistory();
                                }
                            }
                        } else {
                            System.out.println("Enter the file path:");
                            String filePath = scanner.nextLine();
                            for (Customer customer : customers) {
                                if (customer.getCustomerUserName().equals(username)) {
                                    CheckOut checkOut = new CheckOut();
                                    checkOut.exportToCSV(customer.getPurchaseHistory(),filePath);
                                }
                            }
                        }
                        break;
                    //Rohan
                    case 6:
                        CustomerDashboard dashboard = new CustomerDashboard();
                        Customer customer = null;
                        for (int i = 0; i < customers.size(); i++) {
                            if (customers.get(i).getCustomerUserName().equals(username)) {
                                customer = customers.get(i);
                            }
                        }
                        dashboard.printDashboard(customer);
                        break;
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

        writeDataSeller();
        writeDataCustomer();
    }

    public static void writeDataSeller() {
        ArrayList<Seller> sellerData = sellers ;
        String sellerName;
        String storeName;
        String storeSale;
        String productName;
        String productDescription;
        String productQuant;
        String productPrice;
        String productLimit;
        try {
            PrintWriter pw = new PrintWriter( new FileOutputStream("C:\\qtri\\Purdue\\CS180\\Jetbrains\\Project4CSGOld\\MarketPlaceProject\\SellerInfo.txt"));
            for (int i = 0; i < sellerData.size(); i++) {
                sellerName = sellerData.get(i).getUserName();
                ArrayList<Store> stores = sellerData.get(i).getStores();
                for (int j = 0; j < stores.size(); j++) {
                    storeName = stores.get(j).getName();
                    storeSale = String.valueOf(stores.get(j).getSales());
                    ArrayList<Product> products = stores.get(j).getProducts();
                    if (products.size() == 0) {
                        pw.write(String.format("%s/-%s/-%s/-%s/-%s/-%s/-%s/-%s/-",
                                sellerName, storeName, storeSale, "N/A", "N/A", "0", "0.0", "0" ));
                        pw.write("\n");
                    }

                    for (int k = 0; k < products.size(); k++) {
                        productName = products.get(k).getName();
                        productDescription = products.get(k).getDescription();
                        productQuant = String.valueOf(products.get(k).getQuantAvailable());
                        productPrice = String.valueOf(products.get(k).getPrice());
                        productLimit = String.valueOf(products.get(k).getLimit());
                        ArrayList<String> reviews = new ArrayList<>();
                        for (int o = 0; o < products.get(k).getReviews().size(); o++ ) {
                            reviews.add(products.get(k).getReviews().get(o));
                        }
                        // tri,tri's store,sales,milk,taro flavour,10,5.4,5,review1,review2
                        String ans = String.format("%s/-%s/-%s/-%s/-%s/-%s/-%s/-%s/-",
                                sellerName, storeName, storeSale, productName, productDescription,
                                productQuant, productPrice, productLimit);
                        pw.write(ans);
                        for (int l = 0; l < reviews.size(); l++ ) {
                            pw.write(reviews.get(l) + "/-");
                        }
                        pw.write("\n");
                    }
                }
            }
            pw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static ArrayList<Seller> readDataSeller() {
        ArrayList<String> tempList = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("C:\\qtri\\Purdue\\CS180\\Jetbrains\\Project4CSGOld\\MarketPlaceProject\\SellerInfo.txt"));
            String line = br.readLine();
            while (line != null){
                tempList.add(line);
                line = br.readLine();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String nameTemp = "";
        ArrayList<Seller> sellersTemp = new ArrayList<>();
        Seller seller;
        int indexSeller = -1;
        Store store;
        String storeName = "";
        double storeSales;
        int indexStore = -1;
        Product product;
        ArrayList<String> reviewsTemp;

        // tri,tri's store,sales,milk,taro flavour,10,5.4,5,review1,review2
        for (int i = 0; i < tempList.size(); i++) {
            String[] arr = (tempList.get(i)).split("/-");
            // Seller
            if (nameTemp.equals(arr[0])) {  // check if is still the same Seller
                if (storeName.equals(arr[1])) {  // check if is still in the same Store
                    // Product
                    reviewsTemp = new ArrayList<>();
                    for (int j = 8; j < arr.length; j++) { // create an ArrayList of reviews
                        reviewsTemp.add(arr[j]);
                    }
                    int intQuantAvail = Integer.parseInt(arr[5]);
                    double doublePrice = Double.parseDouble(arr[6]);
                    int intLimit = Integer.parseInt(arr[7]);
                    product = new Product(arr[3],arr[1],arr[4],intQuantAvail, doublePrice);
                    product.setReviews(reviewsTemp);
                    product.setLimit(intLimit);
                    Seller test = sellersTemp.get(indexSeller);

                } else {
                    // from new Store
                    indexStore++;
                    storeName = arr[1];
                    storeSales = Double.parseDouble(arr[2]); // from new store
                    store = new Store(null,storeName);
                    store.setSales(storeSales); // set Sales of store
                    sellersTemp.get(indexSeller).createStore(store); // add new Store to seller's stores
                    // Product
                    reviewsTemp = new ArrayList<>();
                    for (int j = 8; j < arr.length; j++) { // create an ArrayList of reviews
                        reviewsTemp.add(arr[j]);
                    }
                    int intQuantAvail = Integer.parseInt(arr[5]);
                    double doublePrice = Double.parseDouble(arr[6]);
                    int intLimit = Integer.parseInt(arr[7]);
                    product = new Product(arr[3],arr[1],arr[4],intQuantAvail, doublePrice);
                    product.setReviews(reviewsTemp);
                    product.setLimit(intLimit);
                    // intialize an ArrayList<Product>
                    sellersTemp.get(indexSeller).getStores().get(indexStore).setProducts(new ArrayList<>());
                    sellersTemp.get(indexSeller).getStores().get(indexStore).addProduct(product); // add Product

                }
            } else {
                // from new Seller
                indexSeller++;
                indexStore = -1;
                nameTemp = arr[0];
                seller = new Seller(null, nameTemp);
                sellersTemp.add(seller); // add new Seller to List of Sellers
                if (storeName.equals(arr[1])) {  // check if is still in the same Store
                    // Product
                    reviewsTemp = new ArrayList<>();
                    for (int j = 8; j < tempList.size(); j++) { // create an ArrayList of reviews
                        reviewsTemp.add(arr[j]);
                    }
                    int intQuantAvail = Integer.parseInt(arr[5]);
                    double doublePrice = Double.parseDouble(arr[6]);
                    int intLimit = Integer.parseInt(arr[7]);
                    product = new Product(arr[3],arr[1],arr[4],intQuantAvail, doublePrice);
                    product.setReviews(reviewsTemp);
                    product.setLimit(intLimit);
                    sellersTemp.get(indexSeller).getStores().get(indexStore).addProduct(product); // add new product
                } else {
                    // Store
                    indexStore++;
                    storeName = arr[1];
                    storeSales = Double.parseDouble(arr[2]); // from new store
                    store = new Store(null,storeName);
                    store.setSales(storeSales); // set Sales of store
                    sellersTemp.get(indexSeller).setStores(new ArrayList<>()); // initialize an ArrayList<Store>
                    sellersTemp.get(indexSeller).createStore(store); // add new Store to seller's stores
                    // Product
                    reviewsTemp = new ArrayList<>();
                    for (int j = 8; j < arr.length; j++) { // create an ArrayList of reviews
                        reviewsTemp.add(arr[j]);
                    }
                    int intQuantAvail = Integer.parseInt(arr[5]);
                    double doublePrice = Double.parseDouble(arr[6]);
                    int intLimit = Integer.parseInt(arr[7]);
                    product = new Product(arr[3],arr[1],arr[4],intQuantAvail, doublePrice);
                    product.setReviews(reviewsTemp);
                    product.setLimit(intLimit);
                    // initialize an ArrayList<Product>
                    sellersTemp.get(indexSeller).getStores().get(indexStore).setProducts(new ArrayList<>());
                    sellersTemp.get(indexSeller).getStores().get(indexStore).addProduct(product); // add new Product
                }
            }
        }
        return sellersTemp;
    }
    public static void writeDataCustomer(){
        ArrayList<Customer> customersData = customers;
        try {
            String custName;
            String productName;
            String storeName;
            String productDescription;
            String productQuant;
            String productPrice;
            String productLimit;
            PrintWriter pw = new PrintWriter(new FileOutputStream("C:\\qtri\\Purdue\\CS180\\Jetbrains\\Project4CSGOld\\MarketPlaceProject\\CustomerInfo.txt"));
            for (int i = 0; i < customersData.size(); i++) {
                Customer custTemp = customersData.get(i);
                custName = custTemp.getCustomerUserName();
                pw.write(custName + "\n");
                ArrayList<Product> prodShop = custTemp.getShoppingCar();
                for (int k = 0; k < prodShop.size(); k++) {
                    productName = prodShop.get(k).getName();
                    storeName = prodShop.get(k).getStoreName();
                    productDescription = prodShop.get(k).getDescription();
                    productQuant = String.valueOf(prodShop.get(k).getQuantAvailable());
                    productPrice = String.valueOf(prodShop.get(k).getPrice());
                    productLimit = String.valueOf(prodShop.get(k).getLimit());
                    ArrayList<String> reviews = new ArrayList<>();
                    for (int o = 0; o < prodShop.get(k).getReviews().size(); o++ ) {
                        reviews.add(prodShop.get(k).getReviews().get(o));
                    }
                    // tri,tri's store,sales,milk,taro flavour,10,5.4,5,review1,review2
                    String ans = String.format("%s/-%s/-%s/-%s/-%s/-%s/-",
                            productName, storeName, productDescription,
                            productQuant, productPrice, productLimit);
                    pw.write(ans);
                    for (int l = 0; l < reviews.size(); l++ ) {
                        pw.write(reviews.get(l) + "/-");
                    }
                    pw.write("\n");
                }
                if (prodShop.size() != 0) {
                    pw.write("change/-value\n");
                }
                ArrayList<Product> prodHistory = custTemp.getPurchaseHistory();
                for (int k = 0; k < prodHistory.size(); k++) {
                    productName = prodHistory.get(k).getName();
                    storeName = prodHistory.get(k).getStoreName();
                    productDescription = prodHistory.get(k).getDescription();
                    productQuant = String.valueOf(prodHistory.get(k).getQuantAvailable());
                    productPrice = String.valueOf(prodHistory.get(k).getPrice());
                    productLimit = String.valueOf(prodHistory.get(k).getLimit());
                    ArrayList<String> reviews = new ArrayList<>();
                    for (int o = 0; o < prodHistory.get(k).getReviews().size(); o++ ) {
                        reviews.add(prodHistory.get(k).getReviews().get(o));
                    }
                    // tri,tri's store,sales,milk,taro flavour,10,5.4,5,review1,review2
                    String ans = String.format("%s/-%s/-%s/-%s/-%s/-%s/-",
                            productName, storeName, productDescription,
                            productQuant, productPrice, productLimit);
                    pw.write(ans);
                    for (int l = 0; l < reviews.size(); l++ ) {
                        pw.write(reviews.get(l) + "/-");
                    }
                    pw.write("\n");
                }
            }
            pw.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static ArrayList<Customer> readDataCustomer() {
        ArrayList<String> tempList = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("C:\\qtri\\Purdue\\CS180\\Jetbrains\\Project4CSGOld\\MarketPlaceProject\\CustomerInfo.txt"));
            String line = br.readLine();
            while (line != null){
                tempList.add(line);
                line = br.readLine();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int index = 0;
        String custName = "";
        int indexCust = 0;
        Customer cust ;
        ArrayList<Customer> customerTemp = new ArrayList<>();
        while (index < tempList.size()) {
            // milk,tri's store,taro flavor,10,5.5,7,review1,review2
            String[] arr = tempList.get(index).split("/-");
            if (arr.length == 1) {
                if (!custName.equals(tempList.get(index)) && !custName.equals("")) {
                    indexCust++;
                }
                custName = tempList.get(index);
                cust = new Customer(new ArrayList<>(), custName);
                cust.setPurchaseHistory(new ArrayList<>());
                customerTemp.add(cust);
                index++;
            } else {
                ArrayList<Product> listShop = new ArrayList<>();
                while (arr.length != 1 && arr.length != 2 && index < tempList.size() ) {
                    Product prodShop;
                    ArrayList<String> reviewsTemp = new ArrayList<>();
                    for (int j = 6; j < arr.length; j++) { // create an ArrayList of reviews
                        reviewsTemp.add(arr[j]);
                    }
                    int intQuantAvail = Integer.parseInt(arr[3]);
                    double doublePrice = Double.parseDouble(arr[4]);
                    int intLimit = Integer.parseInt(arr[5]);
                    prodShop = new Product(arr[0], arr[1], arr[2], intQuantAvail, doublePrice);
                    prodShop.setReviews(reviewsTemp);
                    prodShop.setLimit(intLimit);
                    listShop.add(prodShop);
                    // continue on the next line
                    index++;
                    try {
                        arr = tempList.get(index).split("/-");
                    } catch (IndexOutOfBoundsException e) {

                    }
                }

                if (arr.length != 1 && arr.length == 2) {
                    index++;
                    try {
                        arr = tempList.get(index).split("/-");
                    } catch (IndexOutOfBoundsException e) {
                    }
                }

                ArrayList<Product> listHist = new ArrayList<>();
                while (arr.length != 1 && index < tempList.size()) {
                    Product prodHistory;
                    ArrayList<String> reviewsTemp = new ArrayList<>();
                    for (int j = 6; j < arr.length; j++) { // create an ArrayList of reviews
                        reviewsTemp.add(arr[j]);
                    }
                    int intQuantAvail = Integer.parseInt(arr[3]);
                    double doublePrice = Double.parseDouble(arr[4]);
                    int intLimit = Integer.parseInt(arr[5]);
                    prodHistory = new Product(arr[0], arr[1], arr[2], intQuantAvail, doublePrice);
                    prodHistory.setReviews(reviewsTemp);
                    prodHistory.setLimit(intLimit);
                    listHist.add(prodHistory);
                    index++;
                    arr = tempList.get(index).split("/-");
                }
                customerTemp.get(indexCust).setShoppingCar(listShop);
                customerTemp.get(indexCust).setPurchaseHistory(listHist);
                customerTemp.get(indexCust).setCustomerUserName(custName);
            }
        }
        return customerTemp;
    }



    /*
    public static ArrayList<Seller> read1DataSeller() {
        ArrayList<Seller> result = null;
        try (FileInputStream fis = new FileInputStream("SellerInfo.txt");
             ObjectInputStream ois = new ObjectInputStream(fis);) {
            result = new ArrayList<>();
            for (; ; ) {
                result.add((Seller) ois.readObject());
            }
        } catch (EOFException eof) {
        } catch (FileNotFoundException e) {
            System.out.println("filenotfound");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
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
    */
}
