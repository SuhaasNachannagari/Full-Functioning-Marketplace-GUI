# Project4CSGOld
To run and Compile our project, you must run both the server, and the client side of the program from any device, which serves as the marketplace for the project. After the project compiles, it shows a GUI that prompts the user, asking them whether they are a seller or a customer. The control flow for the program is shown below.
![image](https://github.com/SuhaasNachannagari/Project4CSGOld/assets/143854535/2f61bb32-a6c5-44c7-ad02-e91005411eac)
After the customer/seller signs into their account they are prompted with all the different actions they can perform.

Note: To run the program using any port other than localhost, you must change the port you are connecting the server and client to. On the ClientSide class, on lines 31 and 32, you have to change the port for the socket to whatever you wish.  
Note: To exit the GUI hit the cancel button or close the GUI pop up on the screen.

Raghav - Submitted report on Brightspace  
Tri - Submitted the code on Vocareum

Classes:  
  Logs - Allows for easy login and writing to .txt files to save the login information of users. The logic and methods developed in this are used in ClientSide for implementing the GUIs for logging in.
 
  User - This class helps implement the login features of the code by allowing easy storage of username and passwords. 
    
  Seller -  This class represents a single seller.
  
  Product - A product class that contains all the information for a product that will be used multiple times throughout the marketplace.
  
  Store -  This class represents a single store.
  
  buyShoppingCart -  This class allows the customer to buy their existing carts.
  
  CSVReader -  The class reads a CSV file from the user.
  
  Dashboard(Seller) - A Class that extends main in order to display and enact the code that runs whenever the seller chooses to view a dashboard.  
    
  Customer -  This class represents a customer.  
  
  Create -  This class will be used to create products store.  
    
  Delete - This class will be used to delete products from a store.  
    
  Edit -  This class will be used to edit products in a store. 
    
  CustomerDashboard - A Class that extends main in order to display and enact the code that runs whenever the customer chooses to view a dashboard. It allows the customer to view all the stores and prints out the number of sales for each of them. It also displays the products the customer has bought and the stores they have bought it from. 
    
  Search - A Class that extends main in order to display and enact the code that runs whenever the customer chooses to "search" in the main method. Includes methods that search the market using the customer's input, display the products, and allow the user to write a review, buy the product, or add it to their shopping cart.
    
  Sort - A Class that extends main in order to display and enact the code that runs whenever the customer chooses to "search" in the main method. Includes methods that sort the products by quantity or price, display the products, and allow the user to write a review, buy the product, or add it to their shopping cart
    
  View - A class that extends main in order to display and enact the code that runs whenever the customer chooses to "sort" in the main method. Includes methods that display the products and allow the user to write a review, buy the product, or add it to their shopping cart

  MarketplaceServer -  A markerplace seller that contains ALL the static methods that make significant changes to the data accessed in the server class. Holds the static sellers and customers objects that are the backbone of the marketplace, and many view and edit methods regarding them are included.

  DashboardIO -  This class will be used to display dashboard.

  ServerSide - Establishes multiple connections with clients and recieves and sends information to and from the client and calls methods depending on the data supplied.

  ClientSide -  This class allows the client to independently connect to the server and displays the GUI functionality of this project.
