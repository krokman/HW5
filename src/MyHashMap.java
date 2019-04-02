import java.util.NoSuchElementException;

public class MyHashMap<K, V> implements MyMap<K, V> {
	private Node<K, V> lastNode;
	private int size = 0;

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
		} catch (NoSuchElementException e) {
			if (size == 0) {
				lastNode = new Node<K, V>(key, value, lastNode);
				size++;
			} else {
				Node<K, V> currentNode = new Node<>(key, value, lastNode);
				lastNode = currentNode;
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
		private Node<K, V> previous;

		public Node(K key, V value, Node<K, V> previous) {
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
