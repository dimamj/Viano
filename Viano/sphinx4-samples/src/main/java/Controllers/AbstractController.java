package Controllers;

import View.CtrlGui;
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;

/**
 * Created by dimamj on 14.01.2015.
 */
public abstract class AbstractController implements VoiceControl{

 public     Robot robot;


    {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            /*NOP*/
        }
    }

    public abstract void startVoiceControl(LiveSpeechRecognizer jsgfRecognizer,Configuration config, CtrlGui g,List list);

    public   void setGrammar(String grammar,Configuration config,LiveSpeechRecognizer jsgfRecognizer)
    {
        config.setGrammarName(grammar);
        jsgfRecognizer.stopRecognition();
        jsgfRecognizer.setContext(config);
        jsgfRecognizer.startRecognition(true);
    }
    {
/*
              KeyBoard_Words
               0     ,"вверх"
               1     ,"вниз"
               2     ,"вправо"
               3     ,"влево"
               4     ,"назад"
               5     ,"свернуть"
               6     ,"закрыть"
               7     ,"войти"
               8     ,"переключиться" не работает alt
               9     ,"копировать"
               10     ,"вставить"
               11     ,"вырезать"
               12     ,"отменить"
               13     ,"удалить"
               14     ,"выделить все"
               15     ,"пуск"
               16     ,"диспетчер"
               17     ,"таб"
               18     ,"эскейп"
               19     ,"пробел"
               ------
                Mouse_Words
                0    ,"назад"
                1    ,"правый клик"
                2    ,"левый клик"
                3    ,"двойной клик"
                4    ,"пауза"
                5    ,"двенадцать"
                6    ,"один"
                7    ,"два"
                8    ,"три"
                9    ,"четыре"
                10    ,"пять"
                11    ,"шесть"
                12    ,"семь"
                13    ,"колесико"
                14    ,"зажать"
                15    ,"отмена"

*/
    }
    public  void keyboardControl(LiveSpeechRecognizer recognizer,Configuration configuration,CtrlGui gui,List list)
    {
        setGrammar("keyboard", configuration, recognizer);
        gui.setImage("keyboard active");
        while (true)
        {
            String utterance = recognizer.getResult().getHypothesis();
            gui.setWords(utterance);

            if (utterance.equals(list.get(0)))
            {
                oneButtonPress(KeyEvent.VK_UP);
            }
            else if (utterance.equals(list.get(1)))
            {
                oneButtonPress(KeyEvent.VK_DOWN);
            }
            else if (utterance.equals(list.get(2)))
            {
                oneButtonPress(KeyEvent.VK_RIGHT);
            }
            else if (utterance.equals(list.get(3)))
            {
                oneButtonPress(KeyEvent.VK_LEFT);
            }
            else if (utterance.equals(list.get(4)))
            {
                break;
            }
            else if (utterance.equals(list.get(5)))
            {
                twoButtonPress(KeyEvent.VK_WINDOWS,KeyEvent.VK_D);
            }
            else if (utterance.equals(list.get(6)))//close
            {
                twoButtonPress(KeyEvent.VK_ALT, KeyEvent.VK_F4);
            }
            else if (utterance.equals(list.get(7)))//ENTER
            {
                oneButtonPress(KeyEvent.VK_ENTER);

            }
            else if (utterance.equals(list.get(8)))//ALT+TAB
            {
                oneButtonPress(KeyEvent.VK_F6);
            }
            else if (utterance.equals(list.get(9)))//copy
            {
                twoButtonPress(KeyEvent.VK_CONTROL,KeyEvent.VK_C);
            }
            else if (utterance.equals(list.get(10)))//paste
            {
                twoButtonPress(KeyEvent.VK_CONTROL, KeyEvent.VK_V);
            }
            else if (utterance.equals(list.get(11)))//cut
            {
                twoButtonPress(KeyEvent.VK_CONTROL, KeyEvent.VK_X);
            }
            else if (utterance.equals(list.get(12)))//CTRL+Z
            {
                twoButtonPress(KeyEvent.VK_CONTROL, KeyEvent.VK_Z);
            }
            else if (utterance.equals(list.get(13)))//Delete
            {
               oneButtonPress(KeyEvent.VK_DELETE);
            }
            else if (utterance.equals(list.get(14)))//CTRL+A
            {
                twoButtonPress(KeyEvent.VK_CONTROL, KeyEvent.VK_A);
            }
            else if (utterance.equals(list.get(15)))//start menu
            {
                oneButtonPress(KeyEvent.VK_WINDOWS);
            }
            else if (utterance.equals(list.get(16)))//
            {
                robot.keyPress(KeyEvent.VK_CONTROL);
                robot.keyPress(KeyEvent.VK_SHIFT);
                robot.keyPress(KeyEvent.VK_ESCAPE);
                robot.keyRelease(KeyEvent.VK_CONTROL);
                robot.keyRelease(KeyEvent.VK_SHIFT);
                robot.keyRelease(KeyEvent.VK_ESCAPE);
            }
            else if (utterance.equals(list.get(17)))//
            {
                 oneButtonPress(KeyEvent.VK_TAB);
            }
            else if (utterance.equals(list.get(18)))//
            {
                oneButtonPress(KeyEvent.VK_ESCAPE);
            }
            else if (utterance.equals(list.get(19)))//
            {
                oneButtonPress(KeyEvent.VK_SPACE);
            }

        }
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

    public  void mouseControl(LiveSpeechRecognizer recognizer,Configuration configuration,CtrlGui gui,List list,int speed)
    {
        setGrammar("mouse",configuration,recognizer);
        gui.setImage("mouse active");

        Point location = MouseInfo.getPointerInfo().getLocation();
        while(true)
        {

            String utterance = recognizer.getResult().getHypothesis();
            gui.setWords(utterance);

            if (search(utterance, list, 5,13))
            {
                MouseListenerStop listener = new MouseListenerStop(recognizer,configuration,gui,list);
                Thread thread = new Thread(listener);
                thread.start();
                MouseListenerStop.utterance=utterance;
                Boolean flag = !MouseListenerStop.utterance.equals(list.get(4));
                while (true)
                {
                    location = MouseInfo.getPointerInfo().getLocation();
                    double x = location.getX();
                    double y = location.getY();

                    if (MouseListenerStop.utterance.equals(list.get(5))&&flag) //0
                    {
                        x = location.getX();
                        y = location.getY()-1;
                        robot.mouseMove((int)x,(int)y);
                    }
                    else if (MouseListenerStop.utterance.equals(list.get(6))&&flag)//1
                    {
                        x = location.getX()+1;
                        y = location.getY()-1;
                        robot.mouseMove((int)x,(int)y);
                    }
                    else if (MouseListenerStop.utterance.equals(list.get(7))&&flag)
                    {
                        x = location.getX()+1;
                        y = location.getY();
                        robot.mouseMove((int)x,(int)y);
                    }
                    else if (MouseListenerStop.utterance.equals(list.get(8))&&flag)
                    {
                        x = location.getX()+1;
                        y = location.getY()+1;
                        robot.mouseMove((int)x,(int)y);
                    }
                    if (MouseListenerStop.utterance.equals(list.get(4)))
                    {
                        break;
                    }
                    else if (MouseListenerStop.utterance.equals(list.get(9))&&flag)
                    {
                        x = location.getX();
                        y = location.getY()+1;
                        robot.mouseMove((int)x,(int)y);
                    }
                    else if (MouseListenerStop.utterance.equals(list.get(10))&&flag)
                    {
                        x = location.getX()-1;
                        y = location.getY()+1;
                        robot.mouseMove((int)x,(int)y);
                    }
                    else if (MouseListenerStop.utterance.equals(list.get(11))&&flag)
                    {
                        x = location.getX()-1;
                        y = location.getY();
                        robot.mouseMove((int)x,(int)y);
                    }
                    else if (MouseListenerStop.utterance.equals(list.get(12))&&flag)
                    {
                        x = location.getX()-1;
                        y = location.getY()-1;
                        robot.mouseMove((int)x,(int)y);
                    }

                    try {
                        Thread.sleep(speed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                }

            }

            else if (utterance.equals(list.get(0)))
            {
                break;
            }
            else if (utterance.equals(list.get(1)))//right click
            {
                mouseClick(InputEvent.BUTTON3_MASK);
            }
            else if (utterance.equals(list.get(2)))//left click
            {
                mouseClick(InputEvent.BUTTON1_MASK);
            }
            else if (utterance.equals(list.get(3)))//double click
            {
                robot.mousePress(InputEvent.BUTTON1_MASK);
                robot.mouseRelease(InputEvent.BUTTON1_MASK);
                robot.mousePress(InputEvent.BUTTON1_MASK);
                robot.mouseRelease(InputEvent.BUTTON1_MASK);

            }
            else if (utterance.equals(list.get(13)))//колесико
            {
                mouseClick(InputEvent.BUTTON2_MASK);

                location = MouseInfo.getPointerInfo().getLocation();
                double x = location.getX();
                double y = location.getY();

                robot.mouseMove((int)x,(int)y+16);
            }

            else if (utterance.equals(list.get(14)))//зажать
            {
                robot.mousePress(InputEvent.BUTTON1_MASK);
            }

            else if (utterance.equals(list.get(15)))//отпустить
            {
               robot.mouseRelease(InputEvent.BUTTON1_MASK);

            }
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
    private  void mouseClick(int a)
    {
        robot.mousePress(a);
        robot.delay(300);
        robot.mouseRelease(a);
    }

    private static class MouseListenerStop implements Runnable
    {
        LiveSpeechRecognizer recognizer;
        Configuration configuration;
        CtrlGui gui;
        List list;
        static String utterance = "" ;
        static String str = "";

      public   MouseListenerStop(LiveSpeechRecognizer recognizer,Configuration configuration,CtrlGui gui,List list)
      {
            this.recognizer = recognizer;
            this.configuration = configuration;
            this.gui = gui;
            this.list = list;
      }

        public void run()
        {

            while (true)
            {
                str = utterance;
                utterance = recognizer.getResult().getHypothesis();
                if(utterance.equals("<unk>"))
                {
                    utterance=str;
                }
                else if (search(utterance, list, 0,4))
                {
                    utterance=str;
                }
                gui.setWords(utterance);

                if (utterance.equals(list.get(4)))
                {
                    break;
                }
            }

        }

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
