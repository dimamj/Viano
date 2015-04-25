package Models;

import View.CtrlGui;
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;

/**
 * Created by dimamj on 14.01.2015.
 */
public class AppsCtrl extends AbstractController implements VoiceControl {

    private static final AppsCtrl instance = new AppsCtrl();
    private List<String> paint;
    private List<String> racing;

    private AppsCtrl() {
    }

    public static AppsCtrl getInstance() {
        return instance;
    }

    {  /*
                    1 "назад"
                    2 ,"клавиатура"
                    3 ,"мышь"
                    4 ,"фоторедактор"

     */
    }

    public void startVoiceControl(LiveSpeechRecognizer jsgfRecognizer, Configuration config, CtrlGui ctrlGui, List list) {
        ctrlGui.setImage("games active");
        setGrammar("apps", config, jsgfRecognizer);
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
                setGrammar("apps", config, jsgfRecognizer);
                ctrlGui.setImage("keyboard");
                ctrlGui.setImage("games active");
            }

            else if (utterance.equals(list.get(2)))
            {
                super.mouseControl(jsgfRecognizer, config, ctrlGui, Master.getMouseWords(), 15);
                setGrammar("apps", config, jsgfRecognizer);
                ctrlGui.setImage("mouse");
                ctrlGui.setImage("games active");
            }

            else if (utterance.equals(list.get(3)))
            {
                super.runApplications("cmd /c start " + "C:" + "Viano/Applications/PaintNet.lnk");
                ctrlGui.setImage("paint");
                commandOfPaint(jsgfRecognizer, config, ctrlGui, paint);
                setGrammar("apps", config, jsgfRecognizer);
                ctrlGui.setImage("games active");
            }

            else if (utterance.equals(list.get(4)))
            {

                racingCtrl(jsgfRecognizer, config, ctrlGui, racing);
                setGrammar("apps", config, jsgfRecognizer);
                ctrlGui.setImage("games active");
            }


        }


    }

    public void setList(List<String> list) {
        this.paint = list;
    }
    public void setListR(List<String> list) {
        this.racing = list;
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
    private void racingCtrl(LiveSpeechRecognizer jsgfRecognizer, Configuration config, CtrlGui ctrlGui, List list) {
        setGrammar("racing", config, jsgfRecognizer);
        ctrlGui.setImage("racing");
        super.runApplications("cmd /c start " + "C:" + "Viano/Applications/BurnoutLauncher.lnk");
        while (true) {

            String utterance = jsgfRecognizer.getResult().getHypothesis();
            ctrlGui.setWords(utterance);

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
                Thread thread = new Thread(new Stop(jsgfRecognizer, config, ctrlGui, list));
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
    CtrlGui gui;
    List list;
    static String utterance = "" ;
    static String str = "";

    public   Stop(LiveSpeechRecognizer recognizer,Configuration configuration,CtrlGui gui,List list)
    {
        this.recognizer = recognizer;
        this.configuration = configuration;
        this.gui = gui;
        this.list = list;
    }

    public void run()
    {

        while (true)
        {
            str = utterance;
            utterance = recognizer.getResult().getHypothesis();
            gui.setWords(utterance);
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
    private void commandOfPaint(LiveSpeechRecognizer jsgfRecognizer,Configuration config,CtrlGui ctrlGui,List list ) {
        setGrammar("paint", config, jsgfRecognizer);

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
                setGrammar("paint", config, jsgfRecognizer);
                ctrlGui.setImage("keyboard");
                ctrlGui.setImage("paint");
            }
            else if (utterance.equals(list.get(2)))
            {
                super.mouseControl(jsgfRecognizer, config, ctrlGui, Master.getMouseWords(), 25);
                setGrammar("paint", config, jsgfRecognizer);
                ctrlGui.setImage("mouse");
                ctrlGui.setImage("paint");
            }
            else if (utterance.equals(list.get(3))) //открыть
            {
                super.twoButtonPress(KeyEvent.VK_CONTROL, KeyEvent.VK_O);
            }
            else if (utterance.equals(list.get(4))) //сохранить
            {
                super.twoButtonPress(KeyEvent.VK_CONTROL, KeyEvent.VK_S);
            }
            else if (utterance.equals(list.get(5))) //обрезать
            {
                robot.keyPress(KeyEvent.VK_CONTROL);
                robot.keyPress(KeyEvent.VK_SHIFT);
                robot.keyPress(KeyEvent.VK_X);

                robot.keyRelease(KeyEvent.VK_CONTROL);
                robot.keyRelease(KeyEvent.VK_SHIFT);
                robot.keyRelease(KeyEvent.VK_X);
            }
            else if (utterance.equals(list.get(6))) //размер
            {
                super.twoButtonPress(KeyEvent.VK_CONTROL, KeyEvent.VK_R);
            }
            else if (utterance.equals(list.get(7))) //фоторедактор
            {
                try {
                    Runtime.getRuntime().exec("cmd /c start "+ "C:" + "Viano/Applications/PaintNet.lnk");;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if (utterance.equals(list.get(8))) //меню
            {
                super.oneButtonPress(KeyEvent.VK_ALT);
            }



        }
    }


}
