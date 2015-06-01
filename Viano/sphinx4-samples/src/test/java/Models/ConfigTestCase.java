package Models;

import org.junit.*;
import static org.mockito.Mockito.*;
import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by dimamj on 31.05.2015.
 */
public class ConfigTestCase {

    @org.junit.Test
    public void testParserWords(){
        Config config = new Config();
        ArrayList<String> list = new ArrayList<>();
        ArrayList<String> spy = spy(list);
        list.add("asd");
        config.setEx(spy);

        doNothing().when(spy).clear();

        config.parserWords("C:/Viano/data/words/words_rus.txt");

        verify(spy,times(7)).clear();
    }
}
