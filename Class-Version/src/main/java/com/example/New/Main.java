package com.example.New;

import java.io.*;
import java.util.Scanner;

public class Main implements Serializable{
   static String[][] queues = {{"X", "X", "X"}, {"X", "X", "X"}, {" ", "X", "X"}, {" ", " ", "X"}, {" ", " ", "X"}}; // This queues 2D array was created to store the information of the queue.
   static int burgerCount = 50; //Initializing the burger count to 50
   public static FoodQueue[] allTheQueues = new FoodQueue[3]; //Creating a foodQueue array to hold the food queue objects
   public static WaitingQueue waitingCustomers = new WaitingQueue(15); //Creating an object inside waiting queue class


   public static void main(String[] args) {
      Scanner input = new Scanner(System.in);
      //Creating the food queue objects
      FoodQueue queue1 = new FoodQueue(2, 1, 0); //Creating queue1 object inside FoodQueue class
      FoodQueue queue2 = new FoodQueue(3, 2, 0); //Creating queue2 object inside FoodQueue class
      FoodQueue queue3 = new FoodQueue(5, 3, 0); //Creating queue3 object inside FoodQueue class

      //Appending the created FoodQueue objects into an array
      allTheQueues[0] = queue1;
      allTheQueues[1] = queue2;
      allTheQueues[2] = queue3;

      //To check if data exists from a previous run
      File progData = new File("FoodieCenter_Data_File.txt");
      if (progData.exists()) {
         System.out.println("\nWARNING! Previous data has been found. If you want to load that data enter '107' or 'LPD' in the menu.");
      }

      while (true) {
         if (waitingCustomers.getWaitingCustomers()[0] != null){ //Checking if there are any customers in the waiting queue.
            fromWaitingQueueToQueue(allTheQueues, waitingCustomers);
         }
         if (burgerCount == 10) {
            System.out.println("\n10 Burgers remaining!!!\n"); //Displaying an alert message when the burger count reaches 10
         } else if (burgerCount<10) {
            System.out.println("\nless than 10 burgers remaining!!!\n"); //Displaying alert messages when the burger count goes below 10
         }
         //printing the menu
         System.out.println("""
                 \n--- Main Menu ---\n         
                 100 or VFQ: View all Queues.
                 101 or VEQ: View all Empty Queues.
                 102 or ACQ: Add customer to a Queue.
                 103 or RCQ: Remove a customer from a Queue. (From a specific location)
                 104 or PCQ: Remove a served customer.
                 105 or VCS: View Customers Sorted in alphabetical order.
                 106 or SPD: Store Program Data into file.
                 107 or LPD: Load Program Data from file.
                 108 or STK: View Remaining burgers Stock.
                 109 or AFS: Add burgers to Stock.
                 110 or IFQ: Print the income of each queue
                 111 or GUI: Open GUI
                 999 or EXT: Exit the Program.
                 """);
         System.out.print("Enter an option from the above menu : ");
         String userChoice = input.next(); //Getting an input from the user.

         switch (userChoice) {
            //Calling the desired method depending on the user's selection
            case "100", "VFQ" -> allQueues();
            case "101", "VEQ" -> emptyQueues();
            case "102", "ACQ" -> addCustomer(allTheQueues, waitingCustomers);
            case "103", "RCQ" -> removeCustomer(allTheQueues);
            case "104", "PCQ" -> removeServedCustomer(allTheQueues);
            case "105", "VCS" -> sortCustomers(allTheQueues);
            case "106", "SPD" -> storingProgramData(allTheQueues);
            case "107", "LPD" -> loadingProgramData(allTheQueues);
            case "108", "STK" -> remainingBurgerStock();
            case "109", "AFS" -> addBurgersToStock();
            case "110", "IFQ" -> viewQueueIncome(allTheQueues);
            case "111", "GUI" -> GUIApplication.main(new String[]{});
            case "999", "EXT" -> System.exit(0);
            default -> System.out.println("Invalid Choice!");
         }
      }
   }

