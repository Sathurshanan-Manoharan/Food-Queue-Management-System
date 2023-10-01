package com.example.New;

import java.io.Serializable;
import java.util.ArrayList;

public class FoodQueue implements Serializable {
   private final int maxQueueLength;
   private final int queueNumber;
   private int queueIncome;

   private ArrayList<Customer> customers;

   public FoodQueue(int maxQueueLength, int queueNumber, int queueIncome){
      this.queueNumber = queueNumber;
      this.maxQueueLength = maxQueueLength;
      this.queueIncome = queueIncome;
      this.customers = new ArrayList<>(maxQueueLength);
   }

   public void addCustomers(Customer customer){

      customers.add(customer);
   }

   public int getQueueIncome() {
      return this.queueIncome;
   }

   public ArrayList<Customer> getCustomers() {
      return customers;
   }

   public void setQueueIncome(int queueIncome) {
      this.queueIncome = queueIncome;
   }
}
