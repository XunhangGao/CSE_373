package deques;


/**
 * @see Deque
 */
public class LinkedDeque<T> extends AbstractDeque<T> {
    private int size;
    // IMPORTANT: Do not rename these fields or change their visibility.
    // We access these during grading to test your code.
    Node<T> front;
    Node<T> back;
    // Feel free to add any additional fields you may need, though.

    public LinkedDeque() {
        front = new Node<>(null);
        back = new Node<>(null);
        front.next = back;
        back.prev = front;
        size = 0;
    }

    public void addFirst(T item) {
        size += 1;
        Node<T> temp = new Node<>(item, front, front.next);
        front.next = temp;
        temp.next.prev = temp;
    }

    public void addLast(T item) {
        size += 1;
        Node<T> temp = new Node<>(item, back.prev, back);
        back.prev = temp;
        temp.prev.next = temp;
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        size -= 1;
        T temp = front.next.value;
        front.next = front.next.next;
        front.next.prev = front;
        return temp;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        size -= 1;
        T temp = back.prev.value;
        back.prev = back.prev.prev;
        back.prev.next = back;
        return temp;
    }

    public T get(int index) {
        if ((index >= size) || (index < 0)) {
            return null;
        }
        Node<T> cur = this.front;
        if (index <= size - index) {
            for (int i = 0; i < index + 1; i += 1) {
                cur = cur.next;
            }
        }
        else {
            cur = this.back;
            for (int i = size - 1; i >= index; i -= 1) {
                cur = cur.prev;
            }
        }
        return cur.value;
    }

    public int size() {
        return size;
    }
}
