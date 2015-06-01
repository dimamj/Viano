package Presenter;

import Models.*;
import Models.FAddComand.FactoryMethod;
import Models.Test;
import View.*;
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Properties;

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
    RecognitionListener listener;

    public VPresenter() {
        init();
    }

    private void init() {

        listener = new RecognitionListener() {

            @Override
            public void addCommand(String[] array) {
                new FactoryMethod().getInstance(Boolean.parseBoolean(array[3])).start(array, config.getFilecontain());
            }

            @Override
            public Config getConfig() {
                return config;
            }

            @Override
            public Properties getProperties() {
                return config.getProperties();
            }

            @Override
            public void setLabel(String text,String view) {
                getView(view).setLabel(text);
            }

            @Override
            public void write(String path, String language) {
                writeToFile(path,language);
            }

            @Override
            public void wordRecognized(String word) {
                gui.setWords(word);
            }

            @Override
            public void disposeGui(String view) {
                getView(view).dispose();
            }

            @Override
            public void createGui(String view,String lang) {
               getView(view);
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
            public void setSpeedCursor(int speed,Boolean flag) {
                    MouseController.getInstance().setSpeed(speed,flag);

            }

            @Override
            public String[] getText(String view)
            {
                return getView(view).getText();
            }

            @Override
            public void setText(String view, List<String> list) {
                getView(view).setText(list);
            }

            @Override
            public void setProgressVisible(String view)
            {
                getView(view).setProgressVisible();
            }

            @Override
            public void disposeElements(String view)
            {
                getView(view).disposeElements();
            }

            @Override
            public void errorMessage(String message) {
                gui.setErrorTreyMessage(message);
            }
        };
        config = new Config();
        config.setListener(listener);
        startGui = new StartGui(listener);
        recognizer = config.beginSettings(recognizer);

        master = Master.getInstance();
        master.setConfig(config);
        master.setListener(listener);

        configuration = config.getConfiguration();

        MWords = config.Modules_Words;
        try {recognizer.startRecognition(true);}
        catch (Exception e){
            gui = new CtrlGui(true,listener);
            listener.errorMessage("Error: Code #3");
            error(e.getMessage());}

        if(!config.getFop().read("C:/Viano/data/language.txt",2).equals("true")) {
            test = new View.Test(listener);
            Test.getInstance().startVoiceControl(recognizer, configuration, true);
        }

        master.audio("C:/Viano/data/song.wav");
        gui = new CtrlGui(true,listener);
        String next =  master.startVoiceControl(recognizer, configuration, true);

        while (true) {

            next = master.getListener().goModel(next);

        }

    }

    private static void writeToFile(String path,String language) {

        try {
            PrintWriter out = new PrintWriter(path);
            out.print(language);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
    private void error(String str) {
        gui.dispose();
        listener.errorMessage(str);
        try {
            Thread.sleep(8100);
        } catch (InterruptedException e) {
            e.printStackTrace();

        }
        System.exit(0);
    }

    /**
     * Данный метод возвращает ссылку на указанный класс.
     *
     * @param str название слушателя
     * @return ссылка на слушатель
     */
    private AbstractController getModel(String str) {
        return  str.equals(MWords.get(0)) ? master :
                str.equals(MWords.get(1)) ? ComputerCtrl.getInstance() :
                str.equals(MWords.get(2)) ? InternetCtrl.getInstance() :
                str.equals(MWords.get(3)) ? AppsCtrl.getInstance() :
                str.equals(MWords.get(4)) ? KeyboardController.getInstance() :
                str.equals(MWords.get(5)) ? MouseController.getInstance() :
                str.equals(MWords.get(6)) ? PaintCtrl.getInstance() : master;
    }

    /**
     * Возвращает ссылку или создает обьект и возвращает ссылку
     * на view
     *
     * @param str имя представления
     * @return ссылку на представление
     */
    public ViewInterface getView(String str) {

        if( str.equals("setting")&&settingsGui==null){
            settingsGui = new Settings(listener);
            return settingsGui;
        } else if(str.equals("setting")&&!settingsGui.isDisplayable()){
            settingsGui = new Settings(listener);
            return settingsGui;
        } else if(str.equals("setting")&&settingsGui.isDisplayable()){
            return settingsGui;
        }

        if( str.equals("addCommand")&&addCommandGui==null){
            addCommandGui = new AddCommand(listener);
            return addCommandGui;
        } else if(str.equals("addCommand")&&!addCommandGui.isDisplayable()){
            addCommandGui = new AddCommand(listener);
            return addCommandGui;
        } else if(str.equals("addCommand")&&addCommandGui.isDisplayable()){
            return addCommandGui;
        }

        return  str.equals("start") ? startGui:
                str.equals("main") ? gui:
                str.equals("test") ? test :null;
    }


}
