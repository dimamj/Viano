package Presenter;

import Models.*;
import View.CtrlGui;
import View.Gui;
import View.Settings;
import View.ViewInterface;
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dimamj on 25.04.2015.
 */
public class VPresenter {

    ViewInterface gui;
    ViewInterface startGui;
    ViewInterface settingsGui;
    LiveSpeechRecognizer recognizer;
    Configuration configuration;
    List list;
    static Config config;
    AbstractController master;
    List<String> MWords;

    public VPresenter()
    {
        init();
    }

    private void init()
    {
        RecognitionListener listener = new RecognitionListener() {

            @Override
            public void wordRecognized(String word) {
                gui.setWords(word);
            }

            @Override
            public void disposeGui(String view) {
                getView(view).dispose();
            }

            @Override
            public void createSettingsGui() {
               settingsGui =  new Settings();
            }

            @Override
            public Boolean getEdit(String view) {
                return getView(view).getEdit();
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
            public void setSpeedCursor(int speed) {
                if(speed>0)
                    MouseController.getInstance().setSpeed(speed);

            }

            @Override
            public String getText(String view)
            {
                return getView(view).getText();
            }

            @Override
            public void setText(String view, List<String> list) {
                getView(view).setText(list);
            }

            @Override
            public void setProgressVisible()
            {
                startGui.setProgressVisible();
            }

            @Override
            public void disposeElements(String view)
            {
                getView(view).disposeElements();
            }
        };
        config = new Config();
        config.setListener(listener);
        startGui = new Gui();
        recognizer = config.beginSettings(recognizer);

        master = Master.getInstance();
        master.setListener(listener);


        configuration = config.getConfiguration();
        MWords = config.Modules_Words;
        try {recognizer.startRecognition(true);}
        catch (Exception e){error(e.getMessage());}
        master.audio("/song.wav");
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

    public ViewInterface getView(String str)
    {
        return  str.equals("start") ? startGui:
                str.equals("main") ? gui:
                str.equals("setting") ? settingsGui:null;
    }


}
