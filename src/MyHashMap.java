import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class MyHashMap<K, V> implements MyMap<K, V> {
	private final int DEFAULT_INITIAL_CAPACITY = 16;
	private final int MAXIMUM_CAPACITY = 1 << 30;
	private Node<K, V> lastNode;
	private int size = 0;
	List<Node<K, V>> list;


	public MyHashMap(int initialCapacity) {
		if (initialCapacity < 0)
			throw new IllegalArgumentException("Illegal initial capacity: " +
					initialCapacity);
		if (initialCapacity > MAXIMUM_CAPACITY)
			initialCapacity = MAXIMUM_CAPACITY;
		list = new ArrayList<>(initialCapacity);
	}

	public MyHashMap() {
		list = new ArrayList<>(DEFAULT_INITIAL_CAPACITY);
	}

	static int hash(int hashCode) {
		hashCode ^= (hashCode >>> 20) ^ (hashCode >>> 12);
		return hashCode ^ (hashCode >>> 7) ^ (hashCode >>> 4);
	}


	private Node<K, V> getNode(K key) {
		Node<K, V> requiredNode = lastNode;
		for (int i = 0; i < size; i++) {
			if (requiredNode.key == key) {
				return requiredNode;
			}
			requiredNode = requiredNode.previous;
		}
		throw new NoSuchElementException(key + " Wrong Key");
	}

	@Override
	public void put(K key, V value) {
		try {
			Node<K, V> currentNode = getNode(key);
			currentNode.value = value;
			list.add(currentNode);
		} catch (NoSuchElementException e) {
			if (size == 0) {
				lastNode = new Node<K, V>(hash(key.hashCode()), key, value, lastNode);
				list.add(lastNode);
				size++;
			} else {
				Node<K, V> currentNode = new Node<>(hash(key.hashCode()), key, value, lastNode);
				lastNode = currentNode;
				list.add(lastNode);
				size++;
			}
		}
	}

	@Override
	public V remove(K key) {
		Node<K, V> bufferNode = getNode(key);
		size--;
		for (int i = 0; i < size; i++) {
			if (lastNode.previous == bufferNode) {
				list.remove(lastNode.previous);
				lastNode.previous = lastNode.previous.previous;
				break;
			}
			lastNode = lastNode.previous;
		}
		bufferNode.previous = null;
		return bufferNode.value;
	}

	@Override
	public void clear() {
		lastNode = null;
		size = 0;
		list = new ArrayList<>();
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public V get(K key) {
		Node<K, V> currentNode = getNode(key);
		return currentNode.value;
	}

	@Override
	public String toString() {
		Node<K, V> currentNode = lastNode;
		StringBuilder result = new StringBuilder(currentNode.toString());
		for (int i = 0; i < size - 1; i++) {
			currentNode = currentNode.previous;
			result.append(" ").append(currentNode.toString());
		}
		return result.toString();
	}

	private class Node<K, V> {
		private K key;
		private V value;
		private int hash;
		private Node<K, V> previous;

		public Node(int hash, K key, V value, Node<K, V> previous) {
			this.hash = hash;
			this.key = key;
			this.value = value;
			this.previous = previous;
		}

		@Override
		public String toString() {
			return "key = " + key +
					", value = " + value;
		}
	}
}
