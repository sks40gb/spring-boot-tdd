package com.sunil.hamcrest;

import static org.hamcrest.MatcherAssert.*;
import  static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.text.CharSequenceLength.*;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.*;
import static org.hamcrest.object.HasToString.*;
import static org.hamcrest.object.IsCompatibleType.*;
import org.junit.Test;

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
