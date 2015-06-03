package Models;

import Presenter.RecognitionListener;


import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

/**
 * Created by dimamj on 25.04.2015.
 */
public class Config {

    private  Properties prop = new Properties();
    private  Properties properties = new Properties();
    private  final String version = "1.0";
    private FileOperations fop = FileOperations.getInstance();

    private static final String ACOUSTIC_MODEL_ENG = "resource:/edu/cmu/sphinx/models/en-us/en-us";
    private static final String DICTIONARY_PATH_ENG = "file:C:/Viano/data/dict/cmudict-en-us.dict";
    private static final String GRAMMAR_PATH_ENG = "file:C:/Viano/data/gram_eng/";
    private static final String ACOUSTIC_MODEL_RUS = "resource:/edu/cmu/sphinx/models/en-us/rus";
    private static final String DICTIONARY_PATH_RUS = "file:C:/Viano/data/dict/msu_ru_nsh.dic";
    private static final String GRAMMAR_PATH_RUS = "file:C:/Viano/data/gram_rus/";
    static Boolean flag = false;

    public  List<String> Master_Words = new ArrayList<String>();
    public  List<String> KeyBoard_Words = new ArrayList<String>();
    public  List<String> Mouse_Words = new ArrayList<String>();
    public  List<String> Computer_Words = new ArrayList<String>();
    public  List<String> Internet_Words = new ArrayList<String>();
    public  List<String> Applications_Words = new ArrayList<String>();
    public  List<String> Paint_Words = new ArrayList<String>();
    public  List<String> Racing_Words = new ArrayList<String>();
    public  List<String> Modules_Words = new ArrayList<String>();
    public  static   List<String> confList  = new ArrayList<String>();

    private     String WeatherURL;

    public String getFilecontain() {return filecontain;}

    private  String NewsURL;
    private  String startupFlag;
    private  String FilmsURL;
    private  String TorrentURL;
    private  String filecontain = "";
    private  String path = "C:/Viano/data/language.txt";
    private static String pathconfig = "C:/Viano/data/config.txt";

    private  edu.cmu.sphinx.api.Configuration configuration;

    public void setListener(RecognitionListener listener) {
        this.listener = listener;
    }

    private List<String> gramsList = Arrays.asList("apps.gram","computer.gram","internet.gram","keyboard.gram",
            "master.gram","mouse.gram","paint.gram","racing.gram","test.gram");
    private List<String> gramsListEng = Arrays.asList("master.gram","test.gram");
    private List<String> wordsList = Arrays.asList("apps_rus.txt","forApps.txt","forLinks.txt","web_rus.txt",
            "words_rus.txt","words_eng.txt");
    private List<String> othersList = Arrays.asList("cmdow.exe","song.wav");
    private List<String> dictList = Arrays.asList("msu_ru_nsh.dic","cmudict-en-us.dict");

    private  RecognitionListener listener;

    public  Configuration getConfiguration() {return configuration;}

    private  String mediaPath;
    private  String mediaLink;
    private  String paintNetPath ;
    private  String paintNetLink ;

    ArrayList<String> ex = new ArrayList<String>();

    public void setEx(ArrayList<String> ex) {
        this.ex = ex;
    }

    public Properties getProp() {return prop;}

    /**
     * Данный метод вызывается при первом запуске программы,
     * он получает ответ от gui, о том какой язык выбран и
     * после этого вызывает метод init() инициализирующий
     * разного рода настройки, записывает информацию о языке
     * в файл и передает управление следующему методу.
     *
     * @param recognizer null ссылка на обьект LiveSpeechRecognizer
     *
     * @return инициализированный обьект LiveSpeechRecognizer
     */
    private LiveSpeechRecognizer loadFirstModel(LiveSpeechRecognizer recognizer) {
        String flag[];
        while(true) {
            flag = listener.getText("start");
            if (flag[0].equals("1")) {
                init();
                fop.write(path, "english");
                setJavaTool();
                recognizer =  loadLanguageModel(recognizer, ACOUSTIC_MODEL_ENG, DICTIONARY_PATH_ENG, GRAMMAR_PATH_ENG);
                break;
            }
            else if(flag[0].equals("2")) {
                init();
                fop.write(path, "russian");
                setJavaTool();
                recognizer = loadLanguageModel(recognizer,ACOUSTIC_MODEL_RUS,DICTIONARY_PATH_RUS,GRAMMAR_PATH_RUS);
                break;

            }
            else {}
        }
        return recognizer;
    }

