package Models;

import Presenter.RecognitionListener;
import Presenter.VPresenter;
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by dimamj on 14.01.2015.
 */
public abstract class AbstractController {

    protected Robot robot;
    protected static RecognitionListener listener;
    protected static Config config;

    protected Boolean run = false;

    protected static List<String> conf;

    public  RecognitionListener getListener() {
        return listener;
    }

    public void setConfig(Config config) {
        this.config = config;
    }



    public void setListener(RecognitionListener listener) {
        this.listener = listener;
        conf = config.getConfList();
    }

    {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            /*NOP*/
        }
    }

    /**
     * Данный метод реализован в каждом из слушателей.
     * Он является стартовым для каждого из слушателей, в нем в цикде
     * вызывается метод ожилающий речь и он содержит реализацию всех команд.
     * Результат распознавания проходит по многочисленным условиям проверки
     * на соответствие, в случае истины, команда выполняется.
     * Метод останавливается, как только результат распознавания
     * равен названию какого либо слушателя. В этом случае
     * происхоит возврат имени слушателя.
     *
     * @param jsgfRecognizer обьект для распознавания речи
     * @param config параметры для распознавания
     * @param run переменная позволяющая остановить цикл
     * @return имя слушателя, на который произойдет переход
     */
    public abstract String startVoiceControl(LiveSpeechRecognizer jsgfRecognizer,Configuration config,Boolean run);

    /**
     * Данный метод выывается после любого распознавания слова
     * и результат передается в качестве входного параметра.
     * Есди совпадение есть, метод возвращает истину.
     *
     * @param str результат распознавания
     * @return истина если есть совпадение
     */
    protected Boolean find(String str) {
        List<String> MWords = config.Modules_Words;

        if(str.equals("виано")||str.equals("viano")){
            showViano();
        }

        return  str.equals(MWords.get(0)) ? true: //Master
                str.equals(MWords.get(1)) ? true: //Computer
                str.equals(MWords.get(2)) ? true: //Internet
                str.equals(MWords.get(3)) ? true: //Apps
                str.equals(MWords.get(4)) ? true: //Keyboard
                str.equals(MWords.get(5)) ? true: //Mouse
                 false;

    }

    /**
     *  Данный метод вызывается при переходе на другой слушатель
     *  он останавливает прослушивание и меняет грамматику, после
     *  чего снова запускает прослушивание
     *
     * @param grammar имя грамматики без расширений
     * @param config  параметры для распознавания
     * @param jsgfRecognizer обьект для распознавания
     */
    protected void setGrammar(String grammar,Configuration config,LiveSpeechRecognizer jsgfRecognizer) {
        config.setGrammarName(grammar);
        jsgfRecognizer.stopRecognition();
        jsgfRecognizer.setContext(config);
        jsgfRecognizer.startRecognition(true);
    }

    public void showViano() {

            runApplications("C:/Viano/data/cmdow.exe" +" Viano /ACT");
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        twoButtonPress(KeyEvent.VK_WINDOWS, KeyEvent.VK_UP);
    }

    public abstract void exitController();



    public void audio(String str){
        Clip c = null;
        try {
            c = AudioSystem.getClip();
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File(str));
            c.open(ais);
            c.loop(0);
            Thread.sleep(700);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Данный метод ищет совпадение указанного слова в указанном
     * списке и в указанном диапазоне.
     * Он нужен например, если необходимо запускать какое либо действие
     * для целого диапаона команд, к примеру управление курсором, оно
     * активизируется как только пользователь скажет любое число.
     *
     * @param s результат распознавания
     * @param list список слушателя
     * @param a начало диапазона
     * @param b конец диапазона
     *
     * @return истина если есть вопадение
     */
    public static Boolean search(String s,List list,int a,int b) {
        Boolean flag = false;
       for (int i=a;i<b;i++) {
           if(s.equals(list.get(i))) {
               flag=true;
               break;
           }

       }
        return flag;
    }

    public void oneButtonPress(int key) {
        robot.keyPress(key);
        robot.keyRelease(key);
    }
    public void oneButtonPress(int key,int delay) {
        robot.keyPress(key);
        robot.delay(delay);
        robot.keyRelease(key);
    }
    public void twoButtonPress(int key,int key_two) {
        robot.keyPress(key);
        robot.keyPress(key_two);
        robot.keyRelease(key);
        robot.keyRelease(key_two);
    }


    public void runApplications(String str) {
        try {
            Runtime.getRuntime().exec(str);;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
