package ch17containers;

/**
 * Created by DengWenzhe on 3/19/17.
 */
public class Ex8 {
    public static void main(String[] args) {
        Node<String> node1 = new Node("node1");
        Node<String> node2 = new Node("node2");
        Node<String> node3 = new Node("node3");

        SList<String> sList = new SList<>();
        SListIterator<String> iter = sList.listIterator();
        iter.insert("1");
        iter.insert("2");
        iter.insert("3");
        iter.insert("4");

        System.out.println(sList);

        iter = sList.listIterator();
        iter.remove();
        System.out.println(sList);

        iter.remove();
        System.out.println(sList);
    }
}
class SList<E> {
    Node<E> dummyHead = new Node(null);

    SListIterator<E> listIterator() {
        return new SListIterator(dummyHead);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        SListIterator<E> iter = listIterator();
        while (iter.hasNext()) {
            sb.append("Node ");
            sb.append(iter.next());
            sb.append(", ");
        }

        return sb.toString();
    }
}

class SListIterator<E> {
    Node<E> current = null;
    public SListIterator(Node<E> node) {
        current = node;
    }

    public boolean hasNext() {
        return current.next != null;
    }

    public E next() {
        current = current.next;
        return current.val;
    }

    public void insert(E element) {
        current.next = new Node(element, current.next);
        current = current.next;
    }

    public void remove() {
        if (current.next != null)
            current.next = current.next.next;
    }
}

class Node<E> {
    E val;
    Node next;

    public Node(E val) {
        this.val = val;
    }

    public Node(E val, Node next) {
        this.val = val;
        this.next = next;
    }
}
