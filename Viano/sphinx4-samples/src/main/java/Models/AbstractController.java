package Models;

import Presenter.VPresenter;
import View.CtrlGui;
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dimamj on 14.01.2015.
 */
public abstract class AbstractController {

    protected Robot robot;
    protected static RecognitionListener listener;
    protected static Config config;

    protected Boolean run = false;

    {
        setConfig(VPresenter.getConfig());
    }

    protected static List<String> conf = config.getConfList();////////

    public  RecognitionListener getListener() {
        return listener;
    }

    public void setConfig(Config config) {
        this.config = config;
    }



    public void setListener(RecognitionListener listener) {
        this.listener = listener;
    }

    {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            /*NOP*/
        }
    }

    public abstract String startVoiceControl(LiveSpeechRecognizer jsgfRecognizer,Configuration config,Boolean run);

    protected Boolean find(String str)
    {
        List<String> MWords = config.Modules_Words;

        return  str.equals(MWords.get(0)) ? true: //Master
                str.equals(MWords.get(1)) ? true: //Computer
                str.equals(MWords.get(2)) ? true: //Internet
                str.equals(MWords.get(3)) ? true: //Apps
                str.equals(MWords.get(4)) ? true: //Keyboard
                 false;

    }

    public   void setGrammar(String grammar,Configuration config,LiveSpeechRecognizer jsgfRecognizer)
    {
        config.setGrammarName(grammar);
        jsgfRecognizer.stopRecognition();
        jsgfRecognizer.setContext(config);
        jsgfRecognizer.startRecognition(true);
    }


    public abstract void exitController();



    public void audio(String str) //!поток не закрыт
    {
        Clip c = null;
        try {
            c = AudioSystem.getClip();
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File(Master.class.getResource(str).getFile()));
            c.open(ais);
            c.loop(0);
            Thread.sleep(700);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Boolean search(String s,List list,int a,int b)
    {
        Boolean flag = false;
       for (int i=a;i<b;i++)
       {
           if(s.equals(list.get(i)))
           {
               flag=true;
               break;
           }

       }
        return flag;
    }

    public void oneButtonPress(int key)
    {
        robot.keyPress(key);
        robot.keyRelease(key);
    }
    public void oneButtonPress(int key,int delay)
    {
        robot.keyPress(key);
        robot.delay(delay);
        robot.keyRelease(key);
    }
    public void twoButtonPress(int key,int key_two)
    {
        robot.keyPress(key);
        robot.keyPress(key_two);
        robot.keyRelease(key);
        robot.keyRelease(key_two);
    }


    public void runApplications(String str)
    {
        try {
            Runtime.getRuntime().exec(str);;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
