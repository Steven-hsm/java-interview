package com.hsm.java.juc;

import lombok.Data;
import sun.misc.Unsafe;

import java.util.concurrent.locks.AbstractOwnableSynchronizer;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @description:
 * @author: huangsm
 * @createDate: 2021/3/27
 */
@Data
public class MyAQS extends AbstractOwnableSynchronizer
        implements java.io.Serializable {

    protected MyAQS() {
    }

    static final class Node {
        Node() {
        }

        Node(Thread thread, Node mode) {
            this.nextWaiter = mode;
            this.thread = thread;
        }

        Node(Thread thread, int waitStatus) {
            this.waitStatus = waitStatus;
            this.thread = thread;
        }

        static final Node SHARED = new Node();
        static final Node EXCLUSIVE = null;


        static final int CANCELLED = 1;
        static final int SIGNAL = -1;
        static final int CONDITION = -2;
        static final int PROPAGATE = -3;

        volatile int waitStatus;

        volatile Node prev;

        volatile Node next;

        volatile Thread thread;

        Node nextWaiter;


        final boolean isShared() {
            return nextWaiter == SHARED;
        }

        final Node predecessor() throws NullPointerException {
            Node p = prev;
            if (p == null) {
                throw new NullPointerException();
            }
            return p;
        }
    }

    private static final Unsafe unsafe = Unsafe.getUnsafe();
    private static final long stateOffset;
    private static final long headOffset;
    private static final long tailOffset;
    private static final long waitStatusOffset;
    private static final long nextOffset;

    //自旋超时阈值
    static final long spinForTimeoutThreshold = 1000L;

    static {
        try {
            stateOffset = unsafe.objectFieldOffset
                    (MyAQS.class.getDeclaredField("state"));
            headOffset = unsafe.objectFieldOffset
                    (MyAQS.class.getDeclaredField("head"));
            tailOffset = unsafe.objectFieldOffset
                    (MyAQS.class.getDeclaredField("tail"));
            waitStatusOffset = unsafe.objectFieldOffset
                    (MyAQS.Node.class.getDeclaredField("waitStatus"));
            nextOffset = unsafe.objectFieldOffset
                    (MyAQS.Node.class.getDeclaredField("next"));

        } catch (Exception ex) {
            throw new Error(ex);
        }
    }

    private transient volatile Node head;
    private transient volatile Node tail;
    private volatile int state;

    protected final boolean compareAndSetSate(int expect, int update) {
        return unsafe.compareAndSwapInt(this, stateOffset, expect, update);
    }

    private final boolean compareAndSetHead(Node update){
        return unsafe.compareAndSwapObject(this,headOffset,null,update);
    }

    private final boolean compareAndSetTail(Node expect, Node update) {
        return unsafe.compareAndSwapObject(this, tailOffset, expect, update);
    }


    private Node enq(final Node node){
        for (;;){
            Node t = tail;
            if(t == null){
                if(compareAndSetHead(new Node())){
                    tail = head;
                }
            }else{
                node.prev = t;
                if(compareAndSetTail(t,node)){
                    t.next = node;
                }
            }
        }
    }

    protected boolean tryAcquire(int arg) {
        throw new UnsupportedOperationException();
    }

    protected boolean tryRelease(int arg) {
        throw new UnsupportedOperationException();
    }

    protected int tryAcquireShared(int arg) {
        throw new UnsupportedOperationException();
    }

    protected boolean tryReleaseShared(int arg) {
        throw new UnsupportedOperationException();
    }
}
