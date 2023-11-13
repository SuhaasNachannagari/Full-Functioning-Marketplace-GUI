# Project4CSGOld
To run and Compile our project, you must run the main method of the program, which serves as the marketplace for the project. After the project compiles, it prompts you to input whether the user is a seller or a customer. The control flow for the program is shown below.
![image](https://github.com/SuhaasNachannagari/Project4CSGOld/assets/143854535/2f61bb32-a6c5-44c7-ad02-e91005411eac)
After the customer/seller signs into their account they are prompted with all the different actions they can perform.

Raghav - Submitted report on Brightspace  
Tri - Submitted the code on Vocareum

Classes:  
  Login -  
  
    CustomerLogin - Login for customers:  
      Once the user says they are a customer,   
      ask them whether they have an existing account or not.   
      If: The account exists, check if the username exists and return an error if it doesn't.  
      Ask for the password and if its correct go into the rest of the code.   
      If: The account doesn't exist, ask them for a username and return an error if the username already exists.  
      Ask them for a password.  
      
    SellerLogin -  Login for sellers:   
      Once the user says they are a seller,  
      ask them whether they have an existing account or not.  
      If: The account exists, check if the username exists and return an error if it doesn't.   
      Ask for the password and if its correct go into the rest of the code.   
      If: The account doesn't exist, ask them for a username and return an error if the username already exists.   
      ask them for a password.  
 
    User - This class helps implement the login features of the code by allowing easy storage of username and passwords. 
    
  Seller -  
  
  Product - 
  
  Store -  
  
  buyShoppingCart -  
  
  CSVReader -  
  
  Dashboard(Seller) -  
  
  CustomerSwitchCase -  
  
  Customer -  
  
  SellerMarketplace -  
  
    Create -  
    
    Dashboard -  
    
    Delete -   
    
    Edit -  
    
  CustomerMarketplace -   
  
    CustomerDashboard - A Class that extends main in order to display and enact the code that runs whenever the customer chooses to view a dashboard. It allows the customer       to view all the stores and prints out the number of sales for each of them. It also displays the products the customer has bought and the stores they have bought it         from. 
    
    Search -  A Class that extends main in order to display and enact the code that runs whenever the customer chooses to "search" in the main method. Includes methods that search the market using the customer's input, display the products, and allow the user to write a review, buy the product, or add it to their shopping cart.
    
    Sort -   A Class that extends main in order to display and enact the code that runs whenever the customer chooses to "search" in the main method. Includes methods that sort the products by quantity or price, display the products, and allow the user to write a review, buy the product, or add it to their shopping cart
    
    View -  A class that extends main in order to display and enact the code that runs whenever the customer chooses to "sort" in the main method. Includes methods that display the products and allow the user to write a review, buy the product, or add it to their shopping cart

