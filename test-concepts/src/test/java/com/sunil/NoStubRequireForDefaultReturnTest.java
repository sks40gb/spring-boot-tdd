package com.sunil;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(MockitoJUnitRunner.class) //<-- This will is required ese it will fail
public class NoStubRequireForDefaultReturnTest {


    @Mock
    Animal animal;

    @Test
    public void whenAnimalNotInitiated_shouldBeSuccessful() {
        assertEquals(0, animal.getId());
        assertNull(animal.getName());
    }

    interface Animal {
        int getId();

        String getName();
    }

}