   /**
    * This method is used to display the queue status.
    */
   public static void allQueues() {
      System.out.println("******************");
      System.out.println("*    " + "Cashiers" + "    *");
      System.out.println("******************\n");
      System.out.println("Q1     " + "Q2     " + "Q3");//Displaying the cashier number
      for (int i = 0; i < 5; i++) {       //Nested for loop is used to print the queues 2d array
         for (int u = 0; u < 3; u++) {
            System.out.print(queues[i][u] + "      ");
         }
         System.out.println();
      }
   }

   /**
    * This method is used to display all the empty queues.
    */
   public static void emptyQueues() {
      System.out.println("""
              \t******************
              \t*    Cashiers    *
              \t******************
              """);
      System.out.println("  Q1       " + "   Q2          " + "Q3");//Displaying the cashier number
      for (int i = 0; i < 5; i++) {       //Nested for loop is used to print the queues 2d array
         for (int u = 0; u < 3; u++) {
            if (queues[i][u] == "X") {
               System.out.print("Empty" + "       ");
            } else if (queues[i][u] == "O") {
               System.out.print("Occupied" + "    ");
            }else{
               System.out.print("            ");
            }
         }
         System.out.println();
      }
   }

   /**
    * This method is used to add a customer to the shortest queue.
    *
    * @param allTheQueues  Food Queue objects array.
    * @param waitingCustomers Waiting queue object.
    */
   public static void addCustomer(FoodQueue[] allTheQueues, WaitingQueue waitingCustomers) {
      int queueNumber = checkShortestQueue(allTheQueues);     //Calling the method to check the shortest queue

      String firstName = validateName("first name");          //Calling the validateName method to validate the user input
      String lastName = validateName("last name");            //Calling the validateName method to validate the user input
      int requiredBurgerCount = validateBurgerCount();                  //Calling the requiredBurgerCount method to validate the user input

      if (queueNumber == 4){
         System.out.println("All the queues are occupied!!\nCustomer has been added to the waiting queue.");
         addingCustomerToWaitingQueue(firstName,lastName,requiredBurgerCount,waitingCustomers);  //Creating a customer object inside the waiting queue
      }else if (queueNumber == 1){
         allTheQueues[0].addCustomers(new Customer(firstName,lastName,requiredBurgerCount));    //Creating a customer inside food queue object array
         System.out.println(firstName +" "+ lastName +" has been successfully added to queue 1.");
         queues[allTheQueues[0].getCustomers().size()- 1][queueNumber-1] = "O";                 //Updating the queue status
      }else if (queueNumber == 2) {
         allTheQueues[1].addCustomers(new Customer(firstName, lastName, requiredBurgerCount));  //Creating a customer inside food queue object array
         System.out.println(firstName +" "+ lastName +" has been successfully added to queue 2.");
         queues[allTheQueues[1].getCustomers().size() - 1][queueNumber-1] = "O";                //Updating the queue status
      }else{
         allTheQueues[2].addCustomers(new Customer(firstName, lastName, requiredBurgerCount));  //Creating a customer inside food queue object array
         System.out.println(firstName +" "+ lastName +" has been successfully added to queue 3.");
         queues[allTheQueues[2].getCustomers().size() - 1][queueNumber-1] = "O";                //Updating the queue status
      }
   }

