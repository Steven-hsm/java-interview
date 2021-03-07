package com.hsm.list;

public interface MyList<E> {

    boolean listEmpty();

    void clearList();

    E get(int index);

    int indexOf(E e);

    void add(E e);

    void remove(E e);

    int length();
}
