package View;

import Presenter.RecognitionListener;

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
    private JLabel textStartup;
    private JLabel textWeather;
    private JLabel textNews;
    private JLabel textFilms;
    private JLabel textTorrent;
    String flag = "";
    private   Boolean edit ;
    static String pathconfig = "C:/Viano/data/config.txt";
    RecognitionListener listener;
    String errorMessage = "";
    @Override
    public void dispose() {
        edit=true;
        super.dispose();
    }

    public Settings(final RecognitionListener listener) {
        Settings.super.setTitle("Viano Settings");
        Image icon = new ImageIcon(getClass().getResource("/images/trey.png")).getImage();
        Settings.super.setIconImage(icon);
        setContentPane(panel);
        this.listener = listener;
        Settings.super.setSize(800, 500);
        Settings.super.setLocationRelativeTo(null);
        Settings.super.setResizable(false);
        Settings.super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        init();
        setVisible(true);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                flag = checkBox1.isSelected()?"true":"false";
                String[] array = new String[]{urlWeather.getText(),urlNews.getText(),urlFilms.getText(),
                        urlTorrent.getText()};

                if(listener.validation("settings",array)) {
                    listener.write(pathconfig, flag + "\n" + urlWeather.getText() + "\n" + urlNews.getText() +
                            "\n" + urlFilms.getText()
                            + "\n" + urlTorrent.getText());
                    edit = true;
                    dispose();
                }
                else {
                    listener.errorMessage(errorMessage);
                }

            }
        });
    }

    public void setText(java.util.List<String> list) {
        edit=false;
        checkBox1.setSelected(Boolean.parseBoolean(list.get(0)));
        urlWeather.setText(list.get(1));
        urlNews.setText(list.get(2));
        urlFilms.setText(list.get(3));
        urlTorrent.setText(list.get(4));
    }

    @Override
    public void init() {
        Properties prop = listener.getProperties();
        textStartup.setText(prop.getProperty("textStartup"));
        textWeather.setText(prop.getProperty("textWeather"));
        textNews.setText(prop.getProperty("textNews"));
        textFilms.setText(prop.getProperty("textFilms"));
        textTorrent.setText(prop.getProperty("textTorrent"));
        saveButton.setText(prop.getProperty("textButton1"));
        errorMessage = prop.getProperty("errorURL");
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
    public String[] getText() {
        return null;
    }

    @Override
    public void setWords(String words) {

    }

    @Override
    public void setImage(String key) {

    }

    @Override
    public void setLabel(String text) {

    }

    @Override
    public void setErrorTreyMessage(String message) {

    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
