package com.javarush.task.task20.task2028;

import org.w3c.dom.events.Event;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* 
Построй дерево(1)
*/
public class CustomTree extends AbstractList<String> implements Cloneable, Serializable {
    Entry<String> root;
    List<Entry<String>> tree = new ArrayList<>();

    public CustomTree() {
        this.root = new Entry<>("root");
        this.tree.add(root);

    }

    public Entry<String> find(String s){
        Entry<String> newEntry = new Entry<>(s);
        for (Entry<String> entry: tree) {
            if(entry.equals(newEntry)){
                return newEntry;
            }
        }
        return null;
    }

    @Override
    public boolean remove(Object o) {
        if(!(o instanceof String))
            throw new UnsupportedOperationException();

        String elementName = (String) o;

        //Перебираем лист, пропуская root
        for(int i = 1; i < tree.size(); i++) {

            Entry nextEntry = tree.get(i);
            //Находим необходимую для удаления запись
            if(nextEntry.elementName.equals(elementName)) {

                //Удаляем найденную запись и ее детей
                removeChilds(nextEntry);
                tree.remove(i);

                //восстанавливаем возможность добавить левого или правого ребенка
                Entry parent = nextEntry.parent;

                if(parent.leftChild.elementName == elementName) {
                    parent.availableToAddLeftChildren = true;
                    parent.leftChild = null;
                } else {
                    parent.availableToAddRightChildren = true;
                    parent.rightChild = null;
                }
            }
        }


        return true;
    }

    private void removeChilds(Entry entry) {
        List<Entry> childs = new ArrayList<>();
        for(int i = 0;  i < tree.size(); i++) {
            Entry e = tree.get(i);
            if(e.elementName == entry.elementName) {
                Entry leftChild = e.leftChild;
                Entry rightChild = e.rightChild;

                if(leftChild != null) {
                    removeChilds(leftChild);
                    childs.add(leftChild);
                }

                if(rightChild != null) {
                    removeChilds(rightChild);
                    childs.add(rightChild);
                }
            }
        }
        tree.removeAll(childs);
    }


    public String getParent(String s){
        for (Entry<String> stringEntry : tree) {
            if (stringEntry.elementName.equals(s)) return stringEntry.parent.elementName;
        }
        return null;
    }
    @Override
    public boolean add(String s) {
        Entry<String> child = new Entry<>(s);
        for (int i = 0; i < tree.size(); i++) {
            if(tree.get(i).availableToAddLeftChildren){
                tree.get(i).leftChild = child;
                tree.get(i).availableToAddLeftChildren = false;
                child.parent = tree.get(i);
               break;
            }
            if(tree.get(i).availableToAddRightChildren){
                tree.get(i).rightChild = child;
                child.parent = tree.get(i);
                tree.get(i).availableToAddRightChildren = false;
                break;
            }
        }
        tree.add(child);
        return true;
    }

    @Override
    public String get(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String set(int index, String element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int index, String element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends String> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        return tree.size()-1;
    }

    static class Entry<T> implements Serializable{
        String elementName;
        boolean availableToAddLeftChildren;
        boolean availableToAddRightChildren;
        Entry<T> parent;
        Entry<T> leftChild;
        Entry<T> rightChild;


        public Entry(String elementName) {
            this.elementName = elementName;
            this.availableToAddLeftChildren = true;
            this.availableToAddRightChildren = true;
        }

        public boolean isAvailableToAddChildren(){
            return availableToAddLeftChildren || availableToAddRightChildren;
        }
    }

}
