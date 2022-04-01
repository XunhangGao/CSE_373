package priorityqueues;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * @see ExtrinsicMinPQ
 */
public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    // IMPORTANT: Do not rename these fields or change their visibility.
    // We access these during grading to test your code.
    static final int START_INDEX = 1;
    List<PriorityNode<T>> items;
    Map<T, Integer> checkItems;
    private int size;

    public ArrayHeapMinPQ() {
        items = new ArrayList<>();
        checkItems = new HashMap<>();
        items.add(0, new PriorityNode<>(null, 0.0));
        size = 0;
    }

    // Here's a method stub that may be useful. Feel free to change or remove it, if you wish.
    // You'll probably want to add more helper methods like this one to make your code easier to read.
    /**
     * A helper method for swapping the items at two indices of the array heap.
     */
    private void swap(int one, int two) {
        PriorityNode<T> temp1 = items.get(one);
        PriorityNode<T> temp2 = items.get(two);

        checkItems.put(temp1.getItem(), two);
        checkItems.put(temp2.getItem(), one);

        items.set(one, temp2);
        items.set(two, temp1);
    }

    private void swapUp(int cur, int par) {
        if (this.items.get(cur).getPriority() < this.items.get(par).getPriority()) {
            swap(par, cur);
            if (par > 1) {
                swapUp(par, par / 2);
            }
        }
    }

    @Override
    public void add(T item, double priority) {
        if (checkItems.containsKey(item)) {
            throw new IllegalArgumentException();
        }
        else {
            size++;
            items.add(new PriorityNode<>(item, priority));
            checkItems.put(item, size);
            if (size > 1) {
                swapUp(size, size / 2);
            }
        }
    }

    @Override
    public boolean contains(T item) {
        if (this.items == null || size == 0) {
            return false;
        }
        else {
            return checkItems.containsKey(item);
        }
    }

    @Override
    public T peekMin() {
        if (this.items == null || size == 0) {
            //return null;
            throw new NoSuchElementException();
        }
        else {
            return this.items.get(START_INDEX).getItem();
        }
    }

    @Override
    public T removeMin() {
        if (this.items == null || size == 0) {
            //return null;
            throw new NoSuchElementException();
        }
        else {
            PriorityNode<T> temp = items.set(START_INDEX, items.get(size));
            checkItems.remove(temp.getItem());
            size = size - 1;
            if (size > 0) {
                checkItems.put(items.get(size + 1).getItem(), 1);
                items.remove(size + 1);
                swapDown(START_INDEX);
            }
            return temp.getItem();
        }
    }

    private void swapDown(int cur) {
        if (2 * cur <= size) {
            if (2 * cur + 1 <= size) {
                double check = items.get(2 * cur).getPriority() - items.get(2 * cur + 1).getPriority();
                if (check <= 0) {
                    if (items.get(cur).getPriority() > items.get(2 * cur).getPriority()) {
                        swap(cur, 2 * cur);
                        swapDown(2 * cur);
                    }
                }
                else {
                    if (items.get(cur).getPriority() > items.get(2 * cur + 1).getPriority()) {
                        swap(cur, 2 * cur + 1);
                        swapDown(2 * cur + 1);
                    }
                }
            }
            else {
                if (items.get(cur).getPriority() > items.get(2 * cur).getPriority()) {
                    swap(cur, 2 * cur);
                    swapDown(2 * cur);
                }
            }
        }
    }

    @Override
    public void changePriority(T item, double priority) {
        if (!checkItems.containsKey(item)) {
            throw new NoSuchElementException();
        }
        else {
            int index = checkItems.get(item);
            double initialPriority = items.get(index).getPriority();
            if (priority != initialPriority) {
                items.get(index).setPriority(priority);
                if (size > 0) {
                    if (index != 1 && priority < initialPriority) {
                        swapUp(index, index / 2);
                    } else if (index < size && priority > initialPriority) {
                        swapDown(index);
                    }
                }
            }
        }
    }

    @Override
    public int size() {
        return size;
    }
}
