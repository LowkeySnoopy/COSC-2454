public class LinkedIteratorDiver {
    // Constructor for LinkedIteratorDiver
    public LinkedIteratorDiver() {
    }

    // Main method to demonstrate the functionality of SinglyLinkedList
    public static void main(String[] args) {
        // Create a new singly linked list
        SinglyLinkedList sl = new SinglyLinkedList();

        // Append nodes with different words to the list
        sl.append(new Node("You"));
        sl.append(new Node("Can"));
        sl.append(new Node("Do"));
        sl.append(new Node("Whatever"));
        sl.append(new Node("You"));
        sl.append(new Node("Put"));
        sl.append(new Node("Your"));
        sl.append(new Node("Mind"));
        sl.append(new Node("Too"));
        sl.append(new Node("!"));

        // Check if there is a next element in the list
        sl.hasNext();

        // Retrieve the next element in the list
        sl.getNext();

        // Print all elements in the list
        sl.printList();
    }
}