    private void init() {
        fop.fileSystemInit();
        propertiesInit();
        listener.setProgressVisible("start");
        createFolders();
        loadLabels(mediaLink, mediaPath);
        loadLabels(paintNetLink,paintNetPath);
        fop.write(pathconfig, startupFlag + "\n" + WeatherURL + "\n" + NewsURL
                + "\n" + FilmsURL + "\n" + TorrentURL);

        String path = "C:/Viano/data/";
        fop.copyFiles("/others/Transcription.txt", path + "dict/Transcription.txt", true);

        for(String s:dictList){
            fop.copyFiles("/edu/cmu/sphinx/models/en-us/" + s, path + "dict/" + s, true);
        }

        for(String s:gramsList){
            fop.copyFiles("/gram_rus/" + s, path + "gram_rus/" + s, true);
        }
        for(String s:gramsListEng){
            fop.copyFiles("/gram_eng/" + s, path + "gram_eng/" + s, true);
        }
        for(String s:wordsList){
            fop.copyFiles("/words/" + s, path + "words/" + s, true);
        }
        for(String s:othersList){
            fop.copyFiles("/others/" + s, path + s, true);
        }

    }

    /**
     * Копирование properties файлов из Jar в папку
     * и считывание из них информации и присвоение ее
     * переменным
     */
    private void propertiesInit(){
        String path = "C:/Viano/data/";
        fop.copyFiles("/config.properties", path + "config.properties", true);
        fop.copyFiles("/ru.properties", path + "ru.properties", true);
        fop.copyFiles("/eng.properties", path + "eng.properties", true);

        try {
            String pathF = "C:/Viano/data/config.properties";
            prop.load(new InputStreamReader(new FileInputStream(pathF),"UTF-8"));

        } catch (Exception e) {e.printStackTrace();}

        WeatherURL = prop.getProperty("WeatherURL");
        NewsURL = prop.getProperty("NewsURL");
        startupFlag = prop.getProperty("startupFlag");
        FilmsURL = prop.getProperty("FilmsURL");
        TorrentURL = prop.getProperty("TorrentURL");
        mediaPath = prop.getProperty("mediaPath");
        mediaLink = prop.getProperty("mediaLink");
        paintNetPath = prop.getProperty("paintNetPath");
        paintNetLink = prop.getProperty("paintNetLink");

    }

    public Boolean isJar(){

        URI uri = null;
        try {uri = getClass().getResource("/others").toURI();} catch (URISyntaxException e) {
            e.printStackTrace();
        }

       return  uri.toString().split("!").length>1 ? true : false;
    }

    /**
     * Из-за проблем с кодировкой прии русском языке
     * была введенна установка системной перемены
     * JAVA_TOOL_OPTIONS на кодировку UTF-8
     */
    private void setJavaTool() {
        if(isJar()) {
            try {
                Runtime.getRuntime().exec("setx JAVA_TOOL_OPTIONS -Dfile.encoding=UTF8");
                System.exit(0);
            } catch (Exception e) {e.printStackTrace();}
        }
    }

    public Properties getProperties() {return properties;}

    /**
     * Данный метод инициализирует обьект для распознования
     * речи, для этого ему передаются опреленные параметры.
     * Так же инициализируются списки с командами,настройками.
     * В зависимости от языка загружается properties файл,
     * содержащий тексты для интерфейса.
     * Все параметры у своего языка свои.
     *
     * @param recognizer null ссылка на обьект LiveSpeechRecognizer
     * @param acoustic путь к папке аккустической модели
     * @param dict путь к словарю
     * @param gram путь к папке с грамматикой
     *
     * @return инициализированный обьект LiveSpeechRecognizer
     */
    private   LiveSpeechRecognizer loadLanguageModel(LiveSpeechRecognizer recognizer,String acoustic,String dict,
                                                     String gram) {
        configuration = new edu.cmu.sphinx.api.Configuration();
        configuration.setAcousticModelPath(acoustic);
        configuration.setDictionaryPath(dict);
        configuration.setGrammarPath(gram);
        configuration.setUseGrammar(true);
        configuration.setGrammarName("master");

        try {
            recognizer = new LiveSpeechRecognizer(configuration);
        } catch (IOException e) {
            e.printStackTrace();
        }

        listener.disposeGui("start");

        filecontain = fop.read(path, 1);
        loadWords(filecontain);
        confList  = fop.readMas(pathconfig,confList);
        if(isJar())
            startup(confList);

        if (filecontain.equals("english")) {
            try {
                String path = "C:/Viano/data/eng.properties";
                FileInputStream in = new FileInputStream(path);
                properties.load(new InputStreamReader(in,"UTF-8"));
                in.close();

            } catch (Exception e) {e.printStackTrace();}
        }
        else if(filecontain.equals("russian")) {
            try {
                String path = "C:/Viano/data/ru.properties";
                FileInputStream in = new FileInputStream(path);
                properties.load(new InputStreamReader(in,"UTF-8"));
                in.close();

            } catch (Exception e) {e.printStackTrace();}
        }
        return recognizer;

    }