   /**
    * This method is used to remove a customer from a specified location.
    * @param allTheQueues Food Queue objects array.
    */
   public static void removeCustomer(FoodQueue[] allTheQueues){
      boolean isCorrectSlot = true;
      while (isCorrectSlot){
         int customerSelectedQueue = validateIntInput("remove","queue"); //Calling the validateName method to validate queue number
         int customerSelectedRow = validateIntInput("remove","row");     //Calling the validateName method to validate row number

         if (customerSelectedQueue > 3 || customerSelectedQueue == 0){
            System.out.println("Invalid queue number! Select a queue number from 1,2 and 3");
         }else if (customerSelectedRow > 5 || customerSelectedRow == 0){
            System.out.println("Invalid row number!, Enter a row number below 6");
         } else if ((customerSelectedQueue <= 2 && customerSelectedRow > 3) || (customerSelectedQueue == 1 && customerSelectedRow == 3)) {
            System.out.println("No queues available at selected slot");
         } else {
            if (queues[customerSelectedRow - 1][customerSelectedQueue - 1] == "X") {
               System.out.println("Selected slot doesn't have a customer in the queue!");
               isCorrectSlot = false;           //assigning false to the boolean variable to stop the loop and return to the main menu
            } else{
               queues[customerSelectedRow - 1][customerSelectedQueue - 1] = "X";          //Updating the queue status
               if(customerSelectedQueue == 1){
                  allTheQueues[0].getCustomers().remove(customerSelectedRow - 1);   //Removing the customer object from the specified location
                  for (int row = customerSelectedRow - 1; row < 2; row++) {
                     for (int column = customerSelectedQueue - 1; column < 1; column++) {
                        queues[row][column] = queues[row + 1][column];//Moving each row forward in the queue after removing the desired row
                     }
                  }
                  queues[1][0] = "X";           //Assigning "X" to the last row of the particular column
               } else if (customerSelectedQueue == 2) {
                  allTheQueues[1].getCustomers().remove(customerSelectedRow - 1);   //Removing the customer object from the specified location
                  for (int row = customerSelectedRow - 1; row < 3; row++) {
                     for (int column = customerSelectedQueue - 1; column < 2; column++) {
                        queues[row][column] = queues[row + 1][column];       //Moving each row forward in the queue after removing the desired row
                     }
                  }
                  queues[2][1] = "X";         //Assigning "X" to the last row of the particular column
               } else {
                  allTheQueues[2].getCustomers().remove(customerSelectedRow - 1);   //Removing the customer object from the specified location
                  for (int row = customerSelectedRow - 1; row < 4; row++) {
                     for (int column = customerSelectedQueue - 1; column < 3; column++) {
                        queues[row][column] = queues[row + 1][column];     //Moving each row forward in the queue after removing the desired row
                     }
                  }
                  queues[4][2] = "X";         //Assigning "X" to the last row of the particular column
               }
               System.out.println("Customer Successfully removed from the desired location.");
               isCorrectSlot = false;         //After a particular customer is removed, boolean variable is given false  to stop the while loop
            }
         }
      }
   }

   /**
    * This method is used to create a customer object inside waiting queue object array.
    *
    * @param firstName First name of the customer.
    * @param lastName Last name of the customer.
    * @param requiredBurgerCount Number of burgers required.
    * @param waitingCustomers Waiting queue object.
    */
   public static void addingCustomerToWaitingQueue(String firstName, String lastName, int requiredBurgerCount, WaitingQueue waitingCustomers){
      waitingCustomers.enqueue(new Customer(firstName,lastName,requiredBurgerCount));     //Creating a new customer inside the waiting queue customer class
   }

   /**
    * This method is used to change the customer object from the waiting queue object array to food queue array object.
    *
    * @param allTheQueues Food Queue objects array.
    * @param waitingCustomers Waiting queue object.
    */
   public static void fromWaitingQueueToQueue(FoodQueue[] allTheQueues, WaitingQueue waitingCustomers){
      int availableQueue = checkShortestQueue(allTheQueues);  //Checking if any customer was removed from the queues

      if (availableQueue == 4){
         return;
      } else if (waitingCustomers.getFront() == -1) { // Checking the waitingQueue class array whether it has any objects to remove
         return;
      } else {
         Customer customerObject = waitingCustomers.dequeue();
         if (availableQueue == 1){
            allTheQueues[0].addCustomers(customerObject);         //Changing the customer object waiting queue object to food queue object
            queues[allTheQueues[0].getCustomers().size()- 1][availableQueue-1] = "O";     //Updating the queue status
         }else if (availableQueue == 2) {
            allTheQueues[1].addCustomers(customerObject);         //Changing the customer object waiting queue object to food queue object
            queues[allTheQueues[1].getCustomers().size() - 1][availableQueue-1] = "O";    //Updating the queue status
         }else{
            allTheQueues[2].addCustomers(customerObject);         //Changing the customer object waiting queue object to food queue object
            queues[allTheQueues[2].getCustomers().size() - 1][availableQueue-1] = "O";    //Updating the queue status
         }
         System.out.println("Waiting customer successfully moved from waiting queue to normal queue.");
      }
   }

