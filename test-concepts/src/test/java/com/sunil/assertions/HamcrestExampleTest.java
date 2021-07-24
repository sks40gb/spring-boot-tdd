package com.sunil.assertions;

import java.util.Arrays;
import java.util.List;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.junit.Test;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class HamcrestExampleTest {

    @Test
    public void hamcrestExample() {
        assertThat(true, equalTo(true));
        assertThat(true, is(equalTo(true)));
        assertThat(true, is(true));
    }

    @Test
    public void instanceOfTest() {
        assertThat(1L, not(instanceOf(Integer.class)));
        // shortcut for instanceOf
        assertThat(1L, isA(Long.class));
    }

    @Test
    public void test_list() {
        List<Integer> list = Arrays.asList(5, 2, 4);

        assertThat(list, hasSize(3));

        // ensure the order is correct
        assertThat(list, contains(5, 2, 4));
        assertThat(list, containsInAnyOrder(2, 4, 5));
        assertThat(list, everyItem(greaterThan(1)));

    }

    @Test
    public void test_array() {
        Integer[] ints = new Integer[]{7, 5, 12, 16};
        assertThat(ints, arrayWithSize(4));
        assertThat(ints, arrayContaining(7, 5, 12, 16));
    }


    @Test
    public void test_beans_property() {

        Task task = new Task(1, "Learn Hamcrest", "some description");
        assertThat(task, hasProperty("summary"));
        assertThat(task, hasProperty("summary", equalTo("Learn Hamcrest")));

    }

    @Test
    public void test_beans_are_equals() {

        Task task1 = new Task(1, "Learn Hamcrest", "some description");
        Task task2 = new Task(1, "Learn Hamcrest", "some description");
        assertThat(task1, samePropertyValuesAs(task2));

    }

    public static Matcher<String> stringLength(Matcher<? super Integer> matcher) {
        return new FeatureMatcher<String, Integer>(matcher, "a String of length that", "length") {
            @Override
            protected Integer featureValueOf(String actual) {
                return actual.length();
            }
        };
    }

    @Test
    public void custom_matcher_using_FeatureMatcher() {
        assertThat("Sunil", stringLength(is(5)));
    }

    @Test
    public void testRegularExpressionMatcher() throws Exception {
        String s ="aaabbbaaaa";
        assertThat(s, CustomRegexMatcher.matchesRegex("a*b*a*"));
    }

}
