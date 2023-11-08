package ir.maktab;


import ir.maktab.mockdata.MockData;
import ir.maktab.model.Person;
import ir.maktab.model.PersonSummary;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


public class Main {
    public static void main(String[] args) {
        List<Person> people = MockData.getPeople();


        System.out.println("Q1: filter people over than 50 and remove them");
        System.out.println(filterAge(people));

        System.out.println("Q2: sorted people by user name");
        System.out.println(sortedUserName(people));

        System.out.println("Q3: sorted people by age then last name ");
        System.out.println(sortedAgeAndLastName(people));

        System.out.println("Q4 : map to ipv4");
        System.out.println(mappingByIPV(people));

        System.out.println("Q5");
        Map<String, Person> map = result(people);
        for (Map.Entry<String,Person> entry: map.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }

        System.out.println("Q6:");
        System.out.println(correctBirthDay(people));





    }
    public static List<Person> filterAge(List<Person> people){
        return people.stream().filter(person -> person.getAge()<=50).toList();
    }
    public static List<Person> sortedUserName(List<Person> people){
        return people.stream().
                sorted(Comparator.comparing(Person::getUsername)).toList();
    }
    public static List<Person> sortedAgeAndLastName(List<Person> people){
        return people.stream()
                .sorted(Comparator.comparingInt(Person::getAge)).
                sorted(Comparator.comparing(Person::getLastName)).toList();
    }
    public static Set<String> mappingByIPV(List<Person> people){
        return people.stream().map(Person::getIpv4).collect(Collectors.toSet());
    }
    public static Map<String, Person> result(List<Person> people){
        return people.stream().sorted(Comparator.comparing(Person::getLastName)).
                filter(person -> person.getAge()>40 && person.getGender().equals("Female")).
                dropWhile(person -> person.getFirstName().startsWith("A")).skip(5).limit(100).
                collect(Collectors.
                        toMap(person -> person.getFirstName()+" "+person.getLastName(), Function.identity(),(person, person2) -> person));
    }
    public static OptionalDouble correctBirthDay(List<Person> people){
        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        return people.stream().filter(person -> person.getGender().equals("Male")).
                map(person -> {
                    try {
                        return new PersonSummary(person.getId(), person.getFirstName(), person.getLastName(),
                                2023-(date.parse(person.getBirthDate()).getYear()+1900),date.parse(person.getBirthDate()));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                })
                .map(PersonSummary::getAge).mapToDouble(Integer::doubleValue).average();
    }

}