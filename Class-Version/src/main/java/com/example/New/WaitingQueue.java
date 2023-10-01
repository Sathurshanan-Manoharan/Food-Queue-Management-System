package com.example.New;

import java.io.Serializable;

public class WaitingQueue implements Serializable {
   private int front = -1;
   private int rear = -1;
   private int maxSize;
   private Customer[] waitingCustomers;


   public WaitingQueue(int maxSize){
      this.maxSize = maxSize;
      waitingCustomers = new Customer[maxSize];
   }

   public Customer[] getWaitingCustomers() {
      return waitingCustomers;
   }

   public int getFront() {
      return front;
   }

   public void enqueue(Customer item) {
      if ((front == 0 && rear == maxSize - 1) || (front == rear + 1)) {
         System.out.println("Waiting queue is full.");
      } else {
         if (front == -1) {
            front = 0;
         }
         rear = (rear + 1) % maxSize;
         waitingCustomers[rear] = item;
      }
   }
   public Customer dequeue() {
      Customer item = waitingCustomers[front];
      if (front == rear) {
         front = -1;
         rear = -1;
      } else {
         front = (front + 1) % maxSize;
      }
      return item;
   }
}
