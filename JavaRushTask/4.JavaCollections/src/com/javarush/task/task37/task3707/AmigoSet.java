package com.javarush.task.task37.task3707;


import java.io.*;
import java.net.Inet4Address;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class AmigoSet<E> extends  AbstractSet<E> implements Set<E>, Serializable, Cloneable{
    private static final Object PRESENT = new Object();
    private transient HashMap<E,Object> map;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        HashSet<String> hashSet = new HashSet<>();
        hashSet.add("ddd");
        hashSet.add("rrrr");
        AmigoSet amigoSet = new AmigoSet(hashSet);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream =
                new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(amigoSet);
        objectOutputStream.close();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        AmigoSet amigoSet1 = (AmigoSet)  objectInputStream.readObject();
        System.out.println(amigoSet.equals(amigoSet1));
        System.out.println(amigoSet);
        System.out.println("________");
        System.out.println(amigoSet1);
    }

    public AmigoSet() {
        this.map = new HashMap<>();
    }

    public AmigoSet(Collection<? extends E> collection ) {
        int capacity =(int) Math.max(16, Math.ceil(collection.size()/.75f));
        map = new HashMap<>(capacity);
        this.addAll(collection);
    }

    private void writeObject(ObjectOutputStream outputStream) throws IOException {

        outputStream.defaultWriteObject();
        outputStream.writeObject(map.size());
        for (E element : map.keySet()) {
            outputStream.writeObject(element);
        }
        outputStream.writeObject(HashMapReflectionHelper.callHiddenMethod(map, "capacity"));
        outputStream.writeObject(HashMapReflectionHelper.callHiddenMethod(map, "loadFactor"));


    }

    private void readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {

        inputStream.defaultReadObject();
        int size = (int) inputStream.readObject();
        Set<E> temp = new HashSet<>();
        for (int i = 0; i < size; i++) {
            temp.add((E) inputStream.readObject());

        }
        int capacity = (int) inputStream.readObject();
        float load = (float) inputStream.readObject();
        map = new HashMap<>(capacity, load);
        for(E e: temp){
            map.put(e, PRESENT);
        }


    }

    @Override
    public Object clone()  {
        AmigoSet<E> set = new AmigoSet<>();
        try {
            set = (AmigoSet<E>) super.clone();
            set.map = (HashMap) map.clone();

        }
        catch (Exception e){
            throw new InternalError();
        }
        return set;
    }

    @Override
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return super.contains(o);
    }

    @Override
    public void clear() {
        this.map.clear();
    }

    @Override
    public boolean remove(Object o) {
        return super.remove(o);
    }

    @Override
    public boolean add(E e) {
        return map.put(e,PRESENT) == null;
    }

    @Override
    public Iterator<E> iterator() {
        return this.map.keySet().iterator();
    }

    @Override
    public void forEach(Consumer<? super E> action) {

    }
/*
    @Override
    public <T> T[] toArray(IntFunction<T[]> generator) {
        return null;
    }
    
 */

    @Override
    public boolean removeIf(Predicate<? super E> filter) {
        return false;
    }

    @Override
    public Spliterator<E> spliterator() {
        return null;
    }

    @Override
    public Stream<E> stream() {
        return null;
    }

    @Override
    public Stream<E> parallelStream() {
        return null;
    }

    @Override
    public int size() {
        return this.map.size();
    }
}
