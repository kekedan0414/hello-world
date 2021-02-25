package com.cocoegg.pattern.Iterator.exercise1;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cocoegg
 * @date 2021/1/7 - 12:51
 */
public class IteratorDemo1 {
    public static void main(String[] args) {
        NameContainer contanier = new NameContainer();
        List<String> nameList = new ArrayList<>();
        nameList.add("fjy");
        nameList.add("gtt");
        nameList.add("lxm");
        nameList.add("zhx");
        nameList.add("ocq");
        nameList.add("hj");
        contanier.setNames(nameList);

        while (contanier.getIterator().hasNext()) {
            System.out.println(contanier.getIterator().next());
        }

    }
}


interface Iterator {
    boolean hasNext();
    Object next();
}

interface Contanier {
    Iterator getIterator();
}


class NameContainer implements Contanier {
    List<String> names ;

    Iterator iterator = new NameIterator();

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    @Override
    public Iterator getIterator() {
        return iterator;
    }

    private class NameIterator implements Iterator {

        private int index;

        @Override
        public boolean hasNext() {
            return index < names.size();
        }

        @Override
        public Object next() {
            if (hasNext())
                return names.get(index++);
            return null;
        }
    }
}

