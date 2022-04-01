package maps;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * @see AbstractIterableMap
 * @see Map
 */
public class ArrayMap<K, V> extends AbstractIterableMap<K, V> {
    private static final int DEFAULT_INITIAL_CAPACITY = 10;
    private int size;
    /*
    Warning:
    You may not rename this field or change its type.
    We will be inspecting it in our secret tests.
     */
    SimpleEntry<K, V>[] entries;

    // You may add extra fields or helper methods though!

    /**
     * Constructs a new ArrayMap with default initial capacity.
     */
    public ArrayMap() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    /**
     * Constructs a new ArrayMap with the given initial capacity (i.e., the initial
     * size of the internal array).
     *
     * @param initialCapacity the initial capacity of the ArrayMap. Must be > 0.
     */
    public ArrayMap(int initialCapacity) {
        this.entries = this.createArrayOfEntries(initialCapacity);
        size = 0;
    }

    /**
     * This method will return a new, empty array of the given size that can contain
     * {@code Entry<K, V>} objects.
     *
     * Note that each element in the array will initially be null.
     *
     * Note: You do not need to modify this method.
     */
    @SuppressWarnings("unchecked")
    private SimpleEntry<K, V>[] createArrayOfEntries(int arraySize) {
        /*
        It turns out that creating arrays of generic objects in Java is complicated due to something
        known as "type erasure."

        We've given you this helper method to help simplify this part of your assignment. Use this
        helper method as appropriate when implementing the rest of this class.

        You are not required to understand how this method works, what type erasure is, or how
        arrays and generics interact.
        */
        return (SimpleEntry<K, V>[]) (new SimpleEntry[arraySize]);
    }

    @SuppressWarnings("checkstyle:EmptyBlock")
    @Override
    public V get(Object key) {
        if (size == 0) {
            return null;
        }
        for (int i = 0; i < size; i++) {
            if (Objects.equals(key, entries[i].getKey())) {
                return entries[i].getValue();
            }
        }
        return null;
    }

    @Override
    public V put(K key, V value) {
        if (!this.containsKey(key)) {
            entries[size] = new SimpleEntry<>(key, value);
            size += 1;
            if (size == entries.length) {
                SimpleEntry<K, V>[] temp  = this.createArrayOfEntries(size * 2);
                for (int i = 0; i < size; i++) {
                    temp[i] = entries[i];
                }
                this.entries = temp;
            }
            return null;
        }
        else {
            V temp = null;
            for (int i = 0; i < size; i++) {
                if (Objects.equals(key, entries[i].getKey())) {
                    temp = entries[i].getValue();
                    entries[i] = new SimpleEntry<K, V>(key, value);
                    return temp;
                }
            }
            return temp;
        }
    }

    @Override
    public V remove(Object key) {
        if (!this.containsKey(key)) {
            return null;
        }
        else {
            V temp = null;
            for (int i = 0; i < size; i++) {
                if (Objects.equals(key, entries[i].getKey())) {
                    temp = entries[i].getValue();
                    entries[i] = entries[size - 1];
                    entries[size - 1] = null;
                    size = size - 1;
                    return temp;
                }
            }
            return temp;
        }
    }

    @Override
    public void clear() {
        SimpleEntry<K, V>[] temp = this.createArrayOfEntries(entries.length);
        this.entries = temp;
        size = 0;
    }

    @Override
    public boolean containsKey(Object key) {
        if (size == 0) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (Objects.equals(key, entries[i].getKey())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        // Note: You may or may not need to change this method, depending on whether you
        // add any parameters to the ArrayMapIterator constructor.
        return new ArrayMapIterator<>(this.entries);
    }

    // Doing so will give you a better string representation for assertion errors the debugger.
    @Override
    public String toString() {
        return super.toString();
    }

    private static class ArrayMapIterator<K, V> implements Iterator<Map.Entry<K, V>> {
        private final SimpleEntry<K, V>[] entries;
        private int position;
        // You may add more fields and constructor parameters

        public ArrayMapIterator(SimpleEntry<K, V>[] entries) {
            this.entries = entries;
        }

        @Override
        public boolean hasNext() {
            if (entries == null) {
                return false;
            }
            return entries[position] != null;
        }

        @Override
        public Map.Entry<K, V> next() {
            if (entries == null) {
                return null;
            }
            position += 1;
            if (entries[position - 1] == null) {
                throw new NoSuchElementException();
            }
            return entries[position - 1];
        }
    }
}
