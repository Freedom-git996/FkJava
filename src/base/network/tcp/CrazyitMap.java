package base.network.tcp;

import java.util.*;

/**
 * @Auther: Vectory
 * @Date: 2019/10/16
 * @Description: base.network.tcp
 * @version: 1.0
 */
public class CrazyitMap<K, V> {

    public Map<K, V> map = Collections.synchronizedMap(new HashMap<K, V>());

    public synchronized void removeByValue(Object value){
        for(K key : map.keySet()){
            if(map.get(key) == value){
                map.remove(key);
                break;
            }
        }
    }

    public synchronized Set<V> valueSet(){
        Set<V> result = new HashSet<V>();
        map.forEach((key, value) -> result.add(value));
        return result;
    }

    public synchronized K getKeyByValue(V value){
        for(K key : map.keySet()){
            if(map.get(key) == value || map.get(key).equals(value)){
                return key;
            }
        }
        return null;
    }

    public synchronized V put(K key, V value){
        for(V val : valueSet()){
            if(val.equals(value) && val.hashCode() == value.hashCode()){
                throw new RuntimeException("MyMap实例中不允许重复value!");
            }
        }
        return map.put(key, value);
    }
}
