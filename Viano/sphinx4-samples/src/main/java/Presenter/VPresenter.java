package Presenter;

import Models.*;
import View.CtrlGui;
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dimamj on 25.04.2015.
 */
public class VPresenter {

    VoiceControl voiceControl;
    CtrlGui gui;
    LiveSpeechRecognizer recognizer;
    Configuration configuration;
    List list;
    static Config config;
    AbstractController master;
    List<String> MWords;

    public VPresenter(VoiceControl voiceControl, CtrlGui gui) {
        this.voiceControl = voiceControl;
        this.gui = gui;



    }

    public VPresenter()
    {
        init();
    }

    private void init()
    {
        config = new Config();
        recognizer = config.beginSettings(recognizer);

        master = Master.getInstance();
        master.setListener(new RecognitionListener() {

            @Override
            public void wordRecognized(String word) {
                gui.setWords(word);
            }

            @Override
            public void setImage(String key) {
                gui.setImage(key);
            }

            @Override
            public String goModel(String nextModel) {

             return  getModel(nextModel).startVoiceControl(recognizer,configuration,true);

            }

        });

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


    public void setWords(String text)
    {
        gui.setWords(text);
    }


    public void setLanguageText(String key)
    {
        gui.setLanguageText(key);
    }

    public void setTreyMessage(String s)
    {
        gui.setTreyMessage(s);
    }

    public void disposeGui()
    {
        gui.dispose();
    }

    public  void setErrorTreyMessage(String s)
    {
//        gui.
    }
}
