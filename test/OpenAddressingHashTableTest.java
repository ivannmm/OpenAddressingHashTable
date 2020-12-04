import org.junit.Assert;
import org.junit.Test;

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
        Assert.assertEquals(2, tableTwo.getSize());
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
        tableOne.put(12312, "test");
        Assert.assertEquals("test", tableOne.getOrDefault(12312));
        tableOne.put(12312, "t");
        Assert.assertEquals("t", tableOne.getOrDefault(12312));
    }
}
