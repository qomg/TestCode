package com.java8.test;

import java.util.function.Supplier;

/**
 * Created by yazha on 2017-06-08.
 */
public class Person {

    String name;
    int age;

    public Person(){
        this.name = "default";
        this.age = 20;
    }


    public Person(int age){
        this.age = age;
    }

    public Person(Supplier<Person> other){
        Person p = other.get();
        this.name = p.name;
        this.age = p.age;
    }

    public int compare(Person p){
        return this.age - p.age;
    }

}
