import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class OpenAddressingHashTable <K, V> implements Map<K, V> {

    private Entry<K, V>[] storage;

    private int maxSize;

    private int countElements = 0;

    private Entry<K, V> DEL;

    public OpenAddressingHashTable() {
        maxSize = 16;
        storage = new Entry[maxSize];
    }

    public OpenAddressingHashTable(int tableSize) {
        maxSize = tableSize;
        storage = new Entry[maxSize];
    }


    @Override
    public int size() {
        return countElements;
    }


    public boolean isEmpty() {
        return countElements == 0;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean containsKey(Object o) {
        if (o == null)
            return false;
        K k = (K) o;
        int index = k.hashCode() % maxSize;
        int startIndex = index;
        do {
            if (storage[index] != null && k.equals(storage[index].getKey()))
                return true;
            index = (index + 1) % maxSize;
        } while (index != startIndex);
        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean containsValue(Object o) {
        if (o == null)
            return false;
        V k = (V) o;
        int index = 0;
        while (index < maxSize) {
            if (storage[index] != null && k.equals(storage[index].getValue()))
                return true;
            index++;
        }
        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public V get(Object o) {
        if (o == null)
            throw new IllegalArgumentException();
        K key = (K) o;
        int index = key.hashCode() % maxSize;
        int startIndex = index;
        do {
            if (storage[index] != null && key.equals(storage[index].getKey()))
                return storage[index].getValue();
            index = (index + 1) % maxSize;
        } while (index != startIndex);
        return null;
    }


    @Nullable
    @Override
    @SuppressWarnings("unchecked")
    public Object put(Object o, Object o2) {
        if (o == null || o2 == null)
            throw new IllegalArgumentException();
        K key = (K) o;
        V value = (V) o2;
        if (countElements < maxSize) {
            if (containsKey(key)) remove(key);
            Entry<K, V> pair = new Entry<>(key, value);
            int hash = key.hashCode() % maxSize;
            while ((storage[hash] != null || storage[hash] != DEL)
                    && !storage[hash].getKey().equals(key)) {
                hash = (hash + 1) % maxSize;
            }
            storage[hash] = pair;
                countElements++;
            return value;
        } else {
            restructuring(key, value);
        }
        return null;
    }

    @Override
    public V remove(Object o) {
        if (o == null)
            throw new IllegalArgumentException();
        K key = (K) o;
        int index = key.hashCode() % maxSize;
        int startIndex = index;
        do {
            if (storage[index] != null && key.equals(storage[index].getKey())) {
                V temp = storage[index].getValue();
                storage[index] = DEL;
                countElements--;
                return temp;
            }
            index = (index + 1) % maxSize;
        } while (index != startIndex);
        return null;
    }

    @Override
    public void putAll(@NotNull Map<? extends K, ? extends V> map) {
        for(Map.Entry<? extends K, ? extends V> entry : map.entrySet())
            put(entry.getKey(), entry.getValue());
    }

    @SuppressWarnings("unchecked")
    public void clear() {
        storage = new Entry[maxSize];
        countElements = 0;
    }

    @NotNull
    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        for (int index = 0; index < maxSize; index++){
            if (storage[index] != null)
                set.add(storage[index].getKey());
        }
        return set;
    }

    @NotNull
    @Override
    public Collection<V> values() {
        Collection<V> result = new ArrayList<>();
        for (int index = 0; index < maxSize; index++){
            if (storage[index] != null)
                result.add(storage[index].getValue());
        }
        return result;
    }

    @NotNull
    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> result = new HashSet<>();
        for (int index = 0; index < maxSize; index++)
            if (storage[index] != null)
                result.add(storage[index]);

        return result;
    }

    @SuppressWarnings("unchecked")
    private void restructuring (K newKey, V newValue){
        Entry<K, V>[] temp = new Entry[storage.length * 2];
        for (Entry<K, V> kvPair : storage) {
            int index = kvPair.getKey().hashCode() % temp.length;
            while (temp[index] != null || temp[index] != DEL) {
                index = (index + 1) % temp.length;
            }
            Entry<K, V> pair = new Entry<>(kvPair.getKey(), kvPair.getValue());
            temp[index] = pair;
        }
        maxSize = temp.length;
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
        for (Entry<K, V> kvPair : storage) {
            if (kvPair != null && kvPair != DEL) {
                if (!forEquals.get(kvPair.getKey()).equals(kvPair.getValue()))
                    return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode(){
        int hash = 0;
        for (Entry<K, V> entry : storage){
            if (entry != null && entry != DEL){
                hash += (entry.hashCode() / countElements);
            }
        }
        return (hash + 31);
    }

    static class Entry<K, V> implements Map.Entry<K ,V> {

        K key;
        V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V v) {
            value = v;
            return value;
        }

        @Override
        public boolean equals(Object o){
            if (o == null || getClass() != o.getClass())
                return false;
            Entry<K, V> entry = (Entry) o;
            return getValue().equals(entry.getValue()) && getKey().equals(entry.getKey());
        }

        @Override
        public int hashCode(){
            int temp = key.hashCode() + value.hashCode();
            return (31 + temp / 2);
        }
    }

    public Iterator<Map.Entry<K, V>> iterator() {
        return new OAHTIterator();
    }

    private class OAHTIterator implements Iterator<Map.Entry<K, V>> {

        private int currentNumber = 0;

        List<OpenAddressingHashTable.Entry<K, V>> list = new ArrayList<>();

        OAHTIterator(){
            for (Entry<K, V> entry : storage) {
                if (entry != null && entry != DEL)
                    list.add(entry);
            }
        }

        @Override
        public boolean hasNext() {
            return currentNumber < list.size();
        }

        @Override
        public Entry<K , V> next() {
            if (!hasNext())
                throw new IllegalStateException();
            while (list.get(currentNumber) == null || list.get(currentNumber) == DEL) {
                currentNumber++;
            }
            Entry<K, V> element = list.get(currentNumber);
            currentNumber++;
            return element;
        }
    }
}
