package Models;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by dimamj on 26.04.2015.
 */
public class KeyboardController extends AbstractController {

    private static final KeyboardController instance = new KeyboardController();

    private  List<String> list = config.KeyBoard_Words;


    {
        /*
        KeyBoard_Words
        0     ,"вверх"
        1     ,"вниз"
        2     ,"вправо"
        3     ,"влево"
        4     ,"назад"
        5     ,"свернуть"
        6     ,"закрыть"
        7     ,"войти"
        8     ,"переключиться" не работает alt
        9     ,"копировать"
        10     ,"вставить"
        11     ,"вырезать"
        12     ,"отменить"
        13     ,"удалить"
        14     ,"выделить все"
        15     ,"пуск"
        16     ,"диспетчер"
        17     ,"таб"
        18     ,"эскейп"
        19     ,"пробел"

                */
    }

    private KeyboardController()
    {}

    public static KeyboardController getInstance()
    {
        return instance;
    }

    public String startVoiceControl(LiveSpeechRecognizer recognizer,Configuration configuration,Boolean flag) {
            run = flag;

            setGrammar("keyboard", configuration, recognizer);
            listener.setImage("keyboard active");

            while (run) {
                String utterance = recognizer.getResult().getHypothesis();
                listener.wordRecognized(utterance);

                if(find(utterance)) {
                    exitController();
                    return utterance;
                }
                if (utterance.equals(list.get(0))) {
                    oneButtonPress(KeyEvent.VK_UP);
                }
                else if (utterance.equals(list.get(1))) {
                    oneButtonPress(KeyEvent.VK_DOWN);
                }
                else if (utterance.equals(list.get(2))) {
                    oneButtonPress(KeyEvent.VK_RIGHT);
                }
                else if (utterance.equals(list.get(3))) {
                    oneButtonPress(KeyEvent.VK_LEFT);
                }
                else if (utterance.equals(list.get(4))) {
                    twoButtonPress(KeyEvent.VK_WINDOWS,KeyEvent.VK_D);
                }
                else if (utterance.equals(list.get(5))){//close
                    twoButtonPress(KeyEvent.VK_ALT, KeyEvent.VK_F4);
                }
                else if (utterance.equals(list.get(6))){//ENTER
                    oneButtonPress(KeyEvent.VK_ENTER);

                }
                else if (utterance.equals(list.get(7))) {//ALT+TAB
                    oneButtonPress(KeyEvent.VK_F6);
                }
                else if (utterance.equals(list.get(8))) {//copy
                    twoButtonPress(KeyEvent.VK_CONTROL,KeyEvent.VK_C);
                }
                else if (utterance.equals(list.get(9))) {//paste
                    twoButtonPress(KeyEvent.VK_CONTROL, KeyEvent.VK_V);
                }
                else if (utterance.equals(list.get(10))) {//cut
                    twoButtonPress(KeyEvent.VK_CONTROL, KeyEvent.VK_X);
                }
                else if (utterance.equals(list.get(11))) {//CTRL+Z
                    twoButtonPress(KeyEvent.VK_CONTROL, KeyEvent.VK_Z);
                }
                else if (utterance.equals(list.get(12))){//Delete
                    oneButtonPress(KeyEvent.VK_DELETE);
                }
                else if (utterance.equals(list.get(13))){//CTRL+A
                    twoButtonPress(KeyEvent.VK_CONTROL, KeyEvent.VK_A);
                }
                else if (utterance.equals(list.get(14))){//start menu
                    oneButtonPress(KeyEvent.VK_WINDOWS);
                }
                else if (utterance.equals(list.get(15))){
                    robot.keyPress(KeyEvent.VK_CONTROL);
                    robot.keyPress(KeyEvent.VK_SHIFT);
                    robot.keyPress(KeyEvent.VK_ESCAPE);
                    robot.keyRelease(KeyEvent.VK_CONTROL);
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                    robot.keyRelease(KeyEvent.VK_ESCAPE);
                }
                else if (utterance.equals(list.get(16))) {
                    oneButtonPress(KeyEvent.VK_TAB);
                }
                else if (utterance.equals(list.get(17))) {
                    oneButtonPress(KeyEvent.VK_ESCAPE);
                }
                else if (utterance.equals(list.get(18))) {
                    oneButtonPress(KeyEvent.VK_SPACE);
                }

            }

        return "";
        }



    @Override
    public void exitController() {
        listener.setImage("keyboard");
    }

}
