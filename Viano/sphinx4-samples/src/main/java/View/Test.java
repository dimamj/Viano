package View;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by dimamj on 04.05.2015.
 */
public class Test extends JFrame implements ViewInterface{

    private JPanel panel;
    private JTextField textField1;
    private JProgressBar progressBar1;
    private JLabel word;
    private JTextArea пройдитеТестПроизнесяВышеTextArea;
    int n = 0;

    public Test(){
        Test.super.setTitle("Viano Test");
        setContentPane(panel);
        Image icon = new ImageIcon(getClass().getResource("/images/trey.png")).getImage();
        Test.super.setIconImage(icon);
        Test.super.setSize(800, 500);
        Test.super.setLocationRelativeTo(null);
        Test.super.setResizable(false);
        progressBar1.setValue(0);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }


    @Override
    public void setText(List<String> list) {

    }

    @Override
    public Boolean getEdit() {
        return null;
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
    public void setLabel(String text) {
        word.setText(text);

    }

    @Override
    public void setWords(String word) {

        this.textField1.setText("");
        this.textField1.setText(word);
        n+=15;
        progressBar1.setValue(n);
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


}
