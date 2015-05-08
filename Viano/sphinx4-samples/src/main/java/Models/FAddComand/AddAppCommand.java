package Models.FAddComand;

import java.net.URISyntaxException;
import java.nio.file.Paths;

/**
 * Created by dimamj on 07.05.2015.
 */
public class AddAppCommand extends AbstractAddCommand{

    private static final AddAppCommand instance = new AddAppCommand();
    private String fileName = "";
    private String commandName = "";
    private int accent = 0;

    private String gram = "";
    private String dict = "";
    private String words = "";
    private String file = "C:/Viano/data/words/forApps.txt";

    private AddAppCommand(){
    }
    protected static AddAppCommand getInstance(){
        return instance;
    }

    @Override
    public void start(String[] array,String lang) {

        init(array,lang);

            writeToGram(gram,commandName);
            writeToWords(words,commandName);
            writeToDict(dict,commandName,accent);
            writeToFile(file,fileName,commandName);

    }

    private void init(String[] array,String lang){
        fileName = array[0]; //skype
        commandName = array[1]; //скайп
        accent = Integer.parseInt(array[2]); //3

        if(lang.equals("english")){

        }
        else if(lang.equals("russian")){
            gram = GRAMMAR_PATH_APPS_RUS;
            dict = DICTIONARY_PATH_RUS;
            words = WORDS_PATH_APPS_RUS;
        }

    }
}
