package com.sunil.mockito;

import com.sunil.email.Email;
import com.sunil.email.EmailService;
import com.sunil.email.Format;
import com.sunil.email.Platform;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.aspectj.bridge.Message;
import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.AdditionalMatchers.gt;
import static org.mockito.AdditionalMatchers.or;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
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

    @Test(expected = IllegalArgumentException.class)
    public void someStabbing() {

        List<String> mockedList = mock(List.class);

        when(mockedList.get(0)).thenReturn("Sunil");
        when(mockedList.get(1)).thenThrow(new IllegalArgumentException());

        System.out.println(mockedList.get(0));
        // System.out.println(mockedList.get(1));
        System.out.println(mockedList.get(1));

        //Assertions.assertThat(mockedList, doThrow(new IllegalArgumentException()));

        verify(mockedList).get(0);
        //verify(mockedList).get(1);
        verify(mockedList).get(2);

    }


    @Test
    public void argumentMatcherSomeExamples() {

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


    interface Employee {
        String getName(Boolean flag);
    }

    @Test
    public void argumentMatcherForNull() {

        Employee employeeMock = mock(Employee.class);
        // stubbing using anyBoolean() argument matcher
        when(employeeMock.getName(ArgumentMatchers.any(Boolean.class))).thenReturn("state");

        // below the stub won't match, and won't return "state"
        System.out.println(employeeMock.getName(null));

        // either change the stub
        when(employeeMock.getName(isNull())).thenReturn("state");
        System.out.println(employeeMock.getName(null)); // ok

        // or fix the code ;)
        when(employeeMock.getName(anyBoolean())).thenReturn("state");
        employeeMock.getName(true); // ok
    }

    static class MyClass {

        protected String method() {
            throw new NullPointerException();
        }
    }

    @Test
    public void whenThenReturn_vs_doReturnWhenCase1() {

        MyClass myClass = spy(MyClass.class);
        // would work fine
        doReturn("test").when(myClass).method();
        System.out.println(myClass.method());
    }

    @Test(expected = NullPointerException.class)
    public void whenThenReturn_vs_doReturnWhenCase2() {

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
        assertEquals("sunil", itemsMock.get(0));
        assertEquals("sunil", itemsMock.get(11));
        assertEquals(isNull(), itemsMock.get(5));

    }

    @Test
    public void customMatcher(){

        ArgumentMatcher<Integer> even = new ArgumentMatcher<Integer>() {
            @Override
            public boolean matches(Integer index) {
                return index%2 == 0;
            }
        };

        ArgumentMatcher<Integer> odd = new ArgumentMatcher<Integer>() {
            @Override
            public boolean matches(Integer index) {
                return index%2 == 1;
            }
        };


        List<String> listMock = mock(List.class);

        // Note that argThat(org.mockito.ArgumentMatcher) will not
        // work with primitive int matchers due to NullPointerException auto-unboxing caveat.
//        when(listMock.get(argThat(odd))).thenReturn("Odd"); //<-- it will not work

        when(listMock.get(intThat(odd))).thenReturn("Odd");
        when(listMock.get(intThat(even))).thenReturn("Even");

        assertEquals(listMock.get(0), "Even");
        assertEquals(listMock.get(1), "Odd");
        assertEquals(listMock.get(2), "Even");
        assertEquals(listMock.get(3), "Odd");

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
    public void stubbingWithAnswer(){
        List<String> listMock = mock(List.class);

        when(listMock.get(anyInt()))
            .thenAnswer(new Answer<String>() {
                @Override
                public String answer(InvocationOnMock invocationOnMock) throws Throwable {
                    Arrays.stream(invocationOnMock.getArguments()).forEach(System.out::println);
                    return "Sunil";
                }
            });
        String name = listMock.get(100);
        MatcherAssert.assertThat(name, is(equalTo("Sunil"))); ;
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
        List<String> singleMock = mock(List.class);

        //using a single mock
        singleMock.add("was added first");
        singleMock.add("was added second");

        //create an inOrder verifier for a single mock
        InOrder inOrderA = inOrder(singleMock);

        //following will make sure that add is first called with "was added first", then with "was added second"
        inOrderA.verify(singleMock).add("was added first");
        inOrderA.verify(singleMock).add("was added second");

        // B. Multiple mocks that must be used in a particular order
        List<String> firstMock = mock(List.class);
        List<String> secondMock = mock(List.class);

        //using mocks
        firstMock.add("was called first");
        secondMock.add("was called second");

        //create inOrder object passing any mocks that need to be verified in order
        InOrder inOrderB = inOrder(firstMock, secondMock);

        //following will make sure that firstMock was called before secondMock
        inOrderB.verify(firstMock).add("was called first");
        inOrderB.verify(secondMock).add("was called second");


         //It will fail since order is wrong
//         inOrderB.verify(secondMock).add("was called second");
//         inOrderB.verify(firstMock).add("was called first");


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
        List<String> spy = spy(LinkedList.class);

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

    @Test(expected = IndexOutOfBoundsException.class)
    public void gotchaInSpyingWrongWay(){
        List<String> listSpy = spy(LinkedList.class);

        // Wrong way to stub spy
        when(listSpy.get(anyInt())).thenReturn("Sunil");

        System.out.println(listSpy.get(1));

        verify(listSpy).get(1);
    }

    @Test
    public void gotchaInSpyingRightWay(){
        List<String> listSpy = spy(LinkedList.class);

        // Right way to stub spy
        doReturn("Sunil").when(listSpy).get(1);

        Assert.assertEquals("Sunil", listSpy.get(1));
    }


    @Captor
    private ArgumentCaptor<Email> emailCaptor;

    @Mock
    private Platform platform;

    @InjectMocks
    private EmailService emailService;

    @Test
    public void argumentCaptor(){

        String to = "sks.256@gmail.com";
        String subject = "Using ArgumentCaptor";
        String body = "Hey, let'use ArgumentCaptor";

        emailService.sendEmail(to, subject, body, true);

        verify(platform).sendEmail(emailCaptor.capture());

        assertEquals(Format.HTML, emailCaptor.getValue().getFormat());
    }

    @Test
    public void resetMock(){
        //given
        List<String> listMock = mock(List.class);
        when(listMock.get(1)).thenReturn("Sunil");

        //when
        System.out.println(listMock.get(1)); // this will print Sunil

        reset(listMock);

        //when
        System.out.println(listMock.get(1)); //this will print null

    }

    @Test
    public void BDDAliases(){
        //given
        List<String> listMock = mock(List.class);
        given(listMock.get(1)).willReturn("BDD : Sunil");

        //when
        System.out.println(listMock.get(1));

        //then
        verify(listMock).get(1);
    }

}
