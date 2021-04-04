package com.sunil.mockito;

public class Message {

   public String getGreeting(Gender gender, String name){

        if(gender == Gender.MALE){
            return "Hello Mr. " + name;
        } if(gender == Gender.FEMALE){
            return "Hello Mrs. " + name;
        }else{
            throw new RuntimeException("Gender " + gender + "not handled");
       }
   }
}
