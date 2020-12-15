import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class OpenAddressingHashTable <K, V> implements Map<K, V> {

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
        size = table.getSize();
        countElements = table.getSize();
        Iterator<K> it = table.iterator();
        while (it.hasNext()){
            K key = it.next();
            put(key, table.getOrDefault(key));
        }
    }

    @Override
    public int size() {
        return countElements;
    }

    public boolean isEmpty() {
        return countElements == 0;
    }

    @Override
    public boolean containsKey(Object o) {
        if (o == null)
            return false;
        K k = (K) o;
        int index = k.hashCode() % size;
        int startIndex = index;
        do {
            if (storage[index] != null && k.equals(storage[index].getKey()))
                return true;
            index = (index + 1) % size;
        } while (index != startIndex);
        return false;
    }

    @Override
    public boolean containsValue(Object o) {
        if (o == null)
            return false;
        V k = (V) o;
        int index = 0;
        while (index < size) {
            if (storage[index] != null && k.equals(storage[index].getValue()))
                return true;
            index++;
        }
        return false;
    }

    @Override
    public V get(Object o) {
        if (o == null)
            throw new IllegalArgumentException();
        K key = (K) o;
        int index = key.hashCode() % size;
        int startIndex = index;
        do {
            if (storage[index] != null && key.equals(storage[index].getKey()))
                return storage[index].getValue();
            index = (index + 1) % size;
        } while (index != startIndex);
        return null;
    }

    @Nullable
    @Override
    public Object put(Object o, Object o2) {
        if (o == null || o2 == null)
            throw new IllegalArgumentException();
        K key = (K) o;
        V value = (V) o2;
        if (countElements < size) {
            Pair<K, V> pair = new Pair<>(key, value);
            int hash = key.hashCode() % size;
            while ((storage[hash] != null || storage[hash] != DEL)
                    && !storage[hash].getKey().equals(key)) {
                hash = (hash + 1) % size;
            }
            storage[hash] = pair;
            countElements++;
            return value;
        } else {
            restructuring(key, value);
        }
    }
    private Pair DEL;
    @Override
    public V remove(Object o) {
        if (o == null)
            throw new IllegalArgumentException();
        K key = (K) o;
        int index = key.hashCode() % size;
        int startIndex = index;
        do {
            if (storage[index] != null && key.equals(storage[index].getKey())) {
                V temp = storage[index].getValue();
                storage[index] = DEL;
                countElements--;
                return temp;
            }
            index = (index + 1) % size;
        } while (index != startIndex);
        return null;
    }

    @Override
    public void putAll(@NotNull Map map) {

    }

    public void clear() {
        storage = new Pair[size];
        countElements = 0;
    }

    @NotNull
    @Override
    public Set keySet() {
        return null;
    }

    @NotNull
    @Override
    public Collection values() {
        return null;
    }

    @NotNull
    @Override
    public Set<Entry> entrySet() {
        return null;
    }

    @SuppressWarnings("unchecked")
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

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals (Object o){
        if (o.getClass() != this.getClass())
            return false;
        OpenAddressingHashTable<K,V> forEquals = (OpenAddressingHashTable<K, V>) o;
        if (size() != forEquals.size())
            return false;
        for (Pair<K, V> kvPair : storage) {
            if (kvPair != null && kvPair != DEL) {
                if (!forEquals.get(kvPair.getKey()).equals(kvPair.getValue()))
                    return false;
            }
        }
        return true;
    }

    public Iterator<K> iterator() {
        return new OAHTIterator();
    }

    private class OAHTIterator implements Iterator<K> {

        private int currentNumber = 0;
        private int countFind = 0;
        private Object element = null;

        @Override
        public boolean hasNext() {
            return countFind < size;
        }

        @Override
        @SuppressWarnings("unchecked")
        public K next() {
            if (!hasNext())
                throw new IllegalStateException();
            while (storage[currentNumber] == null || storage[currentNumber] == DEL) {
                currentNumber++;
            }
            element = storage[currentNumber];
            countFind++;
            currentNumber++;
            return (K) element;
        }

        @Override
        public void remove() {
            if (element == null)
                throw new IllegalStateException();
            storage[currentNumber - 1] = DEL;
            size--;
            countFind--;
        }
    }
}
