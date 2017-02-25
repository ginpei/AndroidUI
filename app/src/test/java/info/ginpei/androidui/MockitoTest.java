package info.ginpei.androidui;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MockitoTest {

    @Test
    public void nowYouCanVerifyInteractions() {
        // mock creation
        List mockedList = mock(List.class);

        // using mock object - it does not throw any "unexpected interaction" exception
        mockedList.add("one");
        mockedList.clear();

        // selective, explicit, highly readable verification
        verify(mockedList).add("one");
        verify(mockedList).clear();
    }

    @Test
    public void andStubMethodCalls() {
        // you can mock concrete classes, not only interfaces
        LinkedList mockedList = mock(LinkedList.class);

        // stubbing appears before the actual execution
        when(mockedList.get(0)).thenReturn("first");

        // the following prints "first"
        System.out.println(mockedList.get(0));

        // the following prints "null" because get(999) was not stubbed
        System.out.println(mockedList.get(999));
    }

    @Test
    public void mocking() {
        ArrayList<String> strings = mock(ArrayList.class);

        strings.add("One");
        strings.add("Two");

        doReturn("2").when(strings).get(1);

        assertEquals(0, strings.size());  // methods are just dummies
        assertEquals(null, strings.get(0));
        assertEquals("2", strings.get(1));  // replaced
    }

    @Test
    public void spying() {
        ArrayList<String> strings = spy(ArrayList.class);

        strings.add("One");
        strings.add("Two");

        doReturn("2").when(strings).get(1);

        assertEquals(2, strings.size());  // methods works correctly
        assertEquals("One", strings.get(0));
        assertEquals("2", strings.get(1));  // replaced

        strings.clear();
        assertEquals(0, strings.size());
    }
}
