package github.grit.gaia.demo.datastructure;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public interface LRUCache<K, V> {
    V get(K key);

    void put(K key, V value);


    class DefaultLRUCache<E, T> implements LRUCache<E, T> {
        private final int capacity;
        private final Map<E, T> map;
        private final LinkedList<E> orderList;

        public DefaultLRUCache(int capacity) {
            if (capacity <= 0) {
                throw new IllegalArgumentException("容量必须大于 0");
            }
            this.capacity = capacity;
            this.map = new HashMap<>(capacity);
            this.orderList = new LinkedList<>();
        }

        @Override
        public T get(E key) {
            if (!map.containsKey(key)) {
                return null;
            }
            moveToHead(key);
            return map.get(key);
        }

        @Override
        public void put(E key, T value) {
            // 存在
            if (map.containsKey(key)) {
                map.put(key, value);
                moveToHead(key);
                return;
            }
            // 容量已满
            if (map.size() == capacity) {
                // 淘汰尾部元素
                E lruKey = orderList.pollLast();
                map.remove(lruKey);
            }
            map.put(key, value);
            orderList.addFirst(key);
        }


        private void moveToHead(E key) {
            orderList.remove(key);
            orderList.addFirst(key);
        }
    }
}

