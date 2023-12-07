import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerSide {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(4242);
        MarketplaceServer.readDataCustomer();
        MarketplaceServer.readDataSeller();
        while (true) {
            Socket socket = null;
            try {
                socket = serverSocket.accept();
                // 1
                Thread workFlow = new WorkFlow(socket);
                workFlow.start();
                workFlow.join();
                Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                    public void run() {
                        System.out.println("Data saved");
                        MarketplaceServer.writeDataCustomer();;
                        MarketplaceServer.writeDataSeller();
                    }
                }, "Shutdown-thread"));
            } catch (Exception e) {
                e.printStackTrace();
                socket.close();
            }
        }

    }
}

class WorkFlow extends Thread  {
    Socket socket ;
    private String username;
    public WorkFlow(Socket socket) {
        this.socket = socket;
    }
    public void run() {
        try {
            BufferedReader bfr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter pw = new PrintWriter(socket.getOutputStream());
            //1
            String checkUser = bfr.readLine();
            //2
            String username = bfr.readLine();

            while (true) {
                if (checkUser.equals("1")) {
                    //1s
                    String option = bfr.readLine();
                    switch (option) {
                        case "1 - Delete":
                            String storeNames = MarketplaceServer.showStore(username);
                            if (storeNames.equals("No Stores")) {
                                //1d - ShowStore
                                pw.write("No Stores\n");
                                pw.flush();
                            } else {
                                //1del - showStores
                                pw.write(storeNames + "\n");
                                pw.flush();
                                // 2del - storeChoice
                                String storeChoice = bfr.readLine();
                                String productNames = MarketplaceServer.showProducts(storeChoice, username);
                                // 3del - showNames
                                pw.write(productNames + "\n");
                                pw.flush();
                                // 4del - productChoice
                                String productChoice = bfr.readLine();
                                String deleteResult = MarketplaceServer.deleteDeleteProduct(storeChoice, productChoice, username);
                                // 5del - show delete result
                                pw.write(deleteResult + "\n");
                                pw.flush();
                            }
                            break;
                        case "2 - Edit":
                            String storeNames2 = MarketplaceServer.showStore(username);
                            if (storeNames2.equals("No Stores")) {
                                //1edit - showStore
                                pw.write("No Stores\n");
                                pw.flush();
                            } else {
                                //1edit - showStores
                                pw.write(storeNames2 + "\n");
                                pw.flush();
                                //2edit - storeChoice
                                String storeChoice2 = bfr.readLine();
                                //3edit - "no product" message
                                String noProductMessage = MarketplaceServer.checkNoProductMessage(storeChoice2, username);
                                pw.write(noProductMessage + "\n");
                                pw.flush();
                                if (noProductMessage.equals("No Products")) {

                                } else {
                                    //4edit - show Products
                                    String productNames = MarketplaceServer.showProducts(storeChoice2, username);
                                    pw.write(productNames + "\n");
                                    pw.flush();
                                    //5edit - productChoice
                                    String productChoice2 = bfr.readLine();
                                    //6edit - choice
                                    String choice = bfr.readLine();
                                    //7edit - valueChange
                                    String valueChange = bfr.readLine();
                                    String editResult = MarketplaceServer.editProduct(storeChoice2, productChoice2, choice,
                                            valueChange, username);
                                    //8edit- show Edit result
                                    pw.write(editResult + "\n");
                                    pw.flush();
                                }
                            }
                            break;
                        case "3 - Create":
                            String storeNames3 = MarketplaceServer.showAllStore(username);
                            //1cre - show store
                            pw.write(storeNames3 + "\n");
                            pw.flush();
                            //2cre - storeChoice
                            String storeChoice3 = bfr.readLine();
                            String[] storesCheck = storeNames3.split("/-"); //check Create store
                            if (storeChoice3.equals("1. N/A")) {
                                //3cre - Name to create new store
                                String storeName = bfr.readLine();
                                String createResult = MarketplaceServer.createStoreNA(storeName, username);
                                //4cre - Create Store Result
                                pw.write(createResult + "\n");
                                pw.flush();
                            } else if (storesCheck[storesCheck.length - 1].equals(storeChoice3)) {
                                //3cre - Name to create new store
                                String storeName = bfr.readLine();
                                String createResult = MarketplaceServer.createStore(storeName, username);
                                //4cre - Create Store Result
                                pw.write(createResult + "\n");
                                pw.flush();
                            } else {
                                String createProductResult = "";
                                while (true) {
                                    // 5cre - create new product name
                                    String productName = bfr.readLine();
                                    boolean checkName = MarketplaceServer.checkProductName(storeChoice3, productName, username);
                                    if (checkName) {
                                        //6cre - checkName exists
                                        pw.write("true\n");
                                        pw.flush();
                                        String prodDesc = bfr.readLine(); //7cre - productDecription
                                        String prodQuant = bfr.readLine(); //8cre - productQuant
                                        String prodPrice = bfr.readLine(); //9cre - prodPrice
                                        String prodLimit = bfr.readLine(); //10cre- proLimit
                                        createProductResult = MarketplaceServer.createProduct(storeChoice3, productName,
                                                prodDesc, prodQuant, prodPrice, prodLimit, username);
                                        break;
                                    } else {
                                        //6cre - checkName exists
                                        pw.write("false\n");
                                        pw.flush();
                                    }
                                }
                                //11cre - createProductResult
                                pw.write(createProductResult + "\n");
                                pw.flush();
                            }
                            break;
                        case "4 - View":
                            //1vie - Sales or Products
                            String viewChoice = bfr.readLine();
                            if (viewChoice.equals("1.View Sales by Stores")) {
                                String viewStoresResult = MarketplaceServer.viewStores(username);
                                //2vie - view store result
                                pw.write(viewStoresResult + "\n");
                                pw.flush();
                            } else {
                                String viewCartResult = MarketplaceServer.viewShoppingCart(username);
                                //2vie - view Shopping cart
                                pw.write(viewCartResult + "\n");
                                pw.flush();
                            }
                            break;
                        case "6 - Dashboard":
                            //1dash - choice Cust or Prods
                            String choiceItem = bfr.readLine();
                            DashboardIO dIO = new DashboardIO();
                            if (choiceItem.equals("1. List of Customers")) {
                                String listCustomers = dIO.getListCustomers(username);
                                //2dash - show listCust
                                pw.write(listCustomers + "\n");
                                pw.flush();
                                if (!listCustomers.equals("")) {
                                    //3dash - choice Order
                                    String choiceOrder = bfr.readLine();
                                    //4dash - sortedData
                                    pw.write(dIO.sortCustomers(choiceOrder) + "\n");
                                    pw.flush();
                                }
                            } else if (choiceItem.equals("2. List of Products")) {
                                String listProducts = dIO.getListProducts(username);
                                //2dash - show listProd
                                pw.write(listProducts + "\n");
                                pw.flush();
                                //3dash - choice Order
                                String choiceOrder = bfr.readLine();
                                //4dash - sortedData
                                pw.write(dIO.sortProduct(choiceOrder) + "\n");
                                pw.flush();
                            }
                            break;
                    }
                }
                if (checkUser.equals("2")) {

                }
                String checkDoAgain = bfr.readLine();
                if (checkDoAgain.equals("continueLoop")) {
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}