package Models;

import Presenter.RecognitionListener;


import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dimamj on 25.04.2015.
 */
public class Config {

    private static final String ACOUSTIC_MODEL_ENG =
            "resource:/edu/cmu/sphinx/models/en-us/en-us";
    private static final String DICTIONARY_PATH_ENG =
            "resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict";
    private static final String GRAMMAR_PATH_ENG =
            "resource:/gram_eng/";
    private static final String ACOUSTIC_MODEL_RUS =
            "resource:/edu/cmu/sphinx/models/en-us/rus";
    private static final String DICTIONARY_PATH_RUS =
            "resource:/edu/cmu/sphinx/models/en-us/msu_ru_nsh.dic";
    private static final String GRAMMAR_PATH_RUS =
            "resource:/gram_rus/";
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

    private     String WeatherURL = "google.com.ua/search?q=weather";
    private     String NewsURL = "ukr.net";
    private     String startupFlag = "false";
    private     String FilmsURL = "kinogo.net";
    private     String TorrentURL = "rutorg.org";
    private  String filecontain = "";
    private  String path = "C:"+ File.separator+ "Viano" +File.separator+"language.txt";
    private static String pathconfig = "C:"+ File.separator+ "Viano" +File.separator+"config.txt";

    private  edu.cmu.sphinx.api.Configuration configuration;

    public void setListener(RecognitionListener listener) {
        this.listener = listener;
    }

    private  RecognitionListener listener;
    private  Boolean test;
  //  private  static  InternetCtrl internetCtrl = InternetCtrl.getInstance();!!!!!!!!!!!!!!!!!!!!


    public  Configuration getConfiguration() {
        return configuration;
    }

    private  String mediaPath = "C:/Program Files/Windows Media Player/wmplayer.exe";
    private  String mediaLink = "C:/Viano/Applications/wmplayer.lnk";
    private  String paintNetPath = "C:/Program Files/Paint.NET/PaintDotNet.exe";
    private  String paintNetLink = "C:/Viano/Applications/PaintNet.lnk";

    private LiveSpeechRecognizer loadFirstModel(LiveSpeechRecognizer recognizer)
    {
        String flag = "";
        System.out.println("Enter 1 or 2:");
        while(true) {
            flag = listener.getText("start");
            if (flag.equals("1"))
            {   init();
                wtite(path, "english");
                setJavaTool();
                recognizer =  loadLanguageModel(recognizer, ACOUSTIC_MODEL_ENG, DICTIONARY_PATH_ENG, GRAMMAR_PATH_ENG);
                break;
            }
            else if(flag.equals("2"))
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
        listener.setProgressVisible("start");
        createFolders();
        loadLabels(mediaLink, mediaPath);
        loadLabels(paintNetLink,paintNetPath);
        wtite(pathconfig,startupFlag+"\n"+WeatherURL+"\n"+NewsURL+"\n"+FilmsURL+"\n"+TorrentURL);
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
        copyFiles("/others/song.wav","C:/Viano/song.wav");
        copyFiles("/others/cmdow.exe","C:/Viano/cmdow.exe");

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
        if(isJar) {
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
        if (filecontain.equals("english"))
        {

        }
        else if(filecontain.equals("russian"))
        {
            loadWords(filecontain);
            confList  = readMas(pathconfig);
            //internetCtrl.setConfig(conf);!!!!!!!!!!!!!!!!!!!!!!!
        }
        return recognizer;

    }

    public static List<String> getConfList() {
        return confList;
    }


    private  void createFolders() {

        File myPath = new File("C:/Viano/Applications/");
        myPath.mkdirs();
        myPath = new File("C:/Viano/Photo/");
        myPath.mkdirs();
        myPath = new File("C:/Viano/Video/");
        myPath.mkdirs();

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void copyFiles(String sourcePath,String targetPath){
        Path source = null;
        try {
            if(isJar) {
                source = fs.getPath(sourcePath);
            } else {
                source = Paths.get(Master.class.getResource(sourcePath).toURI());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Path target = Paths.get(targetPath);
        try {
            Files.copy(source,target);
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
                Thread.sleep(700);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private  void parserWords(String resource)
    {
        String str = "";
        Boolean flag = false;
        ArrayList<String> ex = new ArrayList<String>();
        String[] list = null;

        InputStream in=Master.class.getResourceAsStream(resource);
        BufferedReader reader = null;
        try {reader = new BufferedReader(new InputStreamReader(in));}
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
            parserWords("/words/words_eng.txt");
        }

        else if (filecontain.equals("russian"))
        {
            parserWords("/words/words_rus.txt");
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
            Internet_Words.addAll(list);
        }
        else if (str.equals("Applications"))
        {
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
            listener.setProgressVisible("start");
            recognizer =  loadLanguageModel(recognizer, ACOUSTIC_MODEL_ENG, DICTIONARY_PATH_ENG, GRAMMAR_PATH_ENG);
        }
        else if(filecontain.equals("russian"))
        {
            listener.disposeElements("start");
            listener.setProgressVisible("start");
            recognizer = loadLanguageModel(recognizer,ACOUSTIC_MODEL_RUS,DICTIONARY_PATH_RUS,GRAMMAR_PATH_RUS);
        }
        return recognizer;
    }
}
