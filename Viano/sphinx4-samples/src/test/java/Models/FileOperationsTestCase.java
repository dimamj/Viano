package Models;

import org.junit.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

/**
 * Created by dimamj on 31.05.2015.
 */
public class FileOperationsTestCase {


    @org.junit.Test
    public void testReadMasTrue(){
        ArrayList<String> list = mock(ArrayList.class);
        FileOperations obj = FileOperations.getInstance();

        obj.readMas("C:/Viano/data/config.txt",list);

        verify(list,times(5)).add(anyInt(),anyString());
    }


    @org.junit.Test
    public void testReadOneLine(){
        FileOperations obj = FileOperations.getInstance();

        String result = obj.read("C:/Viano/data/language.txt",1);

        Assert.assertEquals(result,"russian");
    }

}
