package ch15generic;

/**
 * Created by DengWenzhe on 3/5/17.
 */
public class LinkedStack<T> {
    // Inner helper class
    private class Node {
        T item;
        Node next;

        Node() { }

        Node(T item, Node next) {
            this.item = item;
            this.next = next;
        }

//        boolean isEnd() {
//            return item == null && next == null;
//        }

        @Override
        public String toString() {
            return "Node " + item;
        }

    }

    Node top = null;
    int size = 0;

    public void push(T item) {
        Node newNode = new Node(item, top);
        top = newNode;
        size++;
    }

    public T pop() {
        if (isEmpty())
            return null;

        T result = top.item;
        top = top.next;

        size--;
        return result;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public int size() {
        return size;
    }
}

class LinkedStackTester {
    public static void main(String[] args) {
        String[] arr = "a b c d e f gg".split(" ");
        LinkedStack<String> stack = new LinkedStack<>();
        for (String s: arr) {
            stack.push(s);
        }

        while (!stack.isEmpty()) {
            System.out.println(stack.pop());
        }
    }
}
