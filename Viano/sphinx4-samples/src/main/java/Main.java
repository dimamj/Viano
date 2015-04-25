import Models.Master;
import Models.VoiceControl;
import View.CtrlGui;
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;

import java.util.List;


/**
 * Created by dimamj on 12.01.2015.
 */
public class Main  {
    static LiveSpeechRecognizer recognizer;
    static Configuration configuration;
    static CtrlGui gui;
    static List list;
    public static void main(String[] args) {

        VoiceControl master = new Master();
        master.startVoiceControl(recognizer,configuration,gui,list);
    }

}
