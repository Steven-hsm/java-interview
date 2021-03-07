package com.hsm.list;

import java.util.Arrays;

public class MyArrayList<E> implements MyList<E>{

    private static final int DEFAULT_CAPACITY = 10;
    /**
     * 提前需要初始化的数组大小
     */
    private Object [] elementData;
    /**
     * 当前元素大小
     */
    private int size;

    public MyArrayList(){
        this.elementData = new Object[DEFAULT_CAPACITY];
    }

    public MyArrayList(int initialCapacity){
        if(initialCapacity > 0){
            this.elementData = new Object[initialCapacity];
        }else{
            throw new IllegalArgumentException("参数异常：" + initialCapacity);
        }
    }

    @Override
    public boolean listEmpty() {
        return false;
    }

    @Override
    public void clearList() {

    }

    @Override
    public E get(int index) {
        rangeCheck(index);
        return elementData(index);
    }

    private E elementData(int index) {
        return (E) elementData[index];
    }

    /**
     * 范围检查
     * @param index
     */
    private void rangeCheck(int index) {
        if(index > size){
            throw new IllegalArgumentException("参数错误：" + index);
        }
    }

    @Override
    public int indexOf(E e) {
        return 0;
    }

    @Override
    public void add(E e) {
        if(elementData.length == size){
            elementData = Arrays.copyOf(elementData,size * 2);
        }
        elementData[size ++] = e;
    }

    @Override
    public void remove(E e) {

    }

    @Override
    public int length() {
        return 0;
    }
}
