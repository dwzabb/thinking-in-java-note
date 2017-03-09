package ch15generic;

import static org.junit.Assert.*;

import org.junit.Assert;

/**
 * Created by DengWenzhe on 3/5/17.
 */
public class LinkedStackTest {
    @org.junit.Test
    public void push() throws Exception {
        LinkedStack<String> stack = new LinkedStack<>();
        stack.push("a");
        stack.push("b");

        assertFalse(stack.isEmpty());
    }

    @org.junit.Test
    public void pop() throws Exception {
        LinkedStack<String> stack = new LinkedStack<>();
        stack.push("a");
        stack.push("b");

        assertEquals(2, stack.size());
    }

}