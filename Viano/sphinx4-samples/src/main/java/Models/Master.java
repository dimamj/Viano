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

    private static final String ACOUSTIC_MODEL_ENG =
            "resource:/edu/cmu/sphinx/models/en-us/en-us";
    private static final String DICTIONARY_PATH_ENG =
            "resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict";
    private static final String GRAMMAR_PATH_ENG =
            "resource:/gram_eng/";
    private static final String ACOUSTIC_MODEL_RUS =
            "resource:/edu/cmu/sphinx/models/en-us/rus";
    private static final String DICTIONARY_PATH_RUS =
            "resource:/edu/cmu/sphinx/models/en-us/msu_ru_nsh.dic";
    private static final String GRAMMAR_PATH_RUS =
            "resource:/gram_rus/";
    static Boolean flag = false;

  public   static  List<String> Master_Words = new ArrayList<String>();
    public  static  List<String> KeyBoard_Words = new ArrayList<String>();
    public  static  List<String> Mouse_Words = new ArrayList<String>();
    public  static  List<String> Computer_Words = new ArrayList<String>();
    public  static  List<String> Internet_Words = new ArrayList<String>();
    public  static  List<String> Applications_Words = new ArrayList<String>();
    public  static  List<String> Paint_Words = new ArrayList<String>();
    public  static  List<String> Racing_Words = new ArrayList<String>();
    static  List<String> conf  = new ArrayList<String>();

    private     String WeatherURL = "google.com.ua/search?q=weather";
    private     String NewsURL = "ukr.net";
    private     String startupFlag = "false";
    private     String FilmsURL = "kinogo.net";
    private     String TorrentURL = "rutorg.org";


    static Gui gui;
    static CtrlGui ctrlGui;
    static Settings settings;
    private  String filecontain = "";
    private  String path = "C:"+ File.separator+ "Viano" +File.separator+"language.txt";
    private static String pathconfig = "C:"+ File.separator+ "Viano" +File.separator+"config.txt";

    private static Configuration configuration;

    private  static  ComputerCtrl computerCtrl = ComputerCtrl.getInstance();
    private  static  InternetCtrl internetCtrl = InternetCtrl.getInstance();
    private  static  AppsCtrl appsCtrl = AppsCtrl.getInstance();

    private  String mediaPath = "C:/Program Files/Windows Media Player/wmplayer.exe";
    private  String mediaLink = "C:/Viano/Applications/wmplayer.lnk";
    private  String paintNetPath = "C:/Program Files/Paint.NET/PaintDotNet.exe";
    private  String paintNetLink = "C:/Viano/Applications/PaintNet.lnk";

    private   LiveSpeechRecognizer loadFirstModel(LiveSpeechRecognizer recognizer)
    {

        String flag = "";
        System.out.println("Enter 1 or 2:");
        while(true) {
            flag = gui.setText();
            if (flag.equals("1"))
            {   gui.setProgressVisible();
                createFolders();
                loadLabels(mediaLink, mediaPath);
                loadLabels(paintNetLink,paintNetPath);
                wtite(path, "english");
                wtite(pathconfig,startupFlag+"\n"+WeatherURL+"\n"+NewsURL+"\n"+FilmsURL+"\n"+TorrentURL);
                recognizer =  loadLanguageModel(recognizer, ACOUSTIC_MODEL_ENG, DICTIONARY_PATH_ENG, GRAMMAR_PATH_ENG);
                break;
            }
            else if(flag.equals("2"))
            {   gui.setProgressVisible();
                createFolders();
                loadLabels(mediaLink, mediaPath);
                loadLabels(paintNetLink, paintNetPath);
                wtite(path,"russian");
                wtite(pathconfig,startupFlag+"\n"+WeatherURL+"\n"+NewsURL+"\n"+FilmsURL+"\n"+TorrentURL);
                recognizer = loadLanguageModel(recognizer,ACOUSTIC_MODEL_RUS,DICTIONARY_PATH_RUS,GRAMMAR_PATH_RUS);
                break;

            }
            else
            {

            }
        }
        return recognizer;
    }

    private   LiveSpeechRecognizer loadLanguageModel(LiveSpeechRecognizer recognizer,String acoustic,String dict,
                                                     String gram)
    {

        configuration = new Configuration();
        configuration.setAcousticModelPath(acoustic);
        configuration.setDictionaryPath(dict);
        configuration.setGrammarPath(gram);
        configuration.setUseGrammar(true);
        configuration.setGrammarName("master");

        try {
            recognizer = new LiveSpeechRecognizer(configuration);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(gui!=null) {
            gui.dispose();
        }
        filecontain = read(path);
        if (filecontain.equals("english"))
        {

        }
        else if(filecontain.equals("russian"))
        {
            loadWords(filecontain);
            conf  = readMas(pathconfig);
            appsCtrl.setList(Paint_Words);
            appsCtrl.setListR(Racing_Words);
            internetCtrl.setConfig(conf);
        }
        return recognizer;

    }

    private  void createFolders()
    {
        File myPath = new File("C:/Viano/Applications/");
        myPath.mkdirs();
        myPath = new File("C:/Viano/Photo/");
        myPath.mkdirs();
        myPath = new File("C:/Viano/Video/");
        myPath.mkdirs();
    }
    private  void loadLabels(String fileName,String filePath)
    {

        String script = "Set sh = CreateObject(\"WScript.Shell\")"
                + "\nSet shortcut = sh.CreateShortcut(\"" +  fileName + "\")"
                + "\nshortcut.TargetPath = \""+ filePath +"\""
                + "\nshortcut.Save";

        File file = new File("C:/Viano/temp.vbs");
        try {
            FileOutputStream fo = new FileOutputStream(file);
            fo.write(script.getBytes());
            fo.close();

            Runtime.getRuntime().exec("wscript.exe " + file.getAbsolutePath());
            sleep(700);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private  void parserWords(String resource)
    {
        String str = "";
        Boolean flag = false;
        ArrayList<String> ex = new ArrayList<String>();
        String[] list = null;

        InputStream in=Master.class.getResourceAsStream(resource);
        BufferedReader reader = null;
        try {reader = new BufferedReader(new InputStreamReader(in));}
        catch (Exception e) {/*NOP*/}

        try {
            while ((str=reader.readLine())!=null)
            {
                if (str.startsWith("!#"))
                {
                    flag = true;
                    list = str.split(" ");
                }
                else if(str.startsWith("#"))
                {
                    flag = false;
                    loadLists(list[1],ex);
                    ex.clear();
                }
                else if(flag && ex!=null)
                {
                    ex.add(str);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private  void loadWords(String filecontain)
    {
        if (filecontain.equals("english"))
        {
            parserWords("/words/words_eng.txt");
        }

        else if (filecontain.equals("russian"))
        {
            parserWords("/words/words_rus.txt");
        }
    }

    private  void loadLists(String str,ArrayList<String> list)
    {
        if(str.equals("Master"))
        {
            Master_Words.addAll(list);
        }
        else if (str.equals("KeyBoard"))
        {
            KeyBoard_Words.addAll(list);
        }
        else if (str.equals("Mouse"))
        {
            Mouse_Words.addAll(list);
        }
        else if (str.equals("Computer"))
        {
            Computer_Words.addAll(list);
        }
        else if (str.equals("Internet"))
        {
            Internet_Words.addAll(list);
        }
        else if (str.equals("Applications"))
        {
            Applications_Words.addAll(list);
        }
        else if (str.equals("Paint"))
        {
            Paint_Words.addAll(list);
        }
        else if (str.equals("Racing"))
        {
            Racing_Words.addAll(list);
        }
    }

    private static void createFile(String path)
    {
        File f = new File(path);
        f.getParentFile().mkdirs();
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public  String read(String path)
    {
        BufferedReader reader;
        while (true) {
            try {
                reader = new BufferedReader(new FileReader(path));
                String s;
                while ((s=reader.readLine()) != null) {
                    filecontain = s;
                }
                reader.close();
                break;
            }
            catch (Exception e) {createFile(path);}
        }

        System.out.println(filecontain);
        return filecontain;
    }
    public static List<String> readMas(String path)
    {
        BufferedReader reader;

        while (true) {
            try {
                reader = new BufferedReader(new FileReader(path));
                String s;
                int i = 0;
                while ((s=reader.readLine()) != null) {
                    conf.add(i,s);
                    i++;
                }
                reader.close();
                break;
            }
            catch (Exception e) {createFile(path);}
        }

        return conf;
    }
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
    public  LiveSpeechRecognizer beginSettings(LiveSpeechRecognizer recognizer)
    {


        filecontain = read(path);

        if (filecontain.isEmpty())
        {    gui = new Gui();
            recognizer = loadFirstModel(recognizer);
        }
        else if (filecontain.equals("english"))
        {   gui = new Gui();
            gui.disposeElements();
            gui.setProgressVisible();
            recognizer =  loadLanguageModel(recognizer, ACOUSTIC_MODEL_ENG, DICTIONARY_PATH_ENG, GRAMMAR_PATH_ENG);
        }
        else if(filecontain.equals("russian"))
        {   gui = new Gui();
            gui.disposeElements();
            gui.setProgressVisible();
            recognizer = loadLanguageModel(recognizer,ACOUSTIC_MODEL_RUS,DICTIONARY_PATH_RUS,GRAMMAR_PATH_RUS);
        }
        return recognizer;
    }

    public void startVoiceControl(LiveSpeechRecognizer jsgfRecognizer,Configuration config,CtrlGui g,List list) {

        jsgfRecognizer = beginSettings(jsgfRecognizer);
        try {jsgfRecognizer.startRecognition(true);}
        catch (Exception e){error(e.getMessage());}
        audio("/song.wav");
        ctrlGui = new CtrlGui(true);

        while (true) {
            String utterance = jsgfRecognizer.getResult().getHypothesis();
            ctrlGui.setWords(utterance);

            if (utterance.equals(Master_Words.get(0))) //Computer
            {
                computerCtrl.startVoiceControl(jsgfRecognizer,configuration,ctrlGui,Computer_Words);
                setGrammar("master",configuration,jsgfRecognizer);
                ctrlGui.setImage("computer");
                ctrlGui.setImage("");
            }
            else if (utterance.equals(Master_Words.get(1))) //Internet
            {
                internetCtrl.startVoiceControl(jsgfRecognizer,configuration,ctrlGui,Internet_Words);
                setGrammar("master",configuration,jsgfRecognizer);
                ctrlGui.setImage("internet");
                ctrlGui.setImage("");
            }
            else if (utterance.equals(Master_Words.get(2)))//Games
            {
                appsCtrl.startVoiceControl(jsgfRecognizer,configuration,ctrlGui,Applications_Words);
                setGrammar("master",configuration,jsgfRecognizer);
                ctrlGui.setImage("games");
                ctrlGui.setImage("");
            }
            else if (utterance.equals(Master_Words.get(3)))//KeyBoard
            {
                super.keyboardControl(jsgfRecognizer, configuration, ctrlGui, KeyBoard_Words);
                setGrammar("master",configuration,jsgfRecognizer);
                ctrlGui.setImage("keyboard");
                ctrlGui.setImage("");
            }
            else if (utterance.equals(Master_Words.get(4)))//Mouse
            {
                super.mouseControl(jsgfRecognizer, configuration, ctrlGui, Mouse_Words, 15);
                setGrammar("master",configuration,jsgfRecognizer);
                ctrlGui.setImage("mouse");
                ctrlGui.setImage("");
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
                Thread thread = new Thread(new Parameters());
                thread.start();
            }
            else if (utterance.equals(Master_Words.get(7)))
            {
                ctrlGui.setLanguageText(filecontain);

                while (true)
                {
                    utterance = jsgfRecognizer.getResult().getHypothesis();
                    ctrlGui.setWords(utterance);

                    if (utterance.equals("английский") || utterance.equals("english"))
                    {
                        ctrlGui.setTreyMessage("Reset Viano");
                        sleep(2000);
                        ctrlGui.dispose();
                        wtite(path, "english");
                        jsgfRecognizer.stopRecognition();
                        System.exit(0);
                        break;

                    }
                    else if (utterance.equals("русский")|| utterance.equals("russian"))
                    {
                        ctrlGui.setTreyMessage("Reset Viano");
                        sleep(2000);
                        ctrlGui.dispose();
                        wtite(path, "russian");
                        jsgfRecognizer.stopRecognition();
                        System.exit(0);
                        break;

                    }
                    else if (utterance.equals("назад") || utterance.equals("back"))
                    {
                        ctrlGui.setImage("");
                        break;
                    }
                }

            }

        }

        jsgfRecognizer.stopRecognition();
        System.exit(0);
    }

    public static List getKeyBoardWords()
    {
        return   KeyBoard_Words;
    }

    private static void sleep(int time)
    {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void audio(String str) //!поток не закрыт
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
    public static List getMouseWords()
    {
        return   Mouse_Words;
    }

    private void error(String str)
    {
        ctrlGui = new CtrlGui(false);
        ctrlGui.dispose();
        ctrlGui.setErrorTreyMessage(str);
        sleep(8100);
        System.exit(0);
    }
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
}
