package com.sunil;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.ArgumentMatcher;
import org.mockito.InOrder;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.AdditionalMatchers.*;

import java.util.Arrays;
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


    @Test
    public void additionalMatches() {
        //given
        List<String> itemsMock = mock(List.class);

        //when
        when(itemsMock.get(or(eq(0), gt(10)))).thenReturn("sunil");

        //then
        Assertions.assertEquals("sunil", itemsMock.get(0));
        Assertions.assertEquals("sunil", itemsMock.get(11));
        Assertions.assertEquals(isNull(), itemsMock.get(5));

    }

    @Test
    public void verifyNumberOfInvocation() {

        List<String> mockedList = mock(List.class);
        //using mock
        mockedList.add("once");

        mockedList.add("twice");
        mockedList.add("twice");

        mockedList.add("three times");
        mockedList.add("three times");
        mockedList.add("three times");

        //following two verifications work exactly the same - times(1) is used by default
        verify(mockedList).add("once");
        verify(mockedList, times(1)).add("once");

        //exact number of invocations verification
        verify(mockedList, times(2)).add("twice");
        verify(mockedList, times(3)).add("three times");

        //verification using never(). never() is an alias to times(0)
        verify(mockedList, never()).add("never happened");

        //verification using atLeast()/atMost()
        verify(mockedList, atMostOnce()).add("once");
        verify(mockedList, atLeastOnce()).add("three times");
        verify(mockedList, atLeast(2)).add("three times");
        verify(mockedList, atMost(5)).add("three times");


    }

    @Test(expected = RuntimeException.class)
    public void stubbingVoidMethodWithException() {

        List<String> mockedList = mock(List.class);

        doThrow(new RuntimeException()).when(mockedList).clear();

        // Invalid syntax
        // when(mockedList.clear()).thenThrow(new RuntimeException("Invalid values"));

        //following throws RuntimeException:
        mockedList.clear();
    }

    @Test
    public void stubbingVoidMethodWithNothing() {

        //given
        List<String> mockedList = mock(List.class);
        doNothing().when(mockedList).clear();

        //when
        mockedList.clear();

        //then
        verify(mockedList).clear();
    }

    @Test
    public void stubbingVoidMethodWithAnswer() {

        //given
        List<String> mockedList = mock(List.class);
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                System.out.println("called with arguments: " + Arrays.toString(args));
                return null;
            }
        }).when(mockedList).add(anyInt(), anyString());

        //when
        mockedList.add(1,"sunil");

        //then
        verify(mockedList).add(anyInt(), anyString());
    }

    @Test
    public void verificationInOrder() {
        // A. Single mock whose methods must be invoked in a particular order
        List singleMock = mock(List.class);

        //using a single mock
        singleMock.add("was added first");
        singleMock.add("was added second");

        //create an inOrder verifier for a single mock
        InOrder inOrderA = inOrder(singleMock);

        //following will make sure that add is first called with "was added first", then with "was added second"
        inOrderA.verify(singleMock).add("was added first");
        inOrderA.verify(singleMock).add("was added second");

        // B. Multiple mocks that must be used in a particular order
        List firstMock = mock(List.class);
        List secondMock = mock(List.class);

        //using mocks
        firstMock.add("was called first");
        secondMock.add("was called second");

        //create inOrder object passing any mocks that need to be verified in order
        InOrder inOrderB = inOrder(firstMock, secondMock);

        //following will make sure that firstMock was called before secondMock
        inOrderB.verify(firstMock).add("was called first");
        inOrderB.verify(secondMock).add("was called second");

        /*
         It will fail since order is wrong
         inOrderB.verify(firstMock).add("was called first");
         inOrderB.verify(secondMock).add("was called second");
        */

    }

    @Test
    public void interactionNeverHappened() {
        //given
        List<String> listMock = mock(List.class);
        //when(listMock.add(any())).thenReturn(true);

        //when
        listMock.add("one");

        //then
        verify(listMock, times(1)).add("one");
        verify(listMock, never()).add("two");
    }

    @Test(expected = IllegalArgumentException.class)
    public void stubbingConsecutiveCalls() {

        //given
        List<String> listMock = mock(List.class);
        when(listMock.add("sunil"))
            .thenReturn(true)
            .thenThrow(new IllegalArgumentException(" calling same twice"));

        //when
        System.out.println(listMock.add("sunil"));
        System.out.println(listMock.add("sunil"));

        //then
        verify(listMock).add("sunil");
        verify(listMock).add("sunil");

    }

    @Test
    public void spyingOnRealObjects(){
        List<String> list = new LinkedList<>();
        List<String> spy = spy(list);

        //optionally, you can stub out some methods:
        when(spy.size()).thenReturn(100);

        //using the spy calls *real* methods
        spy.add("one");
        spy.add("two");

        //prints "one" - the first element of a list
        System.out.println(spy.get(0));

        //size() method was stubbed - 100 is printed
        System.out.println(spy.size());

        //optionally, you can verify
        verify(spy).add("one");
        verify(spy).add("two");

    }

}
