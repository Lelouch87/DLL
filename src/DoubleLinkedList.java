import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * DoubleLinkedList.java: An implementation of a doubly linked list and its Iterator
 * @author Chase Cullen
 * @version 1.0
 */
public class DoubleLinkedList<E>{
    //Data Items
    private int size = 0;
    private Node<E> head = null;
    private Node<E> tail = null;

    /**
     * Default Constructor
     */
    public DoubleLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Add a generic item to the list given an index
     * @param index the index the item will be added after
     * @param obj the object to be stored
     */
    public void add(int index, E obj) {
        DLLIterator temp = new DLLIterator(index);
        temp.add(obj);
    }

    /**
     * Gets a generic item given an index
     * @param index the index to return the item
     * @return the data in the iterator.next()
     */
    public E get(int index) {
        DLLIterator temp = new DLLIterator(index);
        return temp.next();
    }

    /**
     * Adds an item to the beginning of the list
     * @param item the item to be added
     */
    public void addFirst(E item) {
        DLLIterator temp = new DLLIterator(0);
        temp.add(item);
    }

    /**
     * Adds an item to the end of the list
     * @param item the item to be added
     */
    public void addLast(E item) {
        DLLIterator temp = new DLLIterator(size);
        temp.add(item);
    }

    /**
     * Gets the first item in the list
     * @return the head node
     */
    public E getFirst() {
        return head.data;
    }

    /**
     * Gets the last item in the list
     * @return the tail node
     */
    public E getLast() {
        return tail.data;
    }

    /**
     * Removes an item in the list based on an object
     * @param obj the object to be removed
     * @return true if the object was found and removed, false if not
     */
    public boolean remove(E obj) {
        DLLIterator temp = new DLLIterator(0);

        for (int i = 0; i < size; i++) {
            temp.next();
            if (temp.lastItemReturned.data == obj) {
                temp.remove();
                return true;
            }

        }
        return false;
    }

    /**
     * Accessor for size of the List
     * @return the size of the list
     */
    public int size() {
        return size;
    }

    /**
     * String representation of the double linked list
     * @return a string representation of the list
     */
    public String toString() {
        Node<E> nodeRef = head;
        String result = "";
        while(nodeRef != null) {
            result += nodeRef.data;
            if (nodeRef.next != null) {
                result += " ==> ";
            }
            nodeRef = nodeRef.next;
        }
        return result;
    }

    /**
     * Private inner class Node
     * @param <E> generic type
     */
    private static class Node<E> {
        //Data items
        private E data;
        private Node<E> next = null;
        private Node<E> prev = null;

        /**
         * Constructor
         * @param dataItem the data to be stored in this node
         */
        private Node(E dataItem) {
            data = dataItem;
        }
    }
    //End of Inner Class Node

    /**
     * Private inner class DLLIterator
     */
    private class DLLIterator implements ListIterator<E> {
        //Data items
        private Node<E> nextItem;
        private Node<E> lastItemReturned;
        private int index = 0;

        /**
         * Constructor
         * @param i, the length to iterate through the list
         */
        public DLLIterator(int i) {
            if (i < 0 || i > size) {
                throw new IndexOutOfBoundsException("Invalid index "+ i);
            }
            lastItemReturned = null;
            if (i == size) {
                index = size;
                nextItem = null;
            } else {
                nextItem = head;
                for(index = 0; index < i; index++) {
                    nextItem = nextItem.next;
                }
            }
        }

        /**
         * Steps over the next element in the list
         * @return the item that was stepped over
         */
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            lastItemReturned = nextItem;
            nextItem = nextItem.next;
            index++;
            return lastItemReturned.data;
        }

        /**
         * Removes the last Item that was stepped over
         */
        public void remove() {
            //Haven't used next() or previous()
            if (lastItemReturned == null) {
                throw new IllegalStateException();
            }
            //Front of the list
            if (lastItemReturned.prev == null) {
                head = lastItemReturned.next;
                nextItem.prev = head;
            }
            //End of the list
            else if (lastItemReturned.next == null) {
                tail = lastItemReturned.prev;
                lastItemReturned.prev.next = null;

            }
            //Middle of the list
            else {
                lastItemReturned.prev.next = lastItemReturned.next;
                nextItem.prev = lastItemReturned.prev;
            }
            size--;
            index--;
        }

        /**
         * Replaces the last item Returned by a call from next or previous to obj
         * @param obj the item to be set to the lastItemReturned
         */
        public void set(E obj) {
            if (lastItemReturned != null) {
                lastItemReturned.data = obj;
            } else {
                throw new IllegalStateException();
            }
        }

        /**
         * The next index of the list
         * @return the index + 1
         */
        public int nextIndex() {
            if (!hasNext()) {
                return size;
            } else {
                return index++;
            }
        }

        /**
         * The previous index of the list
         * @return the index - 1
         */
        public int previousIndex() {
            if (nextItem == head) {
                throw new IllegalStateException();
            } else {
                return index--;
            }
        }

        /**
         * Checks if the list has a next element
         * @return true if it does, false if not
         */
        public boolean hasNext() {
            return nextItem != null;
        }

        /**
         * Checks if the list has a previous element
         * @return true if it does, false if not
         */
        public boolean hasPrevious() {
            return (nextItem == null && size != 0) || nextItem.prev != null;
        }

        /**
         * Steps over the previous element in the list
         * @return the item that was stepped over
         */
        public E previous() {
            if(!hasPrevious()) {
                throw new NoSuchElementException();
            }
            if (nextItem == null) {
                nextItem = tail;
            } else {
                nextItem = nextItem.prev;
            }
            lastItemReturned = nextItem;
            index--;
            return lastItemReturned.data;
        }

        public void add(E obj) {
            //List is empty
            if (head == null) {
                head = new Node<E>(obj);
                tail = head;
            //One element in list
            } else if(nextItem == head) {
                Node<E> newNode = new Node<E>(obj);
                newNode.next = nextItem;
                nextItem.prev = newNode;
                head = newNode;
            } else if(nextItem == null) {
                Node<E> newNode = new Node<E>(obj);
                tail.next = newNode;
                newNode.prev = tail;
                tail = newNode;
            } else {
                Node<E> newNode = new Node<E>(obj);

                newNode.prev = nextItem.prev;
                nextItem.prev.next = newNode;
                newNode.next = nextItem;
                nextItem.prev = newNode;
            }
            size++;
            index++;
            lastItemReturned = null;
        }
    }
    //End of Inner Class DLLIterator
}
