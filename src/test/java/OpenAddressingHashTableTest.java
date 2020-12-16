import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class OpenAddressingHashTableTest {
    OpenAddressingHashTable<Integer, String> tableOne = new OpenAddressingHashTable<Integer, String>();

    @Test
    public void isContain(){
        tableOne.put(1, "ttt");
        Assert.assertTrue(tableOne.containsKey(1));
    }

    OpenAddressingHashTable<Integer, String> tableTwo = new OpenAddressingHashTable<Integer, String>(1);

    @Test
    public void isOk(){
        tableTwo.put(2, "ewe");
        tableTwo.put(3, "ewe");
        Assert.assertEquals(2, tableTwo.size());
    }

    @Test
    public void isOk2(){
        tableOne.clear();
        tableTwo.clear();
        for (int i = 0; i < 99; i++) {
            tableOne.put(i*2, "Тек" + i);
            tableTwo.put(i*2, "Тек" + i);
        }
        Assert.assertEquals(tableOne, tableTwo);
        tableTwo.put(1231, "forTest");
        Assert.assertNotEquals(tableOne, tableTwo);
        tableOne.clear();
        tableTwo.clear();
        Assert.assertEquals(tableOne, tableTwo);
    }

    @Test
    public void isOk3(){
        tableOne.clear();
        tableOne.put(12312, "test");
        Assert.assertEquals("test", tableOne.get(12312));
        tableOne.put(12312, "t");
        Assert.assertEquals("t", tableOne.get(12312));
        Assert.assertEquals(1, tableOne.size());
        OpenAddressingHashTable<Integer, String> tableThree = new OpenAddressingHashTable<>();
        tableThree.putAll(tableOne);
        Assert.assertEquals(true, tableThree.containsKey(12312));
        Assert.assertEquals(1, tableThree.size());
    }

    @Test
    public void isOk4(){
        OpenAddressingHashTable<Integer, String> tableTest1 = new OpenAddressingHashTable<>();
        OpenAddressingHashTable<Integer, String> tableTest2 = new OpenAddressingHashTable<>();

        for (int i = 0; i < 100; i++){
            tableTest1.put(i, "testtest" + i);
            tableTest2.put(i, "testtest" + i);
        }

        Assert.assertEquals(tableTest1.hashCode(), tableTest2.hashCode());
        tableTest2.clear();
        tableTest2.putAll(tableTest1);
        Assert.assertEquals(tableTest1, tableTest2);
        Assert.assertEquals(tableTest1.hashCode(), tableTest2.hashCode());

        Iterator it1 = tableTest1.iterator();
        Iterator it2 = tableTest2.iterator();
        while (it1.hasNext()) {
            Assert.assertEquals(it1.next(), it2.next());
        }
    }

    @Test
    public void isOk5(){
        OpenAddressingHashTable<Integer, String> tableTest1 = new OpenAddressingHashTable<>();
        Map<Integer, String> tableTest2 = new HashMap<>();
        Assert.assertTrue(tableTest1.isEmpty());
        tableTest1.put(1, "qweqwe");
        tableTest1.put(2, "qweqwe2");
        tableTest1.put(3, "qweqwe3");
        tableTest1.put(4, "qweqwe4");
        tableTest1.put(5, "qweqwe5");
        Assert.assertEquals("qweqwe", tableTest1.remove(1));
        Assert.assertFalse(tableTest1.containsKey(null));
        Assert.assertFalse(tableTest1.containsValue("qweqwe"));
        Assert.assertFalse(tableTest1.containsValue(null));
        Assert.assertFalse(tableTest1.containsKey(1));
        Assert.assertTrue(tableTest1.containsKey(4));
        Assert.assertNotEquals(tableTest1, tableTest2);

        OpenAddressingHashTable<Integer, String> tableTest3 = new OpenAddressingHashTable<>();
        tableTest3.putAll(tableTest1);
        Assert.assertEquals(tableTest3.values(), tableTest1.values());
        Assert.assertEquals(tableTest3.keySet(), tableTest1.keySet());
    }
}
