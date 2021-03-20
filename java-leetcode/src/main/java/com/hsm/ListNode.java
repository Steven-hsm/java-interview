package com.hsm;

/**
 * @description:
 * @author: huangsm
 * @createDate: 2021/3/20
 */
public class ListNode {

    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}
