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
}
