/*
 * Copyright 2013 Carnegie Mellon University.
 * Portions Copyright 2004 Sun Microsystems, Inc.
 * Portions Copyright 2004 Mitsubishi Electric Research Laboratories.
 * All Rights Reserved.  Use is subject to license terms.
 *
 * See the file "license.terms" for information on usage and
 * redistribution of this file, and for a DISCLAIMER OF ALL
 * WARRANTIES.
 */

package Models;


import Presenter.VPresenter;
import View.CtrlGui;
import View.Gui;
import View.Settings;
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.*;
import java.util.List;


public class Master extends AbstractController {

    {
        setConfig(VPresenter.getConfig());
    }

    private static final Master instance = new Master();

    private Master()
    {}

    public static Master getInstance()
    {
        return instance;
    }

    static Boolean flag = false;

    public    List<String> Master_Words = config.Master_Words;
    static  List<String> conf  = new ArrayList<String>();

    private     String WeatherURL = "google.com.ua/search?q=weather";
    private     String NewsURL = "ukr.net";
    private     String startupFlag = "false";
    private     String FilmsURL = "kinogo.net";
    private     String TorrentURL = "rutorg.org";


    static Settings settings;
    private  String filecontain = "";
    private  String path = "C:"+ File.separator+ "Viano" +File.separator+"language.txt";
    private static String pathconfig = "C:"+ File.separator+ "Viano" +File.separator+"config.txt";

    private static Configuration configuration;




    public  void wtite(String path,String language)
    {

        try {
            PrintWriter out = new PrintWriter(path);
            out.print(language);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void exitController() {

    }

    public String startVoiceControl(LiveSpeechRecognizer jsgfRecognizer,Configuration configuration,Boolean flag) {
        run = flag;

            setGrammar("master", configuration, jsgfRecognizer);
            listener.setImage("");


        while (run) {
            String utterance = jsgfRecognizer.getResult().getHypothesis();
          //  ctrlGui.setWords(utterance);
            listener.wordRecognized(utterance);

            if(find(utterance))
            {
                return utterance;
            }

            else if (utterance.equals(Master_Words.get(4)))//Mouse
            {
                MouseController.getInstance().setSpeed(15);
                exitController();
                return utterance;
            }
            else if (utterance.equals(Master_Words.get(5))) //Shutdown
            {
                try {
                    Robot robot = new Robot();
                    robot.keyPress(KeyEvent.VK_WINDOWS);
                    robot.keyPress(KeyEvent.VK_D);
                    robot.keyRelease(KeyEvent.VK_WINDOWS);
                    robot.keyRelease(KeyEvent.VK_D);
                    robot.delay(500);
                    robot.keyPress(KeyEvent.VK_ALT);
                    robot.keyPress(KeyEvent.VK_F4);
                    robot.keyRelease(KeyEvent.VK_ALT);
                    robot.keyRelease(KeyEvent.VK_F4);
                } catch (AWTException e) {
                    /*NOP*/
                }
            }
            else if (utterance.equals("закрыть"))
            {
                break;
            }
            else if (utterance.equals(Master_Words.get(6))) //Settings
            {
                settings = new Settings();
                settings.setText(conf.get(1),conf.get(2),conf.get(0),conf.get(3),conf.get(4));
             /*   Thread thread = new Thread(new Parameters());//////////////////////////////////////
                thread.start();*/
            }
            else if (utterance.equals(Master_Words.get(7)))
            {
                /*
                listener.setLanguageText(filecontain);

                while (true)
                {
                    utterance = jsgfRecognizer.getResult().getHypothesis();
                    listener.setWords(utterance);

                    if (utterance.equals("английский") || utterance.equals("english"))
                    {
                        listener.setTreyMessage("Reset Viano");
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        listener.disposeGui();
                        wtite(path, "english");
                        jsgfRecognizer.stopRecognition();
                        System.exit(0);
                        break;

                    }
                    else if (utterance.equals("русский")|| utterance.equals("russian"))
                    {
                        listener.setTreyMessage("Reset Viano");
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        listener.disposeGui();
                        wtite(path, "russian");
                        jsgfRecognizer.stopRecognition();
                        System.exit(0);
                        break;

                    }
                    else if (utterance.equals("назад") || utterance.equals("back"))
                    {
                        listener.setImage("");
                        break;
                    }
                }
*/
            }

        }

        return "";

    }



    /*
    private static class Parameters implements Runnable
    {
        @Override
        public void run() {
            while (true)
            {
                if (settings.getEdit())
                {
                    conf  = readMas(pathconfig);
                    internetCtrl.setConfig(conf);
                    break;
                }
            }
        }
    }
    */
}
