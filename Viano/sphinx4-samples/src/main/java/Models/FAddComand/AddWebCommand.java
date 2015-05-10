package Models.FAddComand;

/**
 * Created by dimamj on 07.05.2015.
 */
public class AddWebCommand extends AbstractAddCommand {

    private static final AddWebCommand instance = new AddWebCommand();
    private String URLName = "";
    private String commandName = "";
    private int accent = 0;
    private static final String GRAMMAR_PATH_RUS =
            "C:/Viano/data/gram_rus/internet.gram";
    private static final String WORDS_PATH_LINKS_RUS =
            "C:/Viano/data/words/web_rus.txt";

    private String gram = "";
    private String dict = "";
    private String words = "";
    private String file = "C:/Viano/data/words/forLinks.txt";

    private AddWebCommand(){}
    protected static AddWebCommand getInstance(){
        return instance;
    }

    @Override
    public void start(String[] array,String lang) {
        init(array,lang);

        writeToGram(gram,commandName);
        writeToWords(words,commandName);
        writeToDict(dict,commandName,accent);
        writeToFile(file,URLName,commandName);
    }

    private void init(String[] array,String lang){
        URLName = array[0]; //google.com
        commandName = array[1]; //гугл
        accent = Integer.parseInt(array[2]); //2

        if(lang.equals("english")){

        }
        else if(lang.equals("russian")){
            gram = GRAMMAR_PATH_RUS;
            dict = DICTIONARY_PATH_RUS;
            words = WORDS_PATH_LINKS_RUS;
        }

    }
}
