import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

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

class WorkFlow extends Thread {
    Socket socket;
    private String username;

    public WorkFlow(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            BufferedReader bfr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter pw = new PrintWriter(socket.getOutputStream());
            outerloop:
            while (true) {
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
                            case "5 - Import/Export":
                                //1inEx - choice In or Ex
                                String choiceInEx = bfr.readLine();
                                if (choiceInEx.equals("1. Import")) {
                                    //2inEx - Import: filename
                                    String fileName = bfr.readLine();
                                    String importResult = MarketplaceServer.loadFromFileProduct(fileName);
                                    //3inEx - Import: result
                                    pw.write(importResult + "\n");
                                    pw.flush();
                                } else {
                                    String storeNameChoice = MarketplaceServer.showStore(username);
                                    //2inEx - Export: showStore
                                    pw.write(storeNameChoice + "\n");
                                    pw.flush();
                                    //3inEx - Export: store name
                                    String storeName = bfr.readLine();
                                    //4inEx - Export: file name
                                    String fileName = bfr.readLine();
                                    String result = MarketplaceServer.saveToFileProduct(storeName, username, fileName);
                                    //4inEx - Export: result
                                    System.out.println("4inEx");
                                    pw.write(result + "\n");
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
                    if (checkUser.equals("0")) {
                        //1st received from customer client
                        String option = bfr.readLine();
                        switch (option) {
                            case "Sort":
                                //1st sent to customer client, uselss
                                pw.write("" + "\n");
                                pw.flush();
                                //2nd reception, sort option
                                String[] sortOption = bfr.readLine().split(":");
                                int sortBy = 0;
                                if (sortOption[0].equals("Price")) {
                                    sortBy = 1;
                                    ArrayList<Product> priceProducts = MarketplaceServer.sortProducts(sortBy, sortOption[1]);
                                    String priceListing = "";
                                    int i = 1;
                                    for (Product product : priceProducts) {
                                        String productToAdd = String.format("%d. Store: %s, Name: %s, Price: %.2f/-",
                                                i,
                                                product.getStoreName(),
                                                product.getName(),
                                                product.getPrice());
                                        priceListing += productToAdd;
                                        i++;
                                    }
                                    //2nd send to customer, listing of products
                                    if (priceProducts != null) {
                                        if (priceListing.equals("")) {
                                            pw.write("There are no products" + "\n");
                                            pw.flush();
                                        } else {
                                            pw.write(priceListing + "\n");
                                            pw.flush();
                                        }
                                    }
                                    //3rd receiving, number of product they want to look at
                                    int num = Integer.parseInt(bfr.readLine());
                                    Product product = priceProducts.get(num - 1);
                                    ArrayList<String> reviews = product.getReviews();

                                    String reviewsToAdd = "Reviews:...";

                                    int j = 1;
                                    for (String review : reviews) {
                                        if (!review.equals("N/A")) {
                                            reviewsToAdd += String.format(j + ") \"" + review + "\"...");
                                            j++;
                                        }
                                    }
                                    String productDetails = String.format("Name: %s..." + "Store Name: %s..." + "Price: %.2f..." +
                                                    "Quantity Available: %d..." + "Description: %s..." + "%s...",
                                            product.getName(),
                                            product.getStoreName(),
                                            product.getPrice(),
                                            product.getQuantAvailable(),
                                            product.getDescription(),
                                            reviewsToAdd);
                                    //3rd send to client, the product details
                                    pw.write(productDetails + "\n");
                                    pw.flush();

                                    //4th receiving from client, useless
                                    String uselessTwo = bfr.readLine();

                                    int limit = product.getLimit();
                                    int quantAvailable = product.getQuantAvailable();

                                    //4th sending to the client
                                    pw.write("" + limit + "/" + quantAvailable + "\n");
                                    pw.flush();

                                    //5th receiving action, and review/quantity
                                    String[] actionAndInput = bfr.readLine().split(",");

                                    if (actionAndInput[0].equals("Purchase")) {
                                        String purchaseOutput = MarketplaceServer.pricePurchaseItem(priceProducts, username,
                                                Integer.parseInt(actionAndInput[1]), num);
                                        //5th sending action, basically just item purchased
                                        pw.write(purchaseOutput + "\n");
                                        pw.flush();
                                    } else if (actionAndInput[0].equals("Cart")) {
                                        String cartOutput = MarketplaceServer.priceAddToShoppingCart(priceProducts, username,
                                                Integer.parseInt(actionAndInput[1]), num);
                                        //5th sending action, basically just item added
                                        pw.write(cartOutput + "\n");
                                        pw.flush();
                                    } else if (actionAndInput[0].equals("Review")) {
                                        String reviewOutput = MarketplaceServer.addReviewPrice(priceProducts, actionAndInput[1],
                                                num);
                                        //5th sending action, basically just review added
                                        pw.write(reviewOutput + "\n");
                                        pw.flush();
                                    }
                                } else {
                                    sortBy = 2;
                                    ArrayList<Product> quantityProducts = MarketplaceServer.sortProducts(sortBy, sortOption[1]);
                                    String quantityListing = "";
                                    int i = 1;
                                    if (quantityListing != null) {
                                        for (Product product : quantityProducts) {
                                            String productToAdd = String.format("%d. Store: %s, Name: %s, Quantity: %d/-",
                                                    i,
                                                    product.getStoreName(),
                                                    product.getName(),
                                                    product.getQuantAvailable());
                                            quantityListing += productToAdd;
                                            i++;
                                        }
                                    }
                                    //2nd send to customer, listing of products
                                    if (quantityListing.equals("")) {
                                        pw.write("There are no products" + "\n");
                                        pw.flush();
                                    } else {
                                        pw.write(quantityListing + "\n");
                                        pw.flush();
                                    }
                                    //3rd receiving
                                    int num = Integer.parseInt(bfr.readLine());
                                    Product product = quantityProducts.get(num - 1);
                                    ArrayList<String> reviews = product.getReviews();

                                    String reviewsToAdd = "Reviews:...";

                                    int j = 1;
                                    for (String review : reviews) {
                                        if (!review.equals("N/A")) {
                                            reviewsToAdd += String.format(j + ") \"" + review + "\"...");
                                            j++;
                                        }
                                    }
                                    String productDetails = String.format("Name: %s..." + "Store Name: %s..." + "Price: %.2f..." +
                                                    "Quantity Available: %d..." + "Description: %s..." + "%s...",
                                            product.getName(),
                                            product.getStoreName(),
                                            product.getPrice(),
                                            product.getQuantAvailable(),
                                            product.getDescription(),
                                            reviewsToAdd);
                                    //3rd send to client, the product details
                                    pw.write(productDetails + "\n");
                                    pw.flush();

                                    //4th receiving from client, useless
                                    String uselessTwo = bfr.readLine();

                                    int limit = product.getLimit();
                                    int quantAvailable = product.getQuantAvailable();

                                    //4th sending to the client
                                    pw.write("" + limit + "/" + quantAvailable + "\n");
                                    pw.flush();

                                    //5th receiving action, and review/quantity
                                    String[] actionAndInput = bfr.readLine().split(",");

                                    if (actionAndInput[0].equals("Purchase")) {
                                        String purchaseOutput = MarketplaceServer.quantityPurchaseItems(quantityProducts, username,
                                                Integer.parseInt(actionAndInput[1]), num);
                                        //5th sending action, basically just item purchased
                                        pw.write(purchaseOutput + "\n");
                                        pw.flush();
                                    } else if (actionAndInput[0].equals("Cart")) {
                                        String cartOutput = MarketplaceServer.quantityAddToShoppingCart(quantityProducts, username,
                                                Integer.parseInt(actionAndInput[1]), num);
                                        //5th sending action, basically just item added
                                        pw.write(cartOutput + "\n");
                                        pw.flush();
                                    } else if (actionAndInput[0].equals("Review")) {
                                        String reviewOutput = MarketplaceServer.addReviewQuantity(quantityProducts, actionAndInput[1],
                                                num);
                                        //5th sending action, basically just review added
                                        pw.write(reviewOutput + "\n");
                                        pw.flush();
                                    }
                                }
                                break;
                            case "View":
                                ArrayList<Product> products = MarketplaceServer.viewProducts();
                                String listing = "";
                                int i = 1;
                                if (!(products == null)) {
                                    for (Product product : products) {
                                        String productToAdd = String.format("%d. Store: %s, Name: %s, Price: %.2f, Quantity: %d/-",
                                                i,
                                                product.getStoreName(),
                                                product.getName(),
                                                product.getPrice(),
                                                product.getQuantAvailable());
                                        listing += productToAdd;
                                        i++;
                                    }
                                }
                                //1st send to customer, listing of products
                                if (listing.equals("")) {
                                    pw.write("There are no products" + "\n");
                                    pw.flush();
                                } else {
                                    pw.write(listing + "\n");
                                    pw.flush();
                                }
                                //2nd receiving, number of product they want to look at
                                int num = Integer.parseInt(bfr.readLine());
                                Product product = products.get(num - 1);
                                ArrayList<String> reviews = product.getReviews();

                                String reviewsToAdd = "Reviews:...";

                                int j = 1;
                                for (String review : reviews) {
                                    if (!review.equals("N/A")) {
                                        reviewsToAdd += String.format(j + ") \"" + review + "\"...");
                                        j++;
                                    }
                                }
                                String productDetails = String.format("Name: %s..." + "Store Name: %s..." + "Price: %.2f..." +
                                                "Quantity Available: %d..." + "Description: %s..." + "%s...",
                                        product.getName(),
                                        product.getStoreName(),
                                        product.getPrice(),
                                        product.getQuantAvailable(),
                                        product.getDescription(),
                                        reviewsToAdd);
                                //2nd send to client, the product details
                                pw.write(productDetails + "\n");
                                pw.flush();

                                //3rd receiving from client, useless
                                String uselessTwo = bfr.readLine();

                                int limit = product.getLimit();
                                int quantAvailable = product.getQuantAvailable();

                                //3rd sending to the client
                                pw.write("" + limit + "/" + quantAvailable + "\n");
                                pw.flush();

                                //4th receiving action, and review/quantity
                                String[] actionAndInput = bfr.readLine().split(",");

                                if (actionAndInput[0].equals("Purchase")) {
                                    String purchaseOutput = MarketplaceServer.purchaseItem(products, username,
                                            Integer.parseInt(actionAndInput[1]), num);
                                    //4th sending action, basically just item purchased
                                    pw.write(purchaseOutput + "\n");
                                    pw.flush();
                                } else if (actionAndInput[0].equals("Cart")) {
                                    String cartOutput = MarketplaceServer.addToShoppingCart(products, username,
                                            Integer.parseInt(actionAndInput[1]), num);
                                    //4th sending action, basically just item added
                                    pw.write(cartOutput + "\n");
                                    pw.flush();
                                } else if (actionAndInput[0].equals("Review")) {
                                    String reviewOutput = MarketplaceServer.addReview(products, actionAndInput[1],
                                            num);
                                    //4th sending action, basically just review added
                                    pw.write(reviewOutput + "\n");
                                    pw.flush();
                                }
                                break;
                            case "Search":
                                //1st sent to customer client, uselss
                                pw.write("" + "\n");
                                pw.flush();
                                //2nd reception from the client class
                                String search = bfr.readLine();
                                ArrayList<Product> matchedProducts = MarketplaceServer.searchProducts(search);
                                String results = "";
                                int p = 1;
                                if (!(matchedProducts == null)) {
                                    for (Product matchedProduct : matchedProducts) {
                                        String productToAdd = String.format("%d. Store: %s, Name: %s, Price: %.2f, Quantity: %d/-",
                                                p,
                                                matchedProduct.getStoreName(),
                                                matchedProduct.getName(),
                                                matchedProduct.getPrice(),
                                                matchedProduct.getQuantAvailable());
                                        results += productToAdd;
                                        p++;
                                    }
                                }
                                //2nd send to customer, listing of products
                                if (results.equals("")) {
                                    pw.write("There are no products that match your search" + "\n");
                                    pw.flush();
                                } else {
                                    pw.write(results + "\n");
                                    pw.flush();
                                    //3rd receiving, number of product they want to look at
                                    int productNum = Integer.parseInt(bfr.readLine());
                                    Product matchedProduct = matchedProducts.get(productNum - 1);
                                    ArrayList<String> searchReviews = matchedProduct.getReviews();

                                    String reviewsAdd = "Reviews:...";

                                    int h = 1;
                                    for (String review : searchReviews) {
                                        if (!review.equals("N/A")) {
                                            reviewsAdd += String.format(h + ") \"" + review + "\"...");
                                            h++;
                                        }
                                    }
                                    String detailsProduct = String.format("Name: %s..." + "Store Name: %s..." + "Price: %.2f..." +
                                                    "Quantity Available: %d..." + "Description: %s..." + "%s...",
                                            matchedProduct.getName(),
                                            matchedProduct.getStoreName(),
                                            matchedProduct.getPrice(),
                                            matchedProduct.getQuantAvailable(),
                                            matchedProduct.getDescription(),
                                            reviewsAdd);
                                    //3rd send to client, the product details
                                    pw.write(detailsProduct + "\n");
                                    pw.flush();

                                    //4th receiving from client, useless
                                    String uselessThree = bfr.readLine();

                                    int searchLimit = matchedProduct.getLimit();
                                    int seachQuant = matchedProduct.getQuantAvailable();

                                    //4th sending to the client
                                    pw.write("" + searchLimit + "/" + seachQuant + "\n");
                                    pw.flush();

                                    //5th receiving action, and review/quantity
                                    String[] input = bfr.readLine().split(",");

                                    if (input[0].equals("Purchase")) {
                                        String purchaseOutput = MarketplaceServer.purchaseItem(matchedProducts, username,
                                                Integer.parseInt(input[1]), productNum);
                                        //5th sending action, basically just item purchased
                                        pw.write(purchaseOutput + "\n");
                                        pw.flush();
                                    } else if (input[0].equals("Cart")) {
                                        String cartOutput = MarketplaceServer.addToShoppingCart(matchedProducts, username,
                                                Integer.parseInt(input[1]), productNum);
                                        //5th sending action, basically just item added
                                        pw.write(cartOutput + "\n");
                                        pw.flush();
                                    } else if (input[0].equals("Review")) {
                                        String reviewOutput = MarketplaceServer.addReview(matchedProducts, input[1],
                                                productNum);
                                        //5th sending action, basically just review added
                                        pw.write(reviewOutput + "\n");
                                        pw.flush();
                                    }
                                }

                                break;
                            case "Shopping Cart":
                                ArrayList<Product> shoppingCart = MarketplaceServer.getCustomerShoppingCart(username);
                                String shoppingCartProducts = "";
                                if (shoppingCart == null) {
                                    shoppingCartProducts = "Your shopping cart is empty";
                                    //1st sendign to the server, shopping cart contents
                                    pw.write(shoppingCartProducts + "\n");
                                    pw.flush();
                                } else {
                                    int r = 1;
                                    for (Product cartProduct : shoppingCart) {
                                        String productToAdd = String.format("%d. Store: %s, Name: %s, Price: %.2f, Quantity: %d/-",
                                                r,
                                                cartProduct.getStoreName(),
                                                cartProduct.getName(),
                                                cartProduct.getPrice(),
                                                cartProduct.getQuantAvailable());
                                        shoppingCartProducts += productToAdd;
                                        r++;
                                    }
                                    //1st sendign to the server, shopping cart contents
                                    pw.write(shoppingCartProducts + "\n");
                                    pw.flush();
                                    //2nd receiving from server, action with cart
                                    String[] cartOptions = bfr.readLine().split(",");
                                    if (cartOptions[0].equals("Remove From Cart")) {
                                        String removed = MarketplaceServer.removeItem(username,
                                                shoppingCart, Integer.parseInt(cartOptions[1]));
                                        // 2nd sending to client, result of trying to remove itm
                                        pw.write(removed + "\n");
                                        pw.flush();
                                    } else {
                                        String purchaseAll = MarketplaceServer.purchaseItemALL(username);
                                        // 2nd sending to client, result of trying to remove itm
                                        pw.write(purchaseAll + "\n");
                                        pw.flush();
                                    }
                                }
                                break;
                            case "Purchase History":
                                ArrayList<Product> purchaseHistory = MarketplaceServer.customerViewHistory(username);
                                String history = "";
                                if (purchaseHistory == null) {
                                    history = "You haven't bought anything";
                                    //1st sendign to the server, shopping cart contents
                                    pw.write(history + "\n");
                                    pw.flush();
                                } else {
                                    int w = 1;
                                    for (Product boughtProduct : purchaseHistory) {
                                        String productToAdd = String.format("%d. Store: %s, Name: %s, Price: %.2f, Quantity: %d/-",
                                                w,
                                                boughtProduct.getStoreName(),
                                                boughtProduct.getName(),
                                                boughtProduct.getPrice(),
                                                boughtProduct.getQuantAvailable());
                                        history += productToAdd;
                                        w++;
                                    }
                                    //1st sendign to the server, shopping cart contents
                                    pw.write(history + "\n");
                                    pw.flush();
                                    //2nd receiving, number and search
                                    String[] exportQuestion = bfr.readLine().split(",");
                                    if (exportQuestion[0].equals("0")) {
                                        String filename = exportQuestion[1] + ".txt";
                                        String fileWritten = MarketplaceServer.exportPurchaseHistory(
                                                purchaseHistory, filename, username);
                                        //2nd sending
                                        pw.write(filename + "\n");
                                        pw.flush();
                                    }
                                }
                                break;
                            case "Dashboard":
                                //1st useless send, need another input from user
                                pw.write("\n");
                                pw.flush();
                                //2nd reception from client, what to view and to sort it or not
                                String[] inputDashboard = bfr.readLine().split(",");
                                if (inputDashboard[0].equals("Store Sales")) {
                                    if (inputDashboard[1].equals("0")) {
                                        String dashboard = MarketplaceServer.dashboardCheckSell(true, inputDashboard[2]);
                                        //2nd send to client, wresult
                                        pw.write(dashboard + "\n");
                                        pw.flush();
                                    } else {
                                        String dashboard = MarketplaceServer.dashboardCheckSell(false, inputDashboard[2]);
                                        //2nd send to client, wresult
                                        pw.write(dashboard + "\n");
                                        pw.flush();
                                    }
                                } else {
                                    if (inputDashboard[1].equals("0")) {
                                        String dashboard = MarketplaceServer.dashBoardCheckCust(true, username, inputDashboard[2]);
                                        //2nd send to client, wresult
                                        pw.write(dashboard + "\n");
                                        pw.flush();
                                    } else {
                                        String dashboard = MarketplaceServer.dashBoardCheckCust(false, username, inputDashboard[2]);
                                        //2nd send to client, wresult
                                        pw.write(dashboard + "\n");
                                        pw.flush();

                                    }
                                }
                                break;
                        }
                    }
                    // final read-write
                    String checkDoAgain = bfr.readLine();
                    if (checkDoAgain.equals("continueLoop")) {
                    } else {
                        break;
                    }
                } // of main loop
            } // of outerloop
        } catch(IOException e){
            throw new RuntimeException(e);
        }
    }
}
