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
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class ComputerCtrl extends AbstractController implements VoiceControl {

    private static final ComputerCtrl instance = new ComputerCtrl();
    private String sl = File.separator;
    {
        /*
           Computer_Words =
                  0  "назад"
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

    public void startVoiceControl(LiveSpeechRecognizer jsgfRecognizer,Configuration config, CtrlGui ctrlGui,List list) {
        ctrlGui.setImage("computer active");
        setGrammar("computer",config,jsgfRecognizer);

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
                setGrammar("computer",config,jsgfRecognizer);
                ctrlGui.setImage("keyboard");
                ctrlGui.setImage("computer active");

            }
            else if (utterance.equals(list.get(2)))
            {
                super.mouseControl(jsgfRecognizer, config, ctrlGui, Master.getMouseWords(), 15);
                setGrammar("computer",config,jsgfRecognizer);
                ctrlGui.setImage("mouse");
                ctrlGui.setImage("computer active");
            }
            else if (utterance.equals(list.get(3)))
            {
                   twoButtonPress(KeyEvent.VK_WINDOWS, KeyEvent.VK_E);
            }
            else if (utterance.equals(list.get(4)))
            {
                twoButtonPress(KeyEvent.VK_WINDOWS, KeyEvent.VK_Q);
            }
            else if (utterance.equals(list.get(5)))
            {
                twoButtonPress(KeyEvent.VK_WINDOWS, KeyEvent.VK_I);
            }
            else if (utterance.equals(list.get(6)))
            {
                try {
                    Runtime.getRuntime().exec("cmd /c start " + "C:" + "Viano/Applications/wmplayer.lnk");;
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            else if (utterance.equals(list.get(7)))
            {
                super.runApplications("cmd /c start " + "C:" + "Viano/Applications/wmplayer.lnk");
                commandOfMedia(jsgfRecognizer,ctrlGui,list);

                ctrlGui.setImage("computer active");
            }

            else if (utterance.equals(list.get(10)))
            {
                super.runApplications("cmd /c start " + "Explorer" + " C:" + sl + "Viano" + sl + "Photo" + sl);
            }

            else if (utterance.equals(list.get(11)))
            {
                super.runApplications("cmd /c start " + "Explorer" + " C:" + sl + "Viano" + sl + "Video" + sl);
            }


        }


    }




    private void commandOfMedia(LiveSpeechRecognizer recognizer,CtrlGui gui,List list )
    {
        while (true)
        {
            String utterance = recognizer.getResult().getHypothesis();
            gui.setWords(utterance);

            if (utterance.equals(list.get(8)))//тише
            {
                robot.keyPress(KeyEvent.VK_F9);
                robot.keyRelease(KeyEvent.VK_F9);
            }
            else if(utterance.equals(list.get(9)))//громче
            {
                robot.keyPress(KeyEvent.VK_F8);
                robot.keyRelease(KeyEvent.VK_F8);
            }
            else if (utterance.equals(list.get(0)))//назад
            {
                break;
            }
        }
    }


}
