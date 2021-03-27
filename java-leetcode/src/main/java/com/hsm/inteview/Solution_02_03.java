package com.hsm.inteview;

/**
 * @description:
 * @author: huangsm
 * @createDate: 2021/3/27
 */
public class Solution_02_03 {
    public void deleteNode(ListNode node) {
        ListNode next = node.next;
        node.val = node.next.val;
        node.next = node.next.next;
        next = null;
    }

}

 class ListNode {
    int val;
    ListNode next;
    ListNode(int x) { val = x; }
}