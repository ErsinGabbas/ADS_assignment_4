import java.util.Queue;
import java.util.LinkedList;

public class BST<K extends Comparable<K>, V>{
    private Node root;
    private class Node{
        private K key;
        private V val;
        private Node left, right;
        public Node(K key, V val){
            this.key = key;
            this.val = val;
        }
    }
    public void put(K key, V val){
        root = put(root, key, val);
    }
    private Node put(Node node, K key, V val) {
        if (node == null) {
            size++;
            return new Node(key, val);
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = put(node.left, key, val);
        } else if (cmp > 0) {
            node.right = put(node.right, key, val);
        } else {
            node.val = val;
        }
        return node;
    }

    public V get(K key){
        Node node = get(root, key);
        return node == null ? null : node.val;
    }
    private Node get(Node node, K key){
        if(node == null){
            return null;
        }
        int cmp = key.compareTo(node.key);
        if(cmp < 0){
            return get(node.left, key);
        }else if(cmp > 0){
            return get(node.right, key);
        }else{
            return node;
        }
    }

    public void delete(K key){
        root = delete(root, key);
    }
    private Node delete(Node node, K key) {
        if (node == null) {
            return null;
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = delete(node.left, key);
        } else if (cmp > 0) {
            node.right = delete(node.right, key);
        } else {
            if (node.left == null) {
                size--;
                return node.right;
            }
            if (node.right == null) {
                size--;
                return node.left;
            }
            Node t = node;
            node = min(t.right);
            node.right = deleteMin(t.right);
            node.left = t.left;
            size--;
        }
        return node;
    }
    private Node min(Node node){
        if(node.left == null){
            return node;
        }
        return min(node.left);
    }
    private Node deleteMin(Node node) {
        if (node.left == null) {
            return node.right;
        }
        node.left = deleteMin(node.left);
        return node;
    }

    public Iterable<Entry<K, V>> entrySet() {
        Queue<Entry<K, V>> q = new LinkedList<>();
        inorder(root, q);
        return q;
    }

    private void inorder(Node node, Queue<Entry<K, V>> q) {
        if (node == null) {
            return;
        }
        inorder(node.left, q);
        q.add(new Entry<K, V>(node.key, node.val));
        inorder(node.right, q);
    }

    public static class Entry<K extends Comparable<K>, V> {
        private K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }
    }
}