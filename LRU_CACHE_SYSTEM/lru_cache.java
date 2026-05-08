class LRUCache {
    class Node {
        int key;
        int val;
        Node prev;
        Node next;

        Node(int key, int val) {
            this.key = key;
            this.val = val;
        }
    }
    Node head = new Node(-1, -1);
    Node tail = new Node(-1, -1);

    HashMap<Integer, Node> m = new HashMap<>();
    int cap;
    public LRUCache(int capacity) {
        cap = capacity;
        head.next = tail;
        tail.prev = head;
    }
    void addNode(Node newNode) {
        Node nextNode = head.next;
        newNode.next = nextNode;
        newNode.prev = head;
        head.next = newNode;
        nextNode.prev = newNode;
    }
    void deleteNode(Node oldNode) {
        Node oldPrev = oldNode.prev;
        Node oldNext = oldNode.next;
        oldPrev.next = oldNext;
        oldNext.prev = oldPrev;
    }
    public int get(int key) {
        if (m.containsKey(key)) {
            Node ansNode = m.get(key);
            int ans = ansNode.val;
            m.remove(key);
            deleteNode(ansNode);
            addNode(ansNode);

            m.put(key, ansNode);
            return ans;
        }
        return -1;
    }
    public void put(int key, int value) {
        if (m.containsKey(key)) {
            Node oldNode = m.get(key);
            m.remove(key);           //map re remove
            deleteNode(oldNode);     //node se remove
        }
        if (m.size() == cap) {
            m.remove(tail.prev.key);   //map re remove
            deleteNode(tail.prev);     //node se remove
        }
        Node newNode = new Node(key, value);
        addNode(newNode);
        m.put(key, newNode);
    }
    public static void main(String[] args) {
        LRUCache cache = new LRUCache(2);
        cache.put(1, 10);
        cache.put(2, 20);
        System.out.println(cache.get(1)); // 10
        cache.put(3, 30); // removes key 2
        System.out.println(cache.get(2)); // -1
        cache.put(4, 40); // removes key 1
        System.out.println(cache.get(1)); // -1
        System.out.println(cache.get(3)); // 30
        System.out.println(cache.get(4)); // 40
    }
}




/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */