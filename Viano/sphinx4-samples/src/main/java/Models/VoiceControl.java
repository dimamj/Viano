package Models;

import View.CtrlGui;
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;

import java.util.List;

/**
 * Created by dimamj on 14.01.2015.
 */
public interface VoiceControl {
    void startVoiceControl(LiveSpeechRecognizer jsgfRecognizer,Configuration configuration, CtrlGui gui,List list);
}
