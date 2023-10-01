package com.example.New;

import java.io.Serializable;

public class Customer implements Serializable {
   private String firstName;
   private String lastName;
   private int noOfBurgers;

   Customer(String firstName,String lastName, int noOfBurgers){
      this.firstName = firstName;
      this.lastName = lastName;
      this.noOfBurgers = noOfBurgers;
   }

   public String getFirstName() {
      return this.firstName;
   }

   public String getFullName(){
      return this.firstName +" "+ this.lastName;
   }

   public int getNoOfBurgers() {
      return noOfBurgers;
   }

   String getAllDetails() {
      return "First Name \t- " + this.firstName + ".\n"+
              "Last Name \t- " + this.lastName + ".\n"+
              "No of Burgers \t- " + this.noOfBurgers + ".\n";
   }
}
