public class Node<T>{
    T data;
    Node next;
    //Node prev;

    Node(T data){
        this.data = data;
        this.next = null;
    }

    //overloaded constructor for node class
    Node(T data, Node nextNode){
        this.data = data;
        this.next = nextNode;
    }
}
