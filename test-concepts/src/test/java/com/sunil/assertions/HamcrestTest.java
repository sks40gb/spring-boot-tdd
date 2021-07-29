package com.sunil.assertions;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;

public class HamcrestTest {

    @Test
    public void stringTest() {

        String name = "Sunil Kumar Singh";
        assertThat(name, is("Sunil Kumar Singh"));
        assertThat(name, stringContainsInOrder("Sunil", "Singh"));
        assertThat(name, hasLength(17));
        assertThat(name, equalToIgnoringCase("sunil kumar singh"));
    }

    @Test
    public void arrayTest() {
        String[] names = {"John", "David", "Alan"};
        String[] emptyArray = {};

        assertThat(names, arrayWithSize(3));
        assertThat(names, not(arrayContaining("David", "John")));
        assertThat(names, arrayContainingInAnyOrder("David", "John", "Alan"));
        assertThat(names, hasItemInArray("David"));
        assertThat(emptyArray, emptyArray());
    }

    @Test
    public void testMap() {
        Map<Integer, String> names = new HashMap<>();
        names.put(1, "Alan");
        names.put(2, "David");
        names.put(3, "John");

        assertThat(names, hasKey(2));
        assertThat(names, hasValue("David"));
        assertThat(names, hasEntry(3, "John"));
    }

    @Test
    public void testObject() {
        Employee employee = new Employee(1L, "David");
        assertThat(employee, hasProperty("name"));
        assertThat(employee, hasProperty("name", is("David")));

    }

    @Test
    public void testCollection() {
        List<String> nameList = new ArrayList<>();
        nameList.add("Alan");
        nameList.add("David");
        nameList.add("John");
        nameList.add("Mark");

        assertThat(nameList, hasSize(4));
        assertThat(nameList, contains("Alan", "David", "John", "Mark"));
        assertThat(nameList, containsInAnyOrder("Alan", "David", "Mark", "John"));
        assertThat(nameList, hasItems("Alan", "Mark"));
    }

}
