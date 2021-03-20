package com.sunil;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;

public class MockitoTest {

    @Test
    public void verifySomeBehavior() {
        //mock creation
        List<String> mockedList = mock(List.class);

        //using mock object
        mockedList.add("one");
        mockedList.clear();

        //verification
        verify(mockedList).add("one");
        verify(mockedList).clear();
    }

    @Test
    public void someStabbing() {

        List<String> mockedList = mock(List.class);

        when(mockedList.get(0)).thenReturn("Sunil");
        when(mockedList.get(1)).thenThrow(new IllegalArgumentException());

        System.out.println(mockedList.get(0));
        // System.out.println(mockedList.get(1));
        System.out.println(mockedList.get(2));

        //Assertions.assertThat(mockedList, doThrow(new IllegalArgumentException()));

        verify(mockedList).get(0);
        //verify(mockedList).get(1);
        verify(mockedList).get(2);

    }


    @Test
    public void argumentMatcher1() {

        // given
        LinkedList<String> mockedLinkedList = mock(LinkedList.class);
        when(mockedLinkedList.get(anyInt())).thenReturn("Sunil");
        when(mockedLinkedList.contains(anyString())).thenReturn(true);

        //when
        System.out.println(mockedLinkedList.get(9999));
        System.out.println(mockedLinkedList.contains("hello world"));

        //then
        verify(mockedLinkedList).get(anyInt());
        verify(mockedLinkedList).contains(anyString());

    }


    interface Dry {
        String dryRun(Boolean flag);
    }

    @Test
    public void argumentMatcherForNull() {

        Dry mock = mock(Dry.class);
        // stubbing using anyBoolean() argument matcher
        when(mock.dryRun(any(Boolean.class))).thenReturn("state");

        // below the stub won't match, and won't return "state"
        System.out.println(mock.dryRun(null));

        // either change the stub
        when(mock.dryRun(isNull())).thenReturn("state");
        System.out.println(mock.dryRun(null)); // ok

        // or fix the code ;)
        when(mock.dryRun(anyBoolean())).thenReturn("state");
        mock.dryRun(true); // ok
    }

    static class MyClass {

        protected String method() {
            throw new NullPointerException();
        }
    }

    @Test
    public void whenThenReturnVSdoReturnWhenCase1() {

        MyClass myClass = spy(MyClass.class);
        // would work fine
        doReturn("test").when(myClass).method();
        System.out.println(myClass.method());
    }

    @Test(expected = NullPointerException.class)
    public void whenThenReturnVSdoReturnWhenCase2() {

        MyClass myClass = spy(MyClass.class);

        // would throw a NullPointerException
        when(myClass.method()).thenReturn("test");
        verify(myClass).method();

    }


}
