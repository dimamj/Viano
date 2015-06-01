package Models;

import org.junit.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dimamj on 31.05.2015.
 */
public class AbstractCtrlTestCase {

    @org.junit.Test
    public void testSearchTrue(){
        List<String> list = Arrays.asList("dog","horse","bird","pig","cat");
        Boolean result =   AbstractController.search("cat", list, 2, 6);
        Assert.assertTrue(result);

    }

    @org.junit.Test
    public void testSearchCtrlTrue(){
       Config config = new Config();
        config.Modules_Words = Arrays.asList("master","computer","internet",
                "apps","keyboard","mouse");
        AbstractController obj = Master.getInstance();
        obj.setConfig(config);

        Assert.assertTrue(obj.find("mouse"));

    }
}
