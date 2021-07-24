package com.sunil.assertions;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class CustomRegexMatcher extends TypeSafeMatcher<String> {

    private final String regex;

    public CustomRegexMatcher(final String regex) {
        this.regex = regex;
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("matches regular expression=`" + regex + "`");
    }

    @Override
    public boolean matchesSafely(final String string) {
        return string.matches(regex);
    }


    // matcher method you can call on this matcher class
    public static CustomRegexMatcher matchesRegex(final String regex) {
        return new CustomRegexMatcher(regex);
    }
}
