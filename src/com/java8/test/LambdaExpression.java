package com.java8.test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.function.Supplier;

/**
 * Created by yazha on 2017-06-08.
 */
public class LambdaExpression implements Supplier<Person>, PersonCapability{

    @Override
    public Person get() {
        return null;
    }

    public static int compare(Person a, Person b){
        return a.age - b.age;
    }

    private static String[] name = {"Jack", "James", "Jones", "Jane", "Jim"};

    public static void main(String[] args) {
        Random random = new Random(System.currentTimeMillis());
        Person[] people = new Person[name.length];
        for (int i = 0; i < people.length; i++) {
            Person p = new Person(random.nextInt(60));
            p.name = name[i];
            people[i] = p;
        }

        //orginal
        Arrays.sort(people, new Comparator<Person>() {
            @Override
            public int compare(Person p1, Person p2) {
                return p1.age - p2.age;
            }
        });

        //first -> lambda
        Arrays.sort(people, (p1, p2) -> p1.age - p2.age);
        //first transform
        Arrays.sort(people, Comparator.comparingInt(p -> p.age));
        //second -> still lambda
        Arrays.sort(people, (p1, p2) -> compare(p1, p2));
        //second transform -> method reference -> static method
        Arrays.sort(people, LambdaExpression::compare);
        //second transform -> method reference -> object itself method
        Arrays.sort(people, Person::compare);
        //third -> method reference -> object method
        Arrays.sort(people, new ComparisonProvider()::compareByName);
        Arrays.sort(people, new ComparisonProvider()::compareByAge);
        // forth -> class constructor
        Person person = new Person(Person::new);
        System.out.println(person.name + " = " + person.age);

        new LambdaExpression().doSomeThing();
    }

    @Override
    public Person newInstance(Person p) {
        return null;
    }
}
