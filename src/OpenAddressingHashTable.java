public class OpenAddressingHashTable <K, V> {

    private Pair[] storage;

    private int size;

    private int countElements = 0;

    public OpenAddressingHashTable() {
        size = 16;
        storage = new Pair[size];
    }

    public OpenAddressingHashTable(int tableSize) {
        size = tableSize;
        storage = new Pair[size];
    }

    public OpenAddressingHashTable (OpenAddressingHashTable<K, V> table) {

    }

    public boolean isEmpty() {
        return countElements == 0;
    }

    public void clear() {
        storage = new Pair[size];
        countElements = 0;
    }

    public boolean containsKey (K key) {
        int index = 0;
        while (index < size) {
            if (storage[index] != null && key.equals(storage[index].getKey()))
                return true;
            index++;
        }
        return false;
    }

    public boolean containsValue (V value) {
        int index = 0;
        while (index < size) {
            if (storage[index] != null && value.equals(storage[index].getValue()))
                return true;
            index++;
        }
        return false;
    }

    public boolean put (K key, V value) {
        if (countElements < size) {
            if (containsKey(key)) {} // TODO: 30.11.2020

            Pair pair = new Pair(key, value);
            int hash = key.hashCode() % size;
            while (storage[hash] != null) {
                hash = (hash + 1) % size;
            }
            storage[hash] = pair;
            countElements++;
            return true;
        }
        return false;
    }

    public Object getOrDefault (K key) {
        int index = 0;
        while (index < size) {
            if (storage[index] != null && key.equals(storage[index].getKey()))
                return storage[index].getValue();
            index++;
        }
        return null;
    }

    private Pair DEL;
    public boolean remove (K key) {
        int index = 0;
        while (index < size) {
            if (storage[index] != null && key.equals(storage[index].getKey())) {
                storage[index] = DEL;
                countElements--;
                return true;
            }
            index++;
        }
        return false;
    }
}
