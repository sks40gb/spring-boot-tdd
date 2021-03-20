package com.sunil;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.List;

import static org.mockito.Mockito.*;

public class MockitoTest {

    @Test
    public void verifySomeBehavior(){
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
    public void someStabbing(){

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
}
