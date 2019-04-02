public interface MyMap<K, V> {
	void put(K key, V value);
	V remove(K key);
	void clear();
	int size();
	void get(K key);
}
