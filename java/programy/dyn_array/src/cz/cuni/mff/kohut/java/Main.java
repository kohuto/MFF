package cz.cuni.mff.kohut.java;

import java.util.Objects;

interface MyCollection {
    void add(Object o);
    Object get(int i);
    void remove(Object o);
    void remove(int i);
    int getLength();
}



class DynArray implements MyCollection {
    private Object[] array = new Object[1];
    private int length = 0;
    public int getLength() {
        return length;
    }
    private void realocate(float koef) {
        Object newarr[] = new Object[(int) (koef * array.length)];
        for (int i = 0; i < array.length; i++)
            newarr[i] = array[i];
        array = newarr;
    }
    public void add(Object o) {
        if (length == array.length) {
            realocate(2);
        }
        array[length] = o;
        length++;
    }
    public Object get(int i) {
        return array[i];
    }
    private void shiftObjectsInArray(int index) {
        for (int j = index; j < length -1; j++) {
            array[j] = array[j + 1];
        }
        array[length - 1] = null;
    }
    private int indexOf(Object o) {
        for (int i = 0; i < array.length; i++) {
           if(Objects.equals(o,array[i]))
               return i;
        }
        return -1;
    }
    public void remove(Object o) {
        shiftObjectsInArray(indexOf(o));
        length--;
        if (length * 2 == length) {
            realocate(0.5f);
        }
    }
    public void remove(int i) {
        remove(array[i]);
    }
}

public class Main {
    public static void main(String[] args) {
        DynArray dynArray = new DynArray();
        for (int i = 0; i < args.length; i++) {
            dynArray.add(args[i]);
        }
        for (int i = 0; i < dynArray.getLength(); i++) {
            System.out.println(dynArray.get(i));
        }
    }
}