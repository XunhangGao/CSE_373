package disjointsets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A quick-union-by-size data structure with path compression.
 * @see DisjointSets for more documentation.
 */
public class UnionBySizeCompressingDisjointSets<T> implements DisjointSets<T> {
    // Do NOT rename or delete this field. We will be inspecting it directly in our private tests.
    List<Integer> pointers;
    private HashMap<T, Integer> vertices;

    /*
    However, feel free to add more fields and private helper methods. You will probably need to
    add one or two more fields in order to successfully implement this class.
    */

    public UnionBySizeCompressingDisjointSets() {
        pointers = new ArrayList<>();
        vertices = new HashMap<>();
    }

    @Override
    public void makeSet(T item) {
        vertices.put(item, pointers.size());
        pointers.add(-1);
    }

    @Override
    public int findSet(T item) {
        if (!vertices.containsKey(item)) {
            throw new IllegalArgumentException();
        }
        ArrayList<Integer> storeIndex = new ArrayList<>();
        int index = vertices.get(item);
        int temp = 0;
        while (index >= 0) {
            temp = index;
            storeIndex.add(temp);
            index = pointers.get(index);
        }
        for (int i = 0; i < storeIndex.size() - 1; i++) {
            pointers.set(storeIndex.get(i), temp);
        }
        return temp;
    }

    @Override
    public boolean union(T item1, T item2) {
        int index1 = findSet(item1);
        int index2 = findSet(item2);
        if (index1 == index2) {
            return false;
        }

        int weight1 = pointers.get(index1);
        int weight2 = pointers.get(index2);
        if (weight1 <= weight2) {
            pointers.set(index1, weight1 + weight2);
            pointers.set(index2, index1);
        }
        else {
            pointers.set(index2, weight1 + weight2);
            pointers.set(index1, index2);
        }
        return true;
    }
}
