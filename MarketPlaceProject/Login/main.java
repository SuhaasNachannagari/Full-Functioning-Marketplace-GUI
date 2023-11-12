import java.util.Scanner;

public class main {
    private static ArrayList<Seller> sellers;
    
    public static ArrayList<Seller> getSellers() {
        return sellers;
    }
    private static ArrayList<Customer> customers;
    
    public static ArrayList<Customer> getCustomers() {
        return customers;
    }
    public static void setCustomers(ArrayList<Customer> customers) {
        this.customers = customers;
    }
    
    public static void main(String[] args) {
        // this block of code checkes if this is the first time the program is run.
        // If first time, call a class and generate a dataset, else it will call the readDataSeller method to read the existing dataset.
        if (readDataSeller().isEmpty()) {
            InitialDataset sellerData = null;
            sellers = sellerData.createSeller();
        } else {
            sellers = readDataSeller();
        }
        
        Scanner scanner = new Scanner(System.in);
        System.out.println("Filler");//Fill in the welcome message.
        boolean correctInput = true;
        // Tri: I bring this out so that I can use this variable later;
        int custOrSell;
        //
        String username = null;
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
                    int input = scanner.nextInt(); scanner.nextLine();
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
       boolean checkIndexUser ;
       boolean checkIndexDoAgain;
       int checkDoAgain; 
        
       if ( custOrSell == 1) {
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
                            delete.setSeller(username);
                        case 2:
                            Edit edit = null;
                            edit.setSeller(username);
                        case 3:
                            Create create = null;
                            create.setSeller(username);

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

            } while ( checkDoAgain == 1);
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

            } while ( checkDoAgain == 1);

            if (checkDoAgain == 2) {
                System.out.println("Have a good day");
            }

        }
        writeDataSeller();    
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
        } catch (EOFException eof) {  // check if for loop reaches End Of File, and catch the error
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    
}
