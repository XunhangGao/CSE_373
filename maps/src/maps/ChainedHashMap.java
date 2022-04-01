package maps;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * @see AbstractIterableMap
 * @see Map
 */
public class ChainedHashMap<K, V> extends AbstractIterableMap<K, V> {
    private static final double DEFAULT_RESIZING_LOAD_FACTOR_THRESHOLD = 0.5;
    private static final int DEFAULT_INITIAL_CHAIN_COUNT = 10;
    private static final int DEFAULT_INITIAL_CHAIN_CAPACITY = 10;
    private double resizeCoeff;
    private int size;

    /*
    Warning:
    You may not rename this field or change its type.
    We will be inspecting it in our secret tests.
     */
    AbstractIterableMap<K, V>[] chains;

    // You're encouraged to add extra fields (and helper methods) though!

    /**
     * Constructs a new ChainedHashMap with default resizing load factor threshold,
     * default initial chain count, and default initial chain capacity.
     */
    public ChainedHashMap() {
        this(DEFAULT_RESIZING_LOAD_FACTOR_THRESHOLD, DEFAULT_INITIAL_CHAIN_COUNT, DEFAULT_INITIAL_CHAIN_CAPACITY);
    }

    /**
     * Constructs a new ChainedHashMap with the given parameters.
     *
     * @param resizingLoadFactorThreshold the load factor threshold for resizing. When the load factor
     *                                    exceeds this value, the hash table resizes. Must be > 0.
     * @param initialChainCount the initial number of chains for your hash table. Must be > 0.
     * @param chainInitialCapacity the initial capacity of each ArrayMap chain created by the map.
     *                             Must be > 0.
     */
    public ChainedHashMap(double resizingLoadFactorThreshold, int initialChainCount, int chainInitialCapacity) {
        this.chains = this.createArrayOfChains(initialChainCount);
        resizeCoeff = resizingLoadFactorThreshold;
        size = 0;
    }

    /**
     * This method will return a new, empty array of the given size that can contain
     * {@code AbstractIterableMap<K, V>} objects.
     *
     * Note that each element in the array will initially be null.
     *
     * Note: You do not need to modify this method.
     * @see ArrayMap createArrayOfEntries method for more background on why we need this method
     */
    @SuppressWarnings("unchecked")
    private AbstractIterableMap<K, V>[] createArrayOfChains(int arraySize) {
        return (AbstractIterableMap<K, V>[]) new AbstractIterableMap[arraySize];
    }

    /**
     * Returns a new chain.
     *
     * This method will be overridden by the grader so that your ChainedHashMap implementation
     * is graded using our solution ArrayMaps.
     *
     * Note: You do not need to modify this method.
     */
    protected AbstractIterableMap<K, V> createChain(int initialSize) {
        return new ArrayMap<>(initialSize);
    }

    @Override
    public V get(Object key) {
        int temp = 0;
        if (key != null) {
            temp = Math.abs(key.hashCode()) % chains.length;
        }
        if (chains[temp] != null) {
            return chains[temp].get(key);
        }
        return null;
    }

    @Override
    public V put(K key, V value) {
        if ((this.size + 1)/chains.length >= resizeCoeff) {
            AbstractIterableMap<K, V>[] newChains = this.createArrayOfChains(chains.length * 2);
            for (int i = 0; i < chains.length; i++) {
                if (chains[i] != null) {
                    for (K tempKey : chains[i].keySet()) {
                        V tempValue = chains[i].get(tempKey);
                        int temp = 0;
                        if (tempKey != null) {
                            temp = Math.abs(tempKey.hashCode()) % newChains.length;
                        }
                        if (newChains[temp] == null) {
                            newChains[temp] = this.createChain(DEFAULT_INITIAL_CHAIN_CAPACITY);
                        }
                        newChains[temp].put(tempKey, tempValue);
                    }
                }
            }
            this.chains = newChains;
        }
        int temp = 0;
        if (key != null) {
            temp = Math.abs(key.hashCode()) % chains.length;
        }
        if (chains[temp] == null) {
            chains[temp] = this.createChain(DEFAULT_INITIAL_CHAIN_CAPACITY);
        }
        if (!chains[temp].containsKey(key)) {
            size++;
        }
        return chains[temp].put(key, value);
    }

    @Override
    public V remove(Object key) {
        int temp = 0;
        if (key != null) {
            temp = Math.abs(key.hashCode()) % chains.length;
        }
        V tempValue = null;
        if (chains[temp] != null && chains[temp].containsKey(key)) {
            tempValue = chains[temp].remove(key);
            if (chains[temp].size() == 0) {
                chains[temp] = null;
            }
            size = size - 1;
        }
        return tempValue;
    }

    @Override
    public void clear() {
        AbstractIterableMap<K, V>[] newChains = this.createArrayOfChains(DEFAULT_INITIAL_CHAIN_COUNT);
        this.chains = newChains;
        size = 0;
    }

    @Override
    public boolean containsKey(Object key) {
        int temp = 0;
        if (key != null) {
            temp = Math.abs(key.hashCode()) % chains.length;
        }
        if (chains[temp] == null) {
            return false;
        }
        return chains[temp].containsKey(key);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        // Note: you won't need to change this method (unless you add more constructor parameters)
        return new ChainedHashMapIterator<>(this.chains);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    /*
    See the assignment webpage for tips and restrictions on implementing this iterator.
     */
    private static class ChainedHashMapIterator<K, V> implements Iterator<Map.Entry<K, V>> {
        private AbstractIterableMap<K, V>[] chains;
        private int chainPosition;
        private AbstractIterableMap<K, V> currentChain;
        private Iterator<Map.Entry<K, V>> iterator;
        // You may add more fields and constructor parameters

        public ChainedHashMapIterator(AbstractIterableMap<K, V>[] chains) {
            this.chains = chains;
        }

        @SuppressWarnings("checkstyle:WhitespaceAround")
        @Override
        public boolean hasNext() {
            if (chains == null) {
                return false;
            }

            if (currentChain != null && iterator.hasNext()) {
                return true;
            }
            for (int i = chainPosition; i < chains.length; i++) {
                chainPosition = i;
                if (chains[chainPosition] != null) {
                    currentChain = chains[chainPosition];
                    iterator = currentChain.iterator();
                    chainPosition++;
                    return iterator.hasNext();
                }
            }
            return false;
        }

        @Override
        public Map.Entry<K, V> next() {
            if (chains == null) {
                return null;
            }
            if (currentChain == null || !iterator.hasNext()) {
                throw new NoSuchElementException();
            }

            if (currentChain != null && iterator.hasNext()) {
                return iterator.next();
            }
            for (int i = chainPosition; i < chains.length; i++) {
                chainPosition = i;
                if (chains[chainPosition] != null) {
                    currentChain = chains[chainPosition];
                    iterator = currentChain.iterator();
                    chainPosition++;
                    return iterator.next();
                }
            }
            return null;
        }
    }
}
