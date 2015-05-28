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


import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.List;


public class Master extends AbstractController {


    private static final Master instance = new Master();

    private Master()
    {}

    public static Master getInstance()
    {
        return instance;
    }

    static Boolean flag = false;

    private    List<String> Master_Words;

    private  String path = "C:/Viano/data/language.txt";
    private static String pathconfig = "C:/Viano/data/config.txt";


    @Override
    public void exitController() {
        listener.setImage("master");
    }

    public String startVoiceControl(LiveSpeechRecognizer jsgfRecognizer,Configuration configuration,Boolean flag) {
        run = flag;
        Master_Words = config.Master_Words;

        setGrammar("master", configuration, jsgfRecognizer);
        listener.setImage("masterActive");


        while (run) {

            String  utterance = jsgfRecognizer.getResult().getHypothesis();

            listener.wordRecognized(utterance);

            if(find(utterance))
            {
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

            else if (utterance.equals(Master_Words.get(6))) //Settings
            {
                listener.createGui("setting",config.getFilecontain());
                listener.setText("setting",conf);
                Thread thread = new Thread(new Parameters());//////////////////////////////////////
                thread.start();
            }
            else if (utterance.equals(Master_Words.get(7)))
            {

                listener.setImage(Master_Words.get(7));

                while (true)
                {
                    utterance = jsgfRecognizer.getResult().getHypothesis();
                    listener.wordRecognized(utterance);

                    if (utterance.equals("английский") || utterance.equals("english"))
                    {
                        listener.disposeGui("main");
                        config.wtite(path, "english\ntrue");
                        jsgfRecognizer.stopRecognition();
                        restart();
                        break;

                    }
                    else if (utterance.equals("русский")|| utterance.equals("russian"))
                    {
                        listener.disposeGui("main");
                        config.wtite(path, "russian\ntrue");
                        jsgfRecognizer.stopRecognition();
                        restart();
                        break;

                    }
                    else if (utterance.equals("назад") || utterance.equals("back"))
                    {
                        listener.setImage("masterActive");
                        break;
                    }
                }

            }

            else if(utterance.equals(Master_Words.get(8))){   //add comand

                listener.createGui("addCommand",config.getFilecontain());
                while (!listener.getEdit("addCommand")){}
                String[] array = listener.getText("addCommand");
                if(array[1]!=null)
                if(array[1].split(" ").length>1){
                    listener.errorMessage("Error: Code #4");
                    listener.disposeGui("addCommand");
                }
                else if(array[0]!=null&&array[1]!=null&&array[3]!=null) {
                    listener.addCommand(array);
                    listener.disposeGui("addCommand");
                }

            }

        }

        return "";

    }

    private void restart(){
        if(config.isJar()) {
            String[] str = Config.class.getResource("").toString().split("!");
            String path = str[0].substring(10);
            System.out.println(path);
            try {
                Runtime.getRuntime().exec("java -jar " + path);
                System.exit(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.exit(0);
        }
    }

    private static class Parameters implements Runnable
    {
        @Override
        public void run() {
            while (true)
            {
                if (listener.getEdit("setting"))
                {
                    conf  = config.readMas(pathconfig);
                    config.startup(conf);
                    break;
                }
            }
        }
    }

}
