package Models;

import Presenter.RecognitionListener;


import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.*;

/**
 * Created by dimamj on 25.04.2015.
 */
public class Config {


    private  Properties prop = new Properties();
    private  Properties properties = new Properties();
    private  final String version = "1.0";

    private static final String ACOUSTIC_MODEL_ENG =
            "resource:/edu/cmu/sphinx/models/en-us/en-us";
    private static final String DICTIONARY_PATH_ENG =
            "file:C:/Viano/data/dict/cmudict-en-us.dict";
    private static final String GRAMMAR_PATH_ENG =
            "file:C:/Viano/data/gram_eng/";
    private static final String ACOUSTIC_MODEL_RUS =
            "resource:/edu/cmu/sphinx/models/en-us/rus";
    private static final String DICTIONARY_PATH_RUS =
            "file:C:/Viano/data/dict/msu_ru_nsh.dic";
    private static final String GRAMMAR_PATH_RUS =
            "file:C:/Viano/data/gram_rus/";
    static Boolean flag = false;
    private FileSystem fs;
    private Boolean isJar = false;
    public     List<String> Master_Words = new ArrayList<String>();
    public    List<String> KeyBoard_Words = new ArrayList<String>();
    public    List<String> Mouse_Words = new ArrayList<String>();
    public    List<String> Computer_Words = new ArrayList<String>();
    public    List<String> Internet_Words = new ArrayList<String>();
    public    List<String> Applications_Words = new ArrayList<String>();
    public    List<String> Paint_Words = new ArrayList<String>();
    public    List<String> Racing_Words = new ArrayList<String>();
    public    List<String> Modules_Words = new ArrayList<String>();
    public  static   List<String> confList  = new ArrayList<String>();

    private     String WeatherURL;

    public String getFilecontain() {
        return filecontain;
    }

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

    public  Configuration getConfiguration() {
        return configuration;
    }

    private  String mediaPath;
    private  String mediaLink;
    private  String paintNetPath ;
    private  String paintNetLink ;


    public Properties getProp() {
        return prop;
    }

    private LiveSpeechRecognizer loadFirstModel(LiveSpeechRecognizer recognizer)
    {
        String flag[];
        System.out.println("Enter 1 or 2:");
        while(true) {
            flag = listener.getText("start");
            if (flag[0].equals("1"))
            {   init();
                wtite(path, "english");
                setJavaTool();
                recognizer =  loadLanguageModel(recognizer, ACOUSTIC_MODEL_ENG, DICTIONARY_PATH_ENG, GRAMMAR_PATH_ENG);
                break;
            }
            else if(flag[0].equals("2"))
            {   init();
                wtite(path,"russian");
                setJavaTool();
                recognizer = loadLanguageModel(recognizer,ACOUSTIC_MODEL_RUS,DICTIONARY_PATH_RUS,GRAMMAR_PATH_RUS);
                break;

            }
            else
            {

            }
        }
        return recognizer;
    }

    private void init()
    {
        fileSystemInit();
        propertiesInit();
        listener.setProgressVisible("start");
        createFolders();
        loadLabels(mediaLink, mediaPath);
        loadLabels(paintNetLink,paintNetPath);
        wtite(pathconfig,startupFlag+"\n"+WeatherURL+"\n"+NewsURL+"\n"+FilmsURL+"\n"+TorrentURL);

        String path = "C:/Viano/data/";
        copyFiles("/others/Transcription.txt",path+"dict/Transcription.txt",true);

        for(String s:dictList){
            copyFiles("/edu/cmu/sphinx/models/en-us/"+s,path+"dict/"+s,true);
        }

        for(String s:gramsList){
            copyFiles("/gram_rus/"+s,path+"gram_rus/"+s,true);
        }
        for(String s:gramsListEng){
            copyFiles("/gram_eng/"+s,path+"gram_eng/"+s,true);
        }
        for(String s:wordsList){
            copyFiles("/words/"+s,path+"words/"+s,true);
        }
        for(String s:othersList){
            copyFiles("/others/"+s,path+s,true);
        }

    }

