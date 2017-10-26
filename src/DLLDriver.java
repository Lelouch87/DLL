public class DLLDriver {
    /**
     * Test Driver for the DoubleLinkedList
     * @param args command line args (unused)
     */
    public static void main(String[] args) {

        DoubleLinkedList<Integer> list1 = new DoubleLinkedList<>();

        list1.addFirst(3);
        list1.addFirst(2);
        list1.addFirst(1);

        list1.addLast(4);

        System.out.println(list1.toString());

        System.out.println("The third element in the list is "+list1.get(2));

        System.out.println("The first element of the list is "+list1.getFirst());

        System.out.println("The last element of the list is "+list1.getLast());

        System.out.println("The size of the list is "+list1.size());

        list1.add(4, 5);

        list1.remove(5);

        System.out.println("Removed '5'");
        System.out.println(list1.toString());
    }
}
