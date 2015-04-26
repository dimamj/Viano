package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;


public class Settings extends JFrame implements ViewInterface {

    private JPanel panel;
    private JCheckBox checkBox1;
    private JTextField urlWeather;
    private JTextField urlNews;
    private JButton saveButton;
    private JTextField urlFilms;
    private JTextField urlTorrent;
    String flag = "";
    private   Boolean edit ;
    static String pathconfig = "C:"+ File.separator+ "Viano" +File.separator+"config.txt";

    public Settings() {
        Settings.super.setTitle("Viano Settings");
        Image icon = new ImageIcon(getClass().getResource("/images/trey.png")).getImage();
        Settings.super.setIconImage(icon);
        setContentPane(panel);
        Settings.super.setSize(800, 500);
        Settings.super.setLocationRelativeTo(null);
        Settings.super.setResizable(false);
        setVisible(true);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                flag = checkBox1.isSelected()?"true":"false";
                if(!urlWeather.getText().isEmpty()&&!urlNews.getText().isEmpty()&&!urlFilms.getText().isEmpty()
                        &&!urlTorrent.getText().isEmpty())
                {
                    wtite(pathconfig, flag + "\n" + urlWeather.getText() + "\n" + urlNews.getText() + "\n" + urlFilms.getText()
                            + "\n" + urlTorrent.getText());
                    edit=true;
                    dispose();
                }
                else
                {
                   CtrlGui.setErrorTM("Error:URL is empty!"); /////////////////!!!!!!!!!
                }

            }
        });
    }

    public void setText(java.util.List<String> list)
    {
        edit=false;
        checkBox1.setSelected(Boolean.parseBoolean(list.get(0)));
        urlWeather.setText(list.get(1));
        urlNews.setText(list.get(2));
        urlFilms.setText(list.get(3));
        urlTorrent.setText(list.get(4));
    }

    public static void wtite(String path,String language)
    {

        try {
            PrintWriter out = new PrintWriter(path);
            out.print(language);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public Boolean getEdit()
    {
        return edit;
    }

    @Override
    public void setProgressVisible() {

    }

    @Override
    public void disposeElements() {

    }

    @Override
    public String getText() {
        return null;
    }

    @Override
    public void setWords(String words) {

    }

    @Override
    public void setImage(String key) {

    }

    @Override
    public void setLanguageText(String key) {

    }

    @Override
    public void setErrorTreyMessage(String message) {

    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
