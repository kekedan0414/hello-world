package com.cocoegg.effectivejava;


import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class _79 {
    public static void main(String[] args) {
        ObservableSet<Integer> set =
                new ObservableSet<Integer>(new HashSet<Integer>());

        // add 方法调用后会触发notifyElemetnAdded(E element)方法
        // 执行 SetObserver added方法

        set.addObserver(new SetObserver<Integer>() {
            public void added(ObservableSet<Integer> set, Integer element) {
                System.out.println(element);
                if(element == 23)
                    set.removeObserver(this);
            }
        });
        for(int i = 0; i < 100; i++){
            set.add(i);
        }

    }


}


interface SetObserver<E> {
    void added(ObservableSet<E> set, E element);
}

class ObservableSet<E> extends ForwardingSet<E> {

    public ObservableSet(Set<E> s) {
        super(s);
    }

    private final List<SetObserver<E>> observers =
            new ArrayList<SetObserver<E>>();

    public void addObserver(SetObserver<E> observer){
        synchronized (observer) {
            observers.add(observer);
        }
    }

    public boolean removeObserver(SetObserver<E> observer){
        synchronized (observer) {
            return observers.remove(observer);
        }
    }

    private void notifyElemetnAdded(E element){
        synchronized (observers) {
            for(SetObserver<E> observer : observers){
                observer.added(this, element);
            }
        }
    }

    @Override
    public boolean add(E element) {
        boolean added = super.add(element);
        if(added)
            notifyElemetnAdded(element);
        return added;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean result = false;
        for(E element : c){
            result |= add(element);
        }
        return result;
    }
}


class ForwardingSet<E> implements Set<E> {

    private final Set<E> s;

    public ForwardingSet(Set<E> s){
        this.s = s;
    }

    public int size() {
        return s.size();
    }

    public boolean isEmpty() {
        return s.isEmpty();
    }

    public boolean contains(Object o) {
        return s.contains(o);
    }

    public Iterator<E> iterator() {
        return s.iterator();
    }

    public Object[] toArray() {
        return s.toArray();
    }

    public <T> T[] toArray(T[] a) {
        return s.toArray(a);
    }

    public boolean add(E e) {
        return s.add(e);
    }

    public boolean remove(Object o) {
        return s.remove(o);
    }

    public boolean containsAll(Collection<?> c) {
        return s.containsAll(c);
    }

    public boolean addAll(Collection<? extends E> c) {
        return s.addAll(c);
    }

    public boolean retainAll(Collection<?> c) {
        return c.retainAll(c);
    }

    public boolean removeAll(Collection<?> c) {
        return s.removeAll(c);
    }

    public void clear() {
        s.clear();
    }

}