   /**
    * This method is used to remove a served customer from a particular queue.
    *
    * @param allTheQueues Food Queue objects array.
    */
   public static void removeServedCustomer(FoodQueue[] allTheQueues){
      boolean isCorrectSlot = true;
      while (isCorrectSlot){
         int queueNoOfCustomer = validateIntInput("remove","queue"); //Validating the queue number

         if (queueNoOfCustomer > 3 || queueNoOfCustomer == 0){
            System.out.println("Invalid queue number!, Enter a queue number from 1,2 and 3");
         }else if ((queues[0][0] == "X") && (queues[0][1] == "X") && (queues[0][2] == "X")){
            System.out.println("None of the queues have customers to remove.");
            isCorrectSlot = false;
         }else{
            if (queues[0][queueNoOfCustomer - 1] == "X") {
               System.out.println("Selected queue doesn't have a customer in position one!, please enter another queue.");
            } else {
               if (queueNoOfCustomer == 1) {
                  if (burgerCount - allTheQueues[0].getCustomers().get(0).getNoOfBurgers() < 0){ //To check whether there are sufficient burgers to serve the customer
                     System.out.println("\nWARNING! Not enough burgers to server the customer. Add burgers to stock and try removing the customer.\n");
                     return;
                  }else{
                     burgerCount = burgerCount - allTheQueues[0].getCustomers().get(0).getNoOfBurgers(); //Reducing the burger count
                  }
                  //Increasing the queue income after a served customer is being removed
                  allTheQueues[0].setQueueIncome(allTheQueues[0].getQueueIncome() + (allTheQueues[0].getCustomers().get(0).getNoOfBurgers() * 650));
                  allTheQueues[0].getCustomers().remove(0);             //Removing the customer object from the specified location

                  for (int row = 0; row < 2; row++) {
                     for (int column = queueNoOfCustomer - 1; column < 1; column++) {
                        queues[row][column] = queues[row + 1][column];        //Moving each row forward in the queue after removing the 1st row
                     }
                  }
                  queues[1][0] = "X";           //Assigning "X" to the last row of the particular column
                  System.out.println("Customer Successfully removed from queue one.");
               } else if (queueNoOfCustomer == 2) {
                  if (burgerCount - allTheQueues[1].getCustomers().get(0).getNoOfBurgers() < 0){ //To check whether there are sufficient burgers to serve the customer
                     System.out.println("\nWARNING! Not enough burgers to server the customer. Add burgers to stock and try removing the customer.\n");
                     return;
                  }else{
                     burgerCount = burgerCount - allTheQueues[1].getCustomers().get(0).getNoOfBurgers(); //Reducing the burger count
                  }
                  //Increasing the queue income after a served customer is being removed
                  allTheQueues[1].setQueueIncome(allTheQueues[1].getQueueIncome() + allTheQueues[1].getCustomers().get(0).getNoOfBurgers() * 650);
                  allTheQueues[1].getCustomers().remove(0);              //Removing the customer object from the specified location

                  for (int row = 0; row < 3; row++) {
                     for (int column = queueNoOfCustomer - 1; column < 2; column++) {
                        queues[row][column] = queues[row + 1][column];        //Moving each row forward in the queue after removing the 1st row
                     }
                  }
                  queues[2][1] = "X";           //Assigning "X" to the last row of the particular column
                  System.out.println("Customer Successfully removed from queue two.");
               } else {
                  if (burgerCount - allTheQueues[2].getCustomers().get(0).getNoOfBurgers() < 0){ //To check whether there are sufficient burgers to serve the customer
                     System.out.println("\nWARNING! Not enough burgers to server the customer. Add burgers to stock and try removing the customer.\n");
                     return;
                  }else{
                     burgerCount = burgerCount - allTheQueues[2].getCustomers().get(0).getNoOfBurgers(); //Reducing the burger count
                  }
                  //Increasing the queue income after a served customer is being removed
                  allTheQueues[2].setQueueIncome(allTheQueues[2].getQueueIncome() + allTheQueues[2].getCustomers().get(0).getNoOfBurgers() * 650);
                  allTheQueues[2].getCustomers().remove(0);            //Removing the customer object from the specified location

                  for (int row = 0; row < 4; row++) {
                     for (int column = queueNoOfCustomer - 1; column < 3; column++) {
                        queues[row][column] = queues[row + 1][column];        //Moving each row forward in the queue after removing the 1st row
                     }
                  }
                  queues[4][2] = "X";           //Assigning "X" to the last row of the particular column
                  System.out.println("Customer Successfully removed from queue three.");
               }
               isCorrectSlot = false; //After a particular customer is removed, boolean variable is given false  to stop the while loop
            }
         }
      }
   }

