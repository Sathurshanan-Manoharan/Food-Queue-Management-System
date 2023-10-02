import java.io.FileWriter;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;


public class FoodiesFaveFoodcenter {

    static int burgerCount = 50; //Initialising the burger count
    static String[][] queues = {
            {"X  ", "X  ", "X  "},
            {"X  ", "X  ", "X  "},
            {"   ", "X  ", "X  "},
            {"   ", "   ", "X  "},
            {"   ", "   ", "X  "}
    }; // This queues 2D array was created to store the information of the queue.
    static String[][] customerNames = {{" ", " ", " "}, {" ", " ", " "}, {" ", " ", " "}, {" ", " ", " "}, {" ", " ", " "}}; // To store the customers names and their positions.

    static int customerNameCount = 0; //To get the count of the customers inside the shop at a time
    public static File txtFile = new File("Program_Data_File.txt");//Creating the text file to store details about the program

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in); //Importing the scanner

        while (true) {
            //Printing the options in the main menu
            System.out.println("\n100 or VFQ: View all Queues.");
            System.out.println("101 or VEQ: View all Empty Queues.");
            System.out.println("102 or ACQ: Add customer to a Queue.");
            System.out.println("103 or RCQ: Remove a customer from a Queue.");
            System.out.println("104 or PCQ: Remove a served customer.");
            System.out.println("105 or VCS: View Customers Sorted in alphabetical order.");
            System.out.println("106 or SPD: Store Program Data into file.");
            System.out.println("107 or LPD: Load Program Data from file.");
            System.out.println("108 or STK: View Remaining burgers Stock.");
            System.out.println("109 or AFS: Add burgers to Stock.");
            System.out.println("999 or EXT: Exit the Program.\n");

            System.out.print("Enter an option from the above menu : ");
            String userChoice = input.next(); //Getting an input from the user.

            if (burgerCount == 10) {
                System.out.println("\n10 Burgers remaining!!!\n"); //Displaying an alert message when the burger count reaches 10
            } else if (burgerCount<10) {
                System.out.println("\nless than 10 burgers remaining!!!\n");
            }

            switch (userChoice) {
                //Calling the desired method depending on the user's selection
                case "100", "VFQ" -> allQueues();
                case "101", "VEQ" -> emptyQueues();
                case "102", "ACQ" -> addCustomer();
                case "103", "RCQ" -> removeCustomer();
                case "104", "PCQ" -> removeServedCustomer();
                case "105", "VCS" -> sortCustomers();
                case "106", "SPD" -> storingProgramData();
                case "107", "LPD" -> readingProgramData();
                case "108", "STK" -> remainingBurgerStock();
                case "109", "AFS" -> addBurgersToStock();
                case "999", "EXT" -> System.exit(0);
                default -> System.out.println("Invalid Choice!");
            }
        }
    }

    /**
     *This method is used to display the queue status.
     */
    public static void allQueues() {
        System.out.println("******************");
        System.out.println("*    " + "Cashiers" + "    *");
        System.out.println("*****************");
        System.out.println();
        System.out.println("Q1 " + "Q2 " + "Q3");//Displaying the cashier number
        for (int i = 0; i < 5; i++) {
            for (int u = 0; u < 3; u++) {
                System.out.print(queues[i][u]);
            }
            System.out.println();
        }
    }

    /**
     *This method is used to display all the empty queues.
     */
    public static void emptyQueues() {
        //Nested for loop is used to print the queues 2d array
        System.out.println("Empty slots = X");
        for (int i = 0; i < 5; i++) {
            for (int u = 0; u < 3; u++) {
                if (queues[i][u] == "X  " || queues[i][u] == "   ") {
                    System.out.print(queues[i][u]);
                } else {
                    System.out.print("   ");
                }
            }
            System.out.println();
        }
    }

    /**
     * This method is used to add customers.
     */
    public static void addCustomer() {
        int queueNoOfCustomer = 0;//Variable created to store the queue number
        int rowNoOfCustomer = 0;//Variable created to store a row number of particular queue
        Scanner input = new Scanner(System.in);
        boolean isCorrectQueue = true;//Creating a boolean variable to run the while loop
        boolean isQueueFull = true;//Creating a boolean variable to run an if condition to check whether the rows are full
        while (isCorrectQueue) {

            queueNoOfCustomer = validateIntInput("add","queue");

            if (queueNoOfCustomer > 4) {
                System.out.println("Invalid queue number!, Enter a queue number from 1,2 & 3. Enter 4 to go to the main menu");
            } else {
                if (queueNoOfCustomer == 4) {
                    //Returning to the main menu if the users enters 4 as the input
                    return;
                } else if (queueNoOfCustomer == 1) {
                    if (queues[0][queueNoOfCustomer - 1] == "O  " && queues[1][queueNoOfCustomer - 1] == "O  ") {
                        //Checking whether the queue is available to add another customer
                        System.out.println("Selected queue is full!, please select another queue. Enter 4 to go to the main menu");
                        continue;
                    } else {
                        for (rowNoOfCustomer = 0; rowNoOfCustomer < 2; rowNoOfCustomer++) {
                            if (queues[rowNoOfCustomer][queueNoOfCustomer - 1] == "X  ") {
                                //Changing the selected slot from "X"(Not Occupied) to "O"(Occupied)
                                queues[rowNoOfCustomer][queueNoOfCustomer - 1] = "O  ";
                                break;
                            }
                        }
                    }
                } else if (queueNoOfCustomer == 2) {
                    if ((queues[0][queueNoOfCustomer - 1] == "O  ") && (queues[1][queueNoOfCustomer - 1] == "O  ") && (queues[2][queueNoOfCustomer - 1] == "O  ")) {
                        //Checking whether the queue is available to add another customer
                        System.out.println("Selected queue is full!, please select another queue. Enter 4 to go to the main menu");
                        continue;
                    } else {
                        for (rowNoOfCustomer = 0; rowNoOfCustomer < 3; rowNoOfCustomer++) {
                            if (queues[rowNoOfCustomer][queueNoOfCustomer - 1] == "X  ") {
                                //Changing the selected slot from "X"(Not Occupied) to "O"(Occupied)
                                queues[rowNoOfCustomer][queueNoOfCustomer - 1] = "O  ";
                                break;
                            }
                        }
                    }
                } else {
                    if (queueNoOfCustomer == 3) {
                        for (rowNoOfCustomer = 0; rowNoOfCustomer < 5; rowNoOfCustomer++) {
                            //Checking whether the queue is available to add another customer
                            if (queues[rowNoOfCustomer][queueNoOfCustomer - 1] == "X  ") {
                                //Changing the selected slot from "X"(Not Occupied) to "O"(Occupied)
                                queues[rowNoOfCustomer][queueNoOfCustomer - 1] = "O  ";
                                isQueueFull = false;//returning false if the queue is full
                                break;
                            }
                        }
                        if (isQueueFull) {
                            //Displaying a message to the user if the queue full
                            System.out.println("Selected queue is full!, please select another queue. Enter 4 to go to the main menu");
                            continue;
                        }
                    }
                }
                System.out.print("Enter the name of the customer : ");//Getting the name of the customer.
                for (int i = 0; i < 1; i++) {
                    if (customerNameCount == 10) {
                        System.out.println("Queues are full!");
                        return;
                    } else {
                        customerNames[rowNoOfCustomer][queueNoOfCustomer - 1] = input.next();
                        System.out.println("Successfully added "+customerNames[rowNoOfCustomer][queueNoOfCustomer - 1]+" to queue "+queueNoOfCustomer);
                        customerNameCount++;//Increasing the customer name count to ensure that the customer limit doesn't pass 10 inside the shop
                        isCorrectQueue = false;//After a particular customer is added boolean variable is given false attribute to stop the while loop
                    }
                }
            }
        }
    }

    /**
     * This method is used validate the integer type user input.
     * @param displayMessageAddOrRemove Text to display while asking for input.
     * @param displayMessageQueueOrRow Text to display while asking for input.
     * @return Validated input.
     */
    public static int validateIntInput(String displayMessageAddOrRemove, String displayMessageQueueOrRow){
        Scanner input = new Scanner(System.in);
        int number = 0;
        boolean isCorrectInput = true;

        while(isCorrectInput){
            try{
                System.out.print("Enter the " +displayMessageQueueOrRow+ " number you want to "+displayMessageAddOrRemove+" the customer : ");
                number = Integer.parseInt(input.next());     //Getting input
                if (number <= 0){
                    System.out.println("Warning! "+displayMessageQueueOrRow+" number cannot be less than or equal to 0.");
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
     * This method is used to remove a customer from a specific spot.
     */
    public static void removeCustomer() {
        int queueNoOfCustomer = 0;
        int rowNoOfCustomer = 0;
        boolean isCorrectSlot = true;//Creating a boolean variable to run the while loop
        while (isCorrectSlot) {

            queueNoOfCustomer = validateIntInput("remove","queue");
            rowNoOfCustomer = validateIntInput("remove","row");

            if (queueNoOfCustomer > 3) {
                //Checking whether the queue number is in range. if not displaying an error message
                System.out.println("Invalid queue number!, Enter a queue number from 1,2 and 3");
            } else if (rowNoOfCustomer > 5) {
                //Checking whether the row number is in range. if not displaying an error message
                System.out.println("Invalid row number!, Enter a row number below 6");
            } else if ((queueNoOfCustomer <= 2 && rowNoOfCustomer > 3) || (queueNoOfCustomer == 1 && rowNoOfCustomer == 3)) {
                //Checking whether the entered slot is in range. if not displaying an error message
                System.out.println("No queues available at selected slot");
            } else {
                if (queues[rowNoOfCustomer - 1][queueNoOfCustomer - 1] == "X  ") {
                    //Checking whether the entered slot has a customer to remove. if not displaying an error message
                    System.out.println("Selected slot doesn't have a customer in the queue!");
                    isCorrectSlot = false;//assigning false to the boolean variable to stop the loop and return to the main menu
                } else {
                    queues[rowNoOfCustomer - 1][queueNoOfCustomer - 1] = "X  ";//Changing the selected slot from "O"(Occupied) to "X"(Not Occupied)
                    if (queueNoOfCustomer - 1 == 0) {
                        System.out.println("Successfully removed "+customerNames[rowNoOfCustomer-1][queueNoOfCustomer-1]+ " from the queue.");
                        for (int row = rowNoOfCustomer - 1; row < 2; row++) {
                            for (int column = queueNoOfCustomer - 1; column < 1; column++) {
                                queues[row][column] = queues[row + 1][column];//Moving each row forward in the queue after removing the desired row
                                customerNames[row][column] = customerNames[row + 1][column];//Moving customers forward in each column after removing the selected customer
                            }
                        }
                        queues[1][0] = "X  ";//Assigning "X" to the last row of the particular column
                        customerNameCount--;//Decreasing the customer count after removing a customer
                    } else if (queueNoOfCustomer - 1 == 1) {
                        System.out.println("Successfully removed "+customerNames[rowNoOfCustomer-1][queueNoOfCustomer-1]+ " from the queue.");
                        for (int row = rowNoOfCustomer - 1; row < 3; row++) {
                            for (int column = queueNoOfCustomer - 1; column < 2; column++) {
                                queues[row][column] = queues[row + 1][column];//Moving each row forward in the queue after removing the desired row
                                customerNames[row][column] = customerNames[row + 1][column];//Moving customers forward in each column after removing the selected customer
                            }
                        }
                        queues[2][1] = "X  ";//Assigning "X" to the last row of the particular column
                        customerNameCount--;//Decreasing the customer count after removing a customer
                    } else {
                        System.out.println("Successfully removed "+customerNames[rowNoOfCustomer-1][queueNoOfCustomer-1]+ " from the queue.");
                        for (int row = rowNoOfCustomer - 1; row < 4; row++) {
                            for (int column = queueNoOfCustomer - 1; column < 3; column++) {
                                queues[row][column] = queues[row + 1][column];//Moving each row forward in the queue after removing the desired row
                                customerNames[row][column] = customerNames[row + 1][column];//Moving customers forward in each column after removing the selected customer
                            }
                        }
                        queues[4][2] = "X  ";//Assigning "X" to the last row of the particular column
                        customerNames[4][2] = " ";
                        customerNameCount--;//Decreasing the customer count after removing a customer
                    }
                    isCorrectSlot = false;//After a particular customer is removed, boolean variable is given false  to stop the while loop
                }
            }
        }
    }

    /**
     * This method is used to remove the served customers.
     */
    public static void removeServedCustomer() {
        int queueNoOfCustomer = 0;
        boolean isCorrectSlot = true;
        while (isCorrectSlot) {
            queueNoOfCustomer = validateIntInput("remove","queue");

            if (queueNoOfCustomer > 3) {
                //Checking whether the queue number is in range. if not displaying an error message
                System.out.println("Invalid queue number!, Enter a queue number from 1,2 and 3");
            } else if ((queues[0][0] == "X  ") && (queues[0][1] == "X  ") && (queues[0][2] == "X  ")) {
                //Checking whether the entered slot has a customer to remove. if not displaying an error message
                System.out.println("None of the queues have customers to remove.");
                isCorrectSlot = false;
            } else {
                if (queues[0][queueNoOfCustomer - 1] == "X  ") {
                    System.out.println("Selected queue doesn't have a customer in position one!, please enter another queue.");
                } else {
                    queues[0][queueNoOfCustomer - 1] = "X  ";
                    if (queueNoOfCustomer - 1 == 0) {
                        System.out.println("Successfully removed "+customerNames[0][queueNoOfCustomer-1]+ " from the queue.");
                        for (int row = 0; row < 2; row++) {
                            for (int column = queueNoOfCustomer - 1; column < 1; column++) {
                                queues[row][column] = queues[row + 1][column];//Moving each row forward in the queue after removing the 1st row
                                customerNames[row][column] = customerNames[row + 1][column];//Moving customers forward in each column after removing the customer in row one
                            }
                        }
                        queues[1][0] = "X  ";//Assigning "X" to the last row of the particular column
                        customerNameCount--;//Decreasing the customer count after removing a customer
                    } else if (queueNoOfCustomer - 1 == 1) {
                        System.out.println("Successfully removed "+customerNames[0][queueNoOfCustomer-1]+ " from the queue.");
                        for (int row = 0; row < 3; row++) {
                            for (int column = queueNoOfCustomer - 1; column < 2; column++) {
                                queues[row][column] = queues[row + 1][column];//Moving each row forward in the queue after removing the 1st row
                                customerNames[row][column] = customerNames[row + 1][column];//Moving customers forward in each column after removing the customer in row one
                            }
                        }
                        queues[2][1] = "X  ";//Assigning "X" to the last row of the particular column
                        customerNameCount--;//Decreasing the customer count after removing a customer
                    } else {
                        System.out.println("Successfully removed "+customerNames[0][queueNoOfCustomer-1]+ " from the queue.");
                        for (int row = 0; row < 4; row++) {
                            for (int column = queueNoOfCustomer - 1; column < 3; column++) {
                                queues[row][column] = queues[row + 1][column];//Moving each row forward in the queue after removing the 1st row
                                customerNames[row][column] = customerNames[row + 1][column];//Moving customers forward in each column after removing the customer in row one
                            }
                        }
                        queues[4][2] = "X  ";//Assigning "X" to the last row of the particular column
                        customerNames[4][2] = " ";
                        customerNameCount--;//Decreasing the customer count after removing a customer
                    }
                    burgerCount = burgerCount - 5; // Reducing the burger count by 5 after a served customer is removed
                    System.out.println(burgerCount + " Burgers remaining.");
                    isCorrectSlot = false; //After a particular customer is removed, boolean variable is given false  to stop the while loop
                }
            }
        }
    }

    /**
     * This method is used to sort the customer names in the alphabetical order.
     */
    public static void sortCustomers() {
        int count = 0;//Creating a count variable to store the number of customers who are in the shop
        int sortingCustomerListCount = 0;//Creating a count variable to count the times a name is being stored inside an array
        for (int i = 0; i < customerNames.length; i++) {
            for (int j = 0; j < customerNames[i].length; j++) {
                if (customerNames[i][j] != " ") {
                    //Getting the count of the customers who are in the shop
                    count++;
                }
            }
        }
        String[] sortingCustomerList = new String[count]; // array to store the customer names to sort them in alphabetical order

        for (int i = 0; i < customerNames.length; i++) {
            for (int j = 0; j < customerNames[i].length; j++) {
                if (customerNames[i][j] != " ") {
                    //Appending the customer names into a new array to sort
                    sortingCustomerList[sortingCustomerListCount] = customerNames[i][j];
                    sortingCustomerListCount++;
                }
            }
        }
        //Printing the customer names before sorting them
        System.out.print("Names before sorting them according to alphabetical order : ");
        for (int i = 0; i < sortingCustomerList.length; i++) {
            System.out.print(sortingCustomerList[i] + ", ");
        }
        System.out.println();

        for (int i = 0; i < sortingCustomerList.length; i++) {
            for (int j = i + 1; j < sortingCustomerList.length; j++) {
                int letterIndex = 0;//Creating a variable to count the length of the string
                String temp;//Creating a temporary variable to store an element
                for (letterIndex = 0; (letterIndex < sortingCustomerList[i].length()) && (letterIndex < sortingCustomerList[j].length()); letterIndex++) {
                    if (sortingCustomerList[i].charAt(letterIndex) > sortingCustomerList[j].charAt(letterIndex)) {
                        //checking whether the 1st character of the 1st string is greater than the 1st character of the 2nd string
                        temp = sortingCustomerList[i];                  //Temporarily storing the element if it's greater than the element after it
                        sortingCustomerList[i] = sortingCustomerList[j];//appending the element to the ith index from the jth index
                        sortingCustomerList[j] = temp;                  //Then storing the element which was stored in the temp variable to jth index
                        break;
                    } else if (sortingCustomerList[i].charAt(letterIndex) < sortingCustomerList[j].charAt(letterIndex)) {
                        /*
                        checking whether the 1st character of the 1st string is smaller than the 1st character of the 2nd string.
                        if yes, the loop is stopped and the elements aren't changed
                         */
                        break;
                    }
                }
                if (sortingCustomerList[i].length() > letterIndex && sortingCustomerList[j].length() <= letterIndex) {
                    //Changing places of the elements if the length of the first string is greater than the second string
                    temp = sortingCustomerList[i];
                    sortingCustomerList[i] = sortingCustomerList[j];
                    sortingCustomerList[j] = temp;
                }
            }
        }
        //Printing the customer names after sorting them
        System.out.print("Sorted customer names according to alphabetical order : ");
        for (int i = 0; i < sortingCustomerList.length; i++) {
            System.out.print(sortingCustomerList[i] + ", ");
        }
        System.out.println();
    }

    /**
     * This method is used to store the program data
     */
    public static void storingProgramData(){
        try {
            FileWriter writer = new FileWriter("Program_Data_File.txt");
            writer.write("--Customer names--\n");
            for (int i = 0; i < customerNames.length; i++) {//Writing all the customer names in the text file
                for (int j = 0; j < customerNames[i].length; j++) {
                    if (customerNames[i][j] != " ") {
                        writer.write(customerNames[i][j] + "\n");
                    }
                }
            }
            writer.write("Burgers available in the stock are "+burgerCount);//Writing the remaining burger stock in the text file
            writer.close();
        }
        catch (IOException e){
            throw new RuntimeException(e);
        }
        System.out.println("Data is stored into the text file.");
    }

    /**
     * This method is used to load the program data.
     */
    public static void readingProgramData(){
        try {
            Scanner lineReader = new Scanner(txtFile);
            while (lineReader.hasNextLine()) {
                String fileData = lineReader.nextLine();//Storing lines in the text file as string
                System.out.println(fileData);//printing the string line by line
            }
            lineReader.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to display the remaining burger stock.
     */
    public static void remainingBurgerStock(){
        System.out.println("The remaining burgers stock is "+burgerCount);//Displaying the remaining burger stock
    }

    /**
     * This method is used add burgers to the stock.
     * This method is used add burgers to the stock.
     */
    public static void addBurgersToStock(){
        int addingAmount = 0;
        while(true) {
            try {
                Scanner input = new Scanner(System.in);//Getting the number of burgers the user wants to add
                System.out.print("Enter the amount of burgers you want add : ");
                addingAmount = Integer.parseInt(input.next());
                if (burgerCount == 50) {
                    //if the burger count is already 50, a message will be displayed
                    System.out.println("Burger stock is full!");
                    break;
                } else if (burgerCount + addingAmount > 50) {
                    //Checking whether the added burger count and the stock burger is more than 50
                    System.out.println("Burger stock cannot be more than 50. The remaining burger count is " + burgerCount);
                } else if (addingAmount < 0){
                    System.out.println("Warning! adding burger count cannot be less than 0");
                } else {
                    burgerCount = burgerCount + addingAmount;//Adding the user added burger count and remaining burger count
                    System.out.println("Successfully added " + addingAmount + " of burgers to the stock.");
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("\nError! Burger Count must be an integer value. \n");
            }
        }
    }
}
