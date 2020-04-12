package broke.set2map;

import java.util.*;

public class Set2Map<K, V> extends HashSet<SimpleEntry<K, V>> {

    public void clear() {
        super.clear();
    }

    public boolean containsKey(Object key) {
        return super.contains(new SimpleEntry<>(key, null));
    }

    public boolean containsValue(Object value) {
        return this.stream().anyMatch(ele -> ele.getValue().equals(value));
    }

    public V get(Object key) {
        for(SimpleEntry<K, V> simpleEntry : this) {
            if(simpleEntry.getKey().equals(key)) {
                return simpleEntry.getValue();
            }
        }
        return null;
    }

    public V put(K key, V value) {
        super.add(new SimpleEntry<>(key, value));
        return value;
    }

    public void putAll(Map<? extends K, ? extends V> map) {
        for(K key : map.keySet()) {
            add(new SimpleEntry<>(key, map.get(key)));
        }
    }

    public V removeEntry(Object key) {
        for(Iterator<SimpleEntry<K, V>> it = this.iterator(); it.hasNext(); ) {
            SimpleEntry<K, V> simpleEntry = it.next();
            if(simpleEntry.getKey().equals(key)) {
                V v = simpleEntry.getValue();
                it.remove();
                return v;
            }
        }
        return null;
    }

    public int size () {
        return super.size();
    }

    public static void main(String[] args) {
        Set2Map<String, Integer> scores = new Set2Map<>();
        scores.put("语文", 89);
        scores.put("数学", 43);
        scores.put("英语", 79);

        Map<String, Integer> scores2 = new HashMap<>();
        scores2.put("政治", 99);
        scores2.put("物理", 86);
        scores.putAll(scores2);

        System.out.println(scores.size());
        System.out.println("是否存在89的分数：" + scores.containsValue(89));
        System.out.println("是否存在语文这一科：" + scores.containsKey("语文"));
        System.out.println("英语的得分：" + scores.get("英语"));
        scores.removeEntry("英语");
        scores.forEach(System.out::println);

        scores.put("数学", 100);
        System.out.println("scores的长度是：" + scores.size());

        int capacity = 3;
        System.out.println(capacity <<= 2);

        Map<Integer, String> scores3 = new TreeMap<>();
        scores3.put(1, "小明");
        scores3.put(2, "小红");
        scores3.put(3, "小钱");
        System.out.println(scores3.values());
        System.out.println(scores3.values().remove("小明"));
        System.out.println(scores3.values());

        int[] arr = new int[5];
        arr[0] = 1;
        arr[1] = 2;
        arr[2] = 3;

        int[] arr2 = new int[2];
        arr2[0] = 23;
        arr2[1] = 89;

        System.arraycopy(arr, 1, arr, 3, 2);
        System.out.println(arr.length);
        for(int i : arr) {
            System.out.println(i);
        }

        List<String> list = new LinkedList<>();
        list.add("english");
        list.add("math");
        list.add("chinese");
        list.add("science");
        Iterator<String> it = list.iterator();
        while(it.hasNext()) {
            String ele = it.next();
            if(ele.equals("chinese"))
                list.remove("science");
            System.out.println(ele);
        }

        String s = null;
        System.out.println("null是否是String类型的实例：" + (s instanceof String));

        Map<Integer, String> map2Set = new HashMap<>();
        map2Set.put(1, null);
        map2Set.put(2, null);
        map2Set.put(3, null);
        map2Set.put(4, null);
        map2Set.keySet().stream().forEach(System.out::println);

        map2Set.putAll(scores3);
        map2Set.keySet().stream().forEach(System.out::println);
        System.out.println("---------");
        scores.stream().forEach(System.out::println);
    }
}
