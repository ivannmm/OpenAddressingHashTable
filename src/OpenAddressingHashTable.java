public class OpenAddressingHashTable <K, V> {

    private Pair<K, V>[] storage;

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

    public int getSize(){
        return size;
    }
    public boolean isEmpty() {
        return countElements == 0;
    }

    public void clear() {
        storage = new Pair[size];
        countElements = 0;
    }

    public boolean containsKey (K key) {
        int index = key.hashCode() % size;
        int startIndex = index;
        do {
            if (storage[index] != null && key.equals(storage[index].getKey()))
                return true;
            index = (index + 1) % size;
        } while (index != startIndex);
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
            Pair<K, V> pair = new Pair<>(key, value);
            int hash = key.hashCode() % size;
            while (storage[hash] != null || storage[hash] != DEL) {
                hash = (hash + 1) % size;
            }
            storage[hash] = pair;
            countElements++;
            return true;
        } else {
            restructuring(key, value);
        }
        return false;
    }

    public Object getOrDefault (K key) {
        int index = key.hashCode() % size;
        int startIndex = index;
        do {
            if (storage[index] != null && key.equals(storage[index].getKey()))
                return storage[index].getValue();
            index = (index + 1) % size;
        } while (index != startIndex);
        return null;
    }

    private Pair DEL;
    public boolean remove (K key) {
        int index = key.hashCode() % size;
        int startIndex = index;
        do {
            if (storage[index] != null && key.equals(storage[index].getKey())) {
                storage[index] = DEL;
                countElements--;
                return true;
            }
            index = (index + 1) % size;
        } while (index != startIndex);
        return false;
    }

    private void restructuring (K newKey, V newValue){
        Pair<K, V>[] temp = new Pair[storage.length * 2];
        for (Pair<K, V> kvPair : storage) {
            int index = kvPair.getKey().hashCode() % temp.length;
            while (temp[index] != null || temp[index] != DEL) {
                index = (index + 1) % temp.length;
            }
            Pair<K, V> pair = new Pair<>(kvPair.getKey(), kvPair.getValue());
            temp[index] = pair;
        }
        size = temp.length;
        storage = temp;
        put(newKey, newValue);
    }

    /**public Iterator<> iterator() {
        return new OAHTIterator();
    }

    private class OAHTIterator implements Iterator<T> {

        private int currentNumber = 0;
        private int countFind = 0;
        private Object element = null;

        @Override
        public boolean hasNext() {
            return countFind < size;
        }

        @Override
        public T next() {
            if (!hasNext())
                throw new IllegalStateException();
            while (storage[currentNumber] == null || storage[currentNumber] == DEL) {
                currentNumber++;
            }
            element = storage[currentNumber];
            countFind++;
            currentNumber++;
            return (T) element;
        }

        @Override
        public void remove() {
            if (element == null)
                throw new IllegalStateException();
            storage[currentNumber - 1] = DEL;
            size--;
            countFind--;
        }
    }*/
}
