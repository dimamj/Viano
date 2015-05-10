package Presenter;

import Models.*;
import Models.FAddComand.AbstractAddCommand;
import Models.FAddComand.FactoryMethod;
import Models.Test;
import View.*;
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.result.BoundedPriorityQueue;

import java.io.IOException;
import java.util.List;

/**
 * Created by dimamj on 25.04.2015.
 */
public class VPresenter {

    ViewInterface gui;
    ViewInterface startGui;
    ViewInterface settingsGui;
    ViewInterface test;
    ViewInterface addCommandGui;
    LiveSpeechRecognizer recognizer;
    Configuration configuration;
    List list;
    static Config config;
    AbstractController master;
    List<String> MWords;
    FactoryMethod factory;

    public VPresenter()
    {
        init();
    }

    private void init()
    {

        RecognitionListener listener = new RecognitionListener() {

            @Override
            public void addCommand(String[] array) {
                new FactoryMethod().getInstance(Boolean.parseBoolean(array[3])).start(array,config.getFilecontain());
            }

            @Override
            public void setLabel(String text,String view) {
                getView(view,"").setLabel(text);
            }

            @Override
            public void wordRecognized(String word) {
                gui.setWords(word);
            }

            @Override
            public void disposeGui(String view) {
                getView(view,"").dispose();
            }

            @Override
            public void createGui(String view,String lang) {
               getView(view,lang);
            }

            @Override
            public Boolean getEdit(String view) {
                return getView(view,"").getEdit();
            }

            @Override
            public void setImage(String key) {
                gui.setImage(key);
            }

            @Override
            public String goModel(String nextModel) {

                return  getModel(nextModel).startVoiceControl(recognizer,configuration,true);

            }

            @Override
            public void setSpeedCursor(int speed,Boolean flag) {
                    MouseController.getInstance().setSpeed(speed,flag);

            }

            @Override
            public String[] getText(String view)
            {
                return getView(view,"").getText();
            }

            @Override
            public void setText(String view, List<String> list) {
                getView(view,"").setText(list);
            }

            @Override
            public void setProgressVisible(String view)
            {
                getView(view,"").setProgressVisible();
            }

            @Override
            public void disposeElements(String view)
            {
                getView(view,"").disposeElements();
            }

            @Override
            public void errorMessage(String message) {
                gui.setErrorTreyMessage(message);
            }
        };
        config = new Config();
        config.setListener(listener);
        startGui = new StartGui();
        System.out.println(System.getProperty("file.encoding"));
        recognizer = config.beginSettings(recognizer);

        master = Master.getInstance();
        master.setListener(listener);


        configuration = config.getConfiguration();

        MWords = config.Modules_Words;
        try {recognizer.startRecognition(true);}
        catch (Exception e){error(e.getMessage());}

        if(!config.read("C:/Viano/data/language.txt",2).equals("true"))
        {
            test = new View.Test();
            Test.getInstance().startVoiceControl(recognizer, configuration, true);
        }

        master.audio("C:/Viano/data/song.wav");
        gui = new CtrlGui(true);
        String next =  master.startVoiceControl(recognizer, configuration, true);

        while (true) {

            next = master.getListener().goModel(next);

        }

    }

    public static Config getConfig() {
        return config;
    }


    public List<String> getConfigList()
    {
       return config.getConfList();
    }

    private void error(String str)
    {
        gui.dispose();
        gui.setErrorTreyMessage(str);
        try {
            Thread.sleep(8100);
        } catch (InterruptedException e) {
            e.printStackTrace();

        }
        System.exit(0);
    }

    private AbstractController getModel(String str)
    {
        return  str.equals(MWords.get(0)) ? master :
                str.equals(MWords.get(1)) ? ComputerCtrl.getInstance() :
                str.equals(MWords.get(2)) ? InternetCtrl.getInstance() :
                str.equals(MWords.get(3)) ? AppsCtrl.getInstance() :
                str.equals(MWords.get(4)) ? KeyboardController.getInstance() :
                str.equals(MWords.get(5)) ? MouseController.getInstance() :
                str.equals(MWords.get(6)) ? PaintCtrl.getInstance() : master;
    }

    public ViewInterface getView(String str,String lang)
    {

        if( str.equals("setting")&&settingsGui==null){
            settingsGui = new Settings(lang);
            return settingsGui;
        } else if(str.equals("setting")&&!settingsGui.isDisplayable()){
            settingsGui = new Settings(lang);
            return settingsGui;
        } else if(str.equals("setting")&&settingsGui.isDisplayable()){
            return settingsGui;
        }

        if( str.equals("addCommand")&&addCommandGui==null){
            addCommandGui = new AddCommand(lang);
            return addCommandGui;
        } else if(str.equals("addCommand")&&!addCommandGui.isDisplayable()){
            addCommandGui = new AddCommand(lang);
            return addCommandGui;
        } else if(str.equals("addCommand")&&addCommandGui.isDisplayable()){
            return addCommandGui;
        }

        return  str.equals("start") ? startGui:
                str.equals("main") ? gui:
                str.equals("test") ? test :null;
    }


}
