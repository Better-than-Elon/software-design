class LRUCache<K, V>(private val capacity: Int) {
    private val head: Node<K, V> = Node(null, null)
    private val tail: Node<K, V> = Node(null, null)
    private val hashMap = HashMap<K, Node<K, V>?>()
    private var listSize = 0

    init {
        assert(capacity > 0)
        head.next = tail
        tail.prev = head
    }

    class Node<K, V>(var key: K?, var value: V?) {
        var next: Node<K, V>? = null
        var prev: Node<K, V>? = null
    }

    private fun removeNode(node: Node<K, V>) {
        assert(listSize > 0)
        assert(hashMap.containsKey(node.key!!))
        hashMap.remove(node.key!!)
        node.prev!!.next = node.next
        node.next!!.prev = node.prev
        listSize--
        assert(!hashMap.containsKey(node.key!!))
    }

    private fun pushFront(node: Node<K, V>) {
        assert(!hashMap.containsKey(node.key!!))
        assert(listSize < capacity)
        hashMap[node.key!!] = node
        node.value!!
        val next = head.next
        head.next = node
        node.prev = head
        next!!.prev = node
        node.next = next
        listSize++
        assert(hashMap.containsKey(node.key!!))
    }

    fun put(key: K, value: V) {
        var node: Node<K, V>? = hashMap[key]

        if (node != null) {
            removeNode(node)
            node.value = value

        } else {
            if (listSize == capacity) {
                removeNode(tail.prev!!)
            }
            node = Node(key, value);
        }
        pushFront(node)
        assert(listSize in 1..capacity)
        assert(node == head.next)
        assert(hashMap.containsKey(node.key!!))
    }

    fun get(key: K): V? {
        val node: Node<K, V> = hashMap[key] ?: return null
        removeNode(node)
        pushFront(node)
        assert(listSize > 0)
        assert(node == head.next)
        return node.value
    }
}