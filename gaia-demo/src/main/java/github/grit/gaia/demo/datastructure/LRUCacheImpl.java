package github.grit.gaia.demo.datastructure;

import java.util.HashMap;
import java.util.Map;


public class LRUCacheImpl<K, V> implements LRUCache<K, V> {

    static class Node<K, V> {
        private K key;
        private V value;
        private Node<K, V> pre;
        private Node<K, V> next;

        public Node() {
        } // 用于伪节点

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private final int capacity;
    private final Map<K, Node<K, V>> cache;
    private final Node<K, V> dummyHead;
    private final Node<K, V> dummyTail;

    public LRUCacheImpl(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("容量必须大于 0");
        }
        this.capacity = capacity;
        this.cache = new HashMap<>(capacity);
        this.dummyHead = new Node<>();
        this.dummyTail = new Node<>();
    }

    @Override
    public V get(K key) {
        Node<K, V> node = cache.get(key);
        if (node == null) {
            return null;
        }
        // 存在
        moveToHead(node);
        return node.value;
    }

    @Override
    public void put(K key, V value) {
        Node<K, V> node = cache.get(key);
        if (node != null) {
            // 存在
            node.value = value;
            moveToHead(node);
        } else {
            Node<K, V> newNode = new Node<>(key, value);
            cache.put(key, newNode);
            addToHead(newNode);

            // 检查是否超出容量
            if (cache.size() > capacity) {
                // 淘汰尾部节点（最久未使用）
                Node<K, V> removedNode = removeTail();
                // 从 Map 中移除
                cache.remove(removedNode.key);
            }
        }
    }

    private void addToHead(Node<K, V> node) {
        node.pre = dummyHead;
        node.next = dummyHead.next;
        dummyHead.next.pre = node;
        dummyHead.next = node;
    }

    private void removeNode(Node<K, V> node) {
        node.pre.next = node.next;
        node.next.pre = node.pre;
        node.pre = null;
        node.next = null;
    }

    private Node<K, V> removeTail() {
        Node<K, V> tail = dummyTail.pre;
        removeNode(tail);
        return tail;
    }

    private void moveToHead(Node<K, V> node) {
        removeNode(node);
        addToHead(node);
    }

}
