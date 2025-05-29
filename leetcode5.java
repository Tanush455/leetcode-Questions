/**
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode() {}
 * ListNode(int val) { this.val = val; }
 * ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode partition(ListNode head, int x) {
        ListNode dummyNode1 = new ListNode(0); 
        ListNode dummyNode2 = new ListNode(0); 
        ListNode current1 = dummyNode1;
        ListNode current2 = dummyNode2;
        ListNode temp = head;

        while (temp != null) {
            if (temp.val < x) {
                current1.next = temp;
                current1 = current1.next;
            } else {
                current2.next = temp;
                current2 = current2.next;
            }
            temp = temp.next;
        }

        current2.next = null; 
        current1.next = dummyNode2.next; 

        return dummyNode1.next;
    }
}