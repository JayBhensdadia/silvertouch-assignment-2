import java.util.HashMap;

class Node {
    int data;
    int both;

    public Node(int data) {
        this.data = data;
        this.both = 0;
    }
}

class XORLinkedList {
    private Node head;
    private Node tail;
    private HashMap<Integer, Node> nodes;

    public XORLinkedList() {
        this.head = this.tail = null;
        this.nodes = new HashMap<>();
    }

    private int _xor(int a, int b) {
        return a ^ b;
    }

    public void insertAtHead(int data) {
        Node newNode = new Node(data);
        int newId = System.identityHashCode(newNode);
        nodes.put(newId, newNode);

        if (head != null) {
            newNode.both = _xor(0, System.identityHashCode(head));
            head.both = _xor(newNode.both, System.identityHashCode(head));
        } else {
            tail = newNode;
        }
        head = newNode;
    }

    public void insertAtTail(int data) {
        Node newNode = new Node(data);
        int newId = System.identityHashCode(newNode);
        nodes.put(newId, newNode);

        if (tail != null) {
            newNode.both = _xor(System.identityHashCode(tail), 0);
            tail.both = _xor(newNode.both, System.identityHashCode(tail));
        } else {
            head = newNode;
        }
        tail = newNode;
    }

    public void deleteFromHead() {
        if (head != null) {
            int nextNodeId = _xor(0, head.both);
            Node nextNode = nodes.getOrDefault(nextNodeId, null);

            if (nextNode != null) {
                nextNode.both = _xor(System.identityHashCode(head), nextNode.both);
            } else {
                tail = null;
            }

            nodes.remove(System.identityHashCode(head));
            head = nextNode;
        }
    }

    public void deleteFromTail() {
        if (tail != null) {
            int prevNodeId = _xor(tail.both, 0);
            Node prevNode = nodes.getOrDefault(prevNodeId, null);

            if (prevNode != null) {
                prevNode.both = _xor(System.identityHashCode(tail), prevNode.both);
            } else {
                head = null;
            }

            nodes.remove(System.identityHashCode(tail));
            tail = prevNode;
        }
    }

    public void printList() {
        Node current = head;
        int prevId = 0;
        while (current != null) {
            System.out.print(current.data + " ");
            int nextId = _xor(prevId, current.both);
            prevId = System.identityHashCode(current);
            current = nodes.getOrDefault(nextId, null);
        }
        System.out.println();
    }

    
}


class Main {

    public static void main(String[] args) {
        XORLinkedList list = new XORLinkedList();
        list.insertAtHead(10);
        list.insertAtHead(20);
        list.insertAtTail(30);
        list.insertAtTail(40);
        list.printList(); 
        list.deleteFromHead();
        list.printList(); 
        list.deleteFromTail();
        list.printList(); 
    }
}