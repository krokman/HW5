public class MyHashMap<K, V> implements MyMap<K, V> {
	private static final int INITIAL_CAPACITY = 16;
	private int size = 0;
	private Node<K, V>[] table;

	public MyHashMap(int initialCapacity) {
		if (initialCapacity < 0)
			throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);
		table = new Node[initialCapacity];
	}

	public MyHashMap() {
		table = new Node[INITIAL_CAPACITY];
	}

	private int hash(K key) {
		int hashCode = key.hashCode();
		hashCode ^= (hashCode >>> 20) ^ (hashCode >>> 12);
		return hashCode ^ (hashCode >>> 7) ^ (hashCode >>> 4);
	}

	private int indexFor(int hashCode, int length) {
		return hashCode & (length - 1);
	}

	public void put(K key, V value) {
		ensureTable();
		if (indexOf(key) != -1) {
			Node<K, V> current = getNode(key);
			current.setValue(value);
		} else {
			Node<K, V> current = new Node<>(hash(key), key, value, null);
			int index = indexFor(hash(key), table.length);
			if (table[index] != null) {
				table[index].next = current;
			} else {
				table[index] = current;
			}
		}
		size++;
	}

	public V get(K key) {
		Node<K, V> node = getNode(key);
		return node.value;
	}

	public V remove(K key) {
		Node<K, V> node = getNode(key);
		table[indexOf(key)] = null;
		size--;
		return node.value;
	}

	private int indexOf(K key) {
		for (int i = 0; i < table.length; i++) {
			if ((table[i] != null) && (hash(key) == (table[i].hash))) {
				return i;
			}
		}
		return -1;
	}

	public void clear() {
		table = new Node[INITIAL_CAPACITY];
	}

	public int size() {
		return size;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		int indexOfFirst = 0;
		for (int i = 0; i < table.length; i++) {
			if (table[i] != null) {
				result.append(table[i]);
				indexOfFirst = i + 1;
				break;
			}
		}
		for (int i = indexOfFirst; i < table.length; i++) {
			if (table[i] != null) {
				result.append(", ").append(table[i]);
			}
		}
		return result.toString();
	}

	private Node<K, V> getNode(K key) {
		for (int i = 0; i < table.length; i++) {
			if ((table[i] != null) && (table[i].hash == hash(key))) {
				return table[i];
			}
		}
		return null;
	}

	private void ensureTable() {
		if (size - 1 >= table.length) {
			Node<K, V>[] tableOld = table;
			table = new Node[size * 2];
			for (int i = 0; i < tableOld.length; i++) {
				if (tableOld[i] != null) {
					addNode(tableOld[i]);
				}
			}
		}
	}

	private void addNode(Node<K, V> node) {
		put(node.key, node.value);
	}


	private static class Node<K, V> {
		private final int hash;
		private final K key;
		private V value;
		private Node<K, V> next;

		private Node(int hash, K key, V value, Node<K, V> next) {
			this.hash = hash;
			this.key = key;
			this.value = value;
			this.next = next;
		}

		private void setValue(V newValue) {
			value = newValue;
		}

		@Override
		public String toString() {
			return "Key - " + key + " Value = " + value;
		}
	}
}