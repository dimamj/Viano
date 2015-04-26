package Models;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;

/**
 * Created by dimamj on 26.04.2015.
 */
public class PaintCtrl extends AbstractController{


    private static final PaintCtrl instance = new PaintCtrl();

    private List<String> list = config.Paint_Words;


    {
        /*
       !# Paint
            назад
            клавиатура
            мышь
            открыть
            сохранить
            обрезать
            размер
            фоторедактор
            меню
            #
                */
    }

    private PaintCtrl()
    {}

    public static PaintCtrl getInstance()
    {
        return instance;
    }

    @Override
    public String startVoiceControl(LiveSpeechRecognizer jsgfRecognizer, Configuration config, Boolean flag) {
            run = flag;

            listener.setImage("paint");
            setGrammar("paint", config, jsgfRecognizer);

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
                    listener.setSpeedCursor(20);
                    exitController();
                    return utterance;
                }
                 else if (utterance.equals(list.get(1))) //открыть
                {
                    super.twoButtonPress(KeyEvent.VK_CONTROL, KeyEvent.VK_O);
                }
                else if (utterance.equals(list.get(2))) //сохранить
                {
                    super.twoButtonPress(KeyEvent.VK_CONTROL, KeyEvent.VK_S);
                }
                else if (utterance.equals(list.get(3))) //обрезать
                {
                    robot.keyPress(KeyEvent.VK_CONTROL);
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.keyPress(KeyEvent.VK_X);

                    robot.keyRelease(KeyEvent.VK_CONTROL);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.keyRelease(KeyEvent.VK_X);
                } else if (utterance.equals(list.get(4))) //размер
                {
                    super.twoButtonPress(KeyEvent.VK_CONTROL, KeyEvent.VK_R);
                }
                else if (utterance.equals(list.get(5))) //фоторедактор
                {
                    try {
                        Runtime.getRuntime().exec("cmd /c start "+ "C:" + "Viano/Applications/PaintNet.lnk");;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else if (utterance.equals(list.get(6))) //меню
                {
                    super.oneButtonPress(KeyEvent.VK_ALT);
                }



            }

            return "";

    }



    @Override
    public void exitController() {
        listener.setImage("games");
    }
}
