public class SinglyLinkedList<T> implements LinkedIterator<T> {
    // Head node of the linked list
    Node head = null;
    int size = 0; // Tracks the number of nodes in the list

    // Default constructor
    SinglyLinkedList() {
    }

    // Returns the current size of the list
    public int size() {
        return this.size;
    }

    // Adds a new node at the beginning of the list
    public void add(Node newNode) {
        if (newNode != null) {
            newNode.next = this.head;
            this.head = newNode;
            ++this.size;
        } else {
            System.out.println("Can't add the new node, it's null");
        }
    }

    // Inserts a new node after a given node
    public void insertAfter(Node prevNode, Node newNode) {
        if (prevNode == null) {
            System.out.println("Previous node does not exist");
        } else {
            newNode.next = prevNode.next;
            prevNode.next = newNode;
            ++this.size;
        }
    }

    // Appends a new node at the end of the list
    public void append(Node newNode) {
        if (this.head == null) {
            this.add(newNode);
        } else {
            newNode.next = null;

            Node last;
            for (last = this.head; last.next != null; last = last.next) {
            }

            last.next = newNode;
            ++this.size;
        }
    }

    // Removes the last node from the list
    public void remove() {
        if (this.head != null) {
            if (this.head.next == null) {
                this.head = null; // If only one node exists, remove it
            } else {
                Node previous = this.head;
                for (Node last = this.head.next; last.next != null; last = last.next) {
                    previous = previous.next;
                }
                previous.next = null; // Remove the last node
            }
            --this.size;
        }
    }

    // Removes a node at a specific index
    public void remove(int index) {
    }

    // Returns the next element in the list
    public T getNext() {
        if (!this.hasNext()) {
            return null;
        } else {
            Node currentNode = this.head;
            T data = (T) currentNode.data;
            currentNode = currentNode.next;
            return data;
        }
    }

    // Checks if there is a next node in the list
    public boolean hasNext() {
        return this.head != null;
    }

    // Resets the list by clearing all nodes
    public void reset() {
        this.clear();
    }

    // Clears the linked list by setting head to null and resetting size
    public void clear() {
        this.head = null;
        this.size = 0;
    }

    // Reverses the linked list
    public void reverse() {
        Node previous = null;
        Node next;
        while (this.head != null) {
            next = this.head.next;
            this.head.next = previous;
            previous = this.head;
            this.head = next;
        }
        this.head = previous;
    }

    // Prints all elements of the linked list
    public void printList() {
        if (this.head == null) {
            System.out.println("The list is empty");
        } else {
            for (Node it = this.head; it != null; it = it.next) {
                System.out.println(String.valueOf(it.data) + " ");
            }
        }
    }

    // Detects if the linked list has a cycle
    public boolean isCyclic() {
        Node slow = this.head;
        Node fast = this.head;

        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                return true; // Cycle detected
            }
        }
        return false; // No cycle detected
    }
}
