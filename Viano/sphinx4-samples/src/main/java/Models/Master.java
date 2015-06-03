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

    /*
    !# Master
    компьютер 0
    браузер 1
    приложения 2
    клавиатура 3
    мышь 4
    завершить работу 5
    настройки6
    сменить язык7
    добавить команду8
    #
     */

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

            if(find(utterance)) {
                exitController();
                return utterance;
            }

            /*
             Запуск Завершения работы
             */
            else if (utterance.equals(Master_Words.get(5))){ //Shutdown
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
            /*
            Запуск настроек
             */
            else if (utterance.equals(Master_Words.get(6))){ //Settings
                listener.createGui("setting",config.getFilecontain());
                listener.setText("setting",conf);
                Thread thread = new Thread(new Parameters());
                thread.start();
            }
            /*
            Команда смены языка
             */
            else if (utterance.equals(Master_Words.get(7))) {

             changeLang(utterance,jsgfRecognizer);

            }
            /*
            Запуск добавления команды
             */
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


    private void changeLang(String utterance,LiveSpeechRecognizer jsgfRecognizer){
        listener.setImage(Master_Words.get(7));

        while (true) {
            utterance = jsgfRecognizer.getResult().getHypothesis();
            listener.wordRecognized(utterance);

            if (utterance.equals("английский") || utterance.equals("english")) {
                listener.disposeGui("main");
                config.getFop().write(path, "english\ntrue");
                jsgfRecognizer.stopRecognition();
                restart();
                break;

            }
            else if (utterance.equals("русский")|| utterance.equals("russian")) {
                listener.disposeGui("main");
                config.getFop().write(path, "russian\ntrue");
                jsgfRecognizer.stopRecognition();
                restart();
                break;

            }
            else if (utterance.equals("назад") || utterance.equals("back")) {
                listener.setImage("masterActive");
                break;
            }
        }
    }
    /**
     * Метод перезапускает запущенный Jar файл. То есть
     * берется путь к файлу и запускается через командную строку.
     */
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

    /*
    Поток который ожидает сохранение настроек в окне настроек.
     */
    private static class Parameters implements Runnable
    {
        @Override
        public void run() {
            while (true) {
                if (listener.getEdit("setting")) {
                    conf  =   config.getFop().readMas(pathconfig,conf);
                    config.startup(conf);
                    break;
                }
            }
        }
    }

}
