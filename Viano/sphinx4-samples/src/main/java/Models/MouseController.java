package Models;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.List;

/**
 * Created by dimamj on 26.04.2015.
 */
public class MouseController extends AbstractController{

    private int speed = 0;


    private static final MouseController instance = new MouseController();

    List<String> list = config.Mouse_Words;

    {
        /*
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

    private MouseController()
    {}

    public static MouseController getInstance()
    {
        return instance;
    }


    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public String startVoiceControl(LiveSpeechRecognizer recognizer, Configuration configuration, Boolean flags) {
            run = flags;

            setGrammar("mouse",configuration,recognizer);
            listener.setImage("mouse active");
            Point location = MouseInfo.getPointerInfo().getLocation();
            while(run)
            {

                String utterance = recognizer.getResult().getHypothesis();
                listener.wordRecognized(utterance);

                if(find(utterance))
                {
                    exitController();
                    return utterance;
                }

                if (search(utterance, list, 5,13))
                {
                    MouseListenerStop mouseListener = new MouseListenerStop(recognizer,configuration,listener,list);
                    Thread thread = new Thread(mouseListener);
                    thread.start();
                    MouseListenerStop.utterance=utterance;
                    Boolean flag = !MouseListenerStop.utterance.equals(list.get(3));
                    while (true)
                    {
                        location = MouseInfo.getPointerInfo().getLocation();
                        double x = location.getX();
                        double y = location.getY();

                        if (MouseListenerStop.utterance.equals(list.get(4))&&flag) //0
                        {
                            x = location.getX();
                            y = location.getY()-1;
                            robot.mouseMove((int)x,(int)y);
                        }
                        else if (MouseListenerStop.utterance.equals(list.get(5))&&flag)//1
                        {
                            x = location.getX()+1;
                            y = location.getY()-1;
                            robot.mouseMove((int)x,(int)y);
                        }
                        else if (MouseListenerStop.utterance.equals(list.get(6))&&flag)
                        {
                            x = location.getX()+1;
                            y = location.getY();
                            robot.mouseMove((int)x,(int)y);
                        }
                        else if (MouseListenerStop.utterance.equals(list.get(7))&&flag)
                        {
                            x = location.getX()+1;
                            y = location.getY()+1;
                            robot.mouseMove((int)x,(int)y);
                        }
                        if (MouseListenerStop.utterance.equals(list.get(3)))
                        {
                            break;
                        }
                        else if (MouseListenerStop.utterance.equals(list.get(8))&&flag)
                        {
                            x = location.getX();
                            y = location.getY()+1;
                            robot.mouseMove((int)x,(int)y);
                        }
                        else if (MouseListenerStop.utterance.equals(list.get(9))&&flag)
                        {
                            x = location.getX()-1;
                            y = location.getY()+1;
                            robot.mouseMove((int)x,(int)y);
                        }
                        else if (MouseListenerStop.utterance.equals(list.get(10))&&flag)
                        {
                            x = location.getX()-1;
                            y = location.getY();
                            robot.mouseMove((int)x,(int)y);
                        }
                        else if (MouseListenerStop.utterance.equals(list.get(11))&&flag)
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


                else if (utterance.equals(list.get(0)))//right click
                {
                    mouseClick(InputEvent.BUTTON3_MASK);
                }
                else if (utterance.equals(list.get(1)))//left click
                {
                    mouseClick(InputEvent.BUTTON1_MASK);
                }
                else if (utterance.equals(list.get(2)))//double click
                {
                    robot.mousePress(InputEvent.BUTTON1_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_MASK);
                    robot.mousePress(InputEvent.BUTTON1_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_MASK);

                }
                else if (utterance.equals(list.get(12)))//колесико
                {
                    mouseClick(InputEvent.BUTTON2_MASK);

                    location = MouseInfo.getPointerInfo().getLocation();
                    double x = location.getX();
                    double y = location.getY();

                    robot.mouseMove((int)x,(int)y+16);
                }

                else if (utterance.equals(list.get(13)))//зажать
                {
                    robot.mousePress(InputEvent.BUTTON1_MASK);
                }

                else if (utterance.equals(list.get(14)))//отпустить
                {
                    robot.mouseRelease(InputEvent.BUTTON1_MASK);

                }
            }


        return null;
    }

    @Override
    public void exitController() {
        listener.setImage("mouse");
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
        RecognitionListener listener;
        List list;
        static String utterance = "" ;
        static String str = "";

        public   MouseListenerStop(LiveSpeechRecognizer recognizer,Configuration configuration,RecognitionListener listener,List list)
        {
            this.recognizer = recognizer;
            this.configuration = configuration;
            this.listener = listener;
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
                else if (search(utterance, list, 0,3))
                {
                    utterance=str;
                }
                listener.wordRecognized(utterance);

                if (utterance.equals(list.get(3)))
                {
                    break;
                }
            }

        }

    }
}
