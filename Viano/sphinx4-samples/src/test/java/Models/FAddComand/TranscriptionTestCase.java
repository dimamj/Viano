package Models.FAddComand;

import Models.FAddComand.AbstractAddCommand;
import Models.FAddComand.AddAppCommand;
import Models.FAddComand.FactoryMethod;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.util.Random;

import static org.mockito.Mockito.*;

/**
 * Created by dimamj on 31.05.2015.
 */
public class TranscriptionTestCase {

    @Test
    public void testRuleOne(){ //ударение
        AbstractAddCommand obj = new FactoryMethod().getInstance(true);

        String[] result =  obj.conversionWord("зима",4);

        Assert.assertEquals(result[1],"zz i m aa ");

    }

    @Test
    public void testRuleTwo(){ //удвоение мягкой буквы
        AbstractAddCommand obj = new FactoryMethod().getInstance(true);

        String[] result =  obj.conversionWord("милый",2);

        Assert.assertEquals(result[1],"mm ii l y j ");

    }

    @Test
    public void testRuleTwoFalse(){ //буква ц не должна удваиваться
        AbstractAddCommand obj = new FactoryMethod().getInstance(true);

        String[] result =  obj.conversionWord("юстиция",4);

        Assert.assertEquals(result[1],"j u s tt ii c i j a ");

    }
    /**
     * проверка на то, что согласная буква не будет удваиваться
     * при ошибочном ударении на нее
     */
    @Test
    public void testRuleOneFalse(){
        AbstractAddCommand obj = new FactoryMethod().getInstance(true);

        String[] result =  obj.conversionWord("зима",3);

        Assert.assertEquals(result[1],"zz i m a ");

    }



    @Test
    public void testRuleTwoAll(){
        AbstractAddCommand obj = new FactoryMethod().getInstance(true);
        String[] array = new String[]{"б", "в", "г", "д", "ж", "з",
                "к", "л", "м", "н", "п", "р", "с", "т", "ф", "х", "ц", "ч", "ш", "щ"};
        String[] res = new String[array.length];
        String[] letter = new String[]{"и","е","ю","я"};
        for(int i=0;i<array.length;i++){
            res[i] = obj.conversionWord(array[i]+"и",3)[1];
        }

                Assert.assertEquals(res[0],"bb i ");
                Assert.assertEquals(res[1],"vv i ");
                Assert.assertEquals(res[2],"gg i ");
                Assert.assertEquals(res[3],"dd i ");
                Assert.assertEquals(res[4],"zh i ");
                Assert.assertEquals(res[5],"zz i ");
                Assert.assertEquals(res[6],"kk i ");
                Assert.assertEquals(res[7],"ll i ");
                Assert.assertEquals(res[8],"mm i ");
                Assert.assertEquals(res[9],"nn i ");
                Assert.assertEquals(res[10],"pp i ");
                Assert.assertEquals(res[11],"rr i ");
                Assert.assertEquals(res[12],"ss i ");
                Assert.assertEquals(res[13],"tt i ");
                Assert.assertEquals(res[14],"ff i ");
                Assert.assertEquals(res[15],"hh i ");
                Assert.assertEquals(res[16],"c i ");
                Assert.assertEquals(res[17],"ch i ");
                Assert.assertEquals(res[18],"sh i ");
                Assert.assertEquals(res[19],"sch i ");

    }



}
