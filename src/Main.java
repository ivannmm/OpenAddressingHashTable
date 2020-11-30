public class Main {
    public static void main(String[] args) {
        OpenAddressingHashTable<Integer, String> test = new OpenAddressingHashTable<>();
        test.put(1, "ass");
        System.out.println(test.containsKey(1));
        System.out.println(test.isEmpty());
        System.out.println(test.getOrDefault(1));
        System.out.println(test.containsValue("ass"));
        System.out.println(test.containsValue("asss"));
        test.clear();
        test.put(10, "ass");

    }
}
