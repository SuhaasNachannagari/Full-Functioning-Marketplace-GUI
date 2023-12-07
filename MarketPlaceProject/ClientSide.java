import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
public class ClientSide extends Thread {
    private static int ind1 = 0;
    private static int ind2 = 0;
    public boolean checkIndexOption;
    public boolean checkIndexDoAgain;
    public int checkDoAgain; //1 for Yes, 2 for No
    public String username = null;
    private static Object gateKeeper = new Object();

    public static void main(String[] args) {
        ClientSide cs = new ClientSide();
        cs.start();
    }
    public void run() {
        Socket socket;
        BufferedReader bfr;
        PrintWriter pw;
        try {
            System.out.println("check1");
            InetAddress ia = InetAddress.getByName("localhost");
            socket = new Socket(ia, 4242);
            bfr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            pw = new PrintWriter(socket.getOutputStream());
            System.out.println("check2");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        int checkUser = 1; //Logs.runLogs();
        username = "tri"; //Logs.getUsername();
        //1
        pw.println(checkUser);
        pw.flush();
        //2
        pw.println(username);
        pw.flush();
        while (true) {
            try {
                if (checkUser == 1) {
                    String[] showOptions = {"1 - Delete", "2 - Edit", "3 - Create", "4 - View", "5 - Import/Export", "6 - Dashboard"};
                    String option = (String) (JOptionPane.showInputDialog(null, "What do you want to do?",
                            "Choice", JOptionPane.QUESTION_MESSAGE, null, showOptions, showOptions[0]));
                    if (option == null) {
                        throw new Exception();
                    }
                    //1s
                    pw.write(option + "\n");
                    pw.flush();

                    switch (option) {
                        case "1 - Delete":
                            //1del - showStore
                            String message = bfr.readLine();
                            if (message.equals("No Stores")) {
                                JOptionPane.showMessageDialog(null, "This seller doesn't have any stores!",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                            } else {
                                String[] stores = message.split("/-");
                                String storeChoice = (String) (JOptionPane.showInputDialog(null,
                                        "Enter the index of the store you want to edit: ", "Choice",
                                        JOptionPane.QUESTION_MESSAGE, null, stores, stores[0]));
                                if (storeChoice == null) {
                                    throw new Exception();
                                }
                                //2del - storeChoice
                                pw.write(storeChoice + "\n");
                                pw.flush();

                                JOptionPane.showMessageDialog(null,
                                        "N/A means the store doesn't contain any products\n"
                                                + "if you remove N/A, you will remove the store itself", "Explain Message",
                                        JOptionPane.INFORMATION_MESSAGE);
                                // 3del - showProducts
                                String showProducts = bfr.readLine();
                                String[] products = showProducts.split("/-");
                                String productChoice = (String) (JOptionPane.showInputDialog(null,
                                        "Enter the index of the product you want to delete: ", "Choice",
                                        JOptionPane.QUESTION_MESSAGE, null, products, products[0]));
                                if (productChoice == null) {
                                    throw new Exception();
                                }
                                // 4del - productChoice
                                pw.write(productChoice + "\n");
                                pw.flush();
                                // 5del - show delete result
                                String resultMessage = bfr.readLine();
                                JOptionPane.showMessageDialog(null, resultMessage, "Message",
                                        JOptionPane.INFORMATION_MESSAGE);
                            }
                            break;
                        case "2 - Edit":
                            //1edit show Store
                            String message2 = bfr.readLine();
                            if (message2.equals("No Stores")) {
                                JOptionPane.showMessageDialog(null, "This seller doesn't have any stores!",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                            } else {
                                String[] stores = message2.split("/-");
                                String storeChoice = (String) (JOptionPane.showInputDialog(null,
                                        "Enter the index of the store you want to edit: ", "Choice",
                                        JOptionPane.QUESTION_MESSAGE, null, stores, stores[0]));
                                if (storeChoice == null) {
                                    throw new Exception();
                                }
                                //2edit - storeChoice
                                pw.write(storeChoice + "\n");
                                pw.flush();
                                //3edit - "no product" message
                                String noProductMessage = bfr.readLine();
                                if (noProductMessage.equals("No Products")) {
                                    JOptionPane.showMessageDialog(null,
                                            "This store doesn't have any products! You should create a new product",
                                            "Explain Message", JOptionPane.INFORMATION_MESSAGE);
                                } else {
                                    //4edit - show Products
                                    String showProducts2 = bfr.readLine();
                                    String[] products2 = showProducts2.split("/-");
                                    String productChoice = (String) (JOptionPane.showInputDialog(null,
                                            "Enter the index of the product you want to edit: ", "Choice",
                                            JOptionPane.QUESTION_MESSAGE, null, products2, products2[0]));
                                    if (productChoice == null) {
                                        throw new Exception();
                                    }
                                    // 5edit - productChoice
                                    pw.write(productChoice + "\n");
                                    pw.flush();
                                    //6edit - productChoiceOptions
                                    String[] choices = {"1.Name", "2.Store Name", "3.Description", "4.Quantity Available",
                                            "5.Price", "6.Limit"};
                                    String productChoiceOption = (String) (JOptionPane.showInputDialog(null,
                                            "What do you want to change about this product?", "Choice",
                                            JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]));
                                    if (productChoiceOption == null) {
                                        throw new Exception();
                                    }
                                    pw.write(productChoiceOption + "\n");
                                    pw.flush();
                                    //7edit - **storeName if choose to change** / enter value changed
                                    if (productChoiceOption.equals("2.Store Name")) {
                                        String storeNameChange = (String) JOptionPane.showInputDialog(null,
                                                "Which store you want to move to?", "Choice",
                                                JOptionPane.QUESTION_MESSAGE, null, stores, stores[0]);
                                        if (storeNameChange == null) {
                                            throw new Exception();
                                        }
                                        pw.write(storeNameChange + "\n");
                                        pw.flush();
                                    } else {
                                        boolean wrongFormat;
                                        String valueChange;
                                        do {
                                            wrongFormat = false;
                                            valueChange = JOptionPane.showInputDialog(null,
                                                    "Enter the value you want to change", "Choice",
                                                    JOptionPane.QUESTION_MESSAGE);
                                            if (valueChange == null) {
                                                throw new Exception();
                                            }
                                            try {
                                                if (productChoiceOption.equals("4.Quantity Available")) {
                                                    int checkInt = Integer.parseInt(valueChange);
                                                } else if (productChoiceOption.equals("5.Price")) {
                                                    double checkDouble = Double.parseDouble(valueChange);
                                                } else if (productChoiceOption.equals("6.Limit")) {
                                                    int checkInt = Integer.parseInt(valueChange);
                                                }
                                            } catch (Exception e) {
                                                wrongFormat = true;
                                                JOptionPane.showMessageDialog(null,
                                                        "Please enter the correct format!", "Error",
                                                        JOptionPane.ERROR_MESSAGE);
                                            }
                                        } while (wrongFormat);
                                        //7edit - **enter value changed** / storeName if choose to change
                                        pw.write(valueChange + "\n");
                                        pw.flush();
                                    }
                                    //8edit - show Edit result
                                    String resultMessage2 = bfr.readLine();
                                    JOptionPane.showMessageDialog(null, resultMessage2, "Message",
                                            JOptionPane.INFORMATION_MESSAGE);
                                }
                            }
                            break;
                        case "3 - Create":
                            JOptionPane.showMessageDialog(null,
                                    "N/A means the seller doesn't have any stores\n"
                                            + "choose N/A then to create the first store", "Explain Message",
                                    JOptionPane.INFORMATION_MESSAGE);
                            //1cre - showStore
                            String message3 = bfr.readLine();
                            String[] stores = message3.split("/-");
                            String storeChoice = (String) (JOptionPane.showInputDialog(null,
                                    "Choose the index you want to edit: ", "Choice",
                                    JOptionPane.QUESTION_MESSAGE, null, stores, stores[0]));
                            if (storeChoice == null) {
                                throw new Exception();
                            }
                            //2cre - storeChoice
                            pw.write(storeChoice + "\n");
                            pw.flush();
                            if (storeChoice.equals("1.N/A") || stores[stores.length - 1].equals(storeChoice)) {
                                String storeName = JOptionPane.showInputDialog(null,
                                        "Enter the name of the new store:", "Question",
                                        JOptionPane.QUESTION_MESSAGE);
                                if (storeName == null) {
                                    throw new Exception();
                                }
                                //3cre - Name to create new store
                                pw.write(storeName + "\n");
                                pw.flush();
                                //4cre - Create Store Result
                                String createResult = bfr.readLine();
                                JOptionPane.showMessageDialog(null, createResult, "Message",
                                        JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                while (true) {
                                    String productName = JOptionPane.showInputDialog(null, "Name:",
                                            "Enter the information of the product:", JOptionPane.QUESTION_MESSAGE);
                                    if (productName == null) {
                                        throw new Exception();
                                    }
                                    //5cre - Create new product's name
                                    pw.write(productName + "\n");
                                    pw.flush();
                                    //6cre - checkName exists
                                    String checkName = bfr.readLine();
                                    if (checkName.equals("true")) {
                                        String productDes = JOptionPane.showInputDialog(null,
                                                "Description:", "Enter the information of the product:",
                                                JOptionPane.QUESTION_MESSAGE);
                                        if (productDes == null) {
                                            throw new Exception();
                                        }
                                        if (productDes == null) {
                                            throw new Exception();
                                        }
                                        String productQuant;
                                        String productPrice;
                                        String productLimit;
                                        while (true) {
                                            try {
                                                productQuant = JOptionPane.showInputDialog(null,
                                                        "Quantity Available:", "Enter the information of the product:",
                                                        JOptionPane.QUESTION_MESSAGE);
                                                if (productQuant == null) {
                                                    throw new Exception();
                                                }
                                                int checkQuant = Integer.parseInt(productQuant);
                                                productPrice = JOptionPane.showInputDialog(null,
                                                        "Price:", "Enter the information of the product:",
                                                        JOptionPane.QUESTION_MESSAGE);
                                                if (productPrice == null) {
                                                    throw new Exception();
                                                }
                                                double checkPrice = Double.parseDouble(productPrice);
                                                productLimit = JOptionPane.showInputDialog(null,
                                                        "Limit:", "Enter the information of the product:",
                                                        JOptionPane.QUESTION_MESSAGE);
                                                if (productLimit == null) {
                                                    throw new Exception();
                                                }
                                                int checkLimit = Integer.parseInt(productLimit);
                                                break;
                                            } catch (Exception e) {
                                                JOptionPane.showMessageDialog(null,
                                                        "Please enter the correct format!", "Error",
                                                        JOptionPane.ERROR_MESSAGE);
                                            }
                                        }
                                        pw.write(productDes + "\n");
                                        pw.flush(); //7cre - productDescription
                                        pw.write(productQuant + "\n");
                                        pw.flush(); //8cre - productQuantity Avail
                                        pw.write(productPrice + "\n");
                                        pw.flush(); //8cre - productPrice
                                        pw.write(productLimit + "\n");
                                        pw.flush(); //10cre - productLimit
                                        break;
                                    } else {
                                        JOptionPane.showMessageDialog(null,
                                                "This product has already existed, please add another product",
                                                "Error", JOptionPane.ERROR_MESSAGE);
                                    }
                                }
                                //11cre - createProductResult
                                String createProductResult = bfr.readLine();
                                JOptionPane.showMessageDialog(null, createProductResult, "Message",
                                        JOptionPane.INFORMATION_MESSAGE);
                            }
                            break;
                        case "4 - View":
                            String[] message4 = {"1.View Sales by Stores", "2.View Products in Carts"};
                            String choice4 = (String) JOptionPane.showInputDialog(null,
                                    "What do you want to view?", "Choice", JOptionPane.QUESTION_MESSAGE,
                                    null, message4, message4[0]);
                            if (choice4 == null) {
                                throw new Exception();
                            }
                            //1vie - Sales or Products
                            pw.write(choice4 + "\n");
                            pw.flush();
                            if (choice4.equals(message4[0])) {
                                //2vie - Sales by Stores
                                String storesResult = bfr.readLine();
                                String viewStoresResult = storesResult.replace("/-", "\n");
                                if (viewStoresResult.equals("No stores")) {
                                    JOptionPane.showMessageDialog(null,
                                            "This user has no stores, create new ones please!", "Error",
                                            JOptionPane.ERROR_MESSAGE);
                                } else {
                                    JOptionPane.showMessageDialog(null, viewStoresResult, "Message",
                                            JOptionPane.INFORMATION_MESSAGE);
                                }
                            } else {
                                //2vie - Products in Shopping Cart
                                String shoppingCart = bfr.readLine();
                                String viewShoppingCart = shoppingCart.replaceAll("/-", "\n");
                                if (viewShoppingCart.equals("No stores")) {
                                    JOptionPane.showMessageDialog(null,
                                            "This user has no stores, create new ones please!", "Error",
                                            JOptionPane.ERROR_MESSAGE);
                                } else {
                                    JOptionPane.showMessageDialog(null, viewShoppingCart, "Message",
                                            JOptionPane.INFORMATION_MESSAGE);
                                }
                            }
                            break;
                        case "6 - Dashboard":
                            String[] custOrProds = {"1. List of Customers", "2. List of Products"};
                            String[] minOrMax = {"1. Max to min", "2. Min to max"};
                            String choiceItem = (String) JOptionPane.showInputDialog(null,
                                    "What do you want to do?", "Choice", JOptionPane.QUESTION_MESSAGE, null,
                                    custOrProds, custOrProds[0]);
                            if (choiceItem == null) {
                                throw new Exception();
                            }
                            //1dash - choice Cust or Prod
                            pw.write(choiceItem + "\n");
                            pw.flush();
                            //2dash - show Cust or Prod
                            String data = bfr.readLine();
                            String showData;
                            if (data.equals("")) {
                                showData = "There is nothing to display";
                                JOptionPane.showMessageDialog(null, showData, "Message",
                                        JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                showData = data.replaceAll("/-", "\n");
                                JOptionPane.showMessageDialog(null, showData, "Message",
                                        JOptionPane.INFORMATION_MESSAGE);
                                String choiceOrder = (String) JOptionPane.showInputDialog(null,
                                        "How do you want to sort?", "Choice", JOptionPane.QUESTION_MESSAGE,
                                        null, minOrMax, minOrMax[0]);
                                if (choiceOrder == null) {
                                    throw new Exception();
                                }
                                //3dash - choice Order
                                pw.write(choiceOrder + "\n");
                                pw.flush();
                                //4dash - sortedData
                                String data2 = bfr.readLine();
                                String sortedData = data2.replaceAll("/-", "\n");
                                JOptionPane.showMessageDialog(null, sortedData, "Message",
                                        JOptionPane.INFORMATION_MESSAGE);
                            }
                            break;
                        default:
                            System.out.println("Please enter the correct number!");
                            checkIndexOption = false;
                            break;
                    }
                }


                if (checkUser == 0) {

                }

            } catch (Exception e) {
                break;
            }
            if (JOptionPane.showConfirmDialog(null, "Do you want to use the program again?",
                    "Question",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                pw.write("continueLoop" + "\n");    pw.flush();
            } else {
                pw.write("breakLoop" + "\n");   pw.flush();
                break;
            }
        }
        JOptionPane.showMessageDialog(null,"Have a good day","Message",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
