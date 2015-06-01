package Models;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dimamj on 31.05.2015.
 */
public class FileOperations {

    private FileSystem fs;
    private static final FileOperations instance = new FileOperations();

    private FileOperations()
    {}

    public static FileOperations getInstance()
    {
        return instance;
    }

    private Boolean isJar(){

        URI uri = null;
        try {uri = getClass().getResource("/others").toURI();} catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return  uri.toString().split("!").length>1 ? true : false;
    }

    /**
     * Метод позволяет копировать файл из Jar или просто из любой папки
     * для того, чтобы произвести копирование из Jar необходимо в качестве
     * 3 параметра задать true.
     *
     * @param sourcePath путь к файлу, откуда копировать /text.txt
     * @param targetPath путь куда вставить C:/text.txt
     * @param inJar копировать из jar или из папки
     */
    protected void copyFiles(String sourcePath,String targetPath,Boolean inJar){
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
        try {Files.copy(source,target);} catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void deleteFiles(String sourcePath){
        Path source = Paths.get(sourcePath);
        try {Files.delete(source);} catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Инициализирование файловой системы для того, чтобы
     * можно было копировать файлы с Jar.
     */
    protected void fileSystemInit(){
        try {
            final URI uri = getClass().getResource("/others").toURI();
            final Map<String, String> env = new HashMap<>();
            if(uri.toString().split("!").length>1) {
                final String[] array = uri.toString().split("!");
                fs = FileSystems.newFileSystem(URI.create(array[0]), env);
            }
        } catch (Exception e) {e.printStackTrace();}
    }



    public  void createFile(String path) {
        File f = new File(path);
        f.getParentFile().mkdirs();
        try {f.createNewFile();} catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Чтение определенной строки файла
     *
     * @param path путь к файлу
     * @param line номер строки
     *
     * @return строку под указанным номером
     */
    public String read(String path,int line) {
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

    /**
     * Чтение и запись каждой строки в список
     *
     * @param path путь к файлу
     * @return коллекцию со всеми строками файла
     */
    public List<String> readMas(String path,List list) {
        BufferedReader reader;
        while (true) {
            try {
                reader = new BufferedReader(new FileReader(path));
                String s;
                int i = 0;
                while ((s=reader.readLine()) != null) {
                    list.add(i,s);
                    i++;
                }
                reader.close();
                break;
            }
            catch (Exception e) {createFile(path);}
        }

        return list;
    }
    public static void write(String path,String language) {

        try {
            PrintWriter out = new PrintWriter(path);
            out.print(language);
            out.close();
        } catch (FileNotFoundException e) {e.printStackTrace();}

    }
}