    public List<String> getConfList() {return confList;}

    private  void createFolders() {

        File myPath = new File("C:/Viano/Applications/");
        myPath.mkdirs();
        myPath = new File("C:/Viano/Photo/");
        myPath.mkdirs();
        myPath = new File("C:/Viano/Video/");
        myPath.mkdirs();
        myPath = new File("C:/Viano/data/gram_rus");
        myPath.mkdirs();
        myPath = new File("C:/Viano/data/gram_eng");
        myPath.mkdirs();
        myPath = new File("C:/Viano/data/words");
        myPath.mkdirs();
        myPath = new File("C:/Viano/data/dict");
        myPath.mkdirs();

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {e.printStackTrace();}
    }

    /**
     * С помощью скрипта, создаются ярлыки программ и
     * помещаются в папку C:/Viano/Applications
     *
     * @param fileName имя файла
     * @param filePath путь к файлу
     */
    private  void loadLabels(String fileName,String filePath) {

        String script = "Set sh = CreateObject(\"WScript.Shell\")"
                + "\nSet shortcut = sh.CreateShortcut(\"" +  fileName + "\")"
                + "\nshortcut.TargetPath = \""+ filePath +"\""
                + "\nshortcut.Save";

        File file = new File("C:/Viano/temp.vbs");
        try {
            FileOutputStream fo = new FileOutputStream(file);
            fo.write(script.getBytes());
            fo.close();

            Runtime.getRuntime().exec("wscript.exe " + file.getAbsolutePath());
            try {Thread.sleep(1000);} catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {e.printStackTrace();}
    }

    /**
     * Данный метод создан для чтения специально
     * созданных файлов, содержащих списки команд.
     * Вид файла такой:
     *
     * !# Internet
     * мышь
     * поиск
     * прогноз погоды
     * #
     *
     * Метод собирает все команды в один список
     * и запминает имя после !#, по нему и осуществляется
     * добавления списка в необходимый
     *
     * @param resource имя файла, с которо нужно читать
     */
    protected void parserWords(String resource) {
        String str = "";
        Boolean flag = false;
        String[] list = null;

        BufferedReader reader = null;
        try {reader = new BufferedReader(new FileReader(resource));}
        catch (Exception e) {/*NOP*/}

        try {
            while ((str=reader.readLine())!=null) {
                if (str.startsWith("!#")) {
                    flag = true;
                    list = str.split(" ");
                }
                else if(str.startsWith("#")) {
                    flag = false;
                    loadLists(list[1],ex);
                    ex.clear();
                }
                else if(flag && ex!=null) {
                    ex.add(str);
                }
            }
        } catch (IOException e) {e.printStackTrace();}
    }
    private  void loadWords(String filecontain) {
        if (filecontain.equals("english")) {
            parserWords("C:/Viano/data/words/words_eng.txt");
        }

        else if (filecontain.equals("russian")) {
            parserWords("C:/Viano/data/words/words_rus.txt");
            parserWords("C:/Viano/data/words/apps_rus.txt");
            parserWords("C:/Viano/data/words/web_rus.txt");
        }
    }

    protected  void loadLists(String str,List list) {
        if(str.equals("Master")) {
            Master_Words.addAll(list);
        }
        else if (str.equals("KeyBoard")) {
            KeyBoard_Words.addAll(list);
        }
        else if (str.equals("Mouse")) {
            Mouse_Words.addAll(list);
        }
        else if (str.equals("Computer")) {
            Computer_Words.addAll(list);
        }
        else if (str.equals("Internet")) {
            if(!Internet_Words.isEmpty())
                Internet_Words.clear();

            Internet_Words.addAll(list);
        }
        else if (str.equals("Applications")) {
            if(!Applications_Words.isEmpty())
                Applications_Words.clear();

            Applications_Words.addAll(list);
        }
        else if (str.equals("Paint")) {
            Paint_Words.addAll(list);
        }
        else if (str.equals("Racing")) {
            Racing_Words.addAll(list);
        }
        else if (str.equals("Modules")) {
            Modules_Words.addAll(list);
        }
    }

    /**
     * Метод активизирующий или снимающий автозагрузку
     * за счет специальных команд, выполняемых через консоль.
     *
     * @param list список настроек
     */
    protected void startup(List<String> list){
        String[] str = Config.class.getResource("").toString().split("!");
        String path = str[0].substring(10);
        path = path.replaceAll("/","\\\\");
        String start = "cmd /C REG ADD HKCU\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Run /v " +
                "Viano /d " + path + " /f";
        String delete = "cmd /C REG ADD HKCU\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Run /v " +
                "Viano /d null /f";


            if (list.get(0).equals("true")) {
                try {Runtime.getRuntime().exec(start);} catch (IOException e) {e.printStackTrace();}
            } else {
                try{Runtime.getRuntime().exec(delete);}catch (Exception e) {return;}

            }
        }

    /**
     * Метод перезапускает запущенный Jar файл. То есть
     * берется путь к файлу и запускается через командную строку.
     */
    private void restart(){
        if(isJar()) {
            String[] str = Config.class.getResource("").toString().split("!");
            String path = str[0].substring(10);
            try {
                Runtime.getRuntime().exec("java -jar " + path);
                System.exit(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.exit(0);
        }
    }

    /**
     * Чтение версии из properties файла,
     * если версия пользователя не совпадает с версией
     * в Jar файле, происходит обновление и перезапуск.
     */
    private void version(){
        try {
            String pathF = "C:/Viano/data/config.properties";
            FileInputStream in = new FileInputStream(pathF);
            prop.load(new InputStreamReader(in,"UTF-8"));
            in.close();
        } catch (Exception e) {e.printStackTrace();}

        if(!version.equals(prop.getProperty("version"))){
            update();
            restart();
        }
    }

    /**
     * Обновление включает в себя удаление и копирование
     * новых файлов, необходимых для работы. Так же заменяет
     * версию на новую.
     */
    private void update(){
        listener.setLabel("Viano Update","start");
        fop.fileSystemInit();
        String path = "C:/Viano/data/";
        fop.deleteFiles(path + "dict/Transcription.txt");
        fop.copyFiles("/others/Transcription.txt", path + "dict/Transcription.txt", true);

        for(String s:gramsList){
            if(!s.equals("apps.gram")) {
                if(!s.equals("internet.gram")) {
                    fop.deleteFiles(path + "gram_rus/" + s);
                    fop.copyFiles("/gram_rus/" + s, path + "gram_rus/" + s, true);
                }
            }
        }
        for(String s:gramsListEng){
            if(!s.equals("apps.gram")||!s.equals("internet.gram")) {
                fop.deleteFiles(path + "gram_eng/" + s);
                fop.copyFiles("/gram_eng/" + s, path + "gram_eng/" + s, true);
            }
        }
        
        fop.deleteFiles(path + "words/words_eng.txt");
        fop.copyFiles("/words/words_eng.txt", path + "words/words_eng.txt", true);
        fop.deleteFiles(path + "words/words_rus.txt");
        fop.copyFiles("/words/words_rus.txt", path + "words/words_rus.txt", true);

        for(String s:othersList){
            fop.deleteFiles(path + s);
            fop.copyFiles("/others/" + s, path + s, true);
        }

        fop.deleteFiles(path + "config.properties");
        fop.copyFiles("/config.properties", path + "config.properties", true);
        fop.deleteFiles(path + "ru.properties");
        fop.copyFiles("/ru.properties", path + "ru.properties", true);
        fop.deleteFiles(path + "eng.properties");
        fop.copyFiles("/eng.properties", path + "eng.properties", true);


    }


    public FileOperations getFop() {
        return fop;
    }

    /**
     * Стартовый метод данного класса. В нем проверяется
     * если файл пустой, то делаем первую загрузку, со всеми настройками,
     * если нет, загружаем с параметрами соответствующими языку.
     *
     * @param recognizer null ссылка типа LiveSpeechRecognizer
     *
     * @return инициализированный и готовый к работе
     * обьект LiveSpeechRecognizer
     */
    public  LiveSpeechRecognizer beginSettings(LiveSpeechRecognizer recognizer) {
        filecontain = fop.read(path, 1);

        if (filecontain.isEmpty()) {
            recognizer = loadFirstModel(recognizer);
        }
        else if (filecontain.equals("english")) {
            listener.disposeElements("start");
            version();
            listener.setProgressVisible("start");
            recognizer =  loadLanguageModel(recognizer, ACOUSTIC_MODEL_ENG, DICTIONARY_PATH_ENG, GRAMMAR_PATH_ENG);
        }
        else if(filecontain.equals("russian")) {
            listener.disposeElements("start");
            version();
            listener.setProgressVisible("start");
            recognizer = loadLanguageModel(recognizer,ACOUSTIC_MODEL_RUS,DICTIONARY_PATH_RUS,GRAMMAR_PATH_RUS);
        }
        return recognizer;
    }
}
