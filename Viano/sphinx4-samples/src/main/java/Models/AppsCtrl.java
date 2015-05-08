package Models;

import Presenter.RecognitionListener;
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;

import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

/**
 * Created by dimamj on 14.01.2015.
 */
public class AppsCtrl extends AbstractController  {

    private static final AppsCtrl instance = new AppsCtrl();

    private List<String> racing = config.Racing_Words;
    public  List<String> Applications_Words = config.Applications_Words;
    private AppsCtrl() {
    }

    public static AppsCtrl getInstance() {
        return instance;
    }



    {  /*
                  фоторедактор
                  гонки

     */
    }



    public String startVoiceControl(LiveSpeechRecognizer jsgfRecognizer, Configuration configuration,Boolean flag) {
        config.parserWords("C:/Viano/data/words/apps_rus.txt");
        listener.setImage("games active");
        setGrammar("apps", configuration, jsgfRecognizer);
        List<String> list = Applications_Words;
        run = flag;
        while (run) {

            String utterance = jsgfRecognizer.getResult().getHypothesis();
            listener.wordRecognized(utterance);


            if(find(utterance))
            {
                exitController();
                return utterance;
            }

            findAddApps(utterance);

            if (utterance.equals(list.get(1))) {
                super.runApplications("cmd /c start " + "C:" + "Viano/Applications/PaintNet.lnk");
                return utterance;
            }

            else if (utterance.equals(list.get(2))) {

                racingCtrl(jsgfRecognizer, configuration, racing);
                setGrammar("apps", configuration, jsgfRecognizer);
                listener.setImage("games active");
            }






        }

        return "";
    }


        private void findAddApps(String utterance){
            Path filePath = Paths.get("C:/Viano/data/words/forApps.txt");
            Charset charset = Charset.forName("UTF-8");

            try {
                try (BufferedReader reader = Files.newBufferedReader(filePath, charset)){
                    String str = "";
                    while ((str=reader.readLine())!=null){
                        if(!str.isEmpty()) {
                            String[] array = str.split("-");
                            if (utterance.equals(array[1])) {
                                runApplications("cmd /c start " + "C:/" + "Viano/Applications/" +"\""+
                                        array[0].toLowerCase()+"\""+ ".lnk");
                                break;
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    /*
                  0 "назад"
                  1  ,"газ"
                  2  ,"тормоз"
                  3  ,"пауза"
                  4  ,"влево"
                  5  ,"вправо"
                  6  ,"свернуть"
                  7  ,"закрыть"
                  8  ,"войти"
                  9  ,"выход"
                  10 ,"отменить"
                  11 ,"вверх"
                  12 ,"вниз"
     */
    private void racingCtrl(LiveSpeechRecognizer jsgfRecognizer, Configuration config, List list) {
        setGrammar("racing", config, jsgfRecognizer);
        listener.setImage("racing");
        super.runApplications("cmd /c start " + "C:" + "Viano/Applications/BurnoutLauncher.lnk");
        while (true) {

            String utterance = jsgfRecognizer.getResult().getHypothesis();
            listener.wordRecognized(utterance);

            if (utterance.equals(list.get(0)))
            {
                break;
            }
            else if (utterance.equals(list.get(4)))
            {
                super.oneButtonPress(KeyEvent.VK_LEFT, 1000);
            }
            else if (utterance.equals(list.get(5)))
            {
                super.oneButtonPress(KeyEvent.VK_RIGHT, 1000);
            }
            else if (utterance.equals(list.get(6)))
            {
                super.twoButtonPress(KeyEvent.VK_WINDOWS, KeyEvent.VK_D);
            }
            else if (utterance.equals(list.get(7)))
            {
                super.twoButtonPress(KeyEvent.VK_ALT, KeyEvent.VK_F4);
            }
            else if (utterance.equals(list.get(8)))
            {
                super.oneButtonPress(KeyEvent.VK_ENTER, 1000);
            }
            else if (utterance.equals(list.get(9)))
            {
                super.oneButtonPress(KeyEvent.VK_ESCAPE, 1000);
            }
            else if (utterance.equals(list.get(10)))
            {
                super.oneButtonPress(KeyEvent.VK_BACK_SPACE, 1000);
            }
            else if (utterance.equals(list.get(11)))
            {
                super.oneButtonPress(KeyEvent.VK_UP, 1000);
            }
            else if (utterance.equals(list.get(12)))
            {
                super.oneButtonPress(KeyEvent.VK_DOWN, 1000);
            }
            else if (search(utterance, list, 1, 3))
            {
                Thread thread = new Thread(new Stop(jsgfRecognizer, config,listener, list));
                thread.start();
                Stop.utterance = utterance;
                while (true) {

                    if (Stop.utterance.equals(list.get(1)))
                    {
                        Stop.utterance = "";
                        robot.keyPress(KeyEvent.VK_W);
                        robot.keyRelease(KeyEvent.VK_S);
                    }
                    else if (Stop.utterance.equals(list.get(2)))
                    {
                        Stop.utterance = "";
                        robot.keyPress(KeyEvent.VK_S);
                        robot.keyRelease(KeyEvent.VK_W);

                    }
                    else if (Stop.utterance.equals(list.get(3)))
                    {
                        Stop.utterance = "";
                        robot.keyRelease(KeyEvent.VK_W);
                        robot.keyRelease(KeyEvent.VK_S);
                    }
                    else if (Stop.utterance.equals(list.get(4)))
                    {
                        Stop.utterance = "";
                        oneButton(KeyEvent.VK_A, 500);
                    }
                    else if (Stop.utterance.equals(list.get(5)))
                    {
                        Stop.utterance = "";
                        oneButton(KeyEvent.VK_D, 500);
                    }
                    else if (Stop.utterance.equals(list.get(0)))
                    {
                        break;
                    }
                }


            }
        }
    }

    private static class Stop implements Runnable

    {
            LiveSpeechRecognizer recognizer;
    Configuration configuration;
        RecognitionListener listener;
    List list;
    static String utterance = "" ;
    static String str = "";

    public   Stop(LiveSpeechRecognizer recognizer,Configuration configuration,RecognitionListener listener,List list)
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
            listener.wordRecognized(utterance);
            if(utterance.equals("<unk>"))
            {
                utterance=str;
            }

            if (utterance.equals(list.get(0)))
            {
                break;
            }

        }

    }

}
    private void oneButton(int key,int delay)
    {
        robot.keyPress(key);
        robot.delay(delay);
        robot.keyRelease(key);
    }

    @Override
    public void exitController() {
        listener.setImage("games");
    }

}
