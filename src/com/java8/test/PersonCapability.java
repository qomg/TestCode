package com.java8.test;

/**
 * Created by yazha on 2017-06-08.
 */
@FunctionalInterface
public interface PersonCapability {
    Person newInstance(Person p);
    //default method
    default void doSomeThing(){
        System.out.println("haha");
    }
    //static method
    static void doSomeThingElse(){
        System.out.println("hahahahaha");
    }
}
