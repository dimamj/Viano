package Models;

import View.CtrlGui;
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dimamj on 14.01.2015.
 */
public class InternetCtrl extends AbstractController {

    private static final InternetCtrl instance = new InternetCtrl();
    public    List<String> Internet_Words = config.Internet_Words;
    private static final String LANGUAGE_MODEL =
            "resource:/gram_rus/lmbase.lm";

    private InternetCtrl()
    {}

    public static InternetCtrl getInstance()
    {
        return instance;
    }

    {
        /*
           Internet_Words =
                  0  "назад"
                  1  ,"клавиатура"
                  2  ,"мышь"
                  3  ,"поиск"
                  4  ,"погода"
                  5  ,"новости"
                  6  ,"вконтакте"
                  7  ,"фейсбук"
                  8  ,"одноклассники"
                  9  ,"голосовой ввод"
                  10 ,"экранная клавиатура"
                  11 ,"окей гугл"
                  12 ,"обратно"
                  13 ,"вперед"
                  14 ,"обновить"
                  15 ,"закрыть"
                  16 ,"онлайн фильмы"
                  17 ,"торрент"
         */
    }

    private void setLanguageModel(Configuration config,LiveSpeechRecognizer jsgfRecognizer)
    {

        config.setUseGrammar(false);
        config.setLanguageModelPath(LANGUAGE_MODEL);
        jsgfRecognizer.stopRecognition();
        jsgfRecognizer.setLang(config);
        jsgfRecognizer.startRecognition(true);
    }


    @Override
    public void exitController() {
        listener.setImage("internet");
    }

    public String startVoiceControl(LiveSpeechRecognizer jsgfRecognizer,Configuration configuration,Boolean flag) {
        if(config.getFilecontain().equals("russian")) {
            config.parserWords("C:/Viano/data/words/web_rus.txt");
        }
        listener.setImage("internet active");
        setGrammar("internet",configuration,jsgfRecognizer);
        List<String> list = Internet_Words;
        listener.setSpeedCursor(20,false);
        while (true) {

            String utterance = jsgfRecognizer.getResult().getHypothesis();
            listener.wordRecognized(utterance);

            if(find(utterance))
            {
                exitController();
                return utterance;
            }

            findAddApps(utterance);
            if (utterance.equals(list.get(1)))
            {
                openURL("google.com");
            }
            else if (utterance.equals(list.get(2)))
            {
                openURL(conf.get(1));
            }
            else if (utterance.equals(list.get(3)))
            {
                openURL(conf.get(2));
            }
            else if (utterance.equals(list.get(4))) //VK
            {
                openURL("vk.com");
            }
            else if (utterance.equals(list.get(5))) //FaceBook
            {
                openURL("facebook.com");
            }
            else if (utterance.equals(list.get(6))) //Odn.
            {
                openURL("odnoklassniki.ru");
            }
            else if (utterance.equals(list.get(7)))
            {
               /* setGrammar("full",config,jsgfRecognizer);
                while(true)
                {
                    utterance = jsgfRecognizer.getResult().getHypothesis();
                    ctrlGui.setWords(utterance);
                }
              /*  setLanguageModel(config,jsgfRecognizer);
                while (true)
                {
                    utterance = jsgfRecognizer.getResult().getHypothesis();
                    ctrlGui.setWords(utterance);
                }
              */
            }

            else if (utterance.equals(list.get(8))) //keyboard
            {
                try {
                    Runtime.getRuntime().exec("cmd /c start " + "osk.exe");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                screenKeyBoard(jsgfRecognizer, configuration, list);
            }

            else if (utterance.equals(list.get(9))) //Okey Google
            {
                robot.keyPress(KeyEvent.VK_CONTROL);
                robot.keyPress(KeyEvent.VK_SHIFT);
                robot.keyPress(KeyEvent.VK_PERIOD);

                robot.keyRelease(KeyEvent.VK_CONTROL);
                robot.keyRelease(KeyEvent.VK_SHIFT);
                robot.keyRelease(KeyEvent.VK_PERIOD);
            } else if (utterance.equals(list.get(10))) //обратно
            {
               super.twoButtonPress(KeyEvent.VK_ALT, KeyEvent.VK_LEFT);
            } else if (utterance.equals(list.get(11))) //вперед
            {
                super.twoButtonPress(KeyEvent.VK_ALT, KeyEvent.VK_RIGHT);
            } else if (utterance.equals(list.get(12))) //обновить
            {
                super.oneButtonPress(KeyEvent.VK_F5);
            } else if (utterance.equals(list.get(13))) //закрыть вкладку
            {
                super.twoButtonPress(KeyEvent.VK_CONTROL, KeyEvent.VK_W);
            } else if (utterance.equals(list.get(14))) //online films
            {
                openURL(conf.get(3));
            }
            else if (utterance.equals(list.get(15))) //torrent
            {
                openURL(conf.get(4));
            }

        }



    }

    private void findAddApps(String utterance){
        Path filePath = Paths.get("C:/Viano/data/words/forLinks.txt");
        Charset charset = Charset.forName("UTF-8");

        try {
            try (BufferedReader reader = Files.newBufferedReader(filePath, charset)){
                String str = "";/**/
                while ((str=reader.readLine())!=null){
                    if(!str.isEmpty()) {
                        String[] array = str.split("\\|");
                        if (utterance.equals(array[1])) {

                             Desktop.getDesktop().browse(new URI(array[0]));
                             break;

                        }
                    }
                }
            }
        } catch (Exception e) {
            listener.errorMessage("Error: Code #1");
            e.printStackTrace();
        }

    }


    private void screenKeyBoard(LiveSpeechRecognizer jsgfRecognizer,Configuration config,List list)
    {
        while (true) {
            String utterance = jsgfRecognizer.getResult().getHypothesis();
            listener.wordRecognized(utterance);

             if (utterance.equals(list.get(2)))
            {
               // super.mouseControl(jsgfRecognizer, config, 35);
               // setGrammar("internet",config,jsgfRecognizer);
               // listener.setImage("internet active");
            }
            else    if (utterance.equals(list.get(0)))
             {
                 break;
             }

        }
    }

    private void openURL(String url)
    {
        try {
            Desktop.getDesktop().browse(new URI("http://www." + url));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
