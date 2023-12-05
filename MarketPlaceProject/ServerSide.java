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
                BufferedReader bfr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter pw = new PrintWriter(socket.getOutputStream());
                // 1
                Thread thread = new WorkFlow(socket, pw, bfr);
                thread.start();
                Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                    public void run() {
                        System.out.println("Data saved");
                        MarketplaceServer.writeDataCustomer();;
                        MarketplaceServer.writeDataSeller();
                    }
                }, "Shutdown-thread"));
            } catch (Exception e) {
                socket.close();
                e.printStackTrace();
            }
        }

    }
}

class WorkFlow extends Thread {
    Socket socket ;
    PrintWriter pw;
    BufferedReader bfr;
    private String username;
    public WorkFlow(Socket socket, PrintWriter pw, BufferedReader bfr) {
        this.socket = socket;
        this.pw = pw;
        this.bfr = bfr;
    }
    public void run() {
        try {
            String checkUser = bfr.readLine();
            String username = bfr.readLine();

            if (checkUser.equals("1")) {
                MarketplaceServer.setSeller(username);
                //1s
                String option = bfr.readLine();
                switch (option) {
                    case "1 - Delete":
                        String storeNames = MarketplaceServer.showStore();
                        if (storeNames.equals("No Stores")) {
                            //1d - ShowStore
                            pw.write("No Stores\n");   pw.flush();
                        } else {
                            //1del - showStores
                            pw.write(storeNames + "\n");   pw.flush();
                            // 2del - storeChoice
                            String storeChoice = bfr.readLine();
                            String productNames = MarketplaceServer.showProducts(storeChoice);
                            // 3del - showNames
                            pw.write(productNames + "\n");   pw.flush();
                            // 4del - productChoice
                            String productChoice = bfr.readLine();
                            String deleteResult = MarketplaceServer.deleteDeleteProduct(storeChoice, productChoice);
                            // 5del - show delete result
                            pw.write(deleteResult + "\n");   pw.flush();
                        }
                        break;
                    case "2 - Edit":
                        String storeNames2 = MarketplaceServer.showStore();
                        if (storeNames2.equals("No Stores")) {
                            //1edit - showStore
                            pw.write("No Stores\n");    pw.flush();
                        } else {
                            //1edit - showStores
                            pw.write(storeNames2 + "\n");  pw.flush();
                            //2edit - storeChoice
                            String storeChoice2 = bfr.readLine();
                            //3edit - "no product" message
                            String noProductMessage = MarketplaceServer.checkNoProductMessage(storeChoice2);
                            pw.write(noProductMessage + "\n");  pw.flush();
                            //4edit - show Products
                            String productNames = MarketplaceServer.showProducts(storeChoice2);
                            pw.write(productNames + "\n");  pw.flush();
                            //5edit - productChoice
                            String productChoice2 = bfr.readLine();
                            //6edit - choice
                            String choice = bfr.readLine();
                            String valueChange = bfr.readLine();
                            String editResult = MarketplaceServer.editProduct(storeChoice2, productChoice2,choice,
                                    valueChange);
                            //7edit- show Edit result
                            pw.write(editResult + "\n");    pw.flush();
                        }
                    case "3 - Create":
                        String storeNames3 = MarketplaceServer.showAllStore();
                        //1cre - show store
                        pw.write(storeNames3 + "\n");   pw.flush();
                        //2cre - storeChoice
                        String storeChoice3 = bfr.readLine();
                        if (storeChoice3.equals("1. N/A") ||
                              "Create new store".equals(storeChoice3.substring(storeChoice3.length() - 16))) {
                            //3cre - Name to create new store
                            String storeName = bfr.readLine();
                        } else {
                            // create new product
                        }
                        break;
                }
            }
            if (checkUser.equals("2")) {
                MarketplaceServer.setCustomer(username);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
