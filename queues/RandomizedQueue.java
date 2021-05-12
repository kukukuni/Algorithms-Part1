import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private class Node<Item> {
        public Item content = null;
        public Node<Item> prev = null;
        public Node<Item> next = null;
    }

    private int size = 0;
    private Node<Item> head;

    // construct an empty randomized queue
    public RandomizedQueue() {

    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException();

        Node<Item> newItem = new Node<Item>();
        Node<Item> oldItem;
        newItem.content = item;

        if (head != null) {
            oldItem = head;
            oldItem.prev = newItem;
            newItem.next = oldItem;
        }
        head = newItem;
        size += 1;
    }

    // remove and return a random item
    public Item dequeue() {
        if (size == 0)
            throw new NoSuchElementException();
        int randomNum = StdRandom.uniform(size);
        Node<Item> temp = head;
        for (int i = 0; i < randomNum; i++) {
            if (temp.next != null) {
                temp = temp.next;
            }
        }
        if (temp.prev != null && temp.next != null) {
            temp.next.prev = temp.prev;
            temp.prev.next = temp.next;
        } else if (temp.prev == null && temp.next != null) {
            temp.next.prev = null;
            head = temp.next;
        } else if (temp.prev != null && temp.next == null) {
            temp.prev.next = null;
        } else {
            head = null;
        }
        size -= 1;
        return temp.content;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (size == 0)
            throw new NoSuchElementException();
        int randomNum = StdRandom.uniform(size);
        Node<Item> temp = head;
        for (int i = 0; i < randomNum; i++) {
            if (temp.next != null) {
                temp = temp.next;
            }
        }
        Item sample = temp.content;
        return sample;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RanqIterator();
    }

    private class RanqIterator implements Iterator<Item> {
        public Node<Item> current = head;
        private Item[] r;

        public RanqIterator() {
            copyItems();
            StdRandom.shuffle(r);
        }

        private void copyItems() {
            Node<Item> temp = head;
            r = (Item[]) new Object[size];
            for (int i = 0; i < size; i++) {
                r[i] = temp.content;
                temp = temp.next;
            }
        }

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
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();
        queue.size();
    }

}
