package com.java8.test;

/**
 * Created by yazha on 2017-06-08.
 */
public class ComparisonProvider {

    public int compareByName(Person a, Person b) {
        return a.name.compareTo(b.name);
    }

    public int compareByAge(Person a, Person b){
        return a.age - b.age;
    }

}
