package com.sunil.hamcrest;

import java.util.*;
import org.junit.Test;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class HamcrestTest {

    @Test
    public void core(){

        //Anything
        assertThat("Sunil", anything());

        //describeAs
        assertThat("Sunil", describedAs("Value must be Sunil", is("Sunil")));

    }

    @Test
    public void logical(){

        //allOf
        assertThat("Sunil", allOf(is("Sunil"), hasLength(5)));

        //anyOf
        assertThat("Sunil", anyOf(is("Singh"), hasLength(2), containsString("Sun")));

        //both
        assertThat("Sunil", both(is("Sunil")).and(hasLength(5)).and(containsString("Sun")));

        //either
        assertThat("Sunil", either(is("Sunil")).or(containsString("check")));

    }

    @Test
    public void object(){

        Employee sunil1 = new Employee(1,"Sunil");
        Employee sunil2 = new Employee(1,"Sunil");

        //It will match even the instances are different but the content is same
        assertThat(sunil1, is(sunil2));

        //same as above
        assertThat(sunil1, equalTo(sunil2));

        //Compare the string returns by toString method
        assertThat(sunil1, hasToString("Employee(id=1, name=Sunil)"));

        //instanceOf
        assertThat(sunil1, instanceOf(Employee.class));

        //typeCompatibleWith
        assertThat(sunil1.getClass(), typeCompatibleWith(Employee.class));
        assertThat(Integer.class, typeCompatibleWith(Number.class));

        //nullValue
        assertThat(null, nullValue());

        //notNullValue
        assertThat(sunil1, notNullValue());

        //sameInstance
        assertThat(sunil1, sameInstance(sunil1));

    }

    @Test
    public void beans(){

        Employee sunil = new Employee(1,"Sunil");

        //hasProperty
        assertThat(sunil, hasProperty("name"));

        assertThat(sunil, hasProperty("name",equalTo("Sunil")));


    }

    @Test
    public void array(){

        Employee sunil = new Employee(1, "Sunil");
        Employee john = new Employee(2, "John");
        Employee alan = new Employee(3, "Alan");

        Employee[] employeeArray = {sunil, john, alan};
        Employee[] orderedArray = {sunil, john, alan};
        Employee[] unorderedArray = {sunil, alan, john};

        //arrayWithSize
        assertThat(employeeArray, arrayWithSize(3));

        //arrayContaining
        assertThat(employeeArray, arrayContaining(orderedArray));

        //hasItemInArray
        assertThat(employeeArray, hasItemInArray(alan));

        //arrayContainingInAnyOrder
        assertThat(employeeArray, arrayContainingInAnyOrder(unorderedArray));

        //emptyArray
        assertThat(new Employee[0], emptyArray());
    }

    @Test
    public void collections(){

        Employee sunil = new Employee(1, "Sunil");
        Employee john = new Employee(2, "John");
        Employee alan = new Employee(3, "Alan");

        Collection<Employee> employeeList = Arrays.asList(sunil,john,alan);
        Collection<Employee> orderedList = Arrays.asList(sunil,john,alan);
        Collection<Employee> unorderedList = Arrays.asList(sunil,john,alan);

        //iterableWithSize
        assertThat(employeeList, iterableWithSize(3));

        //contains
        assertThat(employeeList, contains(sunil, john, alan));

        //containsInAnyOrder
        assertThat(employeeList, containsInAnyOrder(unorderedList.toArray()));

        //hasItem
        assertThat(employeeList, hasItem(alan));

        //hasItems
        assertThat(employeeList, hasItems(john, sunil));

    }

    @Test
    public void map(){
        Employee sunil = new Employee(1, "Sunil");
        Employee john = new Employee(2, "John");
        Employee alan = new Employee(3, "Alan");

        Map<Integer, Employee> map = new HashMap<>();
        map.put(sunil.getId(), sunil);
        map.put(john.getId(), john);
        map.put(alan.getId(), alan);

        //hasKey
        assertThat(map, hasKey(1));

        //hasValue
        assertThat(map, hasValue(sunil));

        //hasEntry
        assertThat(map, hasEntry(1,sunil));

    }

    @Test
    public void number(){

        //closeTo(value,delta)
        assertThat(1.03, is(equalTo(1.03)));

        //closeTo(value,delta)
        assertThat(1.03, is(closeTo(1.0, 0.04)));

        //lessThan
        assertThat(5, lessThan(10));

        //lessThanOrEqualTo
        assertThat(5, lessThanOrEqualTo(10));

        //greaterThan
        assertThat(5, greaterThan(2));

        //greaterThanOrEqualTo
        assertThat(5, greaterThanOrEqualTo(5));
    }

    @Test
    public void string(){

        //hasLength
        assertThat("Sunil", hasLength(5));

        //equalToIgnoringCase
        assertThat("Sunil", equalToIgnoringCase("SUNIL"));

        //containsString
        assertThat("Sunil", containsString("Sun"));

        //startsWith
        assertThat("Sunil", startsWith("Su"));

        //endsWith
        assertThat("Sunil", endsWith("nil"));

        //endsWithIgnoringCase
        assertThat("Sunil", endsWithIgnoringCase("NIL"));

    }

    @Test
    public void differenceBetweenIsAndEqualTo(){

        // `is` in all its overloaded forms is there for expressiveness.
        Employee sunil1 = new Employee(1,"Sunil");
        Employee sunil2 = new Employee(1,"Sunil");

        // Case 1 :
        assertThat(sunil1, is(equalTo(sunil2))); //<-- recommended
        //is equal to
        assertThat(sunil1, equalTo(sunil2));     //<-- compare the content of object

        // Case 2 :
        assertThat(sunil1, is(instanceOf(Employee.class))); //<-- recommended
        //is equal to
        assertThat(sunil1, isA(Employee.class));            //<-- check for the Class Type

    }
}
