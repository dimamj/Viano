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

import Presenter.VPresenter;
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ComputerCtrl extends AbstractController {

    private static final ComputerCtrl instance = new ComputerCtrl();
    private String sl = File.separator;
    public    List<String> Computer_Words = config.Computer_Words;
    {
        /*
           Computer_Words =
                  0  "мастер"
                  1  ,"клавиатура"
                  2  ,"мышь"
                  3  ,"компьютер"
                  4  ,"приложения"
                  5  ,"параметры"
                  6  ,"музыка"
                  7  ,"управление музыкой"
                  8  ,"громче"
                  9  ,"тише"
                  10 ,"фоторедактор"
                  11 ,"фотографии"
                  12 ,"видео"

                  Paint_Words
                  0   "назад"
                  1  ,"клавиатура"
                  2  ,"мышь"
                  3  ,"открыть"
                  4  ,"сохранить"
                  5  ,"обрезать"
                  6  ,"размер"
                  7  ,"фоторедактор"
                  8  ,"меню"
         */
    }

    private ComputerCtrl()
    {}

    public static ComputerCtrl getInstance()
    {
        return instance;
    }

    public String startVoiceControl(LiveSpeechRecognizer jsgfRecognizer,Configuration config,Boolean flag) {
            run = flag;

            listener.setImage("computer active");
            setGrammar("computer", config, jsgfRecognizer);

        List<String> list = Computer_Words;

        while (run) {

            String utterance = jsgfRecognizer.getResult().getHypothesis();
            listener.wordRecognized(utterance);

            if(find(utterance))
            {
                exitController();
                return utterance;
            }
            else if (utterance.equals(list.get(0)))
            {
                listener.setSpeedCursor(15);
                exitController();
                return utterance;
            }

            else if (utterance.equals(list.get(1)))
            {
                   twoButtonPress(KeyEvent.VK_WINDOWS, KeyEvent.VK_E);
            }
            else if (utterance.equals(list.get(2)))
            {
                twoButtonPress(KeyEvent.VK_WINDOWS, KeyEvent.VK_Q);
            }
            else if (utterance.equals(list.get(3)))
            {
                twoButtonPress(KeyEvent.VK_WINDOWS, KeyEvent.VK_I);
            }
            else if (utterance.equals(list.get(4)))
            {
                try {
                    Runtime.getRuntime().exec("cmd /c start " + "C:" + "Viano/Applications/wmplayer.lnk");;
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            else if (utterance.equals(list.get(5)))
            {
                super.runApplications("cmd /c start " + "C:" + "Viano/Applications/wmplayer.lnk");
                commandOfMedia(jsgfRecognizer,list);

                listener.setImage("computer active");
            }

            else if (utterance.equals(list.get(9)))
            {
                super.runApplications("cmd /c start " + "Explorer" + " C:" + sl + "Viano" + sl + "Photo" + sl);
            }

            else if (utterance.equals(list.get(10)))
            {
                super.runApplications("cmd /c start " + "Explorer" + " C:" + sl + "Viano" + sl + "Video" + sl);
            }


        }

        listener.setImage("computer");
        return "";
    }

    @Override
    public void exitController() {
        listener.setImage("computer");
    }


    private void commandOfMedia(LiveSpeechRecognizer recognizer,List list )
    {
        while (true)
        {
            String utterance = recognizer.getResult().getHypothesis();
            listener.wordRecognized(utterance);

            if (utterance.equals(list.get(6)))//тише
            {
                robot.keyPress(KeyEvent.VK_F9);
                robot.keyRelease(KeyEvent.VK_F9);
            }
            else if(utterance.equals(list.get(7)))//громче
            {
                robot.keyPress(KeyEvent.VK_F8);
                robot.keyRelease(KeyEvent.VK_F8);
            }
            else if (utterance.equals(list.get(8)))//назад
            {
                break;
            }
        }
    }


}
