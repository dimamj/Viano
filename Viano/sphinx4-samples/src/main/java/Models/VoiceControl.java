package Models;

import Presenter.VPresenter;
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;

import java.util.List;

/**
 * Created by dimamj on 14.01.2015.
 */
public interface VoiceControl {
    void startVoiceControl(LiveSpeechRecognizer jsgfRecognizer,Configuration configuration, VPresenter presenter,List list);
}