   /**
    * This method is used to sort the customer names in alphabetical order.
    *
    * @param allTheQueues Food Queue objects array.
    */
   public static void sortCustomers(FoodQueue[] allTheQueues){
      int sortingCustomerListCount = 0; //Getting the number of customers in the shop to be sorted
      String[] sortedCustomerList;
      if(allTheQueues[0].getCustomers().size() + allTheQueues[1].getCustomers().size() + allTheQueues[2].getCustomers().size() == 0){
         System.out.println("No customers are there to sort!");
         return;
      }else{
         sortingCustomerListCount = allTheQueues[0].getCustomers().size() + allTheQueues[1].getCustomers().size() + allTheQueues[2].getCustomers().size();
         sortedCustomerList = new String[sortingCustomerListCount];
         int count = 0;
         for (int i = 0; i < allTheQueues.length; i++) {
            for (int j = 0; j < allTheQueues[i].getCustomers().size(); j++) {
               sortedCustomerList[count] = allTheQueues[i].getCustomers().get(j).getFullName();
               count++;
            }
         }

         System.out.print("Names Before sorting : ");//Displaying the names before Sorting
         for (int i = 0; i < sortedCustomerList.length; i++) {
            System.out.print(sortedCustomerList[i] + ", ");
         }

         for (int i = 0; i < sortedCustomerList.length; i++) {
            for (int j = i + 1; j < sortedCustomerList.length; j++) {
               int letterIndex = 0;                               //Creating a variable to count the length of the string
               String temp;                                       //Creating a temporary variable to store an element
               for (letterIndex = 0; (letterIndex < sortedCustomerList[i].length()) && (letterIndex < sortedCustomerList[j].length()); letterIndex++) {
                  if (sortedCustomerList[i].charAt(letterIndex) > sortedCustomerList[j].charAt(letterIndex)) {
                     //checking whether the 1st character of the 1st string is greater than the 1st character of the 2nd string
                     temp = sortedCustomerList[i];                  //Temporarily storing the element if it's greater than the element after it
                     sortedCustomerList[i] = sortedCustomerList[j];//appending the element to the ith index from the jth index
                     sortedCustomerList[j] = temp;                  //Then storing the element which was stored in the temp variable to jth index
                     break;
                  } else if (sortedCustomerList[i].charAt(letterIndex) < sortedCustomerList[j].charAt(letterIndex)) {
                        /*
                        checking whether the 1st character of the 1st string is smaller than the 1st character of the 2nd string.
                        if yes, the loop is stopped and the elements aren't changed
                         */
                     break;
                  }
               }
               if (sortedCustomerList[i].length() > letterIndex && sortedCustomerList[j].length() <= letterIndex) {
                  //Changing places of the elements if the length of the first string is greater than the second string
                  temp = sortedCustomerList[i];
                  sortedCustomerList[i] = sortedCustomerList[j];
                  sortedCustomerList[j] = temp;
               }
            }
         }
         System.out.print("\nName after sorting : ");
         for (int i = 0; i < sortedCustomerList.length; i++) {
            System.out.print(sortedCustomerList[i] + ", ");
         }
      }
   }

