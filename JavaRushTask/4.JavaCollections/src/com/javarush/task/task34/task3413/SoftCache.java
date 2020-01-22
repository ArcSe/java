package com.javarush.task.task34.task3413;

import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SoftCache {
    private Map<Long, SoftReference<AnyObject>> cacheMap = new ConcurrentHashMap<>();

    public AnyObject get(Long key) {
        SoftReference<AnyObject> softReference = cacheMap.get(key);
        AnyObject result;
        if(cacheMap.containsKey(key)){
            result = cacheMap.get(key).get();
        }
        else{
            result = null;
        }
        return result;

        //напишите тут ваш код
    }

    public AnyObject put(Long key, AnyObject value) {
        AnyObject result = get(key);
        SoftReference<AnyObject> softReference = cacheMap.put(key, new SoftReference<>(value));
        if(softReference != null){
            softReference.clear();
        }

        return result;


        //напишите тут ваш код
    }

    public AnyObject remove(Long key) {
        AnyObject result = get(key);
        SoftReference<AnyObject> softReference = cacheMap.remove(key);
        if(softReference != null){
            softReference.clear();
        }
        return result;

        //напишите тут ваш код
    }
}