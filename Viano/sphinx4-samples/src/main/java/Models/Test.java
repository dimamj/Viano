package Models;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;

import java.util.List;

/**
 * Created by dimamj on 04.05.2015.
 */
public class Test extends AbstractController{

    public    List<String> Test_Words = config.Modules_Words;

    private static final Test instance = new Test();

    private Test()
    {}

    public static Test getInstance()
    {
        return instance;
    }


    @Override
    public String startVoiceControl(LiveSpeechRecognizer jsgfRecognizer, Configuration config, Boolean flag) {
        run = flag;

        setGrammar("test", config, jsgfRecognizer);

        for(int i=0;i<Test_Words.size();i++)
        {
            listener.setLabel(Test_Words.get(i),"test");

            while (true)
            {
                String utterance = jsgfRecognizer.getResult().getHypothesis();
                if(utterance.equals(Test_Words.get(i))){
                    listener.testWord(utterance);
                    break;
                }


            }
        }

        listener.disposeGui("test");
        return "";
    }

    @Override
    public void exitController() {
        //NOP//
    }
}