    private void fileSystemInit(){
        try {
            final URI uri = getClass().getResource("/others").toURI();
            final Map<String, String> env = new HashMap<>();
            if(uri.toString().split("!").length>1) {
                isJar=true;
                final String[] array = uri.toString().split("!");
                fs = FileSystems.newFileSystem(URI.create(array[0]), env);
                // final Path path = fs.getPath(array[1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void propertiesInit(){
        String path = "C:/Viano/data/";
        copyFiles("/config.properties",path+"config.properties",true);
        copyFiles("/ru.properties",path+"ru.properties",true);
        copyFiles("/eng.properties",path+"eng.properties",true);

        try {
            String pathF = "C:/Viano/data/config.properties";
            prop.load(new InputStreamReader(new FileInputStream(pathF),"UTF-8"));

        } catch (Exception e) {
            e.printStackTrace();
        }

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
        try {
            uri = getClass().getResource("/others").toURI();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

       return  uri.toString().split("!").length>1 ? true : false;
    }

    private void setJavaTool()
    {
        if(isJar()) {
            try {
                Runtime.getRuntime().exec("setx JAVA_TOOL_OPTIONS -Dfile.encoding=UTF8");
              /*  String[] str = Config.class.getResource("").toString().split("!");
                String path = str[0].substring(10);
                System.out.println(path);
                Runtime.getRuntime().exec("java -jar " + path);*/
                System.exit(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Properties getProperties() {
        return properties;
    }

    private   LiveSpeechRecognizer loadLanguageModel(LiveSpeechRecognizer recognizer,String acoustic,String dict,
                                                     String gram)
    {
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

        filecontain = read(path,1);
        loadWords(filecontain);
        confList  = readMas(pathconfig);
        if(isJar())
            startup(confList);

        if (filecontain.equals("english")) {
            try {
                String path = "C:/Viano/data/eng.properties";
                FileInputStream in = new FileInputStream(path);
                properties.load(new InputStreamReader(in,"UTF-8"));
                in.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(filecontain.equals("russian")) {
            try {
                String path = "C:/Viano/data/ru.properties";
                FileInputStream in = new FileInputStream(path);
                properties.load(new InputStreamReader(in,"UTF-8"));
                in.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return recognizer;

    }

    public List<String> getConfList() {
        return confList;
    }

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
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void copyFiles(String sourcePath,String targetPath,Boolean inJar){
        Path source = null;
        try {
            if(isJar()&&inJar) {
                source = fs.getPath(sourcePath);
            }else if(isJar()&&!inJar) {
                source = Paths.get(sourcePath);
            }
            else {
                source = Paths.get(Master.class.getResource(sourcePath).toURI());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Path target = Paths.get(targetPath);
        try {
            System.out.println(source);
            System.out.println(target);
            Files.copy(source,target);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteFiles(String sourcePath){
        Path source = Paths.get(sourcePath);
        try {
            Files.delete(source);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected    void parserWords(String resource)
    {
        String str = "";
        Boolean flag = false;
        ArrayList<String> ex = new ArrayList<String>();
        String[] list = null;

        BufferedReader reader = null;
        try {reader = new BufferedReader(new FileReader(resource));}
        catch (Exception e) {/*NOP*/}

        try {
            while ((str=reader.readLine())!=null)
            {
                if (str.startsWith("!#"))
                {
                    flag = true;
                    list = str.split(" ");
                }
                else if(str.startsWith("#"))
                {
                    flag = false;
                    loadLists(list[1],ex);
                    ex.clear();
                }
                else if(flag && ex!=null)
                {
                    ex.add(str);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private  void loadWords(String filecontain)
    {
        if (filecontain.equals("english"))
        {
            parserWords("C:/Viano/data/words/words_eng.txt");
        }

        else if (filecontain.equals("russian"))
        {
            parserWords("C:/Viano/data/words/words_rus.txt");
            parserWords("C:/Viano/data/words/apps_rus.txt");
            parserWords("C:/Viano/data/words/web_rus.txt");
        }
    }

    private  void loadLists(String str,ArrayList<String> list)
    {
        if(str.equals("Master"))
        {
            Master_Words.addAll(list);
        }
        else if (str.equals("KeyBoard"))
        {
            KeyBoard_Words.addAll(list);
        }
        else if (str.equals("Mouse"))
        {
            Mouse_Words.addAll(list);
        }
        else if (str.equals("Computer"))
        {
            Computer_Words.addAll(list);
        }
        else if (str.equals("Internet"))
        {
            if(!Internet_Words.isEmpty())
                Internet_Words.clear();

            Internet_Words.addAll(list);
        }
        else if (str.equals("Applications"))
        {
            if(!Applications_Words.isEmpty())
                Applications_Words.clear();

            Applications_Words.addAll(list);
        }
        else if (str.equals("Paint"))
        {
            Paint_Words.addAll(list);
        }
        else if (str.equals("Racing"))
        {
            Racing_Words.addAll(list);
        }
        else if (str.equals("Modules"))
        {
            Modules_Words.addAll(list);
        }
    }

    private static void createFile(String path)
    {
        File f = new File(path);
        f.getParentFile().mkdirs();
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public  String read(String path,int line)
    {
        String result = "";
        BufferedReader reader;
        int countLine = 0;
        while (true) {
            try {
                reader = new BufferedReader(new FileReader(path));
                String s;
                while ((s=reader.readLine()) != null) {
                    countLine++;
                    if(line==countLine)
                    result = s;
                }
                reader.close();
                break;
            }
            catch (Exception e) {createFile(path);}
        }

        return result;
    }
    public  List<String> readMas(String path)
    {
        BufferedReader reader;

        while (true) {
            try {
                reader = new BufferedReader(new FileReader(path));
                String s;
                int i = 0;
                while ((s=reader.readLine()) != null) {
                    confList.add(i,s);
                    i++;
                }
                reader.close();
                break;
            }
            catch (Exception e) {createFile(path);}
        }

        return confList;
    }
    public  void wtite(String path,String language)
    {

        try {
            PrintWriter out = new PrintWriter(path);
            out.print(language);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    protected void startup(List<String> list){
        String startupPath = "C:/Viano/";

        //C:\ProgramData\Microsoft\Windows\Start Menu\Programs\StartUp
        String[] str = Config.class.getResource("").toString().split("!");
        String path = str[0].substring(10);
        System.out.println(path);
        path = path.replaceAll("/","\\\\");
        System.out.println("=====================================================================");
        System.out.println(path);
        String start = "cmd /C REG ADD HKCU\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Run /v " +
                "Viano /d " + path + " /f";
        String delete = "cmd /C REG ADD HKCU\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Run /v " +
                "Viano /d null /f";

        System.out.println(delete);

            if (list.get(0).equals("true")) {
                try {
                    Runtime.getRuntime().exec(start);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try{
                    Runtime.getRuntime().exec(delete);
                }catch (Exception e)
                {
                    return;
                }


            }
        }

    private void restart(){
        if(isJar()) {
            String[] str = Config.class.getResource("").toString().split("!");
            String path = str[0].substring(10);
            System.out.println(path);
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

    private void version(){
        try {
            String pathF = "C:/Viano/data/config.properties";
            FileInputStream in = new FileInputStream(pathF);
            prop.load(new InputStreamReader(in,"UTF-8"));
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(!version.equals(prop.getProperty("version"))){
            update();
            restart();
        }
    }

    private void update(){
        listener.setLabel("Viano Update","start");
        fileSystemInit();
        String path = "C:/Viano/data/";
        deleteFiles(path+"dict/Transcription.txt");
        copyFiles("/others/Transcription.txt",path+"dict/Transcription.txt",true);

        for(String s:dictList){
            deleteFiles(path+"dict/"+s);
            copyFiles("/edu/cmu/sphinx/models/en-us/"+s,path+"dict/"+s,true);
        }

        for(String s:gramsList){
            if(!s.equals("apps.gram")) {
                if(!s.equals("internet.gram")) {
                    deleteFiles(path + "gram_rus/" + s);
                    copyFiles("/gram_rus/" + s, path + "gram_rus/" + s, true);
                }
            }
        }
        for(String s:gramsListEng){
            if(!s.equals("apps.gram")||!s.equals("internet.gram")) {
                deleteFiles(path + "gram_eng/" + s);
                copyFiles("/gram_eng/" + s, path + "gram_eng/" + s, true);
            }
        }

            deleteFiles(path+"words/words_eng.txt");
            copyFiles("/words/words_eng.txt",path+"words/words_eng.txt",true);
            deleteFiles(path+"words/words_rus.txt");
            copyFiles("/words/words_rus.txt",path+"words/words_rus.txt",true);

        for(String s:othersList){
            deleteFiles(path+s);
            copyFiles("/others/"+s,path+s,true);
        }

        deleteFiles(path + "config.properties");
        copyFiles("/config.properties", path + "config.properties",true);
        deleteFiles(path+"ru.properties");
        copyFiles("/ru.properties",path+"ru.properties",true);
        deleteFiles(path+"eng.properties");
        copyFiles("/eng.properties",path+"eng.properties",true);


    }

    public  LiveSpeechRecognizer beginSettings(LiveSpeechRecognizer recognizer)
    {

        filecontain = read(path,1);

        if (filecontain.isEmpty())
        {
            recognizer = loadFirstModel(recognizer);
        }
        else if (filecontain.equals("english"))
        {
            listener.disposeElements("start");
            version();
            listener.setProgressVisible("start");
            recognizer =  loadLanguageModel(recognizer, ACOUSTIC_MODEL_ENG, DICTIONARY_PATH_ENG, GRAMMAR_PATH_ENG);
        }
        else if(filecontain.equals("russian"))
        {
            listener.disposeElements("start");
            version();
            listener.setProgressVisible("start");
            recognizer = loadLanguageModel(recognizer,ACOUSTIC_MODEL_RUS,DICTIONARY_PATH_RUS,GRAMMAR_PATH_RUS);
        }
        return recognizer;
    }
}
