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
    public void run() {
        Socket socket;
        BufferedReader bfr;
        PrintWriter pw;
        try {
            InetAddress ia = InetAddress.getByName("localhost");
            socket = new Socket(ia, 4242);
            bfr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            pw = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int checkUser = 0; //Logs.runLogs();
        username = null; //Logs.getUsername();
        //1
        pw.write(checkUser + "\n");
        pw.flush();
        //2
        pw.write(username + "\n");
        pw.flush();

        try {
            if (checkUser == 1) {
                String[] showOptions = {"1 - Delete", "2 - Edit", "3 - Create", "4 - View", "5 - Import/Export", "6 - Dashboard"};
                String option = (String) (JOptionPane.showInputDialog(null, "What do you want to do?",
                        "Choice", JOptionPane.QUESTION_MESSAGE, null, showOptions, showOptions[0]));
                if (option == null) {
                    //throw Exception;
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
                                    "Error",JOptionPane.ERROR_MESSAGE);
                        } else {
                            String[] stores = message.split("/-");
                            String storeChoice = (String) (JOptionPane.showInputDialog(null,
                                    "Enter the index of the store you want to edit: ", "Choice",
                                    JOptionPane.QUESTION_MESSAGE,null,stores,stores[0]));
                            //2del - storeChoice
                            pw.write(storeChoice + "\n");    pw.flush();

                            JOptionPane.showMessageDialog(null,
                                    "N/A means the store doesn't contain any products\n"
                                    + "if you remove N/A, you will remove the store itself", "Explain Message",
                                    JOptionPane.INFORMATION_MESSAGE);
                            // 3del - showProducts
                            String showProducts = bfr.readLine();
                            String[] products = showProducts.split("/-");
                            String productChoice = (String) (JOptionPane.showInputDialog(null,
                                    "Enter the index of the product you want to delete: ", "Choice",
                                    JOptionPane.QUESTION_MESSAGE,null,products,products[0]));
                            // 4del - productChoice
                            pw.write(productChoice + "\n");   pw.flush();
                            // 5del - show delete result
                            String resultMessage = bfr.readLine();
                            JOptionPane.showMessageDialog(null, resultMessage,"Message",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                        break;
                    case "2 - Edit":
                        //1edit show Store
                        String message2 = bfr.readLine();
                        if (message2.equals("No Stores")) {
                            JOptionPane.showMessageDialog(null, "This seller doesn't have any stores!",
                                    "Error",JOptionPane.ERROR_MESSAGE);
                        } else {
                            String[] stores = message2.split("/-");
                            String storeChoice = (String) (JOptionPane.showInputDialog(null,
                                    "Enter the index of the store you want to edit: ", "Choice",
                                    JOptionPane.QUESTION_MESSAGE,null,stores,stores[0]));
                            //2edit - storeChoice
                            pw.write(storeChoice + "\n");    pw.flush();
                            //3edit - "no product" message
                            String noProductMessage = bfr.readLine();
                            if (noProductMessage.equals("No Products")) {
                                JOptionPane.showMessageDialog(null,
                                        "This store doesn't have any products! You should create a new product",
                                        "Explain Message", JOptionPane.INFORMATION_MESSAGE);
                            }
                            //4edit - show Products
                            String showProducts2 = bfr.readLine();
                            String[] products2 = showProducts2.split("/-");
                            String productChoice = (String) (JOptionPane.showInputDialog(null,
                                    "Enter the index of the product you want to delete: ", "Choice",
                                    JOptionPane.QUESTION_MESSAGE,null,products2,products2[0]));
                            // 5edit - productChoice
                            pw.write(productChoice + "\n");   pw.flush();
                            //6edit - productChoiceOptions
                            String[] choices = {"1. Name", "2. Store Name", "3. Description", "4. Quantity Available",
                                    "5. Price", "6. Limit"};
                            String productChoiceOption = (String) (JOptionPane.showInputDialog(null,
                                    "What do you want to change about this product?", "Choice",
                                    JOptionPane.QUESTION_MESSAGE,null,choices,choices[0]));
                            pw.write(productChoiceOption + "\n");   pw.flush();
                            //7edit - **storeName if choose to change** / enter value changed
                            if (productChoiceOption.equals("2. Store Name")) {
                                String storeNameChange = (String) JOptionPane.showInputDialog(null,
                                        "Which store you want to move to?", "Choice",
                                        JOptionPane.QUESTION_MESSAGE, null, stores, stores[0]);
                                pw.write(storeNameChange);
                            } else {
                                boolean wrongFormat;
                                String valueChange;
                                do {
                                    wrongFormat = false;
                                    valueChange = JOptionPane.showInputDialog(null,
                                            "Enter the value you want to change", "Choice",
                                            JOptionPane.QUESTION_MESSAGE);
                                    try {
                                        if (productChoiceOption.equals("4. Quantity Available")) {
                                            int checkInt = Integer.parseInt(valueChange);
                                        } else if (productChoiceOption.equals("5. Price")) {
                                            double checkDouble = Double.parseDouble(valueChange);
                                        } else if (productChoiceOption.equals("6. Limit")) {
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
                                pw.write(valueChange);
                            }
                            //8edit - show Edit result
                            String resultMessage2 = bfr.readLine();
                            JOptionPane.showMessageDialog(null, resultMessage2,"Message",
                                    JOptionPane.INFORMATION_MESSAGE);
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
                        //2cre - storeChoice
                        pw.write(storeChoice + "\n");     pw.flush();
                        if (storeChoice.equals("1. N/A") ||
                                "Create new store".equals(storeChoice.substring(storeChoice.length() - 16))) {
                            String storeName = JOptionPane.showInputDialog(null,
                                    "Enter the name of the new store:","Question",
                                    JOptionPane.QUESTION_MESSAGE);
                            //3cre - Name to create new store
                            pw.write(storeName);    pw.flush();
                            //4cre - Create Store Result
                            String createResult = bfr.readLine();
                            JOptionPane.showMessageDialog(null, createResult, "Message",
                                    JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            while (true) {
                                String productName = JOptionPane.showInputDialog(null, "Name:",
                                        "Enter the information of the product:", JOptionPane.QUESTION_MESSAGE);
                                //5cre - Create new product's name
                                pw.write(productName + "\n");
                                pw.flush();
                                //6cre - checkName exists
                                String checkName = bfr.readLine();
                                if (checkName.equals("true")) {
                                    String productDes = JOptionPane.showInputDialog(null,
                                            "Description:", "Enter the information of the product:",
                                            JOptionPane.QUESTION_MESSAGE);
                                    String productQuant;    String productPrice;    String productLimit;
                                    while (true) {
                                        try {
                                            productQuant = JOptionPane.showInputDialog(null,
                                                    "Quantity Available:", "Enter the information of the product:",
                                                    JOptionPane.QUESTION_MESSAGE);
                                            int checkQuant = Integer.parseInt(productQuant);
                                            productPrice = JOptionPane.showInputDialog(null,
                                                    "Price:", "Enter the information of the product:",
                                                    JOptionPane.QUESTION_MESSAGE);
                                            double checkPrice = Double.parseDouble(productPrice);
                                            productLimit = JOptionPane.showInputDialog(null,
                                                    "Limit:", "Enter the information of the product:",
                                                    JOptionPane.QUESTION_MESSAGE);
                                            int checkLimit = Integer.parseInt(productLimit);
                                            break;
                                        } catch ( Exception e) {
                                            JOptionPane.showMessageDialog(null,
                                                    "Please enter the correct format!", "Error",
                                                    JOptionPane.ERROR_MESSAGE);
                                        }
                                    }
                                    pw.write(productDes + "\n");    pw.flush(); //7cre - productDescription
                                    pw.write(productQuant + "\n");    pw.flush(); //8cre - productQuantity Avail
                                    pw.write(productPrice + "\n");    pw.flush(); //8cre - productPrice
                                    pw.write(productLimit + "\n");    pw.flush(); //10cre - productLimit
                                    break;
                                } else {
                                    JOptionPane.showMessageDialog(null,
                                            "This product has already existed, please add another product",
                                            "Error", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                            //11cre - createProductResult
                            String createProductResult = bfr.readLine();
                            JOptionPane.showMessageDialog(null,createProductResult,"Message",
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
        } catch (IOException e) {
        }

        if (checkDoAgain == 2) {
            System.out.println("Have a good day");
        }
    }
}
