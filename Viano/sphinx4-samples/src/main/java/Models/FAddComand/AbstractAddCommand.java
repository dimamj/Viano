package Models.FAddComand;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;


public abstract class AbstractAddCommand {

    protected static final String DICTIONARY_PATH_ENG =
            "C:/Viano/data/dict/cmudict-en-us.dict";
    protected static final String DICTIONARY_PATH_RUS =
            "C:/Viano/data/dict/msu_ru_nsh.dic";
    private static final String transcriptions =
            "C:/Viano/data/dict/Transcription.txt";

    private final Map<String,String> letters = initLetters();
    private final List<String> vowelLetters = Arrays.asList("а","е","ё","и","о",
            "у","ы","э","ю","я");

    abstract public void start(String[] array, String lang);

    protected void writeToGram(String path, String word) {
        Path filePath = Paths.get(path);
        Charset charset = Charset.forName("UTF-8");

        try (BufferedReader reader = Files.newBufferedReader(filePath, charset);
             BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile(),true))) {

            String str = "";
            String result = "";
            String line = "";

            while ((str = reader.readLine()) != null) {
                line = str;
                if (str.startsWith("<standard>")) {
                    line = str;
                    while ((str = reader.readLine()) != null) {
                        if (str.startsWith(";")) {
                            break;
                        }
                        if(str.equals("|"+word)){
                            return;
                        }
                        line += "\n" + str;
                    }
                    line = line + "\n|" + word + "\n;";

                }
                result += line + "\n";
            }
            BufferedWriter reload = new BufferedWriter(new FileWriter(filePath.toFile()));
            reload.close();
            writer.write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    protected void writeToDict(String path, String word,int accent) {
        Path filePath = Paths.get(path);
        Charset charset = Charset.forName("UTF-8");

       String[] result  = conversionWord(word,accent);

        try (BufferedWriter writer = Files.newBufferedWriter(filePath, charset, StandardOpenOption.APPEND);) {
            writer.write("\n"+result[0]+" "+result[1]);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void writeToWords(String path, String word) {
        Path filePath = Paths.get(path);
        Charset charset = Charset.forName("UTF-8");
        String str = "";
        String result = "";
        String line = "";
        try (BufferedReader reader = Files.newBufferedReader(filePath, charset);
             BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile(),true))) {

            while ((str = reader.readLine()) != null) {
                line = str;
                if (str.startsWith("!# Applications")||str.startsWith("!# Internet")) {
                    while ((str = reader.readLine()) != null) {
                        if (str.startsWith("#")) {
                            break;
                        }
                        if(str.equals(word)){
                            return;
                        }
                        line += "\n" + str;
                    }
                    line = line + "\n" + word + "\n" +"#";

                }
                result += line;
            }
            BufferedWriter reload = new BufferedWriter(new FileWriter(filePath.toFile()));
            reload.close();
            writer.write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    protected void writeToFile(String path,String fileName,String commandName){
        Path filePath = Paths.get(path);
        Charset charset = Charset.forName("UTF-8");

        try (BufferedWriter writer = Files.newBufferedWriter(filePath, charset, StandardOpenOption.APPEND);
             BufferedReader reader = Files.newBufferedReader(filePath, charset);) {
                String str = "";
                while ((str=reader.readLine())!=null){
                    if(str.startsWith(fileName)){
                        return;
                    }
                }
                writer.write("\n"+fileName+"|"+commandName);
            } catch (IOException e1) {
            e1.printStackTrace();
        }

    }

    private String[] conversionWord(String word,int accent){

        String result = "";

        for(int i=0;i<word.toCharArray().length;i++) {
            String ch = word.substring(i,i+1);
            String pre = "";
            Iterator<Map.Entry<String,String>> iterator = letters.entrySet().iterator();

            while (iterator.hasNext()){
                Map.Entry<String,String> pair = iterator.next();
                String key = pair.getKey();
                String value = pair.getValue();

                if(ch.equals(key)){
                    pre +=value;

                    if(i==accent-1 && value.toCharArray().length<2 && isVowel(ch)){
                        pre+=value;
                    }
                    if(i>0){
                        String becomeWord=word.substring(i-1,i);
                        String becomeRes[] = result.split(" ");
                        if(!isVowel(becomeWord)&&!becomeWord.equals("ц")&&
                                becomeRes[becomeRes.length-1].toCharArray().length<2
                                &&(ch.equals("и")||ch.equals("е")
                                ||ch.equals("ю")||ch.equals("я"))){

                            result =  result.trim();
                            int size = result.toCharArray().length;
                            String s = result.substring(size - 1, size);
                            result+=s+" ";

                        }
                    }
                    break;
                }

            }
            result+=pre+" ";
        }

        return new String[]{word,result};
    }

    private Map<String,String> initLetters(){
       Map<String,String> letters = new LinkedHashMap<>();
        Path filePath = Paths.get(transcriptions);
        Charset charset = Charset.forName("UTF-8");
        try (BufferedReader reader = Files.newBufferedReader(filePath, charset)) {

            String str = "";

            while ((str=reader.readLine())!=null){
                String[] array = str.split("-");
                letters.put(array[0],array[1]);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return letters;
    }

    private Boolean isVowel(String s){
        for(String letter:vowelLetters)
        {
            if(s.equals(letter)){
                return true;
            }
        }
        return false;
    }
}
