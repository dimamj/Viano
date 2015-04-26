package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.util.List;

/**
 * Created by dimamj on 16.01.2015.
 */
public class Gui extends JFrame implements ViewInterface{

    private JPanel panel;
    private JLabel label1;
    private JTextField textField1;
    private JTextArea TextArea;
    private JProgressBar progressBar1;
    private JToolBar.Separator ToolBar;
    private String text ="";
    public Gui()
    {   Gui.super.setTitle("Viano");
        setContentPane(panel);
        progressBar1.setVisible(false);
        Image icon = new ImageIcon(getClass().getResource("/images/trey.png")).getImage();
        Gui.super.setIconImage(icon);
        Gui.super.setSize(800,500);
        Gui.super.setLocationRelativeTo(null);
        Gui.super.setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        label1.setSize(1,1);



        textField1.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    while (textField1.getText().isEmpty()) {
                        return;
                    }
                    text = textField1.getText();
                }

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }


        });


        setVisible(true);
    }

    @Override
    public void setText(List<String> list) {

    }

    @Override
    public Boolean getEdit() {
        return null;
    }

    public void setProgressVisible()
    {
        progressBar1.setVisible(true);
    }

    public void disposeElements()
    {
        ToolBar.setVisible(true);
        textField1.setVisible(false);
        TextArea.setVisible(false);
    }

    public String getText()
    { text = "";
        while (text=="")
        {

        }
        return this.text;
    }

    @Override
    public void setErrorTreyMessage(String message) {

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

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
