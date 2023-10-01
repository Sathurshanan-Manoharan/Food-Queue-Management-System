package com.example.New;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class GUIController {
    @FXML
    private TextArea customerQueue;
    @FXML
    private TextArea waitingQueue;
    @FXML
    private TextField searchBar;

    @FXML
    protected void searchCustomer() {
        FoodQueue[] allTheQueues = Main.allTheQueues;           //getting all the food queue objects
        WaitingQueue waitingQueueCustomers = Main.waitingCustomers;         //getting all the waiting queue objects

        String name = searchBar.getText();              //Getting the name from user
        String queueOutPutDetails = "";                 //String to store the output
        String waitingQueueOutputDetails = "";          //String to store the output

        //Searching for the name inside food queue
        for (int i = 0; i < allTheQueues.length; i++) {
            for (int j = 0; j < allTheQueues[i].getCustomers().size(); j++) {
                if(allTheQueues[i].getCustomers().get(j).getFirstName().equalsIgnoreCase(name)){
                    queueOutPutDetails += " Queue "+(i+1)+"\n";
                    queueOutPutDetails += allTheQueues[i].getCustomers().get(j).getAllDetails();
                    queueOutPutDetails += "- - - - - -\n";
                }
            }
        }
        //Checking the name inside waiting queue
        for (int i = 0; i < waitingQueueCustomers.getWaitingCustomers().length; i++) {
            if(waitingQueueCustomers.getWaitingCustomers()[i] == null){
                break;
            }
            else if (waitingQueueCustomers.getWaitingCustomers()[i].getFirstName().equalsIgnoreCase(name)){
                waitingQueueOutputDetails += waitingQueueCustomers.getWaitingCustomers()[i].getAllDetails();
                waitingQueueOutputDetails += "- - - - - -\n";
            }
        }
        if (waitingQueueOutputDetails.equals("")){
            waitingQueue.setText("Customer is not in the waiting queue.");
        }else{
            waitingQueue.setText(waitingQueueOutputDetails);        //Printing the stored details
        }
        if(queueOutPutDetails.equals("")){
            customerQueue.setText("Customer is not in the food queue.");
        }else{
            customerQueue.setText(queueOutPutDetails);              //Printing the stored details
        }

    }

    @FXML
    protected void customerDetails(){
        FoodQueue[] allTheQueues = Main.allTheQueues;           //getting all the food queue objects
        WaitingQueue waitingQueueCustomers = Main.waitingCustomers;         //getting all the waiting queue objects

        String queueOutPutDetails = "";                 //String to store the output
        String waitingQueueOutputDetails = "";          //String to store the output

        //Storing all the details from food queue
        for (int i = 0; i < allTheQueues.length; i++) {
            for (int j = 0; j < allTheQueues[i].getCustomers().size(); j++) {
                queueOutPutDetails += " Queue "+(i+1)+" - Customer "+"\n";
                queueOutPutDetails += allTheQueues[i].getCustomers().get(j).getAllDetails();
                queueOutPutDetails += "- - - - - - - - - -\n";
            }
        }
        //Storing all the details from waiting queue
        for (int i = 0; i < waitingQueueCustomers.getWaitingCustomers().length; i++) {
            if(waitingQueueCustomers.getWaitingCustomers()[i] == null){
                break;
            }else {
                waitingQueueOutputDetails += waitingQueueCustomers.getWaitingCustomers()[i].getAllDetails();
                waitingQueueOutputDetails += "- - - - - -\n";
            }
        }

        if (waitingQueueOutputDetails.equals("")){
            waitingQueue.setText("No customers in the waiting queue.");
        }else{
            waitingQueue.setText(waitingQueueOutputDetails);        //Printing the stored details
        }
        if (queueOutPutDetails.equals("")){
            customerQueue.setText("No customers in the food queue.");
        }else{
            customerQueue.setText(queueOutPutDetails);              //Printing the stored details
        }
    }
}