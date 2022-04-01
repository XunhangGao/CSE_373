package problems;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * See the spec on the website for example behavior.
 */
public class MapProblems {

    /**
     * Returns true if any string appears at least 3 times in the given list; false otherwise.
     */
    public static boolean contains3(List<String> list) {
        Map<String, Integer> temp = new HashMap<>();
        for (String word : list) {
            if (!temp.containsKey(word)) {
                temp.put(word, 1);
            }
            else {
                temp.put(word, temp.get(word) + 1);
            }
        }
        for (String word : temp.keySet()) {
            if (temp.get(word) >= 3) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a map containing the intersection of the two input maps.
     * A key-value pair exists in the output iff the same key-value pair exists in both input maps.
     */
    public static Map<String, Integer> intersect(Map<String, Integer> m1, Map<String, Integer> m2) {
        Map<String, Integer> temp = new HashMap<>();
        for (String word1 : m1.keySet()) {
            for (String word2 : m2.keySet()) {
                if (word1 == word2 && m1.get(word1) == m2.get(word2)) {
                    temp.put(word1, m1.get(word1));
                }
            }
        }
        return temp;
    }
}
