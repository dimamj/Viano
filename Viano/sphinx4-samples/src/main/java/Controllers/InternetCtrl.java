package Controllers;

import View.CtrlGui;
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dimamj on 14.01.2015.
 */
public class InternetCtrl extends AbstractController implements VoiceControl {

    private static final InternetCtrl instance = new InternetCtrl();
    private List<String> conf = new ArrayList<String>();
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


    public void startVoiceControl(LiveSpeechRecognizer jsgfRecognizer,Configuration config, CtrlGui ctrlGui,List list) {
        ctrlGui.setImage("internet active");
        setGrammar("internet",config,jsgfRecognizer);
        while (true) {

            String utterance = jsgfRecognizer.getResult().getHypothesis();
            ctrlGui.setWords(utterance);
            if (utterance.equals(list.get(0)))
            {
                break;
            }
            else if (utterance.equals(list.get(1)))
            {
                super.keyboardControl(jsgfRecognizer, config, ctrlGui, Master.getKeyBoardWords());
                setGrammar("internet",config,jsgfRecognizer);
                ctrlGui.setImage("keyboard");
                ctrlGui.setImage("internet active");
            }
            else if (utterance.equals(list.get(2)))
            {
                super.mouseControl(jsgfRecognizer, config, ctrlGui, Master.getMouseWords(), 20);
                setGrammar("internet",config,jsgfRecognizer);
                ctrlGui.setImage("mouse");
                ctrlGui.setImage("internet active");
            }
            else if (utterance.equals(list.get(3)))
            {
                openURL("google.com");
            }
            else if (utterance.equals(list.get(4)))
            {
                openURL(conf.get(1));
            }
            else if (utterance.equals(list.get(5)))
            {
                openURL(conf.get(2));
            }
            else if (utterance.equals(list.get(6))) //VK
            {
                openURL("vk.com");
            }
            else if (utterance.equals(list.get(7))) //FaceBook
            {
                openURL("facebook.com");
            }
            else if (utterance.equals(list.get(8))) //Odn.
            {
                openURL("odnoklassniki.ru");
            }
            else if (utterance.equals(list.get(9)))
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

            else if (utterance.equals(list.get(10))) //keyboard
            {
                try {
                    Runtime.getRuntime().exec("cmd /c start " + "osk.exe");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                screenKeyBoard(jsgfRecognizer,config,ctrlGui,list);
            }

            else if (utterance.equals(list.get(11))) //Okey Google
            {
                robot.keyPress(KeyEvent.VK_CONTROL);
                robot.keyPress(KeyEvent.VK_SHIFT);
                robot.keyPress(KeyEvent.VK_PERIOD);

                robot.keyRelease(KeyEvent.VK_CONTROL);
                robot.keyRelease(KeyEvent.VK_SHIFT);
                robot.keyRelease(KeyEvent.VK_PERIOD);
            }
            else if (utterance.equals(list.get(12))) //обратно
            {
               super.twoButtonPress(KeyEvent.VK_ALT, KeyEvent.VK_LEFT);
            }
            else if (utterance.equals(list.get(13))) //вперед
            {
                super.twoButtonPress(KeyEvent.VK_ALT, KeyEvent.VK_RIGHT);
            }
            else if (utterance.equals(list.get(14))) //обновить
            {
                super.oneButtonPress(KeyEvent.VK_F5);
            }
            else if (utterance.equals(list.get(15))) //закрыть вкладку
            {
                super.twoButtonPress(KeyEvent.VK_CONTROL, KeyEvent.VK_W);
            }
            else if (utterance.equals(list.get(16))) //online films
            {
                openURL(conf.get(3));
            }
            else if (utterance.equals(list.get(17))) //torrent
            {
                openURL(conf.get(4));
            }

        }

    }
    public void setConfig(List<String> list)
    {
        conf = list;
    }

    private void screenKeyBoard(LiveSpeechRecognizer jsgfRecognizer,Configuration config,CtrlGui ctrlGui,List list)
    {
        while (true) {
            String utterance = jsgfRecognizer.getResult().getHypothesis();
            ctrlGui.setWords(utterance);

             if (utterance.equals(list.get(2)))
            {
                super.mouseControl(jsgfRecognizer, config, ctrlGui, Master.getMouseWords(), 35);
                setGrammar("internet",config,jsgfRecognizer);
                ctrlGui.setImage("mouse");
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