   /**
    * This method is used to store the program data into a file.
    *
    * @param allTheQueues Food Queue objects array.
    */
   public static void storingProgramData(FoodQueue[] allTheQueues){
      try {
         FileOutputStream fileOut = new FileOutputStream("FoodieCenter_Data_File.txt");
         ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);

         for (int i = 0; i < allTheQueues.length; i++) {
            objectOut.writeObject(allTheQueues[i]);      //Storing the foodQueue objects
         }
         objectOut.writeObject(burgerCount);             //Storing the burger count
         objectOut.writeObject(waitingCustomers);        //Storing the waiting queue customer objects
         objectOut.close();
         System.out.println("\nSuccessfully stored the Data into the file.\n");

      } catch (IOException e) {
         System.out.println("An error occurred!: " + e);
      }
   }

   /**
    * This method is used to load the program data stored in a file.
    *
    * @param allTheQueues Food Queue objects array.
    */
   public static void loadingProgramData(FoodQueue[] allTheQueues){
      try {
         FileInputStream fileIn = new FileInputStream("FoodieCenter_Data_File.txt");
         ObjectInputStream objectIn = new ObjectInputStream(fileIn);

         for (int i = 0; i < queues.length; i++) {    //Updating queue status before loading data
            for (int j = 0; j < queues[i].length; j++) {
               if (queues[i][j] == "O"){
                  queues[i][j] = "X";
               }
            }
         }

         for (int i = 0; i < allTheQueues.length; i++) {
            allTheQueues[i] = (FoodQueue) objectIn.readObject();     //Loading foodQueue Objects
         }
         burgerCount = (int) objectIn.readObject();                  //loading burger count
         waitingCustomers = (WaitingQueue) objectIn.readObject();    //loading waiting queue objects
         objectIn.close();
         System.out.println("File successfully loaded!");
         updatingLoadedData(allTheQueues);

      } catch (IOException e) {
         System.out.println("File does not exist!\n");
      } catch (ClassNotFoundException e1) {
         System.out.println("Class not found!\n");
      }
   }

   /**
    * This method is used to update the queue status after the data has been loaded.
    *
    * @param allTheQueues Food Queue objects array.
    */
   public static void updatingLoadedData(FoodQueue[] allTheQueues){     //Changing queues state after loading the stored data
      for (int i = 0; i < allTheQueues[0].getCustomers().size(); i++) {
         queues[i][0] = "O";
      }
      for (int i = 0; i < allTheQueues[1].getCustomers().size(); i++) {
         queues[i][1] = "O";
      }
      for (int i = 0; i < allTheQueues[2].getCustomers().size(); i++) {
         queues[i][2] = "O";
      }
   }

   /**
    * This method is used to display the remaining burger stock.
    */
   public static void remainingBurgerStock(){
      System.out.println("The remaining burgers stock is "+burgerCount+" .");//Displaying the remaining burger stock
   }

   /**
    * This method is used to add burgers to the stock.
    */
   public static void addBurgersToStock(){
      Scanner input = new Scanner(System.in);
      while(true){
         try{
            System.out.print("Enter the amount of burgers you want add : ");
            int addingAmount = Integer.parseInt(input.next());
            if(burgerCount == 50){
               System.out.println("Burger stock is full!");    //if the burger count is already 50, a message will be displayed
               break;
            } else if (burgerCount + addingAmount > 50){
               //Checking whether the added burger count and the stock burger is more than 50
               System.out.println("Burger stock cannot be more than 50. The remaining burger count is " + burgerCount);
            }else if (addingAmount < 0){
               System.out.println("Warning! adding burger count cannot be less than 0");
            } else {
               burgerCount = burgerCount + addingAmount;//Adding the user added burger count and remaining burger count
               System.out.println("Successfully added "+addingAmount+" of burgers to the stock.");
               break;
            }
         }catch(NumberFormatException e2){
            System.out.println("\nError! Input must be an integer.\n");
         }
      }
   }

   /**
    * This method is used to display the income of each queue.
    *
    * @param allTheQueues Food Queue objects array.
    */
   public static void viewQueueIncome(FoodQueue[] allTheQueues){
      System.out.println("Queue one income is "+ allTheQueues[0].getQueueIncome()+" Rs.");
      System.out.println("Queue two income is "+ allTheQueues[1].getQueueIncome()+" Rs.");
      System.out.println("Queue three income is "+ allTheQueues[2].getQueueIncome()+" Rs.");
   }

   /**
    * This method is used to find the shortest queue.
    *
    * @param allTheQueues Food Queue objects array.
    * @return Shortest queue number.
    */
   public static int checkShortestQueue(FoodQueue[] allTheQueues) {
      //getting the length of each arraylist of FoodQueue object which contains the arraylist.
      int q1 = allTheQueues[0].getCustomers().size();
      int q2 = allTheQueues[1].getCustomers().size();
      int q3 = allTheQueues[2].getCustomers().size();
      if (q1 == 2 && q2 == 3 && q3 == 5) {
         return 4;                           //Will return int 4 if all the queues are full
      } else if (q1 == 2 && q2 == 3) {
         return 3;
      } else {
         if (q2 < q1) {
            return 2;
         } else if (q3 < q2) {
            return 3;
         }else if ( q2 == 2 && q1 == 2) {
            return 2;
         }else{
            return 1;
         }
      }
   }

   /**
    * This method is used to validate any integer related user input.
    *
    * @param displayMessageAddOrRemove Text to display while asking for input.
    * @param displayMessageQueueOrRow Text to display while asking for input.
    * @return Validated integer.
    */
   public static int validateIntInput(String displayMessageAddOrRemove, String displayMessageQueueOrRow){
      Scanner input = new Scanner(System.in);
      int number = 0;
      boolean isCorrectInput = true;

      while(isCorrectInput){
         try{
            System.out.print("Enter the " +displayMessageQueueOrRow+ " number you want to "+displayMessageAddOrRemove+" the customer : ");
            number = Integer.parseInt(input.next());     //Getting input
            if (number < 0){
               System.out.println("Warning! "+displayMessageQueueOrRow+" number cannot be less than 0.");
            }else{
               isCorrectInput = false;
            }
         }catch (NumberFormatException e1){
            System.out.println("\nError! Input must be an integer.\n");
         }
      }
      return number;
   }

   /**
    * This method is used to validate the name.
    *
    * @param nameType Text which will display first or last name while asking for input from user.
    * @return validated name.
    */
   public static String validateName(String nameType) {
      Scanner input = new Scanner(System.in);
      String name = null;
      boolean isCorrectInput = true;

      while (isCorrectInput) {
         System.out.print("Enter the customer's " +nameType+ ": ");
         name = input.next();       //Getting input
         //name.matches code is taken from https://www.techiedelight.com/check-string-contains-only-alphabets-java/
         if (name.matches("^[a-zA-Z]*$")) {           //This will check whether the entered name contains alphabets.
            isCorrectInput = false;
         }else{
            System.out.println("\nError! Name can only contains letters.\n");
         }
      }
      return name;
   }

   /**
    * This method is used to validate the required burger count.
    *
    * @return validated burger count.
    */
   public static int validateBurgerCount(){
      Scanner input = new Scanner(System.in);
      int requiredBurgers = 0;
      boolean isCorrectInput = true;
      while(isCorrectInput){
         try{
            System.out.print("Enter the number of burgers required : ");
            requiredBurgers = Integer.parseInt(input.next()); //Getting the required burger amount as a string and converting into integer
            if(requiredBurgers > burgerCount ){
               System.out.println("Warning! Number of required burgers cannot exceed the available burger count. ");
               System.out.println("Required burger count should be less than " + burgerCount + " .");
            }else if(requiredBurgers < 0){
               System.out.println("Warning! Number of required burgers cannot be less than 0.");
            }
            else{
               isCorrectInput = false;
            }
         } catch (NumberFormatException e){
            System.out.println("\nError! Burger Count must be an integer value. \n");
         }
      }
      return requiredBurgers;
   }
}

