/**
 * @param <T> The type of the satellite data of the elements in the dynamic-set.
 */
public class MyDynamicSet<T> {
    /*
     * You may add any fields that you wish to add.
     * Remember that the use of built-in Java classes is not allowed,
     * the only variables types you can use are:
     * 	-	the given classes in the assignment
     * 	-	basic arrays
     * 	-	primitive variables
     */

    private ListLink<T> head;
    private ListLink<T> tail;


    /**
     * The constructor should initiate an empty dynamic-set.
     *
     * @param N The maximum number of elements in the dynamic set at each time.
     */
    public MyDynamicSet(int N) {

    }

    public Element<T> search(int k) {
        ListLink<T> currentrent = head;
        while (currentrent != null) {
            if (currentrent.key() == k) {
                return currentrent;
            }
            currentrent = currentrent.getNext();
        }
        return null;
    }

    public void insert(Element<T> x) {
        ListLink<T> newNode = new ListLink<T>(x);

        if (head == null) {
            head = newNode;
            tail = newNode;
            return;
        }

        ListLink<T> current = head;
        while (current != null && current.key() < newNode.key()) {
            current = current.getNext();
        }

        if (current == head) {
            // insert at head
            newNode.setNext(head);
            head.setPrev(newNode);
            head = newNode;
        } else if (current == null) {
            // insert at tail
            tail.setNext(newNode);
            newNode.setPrev(tail);
            tail = newNode;
        } else {
            // insert in the middle
            ListLink<T> prev = current.getPrev();
            prev.setNext(newNode);
            newNode.setPrev(prev);
            newNode.setNext(current);
            current.setPrev(newNode);
        }
    }

    public void delete(Element<T> x) {
        ListLink<T> element = (ListLink<T>) x;
        if (element == head && element == tail) {
            head = null;
            tail = null;
        } else if (element == head) {
            head = head.getNext();
            head.setPrev(null);
        } else if (element == tail) {
            tail = tail.getPrev();
            tail.setNext(null);
        } else if (!(element.getNext() == null && element.getPrev() == null)){
            element.getPrev().setNext(element.getNext());
            element.getNext().setPrev(element.getPrev());
        }

        element.setPrev(null);
        element.setNext(null);
    }

    public Element<T> minimum() {
        return head;
    }

    public Element<T> maximum() {
        return tail;
    }

    public Element<T> successor(Element<T> x) {
        return ((ListLink<T>) x).getNext();
    }

    public ListLink<T> getLinkForKey(int key) {
        ListLink<T> current = this.head;
        while (current != null) {
            if (current.key() == key)
                return current;
            current = current.getNext();
        }
        return null;
    }

    public Element<T> predecessor(Element<T> x) {
        return ((ListLink<T>) x).getPrev();
    }
}
