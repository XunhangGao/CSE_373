package problems;

import datastructures.LinkedIntList;
// Checkstyle will complain that this is an unused import until you use it in your code.
import datastructures.LinkedIntList.ListNode;

//import java.util.Iterator;

/**
 * See the spec on the website for example behavior.
 *
 * REMEMBER THE FOLLOWING RESTRICTIONS:
 * - do not call any methods on the `LinkedIntList` objects.
 * - do not construct new `ListNode` objects for `reverse3` or `firstToLast`
 *      (though you may have as many `ListNode` variables as you like).
 * - do not construct any external data structures such as arrays, queues, lists, etc.
 * - do not mutate the `data` field of any node; instead, change the list only by modifying
 *      links between nodes.
 */

public class LinkedIntListProblems {

    /**
     * Reverses the 3 elements in the `LinkedIntList` (assume there are exactly 3 elements).
     */
    public static void reverse3(LinkedIntList list) {
        list.front.next.next.next = list.front.next;
        list.front.next = list.front.next.next;
        list.front.next.next.next = null;
        list.front.next.next.next = list.front;
        list.front = list.front.next;
        list.front.next.next.next = null;
    }

    /**
     * Moves the first element of the input list to the back of the list.
     */
    public static void firstToLast(LinkedIntList list) {
        if (list.front != null) {
            ListNode cur = list.front;
            while (cur.next != null) {
                cur = cur.next;
            }
            cur.next = list.front;
            list.front = list.front.next;
            cur.next.next = null;
        }
    }

    /**
     * Returns a list consisting of the integers of a followed by the integers
     * of n. Does not modify items of A or B.
     */
    public static LinkedIntList concatenate(LinkedIntList a, LinkedIntList b) {
        LinkedIntList temp = new LinkedIntList();
        if (a.front != null && b.front != null) {
            temp.front = new ListNode(a.front.data);
            ListNode cur1 = temp.front;
            ListNode cur2 = a.front;
            while (cur2.next != null) {
                cur2 = cur2.next;
                cur1.next = new ListNode(cur2.data);
                cur1 = cur1.next;
            }
            cur1.next = b.front;
        }
        else if (a.front == null) {
            temp.front = b.front;
        }
        else {
            temp.front = a.front;
        }
        return temp;
    }
}
