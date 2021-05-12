/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private class Node<Item> {
        public Item content = null;
        public Node<Item> prev = null;
        public Node<Item> next = null;
    }

    private int size = 0;
    private Node<Item> head;
    private Node<Item> tail;

    // construct an empty deque
    public Deque() {
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null)
            throw new IllegalArgumentException();

        Node<Item> newItem = new Node<Item>();
        Node<Item> oldItem;
        newItem.content = item;

        if (head != null) {
            oldItem = head;
            oldItem.prev = newItem;
            newItem.next = oldItem;
        } else {
            tail = newItem;
        }
        head = newItem;
        size += 1;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException();

        Node<Item> newItem = new Node<Item>();
        Node<Item> oldItem;
        newItem.content = item;

        if (tail != null) {
            oldItem = tail;
            oldItem.next = newItem;
            newItem.prev = oldItem;
        } else {
            head = newItem;
        }
        tail = newItem;
        size += 1;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (size == 0)
            throw new NoSuchElementException();
        size -= 1;
        Node<Item> oldItem = head;
        head = head.next;
        if (size != 0) {
            head.prev = null;
        } else {
            tail = null;
        }
        return oldItem.content;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (size == 0)
            throw new NoSuchElementException();
        size -= 1;
        Node<Item> oldItem = tail;
        tail = tail.prev;
        if (size != 0) {
            tail.next = null;
        } else {
            head = null;
        }
        return oldItem.content;
    }


    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DeqIterator();
    }

    private class DeqIterator implements Iterator<Item> {
        private Node<Item> current = head;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (current == null) throw new NoSuchElementException();
            Item value = current.content;
            current = current.next;
            return value;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> queue = new Deque<>();
        queue.size();
    }
}
