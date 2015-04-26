package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;


public class Settings extends JFrame {

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
                   // CtrlGui.setErrorTreyMessage("Error:URL is empty!");!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                }

            }
        });
    }

    public void setText(String weather,String news,String flag,String films,String torrent)
    {
        edit=false;
        checkBox1.setSelected(Boolean.parseBoolean(flag));
        urlWeather.setText(weather);
        urlNews.setText(news);
        urlFilms.setText(films);
        urlTorrent.setText(torrent);
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
    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
