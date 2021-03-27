package com.hsm.java.juc;

import java.util.concurrent.locks.AbstractOwnableSynchronizer;

/**
 * @description:
 * @author: huangsm
 * @createDate: 2021/3/27
 */
public class MyAQS extends AbstractOwnableSynchronizer
        implements java.io.Serializable {

    protected MyAQS() { }

    static final class Node {
        Node(){}

        Node(Thread thread,Node mode){
            this.nextWaiter = mode;
            this.thread = thread;
        }

        Node(Thread thread,int waitStatus){
            this.waitStatus = waitStatus;
            this.thread = thread;
        }

        static final Node SHARED = new Node();
        static final Node EXCLUSIVE = null;


        static final int CANCELLED = 1;
        static final int SIGNAL    = -1;
        static final int CONDITION = -2;
        static final int PROPAGATE = -3;

        volatile int waitStatus;

        volatile Node prev;

        volatile Node next;

        volatile Thread thread;

        Node nextWaiter;


        final  boolean isShared(){
            return nextWaiter == SHARED;
        }

        final Node predecessor() throws NullPointerException{
            Node p = prev;
            if(p == null){
                throw new NullPointerException();
            }
            return p;
        }
    }
}
