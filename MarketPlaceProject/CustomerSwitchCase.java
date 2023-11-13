System.out.println("What do you want to do?");
System.out.println("1 - Sort, 2 - View, 3 - Search, 4 - Shopping Carts, 5 - Purchased Items, 6 - Dashboard ");
int option = scanner.nextInt();
scanner.nextLine();
switch (option) {
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
                        priceNum = scanner.nextInt();
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
    case 6:
        // add code for customer dashboard
        break;
    default:
        System.out.println("Please enter the correct number!");
        checkIndexUser = false;
}
